package egovframework.example.controllerexam.web.ex04;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import egovframework.example.controllerexam.service.DeptVO;
import egovframework.example.controllerexam.service.MemberVO;

@Controller
public class ObjectController {
	// 따라하기 예제1
	@GetMapping("/ex04/example01.do")
	public String example01(
			@ModelAttribute MemberVO memberVO) {
		  return "ex04/example01";
	}
			

}
