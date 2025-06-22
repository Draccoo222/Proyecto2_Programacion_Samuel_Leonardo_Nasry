/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyecto;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author hnleo
 */
public class TableroPantallaStratego extends javax.swing.JFrame {

    /**
     * Creates new form TableroPantallla
     */
    private Tablero iniciar;
    private String jugador1, jugador2;
    private boolean jugador1Bando; //Si es true, el jugador 1 es heroe y el 2 es villano, si no, lo contrario.
    private GestionUsuario gestion;

    public TableroPantallaStratego() {
        initComponents();
        gestion = GestionUsuario.getInstancia();
        verificarJugadores();
        jugador1 = gestion.getJugadorActual();
        jugador2 = gestion.getJugador2Nombre();
        preguntarBando();
        setLocationRelativeTo(null);
        cargarJuego();
        
        System.out.println("Num fichas jugables " + iniciar.getNumFichas());
        String jugadorActivo = getTurnoActual();
        String bandoActivo = bandoTurnoActual();
        turnoLabel.setText(jugadorActivo + "(" + bandoActivo + ")");

    }
    
    
    private void salir(){
        this.dispose();
        menuPrincipal1 me = new menuPrincipal1();
        me.setVisible(true);
    }

    private void verificarJugadores() {
        if (!gestion.verificarJugadores()) {
            JOptionPane.showMessageDialog(null, "Error, Faltan jugadores.");
            this.dispose();
        }
    }

    private void preguntarBando() {
        Object[] opciones = {"Heroes", "Villanos"};

        // Mostrar el diálogo
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "¿Qué bando deseas elegir?",
                "Selección",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0] // Opción predeterminada
        );

        if (seleccion == 0) {
            Tablero.bando = true;
            jugador1Bando = true;
            JOptionPane.showMessageDialog(null, jugador1 + " es Heroes\n" + jugador2 + " es Villanos");
        } else if (seleccion == 1) {
            jugador1Bando = false;
            Tablero.bando = true;
            JOptionPane.showMessageDialog(null, jugador1 + " es Villanos\n" + jugador2 + " es Heroes");

        } else {
            jugador1Bando = true;
            Tablero.bando = true;
            JOptionPane.showMessageDialog(null, "Selección por defecto.");
        }
    }

    public void cambioDeTurno() {
    // DESPUÉS obtener la información del nuevo turno
    String jugadorActivo = getTurnoActual();
    String bandoActivo = bandoTurnoActual();
    
     
    if(iniciar != null){
        iniciar.actualizarVisibilidadPorTurno();
    }
    
        if(iniciar.getNumFichas() == 0){
            iniciar.getJugabilidad().setGanador(0);
        }
    
    
        switch(iniciar.getJugabilidad().getGanador()){
            case 1:
                JOptionPane.showMessageDialog(null, gestion.getJugadorActual() + " ha ganado!");
                gestion.getJugador1().sumarPuntaje();
                salir();
                break;
            case 2:
                JOptionPane.showMessageDialog(null, gestion.getJugador2Nombre() + " ha ganado!");
                gestion.getJugador2().sumarPuntaje();
                salir();
                break;
            case 0:
                JOptionPane.showMessageDialog(null, "EMPATE");
                salir();
                break;
            default:
               /* JOptionPane.showMessageDialog(null, 
                "Turno de: " + jugadorActivo + " (" + bandoActivo + ")", 
                "Cambio de Turno", 
                JOptionPane.INFORMATION_MESSAGE);*/
                turnoLabel.setText(jugadorActivo + "(" + bandoActivo + ")");
                break;
        }
         System.out.println("Num fichas jugables " + iniciar.getNumFichas());
   
    
    
    }

    public String getTurnoActual() {
        if (Tablero.bando) {
            if (jugador1Bando) {
                return jugador1;
            } else {
                return jugador2;
            }
        } else {
            if (jugador1Bando) {
                return jugador2;
            } else {
                return jugador1;
            }
        }
    }

    private String bandoTurnoActual() {
        if (Tablero.bando) {
            return "Heroes";
        } else {
            return "Villanos";
        }
    }

    public String getBandoJugador1(){
        return jugador1 + bandoTurnoActual();
    }
    
    
    public String getJugador1() {
        return jugador1;
    }

    public String getJugador2() {
        return jugador2;
    }

    public boolean esJugador1Bando() {
        return jugador1Bando;
    }

    private void cargarTablero() {
        iniciar = new Tablero(10, 10, "/images/tablero.png", this);
        iniciar.setSize(panelTablero.getWidth(), panelTablero.getHeight());
        iniciar.setPreferredSize(panelTablero.getSize());
        iniciar.setBounds(0, 0, panelTablero.getWidth(), panelTablero.getHeight());
        panelTablero.setLayout(new BorderLayout());
        panelTablero.add(iniciar);

        panelTablero.repaint();
        panelTablero.revalidate();
    }

    private void cargarJuego() {
        cargarTablero();
        if (iniciar != null) {
        iniciar.actualizarVisibilidadPorTurno();
    }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelEliminaciones = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        turnoLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaEliminados = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        panelTablero = new javax.swing.JPanel();
        panelVillanos = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelHeroes = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 920));
        setResizable(false);
        setSize(new java.awt.Dimension(1000, 920));

        panelEliminaciones.setBackground(new java.awt.Color(255, 255, 51));
        panelEliminaciones.setMaximumSize(new java.awt.Dimension(200, 440));
        panelEliminaciones.setMinimumSize(new java.awt.Dimension(200, 440));
        panelEliminaciones.setPreferredSize(new java.awt.Dimension(200, 440));
        panelEliminaciones.setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(160, 50));

        turnoLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        turnoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Turno");
        jLabel5.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(turnoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(turnoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelEliminaciones.add(jPanel1);
        jPanel1.setBounds(20, 640, 160, 80);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        areaEliminados.setColumns(20);
        areaEliminados.setRows(5);
        jScrollPane1.setViewportView(areaEliminados);

        panelEliminaciones.add(jScrollPane1);
        jScrollPane1.setBounds(20, 70, 160, 560);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eliminaciones.png"))); // NOI18N
        panelEliminaciones.add(jLabel1);
        jLabel1.setBounds(0, 0, 200, 800);

        getContentPane().add(panelEliminaciones, java.awt.BorderLayout.LINE_END);

        panelTablero.setBackground(new java.awt.Color(255, 255, 255));
        panelTablero.setMaximumSize(new java.awt.Dimension(800, 800));
        panelTablero.setMinimumSize(new java.awt.Dimension(800, 800));
        panelTablero.setPreferredSize(new java.awt.Dimension(800, 800));

        javax.swing.GroupLayout panelTableroLayout = new javax.swing.GroupLayout(panelTablero);
        panelTablero.setLayout(panelTableroLayout);
        panelTableroLayout.setHorizontalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        panelTableroLayout.setVerticalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );

        getContentPane().add(panelTablero, java.awt.BorderLayout.CENTER);

        panelVillanos.setBackground(new java.awt.Color(255, 0, 0));
        panelVillanos.setMaximumSize(new java.awt.Dimension(1000, 60));
        panelVillanos.setMinimumSize(new java.awt.Dimension(1000, 60));
        panelVillanos.setPreferredSize(new java.awt.Dimension(1000, 60));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bannerVillanos.png"))); // NOI18N

        javax.swing.GroupLayout panelVillanosLayout = new javax.swing.GroupLayout(panelVillanos);
        panelVillanos.setLayout(panelVillanosLayout);
        panelVillanosLayout.setHorizontalGroup(
            panelVillanosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelVillanosLayout.setVerticalGroup(
            panelVillanosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVillanosLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(panelVillanos, java.awt.BorderLayout.PAGE_START);

        panelHeroes.setBackground(new java.awt.Color(51, 0, 255));
        panelHeroes.setMaximumSize(new java.awt.Dimension(1000, 60));
        panelHeroes.setMinimumSize(new java.awt.Dimension(1000, 60));
        panelHeroes.setPreferredSize(new java.awt.Dimension(1000, 60));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bannerHeroes.png"))); // NOI18N

        javax.swing.GroupLayout panelHeroesLayout = new javax.swing.GroupLayout(panelHeroes);
        panelHeroes.setLayout(panelHeroesLayout);
        panelHeroesLayout.setHorizontalGroup(
            panelHeroesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeroesLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelHeroesLayout.setVerticalGroup(
            panelHeroesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeroesLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(panelHeroes, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TableroPantallaStratego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TableroPantallaStratego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TableroPantallaStratego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TableroPantallaStratego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TableroPantallaStratego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaEliminados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelEliminaciones;
    private javax.swing.JPanel panelHeroes;
    private javax.swing.JPanel panelTablero;
    private javax.swing.JPanel panelVillanos;
    private javax.swing.JLabel turnoLabel;
    // End of variables declaration//GEN-END:variables
}
