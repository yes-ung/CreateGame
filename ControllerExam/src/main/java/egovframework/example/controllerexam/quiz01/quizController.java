package egovframework.example.controllerexam.quiz01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class quizController {
	@GetMapping("/qz01/quiz01.do")
	public String quiz01() {
		return "qz01/quiz01";
	}
	@GetMapping("/qz01/quiz02.do")
	public String quiz02(Model x) {
		x.addAttribute("quiz","안녕 페이지");
		return "qz01/quiz02";
	}
	@GetMapping("/qz01/quiz03.do")
	public String quiz03(Model x) {
		x.addAttribute("quiz1","안녕 페이지");
		x.addAttribute("quiz2","안녕 페이지2");
		x.addAttribute("quiz3","안녕 페이지3");
		return "qz01/quiz03";
	}
	@GetMapping("/qz01/quiz04.do")
	public String quiz04(Model x) {
		String quiz1="hello";
		int quiz2 =10;
		double quiz3= 10.5;
		
		
		
		x.addAttribute("quiz1",quiz1);
		x.addAttribute("quiz2",quiz2);
		x.addAttribute("quiz3",quiz3);
		return "qz01/quiz04";
	}

}
