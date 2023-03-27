package amedouhu.skyblockplugin.apis;

import amedouhu.skyblockplugin.SkyBlockPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public class GetIndexNum {
    // 引数のブロックはリストで何番目かを取得する
    FileConfiguration config = SkyBlockPlugin.getPlugin().getConfig();
    public int main(String string) {
        int indexNum = 0;
        boolean isSkyBlock = false;
        List<String> skyblocks = config.getStringList("skyblocks");
        List<String> args = Arrays.asList(string.split(","));
        for (int i=0;i<skyblocks.size();i++) {
            // 全てのスカイブロックと照合する
            List<String> contents = Arrays.asList(skyblocks.get(i).split(","));
            for (int n = 0; n < 4; n++) {
                isSkyBlock = contents.get(n).equals(args.get(n));
                if (! isSkyBlock) {
                    break;
                }
            }
            if (isSkyBlock) {
                indexNum = i;
                break;
            }
        }
        return indexNum;
    }
}
