/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.anna.MainApp;
import com.anna.gui.controllers.DataLoader;
import com.anna.gui.controllers.EmailController;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author igor
 */
public class NewEmptyJUnitTest {
    
    public NewEmptyJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("BEFORE CLASS");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("AFTER CLASS");
    }
    
    @Before
    public void setUp() {
        System.out.println("BEFORE");
    }
    
    @After
    public void tearDown() {
        System.out.println("AFTER");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void sendMessage() 
    {
                //set default email connection settings
        String fromAddress = "multiplexor88@ukr.net";
        String psw = "plya1955";
        String smtp = "smtp.ukr.net";
        String port = "465";
        String ssl = "true";
        
        try {
            //validate data
            String toAddress = "igor.dumchykov@yahoo.com";
            
            //Send message
            Properties props = new Properties();
            
            //igor.dumchykov@yahoo.com
            //multiplexor88@ukr.net     
            //anna_soft_mail_robot    //mypassword26112015
            
            props.put("mail.smtp.host", smtp);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.starttsl.enable", ssl);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.port", port);
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

            SecurityManager security = System.getSecurityManager();

            Authenticator auth = new SMTPAuthenticator("multiplexor88@ukr.net", "plya1955");

            Session session = Session.getDefaultInstance(props, auth);

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromAddress));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));

            String subject = "subject";
            message.setSubject(subject);

            String mes = "text";
            message.setText(mes);

            Transport.send(message);
            
        } catch (AddressException ex) {
            Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
class SMTPAuthenticator extends javax.mail.Authenticator
{
    private String email, password;
    
    public SMTPAuthenticator(String email, String password)
    {
         this.email = email;
        this.password = password;
    }
    
    @Override
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(email, password);
    }
}