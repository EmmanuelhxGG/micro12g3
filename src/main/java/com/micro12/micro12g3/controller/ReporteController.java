package com.micro12.micro12g3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.micro12.micro12g3.model.*;
import com.micro12.micro12g3.service.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<Reporte>> getReportes() {
        List<Reporte> reportes = reporteService.obtenerReportes();
        if (reportes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reportes, HttpStatus.OK);
    }

    @PostMapping("/generar")
    public ResponseEntity<Reporte> postReporte(@RequestBody Reporte reporte) {
        Reporte nuevo = reporteService.generarReporte(reporte);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PostMapping("/inventario")
    public ResponseEntity<InventarioDTO> postInventario(@RequestBody InventarioDTO dto) {
        InventarioDTO agregado = reporteService.agregarInventario(dto);
        return new ResponseEntity<>(agregado, HttpStatus.CREATED);
    }

    @GetMapping("/inventario")
    public ResponseEntity<List<InventarioDTO>> getInventarioPorTienda(@RequestParam int idTienda) {
        List<InventarioDTO> datos = reporteService.getInventarioPorTienda(idTienda);
        if (datos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

    @PostMapping("/ventas")
    public ResponseEntity<VentasDTO> postVentas(@RequestBody VentasDTO dto) {
        VentasDTO agregado = reporteService.agregarVenta(dto);
        return new ResponseEntity<>(agregado, HttpStatus.CREATED);
    }

    @GetMapping("/ventas")
    public ResponseEntity<List<VentasDTO>> getVentas(@RequestParam int idTienda) {
        List<VentasDTO> datos = reporteService.getVentasPorTienda(idTienda);
        if (datos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

    @PostMapping("/ver")
    public ResponseEntity<Object> verReporte(@RequestBody Reporte reporte) {
        Object resultado = reporteService.generarReportePorTipo(reporte);
        if (resultado == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
