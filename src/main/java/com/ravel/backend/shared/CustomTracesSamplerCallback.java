package com.ravel.backend.shared;

import io.sentry.SamplingContext;
import io.sentry.SentryOptions;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomTracesSamplerCallback implements SentryOptions.TracesSamplerCallback {

	@Override
	public Double sample(SamplingContext context) {
		HttpServletRequest request = (HttpServletRequest) context
			.getCustomSamplingContext()
			.get("request");
		String url = request.getRequestURI();
		if ("/payment".equals(url)) {
			// These are important - take a big sample
			return 0.5;
		} else if ("/search".equals(url)) {
			// Search is less important and happen much more frequently - only take 1%
			return 0.01;
		} else if ("/actuator/prometheus".equals(url)) {
			// The health check endpoint is just noise - drop all transactions
			return 0d;
		} else if ("/swagger-ui.html".equals(url)) {
			// The health check endpoint is just noise - drop all transactions
			return 0d;
		} else if ("/v3/api-docs/swagger-config".equals(url)) {
			// The health check endpoint is just noise - drop all transactions
			return 0d;
		} else if ("/v3/api-docs/".equals(url)) {
			// The health check endpoint is just noise - drop all transactions
			return 0d;
		} else if ("/actuator/health".equals(url)) {
			// The health check endpoint is just noise - drop all transactions
			return 0d;
		} else {
			// Default sample rate
			return 1.0;
		}
	}
}
