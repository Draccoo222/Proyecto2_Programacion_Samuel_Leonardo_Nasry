/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author hnleo
 */
public class casillas extends JButton {

    private int fila;
    private int columna;
    private Fichas personaje; // Agregar referencia al personaje
    private boolean tienePersonaje; // Para saber si la casilla tiene personaje
    private Image imagenFondoCompleta;
    private int filaTablero, columnaTablero, totalFilas, totalColumnas;
    private boolean personajeRevelado = false;
    private boolean isMouseClicked;

    public casillas(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.personaje = null;
        this.tienePersonaje = false;

        // Configurar apariencia del botón
        configurarApariencia();

        // Agregar listener para clics
        agregarListenerClick();
        this.setOpaque(false);
        this.setContentAreaFilled(false);
    }

    private void configurarApariencia() {
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(true);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 0));
        this.setFocusPainted(false);
        this.setPreferredSize(new Dimension(80, 80));
        this.setMinimumSize(new Dimension(60, 60));
        isMouseClicked = false;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isMouseClicked) {
                    if (tienePersonaje) {
                        setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                        isMouseClicked = true;
                    }
                } else {
                    if (tienePersonaje) {
                        setBorder(BorderFactory.createLineBorder(Color.GRAY, 0));
                        isMouseClicked = false;
                    }

                }
            }

        });
    }

    private void agregarListenerClick() {
        this.addActionListener(e -> {
            if (tienePersonaje && personaje != null) {
                mostrarAtributosPersonaje();
            } else {
                System.out.println("Casilla [" + fila + "][" + columna + "] - Sin personaje");
            }
        });
    }

    // Método para asignar un personaje a la casilla
    public void asignarPersonaje(Fichas personaje) {
        this.personaje = personaje;
        this.tienePersonaje = true;

        // Mostrar la imagen del personaje en el botón
        if (personaje != null) {
            // Si el personaje es visible, mostrar su imagen real
            if (Tablero.bando && personaje.isEsHeroe()) {
                this.setIcon(personaje.getIconoPersonaje());

            }
            if (Tablero.bando && !personaje.isEsHeroe()) {
                ocultarPersonaje();
            }
            if (!Tablero.bando && !personaje.isEsHeroe()) {
                this.setIcon(personaje.getIconoPersonaje());
            }
            if (!Tablero.bando && personaje.isEsHeroe()) {
                ocultarPersonaje();
            }
        }
    }

    // Método para quitar el personaje de la casilla
    public void quitarPersonaje() {
        this.personaje = null;
        this.tienePersonaje = false;
        this.setIcon(null);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    }

    // Método para revelar el personaje (mostrar su imagen real)
    public void revelarPersonaje() {
        if (tienePersonaje && personaje != null) {
            personajeRevelado = true;
            personaje.revelarPersonaje();
            this.setIcon(personaje.getIconoPersonaje());
        }
    }

    public void ocultarPersonaje() {
        if (tienePersonaje()) {
            personajeRevelado = false;
            if (personaje.isEsHeroe()) {
                this.setIcon(personaje.getImageOculta());

            } else {
                this.setIcon(personaje.getImageOculta());
            }
            this.repaint();
        }
    }

    public boolean isPersonajeRevelado() {
        return personajeRevelado;
    }

    // Método para mostrar atributos en consola
    private void mostrarAtributosPersonaje() {
        System.out.println("=== INFORMACIÓN DEL PERSONAJE ===");
        System.out.println("Posición: [" + fila + "][" + columna + "]");
        System.out.println("Nombre: " + personaje.getNombrePersonaje());
        System.out.println("Equipo: " + (personaje.isEsHeroe() ? "HÉROES" : "VILLANOS"));
        System.out.println("Rango: " + personaje.getRango());
        System.out.println("Puede mover: " + (personaje.isPuedeMover() ? "SÍ" : "NO"));
        System.out.println("Visible: " + (personaje.isVisible() ? "SÍ" : "NO"));
        System.out.println("Ruta imagen: " + personaje.getRuta());
        System.out.println("================================");
    }

    // Getters
    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public Fichas getPersonaje() {
        return personaje;
    }

    public boolean tienePersonaje() {
        return tienePersonaje;
    }

    // Método para verificar si puede mover a otra casilla
//    public boolean puedeMoverA(casillas otraCasilla) {
//        if (!this.tienePersonaje || !this.personaje.isPuedeMover()) {
//            return false;
//        }
//
//        // Verificar si la casilla destino está vacía o tiene un enemigo
//        if (!otraCasilla.tienePersonaje()) {
//            return true; // Casilla vacía
//        }
//
//        // Si tiene personaje, verificar si es enemigo
//        return this.personaje.isEsHeroe() != otraCasilla.getPersonaje().isEsHeroe();
//    }
    // Métodos estáticos para crear arrays de personajes (mantener los existentes)
    public static Fichas[] personajesHeroes() {
        Fichas personajes[] = new Fichas[40];
        int id = 0;

        //Cartas Especiales | Nova Blast y Planeta Tierra
        personajes[id++] = new Fichas("Planet-Earth", true, -1, false, "/images/heroes/Planet-Earth.png");
        for (int i = 0; i < 6; i++) {
            personajes[id++] = new Fichas("Nova Blast", true, 0, false, "/images/heroes/NovaBlast.png");
        }

        //Conjunto de Personajes Divididos en Rangos 10-1
        //Rango 10
        personajes[id++] = new Fichas("Mr. Fantastic", true, 10, true, "/images/heroes/Mr.Fantastic.png");
        //Rango 9
        personajes[id++] = new Fichas("Captain America", true, 9, true, "/images/heroes/CaptainAmerica.png");
        //Rango 8
        personajes[id++] = new Fichas("Professor X", true, 8, true, "/images/heroes/ProfessorX.png");
        personajes[id++] = new Fichas("Nick Fury", true, 8, true, "/images/heroes/NickFury.png");
        //Rango 7
        personajes[id++] = new Fichas("Spider-Man", true, 7, true, "/images/heroes/Spider-Man.png");
        personajes[id++] = new Fichas("Wolverine", true, 7, true, "/images/heroes/Wolverine.png");
        personajes[id++] = new Fichas("Namor", true, 7, true, "/images/heroes/Namor.png");
        //Rango 6
        personajes[id++] = new Fichas("Daredevil", true, 6, true, "/images/heroes/Daredevil.png");
        personajes[id++] = new Fichas("Silver Surfer", true, 6, true, "/images/heroes/SilverSurfer.png");
        personajes[id++] = new Fichas("Hulk", true, 6, true, "/images/heroes/Hulk.png");
        personajes[id++] = new Fichas("Iron Man", true, 6, true, "/images/heroes/IronMan.png");
        //Rango 5
        personajes[id++] = new Fichas("Thor", true, 5, true, "/images/heroes/Thor.png");
        personajes[id++] = new Fichas("Human Torch", true, 5, true, "/images/heroes/HumanTorch.png");
        personajes[id++] = new Fichas("Cyclops", true, 5, true, "/images/heroes/Cyclops.png");
        personajes[id++] = new Fichas("Invisible Woman", true, 5, true, "/images/heroes/InvisibleWoman.png");
        //Rango 4
        personajes[id++] = new Fichas("Ghost Rider", true, 4, true, "/images/heroes/GhostRider.png");
        personajes[id++] = new Fichas("Punisher", true, 4, true, "/images/heroes/Punisher.png");
        personajes[id++] = new Fichas("Blade", true, 4, true, "/images/heroes/Blade.png");
        personajes[id++] = new Fichas("Thing", true, 4, true, "/images/heroes/Thing.png");
        //Rango 3
        personajes[id++] = new Fichas("Emma Frost", true, 3, true, "/images/heroes/EmmaFrost.png");
        personajes[id++] = new Fichas("She-Hulk", true, 3, true, "/images/heroes/She-Hulk.png");
        personajes[id++] = new Fichas("Giant Man", true, 3, true, "/images/heroes/GiantMan.png");
        personajes[id++] = new Fichas("Beast", true, 3, true, "/images/heroes/Beast.png");
        personajes[id++] = new Fichas("Colossus", true, 3, true, "/images/heroes/Colossus.png");
        //Rango 2
        personajes[id++] = new Fichas("Gambit", true, 2, true, "/images/heroes/Gambit.png");
        personajes[id++] = new Fichas("Spider-Girl", true, 2, true, "/images/heroes/Spider-Girl.png");
        personajes[id++] = new Fichas("Ice Man", true, 2, true, "/images/heroes/IceMan.png");
        personajes[id++] = new Fichas("Storm", true, 2, true, "/images/heroes/Storm.png");
        personajes[id++] = new Fichas("Phoenix", true, 2, true, "/images/heroes/Phoenix.png");
        personajes[id++] = new Fichas("Dr. Strange", true, 2, true, "/images/heroes/Dr.Strange.png");
        personajes[id++] = new Fichas("Elektra", true, 2, true, "/images/heroes/Elektra.png");
        personajes[id++] = new Fichas("Nightcrawler", true, 2, true, "/images/heroes/Nightcrawler.png");
        //Rango 1
        personajes[id++] = new Fichas("Black Widow", true, 1, true, "/images/heroes/BlackWidow.png");

        return personajes;
    }

    public static Fichas[] personajesVillanos() {
        Fichas personajes[] = new Fichas[40];
        int id = 0;

        //Cartas Especiales | Pumpkin Bomb y Planeta Tierra Capturada
        personajes[id++] = new Fichas("Planet-Earth", false, -1, false, "/images/villanos/Planet-Earth.png");
        for (int i = 0; i < 6; i++) {
            personajes[id++] = new Fichas("Pumpkin Bomb", false, 0, false, "/images/villanos/PumpkinBomb.png");
        }

        //Conjunto de Personajes Divididos en Rangos 10-1
        //Rango 10
        personajes[id++] = new Fichas("Dr. Doom", false, 10, true, "/images/villanos/Dr.Doom.png");
        //Rango 9
        personajes[id++] = new Fichas("Galactus", false, 9, true, "/images/villanos/Galactus.png");
        //Rango 8
        personajes[id++] = new Fichas("Kingpin", false, 8, true, "/images/villanos/Kingpin.png");
        personajes[id++] = new Fichas("Magneto", false, 8, true, "/images/villanos/Magneto.png");
        //Rango 7
        personajes[id++] = new Fichas("Apocalypse", false, 7, true, "/images/villanos/Apocalypse.png");
        personajes[id++] = new Fichas("Green Goblin", false, 7, true, "/images/villanos/GreenGoblin.png");
        personajes[id++] = new Fichas("Venom", false, 7, true, "/images/villanos/Venom.png");
        //Rango 6
        personajes[id++] = new Fichas("Bullseye", false, 6, true, "/images/villanos/Bullseye.png");
        personajes[id++] = new Fichas("Omega Red", false, 6, true, "/images/villanos/OmegaRed.png");
        personajes[id++] = new Fichas("Onslaught", false, 6, true, "/images/villanos/Onslaught.png");
        personajes[id++] = new Fichas("Red Skull", false, 6, true, "/images/villanos/RedSkull.png");
        //Rango 5
        personajes[id++] = new Fichas("Mystique", false, 5, true, "/images/villanos/Mystique.png");
        personajes[id++] = new Fichas("Mysterio", false, 5, true, "/images/villanos/Mysterio.png");
        personajes[id++] = new Fichas("Dr. Octopus", false, 5, true, "/images/villanos/Dr.Octopus.png");
        personajes[id++] = new Fichas("Deadpool", false, 5, true, "/images/villanos/Deadpool.png");
        //Rango 4
        personajes[id++] = new Fichas("Abomination", false, 4, true, "/images/villanos/Abomination.png");
        personajes[id++] = new Fichas("Thanos", false, 4, true, "/images/villanos/Thanos.png");
        personajes[id++] = new Fichas("Black Cat", false, 4, true, "/images/villanos/BlackCat.png");
        personajes[id++] = new Fichas("Sabretooth", false, 4, true, "/images/villanos/Sabretooth.png");
        //Rango 3
        personajes[id++] = new Fichas("Juggernaut", false, 3, true, "/images/villanos/Juggernaut.png");
        personajes[id++] = new Fichas("Rhino", false, 3, true, "/images/villanos/Rhino.png");
        personajes[id++] = new Fichas("Carnage", false, 3, true, "/images/villanos/Carnage.png");
        personajes[id++] = new Fichas("Mole Man", false, 3, true, "/images/villanos/MoleMan.png");
        personajes[id++] = new Fichas("Lizard", false, 3, true, "/images/villanos/Lizard.png");
        //Rango 2
        personajes[id++] = new Fichas("Mr. Sinister", false, 2, true, "/images/villanos/Mr.Sinister.png");
        personajes[id++] = new Fichas("Sentinel 1", false, 2, true, "/images/villanos/Sentinel1.png");
        personajes[id++] = new Fichas("Ultron", false, 2, true, "/images/villanos/Ultron.png");
        personajes[id++] = new Fichas("Sandman", false, 2, true, "/images/villanos/Sandman.png");
        personajes[id++] = new Fichas("Leader", false, 2, true, "/images/villanos/Leader.png");
        personajes[id++] = new Fichas("Viper", false, 2, true, "/images/villanos/Viper.png");
        personajes[id++] = new Fichas("Sentinel 2", false, 2, true, "/images/villanos/Sentinel2.png");
        personajes[id++] = new Fichas("Electro", false, 2, true, "/images/villanos/Electro.png");
        //Rango 1
        personajes[id++] = new Fichas("Black Widow", false, 1, true, "/images/villanos/BlackWidow.png");

        return personajes;
    }

//    public void setImagenFondoTablero(Image imagenCompleta, int fila, int col, int totalF, int totalC) {
//        this.imagenFondoCompleta = imagenCompleta;
//        this.filaTablero = fila;
//        this.columnaTablero = col;
//        this.totalFilas = totalF;
//        this.totalColumnas = totalC;
//        repaint();
//    }
//    @Override
//    protected void paintComponent(Graphics g) {
//        if (imagenFondoCompleta != null) {
//            // Calcular qué porción de la imagen corresponde a esta casilla
//            int anchoImagen = imagenFondoCompleta.getWidth(this);
//            int altoImagen = imagenFondoCompleta.getHeight(this);
//
//            if (anchoImagen > 0 && altoImagen > 0) {
//                // Calcular el tamaño de cada "pedazo" de imagen
//                int anchoPorcion = anchoImagen / totalColumnas;
//                int altoPorcion = altoImagen / totalFilas;
//
//                // Calcular las coordenadas de origen en la imagen original
//                int xOrigen = columnaTablero * anchoPorcion;
//                int yOrigen = filaTablero * altoPorcion;
//
//                // Dibujar solo la porción que corresponde a esta casilla
//                g.drawImage(imagenFondoCompleta,
//                        0, 0, getWidth(), getHeight(), // destino
//                        xOrigen, yOrigen,
//                        xOrigen + anchoPorcion, yOrigen + altoPorcion, // origen
//                        this);
//            }
//        }
//
//        // Luego pintar el contenido normal de la casilla
//        super.paintComponent(g);
//    }
}
