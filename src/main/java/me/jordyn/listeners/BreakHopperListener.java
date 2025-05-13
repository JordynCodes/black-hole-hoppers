package me.jordyn.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import me.jordyn.BlackHoleHoppers;
import me.jordyn.configs.HopperData;

public class BreakHopperListener implements Listener{

    private final BlackHoleHoppers plugin;

    public BreakHopperListener(BlackHoleHoppers plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onHopperBreak(BlockBreakEvent e){
        Block block = e.getBlock();
        Location blockLocation = new Location(block.getWorld(), block.getX(), block.getY(), block.getZ());

        ConfigurationSection hoppersSection = HopperData.getHopperDataFile().getConfigurationSection("hoppers");
        if (hoppersSection == null)
            return;

        for (String key : hoppersSection.getKeys(false)){
            if (blockLocation.equals(hoppersSection.getLocation(key))){
                HopperData.getHopperDataFile().set("hoppers." + key, null);
                HopperData.saveHopperDataFile();
                ItemStack droppedItem = plugin.getHopper();
                e.setDropItems(false);
                block.getWorld().dropItemNaturally(block.getLocation(), droppedItem);
                return;
            }
        }

    }

}
