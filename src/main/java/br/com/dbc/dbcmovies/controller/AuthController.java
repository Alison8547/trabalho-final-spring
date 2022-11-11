package br.com.dbc.dbcmovies.controller;


import br.com.dbc.dbcmovies.dto.LoginDTO;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.security.TokenService;
import br.com.dbc.dbcmovies.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;

    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getSenha());

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Object principal = authenticate.getPrincipal();

        UsuarioEntity usuarioEntity = (UsuarioEntity) principal;

        return tokenService.getToken(usuarioEntity);


    }

//    @PostMapping("/create")
//    public ResponseEntity<UsuarioDto> create(@RequestBody @Valid LoginDTO usuario) {
//        return new ResponseEntity<>(usuarioService.create(usuario), HttpStatus.OK);
//    }

    @GetMapping("/pegar-user-logado")
    public ResponseEntity<UsuarioDto> pegarUserLogado() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUser(),HttpStatus.OK);
    }
}
