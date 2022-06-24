package me.lagbug.chatutilities.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.lagbug.chatutilities.ChatUtils;
import me.lagbug.chatutilities.common.utils.ActionUtil;
import me.lagbug.chatutilities.common.utils.Utils;
import me.lagbug.chatutilities.utils.Permissions;

public class PlayerCommandPreProcess implements Listener {

	private final ChatUtils plugin = ChatUtils.getPlugin(ChatUtils.class);
	private FileConfiguration data, config, messages;

	public PlayerCommandPreProcess() {
		data = plugin.getDataFile();
		config = plugin.getConfigFile();
		messages = plugin.getLangFile();
	}
	
	@EventHandler
	public void OnCommandPrePreccess(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();

		for (String cmd : data.getConfigurationSection("newCommands").getKeys(false)) {
			if (e.getMessage().equalsIgnoreCase("/" + cmd)) {
				e.setCancelled(true);

				if (player.hasPermission(Permissions.ALL) || player.hasPermission(data.getString("newCommands." + cmd + ".permission"))) {
					if (plugin.getCooldowns().containsKey(player) && plugin.getCooldowns().get(player).containsKey(cmd) && plugin.getCooldowns().get(player).get(cmd) > System.currentTimeMillis()) {
						player.sendMessage(Utils.replace(messages.getString("general.inCooldown")).replace("%remaining%", String.valueOf((plugin.getCooldowns().get(player).get(cmd) - System.currentTimeMillis())/ 1000)));
					} else {
						ActionUtil.execute(player, data.getStringList("newCommands." + cmd + ".actions").toString());

						Map<String, Long> m = new HashMap<>();
						if (plugin.getCooldowns().containsKey(player)) {
							m = plugin.getCooldowns().get(player);
						}

						m.put(cmd, System.currentTimeMillis() + (data.getLong("newCommands." + cmd + ".cooldown") * 1000));
						plugin.getCooldowns().put(player, m);
					}
				} else {
					player.sendMessage(Utils.replace(messages.getString("general.noPermissions")));
					return;
				}
			}
		}
		
		for (String key : data.getStringList("replaceCommands")) {
            String current = key.split(";;")[0].toLowerCase(), future = key.split(";;")[1].toLowerCase();
            
            if (e.getMessage().toLowerCase().startsWith("/" + current)) {
                e.setCancelled(true);
                player.performCommand(e.getMessage().replace("/", "").replace(current, future));
            }
        }
		
		String command = e.getMessage().toLowerCase().substring(1);
        if (config.getStringList("spy.blacklisted").contains(command)) {
            return;
        }
        
        plugin.getSpiers().forEach(p -> p.sendMessage(Utils.replace(messages.getString("spy.format").replace("%command%", command).replace("%player%", e.getPlayer().getName()))));
	}
}