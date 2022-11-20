/**
 * 新規記録を登録するプログラム
 * @author 朴智美, 前田
 *modified by 朴智美：ユーザーの入力時のミス削減のための補助表示をするために、入力時のメッセージを具体例＋単位に変更。
 */

import java.sql.Date;
import java.sql.*;
import java.util.*;


public class Add_record2 {
	private int userID;
	
	public Add_record2(int userID){
		this.userID = userID;
	}
	
	public void addRecord() {
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
			PreparedStatement st=conn.prepareStatement("INSERT INTO record VALUE (?,?,?,?,?,?,?,?);");
			
			
			// ID   | record_date | waist | height | weight | HiBloodpre | LowBloodpre | Bloodsuger
			Scanner scanner = new Scanner(System.in);	
			System.out.println("\n本日分の記録を追加します。");
		
			System.out.println("ウエスト（例：[ 75 ]cm）：");
			String waist_r_s = scanner.nextLine();
			
			System.out.println("身長（例：[ 160 ]cm）：");
			String height_r_s = scanner.nextLine();
			
			System.out.println("体重（例：[ 55 ]kg）：");
			String weight_r_s = scanner.nextLine();
			
			System.out.println("最高血圧（例：[ 130 ]mmHg）：");
			String hiBloodpre_r_s = scanner.nextLine();
			
			System.out.println("最低血圧（例：[ 70 ]mmHg）：");
			String lowBloodpre_r_s = scanner.nextLine();
			
			System.out.println("血糖値（例：[ 100 ]mg/dL）：");
			String bloodsuger_r_s = scanner.nextLine();
			
			//System.out.println("IDを入力してください");
			//String user_ID = scanner.nextLine();
			
			System.out.println("本日の日付を入力してください。（例：2022-11-16）：");
			String day_str = scanner.nextLine();
			
			st.setInt(1, userID); 
			st.setString(2,day_str);
			st.setFloat(3, Float.parseFloat(waist_r_s)); 
			st.setFloat(4, Float.parseFloat(height_r_s)); 
			st.setFloat(5, Float.parseFloat(weight_r_s)); 
			st.setInt(6, Integer.parseInt(hiBloodpre_r_s));
			st.setInt(7, Integer.parseInt(lowBloodpre_r_s));
			st.setInt(8, Integer.parseInt(bloodsuger_r_s));
			
         
			// SQLを実行して、実行結果をResultSetに入れる
			int res = st.executeUpdate();
			System.out.println("データベースへの登録が完了しました。");
			
			System.out.println("\n本日分のスコアを表示します。");
			Comparison e1 = new Comparison();
			Date date = Date.valueOf(day_str);
			e1.compare(userID,date,1);

			// 終了処理
			st.close();
			conn.close();
		} catch (SQLException se) {
			System.out.println("SQL Error 1: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
	}
}
