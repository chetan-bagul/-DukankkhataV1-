package com.dukankhata.auth;

import com.dukankhata.auth.dto.*;
import com.dukankhata.common.exception.ConflictException;
import com.dukankhata.common.exception.InvalidCredentialsException;
import com.dukankhata.shop.Shop;
import com.dukankhata.shop.ShopRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, ShopRepository shopRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse signup(SignupRequest request) {
        String mobile = request.mobile().trim();
        if (userRepository.existsByMobile(mobile)) {
            throw new ConflictException("Mobile number is already registered");
        }

        Shop shop = new Shop();
        shop.setName(request.shopName().trim());
        shop.setOperatingMode(request.operatingMode() == null || request.operatingMode().isBlank()
                ? "BOTH" : request.operatingMode().trim().toUpperCase());
        shop.setPlanStatus("TRIAL");
        shopRepository.save(shop);

        User user = new User();
        user.setName(request.name().trim());
        user.setMobile(mobile);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole("OWNER");
        user.setShop(shop);
        userRepository.save(user);

        return toResponse(user, jwtService.generateToken(user));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByMobile(request.mobile().trim()).orElse(null);
        if (user == null || !user.isActive() ||
                !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        return toResponse(user, jwtService.generateToken(user));
    }

    @Transactional(readOnly = true)
    public MeResponse me(User user) {
        return new MeResponse(user.getId(), user.getShop().getId(), user.getName(), user.getMobile(),
                user.getRole(), user.getShop().getName(), user.getShop().getOperatingMode(), user.getShop().getPlanStatus());
    }

    private AuthResponse toResponse(User user, String token) {
        return new AuthResponse(token, "Bearer", user.getId(), user.getShop().getId(), user.getName(),
                user.getMobile(), user.getRole(), user.getShop().getName(), user.getShop().getOperatingMode(), user.getShop().getPlanStatus());
    }
}
