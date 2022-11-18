package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.dto.LocadoraCreateDto;
import br.com.dbc.dbcmovies.dto.LocadoraDto;
import br.com.dbc.dbcmovies.service.LocadoraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/locadora")
public class LocadoraController {

    private final LocadoraService locadoraService;

    @Operation(summary = "Alugar Item", description = "Alugar um item no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Alugado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<LocadoraDto> create(@Valid @RequestBody LocadoraCreateDto locadoraCreateDto) {
        return ResponseEntity.ok(locadoraService.createLancamento(locadoraCreateDto));
    }
}
