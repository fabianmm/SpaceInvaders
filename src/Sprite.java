
import java.awt.Image;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author http://zetcode.com/
 */
public class Sprite {

        private boolean bVisible;   // boleana de visibilidad del sprite
        private Image imgImage; // imagen del sprite
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
          * Metodo de acceso que regresa la .
          * 
          * @param iY es la posicion y del sprite.
          * 
          */
        public int getY() {
            return iY;
        }

        public int getX() {
            return iX;
        }

        public void setDying(boolean bDying) {
            this.bDying = bDying;
        }

        public boolean isDying() {
            return this.bDying;
        }
}
