package org.springframework.web.reactive.function.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilderFactory;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

class DefaultWebClient implements WebClient {

    private static final String URI_TEMPLATE_ATTRIBUTE = WebClient.class.getName() + ".uriTemplate";

    private static final Mono<ClientResponse> NO_HTTP_CLIENT_RESPONSE_ERROR = Mono.error(
            () -> new IllegalStateException("The underlying HTTP client completed without emitting a response."));


    private final ExchangeFunction exchangeFunction;

    private final UriBuilderFactory uriBuilderFactory;

    @Nullable
    private final HttpHeaders defaultHeaders;

    @Nullable
    private final MultiValueMap<String, String> defaultCookies;

    @Nullable
    private final Consumer<RequestHeadersSpec<?>> defaultRequest;

    private final DefaultWebClientBuilder builder;


    DefaultWebClient(ExchangeFunction exchangeFunction, UriBuilderFactory uriBuilderFactory,
                     @Nullable HttpHeaders defaultHeaders, @Nullable MultiValueMap<String, String> defaultCookies,
                     @Nullable Consumer<RequestHeadersSpec<?>> defaultRequest, DefaultWebClientBuilder builder) {

        this.exchangeFunction = exchangeFunction;
        this.uriBuilderFactory = uriBuilderFactory;
        this.defaultHeaders = defaultHeaders;
        this.defaultCookies = defaultCookies;
        this.defaultRequest = defaultRequest;
        this.builder = builder;
    }

    @Override
    public RequestHeadersUriSpec<?> get() {
        throw new Error();
    }

    @Override
    public RequestHeadersUriSpec<?> head() {
        throw new Error();
    }

    @Override
    public RequestBodyUriSpec post() {
        throw new Error();
    }

    @Override
    public RequestBodyUriSpec put() {
        throw new Error();
    }

    @Override
    public RequestBodyUriSpec patch() {
        throw new Error();
    }

    @Override
    public RequestHeadersUriSpec<?> delete() {
        throw new Error();
    }

    @Override
    public RequestHeadersUriSpec<?> options() {
        throw new Error();
    }

    @Override
    public RequestBodyUriSpec method(HttpMethod method) {
        throw new Error();
    }

    @Override
    public Builder mutate() {
        throw new Error();
    }
}
