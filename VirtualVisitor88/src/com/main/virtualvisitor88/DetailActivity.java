package com.main.virtualvisitor88;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity{
	
	TextView title;
	TextView detail;
	ImageView shrine;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		int id = (Integer)getIntent().getSerializableExtra("id");
		
		TypedArray typedArray = getResources().obtainTypedArray(R.array.temple_all);  
		int length = typedArray.length();  
		int resourceId = typedArray.getResourceId(id,0);    
		// 配列の値を取得(ここではarray_sub1の各値)  
		String[] array_str = getResources().getStringArray(resourceId);  		
		TypedArray dataArray = getResources().obtainTypedArray(resourceId); 
		Resources r = getResources();
	    Bitmap bmp = BitmapFactory.decodeResource(r, dataArray.getResourceId(2,0));		
		
		title  = (TextView) this.findViewById(R.id.shrine_name);
		detail = (TextView) this.findViewById(R.id.shrine_detail);
		shrine = (ImageView)this.findViewById(R.id.shrine_image);

		title.setText(array_str[0]);
		detail.setText(array_str[1]);
		shrine.setImageBitmap(bmp);
	}
}
