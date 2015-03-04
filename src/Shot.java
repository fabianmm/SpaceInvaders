
import javax.swing.ImageIcon;

/**
 * Shot
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Shot</code>
 *
 * @author Fabián Montemayor A01280156 & Mauro Amarante A01191903
 * @version 2.0 
 * @date 4/03/15
 */

public class Shot extends Sprite {

    private String sShot = "shot.png";  // string con el nombre de la imagen
    private final int iH_SPACE = 6;     // constante H_SPACE 
    private final int iV_SPACE = 1;     // constante V_SPACE

    /**
      * Shot
      * 
      * Metodo constructor usado para crear el objeto Shot cuando no contiene 
      * parámetros.
      * 
      */
    public Shot() {
        ImageIcon imiImagen = new ImageIcon(this.getClass().getResource(sShot));
        setImage(imiImagen.getImage()); // actualiza la imagen del shot
    }

    /**
      * Shot
      * 
      * Metodo constructor usado para crear el objeto Shot.
      * 
      * @param iX es la variable para actualizar la posición x del shot
      * @param iY es la variable para actualizar la posicion y del shot
      */
    public Shot(int iX, int iY) {
        ImageIcon imiImagen = new ImageIcon(this.getClass().getResource(sShot));
        setImage(imiImagen.getImage()); // actualiza la imagen del shot
        setX(iX + iH_SPACE);    // actualiza la x del shot
        setY(iY - iV_SPACE);    // actualiza la y del shot
    }
}