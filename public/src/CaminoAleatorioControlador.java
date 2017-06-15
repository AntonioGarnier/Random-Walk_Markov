/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@see <a href = "https://es.wikipedia.org/wiki/Camino_aleatorio" > Camino Aleatorio Wikipedia </a>
@version 1.0
*/


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CaminoAleatorioControlador extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int DENSIDAD_POR_DEFECTO = 1000;					// Densidad por defecto del panel
	private static final String TITULO_FRAME = "Camino Aleatorio";
	private ArrayList<Botonera> almacenBotoneras = new ArrayList<Botonera> ();  // Almacen de todas las botoneras 
	private JPanel botoneraPanel; 					// Panel donde se añaden los botones
	private int caminoAnalizado;						// Índice para saber que camino estamos valorando
	private CaminoAleatorioVista caminoPanel;		// Esta es la vista, el panel donde se pintan los caminos y la cuadrícula
	private int numeroCaminos = 1;					// Número de caminos de nuestro programa
	
	/**
	 * Constructor por defecto del controlador
	 * @param numeroCaminos Define el numero de caminos que tendrá el programa
	 */
	public CaminoAleatorioControlador (int numeroCaminos) {
		setNumeroCaminos(numeroCaminos);
		initFrame();				// Inicializamos el Frame
		initPanelVista();			// Inicializamos el panel de Vista
		initPanelBotones();		// Inicializamos el panel de botones
		getAlmacenBotoneras().forEach(botones->initEscuchas(botones.getIndiceBotonera()));  // Para cada botonera, inicializamos los listener
	}

	/**
	 * Inicialización del frame, se inicializa la ventana maximizada
	 */
	public void initFrame ()	{
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		/*
		Toolkit miPanel = Toolkit.getDefaultToolkit();
		Dimension TamanioPanel = miPanel.getScreenSize();	
		setSize (TamanioPanel.width/2, TamanioPanel.height/2);
		*/		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle (getTituloFrame());
	}
	
	/**
	 * Método para terminar el camino con índice especificado por parámetros
	 * @param indiceBotonera Define el índice para saber que botonera corresponde al camino
	 */
	public void terminarCamino(int indiceBotonera) {
		while (getCaminoPanel().getCamino().get(indiceBotonera).siguientePunto()) {}
		repaint();	
	}
	
	/**
	 * Inicializa los listener
	 * @param indiceBotonera
	 */
	public void initEscuchas (int indiceBotonera)	{
			// Para el boton EMPEZAR
			getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.EMPEZAR).addActionListener(new ActionListener () {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.EMPEZAR).getText().equals(BotonEnum.EMPEZAR.getTexto())) {
						getCaminoPanel().getCamino().get(indiceBotonera).comienzaTimer();
						getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.EMPEZAR).setText("Parar");
					}
					else	{
						getCaminoPanel().getCamino().get(indiceBotonera).getCaminoTiempo().cancel();
						getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.EMPEZAR).setText(BotonEnum.EMPEZAR.getTexto());
					}
				}	
			});
			// Para el boton FINALIZAR	
			getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.FINALIZAR).addActionListener(new ActionListener () {
				@Override
				public void actionPerformed(ActionEvent e) {
					getCaminoPanel().getCamino().get(indiceBotonera).getCaminoTiempo().cancel();
					terminarCamino(indiceBotonera);
					if (!getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.EMPEZAR).getText().equals(BotonEnum.EMPEZAR.getTexto()))
						getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.EMPEZAR).setText(BotonEnum.EMPEZAR.getTexto());
				}
			});
			// Para el boton CAMBIAR COLOR
			getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.COLOR).addActionListener(new ActionListener () {
				@Override
				public void actionPerformed(ActionEvent e) {
					Color color = JColorChooser.showDialog(getCaminoPanel(), "Selecciona un color", Color.ORANGE);
					getCaminoPanel().getCamino().get(indiceBotonera).setColorCamino(color);
				}	
			});
			// Para el boton COLOR ALEATORIO
			getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.RANDCOLOR).addActionListener(new ActionListener () {
				@Override
				public void actionPerformed(ActionEvent e) {
					getCaminoPanel().getCamino().get(indiceBotonera).cambiaColor();
				}
			});
			// Para el boton DENSIDAD			
			getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.DENSIDAD).addActionListener(new ActionListener () {
				@Override
				public void actionPerformed(ActionEvent e) {
					String miniFrame = JOptionPane.showInputDialog("Introduce un valor para la densidad");
					try {	
						int densidad = Integer.parseInt(miniFrame);
						getCaminoPanel().setDensidad(densidad);
						getAlmacenBotoneras().forEach(elemento->elemento.getBoton(BotonEnum.EMPEZAR).setText(BotonEnum.EMPEZAR.getTexto()));
					} 
					catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(getCaminoPanel(), "Debes Introducir un valor entero", "VALOR ERRONEO", JOptionPane.ERROR_MESSAGE);
					}
				}	
			});
			// Para el boton VELOCIDAD
			getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.VELOCIDAD).addActionListener(new ActionListener () {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						String miniFrame = JOptionPane.showInputDialog("Introduce un valor para el delay");
						int delay = Integer.parseInt(miniFrame);
						getCaminoPanel().getCamino().get(indiceBotonera).setDelay(delay);
						getCaminoPanel().getCamino().get(indiceBotonera).getCaminoTiempo().cancel();
						if (getCaminoPanel().getCamino().get(indiceBotonera).getPuntosVisitados().size() > 1)
							getCaminoPanel().getCamino().get(indiceBotonera).comienzaTimer();
					} 
					catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(getCaminoPanel(), "Debes Introducir un valor entero", "VALOR ERRONEO", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			// Para el boton RESET			
			getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.RESET).addActionListener(new ActionListener () {
				@Override
				public void actionPerformed(ActionEvent e) {
					getCaminoPanel().getCamino().get(indiceBotonera).reset();
					if (!getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.EMPEZAR).getText().equals(BotonEnum.EMPEZAR.getTexto()))
						getAlmacenBotoneras().get(indiceBotonera).getBoton(BotonEnum.EMPEZAR).setText(BotonEnum.EMPEZAR.getTexto());
				}		
			});
	}
	
	/**
	 * Inicializa el panel de botones, añadiendo tantas botoneras como se hayan especificado anteriormente y luego añade el
	 * panel de botones al frame, zona Este.
	 */
	public void initPanelBotones () {
		setBotoneraPanel(new JPanel ());
		getBotoneraPanel().setLayout(new GridLayout(0, 2));		
		//getBotoneraPanel().setLayout(new GridLayout(0, BotonEnum.NBOTONES.getNumeroBotones()));
		for (int i = 0; i < getNumeroCaminos(); i++)
			getAlmacenBotoneras().add(new Botonera(i));
		// Bucle for-each anidado usando el metodo "forEach" de ArrayList
		getAlmacenBotoneras().forEach(indice->indice.forEach(boton->getBotoneraPanel().add(boton)));
		/*for (Botonera botones : getAlmacenBotoneras())
			for (MiBoton boton : botones)
				System.out.println();*/
		add(getBotoneraPanel(), BorderLayout.EAST);
	}
	
	/**
	 * Inicializa el panel de vista, con una densidad por defecto y un numero de caminos especificado
	 */
	public void initPanelVista () {
		setCaminoPanel(new CaminoAleatorioVista(getDensidadPorDefecto(), getNumeroCaminos()));
		add(getCaminoPanel(), BorderLayout.CENTER);
	}

	/**
	 * @return the almacenBotoneras
	 */
	public ArrayList<Botonera> getAlmacenBotoneras() {
		return almacenBotoneras;
	}

	/**
	 * @param almacenBotoneras the almacenBotoneras to set
	 */
	public void setAlmacenBotoneras(ArrayList<Botonera> almacenBotoneras) {
		this.almacenBotoneras = almacenBotoneras;
	}

	/**
	 * @return the caminoPanel
	 */
	public CaminoAleatorioVista getCaminoPanel() {
		return caminoPanel;
	}

	/**
	 * @param caminoPanel the caminoPanel to set
	 */
	public void setCaminoPanel(CaminoAleatorioVista caminoPanel) {
		this.caminoPanel = caminoPanel;
	}

	/**
	 * @return the densidadPorDefecto
	 */
	public static int getDensidadPorDefecto() {
		return DENSIDAD_POR_DEFECTO;
	}

	/**
	 * @return the botoneraPanel
	 */
	public JPanel getBotoneraPanel() {
		return botoneraPanel;
	}

	/**
	 * @param botoneraPanel the botoneraPanel to set
	 */
	public void setBotoneraPanel(JPanel botoneraPanel) {
		this.botoneraPanel = botoneraPanel;
	}

	/**
	 * @return the tituloFrame
	 */
	public static String getTituloFrame() {
		return TITULO_FRAME;
	}

	/**
	 * @return the caminoAnalizado
	 */
	public int getCaminoAnalizado() {
		return caminoAnalizado;
	}

	/**
	 * @param caminoAnalizado the caminoAnalizado to set
	 */
	public void setCaminoAnalizado(int caminoAnalizado) {
		this.caminoAnalizado = caminoAnalizado;
	}

	/**
	 * @return the numeroCaminos
	 */
	public int getNumeroCaminos() {
		return numeroCaminos;
	}

	/**
	 * @param numeroCaminos the numeroCaminos to set
	 */
	public void setNumeroCaminos(int numeroCaminos) {
		this.numeroCaminos = numeroCaminos;
	}
	
	
}
