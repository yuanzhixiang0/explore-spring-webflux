package org.springframework.web.reactive.function;

import org.springframework.http.ReactiveHttpOutputMessage;

public class BodyInserters {

    private static final BodyInserter<Void, ReactiveHttpOutputMessage> EMPTY_INSERTER =
            (response, context) -> response.setComplete();

    /**
     * Inserter that does not write.
     * @return the inserter
     */
    @SuppressWarnings("unchecked")
    public static <T> BodyInserter<T, ReactiveHttpOutputMessage> empty() {
        return (BodyInserter<T, ReactiveHttpOutputMessage>) EMPTY_INSERTER;
    }

}
