/*
 Copyright (c) 2011, Sony Ericsson Mobile Communications AB

 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 * Neither the name of the Sony Ericsson Mobile Communications AB nor the names
 of its contributors may be used to endorse or promote products derived from
 this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.sonyericsson.extras.liveware.extension.sensorsample;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.aef.sensor.Sensor;
import com.sonyericsson.extras.liveware.aef.sensor.Sensor.SensorAccuracy;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.control.ControlTouchEvent;
import com.sonyericsson.extras.liveware.extension.util.sensor.AccessorySensor;
import com.sonyericsson.extras.liveware.extension.util.sensor.AccessorySensorEvent;
import com.sonyericsson.extras.liveware.extension.util.sensor.AccessorySensorEventListener;
import com.sonyericsson.extras.liveware.extension.util.sensor.AccessorySensorException;
import com.sonyericsson.extras.liveware.extension.util.sensor.AccessorySensorManager;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;


/**
 * The sample sensor control handles the accelerometer sensor on an accessory.
 * This class exists in one instance for every supported host application that
 * we have registered to
 */
class SampleSensorControl extends ControlExtension{

	public static final int widthPixels = 128;
    public static final int heightPixels = 128;
    
    private int speed = 5;
    
    private int posX,posY,fireY,fireX,move;
    
    private static boolean startGame;
    private static boolean gameReady;
    private static boolean fire,fireStart;
    private boolean running = true;
 
    private Paint paintSimple,paintButton1,paintButton2,paintShipYellowOn,paintShipYellowOff,paintShipBullet,shipPaintYellow,shipPaintBlue,shipPaintGreen,shipPaintMegent,shipPaintPink;
    private Paint paintSimpleBlur,paintTextStartGame,paintTextStartGameOn,paintTextTittle,paintCerdits;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.RGB_565;

    private AccessorySensor mSensor = null;

    private MyTimer timer;
    private MyTimer timerFire;
    private MyTimer startGameTimer;
    private double updateTime;
    private SensorManager mSensorManager;
    
    private Bitmap buffer;
    private Bitmap bitmap; 
    private Canvas canvas;
    
    private Rect bulletRect,playRect;
    private Rect[] enemyHitRect = new Rect[20];
    private boolean[] enemyHit = new boolean[20];
    
    private int[] anglePicker = new int[6];
    private float[] angle = new float[6]; 
    
    private int rightLeft;

    private Random ran;
    private Resources res;
	

	@Override
	public void onStart(){
		super.onStart();
		  Log.v("moto","onStart ");
	
		  
		    paintSimple = new Paint();
	        paintSimple.setAntiAlias(true);
	        paintSimple.setDither(true);
	        paintSimple.setColor(Color.YELLOW);
	        paintSimple.setStrokeWidth(8f);
	        paintSimple.setStyle(Paint.Style.STROKE);
	        paintSimple.setStrokeJoin(Paint.Join.ROUND);
	        paintSimple.setStrokeCap(Paint.Cap.ROUND);
	        
	        paintShipYellowOn = new Paint();
	        paintShipYellowOn.setAntiAlias(true);
	        paintShipYellowOn.setDither(true);
	        paintShipYellowOn.setColor(Color.YELLOW);
	        paintShipYellowOn.setStrokeWidth(2f);
	        paintShipYellowOn.setStyle(Paint.Style.STROKE);
	        paintShipYellowOn.setStrokeJoin(Paint.Join.ROUND);
	        paintShipYellowOn.setStrokeCap(Paint.Cap.ROUND);
	        
	        paintShipYellowOff = new Paint();
	        paintShipYellowOff.setAntiAlias(true);
	        paintShipYellowOff.setDither(true);
	        paintShipYellowOff.setColor(Color.YELLOW);
	        paintShipYellowOff.setStrokeWidth(6f);
	        paintShipYellowOff.setStyle(Paint.Style.STROKE);
	        paintShipYellowOff.setStrokeJoin(Paint.Join.ROUND);
	        paintShipYellowOff.setStrokeCap(Paint.Cap.ROUND);
	        
	        paintShipBullet = new Paint();
	        paintShipBullet.setAntiAlias(true);
	        paintShipBullet.setDither(true);
	        paintShipBullet.setColor(Color.RED);
	        paintShipBullet.setStrokeWidth(2f);
	   
	       
	        paintSimpleBlur = new Paint();
	        paintSimpleBlur.set(paintSimple);
	        paintSimpleBlur.setColor(Color.YELLOW);
	        paintSimpleBlur.setStrokeWidth(8f);
	        paintSimpleBlur.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL)); 
	 	    
	        paintButton1 = new Paint();
	        paintButton1.setAntiAlias(true);
	        paintButton1.setDither(true);
	        paintButton1.setColor(Color.CYAN);
	        
	        paintButton2 = new Paint();
	        paintButton2.setAntiAlias(true);
	        paintButton2.setDither(true);
	        paintButton2.setColor(Color.RED);
	        
	        
	        shipPaintYellow = new Paint();
	        shipPaintYellow.setAntiAlias(true);
	        shipPaintYellow.setColor(Color.YELLOW);
	        
	        shipPaintBlue = new Paint();
	        shipPaintBlue.setAntiAlias(true);
	        shipPaintBlue.setColor(Color.BLUE);
	        
	        shipPaintGreen = new Paint();
	        shipPaintGreen.setAntiAlias(true);
	        shipPaintGreen.setColor(Color.GREEN);
	        
	        shipPaintMegent = new Paint();
	        shipPaintMegent.setAntiAlias(true);
	        shipPaintMegent.setColor(Color.MAGENTA);
	        
	        shipPaintPink = new Paint();
	        shipPaintPink.setAntiAlias(true);
	        shipPaintPink.setColor(Color.YELLOW);
	        
	        
	        
	    	paintTextStartGame = new Paint(); 
	    	paintTextStartGame.setColor(Color.argb(255, 0, 200, 0)); 
	    	paintTextStartGame.setTypeface(Typeface.DEFAULT_BOLD);
	    	paintTextStartGame.setTextSize(12); 
	        
	    	paintTextStartGameOn = new Paint(); 
	    	paintTextStartGameOn.setColor(Color.argb(255, 0, 255, 0)); 
	    	paintTextStartGameOn.setTypeface(Typeface.DEFAULT_BOLD);
	    	paintTextStartGameOn.setTextSize(12); 
	    	
	    	paintTextTittle = new Paint(); 
	    	paintTextTittle.setColor(Color.RED); 
	    	paintTextTittle.setTypeface(Typeface.DEFAULT_BOLD);
	    	paintTextTittle.setTextSize(30); 
	    	
	    	paintCerdits = new Paint(); 
	    	paintCerdits.setColor(Color.WHITE); 
	    	paintCerdits.setTypeface(Typeface.DEFAULT);
	    	paintCerdits.setTextSize(6); 
	    	
	    	
	    	timer = new MyTimer(2);
	    	timerFire = new MyTimer(5);
	    	ran = new Random(125);
	    	
	    	Random rx = new Random();
         	rightLeft = rx.nextInt(2);
    		Random rx2 = new Random();
    		
    		
    		//Random rr = new Random();
    	   	//for(int r = 0; r < objectRect.length; r++)
    	   		//anglePicker[r] = rr.nextInt(2);
	    	
	    	
	    	posX = widthPixels/2 - 10; 
	    	
	    	startGame = true;//test
	    	fire = true;
	    	
	    	for(int i = 0;  i < enemyHitRect.length; i++)
	    		enemyHitRect[i] = new Rect();
		
	    	bulletRect = new Rect();
	    	playRect = new Rect();
	    	
	    	
	    	//res = mContext.getResources();
	     	//game
            //BitmapFactory.Options opt = new BitmapFactory.Options();
    		//opt.inPreferredConfig = Bitmap.Config.RGB_565; 
    		//opt.inDither = false; 
    		
    		//background = BitmapFactory.decodeResource(res,R.drawable.background,opt); 
    	
	    	
	    	//doDrawStartGame();
	    	
	    

	    	
	}

	
	@Override
	public void onTouch(ControlTouchEvent event) {
		super.onTouch(event);
		if(event.getAction() == 1 && startGame!=true)
			  startGame = true;
		
		/*if(event.getAction() == 0){// && startGame
			
		}*/
		
		 //Log.v("moto","event " + event.getX());
		 // Log.v("moto","event " + event.getY());
		  
		if(event.getX() >100){//right
			posX += speed;
        }else if(event.getX() < 30){
        	posX -= speed;
        }

	}

    private final AccessorySensorEventListener mListener = new AccessorySensorEventListener() {

        public void onSensorEvent(AccessorySensorEvent sensorEvent) {
            //Log.v("moto","updateDisplay ");
            updateDisplay(sensorEvent);
        }
    };
    
    /**
     * Create sample sensor control.
     *
     * @param hostAppPackageName Package name of host application.
     * @param context The context.
     */
    SampleSensorControl(final String hostAppPackageName, final Context context) {
        super(context, hostAppPackageName);

        AccessorySensorManager manager = new AccessorySensorManager(context, hostAppPackageName);
        mSensor = manager.getSensor(Sensor.SENSOR_TYPE_ACCELEROMETER);
    }

    @Override
    public void onResume() {
        Log.d(SampleExtensionService.LOG_TAG, "Starting control");
        // Note: Setting the screen to be always on will drain the accessory
        // battery. It is done here solely for demonstration purposes
        setScreenState(Control.Intents.SCREEN_STATE_ON);
        Log.v("moto","onResume ");
        // Start listening for sensor updates.
        if (mSensor != null) {
            try {
                mSensor.registerInterruptListener(mListener);
            } catch (AccessorySensorException e) {
                Log.d(SampleExtensionService.LOG_TAG, "Failed to register listener");
            }
        }
        
        if(bitmap!=null)
        	bitmap = null;
        
        if(canvas!=null)
        	canvas = null;
        
        if(buffer!=null)
        	buffer = null;
        
    	bitmap = Bitmap.createBitmap(widthPixels, heightPixels, BITMAP_CONFIG);
    
  		
    Timer myGameLopp = new Timer();
	
    myGameLopp.schedule(new TimerTask(){          
    	@Override
	    public void run(){
    	
	    	//if(startGame){
	   
    	   //if(gameReady){
    			
    			 //updatePhyics();
        if(bitmap!=null)
        	bitmap = null;
        
    		     updateDraw();
    	   //}
	    	//}
    	
	    }
    
  
    		
    }, 30, 30); // initial delay 1 second, interval 1 second
    		
 
    }

    @Override
    public void onPause() {
        // Stop sensor
        if (mSensor != null) {
            mSensor.unregisterListener();
        }
   
    }

    @Override
    public void onDestroy() {
    	super.onDestroy();
    	
    Log.v("moto","onDestroy ");
    	
    	
        // Stop sensor
        if(mSensor != null) {
            mSensor.unregisterListener();
            mSensor = null;
        }
        
        if(bitmap!=null)
        	bitmap = null;
        
        if(canvas!=null)
        	canvas = null;
        
        if(buffer!=null)
        	buffer = null;
        
        int pid = android.os.Process.myPid();
		android.os.Process.killProcess(pid);
        
    }
    
/*    
    private void updatePhyics(){
   
        Bitmap bitmap = Bitmap.createBitmap(widthPixels, heightPixels, BITMAP_CONFIG);
        // Set default density to avoid scaling.
        bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        Canvas canvas = new Canvas(bitmap);
     	canvas.save();
     	canvas.drawLine(0+fireX,0+posY+fireY,0+fireX,3+posY+fireY,paintShipBullet);
    	canvas.drawBitmap(bitmap,0,0,null);
    	buffer = bitmap;
    	canvas.restore();
    }*/
    
    private void updateDraw(){
        // Create bitmap to draw in.
    	//buffer = null;
    	//canvas = null;
    	
    	bitmap = Bitmap.createBitmap(widthPixels, heightPixels, BITMAP_CONFIG);
    	//bitmap = Bitmap.createBitmap(bitmap,0,0,widthPixels, heightPixels);

        // Set default density to avoid scaling.
        bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);

        LinearLayout root = new LinearLayout(mContext);
        root.setLayoutParams(new LayoutParams(widthPixels, heightPixels));

        LinearLayout sampleLayout = (LinearLayout)LinearLayout.inflate(mContext,R.layout.accelerometer_values, root);
    	
    	
        sampleLayout.measure(widthPixels, heightPixels);
        sampleLayout.layout(0, 0, sampleLayout.getMeasuredWidth(), sampleLayout.getMeasuredHeight());

        canvas = new Canvas(bitmap);
        
        canvas.drawColor(Color.BLACK);
        //sampleLayout.draw(canvas);
        
      /*  if(background!=null)
        	canvas.drawBitmap(background,0,0,null);*/
        
        //if(buffer!=null)
            //canvas.drawBitmap(buffer,0,0,null);
        
		//Bullet
    	canvas.drawLine(0+fireX,0+posY+fireY,0+fireX,3+posY+fireY,paintShipBullet);

	    	if(fireStart){
	    		fireStart = false;
	    		fireX = posX;
	    	}
    	
            if(fire){
            	fireY -= 20;
            }
            
          
            if(fireY < -125){
            	fire = false;
            	fireY = 0;
            }

            
        	if(gameReady){
        		
        		fire = true;
            	fireStart = true;
        		
	            if(move > 1){
	            	posX += speed;
	            	move = 0;
	            }else if(move < -1){
	            	move = 0;
	            	posX -= speed;
		        }
        	}
        	
        
            
        posY = 110;
    	int badGreenShipPosY = 10;
    	int badGreenShipPosX = 10; 
    	
    	
    	if(startGame != true){
    		int X = widthPixels/2 - 25; 
        	int Y = heightPixels/2;
        	
        	
	    	canvas.drawRect(20+X,0+Y,30+X,10+Y,shipPaintBlue);
	    	
	    	canvas.drawRect(10+X,10+Y,20+X,20+Y,shipPaintBlue);
	    	canvas.drawRect(20+X,10+Y,30+X,20+Y,shipPaintYellow);
	    	canvas.drawRect(30+X,10+Y,40+X,20+Y,shipPaintBlue);
	    	
	    	canvas.drawRect(0+X,20+Y,10+X,30+Y,shipPaintBlue);
	    	canvas.drawRect(10+X,20+Y,20+X,30+Y,shipPaintBlue);
	    	canvas.drawRect(20+X,20+Y,30+X,30+Y,shipPaintBlue);
	    	canvas.drawRect(30+X,20+Y,40+X,30+Y,shipPaintBlue);
	    	canvas.drawRect(40+X,20+Y,50+X,30+Y,shipPaintBlue);
	    	//engines
	    	canvas.drawRect(0+X,30+Y,10+X,40+Y,shipPaintBlue);
	    	//fire
	    	if(timerFire.delay()){
	    		canvas.drawLine(20+X,30+Y,25+X,40+Y,paintShipYellowOff);
	    		canvas.drawLine(30+X,30+Y,25+X,40+Y,paintShipYellowOff);
	    	}else{
	    		canvas.drawLine(20+X,30+Y,25+X,40+Y,paintShipYellowOff);
	    		canvas.drawLine(30+X,30+Y,25+X,40+Y,paintShipYellowOff);
	    		canvas.drawLine(20+X,30+Y,25+X,40+Y,paintSimpleBlur);
	    		canvas.drawLine(30+X,30+Y,25+X,40+Y,paintSimpleBlur);
	    	}
	    	//engines
	    	canvas.drawRect(40+X,30+Y,50+X,40+Y,shipPaintBlue);
	    	
	    	
	      	
	    	//Start Game Ship into
	        canvas.drawText("Hakaisha",0,30,paintTextTittle);
	        
	        canvas.drawText("Start Game",30,55,paintTextStartGame);
	        if(timer.delay())
	        canvas.drawText("Start Game",30,55,paintTextStartGameOn);
	        
	        canvas.drawText("---------Hakaisha 2013 by Pier Shaw---------",0,120,paintCerdits);
        
    	}else{
    	
    		
    		int A = 0;
		    int B = 3;
		    int C = 6;
		    int D = 9;
		    int E = 12;
		    int F = 15;
    		
	    	canvas.drawRect(C+posX,A+posY,D+posX,C+posY,shipPaintBlue);
	    	
	    	canvas.drawRect(B+posX,B+posY,C+posX,C+posY,shipPaintBlue);
	    	canvas.drawRect(C+posX,B+posY,D+posX,C+posY,shipPaintYellow);
	    	canvas.drawRect(D+posX,B+posY,E+posX,C+posY,shipPaintBlue);
	    	
	    	canvas.drawRect(A+posX,C+posY,B+posX,D+posY,shipPaintBlue);
	    	canvas.drawRect(B+posX,C+posY,C+posX,D+posY,shipPaintBlue);
	    	canvas.drawRect(C+posX,C+posY,D+posX,D+posY,shipPaintBlue);
	    	canvas.drawRect(D+posX,C+posY,E+posX,D+posY,shipPaintBlue);
	    	canvas.drawRect(E+posX,C+posY,F+posX,D+posY,shipPaintBlue);
	    	//engines
	    	canvas.drawRect(A+posX,D+posY,B+posX,E+posY,shipPaintBlue);
	    	//fire
	    	canvas.drawRect(C+posX,D+posY,D+posX,E+posY,shipPaintYellow);
	    	//engines
	    	canvas.drawRect(E+posX,D+posY,F+posX,E+posY,shipPaintBlue);
	    	
        	
    	//}
	    	
	    	if(!enemyHit[0]){
		    	canvas.drawRect(B+badGreenShipPosX*2,B+badGreenShipPosY,C+badGreenShipPosX*2,C+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(C+badGreenShipPosX*2,B+badGreenShipPosY,D+badGreenShipPosX*2,C+badGreenShipPosY,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*2,B+badGreenShipPosY,E+badGreenShipPosX*2,C+badGreenShipPosY,shipPaintGreen);
		       	canvas.drawRect(A+badGreenShipPosX*2,C+badGreenShipPosY,B+badGreenShipPosX*2,D+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(C+badGreenShipPosX*2,C+badGreenShipPosY,D+badGreenShipPosX*2,D+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(E+badGreenShipPosX*2,C+badGreenShipPosY,F+badGreenShipPosX*2,D+badGreenShipPosY,shipPaintGreen);
	    	}
	    	
	    	if(!enemyHit[1]){
		    	canvas.drawRect(B+badGreenShipPosX*4,B+badGreenShipPosY,C+badGreenShipPosX*4,C+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(C+badGreenShipPosX*4,B+badGreenShipPosY,D+badGreenShipPosX*4,C+badGreenShipPosY,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*4,B+badGreenShipPosY,E+badGreenShipPosX*4,C+badGreenShipPosY,shipPaintGreen);
		       	canvas.drawRect(A+badGreenShipPosX*4,C+badGreenShipPosY,B+badGreenShipPosX*4,D+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(C+badGreenShipPosX*4,C+badGreenShipPosY,D+badGreenShipPosX*4,D+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(E+badGreenShipPosX*4,C+badGreenShipPosY,F+badGreenShipPosX*4,D+badGreenShipPosY,shipPaintGreen);
	    	}
	    	
	    	if(!enemyHit[2]){
		    	canvas.drawRect(B+badGreenShipPosX*6,B+badGreenShipPosY,C+badGreenShipPosX*6,C+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(C+badGreenShipPosX*6,B+badGreenShipPosY,D+badGreenShipPosX*6,C+badGreenShipPosY,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*6,B+badGreenShipPosY,E+badGreenShipPosX*6,C+badGreenShipPosY,shipPaintGreen);
		       	canvas.drawRect(A+badGreenShipPosX*6,C+badGreenShipPosY,B+badGreenShipPosX*6,D+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(C+badGreenShipPosX*6,C+badGreenShipPosY,D+badGreenShipPosX*6,D+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(E+badGreenShipPosX*6,C+badGreenShipPosY,F+badGreenShipPosX*6,D+badGreenShipPosY,shipPaintGreen);
	    	}
	    	
	    	if(!enemyHit[3]){
		    	canvas.drawRect(B+badGreenShipPosX*8,B+badGreenShipPosY,C+badGreenShipPosX*8,C+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(C+badGreenShipPosX*8,B+badGreenShipPosY,D+badGreenShipPosX*8,C+badGreenShipPosY,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*8,B+badGreenShipPosY,E+badGreenShipPosX*8,C+badGreenShipPosY,shipPaintGreen);
		       	canvas.drawRect(A+badGreenShipPosX*8,C+badGreenShipPosY,B+badGreenShipPosX*8,D+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(C+badGreenShipPosX*8,C+badGreenShipPosY,D+badGreenShipPosX*8,D+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(E+badGreenShipPosX*8,C+badGreenShipPosY,F+badGreenShipPosX*8,D+badGreenShipPosY,shipPaintGreen);
	    	}
	    	
	    	if(!enemyHit[4]){
		    	canvas.drawRect(B+badGreenShipPosX*10,B+badGreenShipPosY,C+badGreenShipPosX*10,C+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(C+badGreenShipPosX*10,B+badGreenShipPosY,D+badGreenShipPosX*10,C+badGreenShipPosY,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*10,B+badGreenShipPosY,E+badGreenShipPosX*10,C+badGreenShipPosY,shipPaintGreen);
		       	canvas.drawRect(A+badGreenShipPosX*10,C+badGreenShipPosY,B+badGreenShipPosX*10,D+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(C+badGreenShipPosX*10,C+badGreenShipPosY,D+badGreenShipPosX*10,D+badGreenShipPosY,shipPaintGreen);
		    	canvas.drawRect(E+badGreenShipPosX*10,C+badGreenShipPosY,F+badGreenShipPosX*10,D+badGreenShipPosY,shipPaintGreen);
	    	}
	    	
	    	//
	    
	    	if(!enemyHit[5]){
		    	canvas.drawRect(B+badGreenShipPosX*3,B+badGreenShipPosY*3,C+badGreenShipPosX*3,C+badGreenShipPosY*3,shipPaintMegent);
		    	canvas.drawRect(C+badGreenShipPosX*3,B+badGreenShipPosY*3,D+badGreenShipPosX*3,C+badGreenShipPosY*3,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*3,B+badGreenShipPosY*3,E+badGreenShipPosX*3,C+badGreenShipPosY*3,shipPaintMegent);
		       	canvas.drawRect(A+badGreenShipPosX*3,C+badGreenShipPosY*3,B+badGreenShipPosX*3,D+badGreenShipPosY*3,shipPaintMegent);
		    	canvas.drawRect(C+badGreenShipPosX*3,C+badGreenShipPosY*3,D+badGreenShipPosX*3,D+badGreenShipPosY*3,shipPaintMegent);
		    	canvas.drawRect(E+badGreenShipPosX*3,C+badGreenShipPosY*3,F+badGreenShipPosX*3,D+badGreenShipPosY*3,shipPaintMegent);
	    	}
	    	
	    	if(!enemyHit[6]){
		    	canvas.drawRect(B+badGreenShipPosX*5,B+badGreenShipPosY*3,C+badGreenShipPosX*5,C+badGreenShipPosY*3,shipPaintMegent);
		    	canvas.drawRect(C+badGreenShipPosX*5,B+badGreenShipPosY*3,D+badGreenShipPosX*5,C+badGreenShipPosY*3,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*5,B+badGreenShipPosY*3,E+badGreenShipPosX*5,C+badGreenShipPosY*3,shipPaintMegent);
		       	canvas.drawRect(A+badGreenShipPosX*5,C+badGreenShipPosY*3,B+badGreenShipPosX*5,D+badGreenShipPosY*3,shipPaintMegent);
		    	canvas.drawRect(C+badGreenShipPosX*5,C+badGreenShipPosY*3,D+badGreenShipPosX*5,D+badGreenShipPosY*3,shipPaintMegent);
		    	canvas.drawRect(E+badGreenShipPosX*5,C+badGreenShipPosY*3,F+badGreenShipPosX*5,D+badGreenShipPosY*3,shipPaintMegent);
	    	}
	    	
	    	if(!enemyHit[7]){
		    	canvas.drawRect(B+badGreenShipPosX*7,B+badGreenShipPosY*3,C+badGreenShipPosX*7,C+badGreenShipPosY*3,shipPaintMegent);
		    	canvas.drawRect(C+badGreenShipPosX*7,B+badGreenShipPosY*3,D+badGreenShipPosX*7,C+badGreenShipPosY*3,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*7,B+badGreenShipPosY*3,E+badGreenShipPosX*7,C+badGreenShipPosY*3,shipPaintMegent);
		       	canvas.drawRect(A+badGreenShipPosX*7,C+badGreenShipPosY*3,B+badGreenShipPosX*7,D+badGreenShipPosY*3,shipPaintMegent);
		    	canvas.drawRect(C+badGreenShipPosX*7,C+badGreenShipPosY*3,D+badGreenShipPosX*7,D+badGreenShipPosY*3,shipPaintMegent);
		    	canvas.drawRect(E+badGreenShipPosX*7,C+badGreenShipPosY*3,F+badGreenShipPosX*7,D+badGreenShipPosY*3,shipPaintMegent);
	    	}
	    	
	    	if(!enemyHit[8]){
		    	canvas.drawRect(B+badGreenShipPosX*9,B+badGreenShipPosY*3,C+badGreenShipPosX*9,C+badGreenShipPosY*3,shipPaintMegent);
		    	canvas.drawRect(C+badGreenShipPosX*9,B+badGreenShipPosY*3,D+badGreenShipPosX*9,C+badGreenShipPosY*3,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*9,B+badGreenShipPosY*3,E+badGreenShipPosX*9,C+badGreenShipPosY*3,shipPaintMegent);
		       	canvas.drawRect(A+badGreenShipPosX*9,C+badGreenShipPosY*3,B+badGreenShipPosX*9,D+badGreenShipPosY*3,shipPaintMegent);
		    	canvas.drawRect(C+badGreenShipPosX*9,C+badGreenShipPosY*3,D+badGreenShipPosX*9,D+badGreenShipPosY*3,shipPaintMegent);
		    	canvas.drawRect(E+badGreenShipPosX*9,C+badGreenShipPosY*3,F+badGreenShipPosX*9,D+badGreenShipPosY*3,shipPaintMegent);
	    	}
	    	
	    	//
	    	if(!enemyHit[0]){
		    	canvas.drawRect(B+badGreenShipPosX*2,B+badGreenShipPosY*5,C+badGreenShipPosX*2,C+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(C+badGreenShipPosX*2,B+badGreenShipPosY*5,D+badGreenShipPosX*2,C+badGreenShipPosY*5,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*2,B+badGreenShipPosY*5,E+badGreenShipPosX*2,C+badGreenShipPosY*5,shipPaintPink);
		       	canvas.drawRect(A+badGreenShipPosX*2,C+badGreenShipPosY*5,B+badGreenShipPosX*2,D+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(C+badGreenShipPosX*2,C+badGreenShipPosY*5,D+badGreenShipPosX*2,D+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(E+badGreenShipPosX*2,C+badGreenShipPosY*5,F+badGreenShipPosX*2,D+badGreenShipPosY*5,shipPaintPink);
	    	}
	    	
	    	if(!enemyHit[1]){
		    	canvas.drawRect(B+badGreenShipPosX*4,B+badGreenShipPosY*5,C+badGreenShipPosX*4,C+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(C+badGreenShipPosX*4,B+badGreenShipPosY*5,D+badGreenShipPosX*4,C+badGreenShipPosY*5,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*4,B+badGreenShipPosY*5,E+badGreenShipPosX*4,C+badGreenShipPosY*5,shipPaintPink);
		       	canvas.drawRect(A+badGreenShipPosX*4,C+badGreenShipPosY*5,B+badGreenShipPosX*4,D+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(C+badGreenShipPosX*4,C+badGreenShipPosY*5,D+badGreenShipPosX*4,D+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(E+badGreenShipPosX*4,C+badGreenShipPosY*5,F+badGreenShipPosX*4,D+badGreenShipPosY*5,shipPaintPink);
	    	}
	    	
	    	if(!enemyHit[2]){
		    	canvas.drawRect(B+badGreenShipPosX*6,B+badGreenShipPosY*5,C+badGreenShipPosX*6,C+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(C+badGreenShipPosX*6,B+badGreenShipPosY*5,D+badGreenShipPosX*6,C+badGreenShipPosY*5,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*6,B+badGreenShipPosY*5,E+badGreenShipPosX*6,C+badGreenShipPosY*5,shipPaintPink);
		       	canvas.drawRect(A+badGreenShipPosX*6,C+badGreenShipPosY*5,B+badGreenShipPosX*6,D+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(C+badGreenShipPosX*6,C+badGreenShipPosY*5,D+badGreenShipPosX*6,D+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(E+badGreenShipPosX*6,C+badGreenShipPosY*5,F+badGreenShipPosX*6,D+badGreenShipPosY*5,shipPaintPink);
	    	}
	    	
	    	if(!enemyHit[3]){
		    	canvas.drawRect(B+badGreenShipPosX*8,B+badGreenShipPosY*5,C+badGreenShipPosX*8,C+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(C+badGreenShipPosX*8,B+badGreenShipPosY*5,D+badGreenShipPosX*8,C+badGreenShipPosY*5,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*8,B+badGreenShipPosY*5,E+badGreenShipPosX*8,C+badGreenShipPosY*5,shipPaintPink);
		       	canvas.drawRect(A+badGreenShipPosX*8,C+badGreenShipPosY*5,B+badGreenShipPosX*8,D+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(C+badGreenShipPosX*8,C+badGreenShipPosY*5,D+badGreenShipPosX*8,D+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(E+badGreenShipPosX*8,C+badGreenShipPosY*5,F+badGreenShipPosX*8,D+badGreenShipPosY*5,shipPaintPink);
	    	}
	    	
	    	if(!enemyHit[4]){
		    	canvas.drawRect(B+badGreenShipPosX*10,B+badGreenShipPosY*5,C+badGreenShipPosX*10,C+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(C+badGreenShipPosX*10,B+badGreenShipPosY*5,D+badGreenShipPosX*10,C+badGreenShipPosY*5,shipPaintBlue);
		    	canvas.drawRect(D+badGreenShipPosX*10,B+badGreenShipPosY*5,E+badGreenShipPosX*10,C+badGreenShipPosY*5,shipPaintPink);
		       	canvas.drawRect(A+badGreenShipPosX*10,C+badGreenShipPosY*5,B+badGreenShipPosX*10,D+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(C+badGreenShipPosX*10,C+badGreenShipPosY*5,D+badGreenShipPosX*10,D+badGreenShipPosY*5,shipPaintPink);
		    	canvas.drawRect(E+badGreenShipPosX*10,C+badGreenShipPosY*5,F+badGreenShipPosX*10,D+badGreenShipPosY*5,shipPaintPink);
	    	}//
    	
	    	

    	}
    	
    
    	
    	
    	
    	canvas.save();
    	canvas.drawBitmap(bitmap,0,0,null);
    	buffer = bitmap;
    	canvas.restore();
    
        showBitmap(buffer);
    	
    }
    

    
     /*public void onAccuracyChanged(Sensor sensor, int accuracy) {
    	    Log.v("moto","updateDisplay " + accuracy);
     }
      */
    /**
     * Update the display with new accelerometer data.
     *
     * @param sensorEvent The sensor event.
     */
    private void updateDisplay(AccessorySensorEvent sensorEvent) {
    	//updateDraw();
    /*	   // Create bitmap to draw in.
        Bitmap bitmap = Bitmap.createBitmap(widthPixels, heightPixels, BITMAP_CONFIG);

        // Set default density to avoid scaling.
        bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);

        LinearLayout root = new LinearLayout(mContext);
        root.setLayoutParams(new LayoutParams(widthPixels, heightPixels));

        LinearLayout sampleLayout = (LinearLayout)LinearLayout.inflate(mContext,R.layout.accelerometer_values, root);
    	
    	
        sampleLayout.measure(widthPixels, heightPixels);
        sampleLayout.layout(0, 0, sampleLayout.getMeasuredWidth(), sampleLayout.getMeasuredHeight());

        Canvas canvas = new Canvas(bitmap);*/
        
        //sampleLayout.draw(canvas);
        //buffer
        //if(buffer!=null)
        //canvas.drawBitmap(buffer,0,0,null);
    	//if(buffer!=null)
    		//showBitmap(buffer);
    	
        // Update the values.
        if(sensorEvent != null) {
    		gameReady = true;
    		
    		
            //float[] values = sensorEvent.getSensorValues();
            
            //move = (int)values[0];
            		
        /*    if((int)values[0] > 0 || (int)values[0] < -0 || (int)values[1] > 0 || (int)values[1] < -0 || (int)values[2] > 0 || (int)values[2] < -0 && fire!=true){
            	fire = true;
            	fireStart = true;
            }
            
            	if(gameReady){
		            if(move > 1){
		            	posX += speed;
		            	move = 0;
		            }else if(move < -1){
		            	move = 0;
		            	posX -= speed;
			        }
            	}
            	
            	
                if(bulletRect.intersect(enemyHitRect[0])){
                	//
                }*/
            	
        }
        
    }


    
}
