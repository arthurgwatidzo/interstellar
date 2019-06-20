package za.co.interstellar.constants;



/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
public enum BusinessCodes {
	
	
	INTERSTELLAR_ROUTE_EXISTS(1, "ROUTE EXISTS"),
    INTERSTELLAR_ROUTE_TO_SELF(2, "ROUTE TO SELF"),
    INTERSTELLAR_TRAFFIC_EXISTS(3, "TRAFFIC EXISTS"),
    INTERSTELLAR_TRAFFIC_TO_SELF(4, "TRAFFIC TO SELF");

    final int id;
    final String label;

    
    BusinessCodes(final int id, final String label) {
        this.id = id;
        this.label = label;
    }

    public static BusinessCodes fromString(final String str) {
        for (BusinessCodes e : BusinessCodes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return label;
    }

}
