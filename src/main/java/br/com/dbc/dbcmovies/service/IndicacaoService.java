//package br.com.dbc.dbcmovies.service;
//
//import br.com.dbc.dbcmovies.dto.IndicacaoCreateDto;
//import br.com.dbc.dbcmovies.dto.IndicacaoDto;
//import br.com.dbc.dbcmovies.dto.UsuarioNomeDto;
//import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
//import br.com.dbc.dbcmovies.repository.IndicacaoRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//
//@Service
//@RequiredArgsConstructor
//public class IndicacaoService {
//
//    private final IndicacaoRepository indicacaoRepository;
//    private final UsuarioService usuarioService;
//    private final ObjectMapper objectMapper;
//
//    public IndicacaoDto incluirIndicacao(IndicacaoCreateDto indicacaoDto, Integer idUsuario) throws RegraDeNegocioException {
//        try{
//
//            UsuarioNomeDto usuarioNomeDto = objectMapper.convertValue(usuarioService.findById(idUsuario), UsuarioNomeDto.class);
//            Indicacao indicacao = indicacaoRepository.indicar(indicacaoDto.getItemNome(), idUsuario);
//
//            IndicacaoDto dto = new IndicacaoDto();
//            dto.setItemNome(indicacao.getNomeItem());
//            dto.setUsuario(usuarioNomeDto);
//            return dto;
//
//        }catch (BancoDeDadosException ex){
//            throw new RegraDeNegocioException(ex.getMessage());
//        }
//    }
//}
