/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 *
 * @author hnleo
 */
public class Tablero extends JPanel {

    Random random = new Random();
    Fichas ficha;
    int numRandom = 0;
    private static int numFichasJugables;
    private Image imagen;
    casillas casilla[][];
    JButton botones[][];
    private int fila;
    private int columna;
    public static boolean bando;
    public boolean casillasProhibidasHeroes[][];
    public boolean casillasProhibidasVillanos[][];
    private jugabilidadTablero logicaMovimiento;
    private TableroPantallaStratego juego;
    // Arrays de personajes
    private Fichas[] personajesHeroes;
    private Fichas[] personajesVillanos;

    public Tablero(int fila, int columna, String ruta, TableroPantallaStratego juego) {
        this.imagen = new ImageIcon(getClass().getResource(ruta)).getImage();
        this.fila = fila;
        this.columna = columna;
        this.juego = juego;
        numFichasJugables = 0;

        // Configurar el layout como GridLayout para organizar los botones en cuadrícula
        this.setLayout(new GridLayout(fila, columna));

        // Inicializar el array de casillas
        casilla = new casillas[fila][columna];

        // Cargar arrays de personajes
        cargarPersonajes();

        inicializarCasillas();

        // Asignar personajes al tablero
        asignarPersonajesAlTablero();

        logicaMovimiento = new jugabilidadTablero(casilla, this, juego);
        logicaMovimiento.setGanador(-1);

    }

    public jugabilidadTablero getJugabilidad() {
        return logicaMovimiento;
    }

    public void setBando(boolean bando) {
        this.bando = bando;
    }

    public void cambiarBando() {
        bando = !bando;
    }

    private void cargarPersonajes() {
        personajesHeroes = casillas.personajesHeroes();
        personajesVillanos = casillas.personajesVillanos();
    }

    private void inicializarCasillas() {
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                casillas c = new casillas(i, j);
                casilla[i][j] = c;
                this.add(c);
            }
        }
    }

    private void asignarPersonajesAlTablero() {
        // Crear arrays temporales para mezclar
        Fichas[] heroesTemp = new Fichas[personajesHeroes.length];
        Fichas[] villanosTemp = new Fichas[personajesVillanos.length];

        // Copiar personajes a arrays temporales
        for (int i = 0; i < personajesHeroes.length; i++) {
            heroesTemp[i] = personajesHeroes[i];
        }
        for (int i = 0; i < personajesVillanos.length; i++) {
            villanosTemp[i] = personajesVillanos[i];
        }

        formacionCondicionada(heroesTemp, 6, 9, true);
        formacionCondicionada(villanosTemp, 0, 3, false);

    }

    private void mezclarArray(int posiciones[][], int cant) {
        for (int i = cant - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            int fila = posiciones[i][0];
            int col = posiciones[i][1];
            posiciones[i][0] = posiciones[j][0];
            posiciones[i][1] = posiciones[j][1];
            posiciones[j][0] = fila;
            posiciones[j][1] = col;
        }
    }

    private void formacionCondicionada(Fichas personajes[], int filaIn, int filaFin, boolean isHeroe) {
        Fichas tierra = null;
        Fichas bombs[] = new Fichas[6];
        Fichas piezasRango2[] = new Fichas[8];
        Fichas restoPersonajes[] = new Fichas[26];
        int contBombs = 0, contRango2 = 0, contRestantes = 0;
        for (Fichas fichaa : personajes) {
            if (fichaa.getRango() == -1) { // Tierra
                tierra = fichaa;
            } else if (fichaa.getRango() == 0) { // Bombas
                bombs[contBombs++] = fichaa;
            } else if (fichaa.getRango() == 2) { // Rango 2
                piezasRango2[contRango2++] = fichaa;
            } else if (fichaa.getRango() > 0) { // Otros personajes jugables
                restoPersonajes[contRestantes++] = fichaa;
            }
        }
        int filaUlt = isHeroe ? filaFin : filaIn;
        int colTierra = 1 + random.nextInt(columna - 2);
        casilla[filaUlt][colTierra].asignarPersonaje(tierra);

        //Despues de haber colocado la tierra, con este metodo se colocarán las bombas
        contBombs = bombasAlrededorDeTierra(filaUlt, colTierra, bombs, contBombs);
        bombasRestantes(bombs, contBombs, filaIn, filaFin, isHeroe);

        colocarPiezasRango2(piezasRango2, contRango2, filaIn, filaFin, isHeroe);
        piezasRestantesC(restoPersonajes, contRestantes, filaIn, filaFin);

        //Conteo de fichas Jugables
        for (int i = filaIn; i <= filaFin; i++) {
            for (int j = 0; j < columna; j++) {
                if (casilla[i][j].tienePersonaje() && casilla[i][j].getPersonaje().getRango() > 0) {
                    numFichasJugables++;
                }
            }
        }
    }

    private int bombasAlrededorDeTierra(int filaT, int colT, Fichas bombs[], int contBombs) {
        int dir[][] = {{-1, 1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        int indexBombs = 0;
        for (int i = 0; i < dir.length && indexBombs < contBombs; i++) {
            int nFila = filaT + dir[i][0];
            int nCol = colT + dir[i][1];

            if (nFila >= 0 && nFila < fila && nCol >= 0 && nCol < columna && !casilla[nFila][nCol].tienePersonaje()) {
                casilla[nFila][nCol].asignarPersonaje(bombs[indexBombs]);
                indexBombs++;
            }
        }
        for (int i = 0; i < contBombs - indexBombs; i++) {
            bombs[i] = bombs[i + indexBombs];

        }
        return contBombs - indexBombs;
    }

    private void bombasRestantes(Fichas bombs[], int contBombs, int filaIn, int filaFin, boolean isHeroe) {
        if (contBombs == 0) {
            return;
        }

        int fila1, fila2;
        if (isHeroe) {
            fila1 = filaFin - 1;
            fila2 = filaFin;
        } else { //Para los villanos el inicio del arreglo en el tablero
            fila1 = filaIn;
            fila2 = filaIn + 1;
        }

        int posicionesDisponibles[][] = new int[columna * 2][2];
        int contPosiciones = 0;

        //Recorrido con validación de que exista un espacio para colocar personaje
        for (int filas : new int[]{fila1, fila2}) {
            for (int col = 0; col < columna; col++) {
                if (!casilla[filas][col].tienePersonaje()) {
                    posicionesDisponibles[contPosiciones][0] = filas;
                    posicionesDisponibles[contPosiciones][1] = col;
                    contPosiciones++;
                }
            }
        }

        //Mezclado en el array (Tablero)
        mezclarArray(posicionesDisponibles, contPosiciones);

        // Colocar las bombas restantes
        int bombsColocadas = 0;
        for (int i = 0; i < contPosiciones && bombsColocadas < contBombs; i++) {
            int filaPos = posicionesDisponibles[i][0];
            int colPos = posicionesDisponibles[i][1];
            casilla[filaPos][colPos].asignarPersonaje(bombs[bombsColocadas]);
            bombsColocadas++;
        }
    }

    private void colocarPiezasRango2(Fichas rango2[], int contRango2, int filaIn, int filaFin, boolean isHeroe) {
        if (contRango2 == 0) {
            return;
        }

        int fila1, fila2;
        if (isHeroe) {
            fila1 = filaIn;
            fila2 = filaIn + 1;
        } else { //Para los villanos el inicio del arreglo en el tablero
            fila1 = filaFin - 1;
            fila2 = filaFin;
        }

        int posicionesDisponibles[][] = new int[columna * 2][2];
        int contPosiciones = 0;

        //Recorrido con validación de que exista un espacio para colocar personaje
        for (int filas : new int[]{fila1, fila2}) {
            for (int col = 0; col < columna; col++) {
                if (!casilla[filas][col].tienePersonaje()) {
                    posicionesDisponibles[contPosiciones][0] = filas;
                    posicionesDisponibles[contPosiciones][1] = col;
                    contPosiciones++;
                }
            }
        }

        //Mezclado en el array (Tablero)
        mezclarArray(posicionesDisponibles, contPosiciones);

        // Colocar las piezas rango 2
        int rango2Colocadas = 0;
        for (int i = 0; i < contPosiciones && rango2Colocadas < contRango2; i++) {
            int filaPos = posicionesDisponibles[i][0];
            int colPos = posicionesDisponibles[i][1];
            casilla[filaPos][colPos].asignarPersonaje(rango2[rango2Colocadas]);
            rango2Colocadas++;
        }
    }

    private void piezasRestantesC(Fichas piezasRestantes[], int contRestante, int filaIn, int filaFin) {
        if (contRestante == 0) {
            return;
        }

        int posicionesDisponibles[][] = new int[columna * 4][2];
        int contPosiciones = 0;

        //Recorrido con validación de que exista un espacio para colocar personaje
        for (int filas = filaIn; filas <= filaFin; filas++) {
            for (int col = 0; col < columna; col++) {
                if (!casilla[filas][col].tienePersonaje()) {
                    posicionesDisponibles[contPosiciones][0] = filas;
                    posicionesDisponibles[contPosiciones][1] = col;
                    contPosiciones++;
                }
            }
        }

        //Mezclado en el array (Tablero)
        mezclarArray(posicionesDisponibles, contPosiciones);
        // Colocar las piezas restantes
        int piezasColocadas = 0;
        for (int i = 0; i < contPosiciones && piezasColocadas < contRestante; i++) {
            int filaPos = posicionesDisponibles[i][0];
            int colPos = posicionesDisponibles[i][1];
            casilla[filaPos][colPos].asignarPersonaje(piezasRestantes[piezasColocadas]);
            piezasColocadas++;
        }
    }

    private void mezclarPosiciones(Fichas array[], int cant) {
        for (int i = cant - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Fichas arregloTemporal = array[i];
            array[i] = array[j];
            array[j] = arregloTemporal;

        }
    }

    public static int getNumFichas() {
        return numFichasJugables;
    }

    public static void restarNumFichas(int num) {
        numFichasJugables -= num;
    }


    public void asignarPersonajeEspecifico(int fila, int columna, Fichas personaje) {
        if (fila >= 0 && fila < this.fila && columna >= 0 && columna < this.columna) {
            casilla[fila][columna].asignarPersonaje(personaje);
        }
    }

    public void revelarFichasBandoActual() {
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                if (casilla[i][j].tienePersonaje()) {
                    Fichas personaje = casilla[i][j].getPersonaje();
                    // Si es el turno de los héroes y la ficha es héroe, o viceversa
                    if (seleccionDeModo.modoClasico) {
                        if (personaje.isEsHeroe() == Tablero.bando) {
                            casilla[i][j].revelarPersonaje();
                        } else {
                            casilla[i][j].ocultarPersonaje();
                        }
                    }
                }
            }
        }

    }

    public void actualizarVisibilidadPorTurno() {
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                if (casilla[i][j].tienePersonaje()) {
                    Fichas personaje = casilla[i][j].getPersonaje();

                    // Revelar fichas del bando actual, ocultar las del contrario
                    if (seleccionDeModo.modoClasico) {
                        if (personaje.isEsHeroe() == Tablero.bando) {
                            casilla[i][j].revelarPersonaje();
                        } else {
                            casilla[i][j].ocultarPersonaje();
                        }
                    }
                }
            }
        }

        // Repintar el tablero para que se vean los cambios
        this.repaint();
        this.revalidate();
    }

    // Método para obtener información del tablero
    public void mostrarEstadoTablero() {
        System.out.println("\n=== ESTADO DEL TABLERO ===");
        int heroesCount = 0, villanosCount = 0, casillasVacias = 0;

        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                if (casilla[i][j].tienePersonaje()) {
                    if (casilla[i][j].getPersonaje().isEsHeroe()) {
                        heroesCount++;
                    } else {
                        villanosCount++;
                    }
                } else {
                    casillasVacias++;
                }
            }
        }

        System.out.println("Héroes: " + heroesCount);
        System.out.println("Villanos: " + villanosCount);
        System.out.println("Casillas vacías: " + casillasVacias);
        System.out.println("========================\n");
    }

    public boolean isTurno(boolean esPersonajeHeroe) {
        return (bando && esPersonajeHeroe) || (!bando && !esPersonajeHeroe);
    }

    public void seleccionCasilla(int fila, int columna, JButton boton) {
        casillas casillaSelec = getCasilla(fila, columna);
        System.out.println("Casilla Seleccionada: [" + fila + "][" + columna + "]");
        if (casillaSelec != null & casillaSelec.tienePersonaje()) {
            Fichas personaje = casillaSelec.getPersonaje();
            boolean sHeroe = personaje.isEsHeroe();
            if (!isTurno(sHeroe)) {
                JOptionPane.showMessageDialog(null, "No es tu turno.", "Turno incorrecto", JOptionPane.WARNING_MESSAGE);
            }

            System.out.println("Personaje válido " + personaje.getNombrePersonaje());
        }
    }

    public void turnoDespues() {
        cambiarBando();
        if (juego != null) {
            juego.cambioDeTurno();

        }
    }

    public String getJugadorActual() {
        if (juego != null) {
            return juego.getTurnoActual();
        } else {
            return "Jugador Desconocido";
        }
    }

    // Getters
    public casillas[][] getCasillas() {
        return casilla;
    }

    public casillas getCasilla(int fila, int columna) {
        if (fila >= 0 && fila < this.fila && columna >= 0 && columna < this.columna) {
            return casilla[fila][columna];
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
