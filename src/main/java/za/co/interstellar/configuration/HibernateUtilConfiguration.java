package za.co.interstellar.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
@Configuration
@EnableTransactionManagement
public class HibernateUtilConfiguration {
	
	 	@Bean
	    @Autowired
	    public LocalSessionFactoryBean sessionFactory() {
	        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource());
	        sessionFactory.setPackagesToScan("za.co.interstellar.persistence");
	        sessionFactory.setHibernateProperties(properties());

	        return sessionFactory;
	    }

	    @Bean
	    @Autowired
	    public HibernateTransactionManager transactionManager(final SessionFactory sessionFactory) {
	        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
	        hibernateTransactionManager.setSessionFactory(sessionFactory);

	        return hibernateTransactionManager;
	    }

	    @Bean
	    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	        return new PersistenceExceptionTranslationPostProcessor();
	    }

	    @Bean
	    public Properties properties() {
	        Properties properties = new Properties();
	        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
	        //TODO: Change hibernate.show_sql to false when deploying this to TEST, UAT & PROD environments
	        properties.setProperty("hibernate.show_sql", "true");
	        properties.setProperty("hibernate.hbm2ddl.auto", "create");

	        return properties;
	    }
	    
	   
	    
	    @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
	        dataSource.setUrl("jdbc:derby:interstellar;create=true");
	        dataSource.setUsername("arthur");
	        dataSource.setPassword("gwatidzo");
	        return dataSource;
	    }
	    
	    

}
