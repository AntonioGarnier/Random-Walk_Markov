/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@see <a href = "https://es.wikipedia.org/wiki/Camino_aleatorio" > Camino Aleatorio Wikipedia </a>
@version 1.0
*/


public enum BotonEnum {
	EMPEZAR("Empezar"),
	FINALIZAR("Finalizar"), 
	COLOR("Cambia Color"), 
	RANDCOLOR("Color Aleatorio"), 
	DENSIDAD("Densidad"), 
	VELOCIDAD("Velocidad"), 
	RESET("Reset"),
	NBOTONES (8);
	
	private String valor;
	private int NBotones;
	
	/**
	 * Constructor para el string de cada enumerado
	 * @param valor Define el valor del enumerado
	 */
	private BotonEnum (String valor) {
		this.valor = valor;
	}
	
	/**
	 * Constructor para el entero de cada enumerado
	 * @param valor Define el valor del enumerado
	 */
	private BotonEnum (int valor) {
		this.NBotones = valor;
	}
	
	/**
	 * Getter
	 * @return Devuelve el número de botones de la botonera
	 */
	public int getNumeroBotones ()	{
		return this.NBotones;
	}
	
	/**
	 * Getter
	 * @return Devuelve el nombre del boton
	 */
	public String getTexto ()	{
		return valor;
	}
	
}
