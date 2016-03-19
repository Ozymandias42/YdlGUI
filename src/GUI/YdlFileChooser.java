/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import logic.GlobalParameters;
import logic.LangHandler;

/**
 *
 * @author Ozymandias42
 */
public class YdlFileChooser extends JFileChooser {
    private JFrame parent;
    
    public YdlFileChooser(JFrame parent){
        this(parent,LangHandler.getValue("fchser.default-titel"));
    }
    
    public YdlFileChooser(JFrame parent, String title){
        super();
        setDialogTitle(title);
        this.parent=parent;
        //setDialogType(JFileChooser.SAVE_DIALOG);
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //showSaveDialog(this.parent);
    }
    
    public String getPath(){
        String path = this.getSelectedFile().getAbsolutePath();
        if("/".equals(GlobalParameters.sep())){
            int newEnd = path.lastIndexOf("/");
            path = path.substring(0, newEnd);
        }
        else{}
        
        return path;
        }
    
    public int showDiag(){
        int returnVal = showSaveDialog(this.parent);
        if(returnVal != APPROVE_OPTION){
            return 0;
        }
        else{return 1;}
    }
    
       
}
