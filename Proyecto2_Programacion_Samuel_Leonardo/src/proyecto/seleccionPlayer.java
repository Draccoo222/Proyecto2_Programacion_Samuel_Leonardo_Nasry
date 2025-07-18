/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyecto;

import javax.swing.JOptionPane;

/**
 *
 * @author hnleo
 */
public class seleccionPlayer extends javax.swing.JFrame {

    GestionUsuario gestion;

    /**
     * Creates new form nuevaPass
     */
    public seleccionPlayer() {
        initComponents();
        gestion = GestionUsuario.getInstancia();
        nombre1Label.setText(gestion.getJugadorActual());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        regresarButton = new javax.swing.JButton();
        iniciarButton = new javax.swing.JButton();
        nombre1Label = new javax.swing.JLabel();
        player2Text = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(500, 200));
        setMinimumSize(new java.awt.Dimension(500, 200));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setMaximumSize(new java.awt.Dimension(500, 200));
        jPanel1.setMinimumSize(new java.awt.Dimension(500, 200));
        jPanel1.setLayout(null);

        regresarButton.setBackground(new java.awt.Color(255, 0, 51));
        regresarButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        regresarButton.setForeground(new java.awt.Color(255, 255, 255));
        regresarButton.setText("Regresar");
        regresarButton.setAlignmentY(0.0F);
        regresarButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        regresarButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        regresarButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        regresarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regresarButtonActionPerformed(evt);
            }
        });
        jPanel1.add(regresarButton);
        regresarButton.setBounds(410, 170, 81, 24);

        iniciarButton.setBackground(new java.awt.Color(255, 0, 51));
        iniciarButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        iniciarButton.setForeground(new java.awt.Color(255, 255, 255));
        iniciarButton.setText("INICIAR");
        iniciarButton.setAlignmentY(0.0F);
        iniciarButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        iniciarButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iniciarButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        iniciarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarButtonActionPerformed(evt);
            }
        });
        jPanel1.add(iniciarButton);
        iniciarButton.setBounds(210, 150, 80, 30);

        nombre1Label.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        nombre1Label.setText("nombreJugador1");
        jPanel1.add(nombre1Label);
        nombre1Label.setBounds(40, 120, 150, 20);

        player2Text.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        player2Text.setBorder(null);
        player2Text.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        player2Text.setOpaque(true);
        jPanel1.add(player2Text);
        player2Text.setBounds(310, 120, 150, 21);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/selectJugador.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(0, 0, 500, 200);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void regresarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regresarButtonActionPerformed
        this.dispose();
        menuPrincipal1 menu = new menuPrincipal1();
        menu.setVisible(true);
    }//GEN-LAST:event_regresarButtonActionPerformed

    private void iniciarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarButtonActionPerformed
        String nomJug2 = player2Text.getText().trim();
        if(nomJug2.isEmpty()){
            JOptionPane.showMessageDialog(null,"Error, Ingresa un segundo jugador.");
            return;
        }
        //Validar que el jugador 2 no es el mismo que jugador 1 y que existe.
        if(gestion.buscarJugador2(nomJug2)){
            gestion.setJugador2(nomJug2);
            JOptionPane.showMessageDialog(null, "Iniciando Partida..");
            this.dispose();
            TableroPantallaStratego tablero = new TableroPantallaStratego();
            tablero.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Error, Jugador no existe o ya es jugador 1");
         
        }       
    }//GEN-LAST:event_iniciarButtonActionPerformed

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
            java.util.logging.Logger.getLogger(seleccionPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(seleccionPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(seleccionPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(seleccionPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new seleccionPlayer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton iniciarButton;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nombre1Label;
    private javax.swing.JTextField player2Text;
    private javax.swing.JButton regresarButton;
    // End of variables declaration//GEN-END:variables
}
