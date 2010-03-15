package org.noesb.loadbalancer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Returns messages with a certain sequence, comes in handy for tracking which message goes where.
 *
 * @author iwein
 */
public class SequentialMessageSource {

    private final AtomicInteger counter = new AtomicInteger(1);

    public String next() {
        return "Message " + counter.getAndIncrement();
    }

}
