package vrsoft.teste.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import vrsoft.teste.model.Pedido;

@Service
public class PedidoService {

    private static final String FILA_PEDIDOS = "pedidos.entrada.gabriel";

    private final AmqpTemplate amqpTemplate;

    public PedidoService(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void publicarPedido(Pedido pedido) {
        amqpTemplate.convertAndSend(FILA_PEDIDOS, pedido);
    }
}

