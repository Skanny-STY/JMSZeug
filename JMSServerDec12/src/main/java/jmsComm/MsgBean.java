package jmsComm;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import common.Connection;
import edu.hm.dako.chat.common.ChatPDU;

@MessageDriven( activationConfig = 
				{@ActivationConfigProperty( 
				propertyName = "destination",
				propertyValue = "java:jboss/exported/jms/queue/testqueue"
				)
			})

public class MsgBean implements MessageListener {
	@Inject JMSContext context;
	
	@Resource(mappedName = "java:jboss/exported/jms/topic/chattopic")
    private Topic chatTopic;
	private Connection connection;
	
	JMSConsumer consumertest;
	Message message;
	
	
	//private Connection connection = new WriteToTopicConnection(context, chatTopic);
	
	@Override
	public void onMessage(Message message) {
				
			try {
//				String text = message.getBody(String.class);
//				System.out.println(text);
				ObjectMessage objectMessage = (ObjectMessage) message;
				ChatPDU pduFromQueue = (ChatPDU) objectMessage.getObject();
				System.out.println(pduFromQueue.getPduType());
				
				connection = new TopicConnection(context, chatTopic);
				Messagehandler msgHandler = new Messagehandler(connection);
				msgHandler.handleMessage(pduFromQueue);
			
				
				
				//System.out.println("abgerufene Nachricht "+ pduFromQueue.toString());
				// Messagehandler.processMessage(pduFromQueue) -> return "pduToTopic"
				// writeToTopic(pduToTopic)
//				context.createProducer().send(chatTopic, objectMessage);
				
//				TEST__________________________________________
				
//				consumertest = context.createConsumer(chatTopic);
				
				
//				ChatPDU pduFromTopic = context.createConsumer(chatTopic).receiveBody(ChatPDU.class);
//				System.out.println(pduFromTopic);
				
//				context.createConsumer(chatTopic).receiveBody(ChatPDU.class);
				
				
				
				
//				TEST_____________________________________________
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	
}
