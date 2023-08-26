package org.springframework.web.reactive.function.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilderFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    @Nullable
    private String baseUrl;

    @Nullable
    private Map<String, ?> defaultUriVariables;

    @Nullable
    private UriBuilderFactory uriBuilderFactory;

    @Nullable
    private HttpHeaders defaultHeaders;

    @Nullable
    private MultiValueMap<String, String> defaultCookies;

    @Nullable
    private Consumer<WebClient.RequestHeadersSpec<?>> defaultRequest;

    @Nullable
    private List<ExchangeFilterFunction> filters;

    @Nullable
    private ClientHttpConnector connector;

    @Nullable
    private ExchangeStrategies strategies;

    @Nullable
    private List<Consumer<ExchangeStrategies.Builder>> strategiesConfigurers;

    @Nullable
    private ExchangeFunction exchangeFunction;


    public DefaultWebClientBuilder() {
    }

    public DefaultWebClientBuilder(DefaultWebClientBuilder other) {
        Assert.notNull(other, "DefaultWebClientBuilder must not be null");

        this.baseUrl = other.baseUrl;
        this.defaultUriVariables = (other.defaultUriVariables != null ?
                new LinkedHashMap<>(other.defaultUriVariables) : null);
        this.uriBuilderFactory = other.uriBuilderFactory;

        if (other.defaultHeaders != null) {
            this.defaultHeaders = new HttpHeaders();
            this.defaultHeaders.putAll(other.defaultHeaders);
        }
        else {
            this.defaultHeaders = null;
        }

        this.defaultCookies = (other.defaultCookies != null ?
                new LinkedMultiValueMap<>(other.defaultCookies) : null);
        this.defaultRequest = other.defaultRequest;
        this.filters = (other.filters != null ? new ArrayList<>(other.filters) : null);

        this.connector = other.connector;
        this.strategies = other.strategies;
        this.strategiesConfigurers = (other.strategiesConfigurers != null ?
                new ArrayList<>(other.strategiesConfigurers) : null);
        this.exchangeFunction = other.exchangeFunction;
    }

    @Override
    public WebClient.Builder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
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
        this.connector = connector;
        return this;
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
        ClientHttpConnector connectorToUse =
                (this.connector != null ? this.connector : initConnector());

//        ExchangeFunction exchange = (this.exchangeFunction == null ?
//                ExchangeFunctions.create(connectorToUse, initExchangeStrategies()) :
//                this.exchangeFunction);
//
//        ExchangeFunction filteredExchange = (this.filters != null ? this.filters.stream()
//                .reduce(ExchangeFilterFunction::andThen)
//                .map(filter -> filter.apply(exchange))
//                .orElse(exchange) : exchange);
//
//        HttpHeaders defaultHeaders = copyDefaultHeaders();
//
//        MultiValueMap<String, String> defaultCookies = copyDefaultCookies();
//
//        return new DefaultWebClient(filteredExchange, initUriBuilderFactory(),
//                defaultHeaders,
//                defaultCookies,
//                this.defaultRequest, new DefaultWebClientBuilder(this));
        throw new Error();
    }

    private ClientHttpConnector initConnector() {
        if (reactorClientPresent) {
            return new ReactorClientHttpConnector();
        }
        else if (jettyClientPresent) {
            return new JettyClientHttpConnector();
        }
        else if (httpComponentsClientPresent) {
            return new HttpComponentsClientHttpConnector();
        }
        throw new IllegalStateException("No suitable default ClientHttpConnector found");
    }
}
