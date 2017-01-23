package com.sonyericsson.extras.liveware.extension.sensorsample;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class EnemyShip {
	
	private int badShipPosX;
	private int badShipPosY;
	private int kind;
	
    private Paint paintShipBullet,shipPaintYellow,shipPaintBlue,shipPaintLightBlue,shipPaintGreen,shipPaintMegent,shipPaintPink,shipPaintOrg;


	
	 public EnemyShip(int posX,int posY, int shipNum){
		 this.badShipPosX = posX;
		 this.badShipPosY = posY; 
		 
		 //list of ships
		 this.kind = shipNum;
		 
		 	shipPaintYellow = new Paint();
	        shipPaintYellow.setAntiAlias(true);
	        shipPaintYellow.setColor(Color.YELLOW);
	        
	        shipPaintBlue = new Paint();
	        shipPaintBlue.setAntiAlias(true);
	        shipPaintBlue.setColor(Color.BLUE);
	        
	        shipPaintLightBlue = new Paint();
	        shipPaintLightBlue.setAntiAlias(true);
	        shipPaintLightBlue.setColor(Color.argb(255, 0, 0, 220));
	        
	        shipPaintOrg = new Paint();
	        shipPaintOrg.setAntiAlias(true);
	        shipPaintOrg.setColor(Color.argb(255, 211, 108, 0));
	        
	        shipPaintGreen = new Paint();
	        shipPaintGreen.setAntiAlias(true);
	        shipPaintGreen.setColor(Color.GREEN);
	        
	        shipPaintMegent = new Paint();
	        shipPaintMegent.setAntiAlias(true);
	        shipPaintMegent.setColor(Color.MAGENTA);
	        
	        shipPaintPink = new Paint();
	        shipPaintPink.setAntiAlias(true);
	        shipPaintPink.setColor(Color.YELLOW);
		 
	 }
	 
	 
	
	
	 public void draw(Canvas canvas){
		 
		 	/*int A = 0;
		    int B = 3;
		    int C = 6;
		    int D = 9;
		    int E = 12;
		    int F = 15;
		    int G = 18;*/
		    
			int A = 0;
		    int B = 2;
		    int C = 4;
		    int D = 6;
		    int E = 8;
		    int F = 10;
		    int G = 12;
		    int H = 14;
		    int I = 16;
		    int K = 18;
		    int L = 20;
		  
		  
		 
		 
		 if(kind == 1){
	    	 canvas.drawRect(C+badShipPosX*2,A+badShipPosY,C+badShipPosX*2,B+badShipPosY,shipPaintBlue);
	    	 canvas.drawRect(D+badShipPosX*2,A+badShipPosY,D+badShipPosX*2,B+badShipPosY,shipPaintLightBlue);
	    	 canvas.drawRect(D+badShipPosX*2,A+badShipPosY,E+badShipPosX*2,B+badShipPosY,shipPaintBlue);
	    	 
	    	 canvas.drawRect(C+badShipPosX*2,B+badShipPosY,C+badShipPosX*2,C+badShipPosY,shipPaintLightBlue);
	    	 canvas.drawRect(D+badShipPosX*2,B+badShipPosY,D+badShipPosX*2,C+badShipPosY,shipPaintBlue);
	    	 canvas.drawRect(D+badShipPosX*2,B+badShipPosY,E+badShipPosX*2,C+badShipPosY,shipPaintLightBlue);
	    	 
	    	 canvas.drawRect(C+badShipPosX*2,C+badShipPosY,C+badShipPosX*2,D+badShipPosY,shipPaintLightBlue);
	    	 canvas.drawRect(D+badShipPosX*2,C+badShipPosY,D+badShipPosX*2,D+badShipPosY,shipPaintBlue);
	    	 canvas.drawRect(D+badShipPosX*2,C+badShipPosY,E+badShipPosX*2,D+badShipPosY,shipPaintLightBlue);
	    	 
	    	 canvas.drawRect(C+badShipPosX*2,D+badShipPosY,C+badShipPosX*2,E+badShipPosY,shipPaintLightBlue);
	    	 canvas.drawRect(D+badShipPosX*2,D+badShipPosY,D+badShipPosX*2,E+badShipPosY,shipPaintBlue);
	    	 canvas.drawRect(D+badShipPosX*2,D+badShipPosY,E+badShipPosX*2,E+badShipPosY,shipPaintLightBlue);
	    	 
	    	 canvas.drawRect(C+badShipPosX*2,E+badShipPosY,C+badShipPosX*2,F+badShipPosY,shipPaintLightBlue);
	    	 canvas.drawRect(D+badShipPosX*2,E+badShipPosY,D+badShipPosX*2,F+badShipPosY,shipPaintBlue);
	    	 canvas.drawRect(D+badShipPosX*2,E+badShipPosY,E+badShipPosX*2,F+badShipPosY,shipPaintLightBlue);
	    	 
	    	 canvas.drawRect(C+badShipPosX*2,E+badShipPosY,C+badShipPosX*2,F+badShipPosY,shipPaintBlue);
	    	 canvas.drawRect(D+badShipPosX*2,E+badShipPosY,D+badShipPosX*2,F+badShipPosY,shipPaintOrg);
	    	 canvas.drawRect(D+badShipPosX*2,E+badShipPosY,E+badShipPosX*2,F+badShipPosY,shipPaintBlue);
	    	
	    	 
	    	 //canvas.drawRect(E+badShipPosX*2,C+badShipPosY,F+badShipPosX*2,D+badShipPosY,shipPaintBlue);
		 }
		
    }
	
	 
	
	 public void explodePixel(){
		 
		 //badShipPosX
		 //badShipPosY
		 
		//if(gameReady){
 		///
 		//if(hit) somthing hit
		 
		 
 		/*if(hitNum[r]==1){
				bottleY[r] += 10;
				
				if(anglePicker[r] == 1){
    		 		angle[r] -= 90 * Math.PI / 180;
				}else{
	       		 	angle[r] += 90 * Math.PI / 180;
				}
				
				
			}*/
 		
 		
     	
	        //for(int b = 0; b < bottleRect.length; b++){	
	        	
		   /*      if(rightLeft > 0){
		        	 
		        	 
		        	if(hitNum[b]==1){
			        	canvas.save();
			        	canvas.rotate((float)angle[b],bottleX[hitNum[b]]+milkBottlePlatformX,bottleY[hitNum[b]]+milkBottlePlatformY);
			        	//canvas.drawBitmap(bottleImage,bottleX[hitNum[b]]+milkBottlePlatformX,bottleY[hitNum[b]]+milkBottlePlatformY, null);
			        	canvas.restore();
		        	}else{
			        	canvas.drawBitmap(bottleImage,bottleX[b]+milkBottlePlatformX,bottleY[b]+milkBottlePlatformY, null);
		        	}
		    
		        }else{
		        	if(hitNum[b]==1){
			         	canvas.save();
			         	canvas.rotate((float)angle[b],bottleX[b]+milkBottlePlatformX,bottleY[b]+milkBottlePlatformY);
			        	//canvas.drawBitmap(bottleImage,bottleX[b]-milkBottlePlatformX,bottleY[b]-milkBottlePlatformY, null);
			        	canvas.restore();
		        	}else{
		        	 	canvas.drawBitmap(bottleImage,bottleX[b]-milkBottlePlatformX,bottleY[b]-milkBottlePlatformY, null);
		        	}
		        }*/
	        //}
     	
 		
 		
 		
 		///
 		
		 
	 }

}
