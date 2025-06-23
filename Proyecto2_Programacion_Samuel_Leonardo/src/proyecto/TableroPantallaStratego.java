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
    private String heroesElim[] = new String[40];
    private String villanosElim[] = new String[40];
    private int contH=0, contV=0;

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
     refreshTextArea();
    
    
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
    
    public void personajesEliminados(Fichas personajeEliminado){
        String nombrePersonaje = personajeEliminado.getNombrePersonaje()+" Rango("+personajeEliminado.getRango()+")";
        
        if(personajeEliminado.isEsHeroe()){
            if(contH<heroesElim.length){
                heroesElim[contH] = nombrePersonaje;
                contH++;
            }
        }else{
            if(contV<villanosElim.length){
                villanosElim[contV] = nombrePersonaje;
                contV++;
            }
        }
        refreshTextArea();
    }
    
    private void refreshTextArea(){
        StringBuilder cont = new StringBuilder();
        boolean mostrarFichasEliminadas = Tablero.bando;
        if(mostrarFichasEliminadas){
            for (int i = 0; i < contV; i++) {
                if(villanosElim[i]!=null){
                    cont.append(villanosElim[i]).append("\n");
                }
            }
        }else{
            for (int i = 0; i < contH; i++) {
                if(heroesElim[i]!=null){
                    cont.append(heroesElim[i]).append("\n");
                }
            }
        }
        areaEliminados.setText(cont.toString());
        //Para volver a la primer linea e ir refrescando.
        areaEliminados.setCaretPosition(0);
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
    refreshTextArea();
    
    areaEliminados.setOpaque(false);
    areaEliminados.setBackground(new Color(0, 0, 0, 0));
    areaEliminados.setForeground(Color.BLACK);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        areaEliminados = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        panelTablero = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1000, 800));
        setMinimumSize(new java.awt.Dimension(1000, 800));
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(1000, 800));

        panelEliminaciones.setBackground(new java.awt.Color(255, 255, 51));
        panelEliminaciones.setMaximumSize(new java.awt.Dimension(200, 440));
        panelEliminaciones.setMinimumSize(new java.awt.Dimension(200, 440));
        panelEliminaciones.setPreferredSize(new java.awt.Dimension(200, 440));
        panelEliminaciones.setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(160, 50));
        jPanel1.setOpaque(false);

        turnoLabel.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        turnoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turnoLabel.setText("TURNO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(turnoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(turnoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        panelEliminaciones.add(jPanel1);
        jPanel1.setBounds(20, 540, 160, 50);

        jScrollPane1.setBackground(new java.awt.Color(218, 191, 144));
        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        areaEliminados.setBackground(new java.awt.Color(218, 191, 144));
        areaEliminados.setColumns(20);
        areaEliminados.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        areaEliminados.setRows(5);
        areaEliminados.setBorder(null);
        areaEliminados.setCaretColor(new java.awt.Color(218, 191, 144));
        areaEliminados.setDisabledTextColor(new java.awt.Color(218, 191, 144));
        areaEliminados.setOpaque(false);
        jScrollPane1.setViewportView(areaEliminados);

        panelEliminaciones.add(jScrollPane1);
        jScrollPane1.setBounds(20, 100, 160, 380);

        jButton1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jButton1.setText("RENDIRTE");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelEliminaciones.add(jButton1);
        jButton1.setBounds(10, 620, 180, 60);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ELIMINADOS.png"))); // NOI18N
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

        jPanel2.setBackground(new java.awt.Color(255, 255, 51));
        jPanel2.setMaximumSize(new java.awt.Dimension(100, 800));
        jPanel2.setMinimumSize(new java.awt.Dimension(100, 800));
        jPanel2.setPreferredSize(new java.awt.Dimension(120, 800));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/barraDivid.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.WEST);

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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelEliminaciones;
    private javax.swing.JPanel panelTablero;
    private javax.swing.JLabel turnoLabel;
    // End of variables declaration//GEN-END:variables
}
