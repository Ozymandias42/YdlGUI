package logic;

import java.util.Locale;
import java.util.ResourceBundle;

public class LangHandler implements LangHandlerInterface {

    private ResourceBundle rb;
    private Locale lc;

    private static LangHandler lh;

    private LangHandler() {
        System.out.println("Loading Language File.");
        IniHandler ini;
        String lang;
        String country = "";
        try {
            ini = IniHandler.getIni();
            try {
                lang = ini.getProperty("custom.language");
                try {
                    country = ini.getProperty("custom.country");
                } catch (Exception e1) {
                    country = "";
                }
            } catch (Exception e2) {
                lang = "";
            }
            this.lc = new Locale(lang, country);
        } catch (Exception e) {
            System.out.println("Couln't load Ini-File. Using default values.");
            this.lc = new Locale("", "");
        }

        try {
            this.rb = ResourceBundle.getBundle("lang" + GlobalVariable.sep() + "lang", this.lc);
        } catch (Exception e) {
            System.out.println("Failed loading Language File. "
                    + "\nGracefully shutting down."
                    + "\nPlease debug.");
            System.exit(404);
        }
    }

    /**
     * Factory method of LangHandler. Creates Singleton.
     *
     * @return 0 if instance exists 1 otherwise.
     */
    public static int init() {
        if (LangHandler.lh == null) {
            LangHandler.lh = new LangHandler();
            return 1;
        } else {
            return 0;
        }
    }

    private String getValue1(String key) {

        if (rb.getString(key) != null) {
            return rb.getString(key);
        } else {
            return "KEY [" + key + "] NOT SET";
        }
    }

    public static String getValue(String key) {
        if (LangHandler.lh == null) {
            LangHandler.init();
        }
        return lh.getValue1(key);
    }
}
