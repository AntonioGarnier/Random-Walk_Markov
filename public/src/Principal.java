/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@see <a href = "https://es.wikipedia.org/wiki/Camino_aleatorio" > Camino Aleatorio Wikipedia </a>
@version 1.0
*/

import javax.swing.JOptionPane;

public class Principal {

	public static void main(String[] args) {
		
		try {
			String miniFrame = JOptionPane.showInputDialog("Introduce el número de caminos a visualizar(Recomendado MAX 5)");
			int numeroCaminos = Integer.parseInt(miniFrame);
			CaminoAleatorioControlador CaminoAleatorio= new CaminoAleatorioControlador(numeroCaminos);
			CaminoAleatorio.setVisible(true);
		}
		catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Debes Introducir un valor entero", "VALOR ERRONEO", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		
	}

}
