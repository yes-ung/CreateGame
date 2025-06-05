package egovframework.example.controllerexam.quiz01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import egovframework.example.controllerexam.service.DeptVO;

@Controller
public class Quiz04Controller {
	@GetMapping("qz04/quiz01.do")
	public String quiz01(@ModelAttribute DeptVO deptVO) {
		return "qz04/quiz01";
	}

}
