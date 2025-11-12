package school.sptech.consumer;

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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class BackupConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackupConsumer.class);

    private final JavaMailSender mailSender;

    @Value("${spring.backup.mail.to}")
    private String to;

    @Value("${spring.backup.mail.username}")
    private String from;

    public BackupConsumer(@Qualifier("backupMailSender") JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "${broker.backup.queue.name}")
    public void consumirMensagem(BackupDTO backup) {
        try {
            LOGGER.info("Recebido da fila: {}", backup);

            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, false, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            LOGGER.info("E-mail de notificação de backup enviado para: {}", to);
            helper.setSubject("Notificação de Backup");
            helper.setText(formatHtml(backup), true);
            mailSender.send(mime);
            LOGGER.info("E-mail de notificação de backup enviado: {}", backup);
        } catch (Exception ex) {
            LOGGER.error("Falha ao processar mensagem de backup ou enviar email", ex);
        }
    }

    private String formatHtml(BackupDTO backup) {
        String html = """
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f8f9fa;
                        color: #333;
                        padding: 20px;
                        margin: 0;
                    }

                    .container {
                        max-width: 500px;
                        width: 90%%;
                        background: #f8f9fa;
                        border-radius: 8px;
                        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                        margin: 0 auto;
                        padding: 25px;
                        box-sizing: border-box;
                    }

                    h1 {
                        text-align: center;
                        color: #333;
                        font-size: 1.4em;
                        margin: 15px 0 25px 0;
                        word-wrap: break-word;
                    }

                    .status {
                        font-size: 1.1em;
                        font-weight: bold;
                        text-align: center;
                        padding: 15px;
                        border-radius: 6px;
                        margin: 20px 0;
                        word-wrap: break-word;
                        max-width: 450px;
                        margin-left: auto;
                        margin-right: auto;
                    }

                    .success {
                        background-color: #d4edda;
                        color: #155724;
                        border: 1px solid #c3e6cb;
                    }

                    .failure {
                        background-color: #f8d7da;
                        color: #721c24;
                        border: 1px solid #f5c6cb;
                    }

                    .info-section {
                        max-width: 450px;
                        margin: 20px auto;
                    }

                    p {
                        margin: 15px 0;
                        font-size: 0.95em;
                        line-height: 1.5;
                        white-space: nowrap;
                    }

                    .info {
                        white-space: nowrap;
                    }

                    .caminho-arquivo {
                        word-wrap: break-word;
                        overflow-wrap: break-word;
                        white-space: normal;
                    }

                    strong {
                        display: inline-block;
                        color: #555;
                    }

                    .footer {
                        text-align: center;
                        font-size: 0.85em;
                        color: #777;
                        margin-top: 30px;
                        word-wrap: break-word;
                        max-width: 350px;
                        margin-left: auto;
                        margin-right: auto;
                    }

                    @media (max-width: 600px) and (orientation: landscape) {
                        .container {
                            max-width: 450px;
                            padding: 20px;
                        }

                        .status {
                            max-width: 350px;
                        }

                        .info-section {
                            max-width: 350px;
                        }
                    }

                    @media (max-width: 480px) {
                        body {
                            padding: 15px;
                        }

                        .container {
                            width: 95%%;
                            padding: 20px;
                        }

                        h1 {
                            font-size: 1.3em;
                        }

                        .status {
                            font-size: 1em;
                            padding: 12px;
                            max-width: 100%%;
                        }

                        .info-section {
                            max-width: 100%%;
                        }

                        p {
                            font-size: 0.9em;
                        }

                        strong {
                            display: block;
                            margin-bottom: 3px;
                            min-width: auto;
                        }

                        .footer {
                            max-width: 100%%;
                        }
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Status do Backup do Banco de Dados</h1>
            """;

        if (backup.status().equalsIgnoreCase("Backup Realizado com Sucesso")) {
            html += """
                    <div class="status success">
                        ✅ Backup realizado com Sucesso!
                    </div>
                """;
        } else {
            html += """
                    <div class="status failure">
                        ❌ Falha ao realizar o Backup!
                    </div>
                """;
        }

        // Formatação da data para o padrão brasileiro dd/MM/yyyy HH:mm:ss
        String dataFormatada = formatarData(backup.dataBackup());
        String caminhoFormatado = backup.caminhoArquivo().replace("backups/","backups/<br>");

        html += """
                    <div class="info-section">
                        <p class ="info"><strong>Nome do Arquivo:</strong> %s</p>
                        <p class ="caminho-arquivo"><strong>Caminho do Arquivo:</strong> %s</p>
                        <p class ="info"><strong>Data do Backup:</strong> %s</p>
                        <p class ="info"><strong>Status:</strong> %s</p>
                    </div>

                    <div class="footer">
                        Este é um e-mail automático — não responda.
                    </div>
                </div>
            </body>
            </html>
            """
                .formatted(backup.nomeArquivo(), caminhoFormatado, dataFormatada, backup.status());

        return html;
    }

    private String formatarData(String dataOriginal) {
        try {
            // Tenta fazer o parse da data assumindo formato ISO (yyyy-MM-ddTHH:mm:ss)
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            LocalDateTime dateTime = LocalDateTime.parse(dataOriginal, inputFormatter);
            return dateTime.format(outputFormatter);

        } catch (Exception e) {
            // Se não conseguir fazer o parse, retorna a data original
            LOGGER.warn("Não foi possível formatar a data: {}. Usando formato original.", dataOriginal);
            return dataOriginal;
        }
    }

}