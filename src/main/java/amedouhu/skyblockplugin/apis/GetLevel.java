package amedouhu.skyblockplugin.apis;

import amedouhu.skyblockplugin.SkyBlockPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public class GetLevel {
    // 引数のブロックの段階を取得する
    FileConfiguration config = SkyBlockPlugin.getPlugin().getConfig();
    public int main(String string) {
        int level = 0;
        boolean isSkyBlock = false;
        List<String> skyblocks = config.getStringList("skyblocks");
        List<String> args = Arrays.asList(string.split(","));
        for (String skyblock : skyblocks) {
            // 全てのスカイブロックと照合する
            List<String> contents = Arrays.asList(skyblock.split(","));
            for (int n = 0; n < 4; n++) {
                isSkyBlock = contents.get(n).equals(args.get(n));
                if (! isSkyBlock) {
                    break;
                }
            }
            if (isSkyBlock) {
                level = Integer.parseInt(contents.get(4));
            }
        }
        return level;
    }
}
