/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author Usuario
 */
@Data
public class UsuarioDTO {
    private Long id;
    @NotNull(message = "O campo 'loguin' não pode ser nulo.")
    private String loguin;
    @NotNull(message = "O campo 'senha' não pode ser nulo.")
    private String senha;
}