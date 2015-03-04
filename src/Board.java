
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
  * Board
  *
  * Modela la definición de todos los objetos de tipo
  * <code>Board</code>
  *
  * @author Fabián Montemayor A01280156 & Mauro Amarante A01191903
  * @version 2.0 
  * @date 4/03/15
  */

public class Board extends JPanel implements Runnable, Commons { 

    private Dimension dimD;    // Variable Dimensión del Juego
    private ArrayList arlAliens;   // Lista de arlAliens
    private Player plyPlayer;  // Variable del Jugador
    private Shot shtShot;  // Variable del disparo

    private int iAlienX = 150;   // posición x del alien
    private int iAlienY = 5; // posición y del alien
    private int iDirection = -1; // direccion del jugador
    private int iDeaths = 0;    // numero de arlAliens muertos

    private boolean bIngame = true; // boleana de si esta jugando o no
    private final String sExpl = "explosion.png";   // url de la imagen explosion
    private final String sAlienpix = "alien.png";   // url de la imagen de alien
    private String sMessage = "Game Over";  // mensage de game over

    private Thread animator;    // thread

    /**
      * Board
      * 
      * Metodo constructor usado para crear el objeto Board.
      * 
      */
    public Board() {
        // escucha el teclado
        addKeyListener(new TAdapter());
        setFocusable(true);
        // establece las dimensiones
        dimD = new Dimension(BOARD_WIDTH, BOARD_HEIGTH);
        // establece el color de fondo
        setBackground(Color.black);
        // empieza el juego
        gameInit();
        setDoubleBuffered(true);
    }

    /**
      * addNotify
      * 
      * Metodo que hace el componente visible. 
      * 
      */
    public void addNotify() {
        super.addNotify();
        gameInit();
    }

    public void gameInit() {

        arlAliens = new ArrayList();

        ImageIcon ii = new ImageIcon(this.getClass().getResource(sAlienpix));

        for (int i=0; i < 4; i++) {
            for (int j=0; j < 6; j++) {
                Alien alien = new Alien(iAlienX + 18*j, iAlienY + 18*i);
                alien.setImage(ii.getImage());
                arlAliens.add(alien);
            }
        }

        plyPlayer = new Player();
        shtShot = new Shot();

        if (animator == null || !bIngame) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void drawAliens(Graphics g) 
    {
        Iterator it = arlAliens.iterator();

        while (it.hasNext()) {
            Alien alien = (Alien) it.next();

            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {
                alien.die();
            }
        }
    }

    public void drawPlayer(Graphics g) {

        if (plyPlayer.isVisible()) {
            g.drawImage(plyPlayer.getImage(), plyPlayer.getX(), plyPlayer.getY(), this);
        }

        if (plyPlayer.isDying()) {
            plyPlayer.die();
            bIngame = false;
        }
    }

    public void drawShot(Graphics g) {
        if (shtShot.isVisible())
            g.drawImage(shtShot.getImage(), shtShot.getX(), shtShot.getY(), this);
    }

    public void drawBombing(Graphics g) {

        Iterator i3 = arlAliens.iterator();

        while (i3.hasNext()) {
            Alien a = (Alien) i3.next();

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this); 
            }
        }
    }

    public void paint(Graphics g)
    {
      super.paint(g);

      g.setColor(Color.black);
      g.fillRect(0, 0, dimD.width, dimD.height);
      g.setColor(Color.green);   

      if (bIngame) {

        g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
        drawAliens(g);
        drawPlayer(g);
        drawShot(g);
        drawBombing(g);
      }

      Toolkit.getDefaultToolkit().sync();
      g.dispose();
    }

    public void gameOver()
    {

        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGTH);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH/2 - 30, BOARD_WIDTH-100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH/2 - 30, BOARD_WIDTH-100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(sMessage, (BOARD_WIDTH - metr.stringWidth(sMessage))/2, 
            BOARD_WIDTH/2);
    }

    public void animationCycle()  {

        // si destruye todos los arlAliens, gana el juego
        if (iDeaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            bIngame = false;
            sMessage = "Game won!";
        }

        // actualiza el jugador
        plyPlayer.act();

        // checa colision del disparo
        if (shtShot.isVisible()) {
            Iterator it = arlAliens.iterator();
            int shtShotX = shtShot.getX();
            int shtShotY = shtShot.getY();

            while (it.hasNext()) {
                Alien alien = (Alien) it.next();
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shtShot.isVisible()) {
                    if (shtShot.intersecta(alien)) {
                            ImageIcon imiImage = 
                                new ImageIcon(getClass().getResource(sExpl));
                            alien.setImage(imiImage.getImage());
                            alien.setDying(true);
                            iDeaths++;
                            shtShot.die();
                        }
                }
            }

            int y = shtShot.getY();
            y -= 4;
            if (y < 0)
                shtShot.die();
            else shtShot.setY(y);
        }

        // arlAliens

         Iterator it1 = arlAliens.iterator();

         while (it1.hasNext()) {
             Alien a1 = (Alien) it1.next();
             int x = a1.getX();

             if (x  >= BOARD_WIDTH - BORDER_RIGHT && iDirection != -1) {
                 iDirection = -1;
                 Iterator i1 = arlAliens.iterator();
                 while (i1.hasNext()) {
                     Alien a2 = (Alien) i1.next();
                     a2.setY(a2.getY() + GO_DOWN);
                 }
             }

            if (x <= BORDER_LEFT && iDirection != 1) {
                iDirection = 1;

                Iterator i2 = arlAliens.iterator();
                while (i2.hasNext()) {
                    Alien a = (Alien)i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }


        Iterator it = arlAliens.iterator();

        while (it.hasNext()) {
            Alien alien = (Alien) it.next();
            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > GROUND - ALIEN_HEIGHT) {
                    bIngame = false;
                    sMessage = "Invasion!";
                }

                alien.act(iDirection);
            }
        }

        // bombs

        Iterator i3 = arlAliens.iterator();
        Random generator = new Random();

        while (i3.hasNext()) {
            int shtShot = generator.nextInt(15);
            Alien a = (Alien) i3.next();
            Alien.Bomb b = a.getBomb();
            if (shtShot == CHANCE && a.isVisible() && b.isDestroyed()) {

                b.setDestroyed(false);
                b.setX(a.getX());
                b.setY(a.getY());   
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int plyPlayerX = plyPlayer.getX();
            int plyPlayerY = plyPlayer.getY();

            if (plyPlayer.isVisible() && !b.isDestroyed()) {
                if ( bombX >= (plyPlayerX) && 
                    bombX <= (plyPlayerX+PLAYER_WIDTH) &&
                    bombY >= (plyPlayerY) && 
                    bombY <= (plyPlayerY+PLAYER_HEIGHT) ) {
                        ImageIcon ii = 
                            new ImageIcon(this.getClass().getResource(sExpl));
                        plyPlayer.setImage(ii.getImage());
                        plyPlayer.setDying(true);
                        b.setDestroyed(true);;
                    }
            }

            if (!b.isDestroyed()) {
                b.setY(b.getY() + 1);   
                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
        }
    }

    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (bIngame) {
            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) 
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
        }
        gameOver();
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            plyPlayer.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {

          plyPlayer.keyPressed(e);

          int x = plyPlayer.getX();
          int y = plyPlayer.getY();

          if (bIngame)
          {
            if (e.isAltDown()) {
                if (!shtShot.isVisible())
                    shtShot = new Shot(x, y);
            }
          }
        }
    }
}
