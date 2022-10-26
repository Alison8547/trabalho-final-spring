package br.com.dbc.dbcmovies.controller;


import br.com.dbc.dbcmovies.Dto.AvaliacaoCreateDto;
import br.com.dbc.dbcmovies.Dto.AvaliacaoDto;
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
    public ResponseEntity<AvaliacaoDto> create(@Valid @RequestBody AvaliacaoCreateDto avaliacao,
                                               @PathVariable("idUsuario") Integer idUsuario,
                                               @PathVariable("idItem") Integer idItem) throws RegraDeNegocioException {

        return ResponseEntity.ok(avaliacaoService.create(avaliacao, idUsuario, idItem));
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoDto>> listAll() throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(avaliacaoService.list());
    }

    @GetMapping("/ids")
    public ResponseEntity<AvaliacaoDto> getByIds(@RequestParam("idUsuario") Integer idUsuario,
                                                 @RequestParam("idItem") Integer idItem) throws RegraDeNegocioException {

        return ResponseEntity.ok(avaliacaoService.getAvaliacao(idUsuario, idItem));
    }

    @GetMapping("/{idUsuario}/user")
    public ResponseEntity<List<AvaliacaoDto>> listByUser(@PathVariable("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        return ResponseEntity.ok(avaliacaoService.listByUsers(idUsuario));
    }

    @PutMapping("/ids")
    public ResponseEntity<AvaliacaoDto> update(@Valid @RequestBody AvaliacaoCreateDto avaliacaoDto,
                                               @RequestParam("idUsuario") Integer idUsuario,
                                               @RequestParam("idItem") Integer idItem) throws RegraDeNegocioException {

        return ResponseEntity.ok(avaliacaoService.update(avaliacaoDto, idUsuario, idItem));
    }

    @DeleteMapping("/ids")
    public ResponseEntity<Void> delete(@RequestParam("idUsuario") Integer idUsuario,
                                       @RequestParam("idItem") Integer idItem) throws RegraDeNegocioException {
        avaliacaoService.delete(idUsuario, idItem);
        return ResponseEntity.noContent().build();
    }
}
