package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.LoginDTO;
import br.com.dbc.dbcmovies.security.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void deveTestarLoginComSucesso() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setSenha("123");
        loginDTO.setSenha("alison@hotmail.com");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha());


        when(authenticationManager.authenticate(any())).thenReturn(usernamePasswordAuthenticationToken);

        authService.auth(loginDTO);

    }
}
