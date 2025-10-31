package school.sptech.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import school.sptech.dto.BoloMensagem;

@Component
public class BoloConsumer {

    private final JavaMailSender mailSender;

    public BoloConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "${broker.queue.name}")
    public void consumirMensagem(BoloMensagem bolo) {
        System.out.println("Recebido da fila: " + bolo);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("lucaslucena310805@gmail.com"); // precisa ser o mesmo do spring.mail.username
            message.setTo("gustavo.spresilli@sptech.school"); // pra onde vai o email
            message.setSubject("Novo pedido de bolo");
            message.setText(
                    "Foi feito um pedido de " + bolo.getQtd()
            );

            mailSender.send(message);
            System.out.println("E-mail enviado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}