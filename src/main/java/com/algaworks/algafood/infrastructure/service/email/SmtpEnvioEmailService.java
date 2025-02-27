package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import freemarker.template.Configuration;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var corpo = processarTemplate(mensagem);

            var helper = new MimeMessageHelper(mimeMessage, Constants.ENCODE);
            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(corpo, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailExceptionException("Não foi possível enviar e-mail", e);
        }
    }

    private String processarTemplate(Mensagem mensagem) {
        try {
            var template = freemarkerConfig.getTemplate(mensagem.getCorpo());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getChaves());
        } catch (Exception e) {
            throw new EmailExceptionException("Não foi possível montar template do e-mail.", e);
        }
    }

}
