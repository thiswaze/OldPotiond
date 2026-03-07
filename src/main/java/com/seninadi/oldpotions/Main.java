package com.seninadi.oldpotions;

import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("OldPotions aktif - potionlar artik eski gibi ucuyor!");
    }

    @EventHandler
    public void onPotionThrow(ProjectileLaunchEvent e) {
        if (!(e.getEntity() instanceof ThrownPotion potion)) return;
        if (!(potion.getShooter() instanceof org.bukkit.entity.Player player)) return;

        Vector dir = player.getLocation().getDirection().normalize();
        double speed = 0.5;
        double upward = 0.3;

        Vector velocity = dir.multiply(speed).setY(dir.getY() * 0.5 + upward);
        
        potion.setVelocity(velocity);
    }
}
