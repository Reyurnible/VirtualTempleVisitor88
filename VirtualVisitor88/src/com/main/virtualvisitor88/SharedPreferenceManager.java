package com.main.virtualvisitor88;import java.util.Date;import com.main.virtualvisitor88.Temples.Temple;import android.app.Activity;import android.content.Context;import android.content.SharedPreferences;public class SharedPreferenceManager extends Activity{	//sp key	private final String SP_STEP = "STEP"; 	private final String SP_TEMPLE = "TEMPLE"; 	private final String SP_STARTDAY = "DAY";	private final String SP_NAME = "HUMAN_NAME";	private final String SP_WEIGHT = "HUMAN_WEIGHT"; 	private final String SP_HEIGHT = "HUMAN_HEIGHT";	private final String SP_SEX = "HUMAN_SEX";	private final String SP_TOTALCALORY = "TOTALCALORY";		SharedPreferences pref;	SharedPreferences.Editor editor;		SharedPreferenceManager(String spName,int mode,Context context){		// SharedPrefernces の取得  	    pref = context.getSharedPreferences(spName, mode);	}	//現在の歩数の取得	int getStepCount(){		int count = pref.getInt(SP_STEP, 0);		return count;	}	//現在の歩数の保存	void setStepCount(int step){	    editor = pref.edit(); 	    editor.putInt(SP_STEP, step).commit();	}	//現在のお寺の取得1~88	Temple getTemple(){		int id = pref.getInt(SP_TEMPLE, 1);		return Temples.getTemple(id);	}	//現在のお寺の保存	void setStepCount(Temple temple){		 editor = pref.edit(); 		 editor.putInt(SP_TEMPLE, temple.id).commit();	}	//日付を受け取る	Date getDate(){		long time = pref.getLong(SP_STARTDAY,0);		Date date = new Date();		date.setTime(time);		return date;	}	//日付を保存する	void setDate(Date date){		editor = pref.edit();		editor.putLong(SP_STARTDAY,date.getTime()).commit();	}	//名前を取得する	String getName(){		String name = pref.getString(SP_NAME, "OHENRO");		return name;	}	//名前をセットする	void setName(String name){		editor = pref.edit();		editor.putString(SP_NAME,name).commit();	}	//体重を取得	int getWeight(){		int weight = pref.getInt(SP_WEIGHT, 60);		return weight;	}	//体重をセット	void setWeight(int weight){		editor = pref.edit();		editor.putInt(SP_WEIGHT,weight).commit();	}	//身長を取得	int getHeight(){		int height = pref.getInt(SP_HEIGHT, 60);		return height;	}	//身長をセット	void setHeight(int height){		editor = pref.edit();		editor.putInt(SP_HEIGHT,height).commit();	}	//性別を取得	boolean getSex(){		boolean sex = pref.getBoolean(SP_SEX, false);		return sex;	}	//性別を保存	void setSex(boolean sex){		editor = pref.edit();		editor.putBoolean(SP_SEX,sex).commit();	}	//現在のトータルカロリーを取得	int getTotalCaloly(){		int caloly = pref.getInt(SP_TOTALCALORY, 0);		return caloly;	}	//トータルカロリーを保存	void setTotalCaloly(int caloly){		editor = pref.edit();		editor.putInt(SP_TOTALCALORY,caloly).commit();	}	}