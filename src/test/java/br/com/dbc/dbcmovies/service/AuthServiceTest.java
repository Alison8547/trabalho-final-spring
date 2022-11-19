package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.LoginDTO;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.security.TokenService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


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

        // Criar variaveis (SETUP)
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("alison@hotmail.com");
        loginDTO.setSenha("1234");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha());


        // Ação (ACT)
     //   authService.auth();


        // Verificação (ASSERT)
      //  assertNotNull();
    }
}
