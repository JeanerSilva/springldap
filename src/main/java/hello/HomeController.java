package hello;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
	
	public static User getPrincipal() {
		return (User) ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
	}

    @RequestMapping("/")
    public String index(HttpSession session, 
    					//@RequestBody String body, 
    					@RequestHeader HttpHeaders headers) {
    	String message = "Welcome to the home page!\n\n";
    	message += "session.getAttributeNames(:" + session.getAttributeNames().toString() +"<br /><br />"; 
    	
    	
    	message += "headers.get(\"cookie\"): " + headers.get("cookie") +"<br /><br />";
    	
    	message += "headers: " + headers + "<br /><br />";
    	
    	//System.out.println("getPrincipal().getUsername().toString(): " + (String) getPrincipal().getUsername().toString());	
    	
    	  message += "<br /><br />Getting UsernamePasswordAuthenticationToken from SecurityContextHolder";
           UsernamePasswordAuthenticationToken authentication =
                   (UsernamePasswordAuthenticationToken)
                           SecurityContextHolder.getContext().getAuthentication();

           message +="<br /><br />Getting principal from UsernamePasswordAuthenticationToken";
           LdapUserDetailsImpl principal = (LdapUserDetailsImpl) authentication.getPrincipal();

           message +="<br /><br />authentication: " + authentication;
           message +="<br /><br />principal: " + principal;

           return message;
    }
    
    @GetMapping("/liberado")
    public String liberado(HttpSession session) {
    	String message = "Welcome to the Liberado page!";
        return message;
    }
    
    
    @GetMapping("/acessonegado")
    public String acessonegado() {
    	String message = "Acesso negado! <a href='/logout'> Logout </p>";
        return message;
    }

    @GetMapping("/dge")
    public String dge() {
    	String message = "Welcome to the DGE page! <a href='/logout'> Logout </p>";
        return message;
    }
    
    @GetMapping("/colicon")
    public String colicon() {
    	String message = "Welcome to the COLICON page!  <a href='/logout'> Logout </p>";
        return message;
    }
    
    
    @GetMapping("/system")
    public String system() {
    	String message = "Welcome to the SYSTEM page! <a href='/logout'> Logout </p>";
        return message;
    }
    
    @GetMapping("/logout")
    public String logout() {
    	String message = "Logout concluído!";
        return message;
    }
    
}
