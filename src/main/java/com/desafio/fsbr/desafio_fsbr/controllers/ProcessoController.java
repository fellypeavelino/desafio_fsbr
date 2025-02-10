/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.controllers;

import com.desafio.fsbr.desafio_fsbr.dtos.ProcessoDTO;
import com.desafio.fsbr.desafio_fsbr.dtos.ProcessoPaginadosDTO;
import com.desafio.fsbr.desafio_fsbr.dtos.RequestPageDTO;
import com.desafio.fsbr.desafio_fsbr.services.ProcessoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/processos")
public class ProcessoController {
    @Autowired
    private ProcessoService service;
    
    @PostMapping
    public ResponseEntity<ProcessoDTO> salvar(@RequestBody @Valid ProcessoDTO processoDto) {
        try {
            processoDto = service.salvarContatoDto(processoDto);
        } catch (Exception e) {
            return new ResponseEntity<>(new ProcessoDTO(), HttpStatus.BAD_GATEWAY);
        }
        return ResponseEntity.ok(processoDto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProcessoDTO> alterar(@PathVariable Long id, @RequestBody @Valid ProcessoDTO processoDto) {
        try {
            processoDto = service.alterarDto(id, processoDto);
        } catch (Exception e) {
            return new ResponseEntity<>(new ProcessoDTO(), HttpStatus.BAD_GATEWAY);
        }
        return ResponseEntity.ok(processoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProcessoDTO> pesquisar(@PathVariable Long id) {
        ProcessoDTO processoDto = new ProcessoDTO();
        try {
            processoDto = service.pesquisar(id);
        } catch (Exception e) {
            return new ResponseEntity<>(new ProcessoDTO(), HttpStatus.BAD_GATEWAY);
        }
        return ResponseEntity.ok(processoDto);
    }
    
    @PostMapping("/paginacao")
    public ProcessoPaginadosDTO getContatosPaginadosEOrdenadosPorQuery(@Valid @RequestBody RequestPageDTO dto) {
        return service.getProcessosPaginadosEOrdenadosPorQuery(dto);
    }
}
