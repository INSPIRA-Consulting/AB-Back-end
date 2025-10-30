package school.sptech;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTemplateConfiguration {

    private final ConnectionFactory connectionFactory;

    @Value("${broker.exchange.name}")
    private String exchangeName;

    @Value("${broker.queue.name}")
    private String queueName;

    // Construtor manual para injetar a ConnectionFactory
    public RabbitTemplateConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    // Define a fila
    @Bean
    public Queue queue() {
        return new Queue(queueName, true); // durable = true
    }

    // Define o Fanout Exchange
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(exchangeName);
    }

    // Faz o binding da fila com o exchange
    @Bean
    public Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    // Configura o RabbitTemplate para usar JSON
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(exchangeName);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    // Bean para converter mensagens para JSON
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
