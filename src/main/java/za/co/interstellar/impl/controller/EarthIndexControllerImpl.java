package za.co.interstellar.impl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import za.co.interstellar.controller.EarthIndexController;

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
    private static final String PATH = "/error";

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping(value = PATH)
    public String error(Model model) {
        String message = "Failed to load the page. Please restart again.";
        model.addAttribute("validationMessage", message);
        return "validation";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}

