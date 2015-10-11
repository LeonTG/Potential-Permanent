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

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("pp.manage")) {
			sender.sendMessage(ChatColor.RED + "You can't use this command.");
			return true;
		}
		
		if (args.length == 0) {
			sender.sendMessage(Main.prefix() + "Usage: /pp <enable|disable>");
			return true;
		}
		
		switch (args[0]) {
		case "enable":
			if (Main.enabled) {
				sender.sendMessage(Main.prefix() + "PotentialPermanent is already enabled.");
				return true;
			}
			
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				online.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1726272000, 4));
				online.setMaxHealth(20.0);
				
				online.sendMessage(Main.prefix() + "PotentialPermanent has been enabled.");
			}
			
			Bukkit.getPluginManager().registerEvents(new EatingListener(), Main.plugin);
			Main.enabled = true;
			break;
		case "disable":
			if (!Main.enabled) {
				sender.sendMessage(Main.prefix() + "PotentialPermanent is not enabled.");
				return true;
			}
			
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				online.removePotionEffect(PotionEffectType.ABSORPTION);
				online.setMaxHealth(20.0);
				
				online.sendMessage(Main.prefix() + "PotentialPermanent has been disabled.");
			}
			
			HandlerList.unregisterAll(Main.plugin);
			Main.enabled = false;
			break;
		default: 
			sender.sendMessage(Main.prefix() + "Usage: /pp <enable|disable>");
		}
		return true;
	}
}