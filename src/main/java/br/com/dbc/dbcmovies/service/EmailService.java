package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.TipoTemplate;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;


    public void sendEmailUsuario(UsuarioDto usuarioDto, TipoTemplate tipoTemplate) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuarioDto.getEmail());
            mimeMessageHelper.setSubject("subject");
            mimeMessageHelper.setText(geContentFromTemplateUsuario(usuarioDto, tipoTemplate), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException | RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }


    public String geContentFromTemplateUsuario(UsuarioDto usuarioDto, TipoTemplate tipoTemplate) throws IOException, TemplateException, RegraDeNegocioException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuarioDto.getNome());
        dados.put("id", usuarioDto.getIdUsuario());
        dados.put("idade", usuarioDto.getIdade());
        dados.put("emailUsuario", usuarioDto.getEmail());
        dados.put("email", from);
        Template template = null;

        switch (tipoTemplate) {
            case CREATE -> {
                template = fmConfiguration.getTemplate("emailcreate-template.html");
            }
            case UPDATE -> {
                template = fmConfiguration.getTemplate("emailupdate-template.html");
            }
            case DELETE -> {
                template = fmConfiguration.getTemplate("emaildelete-template.html");
            }
            default -> {
                throw new RegraDeNegocioException("Tipo de template não encontrado!");
            }
        }
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }


    public void sendEmailRecuperacaoSenha(UsuarioEntity usuario,
                                          String template,
                                          String tokenRecuperacaoSenha) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuario.getEmail());
            mimeMessageHelper.setSubject("Recuperação de senha");
            mimeMessageHelper.setText(getContentFromTemplateRecuperacaoSenha(usuario, template, tokenRecuperacaoSenha), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException | RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    public String getContentFromTemplateRecuperacaoSenha(UsuarioEntity usuario,
                                                         String template,
                                                         String tokenRecuperacaoSenha) throws IOException, TemplateException, RegraDeNegocioException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuario.getNome());
        dados.put("emailUsuario", usuario.getEmail());
        dados.put("token", tokenRecuperacaoSenha);
        dados.put("email", from);
        Template templateEmail = fmConfiguration.getTemplate(template);

        return FreeMarkerTemplateUtils.processTemplateIntoString(templateEmail, dados);
    }
}
