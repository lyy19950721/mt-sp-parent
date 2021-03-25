package com.mayikt.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MayiktGatewayFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取nginx中的参数   请求来源IP
        String sourceIp = exchange.getRequest().getHeaders().getFirst("X-Real-IP");
        ServerHttpResponse response = exchange.getResponse();
        if (sourceIp == null) {
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "500");
            jsonObject.put("msg", "sourceIp is null");
            DataBuffer buffer = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
            return response.writeWith(Mono.just(buffer));
        }
        response.getHeaders().set("Access-Control-Allow-Origin","*");

        // 放行请求
        return chain.filter(exchange);
    }
}
