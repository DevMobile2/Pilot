package com.amzur.pilot.pojos;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by RameshK on 10-05-2016.
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemPojo {


   public int itemId;
    public String itemName;
    public String description;

    public String categoryId;

    public String category;

    public String specifications;

    public String seller;
    public int quantity;

    public int price;

    public String imageUrl;

    public long serialNumber;

    public String condition;

 public int discountPercentage;
}


