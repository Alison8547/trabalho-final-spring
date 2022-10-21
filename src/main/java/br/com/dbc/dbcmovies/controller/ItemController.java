package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.entity.Filtro;
import br.com.dbc.dbcmovies.entity.ItemEntretenimento;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/item")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
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

    @GetMapping("/{id}")
    public ResponseEntity<ItemEntretenimento> getById(@PathVariable Integer id) throws RegraDeNegocioException {
        return ResponseEntity.ok(itemService.findById(id));
    }
}
