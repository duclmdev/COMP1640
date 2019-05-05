package edu.fpt.comp1640.config;

import edu.fpt.comp1640.Main;

import java.io.IOException;

public class Config {
    private static Ini ini;

    static {
        try {
            ini = new Ini(Main.class.getResource("config.ini").getPath());
        } catch (IOException e) {
            ini = new Ini() {{
                put("GENERAL", "upload_dir", "/upload");
            }};
        }
    }

    public static Ini getConfig() {
        return ini;
    }
}
