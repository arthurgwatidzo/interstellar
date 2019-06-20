package za.co.interstellar.exception;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @Author Arthur Gwatidzo  
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
@RunWith(MockitoJUnitRunner.class)
public class InterstellarApplicationExceptionTest {

	public InterstellarApplicationExceptionTest() {
		
	}
	
	@AfterClass
	public static void tearDownClass() {
		
	}
	
	@BeforeClass
	public static void setUpClass() {
		
	}
	
	@Before
	public void setup() {
		
	}
	
	@After
	public void tearDown() {
		
	}
	
	
	@Test
	public void testInterstellarApplicationExceptionConstructors() {
		String msg = "message";
		InterstellarApplicationException iaeEx = new InterstellarApplicationException();
		InterstellarApplicationException iaeExMsg = new InterstellarApplicationException("message");
		InterstellarApplicationException iaeExThrw = new InterstellarApplicationException(iaeEx.getCause());
		InterstellarApplicationException iaeExMsgThrw = new InterstellarApplicationException(msg,iaeEx.getCause());
		
		
		Assert.assertTrue(iaeEx instanceof InterstellarApplicationException);
		Assert.assertTrue(iaeExMsg instanceof InterstellarApplicationException);
		Assert.assertTrue(iaeExThrw instanceof InterstellarApplicationException);
		Assert.assertTrue(iaeExMsgThrw instanceof InterstellarApplicationException);
		
		
	}
	
	
}
