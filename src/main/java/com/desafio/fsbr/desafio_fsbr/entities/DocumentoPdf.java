/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "documento_pdf")
@Data
public class DocumentoPdf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "local_pdf")
    private String path;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "documento_pdf")
    private byte[] documentoPdf;
    @ManyToOne
    @JoinColumn(name = "processo_id", nullable = false)
    private Processo processo;
}
