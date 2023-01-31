package it.swe.controlsystem;

import java.util.Collections;
import java.util.List;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class PickerState implements WorkState {

	@Override
	public boolean work() throws Exception {
		try {
			Box box;
			String s = receveBoxLocation();
			if (s != null) {
				int codeMessage = Integer.parseInt(s.replaceAll("[^0-9]", ""));
				String destMessage = s.substring(s.indexOf(":") + 1, s.lastIndexOf("."));
				box = findInStorege(codeMessage, destMessage);
				while(box == null)
					 box = findInStorege(codeMessage, destMessage);
				return box.send();
			}

		} catch (JMSException e) {
			loggerApplication.info(e.getMessage());
		} catch (Exception e) {
			loggerApplication.info(e.getMessage());
			throw new Exception();
		}
		return false;
	}

	private static Box findInStorege(int codeMessage, String destMessage){
		List<Box> objects = Collections.synchronizedList(MonitorSystem.getMap().get(destMessage));
		for (Box b : objects) {
			if (b.getCode() == codeMessage) {
				objects.remove(b);
				return b;
			}
		}
		loggerApplication.error("Il pacco con codice : " + codeMessage + " con direzione : " + destMessage
				+ " non si trova nel magazzino  ");
		return null;
	}

	private static String receveBoxLocation() throws JMSException {

		String url = ActiveMQConnection.DEFAULT_BROKER_URL;
		String queueName = "Loader queue";

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(queueName);
		MessageConsumer consumer = session.createConsumer(destination);
		try {
			Message message = consumer.receive(500);
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				connection.close();
				return textMessage.getText();
			}
		} catch (JMSException e) {
			loggerApplication.error(e.getMessage());
			throw new JMSException("Error on Jms Connection");
		}
		connection.close();
		return null;
	}
}
