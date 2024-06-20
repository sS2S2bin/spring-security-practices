package ex01.filter;

import javax.servlet.Filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import ex01.config.AppConfig;
import ex01.config.WebConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={WebConfig.class, AppConfig.class})
@WebAppConfiguration
public class MyFilterTest {
	private MockMvc mvc;
	
	@BeforeEach // test 할때마다 새로 만들어라고(실행하라고) 
	public void setUp(WebApplicationContext applicationContext) {
		Filter myFilter = applicationContext.getBean("myFilter",Filter.class);
		// 이렇게하면 나중에 고칠 수 있으니까 ㅎㅎ..
		
		mvc = MockMvcBuilders
				.webAppContextSetup(applicationContext) //이때 웹어플리케이션 같은게 만들어진다고?
				.addFilter(new DelegatingFilterProxy(myFilter), "/*") // filter, 경로 
				.build();
				
		
	}
	
	@Test
	public void testMyFilter() throws Throwable{
		mvc
			.perform(get("/hello"))
			.andExpect(status().isOk())
			.andExpect(cookie().value("MyFilter","Works"))
			.andDo(print());
	}

}
