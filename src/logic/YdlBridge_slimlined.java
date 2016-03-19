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
package logic;

import GUI.MainWindowHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabian
 */
public class YdlBridge_slimlined extends Thread{
    
    private Process p;
    private BufferedReader stdInput, stdError;
    private final MainWindowHandler mwi;
    
    public YdlBridge_slimlined() throws IOException{
        this.mwi = MainWindowHandler.getMWI();  
    }
    
    private void feedback2MainWindow() throws IOException{
        String s;
            while ((s = stdInput.readLine()) != null || (s = stdError.readLine()) != null) {
                String[] prog = s.split(" ");
                for (String i : prog) {
                    if (i.matches("\\d{1,3}%") || i.matches("\\d{1,3}\\.\\d{1,2}%")) {
                        String tmp = i.substring(0, i.length() - 1);
                        float f = Float.parseFloat(tmp);
                        int a = (int) f;
                        mwi.updateProgressbar(a);
                    }
                }
                mwi.addYdlOutLine(s + "\n");
            }
    }
    private void exec() throws IOException{
            this.p = CustomProcessBuilder.getInstance().build().start();
            this.mwi.updateProgressbar(0);

         this.stdInput = new BufferedReader(new InputStreamReader(this.p.getInputStream()));
         this.stdError = new BufferedReader(new InputStreamReader(this.p.getErrorStream()));
         feedback2MainWindow();

    }   
    @Override
    public void run(){
        try {
            exec();
        } catch (IOException ex) {
            Logger.getLogger(YdlBridge_slimlined.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
