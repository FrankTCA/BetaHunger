package org.infotoast.betahunger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetaHunger extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BetaHungerListener(), this);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule naturalRegeneration false");
        getServer().getConsoleSender().sendMessage("\u00a7bBetaHunger enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("\u00a7bBetaHunger Disabled.");
    }

    private class BetaHungerListener implements Listener {
        @EventHandler(priority = EventPriority.LOW)
        public void onHunger(FoodLevelChangeEvent evt) {
            if (evt.getFoodLevel() > 19) {
                int levels = getHungerLevelsFromFood(evt.getItem().getType());
                double currentHealth = evt.getEntity().getHealth();
                if (currentHealth + levels > evt.getEntity().getMaxHealth()) {
                    evt.getEntity().setHealth(evt.getEntity().getMaxHealth());
                    evt.setCancelled(true);
                    return;
                }
                evt.getEntity().setHealth(currentHealth + levels);
                evt.setCancelled(true);
                return;
            }
            evt.setCancelled(true);
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onPlayerJoin(PlayerJoinEvent evt) {
            evt.getPlayer().setFoodLevel(19);
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onPlayerWalk(PlayerMoveEvent evt) {
            evt.getPlayer().setFoodLevel(19);
        }

        protected static int getHungerLevelsFromFood(Material mat) {
            switch (mat) {
                case APPLE:
                case CHORUS_FRUIT:
                case GOLDEN_APPLE:
                case ENCHANTED_GOLDEN_APPLE:
                    return 4;
                case BAKED_POTATO:
                case BREAD:
                case COOKED_COD:
                case COOKED_RABBIT:
                    return 5;
                case BEETROOT:
                case DRIED_KELP:
                case POTATO:
                case PUFFERFISH:
                case TROPICAL_FISH:
                    return 1;
                case BEETROOT_SOUP:
                case COOKED_CHICKEN:
                case COOKED_MUTTON:
                case COOKED_SALMON:
                case GOLDEN_CARROT:
                case HONEY_BOTTLE:
                case MUSHROOM_STEW:
                case SUSPICIOUS_STEW:
                    return 6;
                case CAKE:
                case COOKIE:
                case GLOW_BERRIES:
                case MELON_SLICE:
                case POISONOUS_POTATO:
                case CHICKEN:
                case COD:
                case MUTTON:
                case SALMON:
                case SPIDER_EYE:
                case SWEET_BERRIES:
                    return 2;
                case CARROT:
                case BEEF:
                case PORKCHOP:
                case RABBIT:
                    return 3;
                case COOKED_PORKCHOP:
                case PUMPKIN_PIE:
                case COOKED_BEEF:
                    return 8;
                case RABBIT_STEW:
                    return 10;
                default:
                    return 0;
            }
        }
    }
}
