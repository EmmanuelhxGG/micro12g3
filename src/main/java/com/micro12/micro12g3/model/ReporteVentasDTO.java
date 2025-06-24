package com.micro12.micro12g3.model;

import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteVentasDTO {
    private Reporte reporte;
    private List<VentasDTO> ventas;
}