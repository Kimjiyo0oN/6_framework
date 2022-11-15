package edu.kh.project.member.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.vo.Member;

@SessionAttributes({},{})
@Controller
public class MemberController {
	
	@RequestMapping(value ="/member/login", method=RequestMethod.GET)
	public String loginPage() {
		
		return "member/login";
	}
	
	@PostMapping("/member/login")
	public String login(Member inpuMember,
			Model model,
			RedirectAttributes ra,
			@RequestParam(value="saveId", required =false) String saveId,
			HttpServletResponse resp,
			@RequestHeader(value = "referer")  String referer  
			) {
		
		
		
	}
	
	
	
}
