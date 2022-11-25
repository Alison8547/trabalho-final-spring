package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.dto.FilmeDisponivelDto;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.ItemService;
import br.com.dbc.dbcmovies.service.LocadoraService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/locacao")
public class LocadoraController {

    private final LocadoraService locadoraService;

    private final ItemService itemService;

    @Operation(summary = "Buscar filmes disponiveis para locação", description = "Busca pelos filmes que estão disponiveis para locação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/filmes-disponiveis")
    public List<FilmeDisponivelDto> findByDisponibilidade() {
        return itemService.findByDisponibilidade();
    }

    @Operation(summary = "Locar um filme", description = "Chama o serviço de locação de filmes através de um evento enviado para o kafka")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Evento enviado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/filme")
    public ResponseEntity<Void> locarFilme(@RequestParam Integer idFilme, @RequestParam Integer qtdDiasLocacao) throws RegraDeNegocioException, JsonProcessingException {
        locadoraService.locarFilme(idFilme, qtdDiasLocacao);
        return ResponseEntity.ok().build();
    }
}
