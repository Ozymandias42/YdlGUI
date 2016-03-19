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

import java.util.ArrayList;

/**
 *
 * @author fabian
 */
public class SessionParameters {
    private int mode;
    private String srcURL;
    private String dwnDst;
    private final ArrayList<String> parameter;
    private static SessionParameters instance;

    
    public int getMode(){return this.mode;}
    public String getSrcURL(){return this.srcURL;}
    public String getDwnDst(){return this.dwnDst;}
    public ArrayList<String> getParameterArrayList(){
        return this.parameter;
    } 
    public static SessionParameters getInstance(){
        if(instance != null){
        return instance;
        }
        else {
            init();
            return instance;
        }
    }
    
    private SessionParameters(){
        this.parameter = new ArrayList<>();
    }
    private static void init() {
        instance = new SessionParameters();
    }
    
    public void configParameters(String url, int mode, String dwnDst){
        this.mode = mode;
        switch(mode){
            case 0 : setDownloadFormat("mp4");break;
            case 1 : setDownloadFormat("m4a");break;
            default : setDownloadFormat("bestvideo");break;
        }
        this.dwnDst = dwnDst;
        createArgs(url);
        if(this.srcURL == null){this.srcURL = "youtube.com...";}
    }
    private void setDownloadFormat(String f){
        if(this.parameter.contains("-f")){
            int i = this.parameter.indexOf("-f");
            this.parameter.set(i+1, f);
        }
        else{
            this.parameter.add("-f");
            this.parameter.add(f);
        }
    }
    private void createArgs(String s){
        String[] sarr = s.split(" ");
        ArrayList<String> als;
        als = new ArrayList<>();
        for (String t : sarr){
            if(isURL(t) && this.srcURL==null){
                this.srcURL = t;
            }
            else{
                als.add(t);
            }
        }
        
        if(als.contains("-f")){
            String format;
            format = als.get(als.indexOf("-f")+1);
            setDownloadFormat(format);
            als.remove(als.indexOf("-f")+1);
            als.remove(als.indexOf("-f"));
        }
        als.stream().forEach((item) -> {
            this.parameter.add(item);
        });
        
    }
    private boolean isURL(String s){
        return s.matches("((http)|(https))://(.+)");
    }
    
    
}
