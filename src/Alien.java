
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
  * Alien
  *
  * Modela la definición de todos los objetos de tipo
  * <code>Alien</code>
  *
  * @author Fabián Montemayor A01280156 & Mauro Amarante A01191903
  * @version 2.0 
  * @date 4/03/15
  */

public class Alien extends Sprite {

    private Bomb bmbBomb; // bomba del alien, objeto del tipo Bomb
    private final String sShot = "alien.png";   // string con el url de la imagen
    private Animacion aniAlien;   // Animacion para el alien

    /**
      * Alien
      * 
      * Metodo constructor usado para crear el objeto Alien.
      * 
      * @param iX es la variable para actualizar la posición x del alien
      * @param iY es la variable para actualizar la posicion y del alien
      */
    public Alien(int iX, int iY) {
        this.iX = iX;
        this.iY = iY;
        bmbBomb = new Bomb(iX, iY);
        ImageIcon imiImagen = new ImageIcon(this.getClass().getResource(sShot));
        //Se cargan las imágenes(cuadros) para la animación de la portada
        Image alien1 = Toolkit.getDefaultToolkit().getImage(this.getClass().
                            getResource("alien.png"));
        Image alien2 = Toolkit.getDefaultToolkit().getImage(this.getClass().
                            getResource("alien2.png"));
        //Se crea la animación de la portada
        aniAlien = new Animacion();
	aniAlien.sumaCuadro(alien1, 10);
        aniAlien.sumaCuadro(alien2, 10);
        setAnimacion(aniAlien);
        setImage(imiImagen.getImage());
    }

    /**
     * act
     * 
     * Metodo de actualización de posición del alien.
     * 
     */
    public void act(int iDirection) {
        this.iX += iDirection;
    }

    /**
     * getBomb
     * 
     * Metodo de acceso que regresa el objeto Bomb del Alien.
     * 
     */
    public Bomb getBomb() {
        return bmbBomb;
    }

    /**
      * Bomb
      *
      * Modela la definición de todos los objetos de tipo
      * <code>Bomb</code>
      *
      * @author Fabián Montemayor A01280156 & Mauro Amarante A01191903
      * 
      */
    public class Bomb extends Sprite {

        private final String sBomb = "bomb.png";    // string del url de la imagen
        private boolean bDestroyed; // boleana de si la bomba está destruida o no

        /**
          * Bomb
          * 
          * Metodo constructor del objeto Bomb.
          * 
          * @param iX es la posición x de la bomba.
          * @param iY es la posición y de la bomba.
          * 
          */
        public Bomb(int iX, int iY) {
            setDestroyed(true);
            this.iX = iX;
            this.iY = iY;
            ImageIcon imiImagen = new ImageIcon(this.getClass().getResource(sBomb));
            setImage(imiImagen.getImage());
        }

        /**
          * setDestroyed
          * 
          * Metodo de actualización que cambia el estado de la boleana bDestroyed.
          * 
          * @param bDestroyed es la boleana que va a actualizar.
          * 
          */
        public void setDestroyed(boolean bDestroyed) {
            this.bDestroyed = bDestroyed;
        }
        
        /**
          * isDestroyed
          * 
          * Metodo de acceso que regresa el estado de la boleana bDestroyed de Bomb.
          * 
          */
        public boolean isDestroyed() {
            return bDestroyed;
        }
    }
}
