package me.lagbug.chatutilities.commands;

import org.bukkit.command.CommandSender;

import me.lagbug.chatutilities.commands.subcommands.CommandSpy;
import me.lagbug.chatutilities.commands.subcommands.Reload;
import me.lagbug.chatutilities.common.commands.SpigotCommand;
import me.lagbug.chatutilities.utils.Permissions;

public class ChatUtilsCommand extends SpigotCommand {
	
	public ChatUtilsCommand() {
		super(Permissions.USE, 1, new CommandSpy(), new Reload());
		super.setUsage("chatutils");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
	}
}