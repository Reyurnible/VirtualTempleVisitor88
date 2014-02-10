package com.main.virtualvisitor88;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;



@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity{
	//画面の大きさ
	private static int dispWidth,dispHeight;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mTitle;
	SensorManager	sm;
	WalkCount		wc;
	TextView 		tv;
	private activityCallback mService;
	boolean mBind 	= false;
<<<<<<< HEAD

=======
	
>>>>>>> 19df174eb4a43cf5c363a4c23ac0c4624f0d0f8f
	TextView infoDay;
	TextView infoSteps;
	TextView infoDistance;
	TextView infoTemple;
	ImageView templeImage;
	TextView infoNextTemple;
	TextView infoNextTempleDistance;

	final Double ScaleMtoP = 1.2;
	Double stepWidth=0.7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTitle = getTitle();
		getDisplaySize();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(
				this,
				mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close){
                	  
                /** Called when a drawer has settled in a completely closed state. */  
                public void onDrawerClosed(View view) {  
                        setTitle(mTitle);  
                    }  
          
                    /** Called when a drawer has settled in a completely open state. */  
                     public void onDrawerOpened(View drawerView) {  
                        setTitle(mTitle);  
                    } 
			};
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
<<<<<<< HEAD
        
=======
        /*
        View view = this.getLayoutInflater().inflate(R.layout.info_window_activity, null);
        addContentView(view, new　LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT))
        */
>>>>>>> 19df174eb4a43cf5c363a4c23ac0c4624f0d0f8f
        WCSample();		
	}




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

	void setFintViewID(){
		infoDay =(TextView)findViewById(R.id.infoDay);
		infoSteps = (TextView)findViewById(R.id.infoSteps);
		infoDistance= (TextView)findViewById(R.id.infoDistance);
		infoTemple = (TextView)findViewById(R.id.infoTemple);
		templeImage = (ImageView)findViewById(R.id.templeImage);
		infoNextTemple = (TextView)findViewById(R.id.infoNextTemple);
		infoNextTempleDistance = (TextView)findViewById(R.id.infoNextTempleDistance);
	}
	
	void setTemples(Temple temple){
		infoTemple.setText("現在のお寺:"+temple.name);
		templeImage.setImageBitmap(Temples.getImage(temple.id,this));
		infoNextTemple.setText("次のお寺:"+Temples.getTemple(temple.id+1).name);
		infoNextTempleDistance.setText("次のお寺まで:"+Temples.getDistance(temple, Temples.getTemple(temple.id+1))+"km");
	}
	
	void setMapImage(Temple temple,int steps){
		Point center = MapCalculation.getCentetPoint(temple, Temples.getTemple(temple.id+1), steps*stepWidth/ScaleMtoP);
		Bitmap mapImage = MapCalculation.getMapBitmap(center,dispWidth,dispHeight,this);
		ImageView mapImageView = (ImageView)findViewById(R.id.imageMap);
		mapImageView.setImageBitmap(mapImage);
	}
	private RemoteCallbackList<walkCallback> walkCallBack = new RemoteCallbackList<walkCallback>();	

<<<<<<< HEAD
=======
	void WCSample(){
		startService();
//		stopService();
	}
	
>>>>>>> 19df174eb4a43cf5c363a4c23ac0c4624f0d0f8f
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = false;
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
	public void setTitle(CharSequence title) {
	    mTitle = title;
	    getActionBar().setTitle(mTitle);
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
    
	private walkCallback wCallback = new walkCallback.Stub() { //【1】
		@Override
		public void updateWalkCount(int walkNum) throws RemoteException {
			// TODO Auto-generated method stub
			Log.i("test", "call!!");
		//	tv.setText(""+walkNum);
		}
    };    
    
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

	void WCSample(){
//		tv  = new TextView(this);
//		tv.setText("0");
//	    setContentView(tv);
		startService();
//		stopService();		
	}
	
    private walkCallback wCallback = new walkCallback.Stub() { //Åy1Åz
		@Override
		public void updateWalkCount(int walkNum) throws RemoteException {
			// TODO Auto-generated method stub
			Log.i("test", "call!!");
		}
    }; 
    
    //ServiceConnectionÇägí£ÇµÇΩclassÇé¿ëïÇ∑ÇÈ
    private ServiceConnection mConnection = new ServiceConnection(){
    	
    	//ServiceConnection#onServiceConntected()ÇÃà¯êîÇ≈ìnÇ≥ÇÍÇÈ
    	//IBinder objectÇóòópÇµAIDLÇ≈íËã`ÇµÇΩInterfaceÇéÊìæ
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = activityCallback.Stub.asInterface(service);
			try{
				//éÊìæÇµÇΩinterfaceÇóòópÇµServiceópÇÃAIDL fileÇ≈íËã`Ç≥ÇÍÇΩmethodÇ≈Observerìoò^/âèú
				mService.setObserver(wCallback);
			}catch(RemoteException e){
			}
		}
		
        //ServiceÇìÆÇ©ÇµÇƒÇÈProcessÇ™crashÇ∑ÇÈÇ©killÇ≥ÇÍÇ»Ç¢å¿ÇËåƒÇŒÇÍÇ»Ç¢
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
		}
	};
	
	void startService(){
		bindService(new Intent( MainActivity.this, WalkService.class ), mConnection, BIND_AUTO_CREATE);		
		startService( new Intent( MainActivity.this, WalkService.class ));	
		mBind = true;
	}
	void stopService(){
		if(mBind){
<<<<<<< HEAD
			//Context#UnbindService()でServiceとのConnectionを破棄
			unbindService(mConnection);
			stopService( new Intent( MainActivity.this, WalkService.class ) );
			mBind = false;
		}		
=======
			//Context#UnbindService()Ç≈ServiceÇ∆ÇÃConnectionÇîjä¸
			unbindService(mConnection);
			stopService( new Intent( MainActivity.this, WalkService.class ) );
			mBind = false;
		}
>>>>>>> 19df174eb4a43cf5c363a4c23ac0c4624f0d0f8f
	}
}
