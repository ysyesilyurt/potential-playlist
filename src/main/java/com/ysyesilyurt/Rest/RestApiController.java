package com.ysyesilyurt.Rest;

import com.ysyesilyurt.Rest.Response.RestApiResponseBodyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.TimeZone;


public class RestApiController {

	/*
		RequestContextResolver is context holder for request-specific state, like current web application context,
		current locale, current theme, and potential binding errors. It provides easy access to localized messages
		and Errors instances.
	 */

	@Autowired
	protected RequestContextResolver contextResolver;

	@Autowired
	protected RestApiResponseBodyBuilder responseBodyBuilder;

/*
	https://www.javacodegeeks.com/2013/06/spring-mvc-validator-and-initbinder.html

	If I were to wish to validate requests that comes to my endpoints (in my Controllers) I could use
	inject a Validator (I could also create a custom validator class that extends this) and set
	InitBinder's validator with this injected bean.

	@Autowired
	protected Validator validator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
*/

	public <T> ResponseEntity<RestApiResponseBody<Resource<T>>> success(T data, Link... links) {
		return ResponseEntity.ok().body(this.responseBodyBuilder.successBody(data, links));
	}

	public <T> ResponseEntity<RestApiResponseBody<Resources<T>>> successCollection(Collection<T> data, Link... links) {
		return ResponseEntity.ok().body(this.responseBodyBuilder.successCollectionBody(data, links));
	}

	public ResponseEntity<RestApiResponseBody<?>> success() {
		return ResponseEntity.ok().body(this.responseBodyBuilder.successBody());
	}

	protected HttpServletRequest request() {
		return this.contextResolver.request();
	}

	protected HttpServletResponse response() {
		return this.contextResolver.response();
	}

	protected TimeZone timeZone() {
		return this.contextResolver.timeZone();
	}
}
