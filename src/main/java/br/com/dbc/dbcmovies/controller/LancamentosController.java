package br.com.dbc.dbcmovies.controller;


import br.com.dbc.dbcmovies.dto.LancamentoCreateDto;
import br.com.dbc.dbcmovies.dto.LancamentoDto;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.LancamentosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/lancamentos")
public class LancamentosController {

    private final LancamentosService lancamentosService;

    @Operation(summary = "Listar Lançamentos", description = "Lista os lançamentos no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Lista feita com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<LancamentoDto>> listAll() throws RegraDeNegocioException {
        return ResponseEntity.ok(lancamentosService.list());
    }

    @Operation(summary = "Criar Lançamento", description = "Cria um lançamento no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Lançamento Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/lancamento")
    public ResponseEntity<LancamentoDto> createFilme(@Valid @RequestBody LancamentoCreateDto lancamentoCreateDto) {
        return ResponseEntity.ok(lancamentosService.createLancamento(lancamentoCreateDto));
    }

    @Operation(summary = "Atualizar Lançamento", description = "Atualiza um lançamento no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizou com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/filme/{idLancamento}")
    public ResponseEntity<LancamentoDto> updateFilme(@PathVariable("idLancamento") String id,
                                                     @Valid @RequestBody LancamentoCreateDto lancamentoCreateDto) throws RegraDeNegocioException {
        return ResponseEntity.ok(lancamentosService.updateLancamento(id, lancamentoCreateDto));
    }

    @Operation(summary = "Deletar Lançamento", description = "Deletar um lançamento no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idLancamento}")
    public ResponseEntity<Void> delete(@PathVariable("idLancamento") String id) throws RegraDeNegocioException {
        lancamentosService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar Lançamentos por data", description = "Lista os lançamentos por data no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Lista feita com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/datas-lancamentos")
    public ResponseEntity<List<LancamentoDto>> findDataLancamento(String data) {
        return new ResponseEntity<>(lancamentosService.findDataLancamento(data), HttpStatus.OK);
    }

    @Operation(summary = "Listar Lançamentos por classificação", description = "Lista os lançamentos por data no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Lista feita com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/classificacao-lancamentos")
    public ResponseEntity<List<LancamentoDto>> findAllByClassificacao(Integer classificacao) {
        return new ResponseEntity<>(lancamentosService.findAllByClassificacao(classificacao), HttpStatus.OK);
    }
}
