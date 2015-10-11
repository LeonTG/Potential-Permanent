package com.leontg77.potentialpermanent;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Utilities class
 * 
 * @author ghowden
 */
public class Utils {
	protected static Method getHandleMethod;
    protected static Method getAbsorptionMethod;
    protected static Method setAbsorptionMethod;

    /**
     * Setup the utils class for saving the methods.
     */
    protected static void ensureNms() {
        // already set up
        if (setAbsorptionMethod != null) return;

        try {
            // grab the version number from CraftServer implementation
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            String version = packageName.substring(packageName.lastIndexOf(".") + 1);

            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            getHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");

            Class<?> entityHumanClass = Class.forName("net.minecraft.server." + version + ".EntityHuman");
            getAbsorptionMethod = entityHumanClass.getDeclaredMethod("getAbsorptionHearts");
            setAbsorptionMethod = entityHumanClass.getDeclaredMethod("setAbsorptionHearts", float.class);
        } catch (ReflectiveOperationException ex) {
            throw new UnsupportedOperationException("Version of bukkit/spigot is unsupported", ex);
        }
    }

    /**
     * Set the absorption hearts for the given player.
     * 
     * @param player The player setting.
     * @param value The new amount of absorption hearts.
     */
    public static void setAbsHearts(Player player, float value) {
        ensureNms();

        try {
            setAbsorptionMethod.invoke(getHandleMethod.invoke(player), value);
        } catch (ReflectiveOperationException ex) {
            throw new UnsupportedOperationException("Error setting absorption hearts on player", ex);
        }
    }

    /**
     * Get the absorption hearts for the given player.
     * 
     * @param player The player getting.
     * @return The player's absorption hearts.
     */
    public static float getAbsHearts(Player player) {
        ensureNms();

        try {
            return (float) getAbsorptionMethod.invoke(getHandleMethod.invoke(player));
        } catch (ReflectiveOperationException ex) {
            throw new UnsupportedOperationException("Error getting absorption hearts of player", ex);
        }
    }
}
