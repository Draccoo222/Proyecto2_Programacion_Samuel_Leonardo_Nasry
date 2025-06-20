/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author hnleo
 */
public class Fichas {

    private String nombrePersonaje;
    private boolean esHeroe;
    private int rango;
    private boolean puedeMover, visible;
    private String ruta;
    private ImageIcon iconoPersonaje;
    private ImageIcon iconoOculto;
    BufferedImage image = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);

    public Fichas(String nombrePersonaje, boolean esHeroe, int rango, boolean puedeMover, String ruta) {
        this.nombrePersonaje = nombrePersonaje;
        this.esHeroe = esHeroe;
        this.rango = rango;
        this.puedeMover = puedeMover;
        visible = true; // CAMBIO: Cambiado de false a true para que se vean las imágenes
        this.ruta = ruta;
        cargarImagenPersonaje();
    }

    private void cargarImagenPersonaje() {
        if (ruta != null && !ruta.isEmpty()) {
            try {
                // CAMBIO: Usar getClass().getResource() para cargar recursos desde el JAR
                java.net.URL imageURL = getClass().getResource(ruta);
                if (imageURL != null) {
                    ImageIcon imagen = new ImageIcon(imageURL);
                    if (imagen.getIconWidth() > 0) {
                        Image img = imagen.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                        this.iconoPersonaje = new ImageIcon(img);
                        System.out.println("Imagen cargada correctamente: " + ruta); // Debug
                    } else {
                        System.out.println("Imagen no válida: " + ruta); // Debug
                        imagenNull();
                    }
                } else {
                    System.out.println("No se encontró la imagen: " + ruta); // Debug
                    imagenNull();
                }
            } catch (Exception err) {
                System.out.println("Error cargando imagen " + ruta + ": " + err.getMessage()); // Debug
                imagenNull();
            }
        } else {
            imagenNull();
        }
    }

    private void imagenNull() {
        // CAMBIO: Crear imagen con el tamaño correcto
        image = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (esHeroe) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(0, 0, 80, 80);
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, 79, 79); // CAMBIO: Ajustar el borde
        
        // Agregar texto para identificar el personaje
        g.setColor(Color.WHITE);
        g.drawString(nombrePersonaje.substring(0, Math.min(8, nombrePersonaje.length())), 5, 40);

        g.dispose();
        this.iconoPersonaje = new ImageIcon(image);
    }

    public boolean isEsHeroe() {
        return esHeroe;
    }

    public int getRango() {
        return rango;
    }

    public boolean isPuedeMover() {
        return puedeMover;
    }

    public boolean isVisible() {
        return visible;
    }

    public void revelarPersonaje() {
        visible = true;
    }

    public String getRuta() {
        return ruta;
    }
    
    public String getNombrePersonaje(){
        return nombrePersonaje;
    }

    public ImageIcon getIconoPersonaje() {
        return iconoPersonaje;
    }

    public ImageIcon getImageOculta() {
        try {
            java.net.URL imageURL = getClass().getResource("/images/cartaoculta.png");
            if (imageURL != null) {
                ImageIcon imagenOriginal = new ImageIcon(imageURL);
                Image img = imagenOriginal.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                this.iconoOculto = new ImageIcon(img);
            } else {
                // Crear imagen oculta por defecto
                BufferedImage imgOculta = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = imgOculta.createGraphics();
                g.setColor(Color.GRAY);
                g.fillRect(0, 0, 80, 80);
                g.setColor(Color.BLACK);
                g.drawRect(0, 0, 79, 79);
                g.setColor(Color.WHITE);
                g.drawString("?", 35, 45);
                g.dispose();
                this.iconoOculto = new ImageIcon(imgOculta);
            }
        } catch (Exception e) {
            System.out.println("Error cargando imagen oculta: " + e.getMessage());
        }
        return iconoOculto;
    }
}