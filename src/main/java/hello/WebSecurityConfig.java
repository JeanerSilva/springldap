package hello;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Value("${app.ldap.url}")
		String ldapUrl;
		@Value("${app.ldap.port}")
		String ldapPort;	
		
		@Value("${app.auth.ldap.base-dn}")
		String baseDn;
		@Value("${app.auth.ldap.userDnPatterns}")
		String userDnPatterns;
		@Value("${app.auth.ldap.groupSearchBase}")
		String groupSearchBase;
		
		@Value("${app.auth.ldap.groupSearchFilter}")
		String groupSearchFilter;
		
		@Value("${app.auth.ldap.userSearchBase}")
		String userSearchBase;
		
		
		@Value("${app.auth.ldap.userSearchFilter}")
		String userSearchFilter;
		
		@Value("${app.auth.ldap.passwordAttribute}")
		String passwordAttribute;
	
		@Value("${app.auth.ldap.groupRoleAttribute}")
		String groupRoleAttribute;
		
		@Value("${app.auth.ldap.dgerole}")
		String dgeRole;
		@Value("${app.auth.ldap.coliconrole}")
		String coliconRole;
		@Value("${app.auth.ldap.systemrole}")
		String systemRole;
		

		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/colicon").hasRole(coliconRole)	//"admincolicon
				.antMatchers("/dge").hasRole(dgeRole)			//"admindge"
				.antMatchers("/system").hasAnyRole("system","manager","developer","admin","admindge","admincolicon")	//"system"
				.anyRequest().fullyAuthenticated()
			.and()				
				.formLogin()
			.and()
				.logout()
			.and()
				.exceptionHandling().accessDeniedPage("/acessonegado");	
		
		

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.ldapAuthentication()
				.groupRoleAttribute(groupRoleAttribute)
				.rolePrefix("none")
				.userSearchFilter(userSearchFilter)
				.groupSearchFilter(groupSearchFilter)
				.userDnPatterns(userDnPatterns)
				.userSearchBase(userSearchBase)
				.groupSearchBase(groupSearchBase)
				.contextSource()
				.url(ldapUrl + ":" + ldapPort + "/" + baseDn)
				.and()
				.passwordCompare()
					.passwordEncoder(new LdapShaPasswordEncoder())
					.passwordAttribute(passwordAttribute);
	}
	
	   @Bean
	    public DefaultSpringSecurityContextSource contextSource() {
	        return  new DefaultSpringSecurityContextSource(
	        		 Collections.singletonList(ldapUrl + ":" + ldapPort), baseDn);
	    }
	

}
