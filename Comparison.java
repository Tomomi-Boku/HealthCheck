/**
 *評価を算出するクラス（一日分・今までの分）
 * @author 高橋、山本珠理
 */

import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Comparison {
	public float height,weight;
	public String[] a =new String[10];

	public void compare(int id,Date date,int order){
		int j=0;
		try {
		
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/healthcheck?useSSL=false&characterEncoding=utf8&useServerPrepStmts=true", 
				"root", ""
				);		
			PreparedStatement st=conn.prepareStatement("SELECT * FROM target;");

			ResultSet rs=st.executeQuery();
			ResultSetMetaData rsm = rs.getMetaData();
			
			int i=-1;
			for (int iLoop = 2 ;iLoop < rsm.getColumnCount() ; iLoop ++){
				i=i+1;
				a[i]=rsm.getColumnName(iLoop + 1);
				
			}
			Culculate culculator=new Culculate(height,weight,a);
			for (String str: a){
				
				if(str.equals("target_date")){
					j=j;
				}
				else if(str.equals("BMI")){
					break;
				}
				else
				{
					
					PreparedStatement st1=conn.prepareStatement("SELECT w1."+str+",w2."+str+" FROM target w1, record w2 WHERE w2.ID=? AND record_date=? AND w1.created_date=(select max(created_date) from target WHERE ID=?);");
					
					st1.setInt(1,id);
					st1.setDate(2,date);
					st1.setInt(3,id);
					
					ResultSet rs1=st1.executeQuery();
					
					
					while(rs1.next()){
						if(j<7)
						{
							float target_rs = rs1.getFloat("w1."+str+"");
							float record_rs = rs1.getFloat("w2."+str+"");
							if(order==0) culculator.showList_Result(record_rs,target_rs,j); //Accumulationから呼ばれている時
							else if(order==1) culculator.showOne_Result(record_rs,target_rs,j); //一つの記録の結果のみを表示したい時

						}
					}
					
					rs1.close();
					st1.close();
				}
				j++;
			}
			float record_rs=0;
			for(int k=j;k<10;k++){
				PreparedStatement st2=conn.prepareStatement("SELECT "+a[k]+" FROM target where created_date=(select max(created_date) from target WHERE ID=?);");
				st2.setInt(1,id);
				ResultSet rs2=st2.executeQuery();
				
					while(rs2.next())
					{
						float target_rs = rs2.getFloat(a[k]);
						if(order==0) culculator.showList_Result(2,target_rs,k); //Accumulationから呼ばれている時
						else if(order==1) culculator.showOne_Result(2,target_rs,k); //一つの記録の結果のみを表示したい時
					}
				rs2.close();
				st2.close();
			}
			rs.close();
			st.close();
		} catch (SQLException se) {
			System.out.println("SQL Error 1: " + se.toString() + " "+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
	}
		
}
