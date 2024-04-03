package me.jordyn.configs;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class HopperData {
    
    private static File file;
    private static FileConfiguration hopperDataFile;
    private static final Logger LOGGER=Logger.getLogger("blackholehoppers");

    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("blackholehoppers").getDataFolder(), "hopper_data.yml");
        if (!file.exists()){
            try{
                file.createNewFile();
            } catch (IOException e){
                // oof
            }
        }

        hopperDataFile = YamlConfiguration.loadConfiguration(file);

    }

    public static FileConfiguration getHopperDataFile(){
        return hopperDataFile;
    }

    public static void saveHopperDataFile() {
        try {
            hopperDataFile.save(file);
        } catch (IOException e) {
            LOGGER.info("An IOException occurred while saving the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void reloadHopperDataFile(){
        hopperDataFile = YamlConfiguration.loadConfiguration(file);
    }

}