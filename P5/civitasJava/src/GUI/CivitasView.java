package GUI;

import javax.swing.JOptionPane;
import java.util.Arrays;
import javax.swing.JOptionPane;

import civitas.CivitasJuego;
import civitas.GestorEstados;
import civitas.SalidasCarcel;
import civitas.Respuestas;
import GUI.GestionarDialog;
import civitas.Jugador;
import civitas.OperacionesJuego;

public class CivitasView extends javax.swing.JFrame {

    CivitasJuego juego;
    JugadorPanel jugadorPanel;
    GestionarDialog gestionarD;

    int gestionElegida;     // not sure
    int propiedadElegida;   // not sure

    public void setCivitasJuego(CivitasJuego j) {
        juego = j;
        setVisible(true);
    }

    public CivitasView() {
        initComponents();

        jugadorPanel = new JugadorPanel();
        gestionarD = new GestionarDialog(this, rootPaneCheckingEnabled);
        contenedorVistaJugador.add(jugadorPanel);

        repaint();
        revalidate();
    }

    void actualizarVista() {
        jugadorPanel.setJugador(juego.getJugadorActual());
        label_ranking.setVisible(false);
        ranking.setVisible(false);
        
        // Y: get nombre o to string?
        casillaActual.setText(juego.getCasillaActual().getNombre());

        if (juego.finalDelJuego()) {                              // Si es el final del juego
            ranking.setText(String.valueOf(juego.ranking()));   // E: a lo mejor deberíamos hacer un to_string de ranking porque no sé cómo saldrá en el valueOf
            label_ranking.setVisible(true);
            ranking.setVisible(true);

            repaint();
            revalidate();
        }
    }
    
    SalidasCarcel salirCarcel() {
        String[] opciones= {"Pagando", "Tirando"};
        
        int respuesta= JOptionPane.showOptionDialog(null, "¿Cómo quieres salir de la cárcel?",
                       "Salir de la cárcel", JOptionPane.DEFAULT_OPTION,
                       JOptionPane.QUESTION_MESSAGE,null, opciones, opciones[0] );
        
        SalidasCarcel salida;
        
        if (respuesta == 0)
            salida = SalidasCarcel.PAGANDO;
        else salida = SalidasCarcel.TIRANDO;
        
        return salida;
    }

    void mostrarSiguienteOperacion(OperacionesJuego op) {
        siguienteop.setText(String.valueOf(op));
        actualizarVista();
    }

    void mostrarEventos() {
        DiarioDialog diarioD= new DiarioDialog(this); //crea la ventana del diario
        diarioD.mostrarEventos();
        diarioD.repaint();
        diarioD.revalidate();
    }

    Respuestas comprar() {
        int opcion = JOptionPane.showConfirmDialog(null, "¿Comprar la calle actual?", "Compra", JOptionPane.YES_NO_OPTION);
        Respuestas res;

        if (opcion == 0) 
            res = Respuestas.SI;
        else 
            res = Respuestas.NO;

        return res;
    }

    public int getGestionElegida() {
        return gestionarD.getGestionElegida();
    }

    public int getPropiedadElegida() {
        return gestionarD.getPropiedadElegida();
    }
    
    public void gestionar() {
        gestionarD.gestionar(juego.getJugadorActual());
        gestionarD.pack();
        gestionarD.repaint();
        gestionarD.revalidate();
        
        gestionarD.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        que_es_esto = new javax.swing.JLabel();
        contenedorVistaJugador = new javax.swing.JPanel();
        label_siguienteop = new javax.swing.JLabel();
        siguienteop = new javax.swing.JTextField();
        label_ranking = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ranking = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        casillaActual = new javax.swing.JTextField();
        label_casillaActual = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        que_es_esto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        que_es_esto.setText("CivitasView");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, que_es_esto, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), que_es_esto, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        label_siguienteop.setText("Siguiente operación:");

        siguienteop.setText("Siguiente operación");
        siguienteop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siguienteopActionPerformed(evt);
            }
        });

        label_ranking.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_ranking.setText("Ranking");

        ranking.setColumns(20);
        ranking.setRows(5);
        jScrollPane1.setViewportView(ranking);

        casillaActual.setText("CasillaActual");

        label_casillaActual.setText("CasillaActual:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_siguienteop)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(siguienteop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_casillaActual)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(casillaActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(contenedorVistaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(202, 202, 202)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(que_es_esto)
                                        .addGap(8, 8, 8))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(label_ranking, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(que_es_esto, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(contenedorVistaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_siguienteop)
                    .addComponent(siguienteop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_casillaActual)
                    .addComponent(casillaActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label_ranking)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void siguienteopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siguienteopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_siguienteopActionPerformed

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
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CivitasView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField casillaActual;
    private javax.swing.JPanel contenedorVistaJugador;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel label_casillaActual;
    private javax.swing.JLabel label_ranking;
    private javax.swing.JLabel label_siguienteop;
    private javax.swing.JLabel que_es_esto;
    private javax.swing.JTextArea ranking;
    private javax.swing.JTextField siguienteop;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
