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
import java.io.IOException;
import java.util.ArrayList;

/**
 * Prepares the youtube-dl process for execution. 
 * Configures system-, user- and session-specific parameters such as 
 * separator-sign / or \, path to the executable i.e. /usr/local/bin 
 * and session specific parameters such as download URL, 
 * choosen destination folder or additional parameters.
 * @author fabian
 */
public class CustomProcessBuilder {
    private final SessionParameters sp;
    private final IniHandler ini;
    private final GlobalParameters gp;
    private final  ArrayList<String> cmdString;
    private  ArrayList<String> parameterArrayList;
    
    private static CustomProcessBuilder cpb;
    
    private CustomProcessBuilder(){
        this.sp = SessionParameters.getInstance();
        this.ini = IniHandler.getIni();
        this.gp = GlobalParameters.getInstance();
        this.cmdString = new ArrayList<>();
        this.parameterArrayList = sp.getParameterArrayList();
    }
    public static CustomProcessBuilder getInstance(){
        if(cpb==null){
            cpb = new CustomProcessBuilder();
        }
        return cpb;
    }
    public ProcessBuilder build() throws IOException{
        ProcessBuilder pb;
        //clears Array from leftover content from earlier calls
        //to ensure correct building of command string.
        this.cmdString.clear();
        
        //sets current instance of ParameterArrayList
        this.parameterArrayList = sp.getParameterArrayList();
        
        //obligatory first parameter
        this.cmdString.add(ini.getProperty("youtube-dl.path.prefix")+gp.sep+"youtube-dl");
        
        int mode = sp.getMode();
        switch(mode){
            case 0: buildVideoDownload();break;
            case 1: buildAudioDownload();break;
            default: buildVideoDownload();break;
        }
        

        pb = new ProcessBuilder(this.cmdString);
        return pb;
}
    private void buildVideoDownload(){
        this.cmdString.addAll(this.parameterArrayList);
        this.cmdString.add(this.sp.getSrcURL());
        this.cmdString.add("-o");
        this.cmdString.add(this.sp.getDwnDst()+gp.sep+"%(title)s.%(ext)s");
}
    private void buildAudioDownload(){
        //IF path to ffmpeg IS set do stuff.
        if(!"NOT SET".equals(this.ini.getProperty("ffmpeg.path.prefix"))) {
            this.cmdString.add("--ffmpeg-location");
            this.cmdString.add(this.ini.getProperty("ffmpeg.path.prefix"));
        }
        else{
            MainWindowHandler.getMWI().addYdlOutLine("FFMPEG NOT SET. SHOULD BE SET\n\n");
        }
            buildVideoDownload();
    }
}
