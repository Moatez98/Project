package tn.moatez.project.services.impl;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tn.moatez.project.dto.UserDTO;
import tn.moatez.project.payload.request.EmailDetail;
import tn.moatez.project.services.EmailService;
import tn.moatez.project.services.UserService;

@Service
public class EmailServiceImpl implements EmailService {
    private JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UserService userService;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.userService = userService;
    }

    @Override
    public Boolean sendSimpleMail(EmailDetail details) {
        Context context = new Context();
        UserDTO u = userService.getUserByEmail(details.getRecipient());
        context.setVariable("user", u);
        try {
            String process = templateEngine.process("email/welcome", context);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(new InternetAddress("no_reply@petsygabes.com", "NoReply-JD"));

            helper.setReplyTo("noreply@petsygabes.com");
            helper.setSubject("Welcome " );
            helper.setText(process, true);
            helper.setTo(details.getRecipient());
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            return false;
        }
        return null;
    }
}
