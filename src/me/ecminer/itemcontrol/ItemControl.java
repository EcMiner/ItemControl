package me.ecminer.itemcontrol;

import java.io.File;

import me.ecminer.itemcontrol.listeners.ArmourListener;
import me.ecminer.itemcontrol.listeners.OthersListener;
import me.ecminer.itemcontrol.listeners.ToolsListener;
import me.ecminer.itemcontrol.listeners.WeaponsListener;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.mrphpfan.mccombatlevel.McCombatLevel;

public class ItemControl extends JavaPlugin {

	private Settings settings;
	private McCombatLevel mcCombatLevel;

	public void onEnable() {
		saveDefaultConfig();
		saveResource("messages.yml", false);

		if (!findMcCombatLevel()) {
			System.err.println("Couldn't find McCombatLevel, disabling plugin!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		this.settings = new Settings(this, getConfig(), YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml")));

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new ArmourListener(this), this);
		pm.registerEvents(new OthersListener(this), this);
		pm.registerEvents(new ToolsListener(this), this);
		pm.registerEvents(new WeaponsListener(this), this);
	}

	private boolean findMcCombatLevel() {
		mcCombatLevel = (McCombatLevel) getServer().getPluginManager().getPlugin("McCombatLevel");
		return mcCombatLevel != null;
	}

	public McCombatLevel getMcCombatLevel() {
		return mcCombatLevel;
	}

	public Settings getSettings() {
		return settings;
	}

	public void addItemToInventory(Player player, ItemStack item) {
		if (player.getInventory().firstEmpty() >= 0) {
			player.getInventory().addItem(item);
		} else {
			player.getWorld().dropItemNaturally(player.getLocation(), item);
		}
	}

}