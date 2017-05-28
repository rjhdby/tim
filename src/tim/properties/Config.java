package tim.properties;

import tim.Main;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

public class Config {
    private static Properties properties;
    private static String filename = "probes.properties";
    private static String debug = "C:\\Users\\rjhdby\\IdeaProjects\\tim\\probes.properties";

    private static void init() {
        properties = new Properties();
        try {
            String url = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent() + "/" + filename;
            properties.load(new FileInputStream(url));
//            properties.load(new FileInputStream(debug));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Properties file not found");
            System.exit(1);
        }
    }

    public static String get(String name) {
        if (properties == null) {
            init();
        }
        return properties.getProperty(name);
    }
}
