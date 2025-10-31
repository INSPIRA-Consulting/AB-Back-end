package school.sptech.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import school.sptech.dto.BoloMensagem;

@RestController
@RequestMapping("/bolos")
public class BoloController {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.exchange.name}")
    private String exchangeName;

    public BoloController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public String registrarVenda(@RequestBody BoloMensagem bolo) {
        rabbitTemplate.convertAndSend(exchangeName, "", bolo);
        return "Bolo enviado para a fila com sucesso!";
    }
}