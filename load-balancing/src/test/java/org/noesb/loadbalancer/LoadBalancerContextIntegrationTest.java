package org.noesb.loadbalancer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.SubscribableChannel;
import org.springframework.integration.core.Message;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.message.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/module-context.xml"})
public class LoadBalancerContextIntegrationTest {

    @Autowired
    SourcePollingChannelAdapter messageSource;

    @Autowired
    @Qualifier("loadBalancer")
    SubscribableChannel loadBalancer;
    private Message<?> anyMessage = MessageBuilder.withPayload("foo").build();
    private CountDownLatch messagesReceived = new CountDownLatch(1);
    private MessageHandler handler1 = new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message)
                    throws MessageRejectedException, MessageHandlingException, MessageDeliveryException {
                System.out.println(format("Received message [%s]", message));
                messagesReceived.countDown();
            }
        } ;

    @Before
    public void addSubscriber() {
        loadBalancer.subscribe(handler1);
    }

    @Test
    public void shouldLoadConfig() {
        //spring loads the configuration
    }

    @Test
    public void loadBalancerShouldAcceptMessage() {
        loadBalancer.send(anyMessage);
    }

    @Test
    public void shouldStartSendingMessages() throws InterruptedException {
        messageSource.start();
        messagesReceived.await(2, TimeUnit.SECONDS);
        assertThat(messagesReceived.getCount(), is(0l)) ;
    }

}
