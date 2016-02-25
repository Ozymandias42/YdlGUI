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
public interface MainWindowHandlerInterface {
    public JFrame getMainWindow();
    public void addYdlOutLine(String s);
    public void updateProgressbar(int i);
    public void exitProgram();
}
