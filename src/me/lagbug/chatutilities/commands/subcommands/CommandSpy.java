package me.lagbug.chatutilities.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.lagbug.chatutilities.ChatUtils;
import me.lagbug.chatutilities.common.commands.SubCommand;
import me.lagbug.chatutilities.common.utils.Utils;
import me.lagbug.chatutilities.guis.SpyGui;
import me.lagbug.chatutilities.utils.Permissions;

public class CommandSpy extends SubCommand {

	private final ChatUtils plugin = ChatUtils.getPlugin(ChatUtils.class);
	private FileConfiguration messages;
	
	public CommandSpy() {
		super("commandspy:cs:commands:cspy", Permissions.COMMAND_SPY);
		messages = plugin.getLangFile();
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Utils.replace(messages.getString("general.playersOnly")));
		}
		Player player = (Player) sender;

		if (!player.hasPermission("commandspy.use")) {
			player.sendMessage(Utils.replace(messages.getString("general.noPermsisons")));
			return;
		}

		if (args.length >= 3) {
			Player target = Bukkit.getPlayer(args[2]);
			
	        if (target == null || !target.isOnline()) {
	            player.sendMessage(Utils.replace(messages.getString("general.playerNotFound").replace("%player%", args[2])));
	            return;
	        }
			
			if (args[1].equalsIgnoreCase("enable")) {
		        if (plugin.getSpiers().contains(target)) {
		            player.sendMessage(Utils.replace(messages.getString("spy.alreadyEnabled").replace("%player%", args[2])));
		            return;
		        }
		        
		        plugin.getSpiers().add(target);
		        player.sendMessage(Utils.replace((messages.getString("spy.enabled"))));
			} else if (args[1].equalsIgnoreCase("disable")) {
		        if (!plugin.getSpiers().contains(target)) {
		            player.sendMessage(Utils.replace(messages.getString("spy.alreadyDisabled").replace("%player%", args[2])));
		            return;
		        }
		        
		        plugin.getSpiers().remove(target);
		        player.sendMessage(Utils.replace(messages.getString("spy.disabled")));				
			} else {
				new SpyGui(player);		
			}
			return;
		}

		new SpyGui(player);
	}

}
