package vrsoft.teste;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.amqp.core.AmqpTemplate;
import vrsoft.teste.service.PedidoService;
import vrsoft.teste.model.Pedido;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    @Mock
    private AmqpTemplate amqpTemplate;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void devePublicarPedidoNaFilaCorretamente() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        pedido.setProduto("Teclado");
        pedido.setQuantidade(1);
        pedido.setDataCriacao(LocalDateTime.now());

        // Act
        pedidoService.publicarPedido(pedido);

        // Assert
        verify(amqpTemplate, times(1))
                .convertAndSend("pedidos.entrada.gabriel", pedido);
    }
}

