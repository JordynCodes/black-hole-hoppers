package me.jordyn;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Hopper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import me.jordyn.commands.GiveCommand;
import me.jordyn.configs.HopperData;
import me.jordyn.listeners.BreakHopperListener;
import me.jordyn.listeners.CollectItemsListener;
import me.jordyn.listeners.PlaceHopperListener;
import me.jordyn.tasks.CollectItemsTask;

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
    getServer().getPluginManager().registerEvents(new CollectItemsListener(), plugin);
    new CollectItemsTask(this).runTaskTimer(this, 0L, 20L);

  }

  public ItemStack getHopper(){

    ItemStack hopper = new ItemStack(Material.HOPPER);
    ItemMeta hopperMeta = hopper.getItemMeta();
    PersistentDataContainer data = hopperMeta.getPersistentDataContainer();
    data.set(new NamespacedKey(plugin, "message"), PersistentDataType.STRING, "you placed a black hole hopper");
    hopper.setItemMeta(hopperMeta);
    return hopper;

  }

  // checks if a hopper is a vanilla hopper or a plugin hopper by comparing its location from config
  public Boolean isBlackHoleHopper(Location hopperLocation){

    ConfigurationSection hoppersSection = HopperData.getHopperDataFile().getConfigurationSection("hoppers");
    if (hoppersSection == null)
        return false;

    for (String key : hoppersSection.getKeys(false)){
      if (hopperLocation.equals(hoppersSection.getLocation(key)))
        return true;
    }
    
    return false;

  }

  public Boolean isHopperFull(Hopper hopper) {
    for (ItemStack hopperSlot : hopper.getInventory().getContents()) {

        if (hopperSlot == null || hopperSlot.getType().isAir())
            return false;

        if (hopperSlot.getAmount() < hopperSlot.getMaxStackSize())
            return false;

    }
    return true;
  }

  public Boolean canHopperAcceptItem(Hopper hopper, ItemStack itemStack) {
    for (ItemStack hopperSlot : hopper.getInventory().getContents()) {

        if (hopperSlot == null || hopperSlot.getType().isAir()) 
            return true;

        if (hopperSlot.getType() == itemStack.getType() && hopperSlot.getAmount() < itemStack.getMaxStackSize()) 
            return true;

    }
    return false;
  }


  public void onDisable(){
    LOGGER.info("blackholehoppers disabled");
  }

  public static BlackHoleHoppers getPlugin() {
    return plugin;
  }

}
