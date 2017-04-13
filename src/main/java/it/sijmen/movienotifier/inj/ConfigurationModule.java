package it.sijmen.movienotifier.inj;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * Created by Sijmen on 7-4-2017.
 */
public class ConfigurationModule extends AbstractModule {

    private final String configFileName = "movie-notifier.properties";

    @Override
    protected void configure() {

        try {
            File f;
            if(runningFromJar())
                f = new File(configFileName);
            else
                f = new File(getClass().getResource("/"+configFileName).toURI());

            if(!f.exists())
                throw new IllegalArgumentException("Configuration file could not be found. Check if the file "+f.getAbsolutePath()+" is available.");

            Properties properties = new Properties();
            properties.load(new FileReader(f));
            Names.bindProperties(binder(), properties);
        } catch (Exception e) {
            throw new IllegalArgumentException("Configuration file could not be loaded. Check if the file "+configFileName+" is available and valid. Error message: " + e.getMessage(), e);
        }
    }

    public static String getJarName() {
        return new File(ConfigurationModule.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getName();
    }

    public static boolean runningFromJar() {
        return getJarName().contains(".jar");
    }

}
