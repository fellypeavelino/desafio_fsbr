/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Usuario
 */
@Data
public class ProcessoDTO {
    
    private Long id;
    @NotNull(message = "O campo 'npu' não pode ser nulo.")
    private String npu;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataVisualizacao;
    @NotNull(message = "O campo 'municipio' não pode ser nulo.")
    private String municipio;
    @NotNull(message = "O campo 'uf' não pode ser nulo.")
    private String uf;
    @NotNull(message = "O campo 'usuario_id' não pode ser nulo.")
    private Long usuario_id;
    private List<DocumentoPdfDTO> documentosDto;
}
