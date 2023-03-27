package amedouhu.skyblockplugin;

import amedouhu.skyblockplugin.commands.SkyBlock;
import amedouhu.skyblockplugin.events.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SkyBlockPlugin extends JavaPlugin {
    private static JavaPlugin plugin;
    FileConfiguration config = getConfig();
    @Override
    public void onEnable() {
        // プラグイン起動時の処理
        super.onEnable();
        saveDefaultConfig();
        plugin = this;
        // 他クラスの呼び出し処理
        Objects.requireNonNull(getCommand("skyblock")).setExecutor(new SkyBlock());
        Bukkit.getServer().getPluginManager().registerEvents(new BlockBreakEvent(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new BlockBurnEvent(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new BlockExplodeEvent(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new EntityChangeBlockEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryClickEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryCloseEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractEvent(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinEvent(),this);
        // config.ymlの初期化
        if (config.get("skyblocks") == null) {
            // 項目「skyblocks」がnullなら
            List<String> skyblocks = new ArrayList<>();
            config.set("skyblocks",skyblocks);
        }
        if (config.get("blocks.level1") == null) {
            List<String> level1 = new ArrayList<>();
            level1.add(Material.GRASS_BLOCK.name());
            level1.add(Material.DIRT.name());
            level1.add(Material.SAND.name());
            level1.add(Material.CLAY.name());
            level1.add(Material.RED_SAND.name());
            level1.add(Material.SNOW_BLOCK.name());
            level1.add(Material.BARREL.name());
            config.set("blocks.level1",level1);
        }
        if (config.get("blocks.level2") == null) {
            List<String > level2 = new ArrayList<>();
            level2.add(Material.OAK_PLANKS.name());
            level2.add(Material.SPRUCE_PLANKS.name());
            level2.add(Material.BIRCH_PLANKS.name());
            level2.add(Material.JUNGLE_PLANKS.name());
            level2.add(Material.ACACIA_PLANKS.name());
            level2.add(Material.DARK_OAK_PLANKS.name());
            level2.add(Material.MANGROVE_PLANKS.name());
            level2.add(Material.BARREL.name());
            level2.add(Material.OAK_LOG.name());
            level2.add(Material.SPRUCE_LOG.name());
            level2.add(Material.BIRCH_LOG.name());
            level2.add(Material.JUNGLE_LOG.name());
            level2.add(Material.ACACIA_LOG.name());
            level2.add(Material.DARK_OAK_LOG.name());
            level2.add(Material.MANGROVE_LOG.name());
            config.set("blocks.level2",level2);
        }
        if (config.get("blocks.level3") == null) {
            List<String > level3 = new ArrayList<>();
            level3.add(Material.STONE.name());
            level3.add(Material.GRAVEL.name());
            level3.add(Material.COAL_ORE.name());
            level3.add(Material.IRON_ORE.name());
            level3.add(Material.COPPER_ORE.name());
            level3.add(Material.BARREL.name());
            level3.add(Material.GOLD_ORE.name());
            level3.add(Material.REDSTONE_ORE.name());
            level3.add(Material.EMERALD_ORE.name());
            level3.add(Material.LAPIS_ORE.name());
            level3.add(Material.DIAMOND_ORE.name());
            level3.add(Material.TUFF.name());
            level3.add(Material.DEEPSLATE.name());
            config.set("blocks.level3",level3);
        }
        if (config.get("blocks.level4") == null) {
            List<String> level4 = new ArrayList<>();
            level4.add(Material.NETHERRACK.name());
            level4.add(Material.CRIMSON_PLANKS.name());
            level4.add(Material.WARPED_PLANKS.name());
            level4.add(Material.CRIMSON_STEM.name());
            level4.add(Material.WARPED_STEM.name());
            level4.add(Material.MAGMA_BLOCK.name());
            level4.add(Material.GLOWSTONE.name());
            level4.add(Material.BARREL.name());
            level4.add(Material.POLISHED_BASALT.name());
            level4.add(Material.NETHER_BRICKS.name());
            level4.add(Material.BLACKSTONE.name());
            level4.add(Material.GILDED_BLACKSTONE.name());
            level4.add(Material.ANCIENT_DEBRIS.name());
            config.set("blocks.level4",level4);
        }
        if (config.get("blocks.level5") == null) {
            List<String> level5 = new ArrayList<>();
            level5.add(Material.END_STONE.name());
            level5.add(Material.END_STONE_BRICKS.name());
            level5.add(Material.BARREL.name());
            level5.add(Material.PURPUR_BLOCK.name());
            config.set("blocks.level5",level5);
        }
        if (config.get("items.level1") == null) {
            List<String> level1 = new ArrayList<>();
            level1.add(Material.WHEAT_SEEDS.name());
            level1.add(Material.PUMPKIN_SEEDS.name());
            level1.add(Material.BEETROOT_SEEDS.name());
            level1.add(Material.MELON_SEEDS.name());
            level1.add(Material.WHEAT_SEEDS.name());
            level1.add(Material.WATER_BUCKET.name());
            level1.add(Material.RED_TULIP.name());
            level1.add(Material.DEAD_BUSH.name());
            level1.add(Material.DANDELION.name());
            level1.add(Material.POPPY.name());
            level1.add(Material.BLUE_ORCHID.name());
            level1.add(Material.ALLIUM.name());
            level1.add(Material.AZURE_BLUET.name());
            level1.add(Material.RED_TULIP.name());
            level1.add(Material.ORANGE_TULIP.name());
            level1.add(Material.WHITE_TULIP.name());
            level1.add(Material.PINK_TULIP.name());
            level1.add(Material.OXEYE_DAISY.name());
            level1.add(Material.CORNFLOWER.name());
            level1.add(Material.LILY_OF_THE_VALLEY.name());
            level1.add(Material.SUGAR_CANE.name());
            level1.add(Material.CACTUS.name());
            level1.add(Material.SUNFLOWER.name());
            level1.add(Material.LILAC.name());
            level1.add(Material.ROSE_BUSH.name());
            level1.add(Material.BIG_DRIPLEAF.name());
            level1.add(Material.LILY_PAD.name());
            level1.add(Material.SWEET_BERRIES.name());
            level1.add(Material.FISHING_ROD.name());
            config.set("items.level1",level1);
        }
        if (config.get("items.level2") == null) {
            List<String> level2 = new ArrayList<>();
            level2.add(Material.OAK_SAPLING.name());
            level2.add(Material.SPRUCE_SAPLING.name());
            level2.add(Material.BIRCH_SAPLING.name());
            level2.add(Material.JUNGLE_SAPLING.name());
            level2.add(Material.ACACIA_SAPLING.name());
            level2.add(Material.DARK_OAK_SAPLING.name());
            level2.add(Material.MANGROVE_PROPAGULE.name());
            level2.add(Material.BAMBOO.name());
            level2.add(Material.BONE_MEAL.name());
            level2.add(Material.BROWN_MUSHROOM.name());
            level2.add(Material.RED_MUSHROOM.name());
            config.set("items.level2",level2);
        }
        if (config.get("items.level3") == null) {
            List<String> level3 = new ArrayList<>();
            level3.add(Material.MOSS_CARPET.name());
            level3.add(Material.POINTED_DRIPSTONE.name());
            level3.add(Material.LAVA_BUCKET.name());
            level3.add(Material.SMALL_AMETHYST_BUD.name());
            level3.add(Material.MEDIUM_AMETHYST_BUD.name());
            level3.add(Material.LARGE_AMETHYST_BUD.name());
            level3.add(Material.AMETHYST_CLUSTER.name());
            level3.add(Material.AMETHYST_SHARD.name());
            level3.add(Material.COBWEB.name());
            level3.add(Material.SCULK.name());
            level3.add(Material.SCULK_CATALYST.name());
            level3.add(Material.SCULK_SHRIEKER.name());
            level3.add(Material.SCULK_SENSOR.name());
            level3.add(Material.SLIME_BALL.name());
            config.set("items.level3",level3);
        }
        if (config.get("items.level4") == null) {
            List<String> level4 = new ArrayList<>();
            level4.add(Material.CRIMSON_ROOTS.name());
            level4.add(Material.WARPED_ROOTS.name());
            level4.add(Material.NETHER_WART.name());
            level4.add(Material.FIRE_CHARGE.name());
            level4.add(Material.CRIMSON_FUNGUS.name());
            level4.add(Material.WARPED_FUNGUS.name());
            level4.add(Material.WARPED_FUNGUS_ON_A_STICK.name());
            level4.add(Material.MAGMA_CREAM.name());
            level4.add(Material.BLAZE_ROD.name());
            level4.add(Material.BLAZE_POWDER.name());
            config.set("items.level4",level4);
        }
        if (config.get("items.level5") == null) {
            List<String> level5 = new ArrayList<>();
            level5.add(Material.RED_BED.name());
            level5.add(Material.ARROW.name());
            level5.add(Material.BOW.name());
            level5.add(Material.EXPERIENCE_BOTTLE.name());
            level5.add(Material.STONE_SWORD.name());
            level5.add(Material.GOLDEN_APPLE.name());
            config.set("items.level5",level5);
        }
        if (config.get("mobs.level1") == null) {
            List<String> level1 = new ArrayList<>();
            level1.add(EntityType.SHEEP.name());
            level1.add(EntityType.COW.name());
            level1.add(EntityType.ZOMBIE.name());
            level1.add(EntityType.PIG.name());
            config.set("mobs.level1",level1);
        }
        if (config.get("mobs.level2") == null){
            List<String> level2 = new ArrayList<>();
            level2.add(EntityType.CHICKEN.name());
            level2.add(EntityType.BEE.name());
            level2.add(EntityType.WOLF.name());
            level2.add(EntityType.SKELETON.name());
            level2.add(EntityType.MUSHROOM_COW.name());
            config.set("mobs.level2",level2);
        }
        if (config.get("mobs.level3") == null) {
            List<String> level3 = new ArrayList<>();
            level3.add(EntityType.BAT.name());
            level3.add(EntityType.CAVE_SPIDER.name());
            level3.add(EntityType.SQUID.name());
            level3.add(EntityType.GLOW_SQUID.name());
            level3.add(EntityType.FOX.name());
            level3.add(EntityType.CREEPER.name());
            level3.add(EntityType.HORSE.name());
            level3.add(EntityType.AXOLOTL.name());
            level3.add(EntityType.SLIME.name());
            level3.add(EntityType.WANDERING_TRADER.name());
            level3.add(EntityType.VILLAGER.name());
            config.set("mobs.level3",level3);
        }
        if (config.get("mobs.level4") == null) {
            List<String> level4 = new ArrayList<>();
            level4.add(EntityType.ALLAY.name());
            level4.add(EntityType.CAT.name());
            level4.add(EntityType.POLAR_BEAR.name());
            level4.add(EntityType.PILLAGER.name());
            level4.add(EntityType.IRON_GOLEM.name());
            level4.add(EntityType.ZOMBIFIED_PIGLIN.name());
            level4.add(EntityType.ZOGLIN.name());
            level4.add(EntityType.GHAST.name());
            config.set("mobs.level4",level4);
        }
        if (config.get("mobs.level5") == null){
            List<String> level5 = new ArrayList<>();
            level5.add(EntityType.PARROT.name());
            level5.add(EntityType.GOAT.name());
            level5.add(EntityType.ENDERMAN.name());
            level5.add(EntityType.SHULKER.name());
            config.set("mobs.level5",level5);
        }
        // config.ymlに上書きする
        saveConfig();
    }

    @Override
    public void onDisable() {
        // プラグイン終了時の処理
        super.onDisable();
        saveConfig();
    }
    public static JavaPlugin getPlugin() {return plugin;}
}
