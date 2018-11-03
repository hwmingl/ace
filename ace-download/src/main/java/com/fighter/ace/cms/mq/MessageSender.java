package com.fighter.ace.cms.mq;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

/**
 * Created by hanebert on 18/9/2.
 */
public class MessageSender {


    private final JmsTemplate jmsTemplate;

    public MessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendTxtMsg(Destination destination ,String text) {
        jmsTemplate.convertAndSend(destination,text);
    }

}
