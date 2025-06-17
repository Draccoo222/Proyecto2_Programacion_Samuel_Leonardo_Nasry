/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 *
 * @author unwir
 */
public class jugabilidadTablero 
{
    private JButton primerBotonSelecc = null;
    private boolean esperarSegundoClic = false;
    
    private JButton[][] tablero;
    
    public jugabilidadTablero(JButton[][] tablero){
        this.tablero = tablero;
        
    }
    
        
    private void manejarClics() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton boton = tablero[i][j];
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        manejarClicBoton((JButton) e.getSource());

                    }

                });

            }

        }

    }
    
    

    


    private void manejarClicBoton(JButton botonSelecc){
        if(!esperarSegundoClic){
            primerBotonSelecc = botonSelecc;
            esperarSegundoClic = true;
        }else{
            
            JButton segundo = botonSelecc;


        }
    }

   
    
    
}
