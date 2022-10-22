package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.entity.ItemEntretenimento;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.AssistidosService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/assistidos")
public class AssistidosController {

    private final AssistidosService assistidosService;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<ItemEntretenimento>> listarAssistidos(@PathVariable(name = "idUsuario") Integer idUsuario) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(assistidosService.listarAssistidos(idUsuario), HttpStatus.OK);
    }

    @DeleteMapping("/{idItem}/{idUsuario}")
    public ResponseEntity<Void> deletarAssistido(@PathVariable(name = "idItem") Integer idItem, @PathVariable(name = "idUsuario") Integer idUsuario) throws BancoDeDadosException, RegraDeNegocioException {
        assistidosService.deletarAssistido(idItem, idUsuario);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idItem}/{idUsuario}")
    public ResponseEntity<ItemEntretenimento> marcarAssistido(@PathVariable(name = "idItem") Integer idItem, @PathVariable(name = "idUsuario") Integer idUsuario) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(assistidosService.marcarAssistido(idItem, idUsuario),HttpStatus.OK);
    }
}
