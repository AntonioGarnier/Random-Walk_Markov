/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@see <a href = "https://es.wikipedia.org/wiki/Camino_aleatorio" > Camino Aleatorio Wikipedia </a>
@version 1.0
*/


import javax.swing.JButton;

public class MiBoton extends JButton {

	private static final long serialVersionUID = 1L;
	private BotonEnum tipoBoton;
	
	/**
	 * Constructor por defecto de mi boton
	 * @param nombreBoton Define el nombre
	 * @param tipoBoton Define el tipo de boton
	 */
	public MiBoton (String nombreBoton, BotonEnum tipoBoton)	{
		super(nombreBoton);
		setTipoBoton(tipoBoton);
	}

	/**
	 * @return the tipoBoton
	 */
	public BotonEnum getTipoBoton() {
		return tipoBoton;
	}

	/**
	 * @param tipoBoton the tipoBoton to set
	 */
	public void setTipoBoton(BotonEnum tipoBoton) {
		this.tipoBoton = tipoBoton;
	}
	
}
