package amedouhu.skyblockplugin.events;

import amedouhu.skyblockplugin.SkyBlockPlugin;
import amedouhu.skyblockplugin.apis.GetIndexNum;
import amedouhu.skyblockplugin.apis.IsSkyBlock;
import amedouhu.skyblockplugin.commands.SkyBlock;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class InventoryClickEvent implements Listener {
    // InventoryGuiを操作中のプレイヤーの編集を無効化する
    FileConfiguration config = SkyBlockPlugin.getPlugin().getConfig();
    IsSkyBlock isSkyBlock = new IsSkyBlock();
    GetIndexNum getIndexNum = new GetIndexNum();
    @EventHandler
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            // クリックしたアイテムがnullではないなら
            Set<String> UserTags = e.getWhoClicked().getScoreboardTags();
            if(UserTags.contains("InventoryGui")){
                // InventoryGuiを操作中なら
                e.setCancelled(true);
                Player player = (Player) e.getWhoClicked();
                ItemStack clickedItem = e.getCurrentItem();
                if (Objects.requireNonNull(clickedItem.getItemMeta()).hasLore()) {
                    // ロールを持っているなら
                    List<String> lore = Objects.requireNonNull(clickedItem.getItemMeta()).getLore();
                    if (Objects.requireNonNull(lore).contains("InventoryGui")) {
                        // InventoryGuiのアイテムなら
                        // クリックの処理を行う
                        // 左クリックなら
                        Location location = player.getLocation();
                        int x = (int) location.getX();
                        int y = (int) location.getY();
                        int z = (int) location.getZ();
                        location = new Location(location.getWorld(), x, y-1, z);
                        Block block = location.getBlock();
                        // 既に同一ブロックを示す値が存在するかを確認する
                        List<String> skyblocks = config.getStringList("skyblocks");
                        int index = getIndexNum.main(location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ());

                        ArmorStand hologram;
                        switch (clickedItem.getType()) {
                            case GRASS_BLOCK:
                                block.setType(Material.AIR);
                                block.setType(Material.GRASS_BLOCK);

                                if (! isSkyBlock.main(location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ())) {
                                    // 同一ブロックを示す値が存在しないなら
                                    skyblocks.add(Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + ",1,0");
                                }else {
                                    // 同一ブロックを示す値が存在するなら
                                    skyblocks.set(index,Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + ",1,0");
                                }
                                // 既にホログラムがある場合は削除する
                                for (Entity entity : player.getNearbyEntities(3, 3, 3)) {
                                    if (entity instanceof ArmorStand && entity.getLocation().equals(location.add(0.5,2,0.5))) {
                                        entity.remove();
                                    }
                                }
                                // ホログラムをスポーンさせる
                                location = player.getLocation();
                                x = (int) Math.floor(location.getX());
                                y = (int) Math.floor(location.getY());
                                z = (int) Math.floor(location.getZ());
                                location = new Location(location.getWorld(), x, y-1, z);
                                hologram = (ArmorStand) player.getWorld().spawnEntity(location.add(0.5,2,0.5), EntityType.ARMOR_STAND);
                                hologram.setCustomName(ChatColor.BLUE + "§l§oSKYBLOCK");
                                hologram.setVisible(false);
                                hologram.setGravity(false);
                                hologram.setCollidable(false);
                                hologram.setMarker(true);
                                hologram.setCustomNameVisible(true);
                                // config.ymlに上書きする
                                config.set("skyblocks",skyblocks);
                                SkyBlockPlugin.getPlugin().saveConfig();
                                break;
                            case OAK_LOG:
                                block.setType(Material.OAK_LOG);
                                if (! isSkyBlock.main(location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ())) {
                                    // 同一ブロックを示す値が存在しないなら
                                    skyblocks.add(Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + ",2,0");
                                    config.set("skyblocks",skyblocks);
                                    SkyBlockPlugin.getPlugin().saveConfig();
                                }else {
                                    // 同一ブロックを示す値が存在するなら
                                    skyblocks.set(index,Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + ",2,0");
                                }
                                // 既にホログラムがある場合は削除する
                                for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                                    if (entity instanceof ArmorStand && entity.getLocation().equals(location.add(0.5,2,0.5))) {
                                        entity.remove();
                                        break;
                                    }
                                }
                                // ホログラムをスポーンさせる
                                location = player.getLocation();
                                x = (int) Math.floor(location.getX());
                                y = (int) Math.floor(location.getY());
                                z = (int) Math.floor(location.getZ());
                                location = new Location(location.getWorld(), x, y-1, z);
                                hologram = (ArmorStand) player.getWorld().spawnEntity(location.add(0.5,2,0.5), EntityType.ARMOR_STAND);
                                hologram.setCustomName(ChatColor.BLUE + "§l§oSKYBLOCK");
                                hologram.setVisible(false);
                                hologram.setGravity(false);
                                hologram.setCollidable(false);
                                hologram.setMarker(true);
                                hologram.setCustomNameVisible(true);
                                // config.ymlに上書きする
                                config.set("skyblocks",skyblocks);
                                SkyBlockPlugin.getPlugin().saveConfig();
                                break;
                            case STONE:
                                block.setType(Material.STONE);
                                if (! isSkyBlock.main(location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ())) {
                                    // 同一ブロックを示す値が存在しないなら
                                    skyblocks.add(Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + ",3,0");
                                    config.set("skyblocks",skyblocks);
                                    SkyBlockPlugin.getPlugin().saveConfig();
                                }else {
                                    // 同一ブロックを示す値が存在するなら
                                    skyblocks.set(index,Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + ",3,0");
                                }
                                // 既にホログラムがある場合は削除する
                                for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                                    if (entity instanceof ArmorStand && entity.getLocation().equals(location.add(0.5,2,0.5))) {
                                        entity.remove();
                                        break;
                                    }
                                }
                                // ホログラムをスポーンさせる
                                location = player.getLocation();
                                x = (int) Math.floor(location.getX());
                                y = (int) Math.floor(location.getY());
                                z = (int) Math.floor(location.getZ());
                                location = new Location(location.getWorld(), x, y-1, z);
                                hologram = (ArmorStand) player.getWorld().spawnEntity(location.add(0.5,2,0.5), EntityType.ARMOR_STAND);
                                hologram.setCustomName(ChatColor.BLUE + "§l§oSKYBLOCK");
                                hologram.setVisible(false);
                                hologram.setGravity(false);
                                hologram.setCollidable(false);
                                hologram.setMarker(true);
                                hologram.setCustomNameVisible(true);
                                // config.ymlを上書きする
                                config.set("skyblocks",skyblocks);
                                SkyBlockPlugin.getPlugin().saveConfig();
                                break;
                            case NETHERRACK:
                                block.setType(Material.NETHERRACK);
                                if (! isSkyBlock.main(location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ())) {
                                    // 同一ブロックを示す値が存在しないなら
                                    skyblocks.add(Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + ",4,0");
                                    config.set("skyblocks",skyblocks);
                                    SkyBlockPlugin.getPlugin().saveConfig();
                                }else {
                                    // 同一ブロックを示す値が存在するなら
                                    skyblocks.set(index,Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + ",4,0");
                                }
                                // 既にホログラムがある場合は削除する
                                for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                                    if (entity instanceof ArmorStand && entity.getLocation().equals(location.add(0.5,2,0.5))) {
                                        entity.remove();
                                        break;
                                    }
                                }
                                // ホログラムをスポーンさせる
                                location = player.getLocation();
                                x = (int) Math.floor(location.getX());
                                y = (int) Math.floor(location.getY());
                                z = (int) Math.floor(location.getZ());
                                location = new Location(location.getWorld(), x, y-1, z);
                                hologram = (ArmorStand) player.getWorld().spawnEntity(location.add(0.5,2,0.5), EntityType.ARMOR_STAND);
                                hologram.setCustomName(ChatColor.BLUE + "§l§oSKYBLOCK");
                                hologram.setVisible(false);
                                hologram.setGravity(false);
                                hologram.setCollidable(false);
                                hologram.setMarker(true);
                                hologram.setCustomNameVisible(true);
                                // config.ymlを上書きする
                                config.set("skyblocks",skyblocks);
                                SkyBlockPlugin.getPlugin().saveConfig();
                                break;
                            case END_STONE:
                                block.setType(Material.END_STONE);
                                if (! isSkyBlock.main(location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ())) {
                                    // 同一ブロックを示す値が存在しないなら
                                    skyblocks.add(Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + ",5,0");
                                    config.set("skyblocks",skyblocks);
                                    SkyBlockPlugin.getPlugin().saveConfig();
                                }else {
                                    // 同一ブロックを示す値が存在するなら
                                    skyblocks.set(index,Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + ",5,0");
                                }
                                // 既にホログラムがある場合は削除する
                                for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                                    if (entity instanceof ArmorStand && entity.getLocation().equals(location.add(0.5,2,0.5))) {
                                        entity.remove();
                                        break;
                                    }
                                }
                                // ホログラムをスポーンさせる
                                location = player.getLocation();
                                x = (int) Math.floor(location.getX());
                                y = (int) Math.floor(location.getY());
                                z = (int) Math.floor(location.getZ());
                                location = new Location(location.getWorld(), x, y-1, z);
                                hologram = (ArmorStand) player.getWorld().spawnEntity(location.add(0.5,2,0.5), EntityType.ARMOR_STAND);
                                hologram.setCustomName(ChatColor.BLUE + "§l§oSKYBLOCK");
                                hologram.setVisible(false);
                                hologram.setGravity(false);
                                hologram.setCollidable(false);
                                hologram.setMarker(true);
                                hologram.setCustomNameVisible(true);
                                // config.ymlを上書きする
                                config.set("skyblocks",skyblocks);
                                SkyBlockPlugin.getPlugin().saveConfig();
                                break;
                            case STONE_PICKAXE:
                                if (isSkyBlock.main(location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ())) {
                                    // スカイブロックを削除する
                                    block.setType(Material.AIR);
                                    skyblocks.remove(index);
                                    config.set("skyblocks",skyblocks);
                                    SkyBlockPlugin.getPlugin().saveConfig();
                                    // ホログラムを削除する
                                    location.add(0.5,2,0.5);
                                    for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                                        if (entity instanceof ArmorStand && entity.getLocation().equals(location)) {
                                            entity.remove();
                                            break;
                                        }
                                    }
                                }
                                break;
                            case BARRIER:
                                player.closeInventory();
                                break;
                        }
                    }
                }
                e.setCancelled(true);
            }
        }
    }
}
