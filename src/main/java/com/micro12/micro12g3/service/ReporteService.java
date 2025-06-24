package com.micro12.micro12g3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro12.micro12g3.model.*;
import com.micro12.micro12g3.repository.ReporteRepository;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    private List<InventarioDTO> inventario = new ArrayList<>();
    private List<VentasDTO> ventas = new ArrayList<>();

    public Reporte generarReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public List<Reporte> obtenerReportes() {
        return reporteRepository.findAll();
    }

    public Reporte actualizarFormato(int idReporte, String nuevoFormato) {
        Reporte reporte = reporteRepository.findById(idReporte).orElse(null);
        if (reporte == null) {
            return null;
        }

        reporte.setFormato(nuevoFormato);
        return reporteRepository.save(reporte);
    }

    public InventarioDTO agregarInventario(InventarioDTO item) {
        inventario.add(item);
        return item;
    }

    public List<InventarioDTO> getInventarioPorTienda(int idTienda) {
        List<InventarioDTO> resultado = new ArrayList<>();
        for (InventarioDTO item : inventario) {
            if (item.getIdTienda() == idTienda) {
                resultado.add(item);
            }
        }
        return resultado;
    }

    public VentasDTO agregarVenta(VentasDTO venta) {
        ventas.add(venta);
        return venta;
    }

    public List<VentasDTO> getVentasPorTienda(int idTienda) {
        List<VentasDTO> resultado = new ArrayList<>();
        for (VentasDTO venta : ventas) {
            if (venta.getIdTienda() == idTienda) {
                resultado.add(venta);
            }
        }
        return resultado;
    }

    public Object generarReportePorTipo(Reporte reporte) {
        int idTienda = reporte.getIdTienda();
        TipoReporte tipo = reporte.getExportar();

        if (tipo == TipoReporte.INVENTARIO) {
            List<InventarioDTO> inv = getInventarioPorTienda(idTienda);
            return new ReporteInventarioDTO(reporte, inv);
        }

        if (tipo == TipoReporte.VENTAS) {
            List<VentasDTO> ven = getVentasPorTienda(idTienda);
            return new ReporteVentasDTO(reporte, ven);
        }

        return null;
    }
}
