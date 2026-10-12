package com.dukankhata.subscription;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface WebhookEventRepository extends JpaRepository<WebhookEvent,UUID>{boolean existsByProviderEventId(String id);}
