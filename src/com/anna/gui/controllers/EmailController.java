/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.data.Person;
import com.anna.gui.interfaces.AbstractController;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.mail.Message;
import javax.mail.MessagingException;
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
                                fromAddressTxt,
                                subjectTxt;
    
    @FXML
    private TextArea            messageTxt;

    @FXML
    private void onSend(ActionEvent event)
    {
        try {
            //validate data
            /*validate data*/
            Alert alert = new Alert(Alert.AlertType.ERROR, DataLoader.getLangResources().getString("key.err.mail"));
            String toAddress = toAddressTxt.getText();
            if(toAddress != null && !toAddress.isEmpty() && !toAddress.matches(".*@+.*"))
            {
                alert.showAndWait();
                return;
            }
            
            String fromAddress = fromAddressTxt.getText();
            if(fromAddress != null && !fromAddress.isEmpty() && !fromAddress.matches(".*@+.*"))
            {
                alert.showAndWait();
                return;
            }
            
            String subject = subjectTxt.getText();
            String mes = messageTxt.getText();
            
            //Send message
            Properties properties = new Properties();
            
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.host", "smtp.ukr.net");
            properties.put("mail.smtp.port", "2525");
            
            Session session = Session.getDefaultInstance(properties);
            
            Message message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress("multiplexor88@ukr.net"));
            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("multiplexor88@ukr.net"));
            
            if(subject != null)
                message.setSubject("subject");
            
            message.setText("text");
            
            Transport.send(message, "multiplexor88", "plya1955");
            
        } catch (AddressException ex) {
            Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
            
    //sets person email address if it exists
    @Override
    public void setData(Object data) 
    {
        if(data == null)
            return;
        
        Person p = (Person) data;
    }

    @Override
    public void clearContext() 
    {
        messageTxt.clear();
        toAddressTxt.clear();
        subjectTxt.clear();
    }
}
