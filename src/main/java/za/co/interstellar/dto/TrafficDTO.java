package za.co.interstellar.dto;

import javax.persistence.Column;

import lombok.Data;

/**
 * @Author Arthur Gwatidzo  
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
@Data
public class TrafficDTO {
		private String routeId;
	    private String source;
	    private String destination;
	    private float delay;
	    /*Using Lombok to create getters and setters for this class*/
	    
	    /*TODO: Will complete this DTO depending on the data needed to be passed from the presentation layer To other layers
		  Will also need to replace the Traffic entity with the TrafficDTO in the controllers
		*/
		
}
