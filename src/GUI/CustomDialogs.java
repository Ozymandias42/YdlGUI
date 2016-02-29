package GUI;
import logic.LangHandler;
import javax.swing.*;
import logic.GlobalVariable;

public abstract class CustomDialogs {

    public static void firstTime(){
        JOptionPane.showMessageDialog(MainWindowHandler.getMWI().getMainWindow(),
                LangHandler.getValue("diag.ini-creation"),
                LangHandler.getValue("diag.ini-creation.title"),
                JOptionPane.INFORMATION_MESSAGE);
    }
    public static YdlFileChooser ydlLocation(){
        String text;
        if(GlobalVariable.isWin()){text = LangHandler.getValue("diag.chse.ydl.win");}
        else{text = LangHandler.getValue("diag.chse.ydl");}
        YdlFileChooser chooser = new YdlFileChooser(MainWindowHandler.getMWI().getMainWindow(),
                    text);
        return chooser;
    }
    public static YdlFileChooser ffmpegLocation(){
        String text;
        if(GlobalVariable.isWin()){text = LangHandler.getValue("diag.chse.ffmpeg.win");}
        else{text = LangHandler.getValue("diag.chse.ffmpeg");}
        YdlFileChooser chooser2 = new YdlFileChooser(MainWindowHandler.getMWI().getMainWindow(),
                    text);
        return chooser2;
    }
    public static void warningYDLLocation(){
        MainWindowHandler mwh = MainWindowHandler.getMWI();
        String text;
        if(GlobalVariable.isWin()){text = LangHandler.getValue("warning.ydl-not-found.win");}
        else{text = LangHandler.getValue("warning.ydl-not-found");}
        JOptionPane.showMessageDialog(mwh.getMainWindow(), text + System.getProperty("user.home")
                        + System.getProperty("file.separator")
                        + "YDLGUI-settings.ini", LangHandler.getValue("warning.config.title"),
                        JOptionPane.ERROR_MESSAGE);
    }
    public static void warningFfmpegLocation(){
        MainWindowHandler mwh = MainWindowHandler.getMWI();
        String text;
        if(GlobalVariable.isWin()){text = LangHandler.getValue("warning.ffmpeg-not-found.win");}
        else{text = LangHandler.getValue("warning.ffmpeg-not-found");}
        JOptionPane.showMessageDialog(mwh.getMainWindow(), "Path to "
                        + "ffmpeg should be set.\n"
                        + "Otherwise files downloaded by the audio only option"
                        + " might be unplayable by some players. \n"
                        + "Setting path to ffmpeg to a default value.\n"
                        + "Please change it "
                        + "to the correct path later on. \n"
                        + "File resides in: " + System.getProperty("user.home")
                        + System.getProperty("file.separator")
                        + "YDLGUI-settings.ini", LangHandler.getValue("warning.config.title"),
                        JOptionPane.ERROR_MESSAGE);
    }
}
