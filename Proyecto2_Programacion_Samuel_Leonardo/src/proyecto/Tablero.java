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
    private Image imagen;
    casillas casilla[][];
    JButton botones[][];
    private int fila;
    private int columna;
    public static boolean bando;
    public boolean casillasProhibidasHeroes[][];
    public boolean casillasProhibidasVillanos[][];
    private jugabilidadTablero logicaMovimiento;
    // Arrays de personajes
    private Fichas[] personajesHeroes;
    private Fichas[] personajesVillanos;

    public Tablero(int fila, int columna, String ruta) {
        this.imagen = new ImageIcon(getClass().getResource(ruta)).getImage();
        this.fila = fila;
        this.columna = columna;
        
        // Configurar el layout como GridLayout para organizar los botones en cuadrícula
        this.setLayout(new GridLayout(fila, columna)); 
        
        // Inicializar el array de casillas
        casilla = new casillas[fila][columna];
        
        // Cargar arrays de personajes
        cargarPersonajes();
        
        inicializarCasillas();
        
        // Asignar personajes al tablero
        asignarPersonajesAlTablero();
        logicaMovimiento = new jugabilidadTablero(casilla);
    }
    
    public void setBando(boolean bando){
        this.bando = bando;
    }
    public void cambiarBando(){
        bando = !bando;
    
    }
    
    
    private void cargarPersonajes() {
        personajesHeroes = casillas.personajesHeroes();
        personajesVillanos = casillas.personajesVillanos();
    }

    private void inicializarCasillas(){
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
        
        // Mezclar arrays usando algoritmo de Fisher-Yates
        mezclarArray(heroesTemp);
        mezclarArray(villanosTemp);
        
        // Asignar héroes a las primeras 4 filas (0-3)
        int indiceHeroe = 0;
        for (int i = 6; i < fila && indiceHeroe < heroesTemp.length; i++) {
            for (int j = 0; j < columna && indiceHeroe < heroesTemp.length; j++) {
                casilla[i][j].asignarPersonaje(heroesTemp[indiceHeroe]);
                indiceHeroe++;
            }
        }
        
        // Asignar villanos a las últimas 4 filas (6-9)
        int indiceVillano = 0;
        for (int i = 0; i < 4 && indiceVillano < villanosTemp.length; i++) {
            for (int j = 0; j < columna && indiceVillano < villanosTemp.length; j++) {
                casilla[i][j].asignarPersonaje(villanosTemp[indiceVillano]);
                indiceVillano++;
            }
        }
        
        // Filas 4 y 5 quedan como zona neutral (sin personajes)
        System.out.println("Personajes asignados al tablero:");
        System.out.println("Héroes en filas 0-3, Villanos en filas 6-9");
        System.out.println("Filas 4-5: Zona neutral");
    }
    
    // Método para mezclar un array usando algoritmo de Fisher-Yates
    private void mezclarArray(Fichas[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            
            // Intercambiar elementos
            Fichas temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
    
    // Método para asignar personajes específicos (para testing)
    public void asignarPersonajeEspecifico(int fila, int columna, Fichas personaje) {
        if (fila >= 0 && fila < this.fila && columna >= 0 && columna < this.columna) {
            casilla[fila][columna].asignarPersonaje(personaje);
        }
    }
    
    // Método para revelar todos los personajes (para testing)
    public void revelarTodosLosPersonajes() {
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                if (casilla[i][j].tienePersonaje()) {
                    casilla[i][j].revelarPersonaje();
                }
            }
        }
    }
    
    // Método para revelar solo los héroes
    public void revelarHeroes() {
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                if (casilla[i][j].tienePersonaje() && 
                    casilla[i][j].getPersonaje().isEsHeroe()) {
                    casilla[i][j].revelarPersonaje();
                }
            }
        }
    }
    
    // Método para revelar solo los villanos
    public void revelarVillanos() {
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                if (casilla[i][j].tienePersonaje() && 
                    !casilla[i][j].getPersonaje().isEsHeroe()) {
                    casilla[i][j].revelarPersonaje();
                }
            }
        }
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
    
    public void seleccionCasilla(int fila, int columna, JButton boton) {
        System.out.println("Casilla Seleccionada: [" + fila + "][" + columna + "]");
    }
    
   

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
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
}