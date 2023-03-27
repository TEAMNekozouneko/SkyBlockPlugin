package amedouhu.skyblockplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;

public class PlayerJoinEvent implements Listener {
    // InventoryGuiを持ったプレイヤーが参加した時外す(再起動用)
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent e){
        Set<String> UserTags = e.getPlayer().getScoreboardTags();
        if(UserTags.contains("InventoryGui")){
            e.getPlayer().removeScoreboardTag("InventoryGui");
        }
    }
}
