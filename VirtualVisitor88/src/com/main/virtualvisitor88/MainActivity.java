package com.main.virtualvisitor88;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

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
import android.widget.FrameLayout;
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
	
	//OHENRO INFO
	private final String SP_KEY = "OHENRO_KEY";
	
	private CharSequence mTitle;
	SensorManager	sm;
	WalkCount		wc;
	private activityCallback mService;
	static boolean mBind 	= false;
	
	//現在の動的な情報
	static int walkCount=0;
	Temple nowTemple;
	Date startDay;
	
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
	private ImageView mapImageView;
	
	private Button settingButton;	

	private Bitmap mapImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//タイトルの設定
		mTitle = getTitle();
		//ドロわーレイアウトの取得
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        //各ビューのIDとヒモ付
        setFintViewID();
        mapImageView.setImageBitmap(null);
        //ドロワー内の設定呼び出しボタンがクリックされたとき用のリスナーをセット
        settingButton.setOnClickListener(this);
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
        loadData();
        setInfo();
        setTemple(nowTemple);
        setDate();
		startService();		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// menu/activity_main.xmlからメニューを作成
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		settingButton.setBackgroundResource(R.drawable.parts_setting);
		if(dispWidth!=0||dispHeight!=0){
			//マップの表示を行う 現在のお寺と歩数で場所を特定できる。
			setMapImage(nowTemple,walkCount);
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//SPにデータを保存する
	}
	//ディスプレイの大きさを取得
	@Override
	public void onWindowFocusChanged(boolean paramBoolean) {
		super.onWindowFocusChanged(paramBoolean);  
		dispWidth = 500;
		dispHeight = 1000;
		try{
			FrameLayout contentFrame = (FrameLayout)findViewById(R.id.content_frame);
			dispWidth = contentFrame.getWidth();
			dispHeight = contentFrame.getHeight();
			setMapImage(nowTemple,walkCount);
		}catch(Exception e){
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
		mapImageView  = (ImageView)findViewById(R.id.imageMap);
	}
	void setDate(){
		Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        //一日のミリ秒
        long one_day_time = 1000*60*60*24;
        //差分を一日の時間で割る
        long diffDays = (date.getTime()-startDay.getTime())/one_day_time;
        infoDay.setText("日数:"+diffDays+"日目");
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
	void setTemple(Temple temple){
		infoTemple.setText("現在のお寺:\n"+temple.name);
		templeImage.setImageBitmap(Temples.getImage(temple.id,this));
		infoNextTemple.setText("次のお寺:\n"+Temples.getTemple(temple.id+1).name);
		NumberFormat nf;
		nf=NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);
		infoNextTempleDistance.setText("次のお寺まで:\n"+nf.format(Temples.getDistance(temple, Temples.getTemple(temple.id+1)))+"km");
		setTempleLink(temple);
	}
	void setTempleLink(final Temple temple){
    	ImageView templeImg = (ImageView)findViewById(R.id.templeImage);
    	templeImg.setClickable(true);
    	templeImg.setOnClickListener(this);
	}
	void loadData(){
		SharedPreferenceManager spm = new SharedPreferenceManager(SP_KEY, Activity.MODE_PRIVATE,this);
		walkCount = spm.getStepCount();
		nowTemple = spm.getTemple();
		startDay = spm.getDate();
		//歩幅（身長*0.5)
		stepWidth=spm.getHeight()*0.5;
	}
	//メインコンテンツの画像をセットする
	void setMapImage(Temple temple,int steps){
		if(temple!=null){
			Point center = MapCalculation.getCentetPoint(temple, Temples.getTemple(temple.id+1), steps*stepWidth/ScaleMtoP);
			mapImage = MapCalculation.getMapBitmap(center,dispWidth,dispHeight,this);
			mapImageView.setImageBitmap(mapImage);
		}
	}	
	
	@Override
	public void setTitle(CharSequence title) {
	    mTitle = title;
	    getActionBar().setTitle(mTitle);
	}
	
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
		
        //Serviceを動かしてるProcessがcrashするかkillされない限り呼ばれない
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
		}
    };

	private RemoteCallbackList<walkCallback> walkCallBack = new RemoteCallbackList<walkCallback>();	
	
    private walkCallback wCallback = new walkCallback.Stub() { //Åy1Åz
		@Override
		public void updateWalkCount(int walkNum) throws RemoteException {
			// TODO Auto-generated method stub
			Log.i("test", "call!!");
			walkCount++;
			setInfo();
		}
    }; 
	
	void startService(){
		if(mBind == false && walkCount == 0){
			bindService(new Intent( MainActivity.this, WalkService.class ), mConnection, BIND_AUTO_CREATE);		
			startService( new Intent( MainActivity.this, WalkService.class ));	
		}
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
	
	public void drawCharacter(){
		
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.buttonSetting:
				settingButton.setBackgroundResource(R.drawable.parts_setting_f);
				Intent intent = new Intent(this,SettingActivity.class);
				startActivity(intent);
				mapImageView.setImageBitmap(null);
				mapImage.recycle();
				mapImage=null;
				break;
			case R.id.templeImage:
				int id = nowTemple.id - 1;
		        Intent objIntent = new Intent(getApplicationContext(),DetailActivity.class);
		        objIntent.putExtra("id",id);
		        startActivity(objIntent);
				break;
			default:
				break;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
        if (mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
		if(item.getItemId() == R.id.menu_map){ // if使うとエラー（itemがInt形式なため）
			Intent intent = new Intent(this,MapActivity.class);
			startActivity(intent);
		}
		// Handle your other action bar items...  
        return super.onOptionsItemSelected(item);
	}
	
	public void initWalkNum(){
		stopService();
		walkCount = 0;
		startService();
		return;
	}
}
