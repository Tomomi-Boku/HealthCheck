/**
 * ユーザーの新規登録をするプログラム
 * @author 朴智美
 */

import java.sql.*;
import java.util.*;

public class Add_user2 {
	public void addUser() {
		try {
			// 接続
			// characterEncoding=utf8 <- 文字エンコーディングとしてutf-8を使用
			// &useServerPrepStmts=true <- 静的プレースホルダを使用
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/healthcheck?useSSL=false&characterEncoding=utf8&useServerPrepStmts=true", 
				"root", ""
				);		 

			// Statementの代わりにPreparedStatementを使う
			// 最初にSQLを組み立ててしまう。後から値を入れる部分(ホルダー)を ? で示す
			PreparedStatement st1=conn.prepareStatement("INSERT INTO user VALUE (?, ?, ?, ?);");
			PreparedStatement st2=conn.prepareStatement("SELECT MAX(ID) FROM user;");

			Scanner scanner = new Scanner(System.in);	
			System.out.println("新規登録を行います。必要な情報を入力してください。");
		
			System.out.println("氏名（例：山田太郎）：");
			String name_str = scanner.nextLine();
			
			System.out.println("性別（例：0）：");
			System.out.println("性別一覧\n  0：男性\n　1：女性\n　2：トランスジェンダー\n　3：クエスチョニング");
			String gender_str = scanner.nextLine();
			
			System.out.println("生年月日（例：2022-11-16）：");
			String birthday_str = scanner.nextLine();
			
			// SQLを実行して、実行結果をResultSetに入れる
			ResultSet rs2=st2.executeQuery();
			
			// 既存のIDの最大値を取得し、＋1した値を新しいIDとして割り当てる
			while(rs2.next()){
				int user_ID = rs2.getInt("MAX(ID)");
				user_ID += 1;
				st1.setInt(1, user_ID);
				System.out.println("\n以下の内容で登録を完了しました。");
				System.out.print("ID：");
				System.out.println(user_ID);
				
				}
			
			// ここでSQLの ? の場所に値を埋め込んでいる
			st1.setString(2, name_str);
			System.out.println("氏名："+name_str);
			st1.setInt(3, Integer.parseInt(gender_str));
			System.out.println("性別："+gender_str);
			st1.setString(4, birthday_str);
			System.out.println("生年月日："+birthday_str);
			
         
			// SQLを実行して、実行結果をResultSetに入れる
			int res = st1.executeUpdate();
			System.out.println("データベースへの登録が完了しました。");

			// 終了処理
			rs2.close();
			st1.close();
			st2.close();
			conn.close();
		} catch (SQLException se) {
			System.out.println("SQL Error 1: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
	}
}
