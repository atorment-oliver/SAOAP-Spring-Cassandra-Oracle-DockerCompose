package com.detallenavegacion.endpoints;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.detallenavegacion.brl.brlDetalleNavegacion;
import com.detallenavegacion.repository.ResponseReporteRepository;
import com.detallenavegacion.util.UtilDate;
import com.detallenavegacion.webservices.wsDetalleNavegacion;
import com.detallenavegacion.xml.report.GetConsumoPorHora;
import com.detallenavegacion.xml.report.DetalleNavegacionRequest;
import com.detallenavegacion.xml.report.GetConsumoPorHoraResponse;
import com.detallenavegacion.xml.report.ResponseReporte;

@Endpoint
public class DetalleNavegacionEndpoint
{
  private static final String NAMESPACE_URI = "http://www.detallenavegacion.com/xml/report";

  @Autowired
  private final wsDetalleNavegacion wsDetalleNavegacion;

  @Autowired
  public DetalleNavegacionEndpoint(wsDetalleNavegacion wsDetalleNavegacion) {
    this.wsDetalleNavegacion = wsDetalleNavegacion;
  }


  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getConsumoPorHora")
  @ResponsePayload
  public GetConsumoPorHoraResponse getConsumoPorHora(@RequestPayload GetConsumoPorHora request) throws ParseException {
    GetConsumoPorHoraResponse objResponse = wsDetalleNavegacion.getConsumoPorHora(request.getIsdn(), 
    UtilDate.convertToDate(request.getFechaInicio()), 
    UtilDate.convertToDate(request.getFechaFin()), 
    request.getCategoria());
    return objResponse;
  }
}
