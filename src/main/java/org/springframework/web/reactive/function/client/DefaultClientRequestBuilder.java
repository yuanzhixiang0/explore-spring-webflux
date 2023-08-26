package org.springframework.web.reactive.function.client;

import org.reactivestreams.Publisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.*;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Default implementation of {@link ClientRequest.Builder}.
 *
 * @author Arjen Poutsma
 * @since 5.0
 */
final class DefaultClientRequestBuilder implements ClientRequest.Builder {

    private HttpMethod method;

    private URI url;

    private final HttpHeaders headers = new HttpHeaders();

    private final MultiValueMap<String, String> cookies = new LinkedMultiValueMap<>();

    private final Map<String, Object> attributes = new LinkedHashMap<>();

    private BodyInserter<?, ? super ClientHttpRequest> body = BodyInserters.empty();

    @Nullable
    private Consumer<ClientHttpRequest> httpRequestConsumer;


    public DefaultClientRequestBuilder(ClientRequest other) {
        Assert.notNull(other, "ClientRequest must not be null");
        this.method = other.method();
        this.url = other.url();
        headers(headers -> headers.addAll(other.headers()));
        cookies(cookies -> cookies.addAll(other.cookies()));
        attributes(attributes -> attributes.putAll(other.attributes()));
        body(other.body());
        this.httpRequestConsumer = other.httpRequest();
    }

    public DefaultClientRequestBuilder(HttpMethod method, URI url) {
        Assert.notNull(method, "HttpMethod must not be null");
        Assert.notNull(url, "URI must not be null");
        this.method = method;
        this.url = url;
    }


    @Override
    public ClientRequest.Builder method(HttpMethod method) {
        throw new Error();
    }

    @Override
    public ClientRequest.Builder url(URI url) {
        throw new Error();
    }

    @Override
    public ClientRequest.Builder header(String headerName, String... headerValues) {
        throw new Error();
    }

    @Override
    public ClientRequest.Builder headers(Consumer<HttpHeaders> headersConsumer) {
        headersConsumer.accept(this.headers);
        return this;
    }

    @Override
    public ClientRequest.Builder cookie(String name, String... values) {
        throw new Error();
    }

    @Override
    public ClientRequest.Builder cookies(Consumer<MultiValueMap<String, String>> cookiesConsumer) {
        cookiesConsumer.accept(this.cookies);
        return this;
    }


    @Override
    public ClientRequest.Builder body(BodyInserter<?, ? super ClientHttpRequest> inserter) {
        throw new Error();
    }

    @Override
    public <S, P extends Publisher<S>> ClientRequest.Builder body(P publisher, Class<S> elementClass) {
        throw new Error();
    }

    @Override
    public <S, P extends Publisher<S>> ClientRequest.Builder body(P publisher, ParameterizedTypeReference<S> typeReference) {
        throw new Error();
    }

    @Override
    public ClientRequest.Builder attribute(String name, Object value) {
        throw new Error();
    }

    @Override
    public ClientRequest.Builder attributes(Consumer<Map<String, Object>> attributesConsumer) {
        attributesConsumer.accept(this.attributes);
        return this;
    }

    @Override
    public ClientRequest.Builder httpRequest(Consumer<ClientHttpRequest> requestConsumer) {
        throw new Error();
    }

    @Override
    public ClientRequest build() {
        return new BodyInserterRequest(
                this.method, this.url, this.headers, this.cookies, this.body,
                this.attributes, this.httpRequestConsumer);
    }

    private static class BodyInserterRequest implements ClientRequest {

        private final HttpMethod method;

        private final URI url;

        private final HttpHeaders headers;

        private final MultiValueMap<String, String> cookies;

        private final BodyInserter<?, ? super ClientHttpRequest> body;

        private final Map<String, Object> attributes;

        @Nullable
        private final Consumer<ClientHttpRequest> httpRequestConsumer;

        private final String logPrefix;

        public BodyInserterRequest(HttpMethod method, URI url, HttpHeaders headers,
                                   MultiValueMap<String, String> cookies, BodyInserter<?, ? super ClientHttpRequest> body,
                                   Map<String, Object> attributes, @Nullable Consumer<ClientHttpRequest> httpRequestConsumer) {

            this.method = method;
            this.url = url;
            this.headers = HttpHeaders.readOnlyHttpHeaders(headers);
            this.cookies = CollectionUtils.unmodifiableMultiValueMap(cookies);
            this.body = body;
            this.attributes = Collections.unmodifiableMap(attributes);
            this.httpRequestConsumer = httpRequestConsumer;

            Object id = attributes.computeIfAbsent(LOG_ID_ATTRIBUTE, name -> ObjectUtils.getIdentityHexString(this));
            this.logPrefix = "[" + id + "] ";
        }

        @Override
        public HttpMethod method() {
            throw new Error();
        }

        @Override
        public URI url() {
            throw new Error();
        }

        @Override
        public HttpHeaders headers() {
            throw new Error();
        }

        @Override
        public MultiValueMap<String, String> cookies() {
            throw new Error();
        }

        @Override
        public BodyInserter<?, ? super ClientHttpRequest> body() {
            throw new Error();
        }

        @Override
        public Optional<Object> attribute(String name) {
            throw new Error();
        }

        @Override
        public Map<String, Object> attributes() {
            throw new Error();
        }

        @Override
        public Consumer<ClientHttpRequest> httpRequest() {
            throw new Error();
        }

        @Override
        public String logPrefix() {
            throw new Error();
        }

        @Override
        public Mono<Void> writeTo(ClientHttpRequest request, ExchangeStrategies strategies) {
            throw new Error();
        }
    }
}
