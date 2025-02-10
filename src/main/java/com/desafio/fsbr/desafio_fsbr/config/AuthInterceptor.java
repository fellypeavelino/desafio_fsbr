/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.desafio.fsbr.desafio_fsbr.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Usuario
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<String> allowedURIs = Arrays.asList("/token", "/v3/api-docs");
        String requestURI = request.getRequestURI();
        if (allowedURIs.contains(requestURI) || requestURI.contains("swagger") || requestURI.contains("usuario")) {
            return true;
        }
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            logger.warn("Tentativa de acesso sem Authorization header "+request.getRequestURI());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is missing");
            return false;
        }
        if(!this.validarToken(authHeader, response)){;
            return false;
        }
        logger.info("Requisição autorizada "+request.getRequestURI(), request.getRequestURI());
        return true;
    }
    
    private boolean validarToken(String authHeader, HttpServletResponse response) throws IOException {
        try { 
            String sub = jwtUtil.decodeToken(authHeader);
            return sub.equals("fsbr");
        } catch (Exception e) {
            logger.error("Erro ao decodificar JWT: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
            return false;
        }
    }
}
