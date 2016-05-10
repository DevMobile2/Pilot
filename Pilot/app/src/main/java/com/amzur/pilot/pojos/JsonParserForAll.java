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
    private ArrayList<Category> categories;
    private ItemsPojo itemsPojo;

    public JsonParserForAll()
    {
        objectMapper=new ObjectMapper();
        jsonFactory=new JsonFactory();
    }

    /**
     * Parse the response of categories into list of categories
     * @param  response response got from the server*/
    public void parseResponseToCategories(String response)
    {
        try {
            jp=jsonFactory.createJsonParser(response);
            categories=objectMapper.readValue(jp, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,Category.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse the response of categories into list of categories
     * @param  response response got from the server*/
    public void parseResponseToItems(String response)
    {
        try {
            jp=jsonFactory.createJsonParser(response);
            itemsPojo=objectMapper.readValue(jp, ItemsPojo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all categories
     * @return  list of categories*/
    public ArrayList<Category> getCategories() {
        return categories;
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
}
