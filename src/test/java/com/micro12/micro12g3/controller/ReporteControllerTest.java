package com.micro12.micro12g3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro12.micro12g3.model.*;
import com.micro12.micro12g3.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReporteController.class)
class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService reporteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetReportes_conDatos() throws Exception {
        Reporte r1 = new Reporte(1, 1, "tipo1", "pdf", TipoReporte.INVENTARIO);
        Reporte r2 = new Reporte(2, 2, "tipo2", "xls", TipoReporte.VENTAS);

        Mockito.when(reporteService.obtenerReportes()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/api/reportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idReporte").value(1))
                .andExpect(jsonPath("$[1].idTienda").value(2));
    }

    @Test
    void testGetReportes_sinDatos() throws Exception {
        Mockito.when(reporteService.obtenerReportes()).thenReturn(List.of());

        mockMvc.perform(get("/api/reportes"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPostReporte_creaReporte() throws Exception {
        Reporte reporteEntrada = new Reporte(0, 1, "tipo", "pdf", TipoReporte.INVENTARIO);
        Reporte reporteGuardado = new Reporte(10, 1, "tipo", "pdf", TipoReporte.INVENTARIO);

        Mockito.when(reporteService.generarReporte(any(Reporte.class))).thenReturn(reporteGuardado);

        mockMvc.perform(post("/api/reportes/generar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reporteEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idReporte").value(10));
    }

    @Test
    void testPostInventario_creaInventario() throws Exception {
        InventarioDTO dtoEntrada = new InventarioDTO(1, "Producto A", 100);
        InventarioDTO dtoGuardado = new InventarioDTO(1, "Producto A", 100);

        Mockito.when(reporteService.agregarInventario(any(InventarioDTO.class))).thenReturn(dtoGuardado);

        mockMvc.perform(post("/api/reportes/inventario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.producto").value("Producto A"));
    }

    @Test
    void testGetInventarioPorTienda_conDatos() throws Exception {
        InventarioDTO dto1 = new InventarioDTO(1, "Prod1", 10);
        InventarioDTO dto2 = new InventarioDTO(1, "Prod2", 20);

        Mockito.when(reporteService.getInventarioPorTienda(eq(1))).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/reportes/inventario").param("idTienda", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].producto").value("Prod1"))
                .andExpect(jsonPath("$[1].cantidad").value(20));
    }

    @Test
    void testGetInventarioPorTienda_sinDatos() throws Exception {
        Mockito.when(reporteService.getInventarioPorTienda(eq(999))).thenReturn(List.of());

        mockMvc.perform(get("/api/reportes/inventario").param("idTienda", "999"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPostVentas_creaVenta() throws Exception {
        VentasDTO dtoEntrada = new VentasDTO(1, "ProdA", 15);
        VentasDTO dtoGuardado = new VentasDTO(1, "ProdA", 15);

        Mockito.when(reporteService.agregarVenta(any(VentasDTO.class))).thenReturn(dtoGuardado);

        mockMvc.perform(post("/api/reportes/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.producto").value("ProdA"));
    }

    @Test
    void testGetVentasPorTienda_conDatos() throws Exception {
        VentasDTO dto1 = new VentasDTO(1, "Prod1", 5);
        VentasDTO dto2 = new VentasDTO(1, "Prod2", 7);

        Mockito.when(reporteService.getVentasPorTienda(eq(1))).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/reportes/ventas").param("idTienda", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].producto").value("Prod1"))
                .andExpect(jsonPath("$[1].cantidadVendida").value(7));
    }

    @Test
    void testGetVentasPorTienda_sinDatos() throws Exception {
        Mockito.when(reporteService.getVentasPorTienda(eq(999))).thenReturn(List.of());

        mockMvc.perform(get("/api/reportes/ventas").param("idTienda", "999"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testVerReporte_inventario() throws Exception {
        Reporte reporte = new Reporte(1, 1, "tipo", "pdf", TipoReporte.INVENTARIO);

        ReporteInventarioDTO dto = new ReporteInventarioDTO(reporte, List.of(
                new InventarioDTO(1, "ProdX", 10)
        ));

        Mockito.when(reporteService.generarReportePorTipo(any(Reporte.class))).thenReturn(dto);

        mockMvc.perform(post("/api/reportes/ver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reporte.idReporte").value(1))
                .andExpect(jsonPath("$.inventario[0].producto").value("ProdX"));
    }

    @Test
    void testVerReporte_ventas() throws Exception {
        Reporte reporte = new Reporte(2, 2, "tipo", "xls", TipoReporte.VENTAS);

        ReporteVentasDTO dto = new ReporteVentasDTO(reporte, List.of(
                new VentasDTO(2, "ProdY", 5)
        ));

        Mockito.when(reporteService.generarReportePorTipo(any(Reporte.class))).thenReturn(dto);

        mockMvc.perform(post("/api/reportes/ver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reporte.idReporte").value(2))
                .andExpect(jsonPath("$.ventas[0].producto").value("ProdY"));
    }

    @Test
    void testVerReporte_rendimiento() throws Exception {
        Reporte reporte = new Reporte(3, 3, "tipo", "pdf", TipoReporte.RENDIMIENTO);

        RendimientoDTO rendimientoDTO = new RendimientoDTO(3, 75);

        ReporteRendimientoDTO dto = new ReporteRendimientoDTO(reporte, List.of(rendimientoDTO));

        Mockito.when(reporteService.generarReportePorTipo(any(Reporte.class))).thenReturn(dto);

        mockMvc.perform(post("/api/reportes/ver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reporte.idReporte").value(3))
                .andExpect(jsonPath("$.rendimientos[0].porcentaje").value(75));

    }


    @Test
    void testVerReporte_sinResultado() throws Exception {
        Reporte reporte = new Reporte(4, 4, "tipo", "pdf", TipoReporte.VENTAS);

        Mockito.when(reporteService.generarReportePorTipo(any(Reporte.class))).thenReturn(null);

        mockMvc.perform(post("/api/reportes/ver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isNoContent());
    }
}
