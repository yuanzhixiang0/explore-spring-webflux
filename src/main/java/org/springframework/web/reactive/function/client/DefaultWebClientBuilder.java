package org.springframework.web.reactive.function.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.util.ClassUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilderFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

final class DefaultWebClientBuilder implements WebClient.Builder {

    private static final boolean reactorClientPresent;

    private static final boolean jettyClientPresent;

    private static final boolean httpComponentsClientPresent;

    static {
        ClassLoader loader = DefaultWebClientBuilder.class.getClassLoader();
        reactorClientPresent = ClassUtils.isPresent("reactor.netty.http.client.HttpClient", loader);
        jettyClientPresent = ClassUtils.isPresent("org.eclipse.jetty.client.HttpClient", loader);
        httpComponentsClientPresent =
                ClassUtils.isPresent("org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient", loader) &&
                        ClassUtils.isPresent("org.apache.hc.core5.reactive.ReactiveDataConsumer", loader);
    }

    @Override
    public WebClient.Builder baseUrl(String baseUrl) {
        throw new Error();
    }

    @Override
    public WebClient.Builder defaultUriVariables(Map<String, ?> defaultUriVariables) {
        throw new Error();
    }

    @Override
    public WebClient.Builder uriBuilderFactory(UriBuilderFactory uriBuilderFactory) {
        throw new Error();
    }

    @Override
    public WebClient.Builder defaultHeader(String header, String... values) {
        throw new Error();
    }

    @Override
    public WebClient.Builder defaultHeaders(Consumer<HttpHeaders> headersConsumer) {
        throw new Error();
    }

    @Override
    public WebClient.Builder defaultCookie(String cookie, String... values) {
        throw new Error();
    }

    @Override
    public WebClient.Builder defaultCookies(Consumer<MultiValueMap<String, String>> cookiesConsumer) {
        throw new Error();
    }

    @Override
    public WebClient.Builder defaultRequest(Consumer<WebClient.RequestHeadersSpec<?>> defaultRequest) {
        throw new Error();
    }

    @Override
    public WebClient.Builder filter(ExchangeFilterFunction filter) {
        throw new Error();
    }

    @Override
    public WebClient.Builder filters(Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        throw new Error();
    }

    @Override
    public WebClient.Builder clientConnector(ClientHttpConnector connector) {
        throw new Error();
    }

    @Override
    public WebClient.Builder codecs(Consumer<ClientCodecConfigurer> configurer) {
        throw new Error();
    }

    @Override
    public WebClient.Builder exchangeStrategies(ExchangeStrategies strategies) {
        throw new Error();
    }

    @Override
    public WebClient.Builder exchangeStrategies(Consumer<ExchangeStrategies.Builder> configurer) {
        throw new Error();
    }

    @Override
    public WebClient.Builder exchangeFunction(ExchangeFunction exchangeFunction) {
        throw new Error();
    }

    @Override
    public WebClient.Builder apply(Consumer<WebClient.Builder> builderConsumer) {
        throw new Error();
    }

    @Override
    public WebClient.Builder clone() {
        throw new Error();
    }

    @Override
    public WebClient build() {
        throw new Error();
    }
}
