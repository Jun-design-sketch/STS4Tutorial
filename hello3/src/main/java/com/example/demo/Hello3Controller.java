package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello3Controller {
	// {}で囲ってるところはURIテンプレート変数。値として取り出す部分を指定
	@GetMapping("/hello3/{name}/{age}")
	// 経路変数。URIテンプレート変数名と一致する必要がある
	public String sayHello(@PathVariable("name") String name, @PathVariable("age") int age) {
		return "Hello, World!" + "こんにちは！" + name + "さん！" + age + "歳ですか？";
	}
}
