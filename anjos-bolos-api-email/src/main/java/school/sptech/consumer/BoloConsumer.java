package school.sptech.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import school.sptech.dto.BoloDTO;

@Component
public class BoloConsumer {

    private final JavaMailSender mailSender;

    @Value("${spring.encomenda.mail.to}")
    private String to;

    @Value("${spring.encomenda.mail.username}")
    private String from;

    public BoloConsumer(@Qualifier("encomendaMailSender") JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "${broker.encomenda.queue.name}")
    public void consumirMensagem(BoloDTO bolo) {
        System.out.println("Recebido da fila: " + bolo);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Novo pedido de bolo");
            message.setText(
                    "Foi feito um pedido de " + bolo.qtd()
            );

            mailSender.send(message);
            System.out.println("E-mail enviado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}