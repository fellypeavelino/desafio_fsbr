/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Usuario
 */
@Data
public class RequestUploadDocumentoPdfDTO {
    @NotNull(message = "O campo 'file' não pode ser nulo.")
    private MultipartFile file;
    @NotNull(message = "O campo 'processoId' não pode ser nulo.")
    private Long processoId;
}
