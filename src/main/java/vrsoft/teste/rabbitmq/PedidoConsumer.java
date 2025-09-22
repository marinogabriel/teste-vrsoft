package vrsoft.teste.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;
import vrsoft.teste.exception.ExcecaoDeProcessamento;
import vrsoft.teste.model.Pedido;
import vrsoft.teste.model.StatusPedido;
import vrsoft.teste.service.StatusService;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class PedidoConsumer {

    private final AmqpTemplate amqpTemplate;
    private final StatusService statusService;

    private static final Random random = new Random();

    public PedidoConsumer(AmqpTemplate amqpTemplate, StatusService statusService) {
        this.amqpTemplate = amqpTemplate;
        this.statusService = statusService;
    }

    @RabbitListener(queues = "pedidos.entrada.gabriel")
    public void consumirPedido(Pedido pedido) {
        System.out.println("Iniciando processamento do pedido " + pedido.getId());

        try {
            // Simulando o tempo de processamento
            try {
                Thread.sleep(random.nextInt(2000) + 1000); // 1-3 segundos
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 20% de chance de falha no processamento
            if (random.nextDouble() < 0.2) {
                throw new ExcecaoDeProcessamento("Erro durante o processamento do pedido.");
            }

            // Sucesso no processamento
            System.out.println("Pedido " + pedido.getId() + " processado com sucesso.");

            // Publicando o status de sucesso
            StatusPedido status = new StatusPedido(pedido.getId(), "SUCESSO", LocalDateTime.now());
            statusService.atualizarStatus(pedido.getId(), "SUCESSO");

            amqpTemplate.convertAndSend("pedidos.status.sucesso.gabriel", status);

        } catch (ExcecaoDeProcessamento e) {
            System.err.println("Falha no processamento do pedido " + pedido.getId());

            // Enviar para DLQ
            amqpTemplate.convertAndSend("pedidos.status.falha.gabriel", new StatusPedido(pedido.getId(), "FALHA", e.getMessage()));
            statusService.atualizarStatus(pedido.getId(), "FALHA");

            // Rejeitar a mensagem, enviando para a DLQ
            throw e; // Isso vai enviar a mensagem para a DLQ automaticamente
        }
    }
}
