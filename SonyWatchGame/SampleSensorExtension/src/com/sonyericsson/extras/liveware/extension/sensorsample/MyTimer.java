package com.sonyericsson.extras.liveware.extension.sensorsample;

public class MyTimer {

	private int mdelay;
	private int mdelayTimer;
	private boolean mdelayGetTime;
	private int mtimer;
	private float deltaTime;
	
	//i got to fix i notice it will get faster
	public MyTimer(int timer){
		mtimer = timer;
		mdelayGetTime = false;
		mdelayTimer = 0;
		mdelay = 0;
	}

	public boolean delay(){
		
			if(mdelay > mtimer){
				mdelay = 0;
				mdelayTimer = 0;
				mdelayGetTime = true;
				return true;	
			}else{
	        	mdelayTimer++;
	        	mdelay = mdelayTimer;
	        	return false;
			}
	}
	
	 public float getDeltaTime(){
          return deltaTime;
     }
	
	public void reset(){
		mdelayGetTime = false;
	}
	
	public void resetTime(){
		mdelayTimer = 0;
	}
	
	
	
}
