/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.services;

import com.desafio.fsbr.desafio_fsbr.dtos.DocumentoPdfDTO;
import com.desafio.fsbr.desafio_fsbr.dtos.ProcessoDTO;
import com.desafio.fsbr.desafio_fsbr.dtos.ProcessoPaginadosDTO;
import com.desafio.fsbr.desafio_fsbr.dtos.RequestPageDTO;
import com.desafio.fsbr.desafio_fsbr.entities.DocumentoPdf;
import com.desafio.fsbr.desafio_fsbr.entities.Processo;
import com.desafio.fsbr.desafio_fsbr.entities.Usuario;
import com.desafio.fsbr.desafio_fsbr.repositories.DocumentoPdfRepository;
import com.desafio.fsbr.desafio_fsbr.repositories.ProcessoRepository;
import com.desafio.fsbr.desafio_fsbr.repositories.UsuarioRepository;
import com.desafio.fsbr.desafio_fsbr.utils.ConvertUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger logger = LoggerFactory.getLogger(ProcessoService.class);
    
    private static final String PROCESSO_NAO_ENCONTRADO = "Processo não encontrado";
    
    @Autowired
    private ConvertUtil convert;
    
    @Autowired
    private ProcessoRepository repository;
    
    @Autowired
    private DocumentoPdfRepository documentoPdfRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Processo salvarContato(Processo contato, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        contato.setUsuario(usuario);
        return repository.save(contato);
    }

    public ProcessoDTO salvarContatoDto(ProcessoDTO processoDto) {
        Processo processo = convert.convertToEntity(processoDto);
        processo = salvarContato(processo, processoDto.getUsuario_id());
        return convert.convertToDto(processo);
    }

    public ProcessoDTO alterarDto(Long id, ProcessoDTO processoDto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException(PROCESSO_NAO_ENCONTRADO));
        ProcessoDTO pdto = salvarContatoDto(processoDto);
        for (DocumentoPdfDTO documentoPdfDTO : processoDto.getDocumentosDto()) {
            DocumentoPdf doc = convert.convertToEntity(documentoPdfDTO);
            doc.setProcesso(convert.convertToEntity(pdto));
            documentoPdfRepository.save(doc);
        }
        return pdto;
    }
    
    public ProcessoDTO findByNpu(String npu){
        ProcessoDTO pdto = new ProcessoDTO();
        Optional<Processo> p = repository.findByNpu(npu);
        if(p.isPresent()){
            pdto = convert.convertToDto(p.get());
        }
        return pdto;
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public ProcessoDTO pesquisar(Long id) {
        Processo processo = this.findById(id);
        //List<DocumentoPdf> documentos = documentoPdfRepository.findByProcesso(processo);
        //List<DocumentoPdf> documentos = documentoPdfRepository.findByProcessoId(id);
        List<DocumentoPdfDTO> documentosDto = new ArrayList<>();
        List<Object[]> documentos = documentoPdfRepository.findDocumentosByProcessoId(id);
        documentos.forEach(obj -> {;
            DocumentoPdfDTO dto = new DocumentoPdfDTO(
                ((Number) obj[0]).longValue(),  // id
                (String) obj[1],               // path
                ((Number) obj[2]).longValue(), // processoId
                obj[3] instanceof byte[] ? (byte[]) obj[3] : null // documentoPdf
            );
            documentosDto.add(dto);
        });
//        List<DocumentoPdfDTO> documentosDto = documentos.stream()
//            .map(o -> convert.convertToDto(o))
//            .collect(Collectors.toList());
        ProcessoDTO processoDTO = convert.convertToDto(processo);
        processoDTO.setDocumentosDto(documentosDto);
        return processoDTO;
    }

    public Processo findById(Long id) {
        Processo processo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(PROCESSO_NAO_ENCONTRADO));
        return processo;
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
        contatos.forEach(c -> listResult.add(convert.convertToDto(c)));
        return listResult;
    }
}
