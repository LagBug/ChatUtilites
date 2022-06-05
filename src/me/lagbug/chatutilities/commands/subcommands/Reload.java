package me.lagbug.chatutilities.commands.subcommands;

import org.bukkit.command.CommandSender;

import me.lagbug.chatutilities.ChatUtils;
import me.lagbug.chatutilities.utils.Permissions;
import me.lagbug.common.commands.SubCommand;
import me.lagbug.common.utils.Utils;

public class Reload extends SubCommand {
	
	private final ChatUtils plugin = ChatUtils.getPlugin(ChatUtils.class);

	public Reload() {
		super("reload:rel:rl", Permissions.RELOAD);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		plugin.reloadFiles();
		plugin.initiate();
		
		sender.sendMessage(Utils.replace(plugin.getLangFile().getString("reloaded")));	
	}

}
