import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * Goal which touches a timestamp file.
 */
@Mojo( name = "mail", defaultPhase = LifecyclePhase.INSTALL )
public class JarToMail extends AbstractMojo {
    /**
     * Параметры для отправки email - куда, от кого, хост, user, пароль
     */
    @Parameter( defaultValue = "emailTo@yandex.ru", property = "emailTo", required = true, readonly = true)
    private String emailTo;
    @Parameter( defaultValue = "emailFrom@yandex.ru", property = "emailFrom", required = true, readonly = true)
    private String emailFrom;
    @Parameter( defaultValue = "smtp.yandex.ru", property = "authServ", required = true, readonly = true)
    private String authServ;
    @Parameter( defaultValue = "User", property = "authUser", required = true, readonly = true)
    private String authUser;
    @Parameter( defaultValue = "12345", property = "authPass", required = true, readonly = true)
    private String authPass;
    @Parameter(defaultValue = "C:\\", property = "pathToJar")
    private String pathToJar;



    public void execute() throws MojoExecutionException {

        // извлечение метаинформации
        StringBuilder meta = new StringBuilder();
        try {
            JarFile jarFile = new JarFile(pathToJar);
            Attributes attributes = jarFile.getManifest().getMainAttributes();
            for (Object attr : attributes.keySet())
                meta.append(String.format("%s=%s", attr, attributes.getValue(attr.toString()))).append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
        String metaJar = String.valueOf(meta);



        Properties properties = new Properties();

        properties.put("mail.smtp.host", authServ);
        //Требуется ли аутентификация для отправки сообщения
        properties.put("mail.smtp.auth", "true");
        //Порт для установки соединения
        properties.put("mail.smtp.socketFactory.port", "465");
        //Фабрика сокетов, так как при отправке сообщения Yandex требует SSL-соединения
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(properties,
                //Аутентификатор - объект, который передает логин и пароль
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(authUser, authPass);
                    }
                });

        try {
            // новое почтовое сообщение
            Message message = new MimeMessage(session);
            //От кого
            message.setFrom(new InternetAddress(emailFrom));
            //Кому
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            //Тема письма
            message.setSubject("Отчёт о сформированном jar");
            //Файл вложения
            File file = new File(pathToJar);

            //Собираем содержимое письма из частей
            MimeMultipart multipart = new MimeMultipart();
            //Первая часть - текст письма
            MimeBodyPart part1 = new MimeBodyPart();
            part1.addHeader("Content-Type", "text/plain; charset=UTF-8");
            part1.setDataHandler(new DataHandler(metaJar, "text/plain; charset=\"utf-8\""));
            multipart.addBodyPart(part1);

            //Вторая часть - файл
            MimeBodyPart part2 = new MimeBodyPart();
            part2.setFileName(MimeUtility.encodeWord(file.getName()));
            part2.setDataHandler(new DataHandler(new FileDataSource(file)));
            multipart.addBodyPart(part2);
            //Добавляем обе части в сообщение
            message.setContent(multipart);
            //Отправка
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
