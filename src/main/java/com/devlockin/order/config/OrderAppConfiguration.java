package com.devlockin.order.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OrderAppConfiguration implements EnvironmentAware {

	@Override
	public void setEnvironment(final Environment environment) {

		
		// points to the path where orderprocessing.jks keystore is.
		System.setProperty("javax.net.ssl.trustStore", "keys/order/orderprocessing.jks");
		// password of the orderprocessing.jks keystore.
		System.setProperty("javax.net.ssl.trustStorePassword", "devsecurity123");
		
		// points to the path where orderprocessing.jks keystore is located.
		System.setProperty("javax.net.ssl.keyStore", "keys/order/orderprocessing.jks");
		// password of orderprocessing.jks keystore.
		System.setProperty("javax.net.ssl.keyStorePassword", "devsecurity123");

	}
}
