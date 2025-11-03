package org.vortxyz.application.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErroDTO {
    private final String codigo;
    private final String mensagem;
}
