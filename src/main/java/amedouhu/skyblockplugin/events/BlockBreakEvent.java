package amedouhu.skyblockplugin.events;

import amedouhu.skyblockplugin.SkyBlockPlugin;
import amedouhu.skyblockplugin.apis.*;
import org.bukkit.*;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.List;

public class BlockBreakEvent implements Listener {
    // SkyBlockが破壊されたときのドロップを処理するクラス
    GetExp getExp = new GetExp();
    GetIndexNum getIndexNum = new GetIndexNum();
    GetLevel getLevel = new GetLevel();
    FileConfiguration config = SkyBlockPlugin.getPlugin().getConfig();
    Hologram hologram = new Hologram();
    IsSkyBlock isSkyBlock = new IsSkyBlock();
    @EventHandler
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent e) {
        // ブロックが破壊されたとき
        List<String> skyblocks = config.getStringList("skyblocks");
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location location = block.getLocation();
        int x = (int) location.getX();
        int y = (int) location.getY();
        int z = (int) location.getZ();
        location = new Location(location.getWorld(), x, y, z);
        String locationString = Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ();
        if (isSkyBlock.main(locationString)) {
            // スカイブロックなら
            // キャンセルする
            e.setCancelled(true);
            // 次のブロックまたはモブを設置する
            int level = getLevel.main(locationString);
            int exp = getExp.main(locationString);
            Random random = new Random();
            int randomInt = random.nextInt(18);
            if (randomInt == 0) {
                // モブをスポーンさせる
                Location spawnLocation = new Location(location.getWorld(),location.getX() + 0.5,location.getY() + 2,location.getZ() + 0.5);
                Objects.requireNonNull(spawnLocation.getWorld()).spawnEntity(spawnLocation,randomMob(level));
            }else {
                // ブロックを設置する
                // ツールの耐久値を実装する
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                Material type = itemStack.getType();
                if (type.name().endsWith("_PICKAXE") || type.name().endsWith("_AXE") || type.name().endsWith("_SHOVEL") || type.name().endsWith("_SWORD") || type.name().endsWith("_HOE")) {
                    // ツールで破壊されたのなら
                    if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                        // サバイバルモードなら
                        // ダメージ値を取得
                        short durability = itemStack.getDurability();
                        durability ++;
                        itemStack.setDurability(durability);
                        // アイテムの最大耐久値を取得する
                        int maxDurability = type.getMaxDurability();
                        // 耐久値が最大耐久値以上であれば
                        if (durability >= maxDurability) {
                            // アイテムを空にする
                            // プレイヤーのメインハンドのアイテムを取得
                            player.getInventory().setItemInMainHand(null);
                            location.getWorld().playSound(location, Sound.ENTITY_ITEM_BREAK,1.0f,1.0f);
                        }
                    }
                }
                if (block.isPreferredTool(itemStack)) {
                    // 手持ちのツールでブロックを破壊可能なら
                    // ドロップする経験値を実装する
                    if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                        Location dropLocation = new Location(location.getWorld(), x, y+1, z);
                        // ドロップするアイテムを実装する
                        e.setDropItems(false);
                        List<ItemStack> drops = (List<ItemStack>) block.getDrops();
                        if (block.getType().equals(Material.BARREL)) {
                            // 樽を破壊したときは内容物もドロップするようにする
                            Barrel barrel = (Barrel) block.getState();
                            for (ItemStack barrelItemStack : barrel.getInventory().getContents()) {
                                if (barrelItemStack != null) {
                                    drops.add(barrelItemStack);
                                }
                            }
                        }
                        for (ItemStack drop : drops) {
                            block.getWorld().dropItemNaturally(dropLocation, drop);
                        }
                        // ドロップする経験値を実装する
                        if (e.getExpToDrop() != 0) {
                            location.getWorld().spawn(dropLocation, ExperienceOrb.class).setExperience(e.getExpToDrop());
                        }
                    }
                }
                // ブロックを設置する
                block.setType(randomMaterial(level));
                if (block.getType().equals(Material.BARREL)) {
                    // 戻り値が樽なら)
                    randomInventory(level,block);
                }
            }
            // 段階を解放できるかを判定する
            boolean isUpgrade = false;
            final String[] hologramText = {null};
            // 経験値を与える
            if (level<6) {
                exp ++;
            }
            for (int i=0;i<6;i++) {
                if (i+1 == level) {
                    if ((i+1)*100<exp) {
                        // 解放条件を満たしているなら
                        if (level<6) {
                            level++;
                            exp = 0;
                            isUpgrade = true;
                        }
                        switch (level) {
                            case 2:
                                block.setType(Material.OAK_LOG);
                                break;
                            case 3:
                                block.setType(Material.STONE);
                                break;
                            case 4:
                                block.setType(Material.NETHERRACK);
                                break;
                            case 5:
                                block.setType(Material.END_STONE);
                                break;
                        }
                    }else {
                        // 解放条件を満たしていないなら
                        switch (level) {
                            case 1:
                                hologramText[0] = ChatColor.GREEN + "§l段階1(" + exp + "/" + (i+1)*100 + ")";
                                break;
                            case 2:
                                hologramText[0] = ChatColor.GOLD + "§l段階2(" + exp + "/" + (i+1)*100 + ")";
                                break;
                            case 3:
                                hologramText[0] = ChatColor.GRAY + "§l段階3(" + exp + "/" + (i+1)*100 + ")";
                                break;
                            case 4:
                                hologramText[0] = ChatColor.RED + "§l段階4(" + exp + "/" + (i+1)*100 + ")";
                                break;
                            case 5:
                                hologramText[0] = ChatColor.YELLOW + "§l段階5(" + exp + "/" + (i+1)*100 + ")";
                                break;
                            case 6:
                                hologramText[0] = ChatColor.BLUE + "§l§oSKYBLOCK";
                                break;
                        }
                    }
                    break;
                }
            }
            // 演出を処理する
            if (isUpgrade) {
                // 段階開放の演出をする
                if (level == 6) {
                    // 最終段階の開放なら
                    location.getWorld().playSound(location,Sound.UI_TOAST_CHALLENGE_COMPLETE,1.0f,1.0f);
                    // エンドポータルを出現させる
                    // 0層目:落下防止ガラス
                    for (int i = -1; i <= 1; i++) {
                        for (int n = -1; n <= 1; n++) {
                            Block relative = block.getRelative(i, 0, n);
                            if (! isSkyBlock.main(relative.getWorld() + "," + relative.getX() + "," + relative.getY() + "," + relative.getZ())) {
                                // スカイブロックではないなら
                                relative.setType(Material.GLASS);
                            }
                        }
                    }
                    // 1層目:空気
                    for (int i = -1; i <= 1; i++) {
                        for (int n = -1; n <= 1; n++) {
                            Block relative = block.getRelative(i, -1, n);
                            relative.setType(Material.AIR);
                        }
                    }
                    // 2層目:ポータルの淵
                    for (int i = -2; i <= 2; i++) {
                        for (int n = -2; n <= 2; n++) {
                            Block relative = block.getRelative(i, -2, n);
                            relative.setType(Material.END_STONE);
                        }
                    }
                    // 2層目:ポータル
                    for (int i = -1; i <= 1; i++) {
                        for (int n = -1; n <= 1; n++) {
                            Block relative = block.getRelative(i, -2, n);
                            relative.setType(Material.END_PORTAL);
                        }
                    }
                }else {
                    location.getWorld().playSound(location,Sound.ENTITY_PLAYER_LEVELUP,1.0f,1.0f);
                }
            }else {
                // 通常破壊の演出をする
                hologram.main(player,location, hologramText[0]);
            }
            // レベル・経験値をconfig.ymlに上書きする
            int indexNum = getIndexNum.main(location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ());
            skyblocks.set(indexNum,location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + level + "," + exp);
            config.set("skyblocks",skyblocks);
            SkyBlockPlugin.getPlugin().saveConfig();
        }
    }

    public Material randomMaterial(int level) {
        // ランダムなMaterialを返す
        // どのレベルから選ぶかを決定する
        List<Integer> levels = new ArrayList<>();
        if (level == 6) {
            // 最終段階なら
            for (int i=0;i<5;i++) {
                // 5回繰り返す
                for (int n=0;n<i+1;n++) {
                    levels.add(i+1);
                }
            }
        }else {
            for (int i=0;i<level;i++) {
                // level回繰り返す
                for (int n=0;n<i+1;n++) {
                    levels.add(i+1);
                }
            }
        }
        Random random = new Random();
        int randomLevel = levels.get(random.nextInt(levels.size()));
        // どのMaterialを返すかを決定する
        List<String> materials = new ArrayList<>();
        if (level == 6) {
            materials.addAll(config.getStringList("blocks.level" + randomLevel));
        }else {
            for (int i=0;i<config.getStringList("blocks.level" + randomLevel).size();i++){
                for (int n=config.getStringList("blocks.level" + randomLevel).size()-i;0<n;n--) {
                    materials.add(config.getStringList("blocks.level" + randomLevel).get(i));
                }
            }
        }
        return Material.getMaterial(materials.get(random.nextInt(materials.size())));
    }

    public void randomInventory(int level,Block block) {
        // ランダムなInventoryに設定する
        if (block.getType().equals(Material.BARREL)) {
            // blockが樽なら
            Random random = new Random();
            Inventory inventory = ((Barrel) block.getState()).getInventory();
            inventory.clear();// インベントリを初期化する
            // どのレベルから選ぶかを決定する
            List<Integer> levels = new ArrayList<>();
            int randomLevel;
            if (level == 6) {
                // 最大段階なら
                randomLevel = random.nextInt(5) + 1;
            }else {
                // 最大段階ではないなら
                for (int i=0;i<level;i++) {
                    // level回繰り返す
                    for (int n=0;n<i+1;n++) {
                        levels.add(i+1);
                    }
                }
                randomLevel = levels.get(random.nextInt(levels.size()));
            }

            // レベルのアイテムリストを取得する
            List<String> items = new ArrayList<>();
            for (int i=0;i<config.getStringList("items.level" + randomLevel).size();i++){
                for (int n=config.getStringList("items.level" + randomLevel).size()-i;0<n;n--) {
                    items.add(config.getStringList("items.level" + randomLevel).get(i));
                }
            }
            // 何個アイテムを追加するかを決定する
            int randomNum = random.nextInt(5) + 1;
            for (int i=0;i<randomNum;i++) {
                // 何のアイテムを追加するかを決定する
                int randomItem = random.nextInt(items.size());
                ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(items.get(randomItem))));
                // 何番目のスロットに追加するかを決定する
                List<Integer> slots = new ArrayList<>();
                for (int n=0;n<inventory.getSize();n++) {
                    if (inventory.getItem(n) == null) {
                        // スロットが空なら
                        slots.add(n);
                    }
                }
                if (slots.size() >= 1) {
                    // 空のスロットが存在するなら
                    int randomSlot = slots.get(random.nextInt(slots.size()));
                    inventory.setItem(randomSlot,itemStack);
                }
            }
        }
    }

    public EntityType randomMob(int level) {
        // ランダムなモブを返す
        Random random = new Random();
        // どこのレベルから選ぶかを決定する
        List<Integer> levels = new ArrayList<>();
        if (level == 6) {
            for (int i=0;i<5;i++) {
                // 5回繰り返す
                for (int n=0;n<i+1;n++) {
                    levels.add(i+1);
                }
            }
        }else {
            for (int i=0;i<level;i++) {
                // level回繰り返す
                for (int n=0;n<i+1;n++) {
                    levels.add(i+1);
                }
            }
        }
        int randomLevel = levels.get(random.nextInt(levels.size()));
        // レベルのモブリストを作成する
        List<String> mobs = new ArrayList<>();
        if (level == 6) {
            // 最終段階なら
            mobs.addAll(config.getStringList("mobs.level" + randomLevel));
        }else {
            for (int i=0;i<config.getStringList("mobs.level" + randomLevel).size();i++){
                for (int n=config.getStringList("mobs.level" + randomLevel).size()-i;0<n;n--) {
                    mobs.add(config.getStringList("mobs.level" + randomLevel).get(i));
                }
            }
        }
        return EntityType.valueOf(mobs.get(random.nextInt(mobs.size())));
    }
}
