/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.services;

import com.desafio.fsbr.desafio_fsbr.dtos.DocumentoPdfDTO;
import com.desafio.fsbr.desafio_fsbr.dtos.RequestUploadDocumentoPdfDTO;
import com.desafio.fsbr.desafio_fsbr.entities.DocumentoPdf;
import com.desafio.fsbr.desafio_fsbr.entities.Processo;
import com.desafio.fsbr.desafio_fsbr.repositories.DocumentoPdfRepository;
import com.desafio.fsbr.desafio_fsbr.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;

/**
 *
 * @author Usuario
 */
@Service
public class DocumentoPdfService {
    @Autowired
    private DocumentoPdfRepository documentoPdfRepository;
    
    @Autowired
    private ProcessoService processoService;
    
    private final Path rootLocation = Paths.get("uploads");
    
    @Autowired
    private ConvertUtil convert;

    private DocumentoPdf convertToEntity(DocumentoPdfDTO documentoPdf) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(documentoPdf, DocumentoPdf.class);
    }
    
    public DocumentoPdfDTO save(MultipartFile file, Processo processo) throws IOException {
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }
        String filename = file.getOriginalFilename();
        Path destinationFile = rootLocation.resolve(filename);
        Files.copy(file.getInputStream(), destinationFile);

        DocumentoPdf documentoPdf = new DocumentoPdf();
        documentoPdf.setPath(destinationFile.toString());
        documentoPdf.setProcesso(processo);

        documentoPdf  = documentoPdfRepository.save(documentoPdf);
        return convert.convertToDto(documentoPdf);
    }
    
    public DocumentoPdfDTO save(RequestUploadDocumentoPdfDTO request) throws IOException {
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }
        String filename = request.getFile().getOriginalFilename();
        Path destinationFile = rootLocation.resolve(filename);
        Files.copy(request.getFile().getInputStream(), destinationFile);
        
        Processo processo = processoService.findById(request.getProcessoId());
        DocumentoPdf documentoPdf = new DocumentoPdf();
        documentoPdf.setPath(destinationFile.toString());
        documentoPdf.setProcesso(processo);

        documentoPdf  = documentoPdfRepository.save(documentoPdf);
        return convert.convertToDto(documentoPdf);
    }

    public List<DocumentoPdf> findAll() {
        return documentoPdfRepository.findAll();
    }

    public Optional<DocumentoPdf> findById(Long id) {
        return documentoPdfRepository.findById(id);
    }

    public void deleteById(Long id) {
        documentoPdfRepository.deleteById(id);
    }
    
    public List<DocumentoPdfDTO> findByProcesso(Processo processo) {
        List<DocumentoPdf> lista = documentoPdfRepository.findByProcesso(processo);
        return lista.stream().map(o -> convert.convertToDto(o)).collect(Collectors.toList());
    }
}
