/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PauseMenu.java
 *
 * Created on Jan 20, 2012, 9:42:34 AM
 */
package minecarft;

import javax.swing.JFileChooser;

/**
 *
 * @author kevin
 */
public class PauseMenu extends javax.swing.JFrame {

    /** Creates new form PauseMenu */
    public PauseMenu() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        saveButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();
        resumeButton = new javax.swing.JButton();

        jFileChooser1.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pause");

        saveButton.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        saveButton.setText("Save Map");
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                saveButtonMouseReleased(evt);
            }
        });

        quitButton.setFont(new java.awt.Font("Ubuntu", 0, 24));
        quitButton.setText("Quit Game");
        quitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                quitButtonMouseReleased(evt);
            }
        });

        resumeButton.setFont(new java.awt.Font("Ubuntu", 0, 24));
        resumeButton.setText("Resume Game");
        resumeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                resumeButtonMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(quitButton)
                    .addComponent(saveButton)
                    .addComponent(resumeButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resumeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quitButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveButtonMouseReleased
        int returnVal = jFileChooser1.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            FileIO.saveMap(jFileChooser1.getSelectedFile());
        }
        dispose();
    }//GEN-LAST:event_saveButtonMouseReleased

    private void quitButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quitButtonMouseReleased
        System.exit(0);
    }//GEN-LAST:event_quitButtonMouseReleased

    private void resumeButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resumeButtonMouseReleased
        dispose();
    }//GEN-LAST:event_resumeButtonMouseReleased

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
            java.util.logging.Logger.getLogger(PauseMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PauseMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PauseMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PauseMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new PauseMenu().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JButton quitButton;
    private javax.swing.JButton resumeButton;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
