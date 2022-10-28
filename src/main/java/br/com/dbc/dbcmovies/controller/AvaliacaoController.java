package br.com.dbc.dbcmovies.controller;


import br.com.dbc.dbcmovies.Dto.AvaliacaoCreateDto;
import br.com.dbc.dbcmovies.Dto.AvaliacaoDto;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.AvaliacaoService;
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
@Validated
@RequiredArgsConstructor
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;


    @Operation(summary = "Criar uma Avaliação", description = "Cria uma avaliação em algum filme/série no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Avaliação Criada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idUsuario}/{idItem}")
    public ResponseEntity<AvaliacaoDto> create(@Valid @RequestBody AvaliacaoCreateDto avaliacao,
                                               @PathVariable("idUsuario") Integer idUsuario,
                                               @PathVariable("idItem") Integer idItem) throws RegraDeNegocioException {

        return ResponseEntity.ok(avaliacaoService.create(avaliacao, idUsuario, idItem));
    }

    @Operation(summary = "Listar Avaliações", description = "Lista todas as avaliações do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de avaliações"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<AvaliacaoDto>> listAll() throws RegraDeNegocioException {
        return ResponseEntity.ok(avaliacaoService.list());
    }

    @Operation(summary = "Pega uma avaliação pelo id", description = "Resgata uma avaliação pelo id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/ids")
    public ResponseEntity<AvaliacaoDto> getByIds(@RequestParam("idUsuario") Integer idUsuario,
                                                 @RequestParam("idItem") Integer idItem) throws RegraDeNegocioException {

        return ResponseEntity.ok(avaliacaoService.getAvaliacao(idUsuario, idItem));
    }

    @Operation(summary = "Listar Avaliações de um usuário", description = "Lista todas as avaliações de um específico usuário do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de avaliações desse usuário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idUsuario}/user")
    public ResponseEntity<List<AvaliacaoDto>> listByUser(@PathVariable("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        return ResponseEntity.ok(avaliacaoService.listByUsers(idUsuario));
    }

    @Operation(summary = "Atualizar Avaliação", description = "Atualiza a avaliação no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizou com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/ids")
    public ResponseEntity<AvaliacaoDto> update(@Valid @RequestBody AvaliacaoCreateDto avaliacaoDto,
                                               @RequestParam("idUsuario") Integer idUsuario,
                                               @RequestParam("idItem") Integer idItem) throws RegraDeNegocioException {

        return ResponseEntity.ok(avaliacaoService.update(avaliacaoDto, idUsuario, idItem));
    }

    @Operation(summary = "Deletar Avaliação", description = "Deletar a avaliação no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/ids")
    public ResponseEntity<Void> delete(@RequestParam("idUsuario") Integer idUsuario,
                                       @RequestParam("idItem") Integer idItem) throws RegraDeNegocioException {
        avaliacaoService.delete(idUsuario, idItem);
        return ResponseEntity.noContent().build();
    }
}
