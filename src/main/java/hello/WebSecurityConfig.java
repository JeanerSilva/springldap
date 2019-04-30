package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Value("${app.ldap.url}")
		String ldapUrl;
		@Value("${app.ldap.port}")
		String ldapPort;		
		@Value("${app.ldap.base-dn}")
		String ldapBaseDn;
		@Value("${app.ldap.userDnPatterns}")
		String ldapUserDnPatterns;
		@Value("${app.ldap.groupSearchBase}")
		String ldapGroupSearchBaseou;
		@Value("${app.ldap.passwordAttribute}")
		String ldapPasswordAttribute;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/*config*").authenticated().anyRequest().fullyAuthenticated()

			.and()				
			.formLogin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.ldapAuthentication()
				.userDnPatterns(ldapUserDnPatterns)
				.groupSearchBase(ldapGroupSearchBaseou)
				.contextSource()
				.url(ldapUrl + ":" + ldapPort + "/" + ldapBaseDn)
					.and()
				.passwordCompare()
					.passwordEncoder(new LdapShaPasswordEncoder())
					.passwordAttribute(ldapPasswordAttribute);
					
	}

}
