package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.Employee;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.secuirty.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        log.info("Authenticating user: {}", request.getEmail());

        Employee employee = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        // In production, you would have a User/Auth table with password field
        // For now, we'll create a simple validation
        // TODO: Implement proper user authentication with password field

        String accessToken = jwtTokenProvider.generateToken(employee.getId(), employee.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(employee.getId(), employee.getEmail());

        AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo();
        userInfo.setId(employee.getId());
        userInfo.setEmail(employee.getEmail());
        userInfo.setFullName(employee.getFirstName() + " " + employee.getLastName());
        userInfo.setRole("EMPLOYEE"); // TODO: Get from user role

        AuthResponse response = new AuthResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setExpiresIn(3600L); // 1 hour
        response.setUser(userInfo);

        log.info("User {} authenticated successfully", request.getEmail());
        return response;
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        log.info("Refreshing token");

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BadRequestException("Invalid refresh token");
        }

        Long employeeId = jwtTokenProvider.getEmployeeIdFromToken(refreshToken);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        String newAccessToken = jwtTokenProvider.generateToken(employee.getId(), employee.getEmail());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(employee.getId(), employee.getEmail());

        AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo();
        userInfo.setId(employee.getId());
        userInfo.setEmail(employee.getEmail());
        userInfo.setFullName(employee.getFirstName() + " " + employee.getLastName());
        userInfo.setRole("EMPLOYEE");

        AuthResponse response = new AuthResponse();
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(newRefreshToken);
        response.setExpiresIn(3600L);
        response.setUser(userInfo);

        log.info("Token refreshed successfully for employee: {}", employeeId);
        return response;
    }

    @Override
    public void logout(String token) {
        log.info("User logout");
        // In production, invalidate the token by adding it to a blacklist
        // or storing it in Redis with TTL
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateToken(String token) {
        try {
            return jwtTokenProvider.validateToken(token);
        } catch (Exception e) {
            log.error("Token validation error: {}", e.getMessage());
            return false;
        }
    }
}
