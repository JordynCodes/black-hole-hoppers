package me.jordyn.listeners;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import me.jordyn.BlackHoleHoppers;
import me.jordyn.configs.HopperData;

public class PlaceHopperListener implements Listener{

    private final BlackHoleHoppers plugin;

    public PlaceHopperListener(BlackHoleHoppers plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onHopperPlace(BlockPlaceEvent e){
        
        PersistentDataContainer data = e.getItemInHand().getItemMeta().getPersistentDataContainer();

        if (!data.has((new NamespacedKey(plugin, "message")), PersistentDataType.STRING)){
            return;
        }

        Block block = e.getBlock();
        Location location = new Location(block.getWorld(), block.getX(), block.getY(), block.getZ());
        String key = getLocationKey(location);
        HopperData.getHopperDataFile().set("hoppers." + key, location);
        HopperData.saveHopperDataFile();

        String message = data.get(new NamespacedKey(BlackHoleHoppers.getPlugin(), "message"), PersistentDataType.STRING);
        e.getPlayer().sendMessage(message);

    }

    private String getLocationKey(Location location) {
        return location.getWorld().getName() + "_" + location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();
    }
    
}
