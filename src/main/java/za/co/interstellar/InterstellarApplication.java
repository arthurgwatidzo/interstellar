package za.co.interstellar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
* @Author Arthur Gwatidzo - Email: arthur.gwatidzo@gmail.com, Cell: 076-898-3930
*/
@SpringBootApplication
@Configuration
@ComponentScan
public class InterstellarApplication {
	
	 private static final Log log = LogFactory.getLog(InterstellarApplication.class);

	public static void main(String[] args) {
		log.info("Interstellar Application has successfully started up!!!");
		SpringApplication.run(InterstellarApplication.class, args);
	}

}
