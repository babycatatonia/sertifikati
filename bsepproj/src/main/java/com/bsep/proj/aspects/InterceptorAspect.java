package com.bsep.proj.aspects;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bsep.proj.annotations.InterceptorAnnotation;
import com.bsep.proj.iservices.IPrivilegijaService;
import com.bsep.proj.model.users.Korisnik;

@Aspect
public class InterceptorAspect{

	@Autowired
	public IPrivilegijaService privilegijaService;
	
	private HttpServletResponse response;	
	
	@Around("execution(@com.bsep.proj.annotations.InterceptorAnnotation * *(..)) && @annotation(interceptorAnnotation)")
	public Object aspMeth(ProceedingJoinPoint joinPoint, InterceptorAnnotation interceptorAnnotation) throws Throwable{
		
		Object returnObject = null;
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		Korisnik korisnik = (Korisnik) attr.getRequest().getSession().getAttribute("ulogovanKorisnik");

		if(!privilegijaService.getByRole(korisnik.getRola()).contains(privilegijaService.getByNaziv(interceptorAnnotation.value()))){
			response.sendError(401, "Unauthorized request");			
		}else
			returnObject = joinPoint.proceed();
					
		return returnObject;
	}
}
