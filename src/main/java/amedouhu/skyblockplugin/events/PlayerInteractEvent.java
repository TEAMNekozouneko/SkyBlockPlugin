package amedouhu.skyblockplugin.events;

import amedouhu.skyblockplugin.apis.IsSkyBlock;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import java.util.Objects;

public class PlayerInteractEvent implements Listener {
    // スカイブロックの右クリックをキャンセルする(耕したりできないよう)
    IsSkyBlock isSkyBlock = new IsSkyBlock();
    @EventHandler
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // 右クリックかつブロックをクリックしたなら
            Location location = Objects.requireNonNull(e.getClickedBlock()).getLocation();
            if (isSkyBlock.main(Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ())) {
                // スカイブロックなら
                if (e.getItem() != null) {
                    // 素手でクリックされたのではないなら
                    String typeName = Objects.requireNonNull(e.getItem()).getType().name();
                    if (typeName.endsWith("_SHOVEL") || typeName.endsWith("_HOE")) {
                        // しゃべるまたはくわでクリックされたなら
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
