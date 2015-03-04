
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
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
    private int iDeathCounter = 0; //contador para dibujar animacion de muerte
    
    private long lTiempoActual; // tiempo actual
    private long lTiempoInicial;    // tiempo inicial
    private long lTiempoTranscurrido;   //tiempo transcurrido

    private boolean bIngame = true; // boleana de si esta jugando o no
    private boolean bPause = false; // boleana de pausar el juego
    private boolean bInstr = false; // boleana de instrucciones
    private boolean bCred = false;
    
    private final String sExpl = "explosion.png";   // url de la imagen explosion
    private final String sAlienpix = "alien.png";   // url de la imagen de alien
    private String sMessage = "Game Over";  // mensage de game over
    
    // sonidos del juego
    private SoundClip sndDisparo = new SoundClip("disparoShip.wav");
    private SoundClip sndBombas = new SoundClip("disparosAlien.wav");
    private SoundClip sndInicio = new SoundClip("inicioJuego.wav");
    private SoundClip sndLose = new SoundClip("lose.WAV");
    private SoundClip sndAlien = new SoundClip("muerteAlien.wav");
    private SoundClip sndMuerte = new SoundClip("muerteShip.wav");
    private SoundClip sndWin = new SoundClip("win.wav");
    private SoundClip sndInstr = new SoundClip("instruccionesCreditos.wav");
    
    private String sNombreArchivo = "juego.txt";    //Nombre del archivo.
    private String[] sArr;    //Arreglo del archivo divido.
    
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
        sndInicio.play();
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

    /**
      * gameInit
      * 
      * Metodo que inicializa el juego.
      * 
      */
    public void gameInit() {

        arlAliens = new ArrayList();    // crea una nueva array list de aliens

        // agrega la imagen del alien
        ImageIcon imiImagen = new ImageIcon(this.getClass().getResource(sAlienpix));

        // crea cada alien (24)
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = new Alien(iAlienX + 35 * j, iAlienY + 25 * i);
                alien.setImage(imiImagen.getImage());
                arlAliens.add(alien);
            }
        }

        // crea el objeto Player
        plyPlayer = new Player();
        
        // crea el objeto Shot
        shtShot = new Shot();

        // inicializa el thread
        if (animator == null || !bIngame) {
            animator = new Thread(this);
            animator.start();
        }
    }

    /**
      * drawAliens
      * 
      * Metodo que dibuja los aliens.
      * 
      * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
      */
    public void drawAliens(Graphics graGrafico) 
    {    
        Iterator it = arlAliens.iterator();
        
        // mientras existan aliens
        while (it.hasNext()) {
            Alien alien = (Alien) it.next();

            // si existe el alien
            if (alien.isVisible()) {
                alien.getAnimacion().actualiza(lTiempoTranscurrido);
                graGrafico.drawImage(alien.getAnimacion().getImagen(), alien.getX(), alien.getY(), this);
            }


            
            // si el alien se muere
            if (alien.isDying()) {
                alien.dieAnimacion();
                alien.getAnimacion().actualiza(lTiempoTranscurrido);
                graGrafico.drawImage(alien.getAnimacion().getImagen(), alien.getX(), alien.getY(), this);
                alien.die();
            }
        }
    }

    /**
      * drawPlayer
      * 
      * Metodo que dibuja el jugador.
      * 
      * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
      */
    public void drawPlayer(Graphics graGrafico) {
        // mientras exista
        if (plyPlayer.isVisible()) {
            graGrafico.drawImage(plyPlayer.getImage(), plyPlayer.getX(), plyPlayer.getY(), this);
        }

        // si el juegador se muere termina el juego
        if (plyPlayer.isDying()) {
            plyPlayer.die();
            bIngame = false;
        }
    }

    /**
      * drawShot
      * 
      * Metodo que dibuja el shot.
      * 
      * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
      */
    public void drawShot(Graphics graGrafico) {
        // mientras exista
        if (shtShot.isVisible())
            graGrafico.drawImage(shtShot.getImage(), shtShot.getX(), shtShot.getY(), this);
    }

    /**
      * drawBombing
      * 
      * Metodo que dibuja las bombas.
      * 
      * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
      */
    public void drawBombing(Graphics graGrafico) {
        // crea un iterador
        Iterator i3 = arlAliens.iterator();

        // mientras haya bombas
        while (i3.hasNext()) {
            Alien a = (Alien) i3.next();

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {
                graGrafico.drawImage(b.getImage(), b.getX(), b.getY(), this); 
            }
        }
    }

    /**
      * Metodo <I>paint</I> sobrescrito de la clase <code>JPanel</code>,
      * heredado de la clase Container.<P>
      * En este metodo se dibuja la imagen con la posicion actualizada,
      * ademas que cuando la imagen es cargada te despliega una advertencia.
      * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
      */
    public void paint(Graphics graGrafico)
    {
      super.paint(graGrafico);
      // dibuja el fondo
      graGrafico.setColor(Color.black);
      graGrafico.fillRect(0, 0, dimD.width, dimD.height);
      graGrafico.setColor(Color.green);   
      
      // si esta en el juego
      if (bIngame) {
        if (bInstr) {
            // imagen de instrucciones
            URL urlInstr = this.getClass().getResource("intrucciones.png");
            Image imaInstr = Toolkit.getDefaultToolkit().getImage(urlInstr);
            graGrafico.drawImage(imaInstr, 0, -20 , this);
            sndInstr.play();
        }
        else if (bCred) {
            //imagen creditos
            URL urlCred = this.getClass().getResource("creditos.png");
            Image imaCred = Toolkit.getDefaultToolkit().getImage(urlCred);
            graGrafico.drawImage(imaCred, 0, -20 , this);
        }
        else  {
            sndInstr.stop();
            // dibuja la linea de ground
            graGrafico.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
            // dibuja cada sprite
            drawAliens(graGrafico);
            drawPlayer(graGrafico);
            drawShot(graGrafico);
            drawBombing(graGrafico);
            
        }
        
      }
      else {
        URL urlGameOver = this.getClass().getResource("gameOver.png");
        Image imaGameOver = Toolkit.getDefaultToolkit().getImage(urlGameOver);
        graGrafico.drawImage(imaGameOver, 0, 0 , this);
      }

      Toolkit.getDefaultToolkit().sync();
      graGrafico.dispose();
    }

    /**
     * gameOver
     * 
     * Metodo de cuando termina el juego.
     */
    public void gameOver()
    {
        sndLose.play();
        // Dibuja el game over
        Graphics graGrafico = this.getGraphics();

        URL urlGameOver = this.getClass().getResource("gameOver.png");
        Image imaGameOver = Toolkit.getDefaultToolkit().getImage(urlGameOver);
        graGrafico.drawImage(imaGameOver, 0, 0 , this);
        
    }

    /**
     * animationCycle
     * 
     * Metodo que actualiza el juego.
     * 
     */
    public void animationCycle()  {
        //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
        lTiempoTranscurrido = System.currentTimeMillis() - lTiempoActual;
            
        //Guarda el tiempo actual
       	lTiempoActual += lTiempoTranscurrido;
        
        // si destruye todos los arlAliens, gana el juego
        if (iDeaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            bIngame = false;
            sndWin.play();
            sMessage = "Game won!";
        }

        // actualiza el jugador
        plyPlayer.act();

        // checa colision del disparo
        if (shtShot.isVisible()) {
            // crea un iterador para la lista de aliens
            Iterator it = arlAliens.iterator();
            
            // mientras haya aliens
            while (it.hasNext()) {
                Alien alien = (Alien) it.next();
                
                // si el disparo colisiona con un alien
                if (alien.isVisible() && shtShot.isVisible()) {
                    if (shtShot.intersecta(alien)) {
                            alien.setDying(true);   // destruye el alien
                            iDeaths++;  // aumenta las muertes de aliens
                            shtShot.die();  // destruye el disparo
                            sndAlien.play();
                        }
                }
            }
            
            int y = shtShot.getY();
            y -= 4;
            // si el disparo se sale de la pantalla lo destruye
            if (y < 0) {
                shtShot.die();
            }
            else {
                shtShot.setY(y);
            }
        }

        // actualiza aliens
        // crea un iterador
        Iterator it1 = arlAliens.iterator();

        // mientras haya aliens
        while (it1.hasNext()) {
            Alien a1 = (Alien) it1.next();
            int iX = a1.getX(); // la posicion x del alien

            // si colisiona con la derecha de la ventana
            if (iX  > BOARD_WIDTH - BORDER_RIGHT) {
                // cambia la direccion
                iDirection = -1;
                Iterator i1 = arlAliens.iterator();
                // los baja en y
                while (i1.hasNext()) {
                    Alien a2 = (Alien) i1.next();
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }
            
            // si colisiona con la izquierda de la ventana
            if (iX < BORDER_LEFT) {
                // cambia la direccion
                iDirection = 1;

                Iterator i2 = arlAliens.iterator();
                // los baja en y
                while (i2.hasNext()) {
                   sndBombas.play();

                   Alien a = (Alien)i2.next();
                   a.setY(a.getY() + GO_DOWN);
               }
           }
        }

        // crea un iterador de la lista
        Iterator it = arlAliens.iterator();
        
        while (it.hasNext()) {
            Alien alien = (Alien) it.next();
            if (alien.isVisible()) {
                // agarra la posicion y de alien
                int iY = alien.getY();
                // si un alien llega a la tierra pierde
                if (iY > GROUND - ALIEN_HEIGHT) {
                    bIngame = false;
                    sMessage = "Invasion!";
                }
                // actualiza los aliens
                alien.act(iDirection);
            }
        }

        // actualiza las bombas de los aliens

        // crea un iterador de la lista de aliens
        Iterator i3 = arlAliens.iterator();
        // crea un random
        Random rndGenerator = new Random();

        
        while (i3.hasNext()) {
            int shtShot = rndGenerator.nextInt(15); // numero de bombas
            Alien aliAlien = (Alien) i3.next(); // crea un alien auxiliar
            Alien.Bomb bmbBomba = aliAlien.getBomb(); // crea una bomba
            // crea una bomba 
            
            
            if (shtShot == CHANCE && aliAlien.isVisible() && bmbBomba.isDestroyed()) {
                bmbBomba.setDestroyed(false);
                bmbBomba.setX(aliAlien.getX());
                bmbBomba.setY(aliAlien.getY());   
            }  

            // mientras exita el jugador y la bomba
            if (plyPlayer.isVisible() && !bmbBomba.isDestroyed()) {
                // si colisiona la bomba con el jugador
                if (bmbBomba.intersecta(plyPlayer) ) {
                        ImageIcon imiImagen = 
                            new ImageIcon(this.getClass().getResource(sExpl));
                        plyPlayer.setImage(imiImagen.getImage());
                        plyPlayer.setDying(true);   // destruye el jugador
                        sndMuerte.play();
                        bmbBomba.setDestroyed(true);    // destruye la bomba
                    }
            }
            
            // mientras exista la bomba
            if (!bmbBomba.isDestroyed()) {
                // la actualiza hacia abajo
                bmbBomba.setY(bmbBomba.getY() + 1);   
                // si llega a la tierra
                if (bmbBomba.getY() >= GROUND - BOMB_HEIGHT) {
                    bmbBomba.setDestroyed(true);    // destruye la bomba
                }
            }
        }
    }

    
    /**
     * run
     * 
     * Metodo que corre el juego y actualiza el hilo.
     * 
     */
    public void run() {
        //Guarda el tiempo actual del sistema
        lTiempoActual = System.currentTimeMillis();

        long lTimeDiff, lSleep;

        // mientras no pierda
        while (bIngame) {
            // pinta y actualiza
            repaint();
            
            if (!bPause && !bInstr && !bCred) { // checa si no esta pausado el juego
                animationCycle();
            }
            

            lTimeDiff = System.currentTimeMillis() - lTiempoActual;
            lSleep = DELAY - lTimeDiff;

            if (lSleep < 0) 
                lSleep = 2;
            try {
                Thread.sleep(lSleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            lTiempoActual = System.currentTimeMillis();
        }
        gameOver();
    }
    
    /**
     * 
     * leeArchivo
     * 
     * Metodo que lee a informacion de un archivo.
     *
     * @throws IOException
     */
    private void leeArchivo() throws IOException{
        BufferedReader fileIn;
    	try {
    		fileIn = new BufferedReader(new FileReader(sNombreArchivo));
    	} catch (FileNotFoundException e){
    		File puntos = new File(sNombreArchivo);
    		PrintWriter fileOut = new PrintWriter(puntos);
    		fileOut.println("100,demo");
    		fileOut.close();
    		fileIn = new BufferedReader(new FileReader(sNombreArchivo));
    	}
        
    	String sDato = fileIn.readLine();

        
    
    	fileIn.close();
    }
    /**
     * 
     * grabaArchivo 
     * 
     * Metodo graba información en un archivo.
     *
     * @throws IOException
     */
    private void grabaArchivo() throws IOException {
        PrintWriter fileOut = new PrintWriter(new FileWriter(sNombreArchivo));
        
    	fileOut.close();	        
    }

    
    private class TAdapter extends KeyAdapter {

        /**
          * keyReleased
          * 
          * Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
          * En este metodo maneja el evento que se genera al soltar cualquier la tecla.
          * 
          * @param kveEvent es el <code>evento</code> generado al soltar las teclas.
          */
        public void keyReleased(KeyEvent kveEvent) {
            plyPlayer.keyReleased(kveEvent); // manda el evento a player
        }

        /**
          * keyPressed
          * 
          * Metodo <I>keyPressed</I> sobrescrito de la interface <code>KeyListener</code>.<P>
          * En este metodo maneja el evento que se genera al presionar cualquier la tecla.
          * 
          * @param kveEvent es el <code>evento</code> generado al presionar las teclas.
          */
        public void keyPressed(KeyEvent kveEvent) {
          // manda el evento a player
          plyPlayer.keyPressed(kveEvent);

          int x = plyPlayer.getX();
          int y = plyPlayer.getY();

          // si esta en el juego
          if (bIngame) {
            // si le pica a Alt
            if (kveEvent.isAltDown()) {
                // crea un disparo
                if (!shtShot.isVisible()) {
                    shtShot = new Shot(x, y);
                    sndDisparo.play();
                }
                    
                
            }
            else if (kveEvent.getKeyCode() == KeyEvent.VK_P) {
                // pausa el jeugo
                bPause = !bPause;
            }
            else if (kveEvent.getKeyCode() == KeyEvent.VK_I) {
                bInstr = !bInstr;
                if (bCred) {
                    bCred = false;
                }
                sndInstr.play();
            }
            else if (kveEvent.getKeyCode() == KeyEvent.VK_R) {
                bCred = !bCred;
                if (bInstr) {
                    bInstr = false;
                }
            }
          }
        }
    }
}
