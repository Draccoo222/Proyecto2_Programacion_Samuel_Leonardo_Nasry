/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyecto;

import java.awt.*;
import javax.swing.*;
import java.time.LocalDateTime;
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
    private static int partidasTotales;
  


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
        partidasTotales++;
        
        
        if(jugador1Bando){
            gestion.getJugador1().sumPartidasHeroe();
            gestion.getJugador2().sumPartidasVillano();
        }else{
            gestion.getJugador1().sumPartidasVillano();
            gestion.getJugador2().sumPartidasHeroe();
        }

    }

    
    public static int getNumPartidas(){
        return partidasTotales;
    }
    
    
   

    private void salir() {

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
        } else if (seleccion == 1) {
            jugador1Bando = false;
            Tablero.bando = true;

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
        
       
    

        if (iniciar != null) {
            iniciar.actualizarVisibilidadPorTurno();
        }

        if (iniciar.getNumFichas() == 0) {
            iniciar.getJugabilidad().setGanador(0);
        }
        
            String jugadorPerdedor = getTurnoActual();
            String jugadorGanador;
            String bandoGanador;
            String bandoPerdedor;

                if (jugadorPerdedor.equals(jugador1)) {
                    jugadorGanador = jugador2;

                    if (jugador1Bando) {
                        bandoGanador = "Villanos";
                        bandoPerdedor = "Heroes";
                    } else {
                        bandoGanador = "Heroes";
                        bandoPerdedor = "Villanos";
                    }
                } else {
                    jugadorGanador = jugador1;
                    if (jugador1Bando) {
                        bandoGanador = "Heroes";
                        bandoPerdedor = "Villanos";
                    } else {
                        bandoGanador = "Villanos";
                        bandoPerdedor = "Heroes";
                    }
                }
                
               LocalDateTime fecha = LocalDateTime.now();
                
               String tierra = (bandoGanador.equals("Heroe")) ? "Salvado la tierra!" : "Capturado la tierra!";
               String resultado = jugadorGanador + " usando los " + bandoGanador + " ha " + tierra + " Venciendo a "
                       + jugadorPerdedor + "- " + fecha ;
               
               
               
               

        switch (iniciar.getJugabilidad().getGanador()) {
            case 1:
                gestion.getJugador1().sumarPuntaje();
                JOptionPane.showMessageDialog(null, resultado);
                gestion.getJugador1().sumarLogeo(resultado);
                gestion.getJugador2().sumarLogeo(resultado);
                salir();
                break; 
            case 2:
                gestion.getJugador2().sumarPuntaje();
                JOptionPane.showMessageDialog(null, resultado);
                gestion.getJugador1().sumarLogeo(resultado);
                gestion.getJugador2().sumarLogeo(resultado);
                salir();
                break;
            case 0:
                break;
            default:
                turnoLabel.setText(jugadorActivo + "(" + bandoActivo + ")");
                break;
        }

     
    
    
        System.out.println("Num fichas jugables " + iniciar.getNumFichas());
        refreshTextArea();


    }

    private String getTurnoActual() {
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

    private String getBandoJugador1() {
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

    public void personajesEliminados(Fichas personajeEliminado) {
        String nombrePersonaje = personajeEliminado.getNombrePersonaje() + " Rango(" + personajeEliminado.getRango() + ")";

        if (personajeEliminado.isEsHeroe()) {
            if (contH < heroesElim.length) {
                heroesElim[contH] = nombrePersonaje;
                contH++;
            }
        } else {
            if (contV < villanosElim.length) {
                villanosElim[contV] = nombrePersonaje;
                contV++;
            }
        }
        refreshTextArea();
    }

    private void refreshTextArea() {
        StringBuilder cont = new StringBuilder();
        boolean mostrarFichasEliminadas = Tablero.bando;
        if (mostrarFichasEliminadas) {
            for (int i = 0; i < contV; i++) {
                if (villanosElim[i] != null) {
                    cont.append(villanosElim[i]).append("\n");
                }
            }
        } else {
            for (int i = 0; i < contH; i++) {
                if (heroesElim[i] != null) {
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
        rendirseButton = new javax.swing.JButton();
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

        rendirseButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        rendirseButton.setText("RENDIRTE");
        rendirseButton.setBorderPainted(false);
        rendirseButton.setContentAreaFilled(false);
        rendirseButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rendirseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rendirseButtonActionPerformed(evt);
            }
        });
        panelEliminaciones.add(rendirseButton);
        rendirseButton.setBounds(10, 620, 180, 60);

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

    private void rendirseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rendirseButtonActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro desea rendirse?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String jugadorPerdedor = getTurnoActual();
            String jugadorGanador;
            String bandoGanador;
            String bandoPerdedor;

            if (jugadorPerdedor.equals(jugador1)) {
                jugadorGanador = jugador2;

                if (jugador1Bando) {
                    bandoGanador = "Villanos";
                    bandoPerdedor = "Heroes";
                    jugabilidadTablero.sumVictorias(1);
                } else {
                    bandoGanador = "Heroes";
                    bandoPerdedor = "Villanos";
                    jugabilidadTablero.sumVictorias(0);
                }
            } else {
                jugadorGanador = jugador1;
                if (jugador1Bando) {
                    bandoGanador = "Heroes";
                    bandoPerdedor = "Villanos";
                    jugabilidadTablero.sumVictorias(0);
                } else {
                    bandoGanador = "Villanos";
                    bandoPerdedor = "Heroes";
                    jugabilidadTablero.sumVictorias(1);
                }
            }

            LocalDateTime fecha = LocalDateTime.now();
            
            String resultado = jugadorGanador + " usando los " + bandoGanador + " ha ganado ya que " 
                    + jugadorPerdedor + " usando " + bandoPerdedor + " se ha retirado del juego." + "RENDICION -" + fecha;
            
            JOptionPane.showMessageDialog(null, resultado);

            if (jugadorGanador.equals(jugador1)) {
                gestion.getJugador1().sumarPuntaje();
                gestion.getJugador1().sumarLogeo(resultado);
                gestion.getJugador2().sumarLogeo(resultado);
            } else {
                gestion.getJugador2().sumarPuntaje();
                gestion.getJugador1().sumarLogeo(resultado);
                gestion.getJugador2().sumarLogeo(resultado);
            }

            salir();
        }
    }//GEN-LAST:event_rendirseButtonActionPerformed

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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelEliminaciones;
    private javax.swing.JPanel panelTablero;
    private javax.swing.JButton rendirseButton;
    private javax.swing.JLabel turnoLabel;
    // End of variables declaration//GEN-END:variables
}
