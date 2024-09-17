package com.detallenavegacion.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractSessionConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.stereotype.Service;

import com.detallenavegacion.util.Parametros;
@Configuration
@SuppressWarnings("unused")
@Service
@EnableCassandraRepositories(basePackages = "com.detallenavegacion.repository")
public class CassandraConfiguration extends AbstractSessionConfiguration {

	@Autowired
	Environment env;

	@Override
	protected String getContactPoints() {
		return env.getProperty("cassandra.cluster.contact-points", Parametros.bdIp);
	}

	@Override
	protected String getKeyspaceName() {
		return env.getProperty("cassandra.keyspace", Parametros.bdNombre);
	}

	@Override
	protected int getPort() {
		return env.getProperty("cassandra.cluster.port", Integer.TYPE, Parametros.bdPuerto);
	}

    protected boolean getMetricsEnabled() {
        return false;
    }
}