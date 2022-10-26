package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.Dto.UsuarioCreateDto;
import br.com.dbc.dbcmovies.Dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.Usuario;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDto> pegar(@PathVariable(name = "idUsuario")Integer idUsuario) throws  RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.pegar(idUsuario),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> adicionar(@Valid @RequestBody UsuarioCreateDto usuario) throws RegraDeNegocioException {
        log.info("Adicionando o Usuário...");
        UsuarioDto user = usuarioService.adicionar(usuario);
        log.info("Usuário adicionado com sucesso!");
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDto> editar(@PathVariable(name = "idUsuario")Integer idUsuario,@Valid @RequestBody UsuarioCreateDto usuario) throws RegraDeNegocioException {
        log.info("Editando o Usuário...");
        UsuarioDto user = usuarioService.editar(idUsuario, usuario);
        log.info("Usuário editado com sucesso!");
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> remover(@PathVariable(name = "idUsuario")Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.remover(idUsuario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idUsuario}/admin")
    public ResponseEntity<Void> tornarUsuarioAdmin(@PathVariable(name = "idUsuario")Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.remover(idUsuario);
        log.info("Usuário deletado com sucesso");
        return ResponseEntity.noContent().build();
    }
}
