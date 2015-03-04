/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JFrame;

/**
 *
 * @author http://zetcode.com/
 */
public class SpaceInvaders extends JFrame implements Commons {

    /*
    *
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

    /*
    *
    */
    public static void main(String[] args) {
        // crea una nueva instancia de SpaceInvaders
        new SpaceInvaders();
    }
}