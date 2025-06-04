package egovframework.example.controllerexam.quiz01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Quiz02Controller {
	
	@GetMapping("qz02/quiz01.do")
	public String quiz01(Model x,
			@RequestParam(defaultValue = "") String test) {
		x.addAttribute("quiz1" ,test);
		return "qz02/quiz01";
	}
	@GetMapping("qz02/quiz02.do")
	public String quiz02(Model x,
			@RequestParam(defaultValue = "0")int dno) {
		x.addAttribute("quiz2",dno);
		return "qz02/quiz02";
	}

}
