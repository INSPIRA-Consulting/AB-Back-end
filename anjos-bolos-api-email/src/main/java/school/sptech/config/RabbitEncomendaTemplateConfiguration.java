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
public class RabbitEncomendaTemplateConfiguration {

    private final ConnectionFactory connectionFactory;

    @Value("${broker.encomenda.exchange.name}")
    private String encomendaExchangeName;

    @Value("${broker.encomenda.queue.name}")
    private String encomendaQueueName;

    // Construtor manual para injetar a ConnectionFactory
    public RabbitEncomendaTemplateConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    // Define a fila
    @Bean
    public Queue encomendaQueue() {
        return new Queue(encomendaQueueName, true); // durable = true
    }

    // Define o Fanout Exchange
    @Bean
    public FanoutExchange encomendaFanoutExchange() {
        return new FanoutExchange(encomendaExchangeName);
    }

    // Faz o binding da fila com o exchange
    @Bean
    public Binding encomendaBinding(@Qualifier("encomendaQueue")Queue queue, @Qualifier("encomendaFanoutExchange")FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    // Configura o RabbitTemplate para usar JSON
    @Bean
    public RabbitTemplate encomendaRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(encomendaExchangeName);
        rabbitTemplate.setMessageConverter(jackson2EncomendaJsonMessageConverter());
        return rabbitTemplate;
    }

    // Bean para converter mensagens para JSON
    @Bean
    public Jackson2JsonMessageConverter jackson2EncomendaJsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}