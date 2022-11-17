package br.com.dbc.dbcmovies.controller;



import br.com.dbc.dbcmovies.dto.LancamentoCreateDto;
import br.com.dbc.dbcmovies.dto.LancamentoDto;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.LancamentosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "Listar Lancamentos", description = "Lista os lançamentos no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Filme Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<LancamentoDto>> listAll() throws RegraDeNegocioException {
        return ResponseEntity.ok(lancamentosService.list());
    }

    @Operation(summary = "Criar Lancamento", description = "Cria um Filme no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Filme Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/filme")
    public ResponseEntity<LancamentoDto> createFilme(@Valid @RequestBody LancamentoCreateDto lancamentoCreateDto) {
        return ResponseEntity.ok(lancamentosService.createLancamento(lancamentoCreateDto));
    }

    @Operation(summary = "Atualizar Lancamento", description = "Atualiza um filme no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizou com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/filme/{idItem}")
    public ResponseEntity<LancamentoDto> updateFilme(@PathVariable("idItem") String id,
                                                             @Valid @RequestBody LancamentoCreateDto lancamentoCreateDto) throws RegraDeNegocioException {
        return ResponseEntity.ok(lancamentosService.updateLancamento(id, lancamentoCreateDto));
    }

    @Operation(summary = "Deletar Lancamento", description = "Deletar um item entretenimento no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idItem}")
    public ResponseEntity<Void> delete(@PathVariable("idItem") String id) throws RegraDeNegocioException {
        lancamentosService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
