package com.detallenavegacion.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.detallenavegacion.xml.report.Reporte;
import com.detallenavegacion.xml.report.ResponseReporte;

import jakarta.annotation.PostConstruct;

@Component
public class ResponseReporteRepository {
  private static final Map<String, ResponseReporte> Reportes = new HashMap<>();

  @PostConstruct
  public void initData() {

    ResponseReporte Reporte = new ResponseReporte();
    Reporte.setCode(12);
    Reporte.setDescription("RESPONSE1");
    Reporte.setTotal(1200);

    Reporte subReporte = new Reporte();
    subReporte.setFecha("01/01/2000");
    subReporte.setHora("12:00:00");
    subReporte.setId(224);
    subReporte.setNombre("Reporte 2");
    subReporte.setTotal(900);

    List<Reporte> lista = Reporte.getReporte();
    lista.add(subReporte);

    subReporte = new Reporte();
    subReporte.setFecha("01/01/2000");
    subReporte.setHora("12:00:00");
    subReporte.setId(224);
    subReporte.setNombre("Reporte 2");
    subReporte.setTotal(900);

    lista.add(subReporte);
    

    Reportes.put(Reporte.getDescription(), Reporte);

  }

  public ResponseReporte findResponseReporte(String name) {
    Assert.notNull(name, "The Reporte's name must not be null");
    return Reportes.get(name);
  }
}
