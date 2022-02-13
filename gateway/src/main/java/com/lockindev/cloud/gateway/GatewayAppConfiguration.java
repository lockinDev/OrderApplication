package com.lockindev.cloud.gateway;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class GatewayAppConfiguration implements EnvironmentAware {

	@Override
	public void setEnvironment(final Environment environment) {
		
		System.setProperty("javax.net.ssl.trustStore", "gateway.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "devsecurity123");
		
		System.setProperty("javax.net.ssl.keyStore", "gateway.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "devsecurity123");

	}
}
