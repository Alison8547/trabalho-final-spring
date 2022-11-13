package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.IndicacaoCreateDto;
import br.com.dbc.dbcmovies.dto.IndicacaoDto;
import br.com.dbc.dbcmovies.dto.PageDTO;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
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

    public IndicacaoDto incluirIndicacao(IndicacaoCreateDto indicacao) throws RegraDeNegocioException {

        Integer idUsuario = usuarioService.getLoggedUser().getIdUsuario();

        IndicacaoPK indicacaoPK = new IndicacaoPK(idUsuario, indicacao.getItemNome());
        IndicacaoEntity indicacaoEntity = objectMapper.convertValue(indicacao, IndicacaoEntity.class);
        indicacaoEntity.setIndicacaoPK(indicacaoPK);
        indicacaoEntity.setUsuario(usuarioService.findById(idUsuario));
        indicacaoRepository.save(indicacaoEntity);

        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioService.findById(idUsuario), UsuarioDto.class);

        IndicacaoDto indicacaoDto = objectMapper.convertValue(indicacao, IndicacaoDto.class);
        indicacaoDto.setItemNome(indicacao.getItemNome());
        indicacaoDto.setUsuario(usuarioDto);
        return indicacaoDto;
    }

    public PageDTO<IndicacaoDto> listPessoaIndicacaoPaginada(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<IndicacaoEntity> paginaEntity = indicacaoRepository.findAll(pageRequest);

        List<IndicacaoDto> indicacaoDtos = paginaEntity.getContent().stream()
                .map(indicacaoEntity -> {
                    IndicacaoDto indicacaoDto = objectMapper.convertValue(indicacaoEntity, IndicacaoDto.class);
                    indicacaoDto.setItemNome(indicacaoEntity.getIndicacaoPK().getNomeItem());
                    UsuarioDto usuarioDto = objectMapper.convertValue(indicacaoEntity.getUsuario(), UsuarioDto.class);
                    indicacaoDto.setUsuario(usuarioDto);
                    return indicacaoDto;
                }).toList();

        return new PageDTO<>(paginaEntity.getTotalElements(),
                paginaEntity.getTotalPages(),
                pagina,
                tamanho,
                indicacaoDtos);
    }
}
