package Unidad2;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.net.URL;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Principal3D extends JFrame implements MouseWheelListener
{
	Figura3D obj;
	JSlider sliderx, slidery, sliderz,sliderdist;
	int aut = 1;
	JMenuBar Barram;
	JMenu Inicio,Trasnfor,Acerca;
	JMenuItem Restaurar,Salir;
	JMenuItem RotarS,RotarC;
	JMenuItem Autor,Version,Ayuda;
	int rots=0;
	int rotc=0;
	
	public Principal3D(int fig[][], int sec[]) 
	{
		super ("Objetos en 3D");
		setSize(700,500);
		this.addMouseWheelListener(this);
		sliderx=new JSlider(JSlider.HORIZONTAL, -360, 360, 0);
		sliderx.setMajorTickSpacing(100);
		sliderx.setMinorTickSpacing(10);
		sliderx.setPaintTicks(true);
		sliderx.setPaintLabels(true);
		sliderx.addChangeListener(new ChangeListener()
		{		
			public void stateChanged(ChangeEvent arg0)
			{
				int angrx=sliderx.getValue();
				int angry=slidery.getValue();
				int angrz=sliderz.getValue();
				obj.rotacionxyz(angrx,angry,angrz);
				repaint();
			}
		});
		
		slidery=new JSlider(JSlider.HORIZONTAL, -360, 360, 0);
		slidery.setMajorTickSpacing(100);
		slidery.setMinorTickSpacing(10);
		slidery.setPaintTicks(true);
		slidery.setPaintLabels(true);
		slidery.addChangeListener(new ChangeListener()
		{		
			public void stateChanged(ChangeEvent arg0)
			{
				int angrx=sliderx.getValue();
				int angry=slidery.getValue();
				int angrz=sliderz.getValue();
				obj.rotacionxyz(angrx,angry,angrz);
				repaint();
			}
		});
		
		sliderz=new JSlider(JSlider.HORIZONTAL, -360, 360, 0);
		sliderz.setMajorTickSpacing(100);
		sliderz.setMinorTickSpacing(10);
		sliderz.setPaintTicks(true);
		sliderz.setPaintLabels(true);
		sliderz.addChangeListener(new ChangeListener()
		{		
			public void stateChanged(ChangeEvent arg0)
			{
				int angrx=sliderx.getValue();
				int angry=slidery.getValue();
				int angrz=sliderz.getValue();
				obj.rotacionxyz(angrx,angry,angrz);
				repaint();
			}
		});
		sliderdist=new JSlider(JSlider.VERTICAL,100,1000,500);
		sliderdist.setMajorTickSpacing(100);
		sliderdist.setPaintTicks(true);
		sliderdist.setPaintLabels(true);
		
		setLayout(new BorderLayout());
		JPanel gs=new JPanel();
		gs.setLayout(new FlowLayout());
		gs.add(sliderx);
		gs.add(slidery);
		gs.add(sliderz);
		add(gs,BorderLayout.SOUTH);
		add(sliderdist,BorderLayout.EAST);
		sliderdist.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int valor=sliderdist.getValue();
				obj.dist=valor;
				repaint();
				
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj=new Figura3D(fig, sec);
		setVisible(true);
		setBackground(Color.LIGHT_GRAY);
		//menu
		Barram = new JMenuBar();
		setJMenuBar(Barram);
		hacerMenu();
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		obj.conv2D();
		obj.dibujar(g);
		//vista superior
		obj.Vistas(g);
	}
	public void hacerMenu()
	{
		Inicio = new JMenu("Inicio");
		Trasnfor = new JMenu("Rotaciones");
		Acerca = new JMenu("Acerca De");
		Barram.add(Inicio);
		Barram.add(Trasnfor);
		Barram.add(Acerca);
		//inicio
		Restaurar = new JMenuItem("Restaurar");
		Salir = new JMenuItem("Salir");
		Inicio.add(Restaurar);
		Inicio.setMnemonic('R');
		KeyStroke rk =KeyStroke.getKeyStroke("R");
		Restaurar.setAccelerator(rk);
		Inicio.add(Salir);
		Inicio.setMnemonic('S');
		//iconos
		URL rutar=getClass().getResource("rec/MD-reload.png");
		Restaurar.setIcon(new ImageIcon(rutar));
		URL rutas=getClass().getResource("rec/cancel.png");
		Salir.setIcon(new ImageIcon(rutas));
		//Rotaciones
		RotarS = new JMenuItem("Rotar Sentido");
		RotarC = new JMenuItem("Rotar Contra Sentido");
		//
		
		//
		Trasnfor.add(RotarS);
		Trasnfor.setMnemonic('S');
		KeyStroke rs = KeyStroke.getKeyStroke("D");
		RotarS.setAccelerator(rs);
		Trasnfor.add(RotarC);
		KeyStroke rc = KeyStroke.getKeyStroke("I");
		RotarC.setAccelerator(rc);
		Trasnfor.setMnemonic('C');
		//iconos
		URL rutars=getClass().getResource("rec/shapes-rotate-clockwise.png");
		RotarS.setIcon(new ImageIcon(rutars));
		URL rutarc=getClass().getResource("rec/shapes-rotate-anticlockwise.png");
		RotarC.setIcon(new ImageIcon(rutarc));
		//Acerca
		Autor = new JMenuItem("Autor");
		Version = new JMenuItem("Version");
		Ayuda = new JMenuItem("Ayuda");
		KeyStroke ak =KeyStroke.getKeyStroke("F1");
		Ayuda.setAccelerator(ak);
		Acerca.add(Autor);
		Acerca.setMnemonic('A');
		Acerca.add(Version);
		Acerca.setMnemonic('V');
		Acerca.add(Ayuda);
		Acerca.setMnemonic('H');
		//iconos
		URL rutaa=getClass().getResource("rec/about.png");
		Autor.setIcon(new ImageIcon(rutaa));
		URL rutav=getClass().getResource("rec/window.png");
		Version.setIcon(new ImageIcon(rutav));
		URL rutaay=getClass().getResource("rec/help.png");
		Ayuda.setIcon(new ImageIcon(rutaay));
		
		//Eventos
		Salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
				
			}
		});
		Restaurar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				obj.restaurar();
				repaint();
				
			}
		});
		RotarS.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				rots +=5;
				rotc=0;
				obj.rotar_sentido_punto(rots);
				repaint();	
			}		
		});
		
		RotarC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				rotc+=5;
				rots=0;
				obj.rotar_contra_punto(rotc);
				repaint();
			}
		});
		Ayuda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String mensaje="Ayuda \n"+
				"\n Presiona la tecla I para girar la letra a la izquierda"+
				"\n Presiona la tecla D para girar la letra a la derecha"+
				"\n Presiona la tecla R restaurar la letra original"+
				"\n";
				mensajeAyuda(mensaje);
			}
		});
		Autor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String mensaje="Acerca del autor \n"+
						"\n Dante Omar Arciga Camarena"+
						"\n 09420273"+
						"\n";
						mensajeAutor(mensaje);
			}
		});
		
	}
	public void mensajeAyuda(String mensaje)
	{
		JOptionPane.showMessageDialog(this, mensaje,"Ayuda",JOptionPane.INFORMATION_MESSAGE);		
	}
	public void mensajeAutor(String mensaje){
		JOptionPane.showMessageDialog(this, mensaje,"Autor",JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	public static void main(String[] args) 
	{
		int letraD[][]={
				//Frente
				/*0*/{30,-30,-10},/*1*/{-20,-30,-10},
				/*2*/{-30,-20,-10},/*3*/{-30,20,-10},
				/*4*/{-20,30,-10},/*5*/{30,30,-10},
				/*6*/{20,-20,-10},/*7*/{-10,-20,-10},
				/*8*/{-20,-10,-10},/*9*/{-20,10,-10},
				/*10*/{-10,20,-10},/*11*/{20,20,-10},
				//Atras
				/*12*/{30,-30,10},/*13*/{-20,-30,10},
				/*14*/{-30,-20,10},/*15*/{-30,20,10},
				/*16*/{-20,30,10},/*17*/{30,30,10},
				/*18*/{20,-20,10},/*19*/{-10,-20,10},
				/*20*/{-20,-10,10},/*21*/{-20,10,10},
				/*22*/{-10,20,10},/*23*/{20,20,10},				
		};
		int secD[]={/**/0,1,1,2,2,3,3,4,4,5,5,0,/**/
				/**/6,7,7,8,8,9,9,10,10,11,11,6,/*Frente*/
				/**/12,13,13,14,14,15,15,16,16,17,17,12,/**/
				/**/18,19,19,20,20,21,21,22,22,23,23,18,/**/
				/*uniones*/0,12,1,13,2,14,3,15,4,16,5,17,6
				,18,7,19,8,20,9,21,10,22,11,23/**/
		};
		new Principal3D(letraD, secD);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		int movi=e.getWheelRotation();
		//movi+=100;
		//System.out.println(movi);
		if(movi<0)
		{
			//arriba_aumentar
			if(obj.dist>100)
				obj.dist+=movi;
			else
				obj.dist=100;
			//System.out.println(obj.dist);
			//obj.dist=movi;
		}
		else {
			//abajo
			//System.out.println(obj.dist);
			if(obj.dist<1000)
				obj.dist+=movi;
			else
				obj.dist=1000;
			//obj.dist=movi;
		}
		repaint();
		
	}
}
