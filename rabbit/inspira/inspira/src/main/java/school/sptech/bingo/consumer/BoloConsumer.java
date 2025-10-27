package school.sptech.bingo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import school.sptech.bingo.dto.BoloMensagem;

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
                    "Detalhes do pedido:\n\n" +
                            "Peso: " + bolo.getPeso() + " kg\n" +
                            "Valor: R$ " + bolo.getValor() + "\n" +
                            "Recheio: " + bolo.getRecheio() + "\n" +
                            "Massa: " + bolo.getMassa() + "\n" +
                            "Cobertura: " + bolo.getCobertura()
            );

            mailSender.send(message);
            System.out.println("E-mail enviado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}