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
    private GestionUsuario gestion;
    private boolean esperarSegundoClic = false;
    private TableroPantallaStratego juego;
    private int ganador = -1; // -0 empate  (1- ganador jugador 1) (2- ganador jugador 2)
    private static int victoriasHeroe;
    private static int victoriasVillanos;
    private seleccionDeModo selecModo;
    private casillas[][] tablero;
    private Tablero tableroJug;

    public jugabilidadTablero(casillas[][] tablero, Tablero tableroJug, TableroPantallaStratego juego) {
        this.tablero = tablero;
        this.tableroJug = tableroJug;
        this.juego = juego;
        manejarClics();
    }

    private void manejarClics() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                casillas casilla = tablero[i][j];
                if (!esCasillaBloqueada(casilla)) {
                    casilla.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            manejarClicCasilla((casillas) e.getSource());
                        }
                    });
                }
            }
        }
    }

    private boolean esCasillaBloqueada(casillas casilla) {
        return casilla == tablero[4][2] || casilla == tablero[4][3] || casilla == tablero[5][2] || casilla == tablero[5][3] || casilla == tablero[4][6] || casilla == tablero[4][7] || casilla == tablero[5][6] || casilla == tablero[5][7];
    }

    private void manejarClicCasilla(casillas casillaSelecc) {
        if (!esperarSegundoClic) {
            manejarPrimerClic(casillaSelecc);
        } else {
            manejarSegundoClic(casillaSelecc);
        }
    }

    private void manejarPrimerClic(casillas casillaSelecc) {
        casillas.limpiarCasillas(tablero);

        if (!casillaSelecc.tienePersonaje()) {
            mostrarMensajeError("Selecciona una casilla que contenga una ficha.");
            return;
        }

        Fichas personaje = casillaSelecc.getPersonaje();

        // Verificar si es el turno correcto
        if (!tableroJug.isTurno(personaje.isEsHeroe())) {
            mostrarMensajeError("Turno incorrecto.");
            return;
        }

        // Verificacion si el jugador selecciona tierra o bombas.
        if (!personaje.isPuedeMover()) {
            mostrarMensajeError("La ficha " + personaje.getNombrePersonaje() + " no puede moverse.");
            return;
        }

        // Verificacion si personaje puede moverse
        if (!tieneMovimientosDisponibles(casillaSelecc)) {
            mostrarMensajeError("La ficha " + personaje.getNombrePersonaje() + " no tiene movimientos disponibles.\nEstá bloqueada por su equipo");
            return;
        }
        primeraCasillaSelecc = casillaSelecc;
        primeraCasillaSelecc.marcarSeleccion();
        esperarSegundoClic = true;
        mostrarMovimientosDisponibles(casillaSelecc);
        System.out.println("Personaje seleccionado: " + personaje.getNombrePersonaje());
    }

    private void manejarSegundoClic(casillas casillaSelecc) {
        // Si jugador hace click en la misma casilla se cancela la seleccion
        if (primeraCasillaSelecc == casillaSelecc) {
            cancelarSeleccion();
            return;
        }
        //Si el jugador selecciona otra casilla del mismo equipo
        if (casillaSelecc.tienePersonaje()
                && casillaSelecc.getPersonaje().isEsHeroe() == primeraCasillaSelecc.getPersonaje().isEsHeroe()) {
            if (!casillaSelecc.getPersonaje().isPuedeMover()) {
                mostrarMensajeError("La ficha " + casillaSelecc.getPersonaje().getNombrePersonaje() + " no puede moverse.");
                return;
            }
            if (!tieneMovimientosDisponibles(casillaSelecc)) {
                mostrarMensajeError("La ficha " + casillaSelecc.getPersonaje().getNombrePersonaje() + " no tiene movimientos disponibles.");
                return;
            }

            casillas.limpiarCasillas(tablero);
            primeraCasillaSelecc = casillaSelecc;
            primeraCasillaSelecc.marcarSeleccion();
            mostrarMovimientosDisponibles(casillaSelecc);
            return;
        }

        // Intentar mover a la casilla seleccionada
        String mensajeError = validarMovimiento(primeraCasillaSelecc, casillaSelecc);
        if (mensajeError != null) {
            mostrarMensajeError(mensajeError);
            return;
        }

        moverPersonaje(primeraCasillaSelecc, casillaSelecc);
        tableroJug.turnoDespues();
        cancelarSeleccion();
    }

    private String validarMovimiento(casillas origen, casillas destino) {
        if (!origen.getPersonaje().isPuedeMover()) {
            return "La ficha " + origen.getPersonaje().getNombrePersonaje() + " no puede moverse.";
        }

        Fichas personaje = origen.getPersonaje();

        // Verificacion del tipo de movimiento (por el caso especial)
        if (personaje.getRango() == 2) {
            if (!esMovimientoEspecial(origen, destino)) {
                return "Las fichas de rango 2 se mueven como torres: horizontal o vertical, cualquier distancia.";
            }

            String obstaculoError = verificarCaminoLibre(origen, destino);
            if (obstaculoError != null) {
                return obstaculoError;
            }
        } else {

            if (!movimientoOrtogonal(origen, destino)) {
                return "Movimiento inválido. Solo puedes mover una casilla de manera ortogonal.";
            }
        }

        // Verificacion si intenta moverla a su propio equipo
        if (destino.tienePersonaje()) {
            boolean mismoEquipo = origen.getPersonaje().isEsHeroe() == destino.getPersonaje().isEsHeroe();
            if (mismoEquipo) {
                return "No puedes mover a una casilla ocupada por tu propia ficha.";
            }
        }

        return null;
    }

    private boolean esMovimientoValido(casillas origen, casillas destino) {
        return validarMovimiento(origen, destino) == null;
    }

    private boolean movimientoOrtogonal(casillas origen, casillas destino) {
        int diferenciaFila = Math.abs(destino.getFila() - origen.getFila());
        int diferenciaColumna = Math.abs(destino.getColumna() - origen.getColumna());

        return (diferenciaFila == 1 && diferenciaColumna == 0)
                || (diferenciaFila == 0 && diferenciaColumna == 1);
    }

    private boolean esMovimientoEspecial(casillas origen, casillas destino) {
        int diferenciaFila = Math.abs(destino.getFila() - origen.getFila());
        int diferenciaColumna = Math.abs(destino.getColumna() - origen.getColumna());

        return (diferenciaFila == 0 && diferenciaColumna > 0)
                || (diferenciaFila > 0 && diferenciaColumna == 0);
    }

    private String verificarCaminoLibre(casillas origen, casillas destino) {
        int filaOrigen = origen.getFila();
        int columnaOrigen = origen.getColumna();
        int filaDestino = destino.getFila();
        int columnaDestino = destino.getColumna();
        int direccionFila = 0;
        int direccionColumna = 0;

        if (filaDestino > filaOrigen) {
            direccionFila = 1;
        } else if (filaDestino < filaOrigen) {
            direccionFila = -1;
        }

        if (columnaDestino > columnaOrigen) {
            direccionColumna = 1;
        } else if (columnaDestino < columnaOrigen) {
            direccionColumna = -1;
        }

        // Verificacion de que casillas tiene disponibles como movimiento
        int filaActual = filaOrigen + direccionFila;
        int columnaActual = columnaOrigen + direccionColumna;

        while (filaActual != filaDestino || columnaActual != columnaDestino) {

            // Verificar si hay una ficha bloqueando el camino
            if (tablero[filaActual][columnaActual].tienePersonaje()) {
                return "El movimiento está bloqueado por otra ficha.";
            }

            // Verificar si es una casilla bloqueada del tablero
            if (esCasillaBloqueada(tablero[filaActual][columnaActual])) {
                return "El camino está bloqueado por una casilla prohibida.";
            }

            filaActual += direccionFila;
            columnaActual += direccionColumna;
        }

        //Si retorna null significa que el camino está libre
        return null;
    }

    private casillas[] obtenerCasillasAdyacentes(casillas casilla) {
        int fila = casilla.getFila();
        int columna = casilla.getColumna();
        // Para fichas de rangoto especial), obtener todas las casillas en líneas rectas 2 (Movimien
        if (casilla.tienePersonaje() && casilla.getPersonaje().getRango() == 2) {
            return getCasillasMovimientoEspecial(casilla);
        }

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

        casillas[] resultado = new casillas[contador];
        System.arraycopy(adyacentes, 0, resultado, 0, contador);
        return resultado;
    }

    private casillas[] getCasillasMovimientoEspecial(casillas casilla) {
        int fila = casilla.getFila();
        int columna = casilla.getColumna();
        casillas[] casillasTemp = new casillas[tablero.length * tablero[0].length];
        int contador = 0;
        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int i = 0; i < direcciones.length; i++) {
            int dFila = direcciones[i][0];
            int dColumna = direcciones[i][1];

            int filaActual = fila + dFila;
            int columnaActual = columna + dColumna;

            while (filaActual >= 0 && filaActual < tablero.length
                    && columnaActual >= 0 && columnaActual < tablero[0].length) {

                casillas casillaActual = tablero[filaActual][columnaActual];

         
                if (esCasillaBloqueada(casillaActual)) {
                    break;
                }

                // Si hay una ficha
                if (casillaActual.tienePersonaje()) {
                    // Si es ficha enemiga, se puede atacar
                    if (casillaActual.getPersonaje().isEsHeroe() != casilla.getPersonaje().isEsHeroe()) {
                        casillasTemp[contador] = casillaActual;
                        contador++;
                    }

                    break;
                }

                // Si la casilla es vacia, puede mover el personaje
                casillasTemp[contador] = casillaActual;
                contador++;
                filaActual += dFila;
                columnaActual += dColumna;
            }
        }
        casillas[] resultado = new casillas[contador];
        System.arraycopy(casillasTemp, 0, resultado, 0, contador);

        return resultado;
    }

    private void mostrarMovimientosDisponibles(casillas casilla) {
        if (!casilla.tienePersonaje() || esCasillaBloqueada(casilla)) {
            return;
        }

        casillas[] adyacentes = obtenerCasillasAdyacentes(casilla);

        for (casillas destino : adyacentes) {
            if (destino != null && esMovimientoValido(casilla, destino)) {
                destino.marcarCasillasDisponibles();
            }
        }
    }

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

    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
    }

    private void moverPersonaje(casillas origen, casillas destino) {
        Fichas personajeAMover = origen.getPersonaje();

        if (destino.tienePersonaje()) {
            Fichas personajeDefensor = destino.getPersonaje();
            resolverCombate(origen, destino, personajeAMover, personajeDefensor);
        } else {
            System.out.println("Moviendo " + personajeAMover.getNombrePersonaje()
                    + " de [" + origen.getFila() + "][" + origen.getColumna() + "] a ["
                    + destino.getFila() + "][" + destino.getColumna() + "]");
            destino.asignarPersonaje(personajeAMover);
            origen.quitarPersonaje();
        }
    }

    private void resolverCombate(casillas casillaAtacante, casillas casillaDefensor,
            Fichas atacante, Fichas defensor) {
        int rangoAtacante = atacante.getRango();
        int rangoDefensor = defensor.getRango();
        String mensaje;
       
        if (rangoAtacante == 1 && rangoDefensor == 10) {
            mensaje = atacante.getNombrePersonaje() + " Rango(" + atacante.getRango() + ") vs " + defensor.getNombrePersonaje() + " Rango(" + defensor.getRango() + ")\n"
                    + "Vencedor: " + atacante.getNombrePersonaje() + " Rango(" + atacante.getRango() + ")";
            JOptionPane.showMessageDialog(null, mensaje, "COMBATE", JOptionPane.INFORMATION_MESSAGE);
            casillaDefensor.asignarPersonaje(atacante);
            casillaAtacante.quitarPersonaje();
            getEliminados(defensor);
            Tablero.restarNumFichas(1);
        } else if (rangoAtacante == 10 && rangoDefensor == 1) {
            mensaje = atacante.getNombrePersonaje() + " Rango(" + atacante.getRango() + ") vs " + defensor.getNombrePersonaje() + " Rango(" + defensor.getRango() + ")\n"
                    + "Vencedor: " + defensor.getNombrePersonaje() + " Rango(" + defensor.getRango() + ")";
            JOptionPane.showMessageDialog(null, mensaje, "COMBATE", JOptionPane.INFORMATION_MESSAGE);
            casillaAtacante.quitarPersonaje();
            getEliminados(atacante);
            Tablero.restarNumFichas(1);
        } else if (rangoAtacante == 3 && rangoDefensor == 0) {
            mensaje = atacante.getNombrePersonaje() + " Rango(" + atacante.getRango() + ") vs " + defensor.getNombrePersonaje() + " Rango(" + defensor.getRango() + ")\n"
                    + "Vencedor: " + atacante.getNombrePersonaje() + " Rango(" + atacante.getRango() + ")";
            JOptionPane.showMessageDialog(null, mensaje, "COMBATE", JOptionPane.INFORMATION_MESSAGE);
            casillaDefensor.asignarPersonaje(atacante);
            casillaAtacante.quitarPersonaje();
            getEliminados(defensor);
        } else if (rangoAtacante != 3 && rangoDefensor == 0) {
            mensaje = atacante.getNombrePersonaje() + " Rango(" + atacante.getRango() + ") vs " + defensor.getNombrePersonaje() + "\n"
                    + "Vencedor: " + defensor.getNombrePersonaje();
            JOptionPane.showMessageDialog(null, mensaje, "COMBATE", JOptionPane.INFORMATION_MESSAGE);
            casillaAtacante.quitarPersonaje();
            getEliminados(atacante);
            Tablero.restarNumFichas(1);
        } else if (rangoAtacante > rangoDefensor) {
             if (rangoDefensor == -1) {
                 String jugadorGanador = getGanador(atacante);
                 String jugadorPerdedor = getPerdedor(jugadorGanador);
                if (atacante.isEsHeroe()) {
                    JOptionPane.showMessageDialog(null, jugadorGanador+" usando los HEROES ha SALVADO LA TIERRA! Venciendo a "+jugadorPerdedor);
                    if(seleccionDeModo.modoClasico){
                        victoriasHeroe++;
                    ganador = 1;
                    }
                } else {
               JOptionPane.showMessageDialog(null, jugadorGanador+" usando los VILLANOS ha CAPTURADO LA TIERRA! Venciendo a "+jugadorPerdedor);
               if(seleccionDeModo.modoClasico){
                   victoriasVillanos++;
               ganador = 2;}
                }
               juego.dispose();
               menuPrincipal1 menu = new menuPrincipal1();
               menu.setVisible(true);
                
            }
            mensaje = atacante.getNombrePersonaje() + " Rango(" + atacante.getRango() + ") vs " + defensor.getNombrePersonaje() + " Rango(" + defensor.getRango() + ")\n"
                    + "Vencedor: " + atacante.getNombrePersonaje() + " Rango(" + atacante.getRango() + ")";
            JOptionPane.showMessageDialog(null, mensaje, "COMBATE", JOptionPane.INFORMATION_MESSAGE);
            casillaDefensor.asignarPersonaje(atacante);
            casillaAtacante.quitarPersonaje();
            getEliminados(defensor);
            Tablero.restarNumFichas(1);
        } else if (rangoAtacante < rangoDefensor) {
            mensaje = atacante.getNombrePersonaje() + " Rango(" + atacante.getRango() + ") vs " + defensor.getNombrePersonaje() + " Rango(" + defensor.getRango() + ")\n"
                    + "Vencedor: " + defensor.getNombrePersonaje() + " Rango(" + defensor.getRango() + ")";
            JOptionPane.showMessageDialog(null, mensaje, "COMBATE", JOptionPane.INFORMATION_MESSAGE);
            casillaAtacante.quitarPersonaje();
            getEliminados(atacante);
            Tablero.restarNumFichas(1);
        } else if (rangoAtacante == rangoDefensor) {
            mensaje = atacante.getNombrePersonaje() + " Rango(" + atacante.getRango() + ") vs " + defensor.getNombrePersonaje() + " Rango(" + defensor.getRango() + ")\n"
                    + "EMPATE - AMBOS SON ELIMINADOS";
            JOptionPane.showMessageDialog(null, mensaje, "COMBATE", JOptionPane.INFORMATION_MESSAGE);
            casillaAtacante.quitarPersonaje();
            casillaDefensor.quitarPersonaje();
            getEliminados(atacante);
            getEliminados(defensor);
            Tablero.restarNumFichas(2);
        }
       
    }
    
    public static int getNumVictorias(int bando){
        switch(bando){
            case 0: // Heroes
                return victoriasHeroe;
               
            case 1: // villanos
                return victoriasVillanos;
                
        }
        return 0;
    }
    
    public static void sumVictorias(int bando){
       switch(bando){
            case 0: // Heroes
                victoriasHeroe++;
                break;
            case 1: // villanos
                victoriasVillanos++;
                break;      
        }
    }
    
    
    private String getGanador(Fichas atacante){
        if(atacante.isEsHeroe() && Tablero.bando){
            return juego.getJugador1();
        }else if(!atacante.isEsHeroe() && !Tablero.bando){
            return juego.getJugador2();
        }else if(atacante.isEsHeroe() && !Tablero.bando){
            return juego.getJugador2();
        }else{
            return juego.getJugador1();
        }
    }
    private String getPerdedor(String jugadorGanador){
        if(jugadorGanador.equals(juego.getJugador1())){
            return juego.getJugador2();
            
        }else{
            return juego.getJugador1();
        }
    }
    
    public void getEliminados(Fichas personajeEliminado){
        if(juego!=null){
           juego.personajesEliminados(personajeEliminado);
        }
    }
    public void setGanador(int num) {
        ganador = num;
    }

    public int getGanador() {
        return ganador;
    }

    public void cancelarSeleccion() {
        casillas.limpiarCasillas(tablero);
        primeraCasillaSelecc = null;
        esperarSegundoClic = false;
    }

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
