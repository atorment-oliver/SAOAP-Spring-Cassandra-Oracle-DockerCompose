package com.detallenavegacion.config;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import com.detallenavegacion.brl.brlDetalleNavegacion;
import com.detallenavegacion.brl.brlDetalleNavegacion;
import com.detallenavegacion.webservices.wsDetalleNavegacion;


@EnableWs
@Configuration
public class SoapConfig extends WsConfigurerAdapter {

  @Bean
  public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean<>(servlet, "/ws/*");
  }

  @Bean(name = "DetalleNavegacionWsdl")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema detalleNavegacionSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("DetalleNavegacionPort");
    wsdl11Definition.setLocationUri("/ws/operation");
    wsdl11Definition.setTargetNamespace("http://www.detallenavegacion.com/xml/report");
    wsdl11Definition.setSchema(detalleNavegacionSchema);
    return wsdl11Definition;
  }

  @Bean
  public XsdSchema detalleNavegacionSchema() {
    return new SimpleXsdSchema(new ClassPathResource("detalle_navegacion.xsd"));
  }
  @Bean
  public wsDetalleNavegacion wsDetalleNavegacion() {
    return new wsDetalleNavegacion();
  }
}
