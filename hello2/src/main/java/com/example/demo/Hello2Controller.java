package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello2Controller {
	@GetMapping("/hello2")
	// @RequestParamの名称とバインドされる引数名が同じなら、@RequestParamを省略もできる。
	// 例えば、こう
	// public String sayHello(String name, int age)
	public String sayHello(@RequestParam("name")String name, @RequestParam("age")int age) {
		return "Hello, world!" + "こんにちは" + name + "さん！" + age + "歳なんですね。";
	}

}
