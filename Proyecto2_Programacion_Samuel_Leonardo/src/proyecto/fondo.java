/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;
/**
 *
 * @author unwir
 */
public class fondo extends JPanel {
    
    private Image fondito;
    
    public fondo(String ruta){
        fondito = new ImageIcon(getClass().getResource(ruta)).getImage();
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setOpaque(false);
        g.drawImage(fondito, 0, 0, getWidth(), getHeight(), this);
        
    }
    
}
