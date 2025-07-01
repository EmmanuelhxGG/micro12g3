package com.micro12.micro12g3.service;

import com.micro12.micro12g3.model.Reporte;
import com.micro12.micro12g3.model.TipoReporte;
import com.micro12.micro12g3.repository.ReporteRepository;
import com.micro12.micro12g3.model.InventarioDTO;
import com.micro12.micro12g3.model.VentasDTO;
import com.micro12.micro12g3.model.RendimientoDTO;
import com.micro12.micro12g3.model.ReporteInventarioDTO;
import com.micro12.micro12g3.model.ReporteVentasDTO;
import com.micro12.micro12g3.model.ReporteRendimientoDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteService reporteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerarReporte() {
        Reporte entrada = new Reporte(0, 1, "ventas", "pdf", TipoReporte.VENTAS);
        Reporte salida = new Reporte(10, 1, "ventas", "pdf", TipoReporte.VENTAS);

        when(reporteRepository.save(entrada)).thenReturn(salida);

        Reporte resultado = reporteService.generarReporte(entrada);
        assertThat(resultado.getIdReporte()).isEqualTo(10);
        verify(reporteRepository).save(entrada);
    }

    @Test
    void testObtenerReportes() {
        List<Reporte> lista = Arrays.asList(
            new Reporte(1, 1, "ventas", "pdf", TipoReporte.VENTAS),
            new Reporte(2, 2, "inventario", "excel", TipoReporte.INVENTARIO)
        );

        when(reporteRepository.findAll()).thenReturn(lista);

        List<Reporte> resultado = reporteService.obtenerReportes();
        assertThat(resultado).hasSize(2);
        verify(reporteRepository).findAll();
    }

    @Test
    void testActualizarFormatoExistente() {
        Reporte existente = new Reporte(5, 1, "ventas", "pdf", TipoReporte.VENTAS);
        Reporte actualizado = new Reporte(5, 1, "ventas", "excel", TipoReporte.VENTAS);

        when(reporteRepository.findById(5)).thenReturn(Optional.of(existente));
        when(reporteRepository.save(any())).thenReturn(actualizado);

        Reporte resultado = reporteService.actualizarFormato(5, "excel");
        assertThat(resultado.getFormato()).isEqualTo("excel");
        verify(reporteRepository).save(existente);
    }

    @Test
    void testAgregarYObtenerInventario() {
        InventarioDTO item = new InventarioDTO(1, "Producto A", 1000);
        reporteService.agregarInventario(item);

        List<InventarioDTO> resultado = reporteService.getInventarioPorTienda(1);
        assertThat(resultado).contains(item);
    }

    @Test
    void testAgregarYObtenerVentas() {
        VentasDTO venta = new VentasDTO(1, "Producto B", 20);
        reporteService.agregarVenta(venta);

        List<VentasDTO> resultado = reporteService.getVentasPorTienda(1);
        assertThat(resultado).contains(venta);
    }

    @Test
    void testAgregarYObtenerRendimiento() {
        RendimientoDTO rendimiento = new RendimientoDTO(1, 75);
        reporteService.agregarRendimiento(rendimiento);

        List<RendimientoDTO> resultado = reporteService.getRendimientoPorTienda(1);
        assertThat(resultado).contains(rendimiento);
    }

    @Test
    void testGenerarReportePorTipoInventario() {
        Reporte reporte = new Reporte(1, 100, "tipo", "formato", TipoReporte.INVENTARIO);
        InventarioDTO item = new InventarioDTO(100, "Producto A", 500);
        reporteService.agregarInventario(item);

        Object resultado = reporteService.generarReportePorTipo(reporte);
        assertThat(resultado).isInstanceOf(ReporteInventarioDTO.class);
    }

    @Test
    void testGenerarReportePorTipoVentas() {
        Reporte reporte = new Reporte(1, 200, "tipo", "formato", TipoReporte.VENTAS);
        VentasDTO venta = new VentasDTO(200, "Producto B", 50);
        reporteService.agregarVenta(venta);

        Object resultado = reporteService.generarReportePorTipo(reporte);
        assertThat(resultado).isInstanceOf(ReporteVentasDTO.class);
    }

    @Test
    void testGenerarReportePorTipoRendimiento() {
        Reporte reporte = new Reporte(1, 1, "rendimiento", "pdf", TipoReporte.RENDIMIENTO);
        RendimientoDTO rendimiento = new RendimientoDTO(1, 100);
        reporteService.agregarRendimiento(rendimiento);

        Object resultado = reporteService.generarReportePorTipo(reporte);
        assertThat(resultado).isInstanceOf(ReporteRendimientoDTO.class);
    }

    @Test
    void testGetInventarioPorTiendaFiltrado() {
        InventarioDTO item1 = new InventarioDTO(1, "Producto A", 1000);
        InventarioDTO item2 = new InventarioDTO(2, "Producto B", 500);
        reporteService.agregarInventario(item1);
        reporteService.agregarInventario(item2);

        List<InventarioDTO> resultado = reporteService.getInventarioPorTienda(1);
        assertThat(resultado).containsExactly(item1);
    }

    @Test
    void testGetVentasPorTiendaFiltrado() {
        VentasDTO venta1 = new VentasDTO(1, "Producto A", 20);
        VentasDTO venta2 = new VentasDTO(2, "Producto B", 10);
        reporteService.agregarVenta(venta1);
        reporteService.agregarVenta(venta2);

        List<VentasDTO> resultado = reporteService.getVentasPorTienda(1);
        assertThat(resultado).containsExactly(venta1);
    }

    @Test
    void testGetRendimientoPorTiendaFiltrado() {
        RendimientoDTO ren1 = new RendimientoDTO(1, 100);
        RendimientoDTO ren2 = new RendimientoDTO(2, 70);
        reporteService.agregarRendimiento(ren1);
        reporteService.agregarRendimiento(ren2);

        List<RendimientoDTO> resultado = reporteService.getRendimientoPorTienda(1);
        assertThat(resultado).containsExactly(ren1);
    }
}
