package com.dukankhata.common.observability;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long started = System.nanoTime();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long durationMs = (System.nanoTime() - started) / 1_000_000;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String actor = authentication != null && authentication.isAuthenticated()
                    ? authentication.getName() : "anonymous";
            String line = "request method=" + request.getMethod()
                    + " path=" + request.getRequestURI()
                    + " status=" + response.getStatus()
                    + " durationMs=" + durationMs
                    + " actor=" + sanitize(actor);
            if (durationMs >= 1000) {
                log.warn("slow_request {}", line);
            } else {
                log.info("request {}", line);
            }
        }
    }

    private String sanitize(String value) {
        return value == null ? "anonymous" : value.replaceAll("[\\r\\n\\t]", "_");
    }
}
