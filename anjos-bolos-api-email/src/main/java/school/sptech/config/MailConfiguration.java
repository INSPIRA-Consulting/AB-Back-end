package school.sptech.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.encomenda.mail")
    public JavaMailSender encomendaMailSender() {
        return buildSender();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.backup.mail")
    public JavaMailSender backupMailSender() {
        return buildSender();
    }

    private JavaMailSender buildSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        Properties props = sender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        return sender;
    }

}