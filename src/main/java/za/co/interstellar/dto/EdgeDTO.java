package za.co.interstellar.dto;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Data;

/**
 * @Author Arthur Gwatidzo
 * 
 *         Email: arthur.gwatidzo@gmail.com,
 * 
 *         Cell: 076-898-3930
 * 
 */
@Data
public class EdgeDTO implements Serializable {

	private String edgeId;
	private long recordId;
	private String source;
	private String destination;
	private float distance;
	private float timeDelay;

	

}
