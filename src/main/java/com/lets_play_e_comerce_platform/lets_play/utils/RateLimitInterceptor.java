package com.lets_play_e_comerce_platform.lets_play.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    
    private final ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> lastResetTime = new ConcurrentHashMap<>();
    private final int MAX_REQUESTS = 20; // requests per minute
    private final long RESET_INTERVAL = 60000; // 1 minute in milliseconds
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();
        
        // Reset counter if a minute has passed
        Long lastReset = lastResetTime.get(clientIp);
        if (lastReset == null || (currentTime - lastReset) >= RESET_INTERVAL) {
            requestCounts.put(clientIp, new AtomicInteger(0));
            lastResetTime.put(clientIp, currentTime);
        }
        
        AtomicInteger count = requestCounts.computeIfAbsent(clientIp, k -> new AtomicInteger(0));
        
        if (count.incrementAndGet() > MAX_REQUESTS) {
            response.setStatus(429);
            response.getWriter().write("Rate limit exceeded. Try again later.");
            return false;
        }
        
        return true;
    }
}