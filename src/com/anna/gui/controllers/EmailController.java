/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.data.Person;
import com.anna.gui.interfaces.AbstractController;
import com.sun.mail.smtp.SMTPSaslAuthenticator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * IS not finished
 * @author igor
 */
public class EmailController extends AbstractController
{
    @FXML
    private TextField           toAddressTxt,
                                subjectTxt;
    
    @FXML
    private TextArea            messageTxt;

    @FXML
    private void onSend(ActionEvent event)
    {
        //set default email connection settings
        PropertiesController propertiesController = (PropertiesController) ControllerFactory.getInstance().create(ControllerFactory.ControllerType.PROPERTIES);
        String fromAddress = propertiesController.getFromAddressTxt().getText();
        String psw = propertiesController.getPasswordTxt().getText();
        String smtp = propertiesController.getSmtpTxt().getText();
        String port = propertiesController.getPortTxt().getText();
        String ssl = propertiesController.getSslCheckBox().getText().toLowerCase();
        
        try {
            //validate data
            Alert alert = new Alert(Alert.AlertType.ERROR, DataLoader.getLangResources().getString("key.err.mail"));
            String toAddress = toAddressTxt.getText();
            if(toAddress != null && !toAddress.isEmpty() && !toAddress.matches(".*@+.*"))
            {
                alert.showAndWait();
                return;
            }
            
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

            Authenticator auth = new SMTPAuthenticator(fromAddress, psw);

            Session session = Session.getDefaultInstance(props, auth);

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromAddress));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));

            String subject = subjectTxt.getText();
            if(subject != null)
                message.setSubject(subject);

            String mes = messageTxt.getText();
            message.setText(mes);

            Transport.send(message);

            Alert emailSuccessAlert = new Alert(Alert.AlertType.INFORMATION, DataLoader.getLangResources().getString("key.info.email-success"));
            emailSuccessAlert.showAndWait();
            
        } catch (AddressException ex) {
            Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Alert emailErr = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            emailErr.showAndWait();
        }
    }
            
    //sets person email address if it exists
    @Override
    public void setData(Object data) 
    {
        clearContext();
        
        if(data == null)
            return;
     
        Person p = (Person) data;
        String toAddress = p.getContact().getEmail();
        
        if(toAddress != null && !toAddress.isEmpty())
            toAddressTxt.setText(toAddress);
        
    }

    @Override
    public void clearContext() 
    {
        messageTxt.clear();
        toAddressTxt.clear();
        subjectTxt.clear();
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