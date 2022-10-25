package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.Dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.TipoTemplate;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private static final String TO = "alison.ailson@dbccompany.com.br";

    private final JavaMailSender emailSender;


    public void sendSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(TO);
        message.setSubject("Assunto");
        message.setText("Teste \n minha mensagem \n\nAtt,\nSistema.");
        emailSender.send(message);
    }

    public void sendWithAttachment() throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,
                true);

        helper.setFrom(from);
        helper.setTo(TO);
        helper.setSubject("Subject");
        helper.setText("Teste\n minha mensagem \n\nAtt,\nSistema.");

        File file1 = new File("imagem.jpg");
        FileSystemResource file
                = new FileSystemResource(file1);
        helper.addAttachment(file1.getName(), file);

        emailSender.send(message);
    }

    public void sendEmail(UsuarioDto usuarioDto, TipoTemplate tipoTemplate) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(TO);
            mimeMessageHelper.setSubject("subject");
            mimeMessageHelper.setText(geContentFromTemplate(usuarioDto, tipoTemplate), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException | RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }



    public String geContentFromTemplate(UsuarioDto usuarioDto, TipoTemplate tipoTemplate) throws IOException, TemplateException, RegraDeNegocioException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuarioDto.getNome());
        dados.put("id", usuarioDto.getId());
        dados.put("tipoUsuario",usuarioDto.getTipoUsuario());
        dados.put("idade",usuarioDto.getIdade());
        dados.put("emailUsuario",usuarioDto.getEmail());
        dados.put("senha",usuarioDto.getSenha());
        dados.put("email", from);
        Template template = null;

        switch (tipoTemplate) {
            case CREATE -> {
                template = fmConfiguration.getTemplate("emailcreate-template.ftl");
            }
            case UPDATE -> {
                template = fmConfiguration.getTemplate("emailupdate-template.ftl");
            }
            case DELETE -> {
                template = fmConfiguration.getTemplate("emaildelete-template.ftl");
            }
            default -> {
                throw new RegraDeNegocioException("Tipo de template não encontrado!");
            }
        }
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }



}
