package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.IndicacaoCreateDto;
import br.com.dbc.dbcmovies.dto.IndicacaoDto;
import br.com.dbc.dbcmovies.dto.UsuarioNomeDto;
import br.com.dbc.dbcmovies.entity.IndicacaoEntity;
import br.com.dbc.dbcmovies.entity.pk.IndicacaoPK;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.IndicacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
