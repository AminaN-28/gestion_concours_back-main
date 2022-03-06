package jukki.gestion_concours_2.controller;


import com.couchbase.client.java.query.QueryResult;
import jukki.gestion_concours_2.model.*;
import jukki.gestion_concours_2.model.Utils.ScopeManager;
import jukki.gestion_concours_2.model.Utils.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {

    @Autowired
    public SendMessageService sendMessageService;

    ScopeManager scopeManager = new ScopeManager();

    @PostMapping(value = "/voter/sendOtpCode/{concours}/{edition}")
    public Votant sendMail(@RequestBody Message message, @PathVariable String concours, @PathVariable String edition) {
        // TODO: IMPROVE THIS, add the Edition name
        scopeManager.createCollection(concours,"votants");
        QueryResult result = scopeManager.couchbaseFindQuery("SELECT * FROM concoursBucket." + concours + ".votants USE KEYS '" + message.getTo() + "'");

        if (result.rowsAsObject().isEmpty()) {
            int code = sendMessageService.generateOTP();
            try {
                sendMessageService.sendEmail(message.getTo(), message.getBody() +" "+code, "Code de validation");
                System.out.println("MESSAGE ENVOYÉ");
                return new Votant(message.getTo(), code);
            } catch (Exception e) {
                System.out.println("MESSAGE NON ENVOYÉ");
                return null;
            }
        } else {
            System.out.println("VOUS N'AVEZ DROIT QU'À UNE SEULE VOTE.");
            return  null;
        }
    }

    @PostMapping(value = "/voter/sendSmsCode/{concours}/{edition}")
    public Votant sendSms(@RequestBody Message message, @PathVariable String concours, @PathVariable String edition) {
        scopeManager.createCollection(concours,"votants");
        QueryResult result = scopeManager.couchbaseFindQuery("SELECT * FROM concoursBucket." + concours + ".votants USE KEYS '" + message.getTo() + "'");
        if (result.rowsAsObject().isEmpty()) {
            int code = sendMessageService.generateOTP();
            try {
                sendMessageService.sendSms(message.getTo(), message.getBody() +" "+code);
                System.out.println("MESSAGE ENVOYÉ");
                return new Votant(message.getTo(), code);
            } catch (Exception e) {
                System.out.println("MESSAGE NON ENVOYÉ");
                return null;
            }
        } else {
            System.out.println("VOUS N'AVEZ DROIT QU'À UNE SEULE VOTE.");
            return  null;
        }
    }

}
