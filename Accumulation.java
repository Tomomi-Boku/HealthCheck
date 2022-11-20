/**
 *評価の累積を表示する持つクラス
 * @author 前田
 */

import java.util.*;
import java.sql.*;
import java.sql.Date;

//目標以降のrecord_dateを全部取ってきて、それを配列に入れて、for文かwhile文で回して、引数にa[i]という形で入れる
public class Accumulation{
	
	private int userID;
	
	//コンストラクタ
	public Accumulation(int userID){
		this.userID = userID;
	}
	
	public void Accumulator(){
			Comparison comp = new Comparison();
			try {
				System.out.println("\n今までの評価の推移を表示します。[点]");
				System.out.println("日付　　　　　腹囲　　　身長　　　体重　　  最高血圧  最低血圧  血糖値　  BMI   　　筋肉量　  体脂肪率");
				Connection conn = DriverManager.getConnection(
							"jdbc:mysql://localhost/healthcheck?useSSL=false&characterEncoding=utf8&useServerPrepStmts=true", 
							"root", ""
						);	
				PreparedStatement st=conn.prepareStatement("SELECT record_date FROM record WHERE ID="+userID+" AND record_date>=(select max(created_date) from target WHERE ID="+userID+");");
				ResultSet rs=st.executeQuery();
				while(rs.next()){
					Date rs1 = rs.getDate("record_date");
					System.out.print(rs1);
					comp.compare(userID,rs1,0);
				}
				rs.close();
				st.close();
				conn.close();
			}catch (SQLException se) {
				System.out.println("SQL Error 1: " + se.toString() + " "
					+ se.getErrorCode() + " " + se.getSQLState());
			} catch (Exception e) {
				System.out.println("Error: " + e.toString() + e.getMessage());
			}
		
	}
}