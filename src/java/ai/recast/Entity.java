package ai.recast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Entity class represents an entity found by Recast.AI in the user input.
 *
 */
public class Entity {

    private String name;
    private double confidence;
    private JSONObject data;
    private String value;

    Entity (String name, JSONObject o) {
        this.data = o;
        this.name = name;
        
        if(o != null){
        	this.confidence = o.optDouble("confidence");
        	this.value = o.optString("value");
        }
    }

    /**
     * Returns the name of the entity
     * @return The name of the entity
     */
    public String getName() {
        return this.name;
    }
    
    

    /**
     * Returns the confidence of the entity
     * @return The confidence of the entity
     */
    public double getConfidence() {
        return this.confidence;
    }
    
    /**
     * Returns the value of the entity
     * @return The value of the entity
     */
    public String getValue() {
    	return this.value;
	  }

    /**
     * Returns the value of the jason data
     * @return The value of the jason data
     */
    public JSONObject getData() {
    	return this.data;
    }
    
    
    /**
     * Returns the fields described by the parameter if it exists or null otherwise
     * @param name The name of the field
     * @return The value of the field or null
     */
}
