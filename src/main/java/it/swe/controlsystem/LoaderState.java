package it.swe.controlsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoaderState implements WorkState {

	private static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");

	public boolean work() throws Exception {
		try {
			Box box = MonitorSystem.getContainer().remove();
			if (box != null) {
				store(MonitorSystem.getMap(), box);
				loggerApplication.info("Un pacco è stato rimosso dal Container e caricato nella mappa");
				load(box);
				return true;
			} else {
				loggerApplication.info("Non ci sono più pacchi da spedire");
				return false;
			}

		} catch (JMSException e) {
			loggerApplication.error(e.getMessage());
			throw new JMSException("Error on Jms Connection");
		}

	}

	private static synchronized void store(Map<String, List<Box>> map, Box b) {
		if (map.containsKey(b.getShippingVan())) {
			List<Box> boxDestination = map.get(b.getShippingVan());
			boxDestination.add(b);
			map.put(b.getShippingVan(), boxDestination);
		} else {
			List<Box> boxDestination = Collections.synchronizedList(new ArrayList<Box>());
			boxDestination.add(b);
			map.put(b.getShippingVan(), boxDestination);
			loggerApplication.info("Creata area di stoccaggio per destinazione : " + b.getShippingVan());
		}
	}

	private void load(Box box) throws JMSException {

		String url = ActiveMQConnection.DEFAULT_BROKER_URL;
		String queueName = "Loader queue";

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(queueName);
		MessageProducer producer = session.createProducer(destination);
		TextMessage message = session
				.createTextMessage("Box code " + box.getCode() + " shipping to :" + box.getShippingVan() + ".");
		producer.send(message);
		connection.close();
	}
}
