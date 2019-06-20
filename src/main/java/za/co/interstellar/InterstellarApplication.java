package za.co.interstellar;

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

	public static void main(String[] args) {
		SpringApplication.run(InterstellarApplication.class, args);
	}

}
