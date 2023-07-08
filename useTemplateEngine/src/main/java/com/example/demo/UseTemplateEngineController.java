package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

// RestControllerはXMLを返す性質からテキスト返却に利用していたが
// htmlファイルの返却になるからControllerを使用する
@Controller
public class UseTemplateEngineController {
	@GetMapping("/hello5")
	// ModelAndViewクラスは
	// モデルとビュー名を保持するクラスで、その通り両方を保持するオブジェクトを持つ
	public ModelAndView sayHello(@RequestParam("name") String name, ModelAndView mv) {
		// ビュー名（名前）をセット
		mv.setViewName("hello");
		// ビューで使用するデータ（モデル）をセット
		mv.addObject("name", name);
		return mv;
	}

}
