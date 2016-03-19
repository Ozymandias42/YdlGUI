/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JFrame;

/**
 *
 * @author Ozymandias42
 */
public class MainWindowHandler implements MainWindowHandlerInterface{
        private final MainWindow window;
        private static MainWindowHandler mwi;
        
        private MainWindowHandler(MainWindow window){
            this.window = window;
        }
        
        public static MainWindowHandler getMWI(){
                return mwi;
            }
        public static void init(MainWindow window){
            mwi = new MainWindowHandler(window);
        }
            
        
        @Override
        public JFrame getMainWindow() {
            return window;
        }

        @Override
        public void addYdlOutLine(String s) {
            this.window.ydlOutputAddLine(s);
        }
        @Override
        public void exitProgram(){
            MainWindow mw = (MainWindow) this.getMainWindow();
            mw.setVisible(false);
            mw.dispose();
            System.exit(0);
        }

    @Override
    public void updateProgressbar(int i) {
        this.window.downloadProgessbarUpdate(i);
    }

    @Override
    public void updateDefDwnDst(String newPath) {
        this.window.updateDefDwnDst(newPath);
    }
}
