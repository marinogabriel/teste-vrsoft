package vrsoft.teste.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StatusPedido {

    private UUID idPedido;
    private String status;
    private String mensagemErro;
    private LocalDateTime dataProcessamento;

    public StatusPedido(UUID idPedido, String status, String mensagemErro) {
        this.idPedido = idPedido;
        this.status = status;
        this.mensagemErro = mensagemErro;
    }

    public StatusPedido(UUID idPedido, String status, LocalDateTime dataProcessamento) {
        this.idPedido = idPedido;
        this.status = status;
        this.dataProcessamento = dataProcessamento;
    }
}
