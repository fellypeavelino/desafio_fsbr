/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.dtos;

import lombok.Data;

/**
 *
 * @author Usuario
 */
@Data
public class DocumentoPdfDTO {
    
    public DocumentoPdfDTO(){}
    
    public DocumentoPdfDTO(
        Long id, String path,
        Long processo_id,
        byte[] documentoPdf
    ){
        this.id = id;
        this.path = path;
        this.documentoPdf = documentoPdf;
        this.processo_id = processo_id.intValue();
    }
    
    
    private Long id;
    private String path;
    private byte[] documentoPdf;
    private Integer processo_id;
}
