/**
 * 健康管理アプリケーションの本体となるプログラム
 * @author 朴智美
 */

import java.util.*;
import java.sql.*;

public class HealthCheck_Pro {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		Login2 e1 = new Login2();
		Add_user2 e2 = new Add_user2();
		//Add_record2 e3 = new Add_record2();
		//Add_target e4 = new Add_target();
		
		Loop:
		while(true){
			System.out.println("\n=== 健康管理システム ===");
			System.out.println("メニューを選んでください: ");
			System.out.println("1: ログイン");
			System.out.println("2: 新規登録");
			//System.out.println("3: 「テスト」記録を追加する");
			//System.out.println("4: 「テスト」目標を追加する");
			System.out.println("0: 終了する");
			System.out.println("メニュー番号を入力してください: ");
	
			String line = scanner.nextLine();
			switch(line) {
				case "0":
					System.out.println("\nシステムを終了します。またのご利用をお待ちしております。");
					break Loop;
				
				case "1":
					e1.login();
					break;
					
				case "2":
					e2.addUser();
					break;
				/*	
				case "3":
					e3.addRecord();
					break;
					
				case "4":
					e4.addTarget();
					break;
				*/
				default:
					System.out.println("入力が正しくありません。");
			}
		}
		
		
	}
}
