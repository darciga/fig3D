package Unidad2;

import java.awt.*;

public class Figura2 {

	double figb[][]; // bidimensional
	double resp[][];

	public Figura2(double bid[][]) {
		asignarb(bid);
	}

	public void dibujarb(Graphics g) {
		g.setColor(Color.RED);
		for (int i = 0; i < figb.length - 1; i++)
			g.drawLine((int) figb[i][0], (int) figb[i][1],
					(int) figb[i + 1][0], (int) figb[i + 1][1]);
	}
	public void dibujarRef(Graphics g) {
		//dibuja la figura original
		restaurar();
		trasladar(200, -100);
		dibujarb(g);
		restaurar();
		reflexion(-1, 1,590,-100);
		dibujarb(g);
		restaurar();
		reflexion(1,-1,200,650);
		dibujarb(g);
		restaurar();
		reflexion(-1,-1,590,650);
		dibujarb(g);
		
	}
	
	public void restaurar(){
		
		for (int i = 0; i < figb.length; i++)
			for (int j = 0; j < figb[0].length; j++)
			{
				figb[i][j] = resp[i][j];
				
			}
		
	}

	public void asignarb(double bid[][]) {
		figb = new double[bid.length][bid[0].length];
		resp=new double[bid.length][bid[0].length];
		for (int i = 0; i < figb.length; i++)
			for (int j = 0; j < figb[0].length; j++)
			{
				figb[i][j] = bid[i][j];
				resp[i][j] = bid[i][j];
			}
				
	}

	public void escalar(double a, double b) {
		for (int i = 0; i < figb.length; i++) {
			figb[i][0] *= a;// [0] las x
			figb[i][1] *= b;// [1] las y
		}
		

	}
	
	/*public void escalar_punto(double a, double b) {
		//definimos el punto de origen 
		double tx=figb[0][0],ty=figb[0][1];
		trasladar(-tx,-ty);//traslada al origen
		//trasnformamos
		for (int i = 0; i < figb.length; i++) {
			figb[i][0] *= a;// [0] las x
			figb[i][1] *= b;// [1] las y
		}
		trasladar(tx,ty);
		

	}
	*/
	//aplicar la propiedad de las coordenas homogeneas para transformar una figura sobre un punto()
	public void escalar_punto(double escx, double escy) {
		//definimos el punto de origen 
		double tx=figb[0][0],ty=figb[0][1];
		//trasnformamos
		for (int i = 0; i < figb.length; i++) {
			//x
			figb[i][0]=figb[i][0]*escx-tx*escx+escx+tx;// [0] las x
			figb[i][1]=figb[i][1]*escy-ty*escy+ty;// [1] las y
		}	

	}
	
	public void deformar(double A, double B)
	{
		for(int i=0; i<figb.length; i++)
		{
			double x=figb[i][0];
			double y=figb[i][1];
			//x
			figb[i][0]=x+A*y;
			//y
			figb[i][1]=B*x+y;
		}
	}
	public void deformar_punto(double A,double B){
		double tx=figb[0][0],ty=figb[0][1];
		for(int i=0; i<figb.length; i++)
		{
			double x=figb[i][0];
			double y=figb[i][1];
			//x
			figb[i][0]=x+y*B-ty*B;
			//y
			figb[i][1]=x*A+y-tx*A;
		}
		
	}
	public void trasladar(double tx, double ty)
	{
		for(int i=0; i<figb.length; i++)
		{
			figb[i][0]+=tx;
			figb[i][1]+=ty;
		}
	}
	public void rotar_sentido(double ang){
		//convertir angulo a radianes
		double angrad=Math.toRadians(ang);
		double seno=Math.sin(angrad);
		double coseno=Math.cos(angrad);
		for(int i=0;i<figb.length;i++)
		{
			double x=figb[i][0];
			double y=figb[i][1];
			figb[i][0]=x*coseno+y*seno;
			figb[i][1]=-x*seno+y*coseno;
		}
	}
	
	public void rotar_sentido_punto(double ang){
		double tx=figb[0][0],ty=figb[0][1];
		//convertir angulo a radianes
		double angrad=Math.toRadians(ang);
		double seno=Math.sin(angrad);
		double coseno=Math.cos(angrad);
		for(int i=0;i<figb.length;i++)
		{
			double x=figb[i][0];
			double y=figb[i][1];
			figb[i][0]=x*coseno-y*seno-tx*coseno+ty*seno+tx;
			figb[i][1]=x*seno+y*coseno-tx*seno-ty*coseno+ty;
		}
	}
	public void rotar_contra(double ang){
		double angrad=Math.toRadians(ang);
		double seno=Math.sin(angrad);
		double coseno=Math.cos(angrad);
		for(int i=0;i<figb.length;i++)
		{
			double x=figb[i][0];
			double y=figb[i][1];
			figb[i][0]=x*coseno-y*seno;
			figb[i][1]=-x*seno+y*coseno;
		}
	}
	
	public void rotar_contra_punto(double ang){
		double tx=figb[0][0],ty=figb[0][1];
		double angrad=Math.toRadians(ang);
		double seno=Math.sin(angrad);
		double coseno=Math.cos(angrad);
		for(int i=0;i<figb.length;i++)
		{
			double x=figb[i][0];
			double y=figb[i][1];
			figb[i][0]=x*coseno+y*seno-tx*coseno-ty*seno+tx;
			figb[i][1]=-x*seno+y*coseno+tx*seno-ty*coseno+ty;
		}
	}
	public void rotarT(Graphics g,double ang){
		double j=ang;
		for( j=ang;j<360;j+=5){
			rotar_contra_punto(j);
			dibujarb(g);
			try {
				Thread.sleep(20);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
	
	
	public void reflexion(double rx,double ry,double tx,double ty){
		for(int i=0;i<figb.length;i++){
			figb[i][0]*=rx;
			figb[i][1]*=ry;
			
		}
		trasladar(tx, ty);
		
		
		
		
	}
	int minx,miny,maxx,maxy;
	public void minMax(){
		//minimo y maximo de la imagen
		double menorx=figb[0][0];
		double menory=figb[0][1];
		for(int i=1;i<figb.length;i++)
		{
			if(figb[i][0]<menorx)
				menorx=figb[i][0];
			if(figb[i][1]<menory)
				menory=figb[i][1];
		}
		minx=(int)menorx;
		miny=(int)menory;
		double mayorx=0,mayory=0;
		for(int i=0;i<figb.length;i++)
		{
			if(figb[i][0]>mayorx)
				mayorx=figb[i][0];
			if(figb[i][1]>mayory)
				mayory=figb[i][1];
		}
		maxx=(int)mayorx;
		maxy=(int)mayory;
	}
	public boolean verificarCoord(int crx,int cry){
		minMax();
		if(crx>minx && crx<maxx && cry>miny && cry<maxy)
			return true;
		return false;
	}

}
