package logic;

public class GlobalParameters{
        String sep, userHome;
        Boolean win;
        static GlobalParameters gb;

        private GlobalParameters(){
            this.userHome = System.getProperty("user.home");
            this.sep      = System.getProperty("file.separator");
            this.win      = "\\".equals(this.sep);
        }

        public static void init(){
            if(GlobalParameters.gb == null){
                GlobalParameters.gb = new GlobalParameters();
            }
        }
        public static GlobalParameters getInstance(){return gb;}
        public static Boolean isWin(){
            return GlobalParameters.gb.win;
        }
        public static String userHome(){
            return GlobalParameters.gb.userHome;
        }
        public static String sep(){
            return GlobalParameters.gb.sep;
        }
        }