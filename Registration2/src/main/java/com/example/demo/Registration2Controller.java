package com.example.demo;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Registration2Controller {

	// ハンドラーメソッド
	@PostMapping("/register")
	// ModelAttributeを付与することにより、
	// フォーム部品のname属性値とフォームクラスのプロパティ名をキーにして
	// フォーム部品の値がフォームオブジェクトにバインドされる
	// = 部品ごとの引数を並べる必要がなくなる。
	public ModelAndView register(@ModelAttribute RegistData registData, ModelAndView mv) {
		StringBuilder sb = new StringBuilder();

		sb.append("名前：" + registData.getName());
		sb.append("、パスワード：" + registData.getPassword());
		sb.append("、性別：" + registData.getGender());
		sb.append("、地域：" + registData.getArea());
		sb.append("、興味のある分野：" + registData.getInterest());
		sb.append("、備考：" + registData.getRemarks().replaceAll("¥n", ""));

		mv.setViewName("result");
		mv.addObject("registData", sb.toString());

		return mv;
	}
}
