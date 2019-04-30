package hello;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class appConfig {

    @GetMapping("/appconfig")
    public String index() {
    	String message = "Welcome to the APPCONFIG page!";
        return message;
    }
    


    
}
