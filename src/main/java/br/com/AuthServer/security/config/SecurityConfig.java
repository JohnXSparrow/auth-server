package br.com.AuthServer.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import br.com.AuthServer.security.service.CustomAuthenticationFailureHandler;
import br.com.AuthServer.security.service.CustomLogoutSuccessHandler;
import br.com.AuthServer.security.service.LoggingHttpFirewall;
import br.com.AuthServer.security.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired CustomLogoutSuccessHandler customLogoutSuccessHandler;

	@Autowired
	private ClientDetailsService clientDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .csrf().disable()
        .requestMatchers()
        .antMatchers( "/login", "/oauth/authorize", "/logout", "/oauth/token", "/swagger-ui.html", "/swagger")
        .and().authorizeRequests().antMatchers("/swagger-ui.html").hasAnyAuthority("AUTHSERVER_SWAGGER", "ADMIN")
        .and().authorizeRequests().antMatchers("/login").permitAll()
        .and().authorizeRequests().antMatchers("/swagger").permitAll()
        .and().authorizeRequests().anyRequest().authenticated()
        .and().formLogin()
        .loginPage("/login").permitAll()
        .failureHandler(authenticationFailureHandler())
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessHandler(customLogoutSuccessHandler)
        .deleteCookies("JSESSIONID")
        .invalidateHttpSession(true);
	}
	
	private static final String[] AUTH_WHITELIST = {
	        "/swagger-resources/**",
	        "/v2/api-docs",
	        "/webjars/**",	        
	        "/css/**",
	        "/images/**",
	        "/fonts/**",
	        "/javascripts/**",
	        "/stylesheets/**",
	};
	
	@Override
	public final void configure(final WebSecurity web) throws Exception {
		super.configure(web);
		web.httpFirewall(new LoggingHttpFirewall()).ignoring().antMatchers(AUTH_WHITELIST);
		return;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
	@Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Bean
	DefaultOAuth2RequestFactory defaultOAuth2RequestFactory() {
		return new DefaultOAuth2RequestFactory(clientDetailsService);
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsXFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	// Token DataSource

	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		return initializer;
	}

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		return dataSource;
	}

}