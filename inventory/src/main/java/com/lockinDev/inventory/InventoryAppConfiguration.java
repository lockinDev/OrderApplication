package com.lockinDev.inventory;



import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class InventoryAppConfiguration implements EnvironmentAware {

	@Override
	public void setEnvironment(final Environment environment) {
		// points to the path where inventory.jks keystore is.
		System.setProperty("javax.net.ssl.trustStore", "inventory.jks");
		// password of inventory.jks keystore.
		System.setProperty("javax.net.ssl.trustStorePassword", "devsecurity123");
		


	}
}
