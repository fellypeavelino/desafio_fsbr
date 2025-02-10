/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.controllers;

import com.desafio.fsbr.desafio_fsbr.dtos.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desafio.fsbr.desafio_fsbr.utils.JwtUtil;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    private JwtUtil jwtUtil;
    
    @GetMapping
    public ResponseEntity<TokenDTO> gerarToken() {
        TokenDTO t = new TokenDTO();
        t.setSub(jwtUtil.generateToken("fsbr"));
        return ResponseEntity.ok(t);
    }
}
