/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.utils;

import com.desafio.fsbr.desafio_fsbr.dtos.DocumentoPdfDTO;
import com.desafio.fsbr.desafio_fsbr.dtos.ProcessoDTO;
import com.desafio.fsbr.desafio_fsbr.entities.DocumentoPdf;
import com.desafio.fsbr.desafio_fsbr.entities.Processo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Usuario
 */
@Component
public class ConvertUtil {
    public ProcessoDTO convertToDto(Processo processo) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(processo, ProcessoDTO.class);
    }

    public Processo convertToEntity(ProcessoDTO processoDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(processoDto, Processo.class);
    }
    
    public DocumentoPdfDTO convertToDto(DocumentoPdf documentoPdf) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(documentoPdf, DocumentoPdfDTO.class);
    }
}
