 
package edu.mum.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mum.domain.RouteItem;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

/**
 * Routes item based on item type.
 * 
 */
@MessageEndpoint
public class ItemRouter {

    final Logger logger = LoggerFactory.getLogger(ItemRouter.class);
    
    /**
     * Process item.  Routes based on whether or 
     * not the item is a high price or moderate price.
     */
	@Router(inputChannel="processItem")
	public String processItem(RouteItem item) {
	    String destination = null;
	    
	    switch (item.getRouteItemType()) {
        case HIGH_PRICE:
        	destination = "highPrice";
            break;
        case MODERATE_PRICE:
        	destination = "moderatePrice";
            break;
	    }
	    
        return destination;
    }
}