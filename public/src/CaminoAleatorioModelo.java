/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@see <a href = "https://es.wikipedia.org/wiki/Camino_aleatorio" > Camino Aleatorio Wikipedia </a>
@version 1.0
*/


import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CaminoAleatorioModelo {

	private static final int NUMERO_DIRECCIONES = 4;		// Número de direcciones que puede tomar el camino
	private static final int DELAY_POR_DEFECTO = 50;		// Delay por defecto a la hora de dibujar el camino
	private Point puntoInicial, puntoActual;					// Punto inicial desde donde se dibuja el camino y punto actual o ultimo punto
	private int NFilas, NColumnas;								// Número de filas y columnas de la cuadrícula
	private ArrayList<Point> puntosVisitados = new ArrayList<Point> ();
	private Random generadorAleatorio = new Random ();		// Generador de números aleatorios
	private Color colorCamino = Color.RED;						// Atributo para el color del camino (por defecto rojo)
	private int delay = getDelayPorDefecto();					// Atributo para el delay a la hora de dibujar el camino
	private Timer caminoTiempo = new Timer();					// Timer para ir mostrando el camino
	
	/**
	 * Constructor por defecto
	 * @param inicio Define el punto de inicio del camino
	 * @param filas Define el número de filas 
	 * @param columnas Define el número de columnas
	 * @param colorCamino Define el color del camino
	 */
	public CaminoAleatorioModelo (Point inicio, int filas, int columnas, Color colorCamino)	{
		setPuntoInicial (inicio);
		setPuntoActual(inicio);
		setNFilas(filas);
		setNColumnas(columnas);
		setColorCamino(colorCamino);
		getPuntosVisitados().add(inicio);
	}

	/**
	 * Calcula la siguiente dirección a tomar de manera aleatoria
	 * @return Devuelve un enumerado indicando la dirección tomada
	 */
	public DireccionEnum siguienteDireccion ()	{
		int direccion = getGeneradorAleatorio().nextInt(getNumeroDirecciones());
		switch (direccion) {
		case 0: return DireccionEnum.ARRIBA;
		case 1: return DireccionEnum.ABAJO;
		case 2: return DireccionEnum.IZQUIERDA;
		case 3: return DireccionEnum.DERECHA;
		default: return null;
		}
	}
	
	/**
	 * Calcula el siguiente punto, lo añade al vector de puntos visitados y devuelve true en caso de ser posible
	 * @return
	 */
	public boolean siguientePunto ()	{
		if (!limitesAlcanzados(getPuntoActual()))	{
			DireccionEnum direccion = siguienteDireccion();
			Point nuevoPunto = null;
			switch (direccion) {
			case ARRIBA: 
				nuevoPunto = new Point ((int)getPuntoActual().getX(), (int)getPuntoActual().getY() - 1);
				break;
			case ABAJO:
				nuevoPunto = new Point ((int)getPuntoActual().getX(), (int)getPuntoActual().getY() + 1);
				break;
			case IZQUIERDA:
				nuevoPunto = new Point ((int)getPuntoActual().getX() - 1, (int)getPuntoActual().getY());
				break;
			case DERECHA: 
				nuevoPunto = new Point ((int)getPuntoActual().getX() + 1, (int)getPuntoActual().getY());
				break;
			}
			getPuntosVisitados().add(nuevoPunto);
			setPuntoActual(nuevoPunto);
			return true;
		}
		return false;
	}
	
	/**
	 * Calcula si los límites han sido alcanzados o no
	 * @param actual Define el punto actual que se está analizando
	 * @return Devuelve true en caso de alcanzar los límites
	 */
	public boolean limitesAlcanzados (Point actual) {
		if (actual.getX() == getNColumnas() || actual.getX() == 0)
			return true;
		if (actual.getY() == getNFilas() || actual.getY() == 0)
			return true;
		return false;
	}
	
	/**
	 * Inicia el timer y ejecuta una tarea cada "delay" tiempo
	 */
	public void comienzaTimer () {
	    getCaminoTiempo().cancel();
	    
	    TimerTask caminoTiempoTarea = new TimerTask() {

	      public void run() {
	      	if (!siguientePunto())
	      		getCaminoTiempo().cancel();
	      }
	    };

	    setCaminoTiempo(new Timer());
	    getCaminoTiempo().scheduleAtFixedRate(caminoTiempoTarea, 0, getDelay());
	  }
	
	/**
	 * Resetea el camino cancelando el timer, limpiando el vector de puntos visitados y seteando el punto actual al inicial
	 */
	public void reset ()	{
		getCaminoTiempo().cancel();
		getPuntosVisitados().clear();
		setPuntoActual(getPuntoInicial());
	}
	
	/**
	 * Cambia el color del camino de forma aleatoria usando la clase ColorAleatorio
	 * @return
	 */
	public Color cambiaColor() {
		setColorCamino(ColorAleatorio.getColorAleatorio());
		return getColorCamino();
	}

	/**
	 * @return the delayPorDefecto
	 */
	public static int getDelayPorDefecto() {
		return DELAY_POR_DEFECTO;
	}

	/**
	 * @return the delay
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	/**
	 * @return the colorCamino
	 */
	public Color getColorCamino() {
		return colorCamino;
	}

	/**
	 * @param colorCamino the colorCamino to set
	 */
	public void setColorCamino(Color colorCamino) {
		this.colorCamino = colorCamino;
	}

	/**
	 * @return the caminoTiempo
	 */
	public Timer getCaminoTiempo() {
		return caminoTiempo;
	}

	/**
	 * @param caminoTiempo the caminoTiempo to set
	 */
	public void setCaminoTiempo(Timer caminoTiempo) {
		this.caminoTiempo = caminoTiempo;
	}

	/**
	 * @return the numeroDirecciones
	 */
	public static int getNumeroDirecciones() {
		return NUMERO_DIRECCIONES;
	}

	/**
	 * @return the puntoInicial
	 */
	public Point getPuntoInicial() {
		return puntoInicial;
	}

	/**
	 * @param puntoInicial the puntoInicial to set
	 */
	public void setPuntoInicial(Point puntoInicial) {
		this.puntoInicial = puntoInicial;
	}

	/**
	 * @return the puntoActual
	 */
	public Point getPuntoActual() {
		return puntoActual;
	}

	/**
	 * @param puntoActual the puntoActual to set
	 */
	public void setPuntoActual(Point puntoActual) {
		this.puntoActual = puntoActual;
	}

	/**
	 * @return the nFilas
	 */
	public int getNFilas() {
		return NFilas;
	}

	/**
	 * @param nFilas the nFilas to set
	 */
	public void setNFilas(int nFilas) {
		NFilas = nFilas;
	}

	/**
	 * @return the nColumnas
	 */
	public int getNColumnas() {
		return NColumnas;
	}

	/**
	 * @param nColumnas the nColumnas to set
	 */
	public void setNColumnas(int nColumnas) {
		NColumnas = nColumnas;
	}

	/**
	 * @return the puntosVisitados
	 */
	public ArrayList<Point> getPuntosVisitados() {
		return puntosVisitados;
	}

	/**
	 * @param puntosVisitados the puntosVisitados to set
	 */
	public void setPuntosVisitados(ArrayList<Point> puntosVisitados) {
		this.puntosVisitados = puntosVisitados;
	}

	/**
	 * @return the generadorAleatorio
	 */
	public Random getGeneradorAleatorio() {
		return generadorAleatorio;
	}

	/**
	 * @param generadorAleatorio the generadorAleatorio to set
	 */
	public void setGeneradorAleatorio(Random generadorAleatorio) {
		this.generadorAleatorio = generadorAleatorio;
	}
	
	
	
}
