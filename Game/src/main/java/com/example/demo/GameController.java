package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GameController {

	// セッションを利用し、正解や回答履歴を格納する
	// autowiredすることでコントローラクラス起動時、sessionが呼び出されるため
	// 自分でセッションのインスタンスをセットする必要はない
	@Autowired
	HttpSession session;

	@GetMapping("/")
	public String index() {
		// ホーム画面に遷移する時にセッション情報をクリアする
		session.invalidate();
		// 答えを作ってSessionに格納
		// 1~100間の定数を一つセット
		Random rnd = new Random();
		int answer = rnd.nextInt(100) + 1;
		// "answer"の名前でセッションにセット
		session.setAttribute("answer", answer);
		 // コンソールに正解を出力する
		System.out.println("answer =" + answer);
		// 初期アクセス時画面に渡すデータはないので、Thymeleafに表示するビュー名を渡すだけ
		return "game";
	}
	
	// 「トライ！」クリック時
	@PostMapping("/challenge")
	// 回答はパラメータnumberに入っているので
	public ModelAndView challenge(@RequestParam("number") int number, ModelAndView mv) {
		// セッションから答えを取得
		int answer = (Integer)session.getAttribute("answer");
		
		// ユーザーの回答履歴を取得
		@SuppressWarnings("unchecked")
		List<History> histories = (List<History>)session.getAttribute("histories");
		if(histories == null) {
			histories = new ArrayList<>();
			session.setAttribute("histories", histories);
		}
		
		// 判定→回答履歴追加
		if(answer < number) {
			histories.add(new History(histories.size() + 1, number, "もっと小さいです"));
		}else if(answer == number) {
			histories.add(new History(histories.size() + 1, number, "正解です！"));
		}else {
			histories.add(new History(histories.size() + 1, number, "もっと大きいです"));
		}
		
		mv.setViewName("game");
		mv.addObject("histories", histories);
		return mv;
	}
}
