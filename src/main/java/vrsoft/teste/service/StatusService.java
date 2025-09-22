package vrsoft.teste.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private final Map<UUID, String> statusMap = new HashMap<>();

    public void atualizarStatus(UUID id, String status) {
        statusMap.put(id, status);
    }

    public String obterStatus(UUID id) {
        return statusMap.getOrDefault(id, "Status não encontrado.");
    }
}
