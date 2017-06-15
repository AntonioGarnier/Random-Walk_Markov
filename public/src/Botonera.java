/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@see <a href = "https://es.wikipedia.org/wiki/Camino_aleatorio" > Camino Aleatorio Wikipedia </a>
@version 1.0
*/


import java.awt.Font;
import java.util.ArrayList;

public class Botonera extends ArrayList<MiBoton> {

	private static final long serialVersionUID = 1L;
	private int indiceBotonera;			// Número identificativo de la botonera

	/**
	 * Constructor de la botonera
	 * @param indice Define el número identificativo
	 */
	public Botonera (int indice)	{
		super();
		setIndiceBotonera(indice);
		initBotonera();
	}
	
	/**
	 * @return the indiceBotonera
	 */
	public int getIndiceBotonera() {
		return indiceBotonera;
	}

	/**
	 * @param indiceBotonera the indiceBotonera to set
	 */
	public void setIndiceBotonera(int indiceBotonera) {
		this.indiceBotonera = indiceBotonera;
	}

	/**
	 * Método para encontrar un boton
	 * @param tipoBoton Define el tipo de boton
	 * @return Devuelve el boton que coincide con el tipo de boton especificado por parámetros
	 */
	public MiBoton getBoton (BotonEnum tipoBoton) {
		for (MiBoton boton : this)
			if (boton.getTipoBoton() == tipoBoton)
				return boton;
		return null;
	}
	
	/**
	 * Aquí se inicializan e instancian los botones de la botonera
	 */
	public void initBotonera ()	{
		// Boton desactivado, solo informativo
		MiBoton info = new MiBoton ("Controles " + (getIndiceBotonera() + 1), BotonEnum.NBOTONES);
		info.setBackground(ColorAleatorio.getColorAleatorio());
		info.setOpaque(true);
		info.setEnabled(false);
		info.setFont(new Font ("Serif", Font.BOLD, 15));
		add(info);
		// Resto de botones de la botonera
		for (int i = 0; i < BotonEnum.values().length - 1; i ++) {
			add(new MiBoton (BotonEnum.values()[i].getTexto(), BotonEnum.values()[i]));
		} 
	}
}
