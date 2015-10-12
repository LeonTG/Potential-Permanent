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
	private static final float SATURATION_TO_ADD = 9.6f;
	private static final int FOOD_TO_ADD = 4;
	
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		
		if (item == null) {
			return;
		}

		if (item.getType() == Material.MILK_BUCKET) {
			player.sendMessage(ChatColor.RED + "You cannot drink milk in PotentialPermanent.");
			event.setItem(new ItemStack (Material.AIR));
			event.setCancelled(true);
			return;
		}
		
		if (item.getType() != Material.GOLDEN_APPLE) {
			return;
		}
		
		float absHearts = Utils.getAbsHearts(player);
		
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
        player.getWorld().playSound(player.getLocation(), Sound.BURP, 1, 1);
        player.setSaturation(player.getSaturation() + SATURATION_TO_ADD);
        player.setFoodLevel(player.getFoodLevel() + FOOD_TO_ADD);
		event.setCancelled(true);
		
		item.setAmount(1);
		player.getInventory().removeItem(item);
		
		if (absHearts != 0) {
			float toTake = Math.min(4, absHearts);
			
			player.setMaxHealth(player.getMaxHealth() + toTake);
			Utils.setAbsHearts(player, absHearts - toTake);
		}
	}
}