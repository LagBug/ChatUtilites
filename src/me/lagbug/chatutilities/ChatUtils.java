package me.lagbug.chatutilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.lagbug.chatutilities.commands.ChatUtilsCommand;
import me.lagbug.chatutilities.events.InventoryClick;
import me.lagbug.chatutilities.events.PlayerCommandPreProcess;
import me.lagbug.common.utils.FileUtils;
import me.lagbug.common.utils.Metrics;
import me.lagbug.common.utils.UpdateChecker;
import me.lagbug.common.utils.Utils;

public class ChatUtils extends JavaPlugin {

	private final ConsoleCommandSender cs = Bukkit.getConsoleSender();

	private Map<Player, Map<String, Long>> cooldowns = new HashMap<>();
    private List<Player> spiers = new ArrayList<>();

	private FileConfiguration configFile, langFile, dataFile;
	private FileUtils fileUtils = new FileUtils();

	public void onEnable() {
		fileUtils.initiate(this);
		initiate();
		
		Bukkit.getPluginManager().registerEvents(new PlayerCommandPreProcess(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
		getCommand("chatutils").setExecutor(new ChatUtilsCommand());

		new Metrics(this);
		
		cs.sendMessage("--------------------------------------------------");
		cs.sendMessage(" --> " + getDescription().getName() + " v" + getDescription().getVersion() + " has been enabled successfully.");
		cs.sendMessage(Utils.isPluginEnabled("PlaceholderAPI") ? " --> PlaceHolderAPI is found. Placeholders will work." : " --> PlaceHolderAPI could not be found. Placeholders will not work.");
		cs.sendMessage("--------------------------------------------------");
		
		new UpdateChecker(this, 49326).schedule(120);
	}

	public void onDisable() {
		cs.sendMessage("--------------------------------------------------");
		cs.sendMessage(" --> " + getDescription().getName() + " v" + getDescription().getVersion() + " has been disabled successfully.");
		cs.sendMessage("--------------------------------------------------");
	}
	
	public void initiate() {
		configFile = fileUtils.getConfigFile();
		dataFile = fileUtils.getDataFile();
		langFile = fileUtils.getLanguageFile();
	}

	public Map<Player, Map<String, Long>> getCooldowns() {
		return cooldowns;
	}
	
    public List<Player> getSpiers() {
        return spiers;
    }
	
	public FileConfiguration getConfigFile() {
		return configFile;
	}

	public FileConfiguration getLangFile() {
		return langFile;
	}

	public FileConfiguration getDataFile() {
		return dataFile;
	}


	public void saveConfigFile() {
		fileUtils.saveConfigFile();
	}

	public void saveLangFile() {
		fileUtils.saveLangFile();
	}

	public void saveDataFile() {
		fileUtils.saveDataFile();
	}

	public void reloadFiles() {
		fileUtils.reloadFiles();
	}
}