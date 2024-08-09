package school.sptech.apimiseenplace.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendTextEmail(String to, String subject, String text) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("api.miseenplace@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

}
