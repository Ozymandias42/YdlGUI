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
import GUI.CustomDialogs;

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
        loadFile();
    }

    private void loadFile(){
        file = new File(GlobalParameters.userHome() + GlobalParameters.sep() + "YDLGUI-settings.ini");
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
        } else {
            createNewIniFile();
        }
    }
    private void createNewIniFile(){
        try {
                CustomDialogs.firstTime();

                System.out.println("Creating new Ini-File. in "
                        + GlobalParameters.userHome() + GlobalParameters.sep() + "YDLGUI-settings.ini");
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
    public String getProperty(String key) {
        if(iniFile.getProperty(key)!=null){
        return iniFile.getProperty(key);
        }
        else{return "Not yet set.";}
    }

    public void setProperty(String key, String value) {
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

    public static IniHandler getIni() throws NullPointerException{
        return IniHandler.iniHandlerInstance;
    }

    public static void init() {
        System.out.println("Creating new IniHandler() Instance.");
        iniHandlerInstance = new IniHandler();
    }

    private class StandardIniFile extends Properties {

        StandardIniFile() {
            MainWindowHandler mwh = MainWindowHandler.getMWI();
            String ydl;
            String ffmpeg;
            YdlFileChooser chooser = CustomDialogs.ydlLocation();
            if (chooser.showDiag() == 1) {
                ydl = chooser.getPath();
            } else {
                CustomDialogs.warningYDLLocation();
                ydl = "NOT SET";
            }
            YdlFileChooser chooser2 = CustomDialogs.ffmpegLocation();
            if (chooser2.showDiag() == 1) {
                ffmpeg = chooser2.getPath();
            } else {
                CustomDialogs.warningFfmpegLocation();
                ffmpeg = "NOT SET";
            }

            setProperty("youtube-dl.path.prefix", ydl);
            setProperty("ffmpeg.path.prefix", ffmpeg);
            setProperty("custom.country", "");
            setProperty("custom.language", "");

        }

    }
}
