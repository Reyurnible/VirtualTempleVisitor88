package com.main.virtualvisitor88;

import java.text.NumberFormat;

import com.main.virtualvisitor88.Temples.Temple;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;



@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity implements OnClickListener{
	//画面の大きさ
	private static int dispWidth,dispHeight;
	//ピクセルとメートルを変換する係数と一歩の大きさ
	final Double ScaleMtoP = 1.2;
	//この値は設定から呼び出す。
	Double stepWidth=0.7;
	
	
	private CharSequence mTitle;
	SensorManager	sm;
	WalkCount		wc;
	private activityCallback mService;
	boolean mBind 	= false;
<<<<<<< HEAD

	TextView infoDay;
	TextView infoSteps;
	TextView infoDistance;
	TextView infoTemple;
	ImageView templeImage;
	TextView infoNextTemple;
	TextView infoNextTempleDistance;
=======
	
	//現在の動的な情報
	int walkCount=0;
	Temple nowTemple;
	
	//各ビューパーツ
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private TextView infoDay;
	private TextView infoSteps;
	private TextView infoDistance;
	private TextView infoTemple;
	private ImageView templeImage;
	private TextView infoNextTemple;
	private TextView infoNextTempleDistance;
	
	private Button settingButton;
>>>>>>> f50a694374ade68b83a68835a519637eacbb5b5c
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//タイトルの設定
		mTitle = getTitle();
		//画面の大きさを取得
		getDisplaySize();
		//ドロわーレイアウトの取得
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
<<<<<<< HEAD
=======
        //各ビューのIDとヒモ付
>>>>>>> f50a694374ade68b83a68835a519637eacbb5b5c
        setFintViewID();
        //ドロワー内の設定呼び出しボタンがクリックされたとき用のリスナーをセット
        settingButton.setOnClickListener(this);
<<<<<<< HEAD

=======
        //ドロワーの設定
>>>>>>> f50a694374ade68b83a68835a519637eacbb5b5c
		mDrawerToggle = new ActionBarDrawerToggle(
				this,
				mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close){
                //ドロワーが閉じた時の処理  
                public void onDrawerClosed(View view) {  
                    
                }
                //ドロワーが開いた時の処理  
                public void onDrawerOpened(View drawerView) {
                	//ドロワー内の歩数などの情報を更新する
                	setInfo();
                }
			};
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
<<<<<<< HEAD

=======
        
>>>>>>> f50a694374ade68b83a68835a519637eacbb5b5c
        WCSample();		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		//マップの表示を行う 現在のお寺と歩数で場所を特定できる。
		setMapImage(nowTemple,walkCount);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//SPにデータを保存する
	}
	//ディスプレイの大きさを取得
	private void getDisplaySize(){
		WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		dispWidth = 500;
		dispHeight = 1000;
		if(Build.VERSION.SDK_INT >= 13){
			Point size = new Point();
			disp.getSize(size);
			dispWidth=size.x;
			dispHeight=size.y;
		}else{
			dispWidth=disp.getWidth();
			dispHeight=disp.getHeight();
		}
	}
	//IDとのヒモ付を一括して行う
	void setFintViewID(){
		infoDay =(TextView)findViewById(R.id.infoDay);
		infoSteps = (TextView)findViewById(R.id.infoSteps);
		infoDistance= (TextView)findViewById(R.id.infoDistance);
		infoTemple = (TextView)findViewById(R.id.infoTemple);
		templeImage = (ImageView)findViewById(R.id.templeImage);
		infoNextTemple = (TextView)findViewById(R.id.infoNextTemple);
		infoNextTempleDistance = (TextView)findViewById(R.id.infoNextTempleDistance);
		settingButton = (Button)findViewById(R.id.buttonSetting);
	}
	//ドロワーの中の情報セット
	void setInfo(){
		infoSteps.setText("歩数:"+walkCount);
		NumberFormat nf;
		nf=NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);
		if((walkCount*stepWidth)>1000){
			
			infoDistance.setText("距離:"+nf.format((walkCount*stepWidth)/1000)+"km");
		}else{
			infoDistance.setText("距離:"+nf.format((walkCount*stepWidth))+"m");
		}
	}
	//ドロワーの中の寺情報セット
	void setTemples(Temple temple){
		infoTemple.setText("現在のお寺:"+temple.name);
		templeImage.setImageBitmap(Temples.getImage(temple.id,this));
		infoNextTemple.setText("次のお寺:"+Temples.getTemple(temple.id+1).name);
		infoNextTempleDistance.setText("次のお寺まで:"+Temples.getDistance(temple, Temples.getTemple(temple.id+1))+"km");
	}
	//メインコンテンツの画像をセットする
	void setMapImage(Temple temple,int steps){
		Point center = MapCalculation.getCentetPoint(temple, Temples.getTemple(temple.id+1), steps*stepWidth/ScaleMtoP);
		Bitmap mapImage = MapCalculation.getMapBitmap(center,dispWidth,dispHeight,this);
		ImageView mapImageView = (ImageView)findViewById(R.id.imageMap);
		mapImageView.setImageBitmap(mapImage);
	}
<<<<<<< HEAD
=======
	
	void WCSample(){
		startService();
//		stopService();
	}

	@Override
	public void setTitle(CharSequence title) {
	    mTitle = title;
	    getActionBar().setTitle(mTitle);
	}
>>>>>>> f50a694374ade68b83a68835a519637eacbb5b5c
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = false;
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);  
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  
        // Pass the event to ActionBarDrawerToggle, if it returns  
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        // Handle your other action bar items...  
        return super.onOptionsItemSelected(item);  
    }
    
<<<<<<< HEAD
=======
    //ServiceConnectionを拡張したclassを実装する
    private ServiceConnection mConnection = new ServiceConnection(){
    	//ServiceConnection#onServiceConntected()の引数で渡される
    	//IBinder objectを利用しAIDLで定義したInterfaceを取得
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = activityCallback.Stub.asInterface(service);
			try{
				//取得したinterfaceを利用しService用のAIDL fileで定義されたmethodでObserver登録/解除
				mService.setObserver(wCallback);
			}catch(RemoteException e){
			}
		}
>>>>>>> f50a694374ade68b83a68835a519637eacbb5b5c


	private RemoteCallbackList<walkCallback> walkCallBack = new RemoteCallbackList<walkCallback>();	
<<<<<<< HEAD

	void WCSample(){
		startService();
//		stopService();		
	}
=======
>>>>>>> f50a694374ade68b83a68835a519637eacbb5b5c
	
    private walkCallback wCallback = new walkCallback.Stub() { //Åy1Åz
		@Override
		public void updateWalkCount(int walkNum) throws RemoteException {
			// TODO Auto-generated method stub
<<<<<<< HEAD
			Log.i("walkCallback", "call!!");
=======
			Log.i("test", "call!!");
			walkCount++;
			setInfo();
>>>>>>> f50a694374ade68b83a68835a519637eacbb5b5c
		}
    }; 
	
	void startService(){
		bindService(new Intent( MainActivity.this, WalkService.class ), mConnection, BIND_AUTO_CREATE);		
		startService( new Intent( MainActivity.this, WalkService.class ));	
		mBind = true;
	}
	void stopService(){
		if(mBind){
			//Context#UnbindService()でServiceとのConnectionを破棄
			unbindService(mConnection);
			stopService( new Intent( MainActivity.this, WalkService.class ) );
			mBind = false;
		}		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.buttonSetting:
				Intent intent = new Intent(this,SettingActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}
}
