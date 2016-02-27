package logic;

public class GlobalVariable{
        String sep, userHome;
        Boolean win;
        static GlobalVariable gb;

        private GlobalVariable(){
            this.userHome = System.getProperty("user.home");
            this.sep      = System.getProperty("file.separator");
            this.win      = "\\".equals(this.sep);
        }

        public static void init(){
            if(GlobalVariable.gb == null){
                GlobalVariable.gb = new GlobalVariable();
            }
        }
        public static Boolean isWin(){
            return GlobalVariable.gb.win;
        }
        public static String userHome(){
            return GlobalVariable.gb.userHome;
        }
        public static String sep(){
            return GlobalVariable.gb.sep;
        }
        }