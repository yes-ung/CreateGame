package egovframework.example.game;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Example01 {
	 @GetMapping("/example.do")
	    public String examplePage() {
	        return "example01";  // templates/game.html
	    }

}
