package me.lagbug.chatutilities.common.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class FileUtils {

	private JavaPlugin plugin;
	private File configFile, langFile, dataFile;
	private YamlConfiguration modifyConfig, modifyLanguage, modifyData;

	public void initiate(JavaPlugin plugin) {
		this.plugin = plugin;

		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdirs();
		}

		configFile = new File(plugin.getDataFolder(), "config.yml");
		dataFile = new File(plugin.getDataFolder(), "data.yml");
		langFile = new File(plugin.getDataFolder(), "language.yml");


		if (!configFile.exists()) {
			plugin.saveResource("config.yml", false);
		}
		if (!dataFile.exists()) {
			plugin.saveResource("data.yml", false);
		}
		
		if (!langFile.exists()) {
			plugin.saveResource("language.yml", false);
		}

		modifyConfig = YamlConfiguration.loadConfiguration(configFile);
		modifyData = YamlConfiguration.loadConfiguration(dataFile);
		modifyLanguage = YamlConfiguration.loadConfiguration(langFile);
	}

	public void reloadFiles() {
		initiate(plugin);
	}

	public YamlConfiguration getConfigFile() {
		return modifyConfig;
	}

	public File getConfigData() {
		return configFile;
	}

	public YamlConfiguration getLanguageFile() {
		return modifyLanguage;
	}

	public File getLanguageData() {
		return langFile;
	}

	public YamlConfiguration getDataFile() {
		return modifyData;
	}

	public File getDataData() {
		return dataFile;
	}

	public void saveConfigFile() {
		try {
			modifyConfig.save(configFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void saveLangFile() {
		try {
			modifyLanguage.save(langFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void saveDataFile() {
		try {
			modifyData.save(dataFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}