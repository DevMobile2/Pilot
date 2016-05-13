package com.amzur.pilot.pojos;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by RameshK on 10-05-2016.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    public int categoryid;
    public String categoryName;
    public String description;
    public String imageUrl;
}
