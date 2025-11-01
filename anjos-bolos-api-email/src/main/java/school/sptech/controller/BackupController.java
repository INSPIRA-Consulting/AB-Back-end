package school.sptech.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.dto.BackupDTO;

@RestController
@RequestMapping("/backups")
public class BackupController {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.backup.exchange.name}")
    private String exchangeName;

    public BackupController(@Qualifier("backupRabbitTemplate")RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public String registrarBackup(@RequestBody BackupDTO backup) {
        rabbitTemplate.convertAndSend(exchangeName, "", backup);
        return "Backup enviado para a fila com sucesso!";
    }

}