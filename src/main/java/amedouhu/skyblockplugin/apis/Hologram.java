package amedouhu.skyblockplugin.apis;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Hologram {
    // ホログラムのテキストを編集する
    public void main(Player player, Location location,String hologramText) {
        Location hologramLocation = new Location(location.getWorld(),location.getX() + 0.5, location.getY() + 2,location.getZ()+0.5);
        for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
            if (entity instanceof ArmorStand && entity.getLocation().equals(hologramLocation)) {
                entity.setCustomName(hologramText);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (Objects.requireNonNull(entity.getCustomName()).equals(hologramText)) {
                            entity.setCustomName(ChatColor.BLUE + "§l§oSKYBLOCK");
                        }
                    }
                }, 1000);
                break;
            }
        }
    }
}
