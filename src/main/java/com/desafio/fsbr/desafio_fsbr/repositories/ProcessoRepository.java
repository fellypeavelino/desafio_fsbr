/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.repositories;

import com.desafio.fsbr.desafio_fsbr.entities.Processo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Usuario
 */
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    List<Processo> findAllByOrderByIdDesc();
    
    @Query("SELECT p FROM Processo p")
    Page<Processo> findPage(Pageable pageable);
    
    @Query("SELECT p FROM Processo p WHERE " +
            "LOWER(p.npu) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(p.municipio) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(p.uf) LIKE LOWER(CONCAT('%', :term, '%'))")
    Page<Processo> findPageByFiltro(@Param("term") String term, Pageable pageable);
    
    @Query("SELECT p FROM Processo p WHERE " +
            "LOWER(p.npu) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(p.municipio) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(p.uf) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Processo> findFiltro(@Param("term") String term);
}
