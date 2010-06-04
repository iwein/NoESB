package org.noesb.doodles;

import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdminTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * In order for this test to be green you have to have a local AMQP server running
 *
 * @author Iwein Fuld
 */
public class RabbitTest {
    final ConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");

    @Before
    public void setupAmqpStructure() {
        RabbitAdminTemplate adminTemplate = new RabbitAdminTemplate(connectionFactory);
        adminTemplate.declareQueue(new Queue("myqueue"));
    }

    @Test
    public void shouldSendAndReceive() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend("myqueue", "bar");
        Object result = template.receiveAndConvert("myqueue");
        assertThat(result, is(notNullValue()));
    }

}
