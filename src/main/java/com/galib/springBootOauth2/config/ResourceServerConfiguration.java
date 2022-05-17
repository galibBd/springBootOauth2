package com.galib.springBootOauth2.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

	
	@Override
	  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
	        //resources.resourceId("my-resource-name");
	        resources.resourceId(null);
	  }
	
	
	@Override
    public void configure(final HttpSecurity http) throws Exception {
		
        http
        .headers()
            .frameOptions()
            .disable()
        .and()
            .authorizeRequests()
            .antMatchers("/oauth/token").permitAll()
           .antMatchers("/oauth/token/logout").permitAll()
            //.antMatchers("/acl/**").hasRole("SUPERADMIN")
            //.antMatchers(HttpMethod.POST, "/api/**").access("#oauth2.hasScope('WRITE')")
            //.antMatchers("student/**").hasRole("operator")
            //.antMatchers(HttpMethod.POST,"/student/**").hasAuthority("create_profile")
            //.antMatchers(HttpMethod.POST,"/user/**").hasRole("SUPERADMIN")
            .antMatchers("/acl/**").hasAnyRole("ADMIN")
    		.anyRequest().authenticated()
    	.and()
    		.csrf().disable();
            //.antMatchers("/acl/**").authenticated();
            //.anyRequest().access("#oauth2.hasScope('READ')");
            }
       
	
//        @Override
//        public void configure(final HttpSecurity http) throws Exception {
//        	
//    	  	System.out.println("### Start Oauth Resource Server Configuration ###");
//    	  	System.out.println("SECURED_PATTERN :: "+environment.getProperty("SECURED_PATTERN"));
//    	  	System.out.println("SECURED_WRITE_SCOPE ::"+ environment.getProperty("SECURED_WRITE_SCOPE")+" ||  SECURED_READ_SCOPE  :: "+environment.getProperty("SECURED_READ_SCOPE"));
//            
//    	  	
//    	  		http
//            		.requestMatchers()
//                    .antMatchers(environment.getProperty("SECURED_PATTERN"))  //SECURED_PATTERN = "/api/**" 
//                    .and().authorizeRequests()   
//                    .antMatchers(HttpMethod.POST, "${SECURED_PATTERN}").access(environment.getProperty("SECURED_WRITE_SCOPE"))
//                    .anyRequest().access(environment.getProperty("SECURED_READ_SCOPE"));
//        }

	
	
	 
}
