package hanze.nl.infobord;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueListener implements MessageListener {
	private InfoBord board;

	public QueueListener() {
		board = InfoBord.getInfoBord();
	}

	@Override
	public void onMessage(Message message) {
		try {
			InfoBord.verwerktBericht(((TextMessage)message).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		board.setRegels();
	}
}