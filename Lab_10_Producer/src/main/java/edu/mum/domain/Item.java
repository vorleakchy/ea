package edu.mum.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item  implements Serializable {

	  
	  
     private Long id = null;

     private int version = 0;

      private String name;

 
     private float price;

     public Item() {
    	 
     }
 
     public Item(String name, float price) {
    	 this.name = name;
    	 this.price = price;
    	 
     }
 
    // ********************** Accessor Methods ********************** //

    public Long getId() { return id; }
    public int getVersion() { return version; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

 
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

      public void setId(Long id) {
		this.id = id;
	}
  
}
