package com.leontg77.potentialpermanent;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.leontg77.potentialpermanent.cmds.PPCommand;

/**
 * Main class of the UHC plugin.
 * <p>
 * This class contains methods for prefixes, adding recipes, enabling and disabling.
 * 
 * @author LeonTG77
 */
public class Main extends JavaPlugin {
	private Logger logger = getLogger();
	public static Main plugin;
	
	public static final String PREFIX = "§e§lPP §8» §7";
	
	@Override
	public void onDisable() {
		PluginDescriptionFile file = getDescription();
		logger.info(file.getName() + " is now disabled.");
		plugin = null;
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile file = getDescription();
		logger.info(file.getName() + " v" + file.getVersion() + " is now enabled.");
		plugin = this;
		
		getCommand("pp").setExecutor(new PPCommand());
		Utils.ensureNms();
	}
}