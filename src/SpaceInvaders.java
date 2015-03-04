/**
  * SpaceInvaders
  *
  * Juego SpaceInvaders en el cual el jugador debe dispararle a los aliens antes
  * de que lleguen a la tierra.
  *
  * @author Fabián Montemayor A01280156 & Mauro Amarante A01191903
  * @version 2.0 
  * @date 4/03/15
  */
import javax.swing.JFrame;


public class SpaceInvaders extends JFrame implements Commons {

    /**
    * SpaceInvaders
    * 
    * Metodo constructor del juego.
    */
    public SpaceInvaders()
    {
        add(new Board());   // Crea una nueva instancia de objeto Board
        setTitle("Space Invaders"); // establece el título del juego
        setDefaultCloseOperation(EXIT_ON_CLOSE);    
        setSize(BOARD_WIDTH, BOARD_HEIGTH); 
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    /**
    * main
    * 
    * Metodo de main.
    */
    public static void main(String[] args) {
        // crea una nueva instancia de SpaceInvaders
        new SpaceInvaders();
    }
}