package school.sptech.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitBackupTemplateConfiguration {

    private final ConnectionFactory connectionFactory;

    @Value("${broker.backup.exchange.name}")
    private String backupExchangeName;

    @Value("${broker.backup.queue.name}")
    private String backupQueueName;

    // Construtor manual para injetar a ConnectionFactory
    public RabbitBackupTemplateConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    // Define a fila
    @Bean
    public Queue backupQueue() {
        return new Queue(backupQueueName, true); // durable = true
    }

    // Define o Fanout Exchange
    @Bean
    public FanoutExchange backupFanoutExchange() {
        return new FanoutExchange(backupExchangeName);
    }

    // Faz o binding da fila com o exchange
    @Bean
    public Binding backupBinding(@Qualifier("backupQueue")Queue queue, @Qualifier("backupFanoutExchange")FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    // Configura o RabbitTemplate para usar JSON
    @Bean
    public RabbitTemplate backupRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(backupExchangeName);
        rabbitTemplate.setMessageConverter(jackson2BackuoJsonMessageConverter());
        return rabbitTemplate;
    }

    // Bean para converter mensagens para JSON
    @Bean
    public Jackson2JsonMessageConverter jackson2BackuoJsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}