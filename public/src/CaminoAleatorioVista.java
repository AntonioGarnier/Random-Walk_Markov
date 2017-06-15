/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@see <a href = "https://es.wikipedia.org/wiki/Camino_aleatorio" > Camino Aleatorio Wikipedia </a>
@version 1.0
*/


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class CaminoAleatorioVista extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final Color colorCuadricula = Color.GRAY;	// Color de la cuadrícula (por defecto Negro)
	private static final int ANCHO_LINEA = 3;							// Ancho de la línea que dibujamos
	private int densidad;													// Densidad del panel
	private ArrayList<CaminoAleatorioModelo> camino = new ArrayList<CaminoAleatorioModelo> ();
	private int filas, columnas;											// Filas y columnas calculadas a partir de la densidad (aproximación)
	
	/**
	 * Constructor por defecto de la vista
	 * @param densidad Define la densidad de la cuadrícula
	 * @param numeroCaminos Define la cantidad de caminos a dibujar
	 */
	public CaminoAleatorioVista (int densidad, int numeroCaminos) {
		setDensidad(densidad);
		for (int i = 0; i < numeroCaminos; i++)
			getCamino().add(new CaminoAleatorioModelo (new Point (getFilas() / 2, getColumnas() / 2), getFilas(), getColumnas(), ColorAleatorio.getColorAleatorio()));
		refrescar();
	}
	
	public CaminoAleatorioVista (int densidad) {
		this(densidad, 1);
	}

	public CaminoAleatorioVista () {
		this(300);
	}
	
	/**
	 * Hace un repaint del panel cada poco tiempo para ir actualizando la pantalla
	 */
	public void refrescar () {
		
		Timer tiempo = new Timer();
		TimerTask tiempoTarea = new TimerTask ()	{

			@Override
			public void run() {
				repaint();
			}
		};
		tiempo.scheduleAtFixedRate(tiempoTarea, 0, 10);
	}
	
	/**
	 * @return the colorcuadricula
	 */
	public static Color getColorcuadricula() {
		return colorCuadricula;
	}

	/**
	 * @return the densidad
	 */
	public int getDensidad() {
		return densidad;
	}

	/**
	 * @param densidad the densidad to set
	 */
	public void setDensidad(int densidad) {
		// Separamos en filas y columnas segun el valor de densidad dado (Hacemos la raiz cuadrada para obtener valores aproximados)
		int celdasPorFila = (int)Math.sqrt(densidad);
		setFilas(celdasPorFila);
		setColumnas(celdasPorFila);
		this.densidad = densidad;
		reiniciaCamino();				//Cada vez que se cambia la densidad se reinicia el/los caminos
		repaint();
	}

	/**
	 * Reinicia el camino reseteando el punto inicial, actualizando los valores de filas y columnas, y luego hace un reset del camino
	 */
	public void reiniciaCamino() {
		getCamino().forEach(elemento-> {
			elemento.setPuntoInicial(new Point (getFilas() / 2, getColumnas() / 2));
			elemento.setNFilas(getFilas());
			elemento.setNColumnas(getColumnas());
			elemento.reset();
		});
	}
	
	/**
	 * @return the anchoLinea
	 */
	public static int getAnchoLinea() {
		return ANCHO_LINEA;
	}

	/**
	 * @return the filas
	 */
	public int getFilas() {
		return filas;
	}

	/**
	 * @param filas the filas to set
	 */
	public void setFilas(int filas) {
		this.filas = filas;
	}

	/**
	 * @return the columnas
	 */
	public int getColumnas() {
		return columnas;
	}

	/**
	 * @param columnas the columnas to set
	 */
	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	/**
	 * @return the camino
	 */
	public ArrayList<CaminoAleatorioModelo> getCamino() {
		return camino;
	}

	/**
	 * @param camino the camino to set
	 */
	public void setCamino(ArrayList<CaminoAleatorioModelo> camino) {
		this.camino = camino;
	}
	
	/**
	 * En este método llamamos a drawCuadricula encargado de pintar la cuadrícula y drawCamino encargado de pintar
	 * el cmaino
	 */
	protected void paintComponent (Graphics objetoGrafico) {
		super.paintComponent(objetoGrafico);
		drawCuadricula (objetoGrafico);
		drawCamino(objetoGrafico);
	}
	
	/**
	 * Método para pintar el camino
	 * @param objetoGrafico
	 */
	protected void drawCuadricula (Graphics objetoGrafico) {
		// Con esta operación podemos obtener el valor de separacion entre filas y columnas para que queden equidistantes
		double espacioEntreFila = (double)getHeight() / (double)getFilas();       
		double espacioEntreColumna = (double)getWidth() / (double)getColumnas();     

		objetoGrafico.setColor(getColorcuadricula());

		// Aquí pintamos las filas
		for (int i = 0; i < getFilas(); i++) {
			objetoGrafico.drawLine(0, (int)(i * espacioEntreFila), getWidth(), (int)(i * espacioEntreFila));
		}
 
		// Aquí pintamos las columnas
		for (int i = 0; i < getColumnas(); i++) {
			objetoGrafico.drawLine((int)(i * espacioEntreColumna), 0, (int)(i * espacioEntreColumna), getHeight());
		}

	}
	
	/**
	 * Con este método pintamos el/los caminos
	 * @param objetoGrafico
	 */
	protected void drawCamino(Graphics objetoGrafico) {
		// Misma operacion que al dibujar la cuadrícula para pintar encima de ella
		double espacioEntreFila = (double)getHeight() / (double)getFilas();       
		double espacioEntreColumna = (double)getWidth() / (double)getColumnas();
		
		// Bucle foreach de los caminos que hay para irlos pintando
		getCamino().forEach(elemento-> { 
			// Hacemos un cast de graphics a graphics2D para poder controlar la anchura de la línea que dibujamos usando basicStroke
			objetoGrafico.setColor(elemento.getColorCamino());
			Graphics2D objetoGrafico2D = (Graphics2D) objetoGrafico;
			objetoGrafico2D.setStroke(new BasicStroke (getAnchoLinea()));
			Polygon caminoDibujo = new Polygon ();
			
			// Guardamos los puntos en el polígono
			for (int i = 0; i < elemento.getPuntosVisitados().size(); i++)
				caminoDibujo.addPoint((int)(elemento.getPuntosVisitados().get(i).getX() * espacioEntreColumna), (int)(elemento.getPuntosVisitados().get(i).getY() * espacioEntreFila));
	
			// Dibujamos la polyline
			objetoGrafico2D.drawPolyline(caminoDibujo.xpoints, caminoDibujo.ypoints, caminoDibujo.npoints);

		});
		
	}
	
}
