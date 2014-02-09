package com.main.virtualvisitor88;

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
import android.hardware.SensorManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.view.MenuItem;
import android.view.View;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity{

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mTitle;
	SensorManager	sm;
	WalkCount		wc;
	TextView 		tv;
	private activityCallback mService;
	boolean mBind 	= false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTitle = getTitle();
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
        
        WCSample();		
	}
	private RemoteCallbackList<walkCallback> walkCallBack = new RemoteCallbackList<walkCallback>();	

	void WCSample(){
		tv  = new TextView(this);
		tv.setText("0");
	    setContentView(tv);
		startService();
//		stopService();		
	}

	
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
    
	private walkCallback wCallback = new walkCallback.Stub() { //Åy1Åz
		@Override
		public void updateWalkCount(int walkNum) throws RemoteException {
			// TODO Auto-generated method stub
			Log.i("test", "call!!");
			tv.setText(""+walkNum);
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
			//Context#UnbindService()Ç≈ServiceÇ∆ÇÃConnectionÇîjä¸
			unbindService(mConnection);
			stopService( new Intent( MainActivity.this, WalkService.class ) );
			mBind = false;
		}		
	}
}
