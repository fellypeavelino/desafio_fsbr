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
import java.time.LocalDateTime;
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
import org.springframework.transaction.annotation.Transactional;

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
    
    public Processo salvarProcesso(Processo processo, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        processo.setUsuario(usuario);
        return repository.save(processo);
    }

    public ProcessoDTO salvarProcessoDto(ProcessoDTO processoDto) {
        Processo processo = convert.convertToEntity(processoDto);
        processo = salvarProcesso(processo, processoDto.getUsuario_id());
        for (DocumentoPdfDTO documentoPdfDTO : processoDto.getDocumentosDto()) {
            DocumentoPdf doc = convert.convertToEntity(documentoPdfDTO);
            doc.setProcesso(processo);
            documentoPdfRepository.save(doc);
        }
        return convert.convertToDto(processo);
    }

    public ProcessoDTO alterarDto(Long id, ProcessoDTO processoDto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException(PROCESSO_NAO_ENCONTRADO));
        ProcessoDTO pdto = salvarProcessoDto(processoDto);
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

    @Transactional
    public void excluir(Long id) {
        documentoPdfRepository.deleteByProcessoId(id);
        repository.deleteById(id);
    }

    public ProcessoDTO pesquisar(Long id) {
        Processo processo = this.findById(id);
        List<DocumentoPdf> documentos = documentoPdfRepository.findByProcesso(processo);
        //List<DocumentoPdf> documentos = documentoPdfRepository.findByProcessoId(id);
        List<DocumentoPdfDTO> documentosDto = new ArrayList<>();
//        List<Object[]> documentos = documentoPdfRepository.findDocumentosByProcessoId(id);
//        documentos.forEach(obj -> {;
//            DocumentoPdfDTO dto = new DocumentoPdfDTO(
//                ((Number) obj[0]).longValue(),  // id
//                (String) obj[1],               // path
//                ((Number) obj[2]).longValue(), // processoId
//                (byte[]) obj[3] // documentoPdf
//            );
//            documentosDto.add(dto);
//        });
        documentos.forEach(obj -> {
            DocumentoPdfDTO dto = convert.convertToDto(obj);
            documentosDto.add(dto);
        });
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
    
    public ProcessoDTO visualizacao(Long id){
        Optional<Processo> op = repository.findById(id);
        Processo processo = new Processo();
        if (op.isPresent()) {
            processo = op.get();
            processo.setDataVisualizacao(LocalDateTime.now());
            repository.save(processo);
        }
        return pesquisar(id);
    }
}
