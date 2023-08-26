package org.springframework.web.reactive.function.client;

import org.reactivestreams.Publisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
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
        throw new Error();
    }
}
