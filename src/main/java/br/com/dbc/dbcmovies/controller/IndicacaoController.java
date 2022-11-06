package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.dto.IndicacaoCreateDto;
import br.com.dbc.dbcmovies.dto.IndicacaoDto;
import br.com.dbc.dbcmovies.dto.IndicacaoPagDto;
import br.com.dbc.dbcmovies.dto.PageDTO;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.IndicacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/indicar")
public class IndicacaoController {

    private final IndicacaoService indicacaoService;

    @Operation(summary = "Indicar um filme / série", description = "Adiciona uma indicação no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Marcado como assistido com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idUsuario}")
    public ResponseEntity<IndicacaoDto> indicar(@PathVariable(name = "idUsuario") Integer idUsuario, @RequestBody IndicacaoCreateDto indicacaoDto) throws RegraDeNegocioException {
        return new ResponseEntity<>(indicacaoService.incluirIndicacao(indicacaoDto, idUsuario), HttpStatus.OK);
    }

    @Operation(summary = "Pega a lista paginada de indicações", description = "Resgata a lista paginada do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/indicacao-paginada")
    public ResponseEntity<PageDTO<IndicacaoPagDto>> listIndicacaoPaginada(Integer pagina, Integer tamanho) {
        return new ResponseEntity<>(indicacaoService.listPessoaIndicacaoPaginada(pagina,tamanho),HttpStatus.OK);
    }
}
