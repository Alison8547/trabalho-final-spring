package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.entity.Filtro;
import br.com.dbc.dbcmovies.entity.ItemEntretenimento;
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

    @PostMapping
    public ResponseEntity<ItemEntretenimento> create(@Valid @RequestBody ItemEntretenimento itemEntretenimento) throws BancoDeDadosException {
        return ResponseEntity.ok(itemService.create(itemEntretenimento));
    }

    @GetMapping
    public ResponseEntity<List<ItemEntretenimento>> list() throws BancoDeDadosException {
        return ResponseEntity.ok(itemService.list());
    }

    @GetMapping("/byfiltro")
    public ResponseEntity<List<ItemEntretenimento>> filter(@RequestParam("tipo") String tipo,
                                                           @RequestParam("genero") String genero,
                                                           @RequestParam("class") Integer classificacao) throws BancoDeDadosException {

        return ResponseEntity.ok(itemService.filter(new Filtro(tipo, genero, classificacao)));
    }

    @GetMapping("/{idItem}")
    public ResponseEntity<ItemEntretenimento> getById(@PathVariable("idItem") Integer id) throws RegraDeNegocioException {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @PutMapping("/{idItem}")
    public ResponseEntity<ItemEntretenimento> update(@PathVariable("idItem") Integer id,
                                                     @Valid @RequestBody ItemEntretenimento itemEntretenimento) throws BancoDeDadosException, RegraDeNegocioException {

        return ResponseEntity.ok(itemService.update(id, itemEntretenimento));
    }

    @DeleteMapping("/{idItem}")
    public ResponseEntity<Void> delete(@PathVariable("idItem") Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
