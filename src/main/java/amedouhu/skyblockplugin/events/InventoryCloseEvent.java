package amedouhu.skyblockplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;

public class InventoryCloseEvent implements Listener {
    // InventoryGuiを閉じたとき編集の無効化を外す
    @EventHandler
    public void onInventoryClose(org.bukkit.event.inventory.InventoryCloseEvent e){
        Set<String> UserTags = e.getPlayer().getScoreboardTags();
        if(UserTags.contains("InventoryGui")){
            e.getPlayer().removeScoreboardTag("InventoryGui");
        }
    }
}
