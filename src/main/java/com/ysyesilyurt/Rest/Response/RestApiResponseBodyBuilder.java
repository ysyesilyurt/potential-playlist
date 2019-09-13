package com.ysyesilyurt.Rest.Response;

import com.ysyesilyurt.Rest.RestApiError;
import com.ysyesilyurt.Rest.RestApiResponseBody;
import com.ysyesilyurt.Rest.RequestContextResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collection;


@Component
public class RestApiResponseBodyBuilder {

	@Autowired
	private RequestContextResolver requestContextResolver;

	public RestApiResponseBody<String> successBody() {
		return new RestApiResponseBody<>(
				this.requestContextResolver.clientTime(),
				"success",
				this.requestContextResolver.request().getServletPath(),
				null,
				null);
	}

	public <T> RestApiResponseBody<Resource<T>> successBody(T data, Link... links) {
		return new RestApiResponseBody<>(
				this.requestContextResolver.clientTime(),
				"success",
				this.requestContextResolver.request().getServletPath(),
				this.resource(data, links),
				null);
	}

	public <T> RestApiResponseBody<Resources<T>> successCollectionBody(Collection<T> data, Link... links) {
		return new RestApiResponseBody<>(
				this.requestContextResolver.clientTime(),
				"success",
				this.requestContextResolver.request().getServletPath(),
				this.resources(data, links),
				null);
	}

	public <T> RestApiResponseBody<T> errorBody(String errorMessage) {
		return (RestApiResponseBody<T>) RestApiResponseBody.builder()
				.data(null)
				.path(this.requestContextResolver.request().getServletPath())
				.timestamp(this.requestContextResolver.clientTime())
				.errors(new RestApiError[] {
						RestApiError.builder()
								.message(errorMessage)
								.build() })
				.message(errorMessage).build();
	}

	public RestApiResponseBody<Object> errorBody(MethodArgumentTypeMismatchException exception) {
		return RestApiResponseBody.builder()
				.data(null)
				.path(this.requestContextResolver.request().getServletPath())
				.timestamp(this.requestContextResolver.clientTime())
				.message("Argument Type Mismatch error")
				.errors(new RestApiError[] {
						RestApiError.builder()
								.title(exception.getParameter().getParameterName())
								.message(exception.getMessage())
								.build() })
				.build();
	}

	public RestApiResponseBody<Object> errorBody(MissingServletRequestParameterException exception) {
		return RestApiResponseBody.builder()
				.data(null)
				.path(this.requestContextResolver.request().getServletPath())
				.timestamp(this.requestContextResolver.clientTime())
				.message("Missing Servlet Request Parameter error")
				.errors(new RestApiError[] {
						RestApiError.builder()
								.title(exception.getParameterName())
								.message(exception.getMessage())
								.build() })
				.build();
	}

	protected <T> Resource<T> resource(T data, Link... links) {
		return new Resource<>(data, links);
	}

	protected <T> Resources<T> resources(Collection<T> data, Link... links) {
		return new Resources<>(data, links);
	}
}
