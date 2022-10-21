package br.com.dbc.dbcmovies.controller;


import br.com.dbc.dbcmovies.entity.Avaliacao;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.AvaliacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    private AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping("/{idUsuario}/{idItem}")
    public ResponseEntity<Avaliacao> create(@Valid @RequestBody Avaliacao avaliacao,
                                            @PathVariable("idUsuario") Integer idUsuario,
                                            @PathVariable("idItem") Integer idItem) throws BancoDeDadosException, RegraDeNegocioException {

        return ResponseEntity.ok(avaliacaoService.create(avaliacao, idUsuario, idItem));
    }

    @GetMapping
    public ResponseEntity<List<Avaliacao>> listAll() throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(avaliacaoService.list());
    }

    @GetMapping("/ids")
    public ResponseEntity<Avaliacao> getByIds(@RequestParam("idUsuario") Integer idUsuario,
                                              @RequestParam("idItem") Integer idItem) throws BancoDeDadosException, RegraDeNegocioException {

        return ResponseEntity.ok(avaliacaoService.find(idUsuario, idItem));
    }

    @GetMapping("/{idUsuario}/user")
    public ResponseEntity<List<Avaliacao>> listByUser(@PathVariable("idUsuario") Integer idUsuario) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(avaliacaoService.listByUsers(idUsuario));
    }

    @PutMapping("/ids")
    public ResponseEntity<Avaliacao> update(@Valid @RequestBody Avaliacao avaliacao,
                                            @RequestParam("idUsuario") Integer idUsuario,
                                            @RequestParam("idItem") Integer idItem) throws BancoDeDadosException, RegraDeNegocioException {

        return ResponseEntity.ok(avaliacaoService.update(avaliacao, idUsuario, idItem));
    }

    @DeleteMapping("/ids")
    public ResponseEntity<Void> delete(@RequestParam("idUsuario") Integer idUsuario,
                                       @RequestParam("idItem") Integer idItem) throws BancoDeDadosException {
        avaliacaoService.delete(idUsuario, idItem);
        return ResponseEntity.noContent().build();
    }
}
