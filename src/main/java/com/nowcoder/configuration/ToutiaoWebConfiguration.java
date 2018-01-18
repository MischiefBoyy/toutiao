package com.nowcoder.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.nowcoder.interceptor.LoginInterceptor;
import com.nowcoder.interceptor.PassInterceptor;

@Component
public class ToutiaoWebConfiguration extends WebMvcConfigurerAdapter{
	@Autowired
	private PassInterceptor passInterceptor;
	@Autowired
	private LoginInterceptor loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(passInterceptor);
		registry.addInterceptor(loginInterceptor).addPathPatterns("/msg/*");
		super.addInterceptors(registry);
	}
	
	
}
