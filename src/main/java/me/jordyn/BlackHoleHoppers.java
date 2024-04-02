package me.jordyn;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * blackholehoppers java plugin
 */
public class BlackHoleHoppers extends JavaPlugin
{
  private static final Logger LOGGER=Logger.getLogger("blackholehoppers");

  public void onEnable()
  {
    LOGGER.info("blackholehoppers enabled");
  }

  public void onDisable()
  {
    LOGGER.info("blackholehoppers disabled");
  }
}
