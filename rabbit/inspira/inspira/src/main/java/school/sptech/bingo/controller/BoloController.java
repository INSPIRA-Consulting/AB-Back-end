package school.sptech.bingo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import school.sptech.bingo.dto.BoloMensagem;

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