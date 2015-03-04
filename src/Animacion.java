

import java.awt.Image;
import java.util.ArrayList;

/**
  * Alien
  *
  * Modela la definición de todos los objetos de tipo
  * <code>Animacion</code>
  * La clase Animacion maneja una serie de imágenes (cuadros)
  * y la cantidad de tiempo que se muestra cada cuadro.
  *
  * @author Fabián Montemayor A01280156 & Mauro Amarante A01191903
  * @version 2.0 
  * @date 4/03/15
  */

public class Animacion {
	
	private ArrayList ArrCuadros;
	private int iIndiceCuadroActual;
	private long lTiempoDeAnimacion;
	private long lDuracionTotal;
	
       /**
       * Animacion
       * 
       * Metodo constructor usado para crear el objeto Animacion. 
       * Crea una nueva Animacion vacía
       * 
       */
	public Animacion() {
		ArrCuadros = new ArrayList();
		lDuracionTotal = 0;
		iniciar();
	}
	
       /**
       * sumaCuadro
       * 
       * Añade una cuadro a la animación con la duración
       * indicada (tiempo que se muestra la imagen).
       * 
       * @param imaImagen es la variable para almacenar una imagen de los cuadros
       * @param lDuracion es la variable [ara la duracion de dicha imagen
       */	
	public synchronized void sumaCuadro(Image imaImagen, long lDuracion) {
		lDuracionTotal += lDuracion;
		ArrCuadros.add(new cuadroDeAnimacion(imaImagen, lDuracionTotal));
	}
	
	// Inicializa la animación desde el principio. 
	public synchronized void iniciar() {
		lTiempoDeAnimacion = 0;
		iIndiceCuadroActual = 0;
	}
	
	/**
        * actualiza
        * 
        * Actualiza la imagen (cuadro) actual de la animación,
        * si es necesario.
        * 
        * @param lTiempoTranscurrido es la variable para almacenar el tiempo 
        * transcurrido de la animacion
        */
	public synchronized void actualiza(long lTiempoTranscurrido) {
		if (ArrCuadros.size() > 1){
			lTiempoDeAnimacion += lTiempoTranscurrido;
			
			if (lTiempoDeAnimacion >= lDuracionTotal) {
				lTiempoDeAnimacion = lTiempoDeAnimacion 
                                                            % lDuracionTotal;
				iIndiceCuadroActual = 0; 
			}
			
			while (lTiempoDeAnimacion > 
                                  getCuadro(iIndiceCuadroActual).lTiempoFinal) {
				iIndiceCuadroActual++;
			}
		}
	}
	
        /**
        * getImagen
        * 
        * Captura la imagen actual de la animación. Regeresa null
        * si la animación no tiene imágenes.
        *
        */
	public synchronized Image getImagen() {
		if (ArrCuadros.size() == 0) {
			return null;
		}
		else {
			return getCuadro(iIndiceCuadroActual).imaImagen;
		}
	}
	
        /**
        * getCuadro
        * 
        * Captura el cuadro actual de la animacion.
        *
        */
	private cuadroDeAnimacion getCuadro(int i) {
		return (cuadroDeAnimacion)ArrCuadros.get(i);
	}
	
        /**
        * cuadroDeAnimacion
        * 
        * Clase que define la animacion como tal
        *
        */
	public class cuadroDeAnimacion {
		
		Image imaImagen;
		long lTiempoFinal;
		
		public cuadroDeAnimacion() {
			this.imaImagen = null;
			this.lTiempoFinal = 0;
		}
		
		public cuadroDeAnimacion(Image imaImagen, long lTiempoFinal) {
			this.imaImagen = imaImagen;
			this.lTiempoFinal = lTiempoFinal;
		}
		
		public Image getImagen() {
			return imaImagen;
		}
		
		public long getTiempoFinal() {
			return lTiempoFinal;
		}
		
		public void setImagen (Image imaImagen) {
			this.imaImagen = imaImagen;
		}
		
		public void setTiempoFinal(long lTiempoFinal) {
			this.lTiempoFinal = lTiempoFinal;
		}
	}
}