
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
public class Alien extends Sprite {

    private Bomb bomb;
    private final String shot = "alien.png";

    public Alien(int x, int y) {
        this.iX = x;
        this.iY = y;

        bomb = new Bomb(x, y);
        ImageIcon imiImagen = new ImageIcon(this.getClass().getResource(shot));
        setImage(imiImagen.getImage());

    }

    public void act(int direction) {
        this.iX += direction;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public class Bomb extends Sprite {

        private final String bomb = "bomb.png";
        private boolean destroyed;

        public Bomb(int x, int y) {
            setDestroyed(true);
            this.iX = x;
            this.iY = y;
            ImageIcon ii = new ImageIcon(this.getClass().getResource(bomb));
            setImage(ii.getImage());
        }

        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
            return destroyed;
        }
    }
}
