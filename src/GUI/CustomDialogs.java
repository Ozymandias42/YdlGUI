package GUI;
import logic.LangHandler;
import javax.swing.*;
import logic.GlobalParameters;

public abstract class CustomDialogs {

    public static void firstTime(){
        JOptionPane.showMessageDialog(MainWindowHandler.getMWI().getMainWindow(),
                LangHandler.getValue("diag.ini-creation"),
                LangHandler.getValue("diag.ini-creation.title"),
                JOptionPane.INFORMATION_MESSAGE);
    }
    public static YdlFileChooser ydlLocation(){
        String text;
        if(GlobalParameters.isWin()){text = LangHandler.getValue("diag.chse.ydl.win");}
        else{text = LangHandler.getValue("diag.chse.ydl");}
        YdlFileChooser chooser = new YdlFileChooser(MainWindowHandler.getMWI().getMainWindow(),
                    text);
        return chooser;
    }
    public static YdlFileChooser ydlLocation(JFrame parent){
        String text;
        if(GlobalParameters.isWin()){text = LangHandler.getValue("diag.chse.ydl.win");}
        else{text = LangHandler.getValue("diag.chse.ydl");}
        YdlFileChooser chooser = new YdlFileChooser(parent,
                    text);
        return chooser;
    }
    public static YdlFileChooser ffmpegLocation(){
        String text;
        if(GlobalParameters.isWin()){text = LangHandler.getValue("diag.chse.ffmpeg.win");}
        else{text = LangHandler.getValue("diag.chse.ffmpeg");}
        //YdlFileChooser chooser2 = new YdlFileChooser(MainWindowHandler.getMWI().getMainWindow(),text);
        YdlFileChooser chooser2 = new YdlFileChooser(null,
                text);
        return chooser2;
    }
    public static YdlFileChooser ffmpegLocation(JFrame parent){
        String text;
        if(GlobalParameters.isWin()){text = LangHandler.getValue("diag.chse.ffmpeg.win");}
        else{text = LangHandler.getValue("diag.chse.ffmpeg");}
        YdlFileChooser chooser2 = new YdlFileChooser(parent,
                    text);
        return chooser2;
    }
    public static void warningYDLLocation(){
        MainWindowHandler mwh = MainWindowHandler.getMWI();
        String text;
        if(GlobalParameters.isWin()){text = LangHandler.getValue("warning.ydl-not-found.win");}
        else{text = LangHandler.getValue("warning.ydl-not-found");}
        JOptionPane.showMessageDialog(mwh.getMainWindow(), text + System.getProperty("user.home")
                        + System.getProperty("file.separator")
                        + "YDLGUI-settings.ini", LangHandler.getValue("warning.config.title"),
                        JOptionPane.ERROR_MESSAGE);
    }
    public static void warningFfmpegLocation(){
        MainWindowHandler mwh = MainWindowHandler.getMWI();
        String text;
        if(GlobalParameters.isWin()){text = LangHandler.getValue("warning.ffmpeg-not-found.win");}
        else{text = LangHandler.getValue("warning.ffmpeg-not-found");}
        
        JOptionPane.showMessageDialog(mwh.getMainWindow(), text + System.getProperty("user.home")
                + System.getProperty("file.separator")
                + "YDLGUI-settings.ini", LangHandler.getValue("warning.config.title"),
                JOptionPane.ERROR_MESSAGE);   
    }
    public static int errorYdlNotSet(){
        MainWindowHandler mwi = MainWindowHandler.getMWI();
        String o1,o2,txt,ttl;
        o1 = LangHandler.getValue("error.ydl-not-found-o1");
        o2 = LangHandler.getValue("error.ydl-not-found-o2");
        txt = LangHandler.getValue("error.ydl-not-found-txt");
        ttl = LangHandler.getValue("error.ydl-not-found-ttl");
        
        String[] options = {o1, o2};
        
        return JOptionPane.showOptionDialog(
                    mwi.getMainWindow(),
                    txt, ttl,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
    }
    public static int errorFfmpegNotSet(){
        MainWindowHandler mwi = MainWindowHandler.getMWI();
        String o1,o2,txt,ttl;
        o1 = LangHandler.getValue("error.ffmpeg-not-found-o1");
        o2 = LangHandler.getValue("error.ffmpeg-not-found-o2");
        txt = LangHandler.getValue("error.ffmpeg-not-found-txt");
        ttl = LangHandler.getValue("error.ffmpeg-not-found-ttl");
        String[] options = {o1, o2};
        return JOptionPane.showOptionDialog(
                    mwi.getMainWindow(),
                    txt, ttl,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
    }
    
}
