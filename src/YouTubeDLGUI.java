/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import GUI.MainWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Ozymandias42
 */
public class YouTubeDLGUI {

    public static void main(String[] args) {

        try {
            // Set System L&F
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | 
                          InstantiationException | IllegalAccessException e) {
            // handle exception
        }

        
        java.awt.EventQueue.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });

                
        
    }
}
