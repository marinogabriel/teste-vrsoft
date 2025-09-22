package vrsoft.teste.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vrsoft.teste.service.PedidoService;
import vrsoft.teste.service.StatusService;
import vrsoft.teste.model.Pedido;

import java.util.UUID;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final StatusService statusService;

    public PedidoController(PedidoService pedidoService, StatusService statusService) {
        this.pedidoService = pedidoService;
        this.statusService = statusService;
    }

    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody Pedido pedido) {
        if (pedido.getQuantidade() <= 0 || pedido.getProduto().isEmpty()) {
            return ResponseEntity.badRequest().body("Pedido inválido");
        }

        pedidoService.publicarPedido(pedido);
        statusService.atualizarStatus(pedido.getId(), "Recebido");

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Pedido recebido. ID: " + pedido.getId());
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<String> obterStatus(@PathVariable UUID id) {
        String status = statusService.obterStatus(id);
        return ResponseEntity.ok(status);
    }
}
