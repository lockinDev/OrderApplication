package com.devlockin.auth.server;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableAuthorizationServer
public class OAuthServerConfig extends AuthorizationServerConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Value("${spring.security.oauth.jwt.keystore.password}")
    private String password;

    @Value("${spring.security.oauth.jwt.keystore.name}")
    private String privateKey;

    @Value("${spring.security.oauth.jwt.keystore.alias}")
    private String alias;
	
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

    	TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
    	enhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter()));
    	
        endpoints
          .authenticationManager(authenticationManager)
          .accessTokenConverter(jwtAccessTokenConverter())
          .tokenStore(tokenStore())
          .tokenEnhancer(enhancerChain);
    }
    
    

	public OAuthServerConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("orderprocessingapp")
				.secret(passwordEncoder.encode("orderprocessingappsecret"))
				.authorizedGrantTypes("client_credentials", "password").scopes("read", "write")
				.accessTokenValiditySeconds(36000).resourceIds("sample-oauth").
				
				and().withClient("orderprocessingservice")
				.secret(passwordEncoder.encode("orderprocessingservicesecret"))
				.authorizedGrantTypes("client_credentials", "password").scopes("read").accessTokenValiditySeconds(1)
				.resourceIds("sample-oauth1")
				
				.and().withClient("orderprocessingtest")
				.secret(passwordEncoder.encode("orderprocessingservicesauth"))
				.authorizedGrantTypes( "authorization_code", "refresh_token")
				.scopes("read", "write").accessTokenValiditySeconds(36000)
				.refreshTokenValiditySeconds(76000)				
				.redirectUris("http://localhost:9090/home")
				.resourceIds("auth-oauth2")
				
				;
	}

	@Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}
	
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
    	JwtAccessTokenConverter converter;

        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(
                        new ClassPathResource(privateKey),
                        password.toCharArray());
        
        converter = new CustomJWTEncoder(keyStoreKeyFactory.getKeyPair(alias));

        return converter;
    }
    
    
		
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }	

}
