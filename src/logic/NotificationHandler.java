package logic;

import java.awt.*;
import java.io.IOException;

/**
 * Created by fabian on 20/03/16.
 */
public class NotificationHandler {
    private SystemTray tray;
    private final TrayIcon trayIcon;
    private final boolean isMac;
    private static NotificationHandler nh;
    private NotificationHandler(){
         this.tray = null;
         this.trayIcon = null;
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
             this.tray = SystemTray.getSystemTray();
        }
        this.isMac = System.getProperty("os.name").contains("Mac");
    }
    public static NotificationHandler getInstance(){
        if(nh == null){
            nh = new NotificationHandler();
        }
        return nh;
    }
    public void fireDownloadFinishedNotification() {
        if (this.isMac) {
            String path = getClass().getResource("OSX-Notifier").getPath();
            //System.out.println(path);
            String[] command = {path, "Download Finished", "Video Downloaded", ""};
            ProcessBuilder pb = new ProcessBuilder(command);
            try {
                pb.start();
            } catch (IOException e) {}

        }
    }
}
