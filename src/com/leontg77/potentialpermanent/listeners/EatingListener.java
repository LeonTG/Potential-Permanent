package com.leontg77.potentialpermanent.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.leontg77.potentialpermanent.Utils;

/**
 * Eating listener class.
 * 
 * @author LeonTG77
 */
public class EatingListener implements Listener {
	
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		
		if (item == null) {
			return;
		}
		
		if (item.getType() != Material.GOLDEN_APPLE) {
			if (item.getType() == Material.MILK_BUCKET) {
				player.sendMessage(ChatColor.RED + "You cannot drink milk in PotentialPermanent.");
				event.setItem(new ItemStack (Material.AIR));
				event.setCancelled(true);
			}
			return;
		}
		
		float absHearts = Utils.getAbsHearts(player);
		
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
        player.getWorld().playSound(player.getLocation(), Sound.BURP, 1, 1);
        player.setSaturation(player.getSaturation() + 9.6f);
        player.setFoodLevel(player.getFoodLevel() + 4);
		event.setCancelled(true);
        
		if (player.getItemInHand().getAmount() == 1) {
			player.setItemInHand(new ItemStack (Material.AIR));
		} else {
			ItemStack itemInHand = player.getItemInHand();
			itemInHand.setAmount(itemInHand.getAmount() - 1);
			player.setItemInHand(itemInHand);
		}
		
		if (absHearts != 0) {
			player.setMaxHealth(absHearts >= 4 ? player.getMaxHealth() + 4 : player.getMaxHealth() + absHearts);
			Utils.setAbsHearts(player, absHearts >= 4 ? absHearts - 4 : 0);
		}
	}
}