package za.co.stars.configuration;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
@Configuration
public class SessionFactoryComponent {
	
	
	@Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    @Qualifier(value="sessionFacotry")
    public SessionFactory getSessionFactory() {
         return entityManagerFactory.unwrap(SessionFactory.class);
    }

}
