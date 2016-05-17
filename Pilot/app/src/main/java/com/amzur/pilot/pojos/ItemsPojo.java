package com.amzur.pilot.pojos;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by RameshK on 10-05-2016.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemsPojo {

    public int categoryId;
    public String categoryName;
    public String imageUrl;
    public String description;
    public List<ItemPojo> items;
}
