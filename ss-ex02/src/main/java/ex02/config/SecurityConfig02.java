package ex02.config;

import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import ex02.filter.MySecurityFilter01;
import ex02.filter.MySecurityFilter02;
import ex02.filter.MySecurityFilter03;
import ex02.filter.MySecurityFilter04;

@Configuration
public class SecurityConfig02 {
	
	
	@Bean
	// method name == id
	public FilterChainProxy springSecurityFilterChain() {
		List<SecurityFilterChain> securityFilterChain = Arrays.asList(
				new DefaultSecurityFilterChain(new AntPathRequestMatcher("/assets/**")),
				new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**"),mySecurityFilter01(),mySecurityFilter02(),mySecurityFilter03(),mySecurityFilter04() )
			);
		
		
		
		return new FilterChainProxy(securityFilterChain);
	}
	
	@Bean
	public MySecurityFilter01 mySecurityFilter01() {
		return new MySecurityFilter01();
	}
	
	@Bean
	public MySecurityFilter02 mySecurityFilter02() {
		return new MySecurityFilter02();
	}
	
	@Bean
	public MySecurityFilter03 mySecurityFilter03() {
		return new MySecurityFilter03();
	}
	
	@Bean
	public MySecurityFilter04 mySecurityFilter04() {
		return new MySecurityFilter04();
	}
}
