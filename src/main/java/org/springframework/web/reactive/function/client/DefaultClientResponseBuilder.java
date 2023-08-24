package org.springframework.web.reactive.function.client;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;

final class DefaultClientResponseBuilder implements ClientResponse.Builder {
    public DefaultClientResponseBuilder(ClientResponse clientResponse, boolean b) {
    }

    @Override
    public ClientResponse.Builder statusCode(HttpStatus statusCode) {
        throw new Error();
    }

    @Override
    public ClientResponse.Builder rawStatusCode(int statusCode) {
        throw new Error();
    }

    @Override
    public ClientResponse.Builder header(String headerName, String... headerValues) {
        throw new Error();
    }

    @Override
    public ClientResponse.Builder headers(Consumer<HttpHeaders> headersConsumer) {
        throw new Error();
    }

    @Override
    public ClientResponse.Builder cookie(String name, String... values) {
        throw new Error();
    }

    @Override
    public ClientResponse.Builder cookies(Consumer<MultiValueMap<String, ResponseCookie>> cookiesConsumer) {
        throw new Error();
    }

    @Override
    public ClientResponse.Builder body(Function<Flux<DataBuffer>, Flux<DataBuffer>> transformer) {
        throw new Error();
    }

    @Override
    public ClientResponse.Builder body(Flux<DataBuffer> body) {
        throw new Error();
    }

    @Override
    public ClientResponse.Builder body(String body) {
        throw new Error();
    }

    @Override
    public ClientResponse.Builder request(HttpRequest request) {
        throw new Error();
    }

    @Override
    public ClientResponse build() {
        throw new Error();
    }
}
