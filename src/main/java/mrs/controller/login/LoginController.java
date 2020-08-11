package mrs.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	
	//ログイン画面用のGET用コントローラー	
	
	@GetMapping("/login")
	public String getLogin(Model model) {
		//login.htmlに画面遷移
		return "login";
	}
	
	//ログイン画面用のPOST用コントローラー
	
	@PostMapping("/login")
	public String postLogin(Model model) {
		//ホーム画面に画面遷移
	    return "redirect:/login";
	}
	
	//ログアウト用メソッド
		@PostMapping("/logout")
		public String postLogout() {
			//ログイン画面にリダイレクト
			return "redirect:/login";

		}
}
