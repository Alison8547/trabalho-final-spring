package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.TipoTemplate;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String from;

    @Mock
    private freemarker.template.Configuration fmConfiguration;


    @Test
    public void deveTestarGeContentFromTemplateUsuario() throws TemplateException, RegraDeNegocioException, IOException {

        // Criar variaveis (SETUP)

        Template template = new Template("", Reader.nullReader());
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setIdUsuario(1);
        usuarioDto.setNome("alison");
        usuarioDto.setEmail("alison@hotmail.com");
        usuarioDto.setIdade(12);
        usuarioDto.setAtivo(1);
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuarioDto.getNome());
        dados.put("id", usuarioDto.getIdUsuario());
        dados.put("idade", usuarioDto.getIdade());
        dados.put("emailUsuario", usuarioDto.getEmail());
        dados.put("email", from);

        when(fmConfiguration.getTemplate(anyString())).thenReturn(template);

        // Ação (ACT)
        String geContentFromTemplateUsuario = emailService.geContentFromTemplateUsuario(usuarioDto, TipoTemplate.CREATE);
        template = fmConfiguration.getTemplate("emailcreate-template.html");
        FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

        // Verificação (ASSERT)
        assertNotNull(geContentFromTemplateUsuario);

    }

    @Test
    public void deveTestarGetContentFromTemplateRecuperacaoSenha() throws TemplateException, RegraDeNegocioException, IOException {

        // Criar variaveis (SETUP)

        Template template = new Template("", Reader.nullReader());
        String template2 = "Recuperacao";
        String tokenRecuperacaoSenha = "asfkfaskk";
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Alison");
        usuario.setEmail("Alison@hotmail.com");
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuario.getNome());
        dados.put("emailUsuario", usuario.getEmail());
        dados.put("token", tokenRecuperacaoSenha);
        dados.put("email", from);

        when(fmConfiguration.getTemplate(anyString())).thenReturn(template);

        // Ação (ACT)
        String contentFromTemplateRecuperacaoSenha = emailService.getContentFromTemplateRecuperacaoSenha(usuario, template2, tokenRecuperacaoSenha);
        FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

        // Verificação (ASSERT)
        assertNotNull(contentFromTemplateRecuperacaoSenha);

    }

}
