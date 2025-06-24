package com.micro12.micro12g3.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentasDTO {
    private int idTienda;
    private String producto;
    private int cantidadVendida;
}
