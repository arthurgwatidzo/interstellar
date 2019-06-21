package za.co.interstellar.impl.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import za.co.interstellar.controller.EarthIndexController;
import za.co.interstellar.service.EarthEntityManagerService;

/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
@Controller
public class EarthIndexControllerImpl implements EarthIndexController{
	
	private static final Log log = LogFactory.getLog(EarthIndexControllerImpl.class);
    private static final String PATH = "/error";

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping(value = PATH)
    public String error(Model model) {
    	log.debug("Interstellar Application has successfully started up!!!");
        String message = "Failed to render the page. Please restart the interstellar application!.";
        model.addAttribute("validationMessage", message);
        return "validation";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}

