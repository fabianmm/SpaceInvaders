
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Sprite
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Sprite</code>
 *
 * @author Fabián Montemayor A01280156 & Mauro Amarante A01191903
 * @version 2.0 
 * @date 4/03/15
 */

public class Sprite extends Animacion{

        private boolean bVisible;   // boleana de visibilidad del sprite
        private Image imgImage; // imagen del sprite
        private Animacion aniAnimacion;   // Animacion del sprite
        protected int iX;   // posicion x del sprite
        protected int iY;   // posicion y del sprite
        protected boolean bDying;   // boleana de muerte del sprite
        protected int iDx;  // entero de la dirección del sprite

        /**
          * Sprite
          * 
          * Metodo constructor usado para crear el objeto sprite.
          * 
          */
        public Sprite() {
            bVisible = true;    // inicializa bVisible en true
        }

        /**
          * die
          * 
          * Metodo de muerte del sprite.
          * 
          */
        public void die() {
            bVisible = false;   // apaga la boleana de visibilidad
        }

        /**
          * isVisible
          * 
          * Metodo de acceso que regresa el estado de la boleana bVisible.
          * 
          */
        public boolean isVisible() {
            return bVisible;
        }

        /**
          * setVisible
          * 
          * Metodo modificador usado para cambiar el estado de la boleana de 
          * visibilidad.
          * 
          * @param bVisible es el estado de la boleana de visibilidad.
          * 
          */
        protected void setVisible(boolean bVisible) {
            this.bVisible = bVisible;
        }

        /**
          * setImage
          * 
          * Metodo modificador usado para cambiar la imagen del sprite.
          * 
          * @param imgImagen es la imagen a utilizar.
          * 
          */
        public void setImage(Image imgImage) {
            this.imgImage = imgImage;
        }

        /**
          * getImage
          * 
          * Metodo de acceso que regresa la imagen del sprite.
          * 
          */
        public Image getImage() {
            return imgImage;
        }
        
        /**
          * setAnimacion
          * 
          * Metodo modificador usado para cambiar la animacion del sprite
          * 
          * @param aniAnimacion es la animacion a utilizar
          * 
          */
        public void setAnimacion(Animacion aniAnimacion) {
            this.aniAnimacion = aniAnimacion;
        }

        /**
          * getAnimacion
          * 
          * Metodo de acceso que regresa la animacion del sprite
          * 
          */
        public Animacion getAnimacion() {
            return aniAnimacion;
        }

        /**
          * setX
          * 
          * Metodo modificador usado para cambiar la posicion x del sprite.
          * 
          * @param iX es la posicion x del sprite.
          * 
          */
        public void setX(int iX) {
            this.iX = iX;
        }

        /**
          * setY
          * 
          * Metodo modificador usado para cambiar la posicion y del sprite.
          * 
          * @param iY es la posicion y del sprite.
          * 
          */
        public void setY(int iY) {
            this.iY = iY;
        }
        
        /**
          * getY
          * 
          * Metodo de acceso que regresa la posición y del sprite.
          * 
          */
        public int getY() {
            return iY;
        }
        /**
          * getX
          * 
          * Metodo de acceso que regresa la posición x del sprite.
          * 
          */
        public int getX() {
            return iX;
        }

        /**
          * setDying
          * 
          * Metodo modificador usado para cambiar el estado de la boleana bDying.
          * 
          * @param bDying es la boleana para actualizar bDying.
          * 
          */
        public void setDying(boolean bDying) {
            this.bDying = bDying;
        }

        /**
          * isDying
          * 
          * Metodo de acceso que regresa el estado de la boleana bDying.
          * 
          */
        public boolean isDying() {
            return this.bDying;
        }
        
        /**
          * intersecta
          *
          * Metodo que checa si una base intersecta a otro.
          *
          * @param objObjeto es un objeto de la clase <code>Object</code>
          * @return un boleano para saber si intersecta o no
          */
        public boolean intersecta(Object objObjeto) {
            if (objObjeto instanceof Sprite) {
                // castea el objeto a Sprite
                Sprite sprObjeto = (Sprite) objObjeto;
                
                // crea un image icon de cada objeto
                ImageIcon imiEsteObjeto = new ImageIcon(imgImage);
                ImageIcon imiOtroObjeto = new ImageIcon(sprObjeto.getImage());
                
                // crea un objeto rectangulo de cada objeto
                Rectangle rctEsteObjeto = new Rectangle(this.getX(), this.getY(),
                        imiEsteObjeto.getIconWidth(), 
                        imiEsteObjeto.getIconHeight());
                Rectangle rctObjetoParam = new Rectangle(sprObjeto.getX(), 
                        sprObjeto.getY(), imiOtroObjeto.getIconWidth(), 
                        imiOtroObjeto.getIconHeight());
                
                // regresa la intereccion entre ellos
                return rctEsteObjeto.intersects(rctObjetoParam);
            } 
            else {
                return false;
            }
        }
}
