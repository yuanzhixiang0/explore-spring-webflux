package org.springframework.web.reactive.function.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

/**
 * Static factory methods to create an {@link ExchangeFunction}.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @since 5.0
 */
public abstract class ExchangeFunctions {

    private static final Log logger = LogFactory.getLog(ExchangeFunctions.class);


    /**
     * Create an {@code ExchangeFunction} with the given {@code ClientHttpConnector}.
     * This is the same as calling
     * {@link #create(ClientHttpConnector, ExchangeStrategies)} and passing
     * {@link ExchangeStrategies#withDefaults()}.
     *
     * @param connector the connector to use for connecting to servers
     * @return the created {@code ExchangeFunction}
     */
    public static ExchangeFunction create(ClientHttpConnector connector) {
        return create(connector, ExchangeStrategies.withDefaults());
    }

    /**
     * Create an {@code ExchangeFunction} with the given
     * {@code ClientHttpConnector} and {@code ExchangeStrategies}.
     *
     * @param connector  the connector to use for connecting to servers
     * @param strategies the {@code ExchangeStrategies} to use
     * @return the created {@code ExchangeFunction}
     */
    public static ExchangeFunction create(ClientHttpConnector connector, ExchangeStrategies strategies) {
        return new DefaultExchangeFunction(connector, strategies);
    }

    private static class DefaultExchangeFunction implements ExchangeFunction {

        private final ClientHttpConnector connector;

        private final ExchangeStrategies strategies;

        private boolean enableLoggingRequestDetails;


        public DefaultExchangeFunction(ClientHttpConnector connector, ExchangeStrategies strategies) {
            Assert.notNull(connector, "ClientHttpConnector must not be null");
            Assert.notNull(strategies, "ExchangeStrategies must not be null");
            this.connector = connector;
            this.strategies = strategies;

            strategies.messageWriters().stream()
                    .filter(LoggingCodecSupport.class::isInstance)
                    .forEach(reader -> {
                        if (((LoggingCodecSupport) reader).isEnableLoggingRequestDetails()) {
                            this.enableLoggingRequestDetails = true;
                        }
                    });
        }


        @Override
        public Mono<ClientResponse> exchange(ClientRequest request) {
            throw new Error();
        }

        @Override
        public ExchangeFunction filter(ExchangeFilterFunction filter) {
            throw new Error();
        }
    }
}
