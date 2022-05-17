package com.galib.springBootOauth2.config;

import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;

public class CustomTokenEnhancer implements TokenEnhancer {
	  
	  
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        
   	final Map<String, Object> additionalInfo = new HashMap<String, Object>();
        
//   	  MyUserDetails myUserDetails = (MyUserDetails)authentication.getPrincipal();
//     	  Long sessionNo = sessionNo(myUserDetails.getOrganizationNo(), myUserDetails.getCompanyNo());
     	
//        String remoteIpAddress1 =	userAgent().getRemoteAddress();
//        String clientIpAdress = userAgent().getClientIpAddress();
//        String macAdress = userAgent().getMacAddress();
//        String osName =	userAgent().getOs();
//        String browserName =	userAgent().getBrowser();
        
//        System.out.println("clientIpAdress "+ clientIpAdress);
//		  System.out.println(remoteIpAddress1);
//        System.out.println(osName);
//        System.out.println(browserName);

        additionalInfo.put("name", authentication.getName());
        
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
       
//        userAccessLog(myUserDetails.getUserId(), myUserDetails.getOrganizationNo(), myUserDetails.getCompanyNo(), clientIpAdress,sessionNo,osName,browserName,macAdress);
       System.out.println("====== save login info to LOGIN in TABLE ============= : accessToken: "+accessToken);
        return accessToken;
    }
    
    
}
