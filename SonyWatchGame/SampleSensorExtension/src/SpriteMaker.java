import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class SpriteMaker {

	
	private int Xpos;
	private int Ypos;
	private int scale;
	
	private int[] bit8 = new int[8];
	private int[] bit16 = new int[16];
	private int[] bit32 = new int[32];
	private int[] bit64 = new int[64];
	private int A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,
	A1,B1,C1,D1,E1,F1,G1,H1,I1,J1,K1,L1,M1,N1,O1,P1,Q1,R1,S1,T1,U1,V1,W1,X1,Y1,Z1,
	A2,B2,C2,D2,E2,F2,G2,H2,I2,J2,K2,L2,M2,N2,O2,P2,Q2,R2,S2,T2,U2,V2,W2,X2,Y2,Z2;
	
    private ArrayList<Paint> paint = new ArrayList<Paint>();


	
	 public SpriteMaker(){
		
	 }
	 
	 public void setScale(int scale){
		 this.scale = scale;
	 }
	 
	 
	 public void newPaint(int r,int g,int b){
		 Paint paint;
		 paint = new Paint();
		 paint.setAntiAlias(true);
		 paint.setColor(Color.argb(255, r, g, b));
		 this.paint.add(paint);
	 }
	
	 public void draw(Canvas canvas){
		 
	//i need the to pick grid 1st so i can center it
		 	A = 0*scale;
		 	B = 1*scale;
		    C = 2*scale;
		    D = 3*scale;
		    E = 4*scale;
		    F = 5*scale;
		    G = 6*scale;
		    H = 7*scale;
		    I = 8*scale;
		    J = 9*scale;
		    K = 10*scale;
		    L = 11*scale;
		    M = 12*scale;
		    N = 13*scale;
		    O = 14*scale;
		    P = 15*scale;
		    Q = 16*scale;
		    R = 17*scale;
		    S = 18*scale;
		    T = 19*scale;
		    U = 20*scale;
		    V = 21*scale;
		    W = 22*scale;
		    X = 23*scale;
		    Y = 24*scale;
		    Z = 25*scale;
			A1 = 26*scale;
		 	B1 = 27*scale;
		    C1 = 28*scale;
		    D1 = 29*scale;
		    E1 = 30*scale;
		    F1 = 31*scale;
		    G1 = 32*scale;
		    H1 = 33*scale;
		    I1 = 34*scale;
		    J1 = 35*scale;
		    K1 = 36*scale;
		    L1 = 37*scale;
		    M1 = 38*scale;
		    N1 = 39*scale;
		    O1 = 40*scale;
		    P1 = 41*scale;
		    Q1 = 42*scale;
		    R1 = 43*scale;
		    S1 = 44*scale;
		    T1 = 45*scale;
		    U1 = 46*scale;
		    V1 = 47*scale;
		    W1 = 48*scale;
		    X1 = 49*scale;
		    Y1 = 50*scale;
		    Z1 = 51*scale;
			A2 = 52*scale;
		 	B2 = 53*scale;
		    C2 = 54*scale;
		    D2 = 55*scale;
		    E2 = 56*scale;
		    F2 = 57*scale;
		    G2 = 58*scale;
		    H2 = 59*scale;
		    I2 = 60*scale;
		    J2 = 61*scale;
		    K2 = 62*scale;
		    L2 = 63*scale;
		    M2 = 64*scale;
		    
		 // A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z
		    bit64[0] = A;
		    bit64[1] = B;
		    bit64[2] = C;
		    bit64[3] = D;
		    bit64[4] = E;
		    bit64[5] = F;
		    bit64[6] = G;
		    bit64[7] = H;
		    bit64[8] = I;
		    bit64[9] = J;
		    bit64[10] = K;
		    bit64[11] = L;
		    bit64[12] = M;
		    bit64[13] = N;
		    bit64[14] = O;
		    bit64[15] = P;
		    bit64[16] = Q;
		    bit64[17] = R;
		    bit64[18] = S;
		    bit64[19] = T;
		    bit64[20] = U;
		    bit64[21] = N;
		    bit64[22] = W;
		    bit64[23] = X;
		    bit64[24] = Y;
		    bit64[25] = Z;
		    bit64[26] = A1;
		    bit64[27] = B1;
		    bit64[28] = C1;
		    bit64[29] = D1;
		    bit64[30] = E1;
		    bit64[31] = F1;
		    bit64[32] = G1;
		    bit64[33] = H1;
		    bit64[34] = I1;
		    bit64[35] = J1;
		    bit64[36] = K1;
		    bit64[37] = L1;
		    bit64[38] = M1;
		    bit64[39] = N1;
		    bit64[40] = O1;
		    bit64[41] = P1;
		    bit64[42] = Q1;
		    bit64[43] = R1;
		    bit64[44] = S1;
		    bit64[45] = T1;
		    bit64[46] = U1;
		    bit64[47] = N1;
		    bit64[48] = W1;
		    bit64[49] = X1;
		    bit64[50] = Y1;
		    bit64[51] = Z1;
		    bit64[52] = A2;
		    bit64[53] = B2;
		    bit64[54] = C2;
		    bit64[55] = D2;
		    bit64[56] = E2;
		    bit64[57] = F2;
		    bit64[58] = G2;
		    bit64[59] = H2;
		    bit64[60] = I2;
		    bit64[61] = J2;
		    bit64[62] = K2;
		    bit64[63] = L2;
		    bit64[64] = M2;
		    
		 
		 
		 if(kind == 1){
	    	 canvas.drawRect(C+X*2,A+X,C+X*2,B+X,shipPaintBlue);
	    	 canvas.drawRect(D+X*2,A+X,D+X*2,B+X,shipPaintLightBlue);
	    	 canvas.drawRect(D+X*2,A+X,E+X*2,B+X,shipPaintBlue);
	    	 
	    	 canvas.drawRect(C+X*2,B+X,C+X*2,C+X,shipPaintLightBlue);
	    	 canvas.drawRect(D+X*2,B+X,D+X*2,C+X,shipPaintBlue);
	    	 canvas.drawRect(D+X*2,B+X,E+X*2,C+X,shipPaintLightBlue);
	    	 
	    	 canvas.drawRect(C+X*2,C+X,C+X*2,D+X,shipPaintLightBlue);
	    	 canvas.drawRect(D+X*2,C+X,D+X*2,D+X,shipPaintBlue);
	    	 canvas.drawRect(D+X*2,C+X,E+X*2,D+X,shipPaintLightBlue);
	    	 
	    	 canvas.drawRect(C+X*2,D+X,C+X*2,E+X,shipPaintLightBlue);
	    	 canvas.drawRect(D+X*2,D+X,D+X*2,E+X,shipPaintBlue);
	    	 canvas.drawRect(D+X*2,D+X,E+X*2,E+X,shipPaintLightBlue);
	    	 
	    	 canvas.drawRect(C+X*2,E+X,C+X*2,F+X,shipPaintLightBlue);
	    	 canvas.drawRect(D+X*2,E+X,D+X*2,F+X,shipPaintBlue);
	    	 canvas.drawRect(D+X*2,E+X,E+X*2,F+X,shipPaintLightBlue);
	    	 
	    	 canvas.drawRect(C+X*2,E+X,C+X*2,F+X,shipPaintBlue);
	    	 canvas.drawRect(D+X*2,E+X,D+X*2,F+X,shipPaintOrg);
	    	 canvas.drawRect(D+X*2,E+X,E+X*2,F+X,shipPaintBlue);
	    	
	    	 
	    	 //canvas.drawRect(E+X*2,C+X,F+X*2,D+X,shipPaintBlue);
		 }
		
    }
	
	 
	
}
