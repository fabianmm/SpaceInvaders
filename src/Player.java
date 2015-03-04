
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
  * Player
  *
  * Modela la definición de todos los objetos de tipo
  * <code>Player</code>
  *
  * @author Fabián Montemayor A01280156 & Mauro Amarante A01191903
  * @version 2.0 
  * @date 4/03/15
  */

public class Player extends Sprite implements Commons {

    private final int iSTART_Y = 500; // constante de la posición inicial en Y
    private final int iSTART_X = 330; // constante de la posición inicial en X

    private final String sPlayer = "player.png";    // url de la imagen
    private int iWidth; // entero del ancho del player

    /**
      * Player
      * 
      * Metodo constructor usado para crear el objeto Player.
      *
      */
    public Player() {

        ImageIcon imiImagen = new ImageIcon(this.getClass().getResource(sPlayer));

        iWidth = imiImagen.getImage().getWidth(null); 

        setImage(imiImagen.getImage());
        setX(iSTART_X);
        setY(iSTART_Y);
    }

    /**
      * act
      * 
      * Metodo que actualiza la posición del player.
      *
      */
    public void act() {
        iX += iDx;  // actualiza la x del player con la dirección
        if (iX <= 2) // no deja que baje de 2
            iX = 2;
        if (iX >= BOARD_WIDTH - 2 * iWidth) // previene que se salga
            iX = BOARD_WIDTH - 2 * iWidth;
    }

    /**
      * Metodo <I>keyPressed</I> sobrescrito de la interface <code>KeyListener</code>.<P>
      * En este metodo maneja el evento que se genera al presionar cualquier la tecla.
      * @param kveEvent es el <code>evento</code> generado al presionar las teclas.
      */
    public void keyPressed(KeyEvent kveEvent) {
        // guarda la tecla presionada
        int iKey = kveEvent.getKeyCode();

        // si la tecla es la flecha izq
        if (iKey == KeyEvent.VK_LEFT) {
            iDx = -2;   // actualiza la dirección en -2
        }
        
        // si la tecla es la flecha der
        if (iKey == KeyEvent.VK_RIGHT) {
            iDx = 2;    // actualiza la direccion en 2
        }
    }

    /**
      * Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
      * En este metodo maneja el evento que se genera al soltar cualquier la tecla.
      * @param kveEvent es el <code>evento</code> generado al soltar las teclas.
      */
    public void keyReleased(KeyEvent kveEvent) {
        // guarda la tecla presionada
        int iKey = kveEvent.getKeyCode();

        // si la tecla es la flecha izq
        if (iKey == KeyEvent.VK_LEFT) {
            iDx = 0;   // actualiza la dirección en 0
        }
        
        // si la tecla es la flecha der
        if (iKey == KeyEvent.VK_RIGHT) {
            iDx = 0;    // actualiza la direccion en 0
        }
    }
}
