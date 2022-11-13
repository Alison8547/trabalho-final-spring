package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.dto.ItemEntretenimentoDto;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.AssistidosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/assistidos")
public class AssistidosController {

    private final AssistidosService assistidosService;

    @Operation(summary = "Listar todos os Assistidos", description = "Lista todos os assistidos do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de assistidos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping()
    public ResponseEntity<List<ItemEntretenimentoDto>> listarAssistidos() throws RegraDeNegocioException {
        return new ResponseEntity<>(assistidosService.listarAssistidos(), HttpStatus.OK);
    }

    @Operation(summary = "Deletar Um Assistido", description = "Deletar um assistido no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idItem}")
    public ResponseEntity<Void> deletarAssistido(@PathVariable(name = "idItem") Integer idItem) throws RegraDeNegocioException {
        assistidosService.deletarAssistido(idItem);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Marca um item como Assistido", description = "Cria um item assistido no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Marcado como assistido com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idItem}")
    public ResponseEntity<ItemEntretenimentoDto> marcarAssistido(@PathVariable(name = "idItem") Integer idItem) throws RegraDeNegocioException {
        return new ResponseEntity<>(assistidosService.marcarAssistido(idItem), HttpStatus.OK);
    }
}
