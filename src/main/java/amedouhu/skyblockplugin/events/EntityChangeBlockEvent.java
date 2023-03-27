package amedouhu.skyblockplugin.events;

import amedouhu.skyblockplugin.apis.IsSkyBlock;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class EntityChangeBlockEvent implements Listener {
    IsSkyBlock isSkyBlock = new IsSkyBlock();
    @EventHandler
    public void onBlockFall(org.bukkit.event.entity.EntityChangeBlockEvent e) {
        Block block = e.getBlock();
        Location location = new Location(block.getWorld(),block.getX(),block.getY(),block.getZ());
        for (int i=1;i<=5;i++) {
            String locationString = location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ();
            if (isSkyBlock.main(locationString)) {
                e.setCancelled(true);
                break;
            }
        }
    }
}
