package com.ysyesilyurt.Rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestApiResponseBody<T> {

	private LocalDateTime timestamp;
	private String message;
	private String path;
	private T data;
	private RestApiError[] errors;
}
