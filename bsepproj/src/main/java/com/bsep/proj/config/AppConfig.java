package com.bsep.proj.config;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.bsep.proj.aspects.InterceptorAspect;

@Configuration
@ComponentScan(basePackages = "com.bsep.proj")
@EnableAspectJAutoProxy
public class AppConfig {
	
	/*@Bean
	public InterceptorAspect interceptorBankaAspect() {
		InterceptorAspect aspect = Aspects.aspectOf(InterceptorAspect.class);
	    return aspect;
	}*/

}
