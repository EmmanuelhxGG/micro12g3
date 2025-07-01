package com.micro12.micro12g3.model;

import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReporteRendimientoDTO {
    private Reporte reporte;
    private List<RendimientoDTO> rendimientos;
}
