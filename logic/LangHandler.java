package logic;

import java.util.Locale;
import java.util.ResourceBundle;

public class LangHandler implements LangHandlerInterface{
    private ResourceBundle rb;
    private Locale lc;
    
    private static LangHandler lh;

    private LangHandler(){
        try{
            this.lc = new Locale("en", "");
            this.rb = ResourceBundle.getBundle(FILE, this.lc);
        }
        catch(Exception e){}
    }
    /**Factory method of LangHandler. Creates Singleton.
     * @return 0 if instance exists 1 otherwise.*/
    public static int init(){
        if(LangHandler.lh != null){
        LangHandler.lh = new LangHandler();
        return 1;
        }
        else{return 0;}
    }
    private String getValue1(String key){
        if(rb.getString(key)!= null){return rb.getString(key);}
        else{return "KEY ["+key+"] NOT SET";}
    }
    public static String getValue(String key){
        return lh.getValue1(key);
    }
}