/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.repositories;

import com.desafio.fsbr.desafio_fsbr.entities.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Usuario
 */
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    
}
