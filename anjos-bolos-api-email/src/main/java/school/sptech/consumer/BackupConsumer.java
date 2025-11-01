package school.sptech.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import school.sptech.dto.BackupDTO;

@Component
public class BackupConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackupConsumer.class);

    private final JavaMailSender mailSender;

    private final ObjectMapper objectMapper;

    @Value("${spring.backup.mail.to}")
    private String to;

    @Value("${spring.backup.mail.username}")
    private String from;

    public BackupConsumer(@Qualifier("backupMailSender") JavaMailSender mailSender, ObjectMapper objectMapper) {
        this.mailSender = mailSender;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${broker.backup.queue.name}")
    public void consumirMensagem(byte[] payload) {
        try {
            BackupDTO backup = objectMapper.readValue(payload, BackupDTO.class);
            LOGGER.info("Recebido da fila: {}", backup);

            String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(backup);
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, false, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            System.out.println("Destinatário: " + to);
            helper.setSubject("Notificação de Backup");
            helper.setText(formatHtml(backup), true);
            mailSender.send(mime);
            LOGGER.info("E-mail de notificação de backup enviado: {}", backup);
        } catch (Exception ex) {
            LOGGER.error("Falha ao processar mensagem de backup ou enviar email", ex);
        }
    }

    private String formatHtml(BackupDTO backup) {
        return "<html><body>" +
                "<h1>Notificação de Backup</h1>" +
                "<p>Detalhes do backup:</p>" +
                "<ul>" +
                "<li><strong>Nome do Arquivo:</strong> " + backup.nomeArquivo() + "</li>" +
                "<li><strong>Caminho do Arquivo</strong> " + backup.caminhoArquivo() + "</li>" +
                "<li><strong>Data do Backup</strong> " + backup.dataBackup() + "</li>" +
                "<li><strong>Status:</strong> " + backup.status() + "</li>" +
                "</ul>" +
                "</body></html>";
    }

}