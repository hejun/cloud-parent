package io.github.hejun.cloud.auth.filter;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.ServerHttpObservationFilter;

/**
 * 链路追踪前端输出Filter
 *
 * @author HeJun
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class TraceHeaderObservationFilter extends ServerHttpObservationFilter {

	private static final String TRACE_ID_HEADER_NAME = "X-Trace-Id";
	private final Tracer tracer;

	public TraceHeaderObservationFilter(Tracer tracer, ObservationRegistry observationRegistry) {
		super(observationRegistry);
		this.tracer = tracer;
	}

	@Override
	protected void onScopeOpened(Observation.Scope scope, HttpServletRequest request, HttpServletResponse response) {
		Span currentSpan = this.tracer.currentSpan();
		if (currentSpan != null) {
			response.setHeader(TRACE_ID_HEADER_NAME, currentSpan.context().traceId());
		}
	}

}
