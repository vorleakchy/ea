package edu.mum.integration;

import edu.mum.domain.RouteItem;
import edu.mum.domain.Item;

public interface ItemTransformer {
	public RouteItem transformItem(Item item);
}
