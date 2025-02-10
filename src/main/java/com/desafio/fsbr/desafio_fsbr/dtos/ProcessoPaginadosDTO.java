/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.dtos;

import java.util.List;
import lombok.Data;

/**
 *
 * @author Usuario
 */
@Data
public class ProcessoPaginadosDTO {
    private List<ProcessoDTO> processoDto;
    private RequestPageDTO param;
    private Long total;
}
