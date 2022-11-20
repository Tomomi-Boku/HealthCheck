/**
 *評価の整合性を保証するクラス
 * @author 高橋、山本珠理
 *modified by 朴智美：結果表示を見やすいようにするために、110-111行目で「項目：点数」に変更。
 */

public class Culculate{
	public float height,weight;
	public String[] a;

	public String culumcheck(String name,float x)
	{
		String culmname="none";
		switch(name)
		{
			case "waist":
				culmname="腹囲";
				break;
			case "height":
				culmname="身長";
				height=x;
				break;
			case "weight":
				culmname="体重";
				weight=x;
				break;
			case "HiBloodpre":
				culmname="最高血圧";
				break;
			case "LowBloodpre":
				culmname="最低血圧";
				break;
			case "Bloodsuger":
				culmname="血糖値";
				break;
			case "BMI":
				culmname="BMI";
				break;
			case "muscle":
				culmname="筋肉量";
				break;
			case "body_fat":
				culmname="体脂肪率";
				break;
		}
		return culmname;
	}

	public Culculate(float height,float weight,String[] a){
		this.height = height;
		this.weight = weight;
		this.a = a;
	}

	public float culculate_BMI(float weight, float height)
	{
		//体重/身長^2
		return weight*10000/(height*height);
	}

	public float culculate_body_Fat(float weight, float height)
	{
		//体脂肪率 ＝ （実際の体重 − 標準体重） ÷ 標準体重 × 10
		//標準体重（kg）＝身長（m）×身長（m）×22
		float criteria,fat;
		criteria = height*height*22;
		fat = (weight - criteria) / criteria * 10;
		if(fat<0) fat = fat*-1;
		return fat;
	}

	public float culculate_muscle(float weight, float height)
	{
		// 体重(kg)×体脂肪率(％)＝体脂肪量(kg)
		// 体重(kg)－体脂肪量(kg)＝除脂肪体重(kg)
		// 除脂肪体重(kg)／2＝筋肉量(kg)
		float bodyFat;
		bodyFat=culculate_body_Fat(weight,height);
		return (weight-weight*bodyFat*1/100)/2;
	}

	public float culculate_work_parsent(float record_rs, float target_rs, int index)
	{
		if(index==7) record_rs=culculate_BMI(weight,height);
		if(index==8) record_rs=culculate_muscle(weight,height);
		if(index==9) record_rs=culculate_body_Fat(weight,height);

		float res = record_rs/target_rs;
		res=res-1;
		if (res>=2) return -1;
		else if(res==0) return 100;
		else if (res<0) res=res*-1;
		res=res*100-100;
		if(res<0) res=res*-1;
		return res;
	}

	public void showList_Result(float record_rs, float target_rs, int index){
		float score = culculate_work_parsent(record_rs,target_rs,index);
		String culumname = culumcheck(a[index],record_rs);
		if(score==-1) System.out.print("--"+"　");
		else if(score==100) System.out.print("　　100 　");
		else System.out.print(String.format("　　%.1f",score)+"　");
		if(index==9) System.out.println(" ");
	}

	public void showOne_Result(float record_rs, float target_rs, int index){
		float score = culculate_work_parsent(record_rs,target_rs,index);
		String culumname = culumcheck(a[index],record_rs);
		if(score==-1) System.out.println(culumname+"：もう少し堅実的な目標を立てましょう");
		else System.out.println(culumname+"："+String.format("%.1f",score)+"点");
	}
}	