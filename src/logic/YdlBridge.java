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
 * @deprecated
 */
import GUI.*;
import java.io.File;

public class YdlBridge extends Thread {

    //private final String[] commands;
    private final String dURL;
    private final String targetDir;
    private String[] commands;
    private final int mode;
    private ProcessBuilder pb;
    private Process proc;
    private String prefix;
    private String ffmpegPrefix;
    private final MainWindowHandlerInterface mwi;
    private BufferedReader stdInput;
    private BufferedReader stdError;
    private String sep, userHome;

    public YdlBridge(int mode, String dURL) {
        this.mwi = MainWindowHandler.getMWI();
        this.dURL = dURL;
        this.mode = mode;
        setPrefixes();
        File currDir = new File(userHome + sep);
        if (IniHandler.getIni().getProperty("default-download.path") == null) {
            YdlFileChooser chooser = new YdlFileChooser(mwi.getMainWindow());

            chooser.setCurrentDirectory(currDir);
            if (chooser.showDiag() == 1) {
                this.targetDir = chooser.getPath();
            } else {
                this.targetDir = userHome + sep + "Downloads";
            }
        } else {
            this.targetDir = IniHandler.getIni().getProperty("default-download.path");
        }
        buildCommand();

    }

    public YdlBridge(int mode, String dURL, String dwnDst) {
        this.mwi = MainWindowHandler.getMWI();
        this.dURL = dURL;
        this.mode = mode;
        setPrefixes();
        this.targetDir = dwnDst;
        buildCommand();
    }

    private void buildCommand() {

        if (mode == 0) {
            String[] bob = {prefix + sep + "youtube-dl", "-f", "mp4", dURL, "-o", targetDir + sep + "%(title)s.%(ext)s"};
            this.commands = bob;

        } else {
            if ("NOT SET".equals(ffmpegPrefix)) {
                String[] bob = {prefix + sep + "youtube-dl", "-f", "m4a", dURL, "-o",
                    targetDir + sep + "%(title)s.%(ext)s"};
                this.commands = bob;
            } else {
                String[] bob = {prefix + sep + "youtube-dl", "--ffmpeg-location", ffmpegPrefix + sep, "-f", "m4a", dURL, "-o",
                    targetDir + sep + "%(title)s.%(ext)s"};
                this.commands = bob;
            }
        }
    }

    private void setPrefixes() {
        this.sep = GlobalParameters.sep();
        this.userHome = GlobalParameters.userHome();
        IniHandler iniProps = IniHandler.getIni();
        this.prefix = iniProps.getProperty("youtube-dl.path.prefix");
        this.ffmpegPrefix = iniProps.getProperty("ffmpeg.path.prefix");
        this.sep = System.getProperty("file.separator");

        if (prefix.equals("NOT SET")) {
            if (CustomDialogs.errorYdlNotSet() == 0) {
                YdlFileChooser chooser2 = CustomDialogs.ydlLocation();
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

            if (CustomDialogs.errorFfmpegNotSet() == 0) {

                YdlFileChooser chooser2 = CustomDialogs.ffmpegLocation();
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
