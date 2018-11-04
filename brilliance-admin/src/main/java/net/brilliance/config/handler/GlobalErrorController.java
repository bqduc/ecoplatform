/**
 * 
 */
package net.brilliance.config.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ducbq
 *
 */
@RestController
@Controller
public class GlobalErrorController implements ErrorController {

	private static final String PATH = "/error";

	@RequestMapping(value = PATH)
	public void error(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int status = response.getStatus();
		for (String parameter :request.getParameterMap().keySet()){
			request.getSession().setAttribute(parameter, request.getParameterMap().get(parameter));
		}

		switch (status) {
		case 404:
			response.sendRedirect("/404");
			break;
		case 401:
			response.sendRedirect("/401");
			break;
		case 403:
			response.sendRedirect("/401");
			break;
		default:
			response.sendRedirect("/404");
		}
	}

	/*@RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	     
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	     
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	            return "error-404";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            return "error-500";
	        }
	    }
	    return "error";
    }*/

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
