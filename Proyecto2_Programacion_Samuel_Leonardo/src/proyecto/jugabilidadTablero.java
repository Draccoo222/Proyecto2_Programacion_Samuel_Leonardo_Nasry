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

    private casillas primeraCasillaSelecc = null;
    private boolean esperarSegundoClic = false;

    private casillas[][] tablero;

    public jugabilidadTablero(casillas[][] tablero) {
        this.tablero = tablero;
        manejarClics();
    }

    private void manejarClics() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
               
                
                casillas casilla = tablero[i][j];
                if(casilla != tablero[4][2] && casilla != tablero[4][3] && casilla != tablero[5][2] && casilla != tablero[5][3] && casilla != tablero[4][6] && casilla != tablero[4][7] && casilla != tablero[5][6] && casilla != tablero[5][7]){
                casilla.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        manejarClicCasilla((casillas) e.getSource());
                    }
                });}
                else{
                    System.out.println("Casilla bloqueada");
                }
            }
        }
    }

    private void manejarClicCasilla(casillas casillaSelecc) {
        if (!esperarSegundoClic) {
            // Primer clic
            if (casillaSelecc.tienePersonaje()) {
                primeraCasillaSelecc = casillaSelecc;
                esperarSegundoClic = true;

            } else {

            }
        } else {
            // Segundo clic
            casillas segundaCasilla = casillaSelecc;

            // Verificar si es un movimiento válido
            if (esMovimientoValido(primeraCasillaSelecc, segundaCasilla)) {
                moverPersonaje(primeraCasillaSelecc, segundaCasilla);

            } else {

            }

            // Resetear selección
            primeraCasillaSelecc = null;
            esperarSegundoClic = false;
        }
    }

    private boolean esMovimientoValido(casillas origen, casillas destino) {
        // No puede mover a la misma casilla
        if (origen == destino) {
            System.out.println("Misma casilla seleccionada");
            return false;
        }

        // Verificar si el personaje puede moverse
        if (!origen.getPersonaje().isPuedeMover()) {
            System.out.println("Personaje no puede moverse");
            return false;
        }

        // Si la casilla destino tiene personaje, verificar si es del equipo contrario
        if (destino.tienePersonaje()) {
            boolean mismoEquipo = origen.getPersonaje().isEsHeroe() == destino.getPersonaje().isEsHeroe();
            if (mismoEquipo) {
                System.out.println("No puedes atacar a tu propio equipo");
                return false;
            } else {

                return true;
            }
        }

        return true; // Movimiento a casilla vacía
    }

    private void moverPersonaje(casillas origen, casillas destino) {
        // Obtener el personaje de la casilla origen
        Fichas personajeAMover = origen.getPersonaje();

        // Si la casilla destino tiene un personaje (combate)
        if (destino.tienePersonaje()) {
            Fichas personajeDefensor = destino.getPersonaje();

            // Resolver combate
            resolverCombate(origen, destino, personajeAMover, personajeDefensor);
        } else {
            // Movimiento simple a casilla vacía

            // Mover el personaje
            destino.asignarPersonaje(personajeAMover);
            origen.quitarPersonaje();
        }
    }

    private void resolverCombate(casillas casillaAtacante, casillas casillaDefensor,
            Fichas atacante, Fichas defensor) {
        int rangoAtacante = atacante.getRango();
        int rangoDefensor = defensor.getRango();

        // Casos especiales
        if (rangoAtacante == 0 && rangoDefensor == 10) {
            // Nova Blast/Pumpkin Bomb puede derrotar al rango 10
            casillaDefensor.asignarPersonaje(atacante);
            casillaAtacante.quitarPersonaje();
        } else if (rangoAtacante == 10 && rangoDefensor == 0) {
            // Rango 10 es derrotado por Nova Blast/Pumpkin Bomb
            // El defensor se queda en su lugar, el atacante es eliminado
            casillaAtacante.quitarPersonaje();
        } else if (rangoAtacante > rangoDefensor) {
            // Atacante gana (rango mayor)
            casillaDefensor.asignarPersonaje(atacante);
            casillaAtacante.quitarPersonaje();
        } else if (rangoAtacante < rangoDefensor) {
            // Defensor gana (rango mayor)
            // El defensor se queda en su lugar, el atacante es eliminado
            casillaAtacante.quitarPersonaje();
        } else {
            // Empate - ambos son eliminados
            casillaAtacante.quitarPersonaje();
            casillaDefensor.quitarPersonaje();
        }
    }

    // Método para cancelar selección actual
    public void cancelarSeleccion() {
        primeraCasillaSelecc = null;
        esperarSegundoClic = false;
    }

    // Método para obtener información del estado actual
    public void mostrarEstadoSeleccion() {
        if (esperarSegundoClic && primeraCasillaSelecc != null) {
            System.out.println("Personaje seleccionado: "
                    + primeraCasillaSelecc.getPersonaje().getNombrePersonaje()
                    + " en [" + primeraCasillaSelecc.getFila() + "][" + primeraCasillaSelecc.getColumna() + "]");
        } else {
            System.out.println("Ningún personaje seleccionado");
        }
    }

}
