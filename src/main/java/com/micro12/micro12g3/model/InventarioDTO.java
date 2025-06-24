package com.micro12.micro12g3.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {
    private int idTienda;
    private String producto;
    private int cantidad;
}
