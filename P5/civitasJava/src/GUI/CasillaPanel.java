
package GUI;

import civitas.Casilla;
import civitas.CasillaImpuesto;
import civitas.CasillaSorpresa;
import civitas.CasillaJuez;
import civitas.CasillaCalle;

public class CasillaPanel extends javax.swing.JPanel {
    
    Casilla casilla;

    /**
     * Creates new form CasillaPanel
     */
    public CasillaPanel() {
        initComponents();
    }

    void setCasilla(Casilla c) {
        
        casilla = c;
        
        nombre.setText(casilla.getNombre());
        tipo.setText(casilla.getTipo());
        
        if (casilla instanceof CasillaImpuesto) 
            importe.setText(String.valueOf( ((CasillaImpuesto)casilla).getImporte() ));
        else
            importe.setText("No aplica");
        
        repaint();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_nombre = new javax.swing.JLabel();
        label_tipo = new javax.swing.JLabel();
        label_importe = new javax.swing.JLabel();
        nombre = new javax.swing.JTextField();
        tipo = new javax.swing.JTextField();
        importe = new javax.swing.JTextField();

        label_nombre.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_nombre.setText("Nombre casilla:");

        label_tipo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_tipo.setText("Tipo:");

        label_importe.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_importe.setText("Importe:");

        nombre.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        nombre.setText("nombre");

        tipo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tipo.setText("tipo");

        importe.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        importe.setText("importe");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_importe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_tipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_nombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_nombre)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_tipo)
                    .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_importe)
                    .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField importe;
    private javax.swing.JLabel label_importe;
    private javax.swing.JLabel label_nombre;
    private javax.swing.JLabel label_tipo;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField tipo;
    // End of variables declaration//GEN-END:variables
}
