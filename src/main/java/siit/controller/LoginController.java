package siit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String displayForm() {
		return "login";
	}
	
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ModelAndView doLogin(
			@RequestParam String user,
			@RequestParam String password,
			HttpSession session) {
		
		if (user.startsWith("a") && "b".equals(password)) {
			session.setAttribute("authUser", user);
			session.setAttribute("authUserCanEdit", 
					user.length() != 6);
			return new ModelAndView("redirect:/customers");
		} else {
			ModelAndView mav = new ModelAndView("login");
			mav.addObject("error", "User name or password is not correct");
			return mav;
		}
	}
	
}
