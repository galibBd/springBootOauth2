package com.galib.springBootOauth2.config;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Galib
 *
 */
@CrossOrigin(origins="*")
@FrameworkEndpoint
@RestController
@RequestMapping("/oauth/token/logout")
public class RevokeTokenEndpoint {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource(name = "tokenServices")
    ConsumerTokenServices tokenServices;
    
    @Autowired
	private TokenStore tokenStore;

    /**
     * @param request
     * @return
     */
    @DeleteMapping
    @ResponseBody
    public String revokeToken(HttpServletRequest request, OAuth2Authentication authentication) {

    	Object sessionNoObj = null;
    	Long sessiongLong = null;
        Boolean revokeFlag = false;
//        MyUserDetails myUserDetails = null;
        
//        CustomWebAuthenticationDeatils authenticationDeatils  = new CustomWebAuthenticationDeatils(request);
        
        String authorization = request.getHeader("Authorization");
        
        if (authorization != null && authorization.contains("Bearer ")) {
        	
            String tokenId = authorization.substring("Bearer".length() + 1);
          
            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(tokenId);
          
           if(null != oAuth2AccessToken) {

        	   sessionNoObj = oAuth2AccessToken.getAdditionalInformation().get("sessionNo");
        	
        	   if(null !=sessionNoObj) {

        		   sessiongLong = Long.valueOf(sessionNoObj.toString()); 
        	   }
        	
           }

            revokeFlag = tokenServices.revokeToken(tokenId);
            System.out.println("access token : "+tokenId+ " revokeFlag : "+revokeFlag +"=================== LOG OUT ==========");
            
//            if(revokeFlag) {
//            	
//            	if(null !=sessiongLong) {
//            	    userNormalLogOut(sessiongLong);	
//            	}
//           
//         	   return getSuccessResponse("User Revoke Successfull"); 
//           }
   
        }
        
         return "User Revoke";  
    }

}
