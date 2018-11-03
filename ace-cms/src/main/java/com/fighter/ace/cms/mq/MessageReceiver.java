package com.fighter.ace.cms.mq;

import javax.jms.*;

/**
 * Created by hanebert on 18/9/2.
 */
public class MessageReceiver implements MessageListener {


    @Override
    public void onMessage(Message message) {

        if (message instanceof TextMessage){
            try {
                System.out.println("jz3d received:"+((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }


}
