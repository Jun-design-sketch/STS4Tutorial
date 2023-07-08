package com.example.demo;
import lombok.Data;

// フォームの内容を受け取るフォームクラスを作成する。
// これと@ModelAttributeアノテーションを組み合わせ、フォームからのパラメータ取得を簡潔にする
@Data
public class RegistData {
	// フォーム部品と同じ名前のプロパティを定義する
	// データ型は入力される値を考慮して決める
	private String name;
	private String password;
	private int gender;
	private int area;
	private int[] interest;
	private String remarks;
}
