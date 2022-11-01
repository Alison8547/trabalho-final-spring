package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.dto.UsuarioCreateDto;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @Operation(summary = "Listar Usuários", description = "Lista todos os usuários do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de usuários"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
    }

    @Operation(summary = "Pega o usuário pelo id dele", description = "Resgata o usuário do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDto> pegar(@PathVariable(name = "idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.pegar(idUsuario), HttpStatus.OK);
    }

    @Operation(summary = "Criar usuário", description = "Cria um usuário no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Usuário Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<UsuarioDto> adicionar(@Valid @RequestBody UsuarioCreateDto usuario) throws RegraDeNegocioException {
        log.info("Adicionando o Usuário...");
        UsuarioDto user = usuarioService.adicionar(usuario);
        log.info("Usuário adicionado com sucesso!");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar Usuário", description = "Atualiza o usuário no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizou com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDto> editar(@PathVariable(name = "idUsuario") Integer idUsuario, @Valid @RequestBody UsuarioCreateDto usuario) throws RegraDeNegocioException {
        log.info("Editando o Usuário...");
        UsuarioDto user = usuarioService.editar(idUsuario, usuario);
        log.info("Usuário editado com sucesso!");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Deletar Usuário", description = "Deletar o usuário no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> remover(@PathVariable(name = "idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.remover(idUsuario);
        log.info("Usuário deletado com sucesso");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar o tipo do Usuário", description = "Atualiza o tipo do usuário no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizou com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idUsuario}/admin")
    public ResponseEntity<UsuarioDto> tornarUsuarioAdmin(@PathVariable(name = "idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.tornarUsuarioAdmin(idUsuario);
        return new ResponseEntity<>(usuarioService.tornarUsuarioAdmin(idUsuario),HttpStatus.OK);
    }
}
