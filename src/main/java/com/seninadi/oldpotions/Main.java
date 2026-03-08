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
        
        // 1.20.6 gerçek değerleri
        double speed = 0.80;      // 0.5'ten 0.75'e çıkardım (daha hızlı)
        double upward = 0.15;     // 0.3'ten 0.25'e düşürdüm (daha az yukarı)
        double verticalMultiplier = 0.35; // Dikey hız çarpanı

        Vector velocity = dir.multiply(speed);
        velocity.setY(dir.getY() * verticalMultiplier + upward);
        
        potion.setVelocity(velocity);
    }
}
