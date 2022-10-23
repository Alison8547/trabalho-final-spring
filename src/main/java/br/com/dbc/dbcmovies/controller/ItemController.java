package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.Dto.FilmeCreateDto;
import br.com.dbc.dbcmovies.Dto.ItemEntretenimentoDto;
import br.com.dbc.dbcmovies.Dto.SerieCreateDto;
import br.com.dbc.dbcmovies.entity.Filtro;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/item")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/filme")
    public ResponseEntity<ItemEntretenimentoDto> createFilme(@Valid @RequestBody FilmeCreateDto filmeCreateDto) throws BancoDeDadosException {
        return ResponseEntity.ok(itemService.createFilme(filmeCreateDto));
    }

    @PostMapping("/serie")
    public ResponseEntity<ItemEntretenimentoDto> createSerie(@Valid @RequestBody SerieCreateDto serieCreateDto) throws BancoDeDadosException {
        return ResponseEntity.ok(itemService.createSerie(serieCreateDto));
    }

    @GetMapping
    public ResponseEntity<List<ItemEntretenimentoDto>> listAll() throws BancoDeDadosException {
        return ResponseEntity.ok(itemService.list());
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<ItemEntretenimentoDto>> filterFilme(@RequestParam("tipo") String tipo,
                                                                   @RequestParam("genero") String genero,
                                                                   @RequestParam("class") Integer classificacao) throws BancoDeDadosException {

        return ResponseEntity.ok(itemService.filter(new Filtro(tipo, genero, classificacao)));
    }

    @GetMapping("/{idItem}")
    public ResponseEntity<ItemEntretenimentoDto> getById(@PathVariable("idItem") Integer id) throws RegraDeNegocioException {
        return ResponseEntity.ok(itemService.getItem(id));
    }

    @PutMapping("/filme/{idItem}")
    public ResponseEntity<ItemEntretenimentoDto> updateFilme(@PathVariable("idItem") Integer id,
                                                     @Valid @RequestBody FilmeCreateDto filmeCreateDto) throws BancoDeDadosException, RegraDeNegocioException {

        return ResponseEntity.ok(itemService.updateFilme(id, filmeCreateDto));
    }

    @PutMapping("/serie/{idItem}")
    public ResponseEntity<ItemEntretenimentoDto> updateSerie(@PathVariable("idItem") Integer id,
                                                             @Valid @RequestBody SerieCreateDto serieCreateDto) throws BancoDeDadosException, RegraDeNegocioException {

        return ResponseEntity.ok(itemService.updateSerie(id, serieCreateDto));
    }

    @DeleteMapping("/{idItem}")
    public ResponseEntity<Void> delete(@PathVariable("idItem") Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
