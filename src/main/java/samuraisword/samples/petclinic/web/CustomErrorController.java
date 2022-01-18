package samuraisword.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * This advice is necessary because MockMvc is not a real servlet environment, therefore it does not redirect error
 * responses to [ErrorController], which produces validation response. So we need to fake it in tests.
 * It's not ideal, but at least we can use classic MockMvc tests for testing error response + document it.
 */
@Controller
public class CustomErrorController implements ErrorController {
	
	@Override
	public String getErrorPath() {	
		return "/error";
	}
	
	@RequestMapping("/error")
	public String handlerError(HttpServletRequest request, Exception ex) {
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if(status!= null) {
			int statusCode = Integer.parseInt(status.toString());
			request.setAttribute("exception", "Exception: " + ex.getMessage() + " -- " + ex.getCause());
			request.setAttribute("errorCode", "Codigo de error: " + status.toString());
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error-404";
			}else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "error-500";
			}else if(statusCode == HttpStatus.FORBIDDEN.value()) {
				return "error-403";
			}
		}
		
		return "error";
	}
}