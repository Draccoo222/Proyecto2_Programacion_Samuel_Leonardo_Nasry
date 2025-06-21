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
    private Tablero tableroJug;

    public jugabilidadTablero(casillas[][] tablero, Tablero tableroJug) {
        this.tablero = tablero;
        this.tableroJug = tableroJug;
        manejarClics();
    }

    private void manejarClics() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                casillas casilla = tablero[i][j];
                if (casilla != tablero[4][2] && casilla != tablero[4][3] && casilla != tablero[5][2] && casilla != tablero[5][3] && casilla != tablero[4][6] && casilla != tablero[4][7] && casilla != tablero[5][6] && casilla != tablero[5][7]) {
                    casilla.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            manejarClicCasilla((casillas) e.getSource());
                        }
                    });
                } else {
                    System.out.println("Casilla bloqueada");
                }
            }
        }
    }

    private void manejarClicCasilla(casillas casillaSelecc) {
        if (!esperarSegundoClic) {
            // Primer clic
            casillas.limpiarCasillas(tablero);

            if (casillaSelecc.tienePersonaje()) {
                Fichas personaje = casillaSelecc.getPersonaje();
                if (!tableroJug.isTurno(personaje.isEsHeroe())) {
                    JOptionPane.showMessageDialog(null, "Turno incorrecto.");
                    return;
                }
                primeraCasillaSelecc = casillaSelecc;
                primeraCasillaSelecc.marcarSeleccion();
                esperarSegundoClic = true;
                System.out.println("Personaje: " + personaje.getNombrePersonaje());

                // Mostrar casillas disponibles para mover
                mostrarMovimientosDisponibles(casillaSelecc);
            } else {
                System.out.println("Casilla vacia");
            }
        } else {
            // Segundo clic
            casillas segundaCasilla = casillaSelecc;

            if (primeraCasillaSelecc == segundaCasilla) {
                cancelarSeleccion();
                return;
            }

            // Verificar si es un movimiento válido
            if (esMovimientoValido(primeraCasillaSelecc, segundaCasilla)) {
                moverPersonaje(primeraCasillaSelecc, segundaCasilla);
                tableroJug.turnoDespues();
                cancelarSeleccion();
            } else {
                if (segundaCasilla.tienePersonaje() && segundaCasilla.getPersonaje().isEsHeroe() == primeraCasillaSelecc.getPersonaje().isEsHeroe()) {
                    casillas.limpiarCasillas(tablero);
                    primeraCasillaSelecc = segundaCasilla;
                    primeraCasillaSelecc.marcarSeleccion();
                    mostrarMovimientosDisponibles(segundaCasilla);
                } else {
                    System.out.println("Movimiento invalido");
                }
            }
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

        // Verificar si el movimiento es ortogonal y de una sola casilla
        if (!esMovimientoOrtogonalUnico(origen, destino)) {
            System.out.println("Movimiento no ortogonal o más de una casilla");
            return false;
        }

        // Si la casilla destino tiene personaje del mismo equipo, no se puede mover
        if (destino.tienePersonaje()) {
            boolean mismoEquipo = origen.getPersonaje().isEsHeroe() == destino.getPersonaje().isEsHeroe();
            if (mismoEquipo) {
                System.out.println("No puedes mover a una casilla ocupada por tu propio equipo");
                return false;
            } else {
                // Es un enemigo, se puede atacar
                return true;
            }
        }

        return true; // Movimiento a casilla vacía y válido
    }

    /*
      Verifica si el movimiento es ortogonal (vertical u horizontal) y de una sola casilla
     */
    private boolean esMovimientoOrtogonalUnico(casillas origen, casillas destino) {
        int diferenciaFila = Math.abs(destino.getFila() - origen.getFila());
        int diferenciaColumna = Math.abs(destino.getColumna() - origen.getColumna());

        // Movimiento ortogonal de una casilla: 
        // - Una diferencia debe ser 1 y la otra 0
        return (diferenciaFila == 1 && diferenciaColumna == 0)
                || (diferenciaFila == 0 && diferenciaColumna == 1);
    }

    /*
      Obtiene las casillas adyacentes ortogonalmente a una casilla dada
     */
    private casillas[] obtenerCasillasAdyacentes(casillas casilla) {
        int fila = casilla.getFila();
        int columna = casilla.getColumna();

        // Array para almacenar las casillas adyacentes (máximo 4)
        casillas[] adyacentes = new casillas[4];
        int contador = 0;

        // Arriba
        if (fila > 0) {
            adyacentes[contador++] = tablero[fila - 1][columna];
        }

        // Abajo
        if (fila < tablero.length - 1) {
            adyacentes[contador++] = tablero[fila + 1][columna];
        }

        // Izquierda
        if (columna > 0) {
            adyacentes[contador++] = tablero[fila][columna - 1];
        }

        // Derecha
        if (columna < tablero[0].length - 1) {
            adyacentes[contador++] = tablero[fila][columna + 1];
        }

        // Crear array del tamaño exacto
        casillas[] resultado = new casillas[contador];
        System.arraycopy(adyacentes, 0, resultado, 0, contador);

        return resultado;
    }

    /*
      Muestra en consola los movimientos disponibles para un personaje
     */
    private void mostrarMovimientosDisponibles(casillas casilla) {
        if (!casilla.tienePersonaje()) {
            return;
        }
        casillas[] adyacentes = obtenerCasillasAdyacentes(casilla);
        int movimientosDisponibles = 0;

        for (casillas destino : adyacentes) {
            if (destino != null && esMovimientoValido(casilla, destino)) {
                movimientosDisponibles++;
                destino.marcarCasillasDisponibles();
            }
        }

        if (movimientosDisponibles == 0) {
            System.out.println("- No hay movimientos disponibles");
        }

        System.out.println("===============================");
    }

    private boolean esMovimientoValidoParaMostrar(casillas origen, casillas destino) {
        // Verificar si el personaje puede moverse
        if (!origen.getPersonaje().isPuedeMover()) {
            return false;
        }

        // Verificar si el movimiento es ortogonal y de una sola casilla
        if (!esMovimientoOrtogonalUnico(origen, destino)) {
            return false;
        }

        // Si la casilla destino tiene personaje del mismo equipo, no se puede mover
        if (destino.tienePersonaje()) {
            boolean mismoEquipo = origen.getPersonaje().isEsHeroe() == destino.getPersonaje().isEsHeroe();
            if (mismoEquipo) {
                return false;
            }
        }

        return true;
    }

    /*
      Verifica si un personaje tiene movimientos disponibles
     */
    public boolean tieneMovimientosDisponibles(casillas casilla) {
        if (!casilla.tienePersonaje()) {
            return false;
        }

        casillas[] adyacentes = obtenerCasillasAdyacentes(casilla);

        for (casillas destino : adyacentes) {
            if (destino != null && esMovimientoValido(casilla, destino)) {
                return true;
            }
        }

        return false;
    }

    /*
     Cuenta el número de movimientos disponibles para un personaje
     */
    public int contarMovimientosDisponibles(casillas casilla) {
        if (!casilla.tienePersonaje()) {
            return 0;
        }

        casillas[] adyacentes = obtenerCasillasAdyacentes(casilla);
        int contador = 0;

        for (casillas destino : adyacentes) {
            if (destino != null && esMovimientoValido(casilla, destino)) {
                contador++;
            }
        }

        return contador;
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
            System.out.println("Moviendo " + personajeAMover.getNombrePersonaje()
                    + " de [" + origen.getFila() + "][" + origen.getColumna() + "] a ["
                    + destino.getFila() + "][" + destino.getColumna() + "]");
            // Mover el personaje
            destino.asignarPersonaje(personajeAMover);
            origen.quitarPersonaje();
        }
    }

    private void resolverCombate(casillas casillaAtacante, casillas casillaDefensor,
            Fichas atacante, Fichas defensor) {
        int rangoAtacante = atacante.getRango();
        int rangoDefensor = defensor.getRango();

        System.out.println("=== COMBATE ===");
        System.out.println("Atacante: " + atacante.getNombrePersonaje() + " (Rango " + rangoAtacante + ")");
        System.out.println("Defensor: " + defensor.getNombrePersonaje() + " (Rango " + rangoDefensor + ")");

        // Casos especiales
        if (rangoAtacante == 1 && rangoDefensor == 10) {
            //Personaje rango 1 puede ganar a rango 10
            System.out.println("Rango 1, derrota Rango 10");
            casillaDefensor.asignarPersonaje(atacante);
            casillaAtacante.quitarPersonaje();
        } else if (rangoAtacante == 10 && rangoDefensor == 1) {
            System.out.println("¡Rango 10 es derrotado por Nova Blast/Pumpkin Bomb!");
            casillaDefensor.asignarPersonaje(atacante);
            casillaAtacante.quitarPersonaje();

        } else if (rangoAtacante > rangoDefensor) {
            // Atacante gana (rango mayor)
            System.out.println("¡" + atacante.getNombrePersonaje() + " gana el combate!");
            casillaDefensor.asignarPersonaje(atacante);
            casillaAtacante.quitarPersonaje();
        } else if (rangoAtacante < rangoDefensor) {
            // Defensor gana (rango mayor)
            System.out.println("¡" + defensor.getNombrePersonaje() + " defiende exitosamente!");
            casillaAtacante.quitarPersonaje();
        } else {
            // Empate - ambos son eliminados
            System.out.println("¡Empate! Ambos personajes son eliminados.");
            casillaAtacante.quitarPersonaje();
            casillaDefensor.quitarPersonaje();
        }
        System.out.println("===============");
    }

    // Método para cancelar selección actual
    public void cancelarSeleccion() {
        casillas.limpiarCasillas(tablero);
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
