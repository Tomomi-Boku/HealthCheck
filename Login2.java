/**
 * ログインに責任を持つクラス（新規の記録・目標追加を呼び出す）
 * @author 朴智美
 */

import java.sql.*;
import java.util.*;


public class Login2 {
	private int userID;
	private String userName;
	
	public void login() {
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
			PreparedStatement st=conn.prepareStatement("SELECT user.ID, name,  gender.gender, birthday FROM user, gender" 
				+" WHERE user.ID = ?" 
				+" AND user.name = ?" 
				+" AND user.gender = gender.number;");
				
			PreparedStatement st2 =conn.prepareStatement("SELECT name FROM user	WHERE ID = ?;" );

			Scanner scanner = new Scanner(System.in);	
			
			//入力されたIDと氏名が正しいかチェックする
			Loop1:
			while(true){
				System.out.println("\nログインをします。");
				System.out.println("ID：");
				String user_ID = scanner.nextLine();
				this.userID = Integer.parseInt(user_ID);
				
				System.out.println("氏名：");
				this.userName = scanner.nextLine();
				
				st2.setInt(1, this.userID);
				ResultSet r2 = st2.executeQuery();
				

				while(r2.next()) {
					if(this.userName.equals(r2.getString("name"))){
						System.out.println("ログインに成功しました。");
					
						// 終了処理
						st2.close();
						r2.close();
						break Loop1;
					}
					
					else{
						System.out.println("IDと名前が違います。再度やり直してください。");
					}
					
				}
				
				
			}
			
			//正しければ、ログインしてユーザーの情報を表示する。
			st.setInt(1, this.userID); 
			st.setString(2, this.userName); 
			ResultSet r = st.executeQuery();
			
			System.out.println("\nこんにちは、"+this.userName+"さん。あなたの登録情報は以下になります。");
			while(r.next()) {
				System.out.println(
						"ID："+r.getInt("ID")+"\n"+
						"氏名："+r.getString("name")+"\n"+
						"性別："+r.getString("gender")+"\n"+
						"生年月日："+r.getString("birthday"));
						}
			
			Loop:
			while(true){
				System.out.println("\n＝＝＝ログインメニュー＝＝＝");
				System.out.println("1: 新しい記録を追加する");
				System.out.println("2: 新しい目標を立てる");
				System.out.println("3: 今までの推移を見る");
				System.out.println("0: 戻る");
				System.out.println("メニュー番号を入力してください: ");
				
				Add_record2 e3 = new Add_record2(this.userID);
				Add_target e4 = new Add_target(this.userID);
				Accumulation e5 = new Accumulation(this.userID);
		
				String line = scanner.nextLine();
				switch(line) {
				case "0":
					System.out.println("最初の画面に戻ります。");
					break Loop;
					
				case "1":
					e3.addRecord();
					break;
				
				case "2":
					e4.addTarget();
					break;
					
				case "3":
					e5.Accumulator();
					break;
				
				default:
					System.out.println("入力が正しくありません。");	
				}
			}
			
			
			// 終了処理
			st.close();
			r.close();
			conn.close();
		} catch (SQLException se) {
			System.out.println("SQL Error 1: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
	}
}
