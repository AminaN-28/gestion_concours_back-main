package jukki.gestion_concours_2.model.Utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SendMessageService {

    @Autowired
    public JavaMailSender javaMailSender;

    public static final String ACCOUNT_SID = "AC98fcb6d37cbfe53223d8eb77ae1e110f";
    public static final String AUTH_TOKEN = "96c899e597f8d48fb7ea14ec9d8c6374";

    public Integer generateOTP()
    {
        return 100000 + new Random().nextInt(900000);
    }

    public void sendEmail(String to, String body, String topic)
    {
        System.out.println("sending email");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("kingteamnosql@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
        System.out.println("sent email");
    }

    public void sendSms(String to, String body) {
        System.out.println("Sending sms");

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(new PhoneNumber(to),
                new PhoneNumber("+13254236632"),
                body).create();

        System.out.println("OK : sms send, status : "+message.getStatus());
    }

}
