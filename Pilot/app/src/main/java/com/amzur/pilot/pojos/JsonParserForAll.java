package com.amzur.pilot.pojos;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RameshK on 10-05-2016.
 *
 */
public class JsonParserForAll {

    private ObjectMapper objectMapper = null;
    private JsonFactory jsonFactory = null;
    private JsonParser jp = null;
    private Categories categories;
    private ItemsPojo itemsPojo;
    private ItemPojo item;

    public JsonParserForAll()
    {
        objectMapper=new ObjectMapper();
        jsonFactory=new JsonFactory();
    }

    /**
     * Parse the response of categories into list of categories
     * @param  response response got from the server*/
    public Categories parseResponseToCategories(String response)
    {
        try {
            jp=jsonFactory.createJsonParser(response);
            //TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,Category.class)
            categories=objectMapper.readValue(jp, Categories.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Parse the response of categories into list of categories
     * @param  response response got from the server*/
    public ItemsPojo parseResponseToItems(String response)
    {
        try {
            jp=jsonFactory.createJsonParser(response);

            itemsPojo=objectMapper.readValue(jp, ItemsPojo.class);
            return itemsPojo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parse the response of item details
     * @param response response of the item*/
    public ItemPojo parseItemResponse(String response)
    {
        try {
            jp=jsonFactory.createJsonParser(response);
            item=objectMapper.readValue(jp,ItemPojo.class);
            return item;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all categories
     * @return  list of categories*/
    public ArrayList<Category> getCategories() {
        return categories.Categories;
    }

    /**
     * Get all items object
     * @return complete object of items page*/
    public ItemsPojo getItemsPojo() {
        return itemsPojo;
    }

    /**
     * Get only items from the response*/
    public List<ItemPojo> getItems()
    {
        if(itemsPojo!=null)
        return itemsPojo.items;
        else
            return new ArrayList<>();
    }

    /**
     * Get item details*/
    public ItemPojo getItem() {
        return item;
    }
}
