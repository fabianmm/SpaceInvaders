
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author http://zetcode.com/
 */
public class Player extends Sprite implements Commons {

    private final int iSTART_Y = 280; 
    private final int iSTART_X = 270;

    private final String sPlayer = "player.png";
    private int iWidth;

    public Player() {

        ImageIcon imiImagen = new ImageIcon(this.getClass().getResource(sPlayer));

        iWidth = imiImagen.getImage().getWidth(null); 

        setImage(imiImagen.getImage());
        setX(iSTART_X);
        setY(iSTART_Y);
    }

    public void act() {
        iX += iDx;
        if (iX <= 2) 
            iX = 2;
        if (iX >= BOARD_WIDTH - 2 * iWidth) 
            iX = BOARD_WIDTH - 2 * iWidth;
    }

    public void keyPressed(KeyEvent kveEvent) {
        int iKey = kveEvent.getKeyCode();

        if (iKey == KeyEvent.VK_LEFT)
        {
            iDx = -2;
        }

        if (iKey == KeyEvent.VK_RIGHT)
        {
            iDx = 2;
        }
    }

    public void keyReleased(KeyEvent kveEvent) {
        int iKey = kveEvent.getKeyCode();

        if (iKey == KeyEvent.VK_LEFT)
        {
            iDx = 0;
        }

        if (iKey == KeyEvent.VK_RIGHT)
        {
            iDx = 0;
        }
    }
}
