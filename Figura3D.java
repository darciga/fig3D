package Unidad2;
import java.awt.*;

import javax.swing.RepaintManager;

public class Figura3D 
{
	int figura[][], orig[][];
	int figura2D[][], sec[]; 
	int dist,mz;
	int resp[][];
	
	public Figura3D(int fig[][], int s[]) 
	{
		figura=fig;
		sec=s;
		orig=new int[figura.length][3];
		for(int i=0;i<figura.length;i++)
			for(int j=0;j<figura[0].length;j++)
			{
				orig[i][j]=figura[i][j];
			}
		mz=-350;
		dist=500;
		figura2D=new int[figura.length][2];
	}
	
	public void conv2D()
	{
		for(int i=0;i<figura.length;i++)
			for(int j=0;j<figura[0].length;j++)
			{
				int sx=(dist*figura[i][0])/(figura[i][2]+mz);
				int sy=(dist*figura[i][1])/(figura[i][2]+mz);
				figura2D[i][0]=sx+360;//el 300 y el 250 son solo para que quede 
				figura2D[i][1]=sy+250;//
			}
	}
	
	public void restaurar()
	{
		for(int i=0; i<figura.length; i++)
			for(int j=0; j<figura[0].length; j++)
			{
				figura[i][j]=orig[i][j];
			}
	}
	
	public void regresarValues()
	{
		for(int i=0;i<orig.length;i++)
			for(int j=0;j<orig[0].length;j++)
				figura[i][j]=orig[i][j];
	}
	
	public int [][] rotacionx(int grados)
	{
		double radianes=Math.toRadians(grados);
		double seno=Math.sin(radianes);
		double coseno=Math.cos(radianes);
		int tem[][]=new int [orig.length][orig[0].length];
		regresarValues();
		for(int i=0; i<orig.length; i++)
		{
			int y=figura[i][1];
			tem[i][0]=orig[i][0];
			tem[i][1]=(int)(y*coseno-orig[i][2]*seno);
			tem[i][2]=(int)(y*seno+orig[i][2]*coseno);
		}
		return tem;
	}
	
	public int [][] rotaciony(int grados)
	{
		int temp[][]=new int[orig.length][orig[0].length];
		double radianes=Math.toRadians(grados);
		double seno=Math.sin(radianes);
		double coseno=Math.cos(radianes);
		regresarValues();
		for(int i=0; i<orig.length; i++)
		{
			int x=orig[i][0];			//x,z,y
			temp[i][1]=orig[i][1];
			temp[i][0]=(int)(x*coseno-orig[i][2]*seno);
			temp[i][2]=(int)(x*seno+orig[i][2]*coseno);
		}
		return temp;
	}
	
	public void rotacionz(int grados)
	{
		double radianes=Math.toRadians(grados);
		double seno=Math.sin(radianes);
		double coseno=Math.cos(radianes);
		regresarValues();
		for(int i=0; i<figura.length; i++)
		{
			int x=figura[i][0];
			//int y=figura[i][1];
			figura[i][0]=(int)(x*coseno-figura[i][1]*seno);
			figura[i][1]=(int)(x*seno+figura[i][1]*coseno);
		}
	}
	public void rotacionxyz(int angx,int angy,int angz)
	{
		double angxr=Math.toRadians(angx);
		double angyr=Math.toRadians(angy);
		double angzr=Math.toRadians(angz);
		double srx=Math.sin(angxr);
		double crx=Math.cos(angxr);
		double sry=Math.sin(angyr);
		double cry=Math.cos(angyr);
		double srz=Math.sin(angzr);
		double crz=Math.cos(angzr);
		regresarValues();
		for(int i=0; i<figura.length; i++)
		{
			int x=figura[i][0];
			int y=figura[i][1];
			int z=figura[i][2];
			figura[i][0]=(int)(x*(cry*crz)+y*((-srx*sry)*crz+(crx*srz))+z*((crx*sry)*crz+(srx*srz)));
			figura[i][1]=(int)(x*(cry*-srz)+y*((-srx*sry)*-srz+(crx*crz))+z*((crx*sry)*-srz+(srx*crz)));
			figura[i][2]=(int)(x*-sry+y*(-srx*cry)+z*(crx*cry));
		}
		
		
	}
	public void dibujar(Graphics g)
	{
		g.setColor(Color.BLUE);
		int i=0;
		for(i=0;i<sec.length-1;i+=2)
		{
			g.drawLine(figura2D[sec[i]][0], figura2D[sec[i]][1], 
					   figura2D[sec[i+1]][0], figura2D[sec[i+1]][1]);
		}
	}
	public void Vistas(Graphics g){
		//vista superior a 90 grados
		int temp[][] = rotacionx(90);
		for(int i=0;i<temp.length;i++)
		{
			//eliminamos la profundidad
			temp[i][2]=0;
		}
		//convertir a dos dimenciones y dibujarlo
		conv2D(temp, 100, 90, g);
		//vista lateral rotar sobre el eje 'y' 90 grados
		int templ[][]=rotaciony(90);
		for(int i=0;i<templ.length;i++)
		{
			//eliminamos la profundidad
			templ[i][2]=0;
		}
		//convertir a dos dimenciones y dibujarlo
		conv2D(templ, 100, 190, g);
		//vista frontal rotar
		int tempf[][]=rotacionx(0);
		for(int i=0;i<tempf.length;i++)
		{
			//eliminamos la profundidad
			tempf[i][2]=0;
		}
		//convertir a dos dimenciones y dibujarlo
		conv2D(tempf, 100, 330, g);
		
		
	}
	public void conv2D(int t[][],int despx,int despy,Graphics g)
	{
		int t2d[][]=new int[t.length][2];
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[0].length;j++)
			{
				//int sx=(dist*t[i][0])/(t[i][2]+mz);si queremos que cambie el tamaño
				int sx=(500*t[i][0])/(t[i][2]+mz);
				int sy=(500*t[i][1])/(t[i][2]+mz);
				t2d[i][0]=sx+despx;//el 300 y el 250 son solo para que quede 
				t2d[i][1]=sy+despy;//
			}
		int i=0;
		g.setColor(Color.BLUE);
		
		for(i=0;i<sec.length-1;i+=2)
		{
			g.drawLine(t2d[sec[i]][0], t2d[sec[i]][1], 
					   t2d[sec[i+1]][0], t2d[sec[i+1]][1]);
		}
	}
	public void escalar_punto(double escx, double escy) {
		//definimos el punto de origen 
		double tx=figura[0][0],ty=figura[0][1];
		//trasnformamos
		for (int i = 0; i < figura.length; i++) {
			//x
			figura[i][0]=(int)(figura[i][0]*escx-tx*escy+tx);// [0] las x
			figura[i][1]=(int)(figura[i][1]*escy-ty*escy+ty);// [1] las y
		}	
		conv2D();
		

	}
	public void escalar(double sx, double sy) {
		for (int i = 0; i < figura.length; i++) {
			figura[i][0] = (int)(figura[i][0] * sx);
			figura[i][1] = (int)(figura[i][1] * sy);
		}
		
		
	}
	
	//rotar sentido
	public void rotar_sentido_punto(double ang){
		double tx=figura[0][0],ty=figura[0][1];
		//convertir angulo a radianes
		double angrad=Math.toRadians(ang);
		double seno=Math.sin(angrad);
		double coseno=Math.cos(angrad);
		for(int i=0;i<figura.length;i++)
		{
			double x=figura[i][0];
			double y=figura[i][1];
			figura[i][0]=(int)(x*coseno-y*seno-tx*coseno+ty*seno+tx);
			figura[i][1]=(int)(x*seno+y*coseno-tx*seno-ty*coseno+ty);
		}
		//conv2D();
	}
	//rotar contra
	public void rotar_contra_punto(double ang){
		double tx=figura[0][0],ty=figura[0][1];
		double angrad=Math.toRadians(ang);
		double seno=Math.sin(angrad);
		double coseno=Math.cos(angrad);
		for(int i=0;i<figura.length;i++)
		{
			double x=figura[i][0];
			double y=figura[i][1];
			figura[i][0]=(int)(x*coseno+y*seno-tx*coseno-ty*seno+tx);
			figura[i][1]=(int)(-x*seno+y*coseno+tx*seno-ty*coseno+ty);
		}
		//conv2D();
	}
}
