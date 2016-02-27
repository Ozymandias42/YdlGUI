/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import GUI.MainWindowHandler;
import GUI.YdlFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ozymandias42
 */
public class IniHandler {

    private Properties iniFile;
    private File file;
    private static IniHandler iniHandlerInstance;

    private IniHandler() {
        this.iniFile = new Properties();

        /*
        System.out.println("System.getProperty(\"user.home\")+"
                + "System.getProperty(\"file.separator\") = "
                + System.getProperty("user.home")
                + System.getProperty("file.separator"));
        */

        /*
        String home = System.getProperty("user.home");
        String separator = System.getProperty("file.separator");
        */

        file = new File(GlobalVariable.userHome() + GlobalVariable.sep() + "YDLGUI-settings.ini");
        FileReader in;
        if (file.exists()) {
            try {
                in = new FileReader(file);
                iniFile.load(in);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(IniHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(IniHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
        try {
            
            JOptionPane.showMessageDialog(MainWindowHandler.getMWI().getMainWindow(),
                    "This seems to be your first time running this App.\n"
                            + "To ensure a smooth experience we need you to "
                            + "tell us where two important Files are. \n"
                            + "Don't worry though. We shouldn't need to go through this hassle again.", 
                    "Initial Configuaration", 
                            JOptionPane.INFORMATION_MESSAGE);
            
            System.out.println("Creating new Ini-File. in " + 
                    GlobalVariable.userHome() + GlobalVariable.sep() + "YDLGUI-settings.ini");
            file.createNewFile();
            iniFile = new StandardIniFile();

            try (FileWriter out = new FileWriter(file)) {
                iniFile.store(out, "Generated INI-File");
                out.close();
            }
        } catch (IOException ex1) {
            System.out.println("IniHandler. IOException 2 Catch-Block. \n "
                    + "creation of File failed.");
            Logger.getLogger(IniHandler.class.getName()).log(Level.SEVERE, null, ex1);
        }
        }
    }

    public String getProperty(String key) {
        return iniFile.getProperty(key);
    }
    
    public void setProperty(String key, String value){
        iniFile.setProperty(key, value);
        try {
            try (FileWriter fr = new FileWriter(this.file)) {
                iniFile.store(fr, "Generated INI-File");
                fr.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(IniHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static IniHandler getIni() {
        if (iniHandlerInstance != null) {
            return iniHandlerInstance;
        } else {
            System.out.println("Creating new IniHandler() Instance.");
            iniHandlerInstance = new IniHandler();
            return iniHandlerInstance;

        }
    }

    private class StandardIniFile extends Properties {

        StandardIniFile() {
            MainWindowHandler mwh = MainWindowHandler.getMWI();
            String ydl;
            String ffmpeg;
            YdlFileChooser chooser = new YdlFileChooser(mwh.getMainWindow(),
                    "Choose location of youtube-dl");
            if (chooser.showDiag() == 1) {
                ydl = chooser.getPath();
            } else {
                JOptionPane.showMessageDialog(mwh.getMainWindow(), "Path to "
                        + "youtube-dl needs to be set.\n"
                        + "Setting it to a default value.\n"
                        + "This program won't run this way. Please change it "
                        + "to the correct path later on. \n"
                        + "File resides in: " + System.getProperty("user.home")
                        + System.getProperty("file.separator")
                        + "YDLGUI-settings.ini", "Config Error",
                        JOptionPane.ERROR_MESSAGE);
                ydl = "NOT SET";
            }
            YdlFileChooser chooser2 = new YdlFileChooser(mwh.getMainWindow(),
                    "Choose location of ffmpeg");
            if (chooser2.showDiag() == 1) {
                ffmpeg = chooser2.getPath();
            } else {
                JOptionPane.showMessageDialog(mwh.getMainWindow(), "Path to "
                        + "ffmpeg should be set.\n"
                        + "Otherwise files downloaded by the audio only option"
                        + " might be unplayable by some players. \n"
                        + "Setting path to ffmpeg to a default value.\n"
                        + "Please change it "
                        + "to the correct path later on. \n"
                        + "File resides in: " + System.getProperty("user.home")
                        + System.getProperty("file.separator")
                        + "YDLGUI-settings.ini", "Config Error",
                        JOptionPane.ERROR_MESSAGE);
                ffmpeg = "NOT SET";
            }

            setProperty("youtube-dl.path.prefix", ydl);
            setProperty("ffmpeg.path.prefix", ffmpeg);
            setProperty("customCountry", "");
            setProperty("customLanguage","");

        }

    }
}
