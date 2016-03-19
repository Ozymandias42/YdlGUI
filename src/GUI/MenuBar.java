/*
 * Copyright (C) 2016 fabian
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package GUI;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author fabian
 */
public class MenuBar extends JMenuBar{
    private final JMenu file;
    private final JMenuItem settings;
    private final MenuBarListener ml;
    
    public MenuBar(){
        this.ml = new MenuBarListener();
        this.file = new JMenu("File");
        this.settings = new JMenuItem("Settings");
        add(file);
        this.file.add(settings);
        this.settings.addActionListener(ml);
        
        settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA,
        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }
    
    private class MenuBarListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            java.awt.EventQueue.invokeLater(() -> {
                new SettingsWindow().setVisible(true);
            });
        }
    }
}
