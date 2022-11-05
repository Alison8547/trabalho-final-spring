package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.dto.FilmeCreateDto;
import br.com.dbc.dbcmovies.dto.ItemEntretenimentoDto;
import br.com.dbc.dbcmovies.dto.SerieCreateDto;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.ItemService;
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
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;



    @Operation(summary = "Criar Filme", description = "Cria um Filme no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Filme Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/filme/{idAdmin}")
    public ResponseEntity<ItemEntretenimentoDto> createFilme(@Valid @RequestBody FilmeCreateDto filmeCreateDto,@PathVariable("idAdmin") Integer idAdmin) throws RegraDeNegocioException {
        return ResponseEntity.ok(itemService.createFilme(filmeCreateDto,idAdmin));
    }

    @Operation(summary = "Criar Serie", description = "Cria uma Serie no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Filme Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/serie/{idAdmin}")
    public ResponseEntity<ItemEntretenimentoDto> createSerie(@Valid @RequestBody SerieCreateDto serieCreateDto,@PathVariable("idAdmin") Integer idAdmin) throws RegraDeNegocioException {
        return ResponseEntity.ok(itemService.createSerie(serieCreateDto, idAdmin));
    }

    @Operation(summary = "Listar Todos item", description = "Lista todos os Itens de entretenimento do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de itenm entretenimento"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<ItemEntretenimentoDto>> listAll() throws RegraDeNegocioException {
        return ResponseEntity.ok(itemService.list());
    }


    @Operation(summary = "Filtra os item", description = "Filtra todos os Itens de entretenimento do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de itenm entretenimento filtrada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/filtro")
    public ResponseEntity<List<ItemEntretenimentoDto>> filterFilme(@RequestParam("tipo") String tipo,
                                                                   @RequestParam("genero") String genero,
                                                                   @RequestParam("class") Integer classificacao){

        return ResponseEntity.ok(itemService.filter(tipo, genero, classificacao));
    }


    @Operation(summary = "Pega um item", description = "Pega um Item do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de itenm entretenimento filtrada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idItem}")
    public ResponseEntity<ItemEntretenimentoDto> getById(@PathVariable("idItem") Integer id) throws RegraDeNegocioException {
        return ResponseEntity.ok(itemService.getItem(id));
    }


    @Operation(summary = "Atualizar filme", description = "Atualiza um filme no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizou com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/filme/{idItem}/{idAdmin}")
    public ResponseEntity<ItemEntretenimentoDto> updateFilme(@PathVariable("idItem") Integer id,
                                                     @Valid @RequestBody FilmeCreateDto filmeCreateDto, @PathVariable("idAdmin") Integer idAdmin) throws RegraDeNegocioException {

        return ResponseEntity.ok(itemService.updateFilme(id, filmeCreateDto, idAdmin));
    }

    @Operation(summary = "Atualizar serie", description = "Atualiza uma serie no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizou com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/serie/{idItem}/{idAdmin}")
    public ResponseEntity<ItemEntretenimentoDto> updateSerie(@PathVariable("idItem") Integer id,
                                                             @Valid @RequestBody SerieCreateDto serieCreateDto, @PathVariable("idAdmin") Integer idAdmin) throws RegraDeNegocioException {

        return ResponseEntity.ok(itemService.updateSerie(id, serieCreateDto, idAdmin));
    }


    @Operation(summary = "Deletar item", description = "Deletar um item entretenimento no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idItem}/{idAdmin}")
    public ResponseEntity<Void> delete(@PathVariable("idItem") Integer id, @PathVariable("idAdmin") Integer idAdmin) throws RegraDeNegocioException {
        itemService.delete(id, idAdmin);
        return ResponseEntity.noContent().build();
    }
}
