package com.micro12.micro12g3.assembler;

import com.micro12.micro12g3.controller.ReporteController;
import com.micro12.micro12g3.model.Reporte;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReporteAssembler implements RepresentationModelAssembler<Reporte, EntityModel<Reporte>> {

    @Override
    @NonNull
    public EntityModel<Reporte> toModel(@NonNull Reporte reporte) {
        return EntityModel.of(reporte,
            linkTo(methodOn(ReporteController.class).getReportes()).withRel("reportes"),
            linkTo(methodOn(ReporteController.class).postReporte(reporte)).withRel("crear"),
            linkTo(methodOn(ReporteController.class).verReporte(reporte)).withRel("verReporte"),
            linkTo(methodOn(ReporteController.class).postInventario(null)).withRel("agregarInventario"),
            linkTo(methodOn(ReporteController.class).getInventarioPorTienda(reporte.getIdTienda())).withRel("inventario"),
            linkTo(methodOn(ReporteController.class).postVentas(null)).withRel("agregarVentas"),
            linkTo(methodOn(ReporteController.class).getVentas(reporte.getIdTienda())).withRel("ventas")
        );
    }
}
