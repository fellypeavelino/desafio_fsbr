/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.controllers;

import com.desafio.fsbr.desafio_fsbr.dtos.UsuarioDTO;
import com.desafio.fsbr.desafio_fsbr.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping
    public ResponseEntity<UsuarioDTO> salvar(@RequestBody @Valid UsuarioDTO usuario) {
        UsuarioDTO usuarioDto = new UsuarioDTO();
        try {
            usuarioDto = usuarioService.encontrarPorLoguinESenhaDTO(usuario);
        } catch (Exception e) {
            return new ResponseEntity<>(usuarioDto, HttpStatus.BAD_GATEWAY);
        }
        return ResponseEntity.ok(usuarioDto);
    }
}
