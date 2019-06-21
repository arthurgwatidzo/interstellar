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
	// Using a root package also allows the @ComponentScan annotation to be used without needing to specify a basePackage attribute	
	 private static final Log log = LogFactory.getLog(InterstellarApplication.class);

	public static void main(String[] args) {
		log.info("Interstellar Application is now starting up. Might take a couple of minutes. Please be patient!!!");
		SpringApplication.run(InterstellarApplication.class, args);
	}

}
