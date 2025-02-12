/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.repositories;

import com.desafio.fsbr.desafio_fsbr.entities.DocumentoPdf;
import com.desafio.fsbr.desafio_fsbr.entities.Processo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Usuario
 */
public interface DocumentoPdfRepository extends JpaRepository<DocumentoPdf,Long> {
    List<DocumentoPdf> findByProcesso(Processo processo);
    
    @Query(value = "SELECT * FROM documento_pdf WHERE processo_id = :processoId", nativeQuery = true)
    List<DocumentoPdf> findByProcessoId(@Param("processoId") Long processoId);
    
    @Query(value = "SELECT id, local_pdf, processo_id, documento_pdf FROM documento_pdf WHERE processo_id = :processoId", nativeQuery = true)
    List<Object[]> findDocumentosByProcessoId(@Param("processoId") Long processoId);
}
