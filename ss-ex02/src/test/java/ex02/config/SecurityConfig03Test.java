package ex02.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.servlet.Filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={WebConfig.class, SecurityConfig03.class})
@WebAppConfiguration
public class SecurityConfig03Test {
	private MockMvc mvc;
	private FilterChainProxy filterChainProxy;
	
	@BeforeEach // test 할때마다 새로 만들어라고(실행하라고) 
	public void setUp(WebApplicationContext applicationContext) {
		Filter filterChainProxy = applicationContext.getBean("springSecurityFilterChain",FilterChainProxy.class);
		// 이렇게하면 나중에 고칠 수 있으니까 ㅎㅎ..
		
		mvc = MockMvcBuilders
				.webAppContextSetup(applicationContext) //이때 웹어플리케이션 같은게 만들어진다고?
				.addFilter(new DelegatingFilterProxy(filterChainProxy), "/*") // filter, 경로 
				.build();
				
		
	}
	
	@Test
	public void testSecurityFilterChains() {
		List<SecurityFilterChain> securityFilterChain = filterChainProxy.getFilterChains();
		assertEquals(1,securityFilterChain.size());
	}
	
	@Test
	public void testSecurityFitlers() {
		SecurityFilterChain securityFilterChain = filterChainProxy.getFilterChains().get(1);
		List<Filter> filters = securityFilterChain.getFilters();
		
		assertEquals(2,filters.size());
	}
	
	@Test
	public void testSecurityFilterChain01() throws Throwable{
		mvc.perform(get("/assets/images/logo.png"))
		.andExpect(status().isOk())
		.andExpect(cookie().value("MySecurityFilter04", "Works"));
	}
	
	@Test
	public void testSecurityFilterChain02() throws Throwable{
		mvc
			.perform(get("/hello"))
			.andExpect(status().isOk())
			.andExpect(cookie().value("MySecurityFilter04", "Works"));
	}
}
