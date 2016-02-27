/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ozymandias42
 */
import GUI.*;
import java.io.File;
import javax.swing.JOptionPane;

public class YdlBridge extends Thread {

    //private final String[] commands;
    private final String dURL;
    private final String targetDir;
    private String[] commands;
    private final int mode;
    private ProcessBuilder pb;
    private Process proc;
    private String prefix;
    private String sep;
    private String ffmpegPrefix;
    private final MainWindowHandlerInterface mwi;
    private final String dirChar;
    private BufferedReader stdInput;
    private BufferedReader stdError;

    public YdlBridge(int mode, String dURL) {

        this.dirChar = System.getProperty("file.separator");
        this.mwi = MainWindowHandler.getMWI();
        this.dURL = dURL;
        this.mode = mode;
        setPrefixes();
        YdlFileChooser chooser = new YdlFileChooser(mwi.getMainWindow());
        File currDir = new File(System.getProperty("user.home") + dirChar);
        chooser.setCurrentDirectory(currDir);
        if (chooser.showDiag() == 1) {
            this.targetDir = chooser.getPath();
        } else {
            this.targetDir = System.getProperty("user.home") + dirChar + "Downloads";
        }

        buildCommand();

    }

    private void buildCommand() {

        if (mode == 0) {
            String[] bob = {prefix + sep + "youtube-dl", "-f", "mp4", dURL, "-o", targetDir + dirChar + "%(title)s.%(ext)s"};
            this.commands = bob;

        } else {
            if ("NOT SET".equals(ffmpegPrefix)) {
                String[] bob = {prefix + sep + "youtube-dl", "-f", "m4a", dURL, "-o",
                    targetDir + dirChar + "%(title)s.%(ext)s"};
                this.commands = bob;
            } else {
                String[] bob = {prefix + sep + "youtube-dl", "--ffmpeg-location", ffmpegPrefix + sep, "-f", "m4a", dURL, "-o",
                    targetDir + dirChar + "%(title)s.%(ext)s"};
                this.commands = bob;
            }
        }
    }

    private void setPrefixes() {
        IniHandler iniProps = IniHandler.getIni();
        this.prefix = iniProps.getProperty("youtube-dl.path.prefix");
        this.ffmpegPrefix = iniProps.getProperty("ffmpeg.path.prefix");
        this.sep = System.getProperty("file.separator");

        if (prefix.equals("NOT SET")) {
            String[] options = {"Set Path", "Quit App"};
            if (JOptionPane.showOptionDialog(
                    mwi.getMainWindow(),
                    "Path to youtube-dl not set.\n"
                    + "App won't run this way.\n"
                    + "Set now or Exit?", "youtube-dl not set Error.",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]) == 0) {
                YdlFileChooser chooser2 = new YdlFileChooser(mwi.getMainWindow(), "Path to youtube-dl");
                if (chooser2.showDiag() == 1) {
                    iniProps.setProperty("youtube-dl.path.prefix", chooser2.getPath());
                    prefix = iniProps.getProperty("youtube-dl.path.prefix");
                } else {
                    mwi.exitProgram();
                }
            } else {
                mwi.exitProgram();
            }

        }

        if (ffmpegPrefix.equals("NOT SET") && this.mode == 1) {
            String[] options2 = {"Set Path", "Ignore"};
            if (JOptionPane.showOptionDialog(
                    mwi.getMainWindow(),
                    "Path to ffmpeg not set.\n"
                    + "We advise installing it and setting the path.\n"
                    + "Otherwise you might run into problems "
                    + "downloading audio files."
                    + "Set Path now", "youtube-dl not set Error.",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options2, options2[1]) == 0) {

                YdlFileChooser chooser2 = new YdlFileChooser(mwi.getMainWindow(), "Path to ffmpeg");
                if (chooser2.showDiag() == 1) {
                    iniProps.setProperty("ffmpeg.path.prefix", chooser2.getPath());
                    ffmpegPrefix = iniProps.getProperty("ffmpeg.path.prefix");
                }
            }
        }

    }

    public void exec() {
        try {
            mwi.updateProgressbar(0);
            pb = new ProcessBuilder(commands);
            proc = pb.start();

        } catch (IOException ex) {
            mwi.addYdlOutLine(ex.getMessage());
            Logger.getLogger(YdlBridge.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        this.stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        String s;

        try {
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

        } catch (IOException ex) {
            mwi.addYdlOutLine(ex.getMessage());
            Logger.getLogger(YdlBridge.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        exec();
    }

}
