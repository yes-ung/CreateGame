package egovframework.example.game;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controller01 {
	@GetMapping("/game.do")
	public String game() {
		return "game/game01";
	}

}
