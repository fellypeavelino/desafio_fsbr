/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.controllers;

import com.desafio.fsbr.desafio_fsbr.dtos.DocumentoPdfDTO;
import com.desafio.fsbr.desafio_fsbr.dtos.RequestUploadDocumentoPdfDTO;
import com.desafio.fsbr.desafio_fsbr.entities.DocumentoPdf;
import com.desafio.fsbr.desafio_fsbr.entities.Processo;
import com.desafio.fsbr.desafio_fsbr.services.DocumentoPdfService;
import com.desafio.fsbr.desafio_fsbr.services.ProcessoService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/documentos")
public class DocumentoPdfController {
    
    @Autowired
    private DocumentoPdfService documentoPdfService;
    
    @Autowired
    private ProcessoService processoService;
    
    @PostMapping
    public ResponseEntity<DocumentoPdfDTO> uploadFile(@Valid @RequestBody RequestUploadDocumentoPdfDTO request) throws IOException {
        DocumentoPdfDTO documentoPdf = new DocumentoPdfDTO();
        try {
            documentoPdf = documentoPdfService.save(request);
        } catch (Exception e) {
            return new ResponseEntity<>(documentoPdf, HttpStatus.BAD_GATEWAY);
        }
        return ResponseEntity.ok(documentoPdf);
    }
    
    @GetMapping("/processo/{processoId}")
    public ResponseEntity<List<DocumentoPdfDTO>> getDocumentosByProcesso(@PathVariable Long processo_id) {
        List<DocumentoPdfDTO> documentos = new ArrayList<>();
        try {
            Processo processo = processoService.findById(processo_id);
            documentos = documentoPdfService.findByProcesso(processo);
        } catch (Exception e) {
            return new ResponseEntity<>(documentos, HttpStatus.BAD_GATEWAY);
        }
        return ResponseEntity.ok(documentos);
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadPdf(@PathVariable Long id) {
        Optional<DocumentoPdf> op = documentoPdfService.findById(id);
        DocumentoPdf documentoPdf = new DocumentoPdf();
        if (op.isPresent()) {
            documentoPdf = op.get();
        }
        // Recuperar o conteúdo do PDF (byte[])
        byte[] pdfBytes = documentoPdf.getDocumentoPdf();

        // Criar um ByteArrayResource a partir dos bytes do PDF
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+documentoPdf.getPath())
            .contentLength(pdfBytes.length)
            .body(resource);
    }
}
