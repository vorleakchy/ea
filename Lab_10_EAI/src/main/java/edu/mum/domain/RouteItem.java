package edu.mum.domain;

import java.io.Serializable;

public class RouteItem  implements Serializable {
	
	public enum RouteItemType { HIGH_PRICE, MODERATE_PRICE }
	
	Item item;
	private final RouteItemType routeItemType;
	
	private String auctionId;

	public RouteItem(Item item, RouteItemType routeItemType, String auctionId) {
		super();
		this.item = item;
		this.routeItemType = routeItemType;
		this.auctionId = auctionId;
	}

	public Item getItem() {
		return item;
	}

	public String getAuctionId() {
		return auctionId;
	}

	public RouteItemType getRouteItemType() {
		return routeItemType;
	}
	
    
  
}
