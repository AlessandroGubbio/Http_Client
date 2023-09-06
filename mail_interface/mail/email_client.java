package mail_interface.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class email_client {
    private Properties pSMTPServerProperties;
    private Authenticator auth;

    public void InitSMTPServer(String sHostName, String sPort, int iProtocol, String sUser, String sPwd)
    {
        pSMTPServerProperties = new Properties();
        if(iProtocol>0)
        {
            pSMTPServerProperties.put("mail.smtp.auth", "true");
            if(iProtocol==2)
                pSMTPServerProperties.put("mail.smtp.starttls.enable", "true");
            else
                pSMTPServerProperties.put("mail.smtp.ssl.enable", "true");
        }
        pSMTPServerProperties.put("mail.smtp.host", sHostName);
        pSMTPServerProperties.put("mail.smtp.port", sPort);

        auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sUser, sPwd);
            }
        };
    }
    public boolean SendSmtpMail(String sFrom, String sTo, String sSubject, String sMessage)
    {

        //configure Mailtrap's SMTP server details

        Session session = Session.getInstance(pSMTPServerProperties, auth);

        try {
            //create a MimeMessage object
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sFrom));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(sTo));
            message.setSubject(sSubject);
            message.setText(sMessage);
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
            return true;
        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            return false;
        }
    }

    public static void main(String[] args) {
        email_client email_client = new email_client();
        email_client.InitSMTPServer("smtp.libero.it", "465", 1,
                "secchio_gubbio@libero.it", "-Alessandro98-_");

        email_client.SendSmtpMail("secchio_gubbio@libero.it", "secchio_gubbio@libero.it", "ciccia",
                "bello de zio");
    }
}
