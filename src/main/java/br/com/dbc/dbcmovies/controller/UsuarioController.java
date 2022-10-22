package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.entity.Usuario;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() throws BancoDeDadosException {
        return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> pegar(@PathVariable(name = "idUsuario")Integer idUsuario) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.pegar(idUsuario),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Usuario> adicionar(@Valid @RequestBody Usuario usuario) throws BancoDeDadosException {
        return new ResponseEntity<>(usuarioService.adicionar(usuario),HttpStatus.CREATED);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Usuario> editar(@PathVariable(name = "idUsuario")Integer idUsuario,@Valid @RequestBody Usuario usuario) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.editar(idUsuario, usuario),HttpStatus.OK);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> remover(@PathVariable(name = "idUsuario")Integer idUsuario) throws BancoDeDadosException, RegraDeNegocioException {
        usuarioService.remover(idUsuario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idUsuario}/admin")
    public ResponseEntity<Usuario> tornarUsuarioAdmin(@PathVariable(name = "idUsuario")Integer idUsuario) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.tornarUsuarioAdmin(idUsuario),HttpStatus.OK);
    }
}
