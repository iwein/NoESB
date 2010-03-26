package org.noesb.receiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.message.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@ContextConfiguration(locations = {"/spring/module-context.xml", "/test-load-balancer-channel.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ReceiverContextIntegrationTest {

    @Autowired     @Qualifier("loadBalancer")
    MessageChannel inputChannel;

    @Test
    public void shouldLoadConfiguration() {
        // just testing if the context is valid
    }

    @Test
    public void shouldAcceptMessage() {
        inputChannel.send(MessageBuilder.withPayload("test").build());
    }
}
