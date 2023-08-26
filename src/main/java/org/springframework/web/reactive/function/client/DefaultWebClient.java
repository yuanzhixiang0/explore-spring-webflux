package org.springframework.web.reactive.function.client;

import org.reactivestreams.Publisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.*;

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
        return methodInternal(HttpMethod.GET);
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

    private RequestBodyUriSpec methodInternal(HttpMethod httpMethod) {
        return new DefaultRequestBodyUriSpec(httpMethod);
    }

    private class DefaultRequestBodyUriSpec implements RequestBodyUriSpec {

        private final HttpMethod httpMethod;

        @Nullable
        private URI uri;

        @Nullable
        private HttpHeaders headers;

        @Nullable
        private MultiValueMap<String, String> cookies;

        @Nullable
        private BodyInserter<?, ? super ClientHttpRequest> inserter;

        private final Map<String, Object> attributes = new LinkedHashMap<>(4);

        @Nullable
        private Function<Context, Context> contextModifier;

        @Nullable
        private Consumer<ClientHttpRequest> httpRequestConsumer;

        DefaultRequestBodyUriSpec(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }

        @Override
        public RequestBodySpec uri(URI uri) {
            this.uri = uri;
            return this;
        }

        @Override
        public RequestBodySpec uri(String uriTemplate, Object... uriVariables) {
            attribute(URI_TEMPLATE_ATTRIBUTE, uriTemplate);
            return uri(uriBuilderFactory.expand(uriTemplate, uriVariables));
        }

        @Override
        public RequestBodySpec uri(String uri, Map<String, ?> uriVariables) {
            throw new Error();
        }

        @Override
        public RequestBodySpec uri(String uri, Function<UriBuilder, URI> uriFunction) {
            throw new Error();
        }

        @Override
        public RequestBodySpec uri(Function<UriBuilder, URI> uriFunction) {
            throw new Error();
        }

        @Override
        public RequestBodySpec accept(MediaType... acceptableMediaTypes) {
            throw new Error();
        }

        @Override
        public RequestBodySpec acceptCharset(Charset... acceptableCharsets) {
            throw new Error();
        }

        @Override
        public RequestBodySpec cookie(String name, String value) {
            throw new Error();
        }

        @Override
        public RequestBodySpec cookies(Consumer<MultiValueMap<String, String>> cookiesConsumer) {
            throw new Error();
        }

        @Override
        public RequestBodySpec ifModifiedSince(ZonedDateTime ifModifiedSince) {
            throw new Error();
        }

        @Override
        public RequestBodySpec ifNoneMatch(String... ifNoneMatches) {
            throw new Error();
        }

        @Override
        public RequestBodySpec header(String headerName, String... headerValues) {
            throw new Error();
        }

        @Override
        public RequestBodySpec headers(Consumer<HttpHeaders> headersConsumer) {
            throw new Error();
        }

        @Override
        public RequestBodySpec attribute(String name, Object value) {
            this.attributes.put(name, value);
            return this;
        }

        @Override
        public RequestBodySpec attributes(Consumer<Map<String, Object>> attributesConsumer) {
            throw new Error();
        }

        @Override
        public RequestBodySpec context(Function<Context, Context> contextModifier) {
            throw new Error();
        }

        @Override
        public RequestBodySpec httpRequest(Consumer<ClientHttpRequest> requestConsumer) {
            throw new Error();
        }

        @Override
        public ResponseSpec retrieve() {
            return new DefaultResponseSpec(exchange(), this::createRequest);
        }

        private HttpRequest createRequest() {
            return new HttpRequest() {
                private final URI uri = initUri();
                private final HttpHeaders headers = initHeaders();

                @Override
                public HttpMethod getMethod() {
                    return httpMethod;
                }

                @Override
                public String getMethodValue() {
                    return httpMethod.name();
                }

                @Override
                public URI getURI() {
                    return this.uri;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return this.headers;
                }
            };
        }

        private URI initUri() {
            return (this.uri != null ? this.uri : uriBuilderFactory.expand(""));
        }

        private HttpHeaders initHeaders() {
            if (CollectionUtils.isEmpty(this.headers)) {
                return (defaultHeaders != null ? defaultHeaders : new HttpHeaders());
            } else if (CollectionUtils.isEmpty(defaultHeaders)) {
                return this.headers;
            } else {
                HttpHeaders result = new HttpHeaders();
                result.putAll(defaultHeaders);
                result.putAll(this.headers);
                return result;
            }
        }

        @Override
        public <V> Mono<V> exchangeToMono(Function<ClientResponse, ? extends Mono<V>> responseHandler) {
            throw new Error();
        }

        @Override
        public <V> Flux<V> exchangeToFlux(Function<ClientResponse, ? extends Flux<V>> responseHandler) {
            throw new Error();
        }

        @Override
        public Mono<ClientResponse> exchange() {
            throw new Error();
        }

        @Override
        public RequestBodySpec contentLength(long contentLength) {
            throw new Error();
        }

        @Override
        public RequestBodySpec contentType(MediaType contentType) {
            throw new Error();
        }

        @Override
        public RequestHeadersSpec<?> bodyValue(Object body) {
            throw new Error();
        }

        @Override
        public <T, P extends Publisher<T>> RequestHeadersSpec<?> body(P publisher, Class<T> elementClass) {
            throw new Error();
        }

        @Override
        public <T, P extends Publisher<T>> RequestHeadersSpec<?> body(P publisher, ParameterizedTypeReference<T> elementTypeRef) {
            throw new Error();
        }

        @Override
        public RequestHeadersSpec<?> body(Object producer, Class<?> elementClass) {
            throw new Error();
        }

        @Override
        public RequestHeadersSpec<?> body(Object producer, ParameterizedTypeReference<?> elementTypeRef) {
            throw new Error();
        }

        @Override
        public RequestHeadersSpec<?> body(BodyInserter<?, ? super ClientHttpRequest> inserter) {
            throw new Error();
        }

        @Override
        public RequestHeadersSpec<?> syncBody(Object body) {
            throw new Error();
        }
    }

    private static class DefaultResponseSpec implements ResponseSpec {

        private static final IntPredicate STATUS_CODE_ERROR = (value -> value >= 400);

        private static final StatusHandler DEFAULT_STATUS_HANDLER =
                new StatusHandler(STATUS_CODE_ERROR, ClientResponse::createException);

        private final Mono<ClientResponse> responseMono;

        private final Supplier<HttpRequest> requestSupplier;

        private final List<StatusHandler> statusHandlers = new ArrayList<>(1);


        DefaultResponseSpec(Mono<ClientResponse> responseMono, Supplier<HttpRequest> requestSupplier) {
            this.responseMono = responseMono;
            this.requestSupplier = requestSupplier;
            this.statusHandlers.add(DEFAULT_STATUS_HANDLER);
        }

        @Override
        public ResponseSpec onStatus(Predicate<HttpStatus> statusPredicate, Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction) {
            throw new Error();
        }

        @Override
        public ResponseSpec onRawStatus(IntPredicate statusCodePredicate, Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction) {
            throw new Error();
        }

        @Override
        public <T> Mono<T> bodyToMono(Class<T> elementClass) {
            throw new Error();
        }

        @Override
        public <T> Mono<T> bodyToMono(ParameterizedTypeReference<T> elementTypeRef) {
            throw new Error();
        }

        @Override
        public <T> Flux<T> bodyToFlux(Class<T> elementClass) {
            throw new Error();
        }

        @Override
        public <T> Flux<T> bodyToFlux(ParameterizedTypeReference<T> elementTypeRef) {
            throw new Error();
        }

        @Override
        public <T> Mono<ResponseEntity<T>> toEntity(Class<T> bodyClass) {
            throw new Error();
        }

        @Override
        public <T> Mono<ResponseEntity<T>> toEntity(ParameterizedTypeReference<T> bodyTypeReference) {
            throw new Error();
        }

        @Override
        public <T> Mono<ResponseEntity<List<T>>> toEntityList(Class<T> elementClass) {
            throw new Error();
        }

        @Override
        public <T> Mono<ResponseEntity<List<T>>> toEntityList(ParameterizedTypeReference<T> elementTypeRef) {
            throw new Error();
        }

        @Override
        public <T> Mono<ResponseEntity<Flux<T>>> toEntityFlux(Class<T> elementType) {
            throw new Error();
        }

        @Override
        public <T> Mono<ResponseEntity<Flux<T>>> toEntityFlux(ParameterizedTypeReference<T> elementTypeReference) {
            throw new Error();
        }

        @Override
        public <T> Mono<ResponseEntity<Flux<T>>> toEntityFlux(BodyExtractor<Flux<T>, ? super ClientHttpResponse> bodyExtractor) {
            throw new Error();
        }

        @Override
        public Mono<ResponseEntity<Void>> toBodilessEntity() {
            throw new Error();
        }

        private static class StatusHandler {

            private final IntPredicate predicate;

            private final Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction;

            public StatusHandler(IntPredicate predicate,
                                 Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction) {

                this.predicate = predicate;
                this.exceptionFunction = exceptionFunction;
            }

            public boolean test(int status) {
                return this.predicate.test(status);
            }

            public Mono<? extends Throwable> apply(ClientResponse response) {
                return this.exceptionFunction.apply(response);
            }
        }
    }
}
