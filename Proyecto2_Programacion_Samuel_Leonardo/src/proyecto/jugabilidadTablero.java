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
public class jugabilidadTablero {

    private JButton primerBotonSelecc = null;
    private boolean esperarSegundoClic = false;

    private JButton[][] tablero;

    public jugabilidadTablero(JButton[][] tablero) {
        this.tablero = tablero;
        manejarClics();
    }

    private void manejarClics() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
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

    private void manejarClicBoton(JButton botonSelecc) {
        if (!esperarSegundoClic) {
            primerBotonSelecc = botonSelecc;
            esperarSegundoClic = true;
            System.out.println("Esperando segundo Boton");
        } else {
            JButton segundo = botonSelecc;
            cambioDeBoton(primerBotonSelecc, segundo);
            System.out.println("Boton Cambiado");

        }
    }

    public void cambioDeBoton(JButton boton1, JButton boton2) {
        Icon temp = boton1.getIcon();
        boton1.setIcon(boton2.getIcon());
        boton2.setIcon(temp);
    }

}
