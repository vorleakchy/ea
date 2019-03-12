package edu.mum.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import edu.mum.domain.Item;

public class ItemServiceImpl implements ItemService {

	@Override
	public void publish(RabbitTemplate rabbitTemplate) {
		Item item1 = new Item("Kazoo", 50);
		Item item2 = new Item("Hammer", 10);
		
		rabbitTemplate.convertAndSend(item1);
		rabbitTemplate.convertAndSend(item2);
	}


}
