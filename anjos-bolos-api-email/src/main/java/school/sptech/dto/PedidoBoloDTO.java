package school.sptech.dto;

import java.util.List;

public record PedidoBoloDTO(
        List<String> massas,
        List<String> coberturas,
        List<String> recheios,
        Double pesoKg,
        String observacao
) {}
