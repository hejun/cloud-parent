package io.github.hejun.cloud.gateway.filter;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 链路追踪前端输出Filter
 *
 * @author HeJun
 */
@Order
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TraceHeaderObservationFilter implements GlobalFilter {

	private static final String TRACE_ID_HEADER_NAME = "X-Trace-Id";

	private final Tracer tracer;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		return chain.filter(exchange).then(Mono.fromRunnable(() -> this.addHeader(exchange)));
	}

	void addHeader(ServerWebExchange exchange) {
		if (!exchange.getResponse().isCommitted()) {
			Span currentSpan = this.tracer.currentSpan();
			if (currentSpan != null) {
				exchange.getResponse().getHeaders().add(TRACE_ID_HEADER_NAME, currentSpan.context().traceId());
			}
		}
	}

}
