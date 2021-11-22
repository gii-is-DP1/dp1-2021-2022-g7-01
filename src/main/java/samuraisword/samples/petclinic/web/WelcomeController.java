package samuraisword.samples.petclinic.web;

import java.util.Date;
import java.util.Map;


import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model, HttpServletResponse a) {	      
	    return "welcome";
	  }
}
