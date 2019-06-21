package za.co.interstellar.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;



/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
@EnableWs
@Configuration
public class WServiceConfiguration extends WsConfigurerAdapter{
	
	 @Bean
	    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
	        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
	        servlet.setApplicationContext(applicationContext);
	        servlet.setTransformWsdlLocations(true);
	        return new ServletRegistrationBean(servlet, "/ws/*");
	    }

	    @Bean(name = "interstellar")
	    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema interstellarXSDSchema) {
	        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
	        wsdl11Definition.setPortTypeName("InterstellarPort");
	        wsdl11Definition.setLocationUri("/ws");
	        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
	        wsdl11Definition.setSchema(interstellarXSDSchema);
	        return wsdl11Definition;
	    }

	    @Bean
	    public XsdSchema interstellarSchema() {
	        return new SimpleXsdSchema(new ClassPathResource("interstellar.xsd"));
	    }

}
