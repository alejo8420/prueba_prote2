/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proteccion.escalador.demo.servicesimpl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import proteccion.escalador.demo.services.GestorImagenService;

@Service
public class GestorImagenServiceImpl implements GestorImagenService {

    final static private String FORMAT = "jpg";

    final static private double WITDH_TARGET = 1123;
    final static private double LENGHT_TARGET = 796;
    final static private double AREA_A4 = WITDH_TARGET * LENGHT_TARGET;
    public String respuesta = "";

    @Override
    public BufferedImage loadImage(String name) {
        System.out.println("Cargando Imagen");
        try {
            return ImageIO.read(new File(System.getProperty("user.dir") + "/" + name));
        } catch (IOException e) {
            System.out.println("No se pudo cargar la imagen " + name + " de " + name);
            System.out.println("El error fue : " + e.getClass().getName() + " " + e.getMessage());

            return null;
        }
    }

    @Override
    public boolean saveImage(Image image) {

        System.out.println("Salvando Imagen ");
        
        try {
            ImageIO.write((RenderedImage) image, FORMAT, new File("Imagen_Nueva.jpg"));

        } catch (IOException ex) {
            Logger.getLogger(GestorImagenServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean procesarImagen() {
        System.out.println("Procesando Imagen");
        BufferedImage image = loadImage("Imagen.jpg");
        double xWidth = image.getWidth();
        double xLength = image.getHeight();
        double ratio = xWidth / xLength;

 //Comprobamos el area de la imagen 
        if (xWidth * xLength > AREA_A4) {
            saveImage(redimensionar(image));
            return true;

        } else {
            System.out.println("Imagen más pequeña. No es necesario procesarla");
            respuesta = "Imagen no cumple tamaño mínimo";
            return false;
        }

    }

    public BufferedImage redimensionar(BufferedImage bf) {
        int ancho = bf.getWidth();
        int alto = bf.getHeight();     
        
        double nuevo_ancho = WITDH_TARGET/(double)ancho;
        double nuevo_alto = LENGHT_TARGET/(double)alto;
        
        double nuevo_relacion = Math.min(nuevo_ancho, nuevo_alto);
               
        double escalaAncho2 = ancho*nuevo_relacion;
        double escalaAlto2 = alto*nuevo_relacion;
        
        int escalaAncho = (int)(escalaAncho2);
        int escalaAlto = (int)(escalaAlto2);
        
        BufferedImage bufim = new BufferedImage(escalaAncho, escalaAlto, bf.getType());
        Graphics2D g = bufim.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bf, 0, 0, escalaAncho, escalaAlto, 0, 0, ancho, alto, null);
        g.dispose();
        return bufim;
    }

}
