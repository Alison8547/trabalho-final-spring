package br.com.dbc.dbcmovies.controller;


import br.com.dbc.dbcmovies.dto.LoginDTO;
import br.com.dbc.dbcmovies.dto.UsuarioCreateDto;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.security.TokenService;
import br.com.dbc.dbcmovies.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;

    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha());

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Object principal = authenticate.getPrincipal();

        UsuarioEntity usuarioEntity = (UsuarioEntity) principal;

        return tokenService.getToken(usuarioEntity);


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
    public ResponseEntity<UsuarioDto> cadastrar(@Valid @RequestBody UsuarioCreateDto usuario){
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
    @GetMapping("/pegar-user-logado")
    public ResponseEntity<UsuarioDto> pegarUserLogado() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUser(),HttpStatus.OK);
    }

    @Operation(summary = "Desativar conta", description = "Desativar sua conta do aplicativo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Conta desativada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/desativar-conta/{idUsuario}")
    public ResponseEntity<UsuarioDto> desativar(@PathVariable(name = "idUsuario")Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.desativarConta(idUsuario),HttpStatus.OK);

    }
}
