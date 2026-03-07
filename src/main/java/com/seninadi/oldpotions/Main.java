package com.seninadi.oldpotions;

import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener {

    // Config değerleri - istersen config.yml'den de okuyabiliriz
    private double horizontalSpeed = 0.7;    // Yatay hız
    private double verticalBoost = 0.25;     // Yukarı kalkma
    private double minVerticalSpeed = -0.15; // Alta bakınca minimum Y hızı
    private double maxVerticalSpeed = 0.4;   // Yukarı bakınca maximum Y hızı

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("OldPotions aktif - NethPot tarzı pot mekaniği!");
    }

    @EventHandler
    public void onPotionThrow(ProjectileLaunchEvent e) {
        if (!(e.getEntity() instanceof ThrownPotion potion)) return;
        if (!(potion.getShooter() instanceof Player player)) return;

        // Oyuncunun baktığı yön
        Vector direction = player.getLocation().getDirection();
        float pitch = player.getLocation().getPitch();
        
        // Yatay velocity (X ve Z)
        Vector horizontal = direction.clone();
        horizontal.setY(0);
        horizontal.normalize();
        horizontal.multiply(horizontalSpeed);
        
        // Dikey velocity (Y) - pitch'e göre sınırlandırılmış
        double verticalComponent;
        
        if (pitch > 45) {
            // Alta bakıyorsa - pot çok aşağı gitmesin
            verticalComponent = Math.max(direction.getY() * 0.4, minVerticalSpeed);
        } else if (pitch < -15) {
            // Yukarı bakıyorsa - pot çok yukarı gitmesin
            verticalComponent = Math.min(direction.getY() * 0.5 + verticalBoost, maxVerticalSpeed);
        } else {
            // Normal bakış açısı - düz gidiyor
            verticalComponent = direction.getY() * 0.35 + verticalBoost;
        }
        
        // Final velocity
        Vector velocity = horizontal;
        velocity.setY(verticalComponent);
        
        potion.setVelocity(velocity);
    }
}
