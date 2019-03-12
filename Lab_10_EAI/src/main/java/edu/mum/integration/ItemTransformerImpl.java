package edu.mum.integration;

import edu.mum.domain.RouteItem;
import edu.mum.domain.RouteItem.RouteItemType;

import org.springframework.integration.annotation.Transformer;

import edu.mum.domain.Item;

public class ItemTransformerImpl implements ItemTransformer {

	@Transformer(inputChannel="fromAmqpItem", outputChannel="processItem")
	public RouteItem transformItem(Item item) {
		String auctionId = item.getName() + item.getVersion();
		RouteItemType routeItemType = null;
		
		if (item.getPrice() > 20)
			routeItemType = RouteItemType.HIGH_PRICE;
		else
			routeItemType = RouteItemType.MODERATE_PRICE;
		
		RouteItem routeItem = new RouteItem(item, routeItemType, auctionId);
		
		return routeItem;
	}

}
