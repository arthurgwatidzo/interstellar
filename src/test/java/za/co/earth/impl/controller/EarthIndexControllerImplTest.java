package za.co.earth.impl.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;

import za.co.interstellar.impl.controller.EarthIndexControllerImpl;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
public class EarthIndexControllerImplTest {
	
	@Mock
    View mockView;
    @InjectMocks
    private EarthIndexControllerImpl controller;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller)
                .setSingleView(mockView)
                .build();

    }

    @Test
    public void verifyIndexViewIsCorrect() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
    
    @Ignore
    @Test
    public void verifyIndexViewIsNotCorrect() throws Exception{
    	//TODO: Negative testing needs to be done
    }

    @Test
    public void verifyErrorPageViewIsCorrect() throws Exception {
        String message = "Failed to render the page. Please restart the interstellar application!.";
        mockMvc.perform(get(controller.getErrorPath()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("validationMessage", message))
                .andExpect(view().name("validation"));
    }
    
    
    @Ignore
    @Test
   public  void verifyErrorPageViewIsNotCorrect() throws Exception{
    	//TODO: Negative testing needs to be done
    }

}
