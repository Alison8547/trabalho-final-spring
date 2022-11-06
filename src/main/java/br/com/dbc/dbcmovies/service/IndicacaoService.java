package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.*;
import br.com.dbc.dbcmovies.entity.IndicacaoEntity;
import br.com.dbc.dbcmovies.entity.pk.IndicacaoPK;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.IndicacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndicacaoService {

    private final IndicacaoRepository indicacaoRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public IndicacaoDto incluirIndicacao(IndicacaoCreateDto indicacaoDto, Integer idUsuario) throws RegraDeNegocioException {

        UsuarioNomeDto usuarioNomeDto = objectMapper.convertValue(usuarioService.findById(idUsuario), UsuarioNomeDto.class);
        IndicacaoPK indicacaoPK = new IndicacaoPK(idUsuario, indicacaoDto.getItemNome());
        IndicacaoEntity indicacao = objectMapper.convertValue(indicacaoDto, IndicacaoEntity.class);
        indicacao.setIndicacaoPK(indicacaoPK);
        indicacao.setUsuario(usuarioService.findById(idUsuario));
        indicacaoRepository.save(indicacao);

        IndicacaoDto indicacaoDTO = objectMapper.convertValue(indicacao, IndicacaoDto.class);
        indicacaoDTO.setUsuario(usuarioNomeDto);
        indicacaoDTO.setItemNome(indicacaoDto.getItemNome());
        return indicacaoDTO;
    }

    public PageDTO<IndicacaoPagDto> listPessoaIndicacaoPaginada(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<IndicacaoEntity> paginaEntity = indicacaoRepository.findAll(pageRequest);

        List<IndicacaoPagDto> indicacaoDtos = paginaEntity.getContent().stream()
                .map(indicacaoEntity -> {
                    IndicacaoPagDto indicacaoDto = objectMapper.convertValue(indicacaoEntity, IndicacaoPagDto.class);
                    indicacaoDto.setNomeItem(indicacaoEntity.getIndicacaoPK().getNomeItem());
                    indicacaoDto.setIdUsuario(indicacaoEntity.getUsuario().getIdUsuario());
                    return indicacaoDto;
                }).toList();


        return new PageDTO<>(paginaEntity.getTotalElements(),
                paginaEntity.getTotalPages(),
                pagina,
                tamanho,
                indicacaoDtos);
    }
}
