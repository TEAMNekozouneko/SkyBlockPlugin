package amedouhu.skyblockplugin.events;

import amedouhu.skyblockplugin.apis.IsSkyBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Iterator;
import java.util.List;

public class BlockExplodeEvent implements Listener {
    // 爆発によりスカイブロックが破壊されないようにする
    IsSkyBlock isSkyBlock = new IsSkyBlock();
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        // 爆発が発生したとき
        List<Block> blocks = e.blockList();
        for (Iterator<Block> iterator = blocks.iterator(); iterator.hasNext();) {
            // 破壊されたブロックをループ
            // ブロックを取得
            Block block = iterator.next();
            Location location = block.getLocation();
            int x = (int) location.getX();
            int y = (int) location.getY();
            int z = (int) location.getZ();
            location = new Location(location.getWorld(), x, y, z);
            String locationString = location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ();
            if (isSkyBlock.main(locationString)) {
                // 破壊されたブロックがスカイブロックなら
                // リストからブロックを削除
                iterator.remove();
            }
        }
    }
}
