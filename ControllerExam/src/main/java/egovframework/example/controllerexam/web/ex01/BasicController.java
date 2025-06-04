package egovframework.example.controllerexam.web.ex01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BasicController {
	 @RequestMapping(value ="/ex01/hello.do", method 
			 =RequestMethod.GET)
			     public String hello() {
			         return "ex01/hello";
			  }
			  //  따라하기 예제 1
			  @GetMapping("/ex01/example01.do")
			     public String example01() {
			         return "ex01/example01";
			  }
			  
			  @GetMapping("/ex01/example02.do")
			  public String example02(Model model) {
				  model.addAttribute("greeting", "안녕 스프링");
				  return "ex01/example02";
			  }
			  //예제5
			  @GetMapping("/ex01/example03.do")
			  public String example03(Model model) {
				  model.addAttribute("greeting","안녕 스프링");
				  model.addAttribute("greeting2","안녕 스프링2");
				  return "ex01/example03";
			  }
			  
			//예제7
			  @GetMapping("/ex01/example04.do")
			  public String example04(Model model) {
				  
				  String str = "abc";
				  int num = 10;
				  boolean value = true;
				  
				  model.addAttribute("str",str);
				  model.addAttribute("num",num);
				  model.addAttribute("value",value);
				  return "ex01/example04";
			  }

}
