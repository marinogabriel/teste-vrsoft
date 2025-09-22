package vrsoft.teste.rabbitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    // Fila de entrada
    @Bean
    public Queue pedidosEntrada() {
        return QueueBuilder.durable("pedidos.entrada.gabriel")
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", "pedidos.entrada.gabriel.dlq")
                .build();
    }

    // Fila de saída de sucesso
    @Bean
    public Queue pedidosStatusSucesso() {
        return new Queue("pedidos.status.sucesso.gabriel");
    }

    // Fila de saída de falha
    @Bean
    public Queue pedidosStatusFalha() {
        return new Queue("pedidos.status.falha.gabriel");
    }

    // Dead Letter Queue
    @Bean
    public Queue pedidosEntradaDlq() {
        return new Queue("pedidos.entrada.gabriel.dlq");
    }

    // Exchange (se necessário)
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("pedidos.exchange");
    }

    // Binding (se necessário)
    @Bean
    public Binding bindingEntrada() {
        return BindingBuilder.bind(pedidosEntrada()).to(directExchange()).with("pedidos");
    }

    @Bean
    public Binding bindingStatusSucesso() {
        return BindingBuilder.bind(pedidosStatusSucesso()).to(directExchange()).with("status.sucesso");
    }

    @Bean
    public Binding bindingStatusFalha() {
        return BindingBuilder.bind(pedidosStatusFalha()).to(directExchange()).with("status.falha");
    }
}
