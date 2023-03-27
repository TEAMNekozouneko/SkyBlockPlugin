package amedouhu.skyblockplugin.events;

import amedouhu.skyblockplugin.apis.IsSkyBlock;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class BlockBurnEvent implements Listener {
    // 燃焼によるスカイブロックの消失をキャンセルする
    IsSkyBlock isSkyBlock = new IsSkyBlock();
    @EventHandler
    public void onBlockBurn(org.bukkit.event.block.BlockBurnEvent e) {
        // 燃えたブロックを取得
        Block block = e.getBlock();
        Location location = block.getLocation();
        int x = (int) location.getX();
        int y = (int) location.getY();
        int z = (int) location.getZ();
        location = new Location(location.getWorld(), x, y, z);
        if (isSkyBlock.main(Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ())) {
            // 破壊されたブロックがスカイブロックなら
            e.setCancelled(true);
        }
    }
}
