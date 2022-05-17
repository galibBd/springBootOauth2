package com.galib.springBootOauth2.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

@Configuration
public class AuthorizationServerConfiguration implements AuthorizationServerConfigurer{
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    @Autowired
	    private DataSource dataSource;
	    @Autowired
	    private AuthenticationManager authenticationManager;
	    @Autowired
	    private TokenStore tokenStore;

	    @Bean
	    TokenStore jdbcTokenStore() {
	        return new JdbcTokenStore(dataSource);
	    }

	    @Override
	    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	        security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");

	    }

	    @Override
	    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);

	    }

	    @Override
	    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	        endpoints.tokenStore(jdbcTokenStore());
	        endpoints.authenticationManager(authenticationManager);
	    }
	    
	    @Bean
	    public DefaultTokenServices tokenServices() {
	    	
	        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	        defaultTokenServices.setTokenStore(tokenStore);
	        defaultTokenServices.setSupportRefreshToken(true);
	        defaultTokenServices.setAuthenticationManager(authenticationManager);
	        defaultTokenServices.setTokenEnhancer(tokenEnhancer());
	       
	        return defaultTokenServices;
	    }
	     
	    
	    @Bean
	    public TokenEnhancer tokenEnhancer() {
			return new CustomTokenEnhancer();
	        
	    }
}