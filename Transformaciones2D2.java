package Unidad2;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;

class eventoRatonclick extends MouseAdapter{
		//enviar elementos que ocupa
			Transformaciones2D2 obj;
			public eventoRatonclick(Transformaciones2D2 T){
			obj=T;
			
			
		}
		public void mouseClicked(MouseEvent me){
			int noclicks=me.getClickCount();
			//donde le dimos click
			if(noclicks>=2){
				int crx=me.getX();
				int cry=me.getY();
				//de que lado se encuetra la figura y si esta en lado izquierdo
				obj.objf.minMax();
				//donde se encuetra la figura izq min en x
				if(crx<obj.objf.minx){
					obj.objf.rotar_contra_punto(10.0);
				}
				obj.repaint();
				//del lado derecho
				if(cry>obj.objf.miny){
					obj.objf.rotar_sentido_punto(10.0);
				}
				
			}
			
			
			
		}
		public void mousePressed(MouseEvent me) {
			int crx=me.getX();
			int cry=me.getY();
			//verificar que esten dentro de la figura
			obj.mover=obj.objf.verificarCoord(crx,cry);
			/*if(dentro)
				JOptionPane.showMessageDialog(null, "Precionaste dentro de la figura");
			else
				JOptionPane.showMessageDialog(null, "Precionaste fuera de la figura");*/
					
	}
		

		
}
//cuadro de dialogo personzalido
class Crotar extends JDialog{
	//regresa cual boton se regreso
	JRadioButton ri,rd;//1,2
	JLabel l1,l2,gr;//grados
	JButton aceptar,cancelar;
	JTextField texto;
	int sel[];
	ButtonGroup bg;
	
	public Crotar(JFrame v,boolean modal){
		super(v,modal);
		setSize(440,80);
		setLocation(300,10);
		setLayout(new FlowLayout());
		setTitle("Selecciona el sentido y da los grados a rotar");
		URL ruta=getClass().getResource("rec/shapes-rotate-clockwise.png");
		l1=new JLabel(new ImageIcon(ruta));
		URL rutac=getClass().getResource("rec/shapes-rotate-anticlockwise.png");
		l2=new JLabel(new ImageIcon(rutac));
		bg= new ButtonGroup();
		ri=new JRadioButton("",true);
		bg.add(ri);
		rd=new JRadioButton("",false);
		bg.add(rd);
		
		gr=new JLabel("Grados");
		texto=new JTextField(5);
		aceptar=new JButton("Aceptar");
		cancelar=new JButton("Cancelar");
		add(l1);add(ri);
		add(l2);add(rd);
		
		add(gr);add(texto);
		add(aceptar);add(cancelar);
		sel=new int[2];//0,0 no debe rotar
		aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(ri.isSelected())
					sel[0]=1;//rotar izquierdo
				else
					sel[0]=2;//rotar derecho
				String cad=texto.getText();
				try {
					sel[1]=Integer.parseInt(cad);
				} catch (NumberFormatException e) {
					// TODO: handle exception
					sel[1]=0;
				}
				setVisible(false);
				
			}
		});
		cancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sel[0]=0;
				sel[1]=0;
				setVisible(false);
				
				
			}
		});		
		
	}

	public int[] mostrar() {
		// TODO Auto-generated method stub
		setVisible(true);
		return sel;
	}
}

class eventoRatonarrastre extends MouseMotionAdapter{
	Transformaciones2D2 obj;
	public eventoRatonarrastre(Transformaciones2D2 T){
		obj=T;
	}
	public void mouseDragged(MouseEvent arg0){
		//a donde estamos arrastrando
		if(obj.mover){
			int x=arg0.getX();
			int y=arg0.getY();
			//sacar diferencia punto de pivote
			int pvx=(int)obj.objf.figb[0][0];
			int pvy=(int)obj.objf.figb[0][1];
			int despx=x-pvx;
			int despy=y-pvy;
			obj.objf.trasladar(despx, despy);
			obj.repaint();
		}
		
		
	}
}

public class Transformaciones2D2 extends JPanel implements MouseWheelListener {
	JFrame vent;
	Container cont;
	JButton dibujarb, escalar, deformar,trasladar,rotarc,rotars,reflexion;
	Figura2 objf;
	int op;
	boolean mover=false;
	JMenuBar Barram;
	JMenu Inicio,Trasnfor,Acerca;
	JMenuItem Restaurar,Salir;
	JMenuItem Escalar,Deformar,Trasladar,Rotar,Reflejar;
	JMenuItem Autor,Version,Ayuda;
	JToolBar barrah;
	public Transformaciones2D2(double b[][]) {
		vent = new JFrame("Transformaciones 2D");
		Image icon = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("rec/Games-flat-circle-64.png"));
		vent.setIconImage(icon);
		cont = vent.getContentPane();
		//menu
		
		//poner el menu
		Barram=new JMenuBar();
		vent.setJMenuBar(Barram);
		hacerMenu();
		
		//
		cont.setLayout(new BorderLayout());
		setSize(700, 500);
		setBackground(Color.LIGHT_GRAY);
		vent.setSize(700, 500);
		cont.add(this, BorderLayout.CENTER);
		vent.setLocationRelativeTo(null);
		op = -1;
		objf = new Figura2(b);
		//dibujarb = new JButton("Dibujar");
		//escalar = new JButton("Escalar");
		//deformar = new JButton("Defromar");
		//trasladar = new JButton("Trasladar");
		//rotarc=new JButton("Rotar En Contra");
		//rotars=new JButton("Rotar Sentido");
		//reflexion=new JButton("Reflexion");
		/*dibujarb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				op = -1;
				objf.restaurar();
				repaint();
			}
		});*/

		/*escalar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String v1 = JOptionPane
						.showInputDialog("Dama la cantidad a escalar en x");
				String v2 = JOptionPane
						.showInputDialog("Dama la cantidad a escalar en y");
				double escx, escy;
				try {
					escx = Double.parseDouble(v1);
					escy = Double.parseDouble(v1);
				} catch (NumberFormatException e) {
					escx = 1.0;
					escy = 1.0;
				}

				objf.escalar_punto(escx, escy);// obtenemos
											// el
											// objeto
											// grafico
				repaint();
			}
		});
		deformar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String v1 = JOptionPane
						.showInputDialog("Dama la cantidad a deformar en x");
				String v2 = JOptionPane
						.showInputDialog("Dama la cantidad a deformar en y");
				double defx, defy;
				try {
					defx = Double.parseDouble(v1);
					defy = Double.parseDouble(v1);
				} catch (NumberFormatException e) {
					// valor que no me altere la figura
					defx = 0.0;// x=x+AY
					defy = 0.0;// y=Bx+y
				}

				objf.deformar_punto(defx, defy);// obtenemos
											// el
											// objeto
											// grafico
				repaint();
			}
		});
		trasladar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String v1=JOptionPane.showInputDialog("Dame la cantidad a trasladar en x");
				String v2=JOptionPane.showInputDialog("Dame la cantidad a trasladar en y");
				double tx, ty;
				try
				{
					tx=Double.parseDouble(v1);
					ty=Double.parseDouble(v2);
				}
				catch(NumberFormatException e)
				{
					tx=0.0; //
					ty=0.0; //
				}
				objf.trasladar(tx,ty);
				repaint();
			}
		});		rotarc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String v1 = JOptionPane
						.showInputDialog("Dama los grados a rotar en contra sentido de las manecillas del relog");
				
				double ang;
				try {
					ang = Double.parseDouble(v1);
					
				} catch (NumberFormatException e) {
					ang = 0.0;
					
				}

				//objf.rotar_contra_punto(ang);// obtenemos
											// el
											// objeto
											// grafico
				objf.rotarT(Transformaciones2D2.this.getGraphics(),ang);
				repaint();
			}
		});
		rotars.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String v1 = JOptionPane
						.showInputDialog("Dama los grados a rotar en el sentido de las manecillas del relog");
				
				double ang;
				try {
					ang = Double.parseDouble(v1);
					
				} catch (NumberFormatException e) {
					ang = 0.0;
					
				}

				objf.rotar_sentido_punto(ang);// obtenemos
											// el
											// objeto
											// grafico
				repaint();
			}
		});
		reflexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				op=1;
				
				repaint();
			}
		});
		*/





		/*JPanel abajo = new JPanel();
		abajo.setLayout(new FlowLayout());
		abajo.add(dibujarb);
		abajo.add(escalar);
		abajo.add(deformar);
		abajo.add(trasladar);
		abajo.add(rotarc);
		abajo.add(rotars);
		abajo.add(reflexion);
		cont.add(abajo, BorderLayout.SOUTH);
		*/
		objf = new Figura2(b);
		
		vent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vent.setResizable(false);
		this.addMouseListener(new eventoRatonclick(Transformaciones2D2.this));
		this.addMouseMotionListener(new eventoRatonarrastre(Transformaciones2D2.this));
		this.addMouseWheelListener(this);
		barrah=new JToolBar("Accesos rapidos",JToolBar.VERTICAL);
		//agregar acciones dela barra de herrameintas
		URL rutaa1=getClass().getResource("rec/zoombarra.png");
		Action a1=new AbstractAction("Escalar",new ImageIcon(rutaa1)) {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				objf.escalar_punto(1.1, 1.1);
				repaint();
				
				
			}
		};
		
		//acciones
		URL rutaa3=getClass().getResource("rec/shapes-move-backwardbarra.png");
		Action a3=new AbstractAction("Restaurar",new ImageIcon(rutaa3)) {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				objf.restaurar();
				repaint();
				
				
			}
		};
		a3.putValue(Action.SHORT_DESCRIPTION, "Restaurar la figura");
		a3.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.ALT_MASK));
		barrah.add(a3);
		
		
		a1.putValue(Action.SHORT_DESCRIPTION, "Escala una figura en 0.1");
		a1.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.ALT_MASK));
		barrah.add(a1);
		URL rutaa2=getClass().getResource("rec/shapes-move-backwardbarra.png");
		Action a2=new AbstractAction("Rotar",new ImageIcon(rutaa2)) {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				objf.rotar_contra_punto(1);
				repaint();
				
				
			}
		};
		a2.putValue(Action.SHORT_DESCRIPTION, "Rotar la figura en 1°");
		a2.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.ALT_MASK));
		barrah.add(a2);
		
		
		URL rutaa4=getClass().getResource("rec/shapes-move-backwardbarra.png");
		Action a4=new AbstractAction("Salir",new ImageIcon(rutaa4)) {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
				repaint();
				
				
			}
		};
		a4.putValue(Action.SHORT_DESCRIPTION, "Salir del programa");
		a4.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.ALT_MASK));
		barrah.add(a4);
		
		cont.add(barrah,BorderLayout.EAST);
		
		vent.setVisible(true);
		
		
	}
	
	public void hacerMenu() {
		// TODO Auto-generated method stub
		Inicio=new JMenu("Inicio");
		Trasnfor=new JMenu("Transformaciones");
		Acerca=new JMenu("Acerca De");
		Barram.add(Inicio);
		Barram.add(Trasnfor);
		Barram.add(Acerca);
		//inicio
		Restaurar=new JMenuItem("Restaurar");
		Salir=new JMenuItem("Salir");
		Inicio.add(Restaurar);
		Inicio.setMnemonic('R');
		Inicio.add(Salir);
		Inicio.setMnemonic('S');
		Restaurar.setToolTipText("Restuarar figura");
		//poner imagen
		URL rutar=getClass().getResource("rec/MD-reload.png");
		Restaurar.setIcon(new ImageIcon(rutar));
		URL rutas=getClass().getResource("rec/cancel.png");
		Salir.setIcon(new ImageIcon(rutas));
		
		//Trasnformaciones
		Escalar=new JMenuItem("Escalar");
		Deformar=new JMenuItem("Deformar");
		Rotar=new JMenuItem("Rotar");
		Trasladar=new JMenuItem("Trasladar");
		Reflejar=new JMenuItem("Reflejar");
		Trasnfor.add(Escalar);
		Trasnfor.add(Deformar);
		Trasnfor.add(Rotar);
		Trasnfor.add(Trasladar);
		Trasnfor.add(Reflejar);
		URL rutae=getClass().getResource("rec/zoom.png");
		Escalar.setIcon(new ImageIcon(rutae));
		URL rutad=getClass().getResource("rec/games-alt-1.png");
		Deformar.setIcon(new ImageIcon(rutad));
		URL rutaro=getClass().getResource("rec/shapes-rotate-anticlockwise.png");
		Rotar.setIcon(new ImageIcon(rutaro));
		URL rutat=getClass().getResource("rec/shapes-move-backward.png");
		Trasladar.setIcon(new ImageIcon(rutat));
		URL rutare=getClass().getResource("rec/shapes-flip-horizontal.png");
		Reflejar.setIcon(new ImageIcon(rutare));
		
		
		
		//Acerca de
		Autor=new JMenuItem("Autor");
		URL rutaa=getClass().getResource("rec/about.png");
		Autor.setIcon(new ImageIcon(rutaa));
		Version=new JMenuItem("Version");
		URL rutav=getClass().getResource("rec/window.png");
		Version.setIcon(new ImageIcon(rutav));
		Ayuda=new JMenuItem("Ayuda");
		URL rutaay=getClass().getResource("rec/help.png");
		Ayuda.setIcon(new ImageIcon(rutaay));
		Acerca.add(Autor);
		Acerca.add(Version);
		Acerca.add(Ayuda);
		//Poner Imagenes
		
		
		
		//eventos
		Salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
				
			}
		});
		
		Rotar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Crotar ob=new Crotar(vent, true);
				int val[] =ob.mostrar();
				if(val[0]==1)
				{
					//rotar izq
					objf.rotar_sentido_punto(val[1]);
				}
				if(val[0]==2)
				{
					//rotar izq
					objf.rotar_contra_punto(val[1]);
				}
				repaint();
				
				
			}
		});
		
		Restaurar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				objf.restaurar();
				repaint();
				
			}
		});
		Reflejar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				op=1;
				repaint();
				
			}
		});
		
		
		
		
		
	}
	
	
	

	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (op==1)
			{
			objf.dibujarRef(g);
			op=-1;
		}
		else
		objf.dibujarb(g);

	}

	public static void main(String[] args) {

		double figxb[][] = new double[][] { { 200, 300 }, { 250, 200 },
				{ 320, 200 }, { 320, 360 }, { 250, 360 }, { 250, 320 },
				{ 200, 300 } };

		new Transformaciones2D2(figxb);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent mw) {
		// TODO Auto-generated method stub
		int movi=mw.getWheelRotation();
		if(movi<0){
			//arriba aumentar
			objf.escalar(1.05, 1.05);
		}
		else {
			//abajo
			
			objf.escalar(0.95, 0.95);
		}
		repaint();
		
	}
}
