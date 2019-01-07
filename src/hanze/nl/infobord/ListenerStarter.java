package hanze.nl.infobord;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class ListenerStarter implements Runnable, ExceptionListener {
	private Connection connection;

	public ListenerStarter(String selector) throws JMSException {
		QueueListener listener = new QueueListener();

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("INFO_BORD");
		MessageConsumer consumer = session.createConsumer(destination, selector);
		consumer.setMessageListener(listener);
		System.out.println("Constructed");
	}

	@Override
	public void run() {
		System.out.println("Running");
	}

	@Override
	public void onException(JMSException e) {
		e.printStackTrace();
		close();
	}

	void close() {
		try {
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}