package com.leontg77.potentialpermanent.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.leontg77.potentialpermanent.Main;
import com.leontg77.potentialpermanent.listeners.EatingListener;

/**
 * PP command class
 * 
 * @author LeonTG77
 */
public class PPCommand implements CommandExecutor {
	private boolean enabled = false;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("pp.manage")) {
			sender.sendMessage(ChatColor.RED + "You can't use this command.");
			return true;
		}
		
		if (args.length == 0) {
			sender.sendMessage(Main.PREFIX + "Usage: /pp <enable|disable>");
			return true;
		}
		
		String enable = Main.PREFIX + "PotentialPermanent has been enabled.";
		String disable = Main.PREFIX + "PotentialPermanent has been disabled.";
		
		if (args[0].equalsIgnoreCase("enable")) {
			if (enabled) {
				sender.sendMessage(Main.PREFIX + "PotentialPermanent is already enabled.");
				return true;
			}
			
			PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, Short.MAX_VALUE, 4);
			
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				online.addPotionEffect(effect);
				online.setMaxHealth(20.0);
				
				online.sendMessage(enable);
			}
			
			Bukkit.getPluginManager().registerEvents(new EatingListener(), Main.plugin);
			enabled = true;
		} else if (args[0].equalsIgnoreCase("disable")) {
			if (!enabled) {
				sender.sendMessage(Main.PREFIX + "PotentialPermanent is not enabled.");
				return true;
			}
			
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				online.removePotionEffect(PotionEffectType.ABSORPTION);
				online.setMaxHealth(20.0);
				
				online.sendMessage(disable);
			}
			
			HandlerList.unregisterAll(Main.plugin);
			enabled = false;
		} else {
			sender.sendMessage(Main.PREFIX + "Usage: /pp <enable|disable>");
		}
		return true;
	}
}