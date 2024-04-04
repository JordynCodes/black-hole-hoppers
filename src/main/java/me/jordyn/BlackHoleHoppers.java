package me.jordyn;

import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import me.jordyn.commands.GiveCommand;
import me.jordyn.configs.HopperData;
import me.jordyn.listeners.BreakHopperListener;
import me.jordyn.listeners.ColllectItemsListener;
import me.jordyn.listeners.PlaceHopperListener;

public class BlackHoleHoppers extends JavaPlugin{
  private static final Logger LOGGER=Logger.getLogger("blackholehoppers");
  private static BlackHoleHoppers plugin;

  public void onEnable(){

    // config.yml
    getConfig().options().copyDefaults();
    saveDefaultConfig();

    // hopper_data.yml
    HopperData.setup();
    HopperData.getHopperDataFile().options().copyDefaults(true);
    HopperData.saveHopperDataFile();

    plugin = this;

    LOGGER.info("blackholehoppers enabled");
    getCommand("gethopper").setExecutor(new GiveCommand());
    getServer().getPluginManager().registerEvents(new PlaceHopperListener(plugin), plugin);
    getServer().getPluginManager().registerEvents(new BreakHopperListener(plugin), plugin);
    getServer().getPluginManager().registerEvents(new ColllectItemsListener(), plugin);

  }

  public ItemStack getHopper(){
        ItemStack hopper = new ItemStack(Material.HOPPER);
        ItemMeta hopperMeta = hopper.getItemMeta();
        PersistentDataContainer data = hopperMeta.getPersistentDataContainer();
        data.set(new NamespacedKey(plugin, "message"), PersistentDataType.STRING, "you placed a black hole hopper");
        hopper.setItemMeta(hopperMeta);
        return hopper;
  }

  public void onDisable(){
    LOGGER.info("blackholehoppers disabled");
  }

  public static BlackHoleHoppers getPlugin() {
    return plugin;
  }

}
