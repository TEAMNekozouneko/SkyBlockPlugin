package amedouhu.skyblockplugin.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class SkyBlock implements CommandExecutor {
    // 「skyblock」コマンドの処理クラス
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // コマンドが実行されたとき
        if (sender.isOp() && sender instanceof Player && args.length == 0) {
            // 送信者がサーバー管理者かつプレイヤーかつ引数の長さが0なら
            // インベントリを作成する
            Player player = (Player) sender;
            Inventory inventory = Bukkit.createInventory(null,9,ChatColor.BLUE + "§l§oSKYBLOCK");
            ItemStack itemStack;
            ItemMeta itemMeta;

            itemStack = new ItemStack(Material.GRASS_BLOCK);
            itemMeta = itemStack.getItemMeta();
            Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.GREEN + "§l段階1(草原時代)");
            itemMeta.setLore(List.of(ChatColor.WHITE + "クリックして設置！","InventoryGui"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(0,itemStack);

            itemStack = new ItemStack(Material.OAK_LOG);
            itemMeta = itemStack.getItemMeta();
            Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.GOLD + "§l段階2(原始時代)");
            itemMeta.setLore(List.of(ChatColor.WHITE + "クリックして設置！","InventoryGui"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(1,itemStack);

            itemStack = new ItemStack(Material.STONE);
            itemMeta = itemStack.getItemMeta();
            Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.GRAY + "§l段階3(石器時代)");
            itemMeta.setLore(List.of(ChatColor.WHITE + "クリックして設置！","InventoryGui"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(2,itemStack);

            itemStack = new ItemStack(Material.NETHERRACK);
            itemMeta = itemStack.getItemMeta();
            Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.RED + "§l段階4(戦闘時代)");
            itemMeta.setLore(List.of(ChatColor.WHITE + "クリックして設置！","InventoryGui"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(3,itemStack);

            itemStack = new ItemStack(Material.END_STONE);
            itemMeta = itemStack.getItemMeta();
            Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.YELLOW + "§l段階5(終末時代)");
            itemMeta.setLore(List.of(ChatColor.WHITE + "クリックして設置！","InventoryGui"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(4,itemStack);

            itemStack = new ItemStack(Material.STONE_PICKAXE);
            itemMeta = itemStack.getItemMeta();
            Objects.requireNonNull(itemMeta).setDisplayName("§l破壊ピッケル");
            itemMeta.setLore(List.of(ChatColor.WHITE + "クリックして破壊！","InventoryGui"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(7,itemStack);

            itemStack = new ItemStack(Material.BARRIER);
            itemMeta = itemStack.getItemMeta();
            Objects.requireNonNull(itemMeta).setDisplayName("§l閉じる");
            itemMeta.setLore(List.of(ChatColor.WHITE + "クリックして閉じる！","InventoryGui"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(8,itemStack);

            // インベントリをプレイヤーに開かせる
            if (! player.getScoreboardTags().contains("InventoryGui")) {
                // まだスコアボードタグを持っていないなら
                player.addScoreboardTag("InventoryGui");
            }
            player.openInventory(inventory);
        }
        return true;
    }
}
