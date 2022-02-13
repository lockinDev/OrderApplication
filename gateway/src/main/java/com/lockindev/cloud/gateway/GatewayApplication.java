package com.lockindev.cloud.gateway;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import com.lockindev.cloud.gateway.filter.OAuthFilter;
import com.lockindev.cloud.gateway.filter.ThrottlingFilter;

@EnableZuulProxy
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class GatewayApplication {
	
	static {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
	}
	


    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /*@Bean
    public OAuthFilter oAuthFilter(){
        return new OAuthFilter();
    }*/

    /*@Bean
    public ThrottlingFilter throttlingFilter() {
        return new ThrottlingFilter();
    }*/
    
}
