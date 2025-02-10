/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.services;

import com.desafio.fsbr.desafio_fsbr.dtos.ProcessoDTO;
import com.desafio.fsbr.desafio_fsbr.dtos.ProcessoPaginadosDTO;
import com.desafio.fsbr.desafio_fsbr.dtos.RequestPageDTO;
import com.desafio.fsbr.desafio_fsbr.entities.Processo;
import com.desafio.fsbr.desafio_fsbr.entities.Usuario;
import com.desafio.fsbr.desafio_fsbr.repositories.ProcessoRepository;
import com.desafio.fsbr.desafio_fsbr.repositories.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class ProcessoService {
    
    private static final String PROCESSO_NAO_ENCONTRADO = "Processo não encontrado";
    
    @Autowired
    private ProcessoRepository repository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private ProcessoDTO convertToDto(Processo processo) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(processo, ProcessoDTO.class);
    }

    private Processo convertToEntity(ProcessoDTO processoDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(processoDto, Processo.class);
    }
    
    public Processo salvarContato(Processo contato, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        contato.setUsuario(usuario);
        return repository.save(contato);
    }

    public ProcessoDTO salvarContatoDto(ProcessoDTO processoDto) {
        Processo processo = convertToEntity(processoDto);
        processo = salvarContato(processo, processoDto.getUsuario_id());
        return convertToDto(processo);
    }

    public ProcessoDTO alterarDto(Long id, ProcessoDTO processoDto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException(PROCESSO_NAO_ENCONTRADO));
        return salvarContatoDto(processoDto);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public ProcessoDTO pesquisar(Long id) {
        Processo processo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(PROCESSO_NAO_ENCONTRADO));
        ProcessoDTO processoDTO = convertToDto(processo);
        return processoDTO;
    }
    
    public ProcessoPaginadosDTO getProcessosPaginadosEOrdenadosPorQuery(RequestPageDTO dto) {
        ProcessoPaginadosDTO result = new ProcessoPaginadosDTO();
        List<ProcessoDTO> processosDto = new ArrayList<>();
        result.setParam(dto);

        String sortBy = dto.getSortBy();
        String sortDir = dto.getSortDir();
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), sort);

        Page<Processo> page = null;
        long total = 0;
        if (dto.getFiltro() != null && !dto.getFiltro().isEmpty()) {
            page = repository.findPageByFiltro(dto.getFiltro(), pageable);
            total = repository.findFiltro(dto.getFiltro()).size();
        } else {
            page = repository.findPage(pageable);
            total = repository.count();
        }

        processosDto = this.convertToListDto(page.getContent());
        result.setProcessoDto(processosDto);
        result.setTotal(total);
        return result;
    }
    
    private List<ProcessoDTO> convertToListDto(List<Processo> contatos) {
        List<ProcessoDTO> listResult = new ArrayList<>();
        contatos.forEach(c -> listResult.add(this.convertToDto(c)));
        return listResult;
    }
}
