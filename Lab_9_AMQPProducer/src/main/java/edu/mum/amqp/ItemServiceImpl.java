package edu.mum.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import edu.mum.domain.Item;

public class ItemServiceImpl implements ItemService {

	@Override
	public void publish(RabbitTemplate rabbitTemplate) {
		// TODO Auto-generated method stub
		Item item1 = new Item("Kazoo", "kazoo desc...");
		Item item2 = new Item("Hammer", "hammer desc...");
		
		rabbitTemplate.convertAndSend(item1);
		rabbitTemplate.convertAndSend(item2);
	}


}
