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
        getLogger().info("OldPotions aktif - potionlar 1.20.1 vanilla gibi ucuyor!");
    }

    @EventHandler
    public void onPotionThrow(ProjectileLaunchEvent e) {
        if (!(e.getEntity() instanceof ThrownPotion potion)) return;
        if (!(potion.getShooter() instanceof org.bukkit.entity.Player player)) return;

        // Vanilla 1.20.1 tam değerleri
        float pitch = player.getLocation().getPitch();
        float yaw = player.getLocation().getYaw();
        
        // Vanilla'daki -20F pitch offset
        float adjustedPitch = pitch - 20.0F;
        
        // Radyan'a çevir
        double pitchRad = Math.toRadians(adjustedPitch);
        double yawRad = Math.toRadians(yaw);
        
        // Vanilla velocity hesaplama (tam formül)
        double x = -Math.sin(yawRad) * Math.cos(pitchRad);
        double y = -Math.sin(pitchRad);
        double z = Math.cos(yawRad) * Math.cos(pitchRad);
        
        Vector velocity = new Vector(x, y, z);
        velocity.normalize();
        velocity.multiply(0.5); // Vanilla hız: 0.5F
        
        potion.setVelocity(velocity);
    }
}
