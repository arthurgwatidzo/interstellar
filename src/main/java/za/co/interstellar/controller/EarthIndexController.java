package za.co.interstellar.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;


/**
 * @Author Arthur Gwatidzo  
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
public interface EarthIndexController extends ErrorController  {
	
	 public String error(Model model);

}
