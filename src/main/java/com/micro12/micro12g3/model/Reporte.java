package com.micro12.micro12g3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReporte;

    @Column(name = "id_tienda", length =  10)
    private int idTienda;

    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo;

    @Column(name = "formato", length = 20, nullable = false)
    private String formato;

    @Enumerated(EnumType.STRING)
    @Column(name = "exportar", length = 20, nullable = false)
    private TipoReporte exportar;
}
