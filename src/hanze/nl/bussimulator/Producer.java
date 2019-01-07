package hanze.nl.bussimulator;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

final class Producer {
	private static Session session;
	private static Connection connection;
	private static MessageProducer producer;

	static void init() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("BUS_INFO");
		producer = session.createProducer(destination);
	}

	static void send(String message) {
		try {
			TextMessage msg = session.createTextMessage(message);
			producer.send(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	static void close() throws JMSException {
		connection.close();
	}
}
