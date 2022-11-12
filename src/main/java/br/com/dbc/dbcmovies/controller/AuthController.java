package br.com.dbc.dbcmovies.controller;


import br.com.dbc.dbcmovies.dto.LoginDTO;
import br.com.dbc.dbcmovies.dto.RecuperarSenhaDto;
import br.com.dbc.dbcmovies.dto.UsuarioCreateDto;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.AuthService;
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

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @Operation(summary = "Autentificar Usuário", description = "Autentificar usuário no aplicativo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) {
        return authService.auth(loginDTO);
    }

    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/cadastro-usuario")
    public ResponseEntity<UsuarioDto> cadastrar(@Valid @RequestBody UsuarioCreateDto usuario) {
        log.info("Cadastrando novo usuário...");
        UsuarioDto user = usuarioService.cadastrar(usuario);
        log.info("Usuário cadastrado com sucesso!");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Pegar conta logada", description = "Pegar sua conta logado no aplicativo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Usuário pego com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario-logado")
    public ResponseEntity<UsuarioDto> pegarUserLogado() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUser(), HttpStatus.OK);
    }

    @Operation(summary = "Desativar conta", description = "Desativar sua conta do aplicativo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Conta desativada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/desativacao-conta/{idUsuario}")
    public ResponseEntity<UsuarioDto> desativar(@PathVariable(name = "idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.desativarConta(idUsuario), HttpStatus.OK);

    }

    @Operation(summary = "Recuperar senha", description = "Envia e-mail com token para recuperação de senha")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "E-mail de recuperação de senha enviado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/recuperacao-senha")
    public ResponseEntity<Void> recuperarSenha(@Valid @RequestBody RecuperarSenhaDto email) throws RegraDeNegocioException {
        log.info("Verificando e-mail...");
        usuarioService.recuperarSenha(email.getEmail());
        log.info("E-mail verificado! Foi enviado um token de recuperação de senha para o e-mail informado.");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Alterar senha", description = "Altera a senha da sua conta")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Alterou com sucesso sua senha"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/alteracao-senha")
    public ResponseEntity<Void> alterarSenha(String senha) throws RegraDeNegocioException {
        log.info("Alterando a senha...");
        usuarioService.alterarSenha(senha);
        log.info("Senha alterada com sucesso!");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Tornar conta admin", description = "Altera conta para admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Alterou com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/tornar-conta-admin/{idUsuario}")
    public ResponseEntity<UsuarioDto> alterarSenha(@PathVariable(name = "idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.tornarContaAdmin(idUsuario), HttpStatus.OK);
    }
}
