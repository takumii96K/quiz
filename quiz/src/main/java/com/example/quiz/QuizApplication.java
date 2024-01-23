package com.example.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.quiz.entity.Quiz;
import com.example.quiz.service.QuizService;

@SpringBootApplication
public class QuizApplication {
	
	/**起動メソッド*/
	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args).getBean(QuizApplication.class).execute();
		
	}
	
	/**注入*/
	@Autowired
	QuizService service;
	
	/**実行メソッド*/
	private void execute() {
//		//登録処理
//		setup();
//		//全件表示
//		showList();
//		//1件取得
//		showOne();
//		//更新処理
//		updateQuiz();
//		//削除処理
//		deleteQuiz();
//		//クイズを実行する
		doQuiz();
	}
	/**== クイズを5件登録します ==*/
	private void setup() {
		//entityを作成
		Quiz quiz1 = new Quiz(null, "JAVAはオブジェクト指向言語である", true, "登録太郎");
		
		//entityを作成
		Quiz quiz2 = new Quiz(null,"「Spring Data」はデータアクセスに"
				+ "対する機能を提供する", true, "登録太郎");
		
		//entityを作成
		Quiz quiz3 = new Quiz(null, "プログラムがたくさん配置されている"
				+ "サーバーのことを「ライブラリという」", false, "登録太郎");
		
		//entityを作成
		Quiz quiz4 = new Quiz(null, "「@Component」は"
				+ "インスタンス生成アノテーションである", true, "登録太郎");
		
		//entityを作成
		Quiz quiz5 = new Quiz(null, "「Spring MVC」が実装している「デザインパターン」で"
				+ "全てのリクエストを１つのコントローラで受け取るパターンは"
				+ "「シングルコントローラ・パターン」である", true, "登録太郎");
		
		//リストにエンティティを格納
		List<Quiz> quizList = new ArrayList<>();
		//第一引数に格納先、第二引数は可変超引数なのでエンティティを記述
		Collections.addAll(quizList, quiz1, quiz2, quiz3, quiz4, quiz5);
		for(Quiz quiz :quizList) {
			//登録実行
			service.insertQuiz(quiz);
		}
	}
	
	/**== 全件処理 ==*/
	private void showList() {
		System.out.println("-- 全件取得開始 --");
		//レポジトリを使用して全件取得を実施、結果を取得
		Iterable<Quiz> quizzes = service.selectAll();
		for(Quiz quiz :quizzes) {
			System.out.println(quiz);
		}
		System.out.println("-- 全件取得完了 --");
	}
	
	/** == 1件取得 == */
	private void showOne() {
		System.out.println("-- 1件取得開始 --");
		//レポジトリを使用して１件取得を実施、結果を取得
		Optional<Quiz> quizOpt = service.selectOneById(1);
		//値存在チェック
		if(quizOpt.isPresent()) {
			System.out.println(quizOpt.get());
		}else {
			System.out.println("該当する問題が存在しません");
		}
		System.out.println("１件取得完了");
	}
	
	/** == 更新処理 == */
	private void updateQuiz() {
		System.out.println("-- 更新処理開始 --");
		//変更したエンティティを生成する
		Quiz quiz1 = new Quiz(1,"「Spring」はフレームワークですか？", true, "変更タロウ");
		//更新実行
		service.updateQuiz(quiz1);
		//更新確認
		System.out.println("更新したデータは" + quiz1 + "です");
		System.out.println(" -- 更新処理完了 -- ");
	}
	
	/**== 削除処理 ==*/
	private void deleteQuiz() {
		System.out.println("-- 削除処理開始 --");
		//削除実行
		service.deleteQuizById(2);
		System.out.println("-- 削除処理完了 --");
	}
	
	/**== ランダムでクイズを取得して、クイズの正解不正解を判定する ==*/
	private void doQuiz() {
		System.out.println("--クイズ１件取得開始--");
		//レポジトリを使用して１件取得を実施、結果を取得（戻り値はOptional)
		Optional<Quiz> quizOpt = service.selectOneRandomQuiz();
		//値存在チェック
		if(quizOpt.isPresent()) {
			System.out.println(quizOpt.get());
		}else {
			System.out.println("該当する問題が存在しません。");
		}
		System.out.println("-- クイズ１件取得完了 --");
		
		//解答を実施
		Boolean myAnswer = false;
		Integer id = quizOpt.get().getId();
		if(service.checkQuiz(id, myAnswer)) {
			System.out.println("正解です！");
		}else {
			System.out.println("不正解です・・・");
		}
	}
}
