package me.ecminer.itemcontrol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Settings {

	private Map<String, Integer> levels = new HashMap<String, Integer>();
	private Map<Integer, List<String>> levelItemLookup = new HashMap<Integer, List<String>>();
	private ItemControl plugin;
	private String cancelled_armour = "&cYou can't wear %item%, you need to be level &6%level%";
	private String cancelled_tool = "&cYou can't use a %item%, you need to be level &6%level%";
	private String cancelled_weapon = "&cYou can't use a %item%, you need to be level &6%level%";
	private String cancelled_others = "&cYou can't use a %item%, you need to be level &6%level%";
	private String leveled_up = "&3Congratulations, you reached &6level %level%&3! You can now use %item%!";
	private String leveled_down = "&cYou were leveled-down to %level%, you can't use %item% anymore!";

	public Settings(ItemControl plugin, FileConfiguration config, FileConfiguration messages) {
		this.plugin = plugin;

		try {
			addItem("leather_helmet", config.getInt("armour.helmet.leather"));
			addItem("chainmail_helmet", config.getInt("armour.helmet.chain"));
			addItem("gold_helmet", config.getInt("armour.helmet.gold"));
			addItem("iron_helmet", config.getInt("armour.helmet.iron"));
			addItem("diamond_helmet", config.getInt("armour.helmet.diamond"));

			addItem("leather_chestplate", config.getInt("armour.chestplate.leather"));
			addItem("chainmail_chestplate", config.getInt("armour.chestplate.chain"));
			addItem("gold_chestplate", config.getInt("armour.chestplate.gold"));
			addItem("iron_chestplate", config.getInt("armour.chestplate.iron"));
			addItem("diamond_chestplate", config.getInt("armour.chestplate.diamond"));

			addItem("leather_leggings", config.getInt("armour.leggings.leather"));
			addItem("chainmail_leggings", config.getInt("armour.leggings.chain"));
			addItem("gold_leggings", config.getInt("armour.leggings.gold"));
			addItem("iron_leggings", config.getInt("armour.leggings.iron"));
			addItem("diamond_leggings", config.getInt("armour.leggings.diamond"));

			addItem("leather_boots", config.getInt("armour.boots.leather"));
			addItem("chainmail_boots", config.getInt("armour.boots.chain"));
			addItem("gold_boots", config.getInt("armour.boots.gold"));
			addItem("iron_boots", config.getInt("armour.boots.iron"));
			addItem("diamond_boots", config.getInt("armour.boots.diamond"));

			addItem("bow", config.getInt("weapons.bow"));
			addItem("wood_sword", config.getInt("weapons.wood-sword"));
			addItem("stone_sword", config.getInt("weapons.stone-sword"));
			addItem("gold_sword", config.getInt("weapons.gold-sword"));
			addItem("iron_sword", config.getInt("weapons.iron-sword"));
			addItem("diamond_sword", config.getInt("weapons.diamond-sword"));

			addItem("wood_pickaxe", config.getInt("tools.pickaxe.wood"));
			addItem("stone_pickaxe", config.getInt("tools.pickaxe.stone"));
			addItem("gold_pickaxe", config.getInt("tools.pickaxe.gold"));
			addItem("iron_pickaxe", config.getInt("tools.pickaxe.iron"));
			addItem("diamond_pickaxe", config.getInt("tools.pickaxe.diamond"));

			addItem("wood_spade", config.getInt("tools.shovel.wood"));
			addItem("stone_spade", config.getInt("tools.shovel.stone"));
			addItem("gold_spade", config.getInt("tools.shovel.gold"));
			addItem("iron_spade", config.getInt("tools.shovel.iron"));
			addItem("diamond_spade", config.getInt("tools.shovel.diamond"));

			addItem("wood_axe", config.getInt("tools.axe.wood"));
			addItem("stone_axe", config.getInt("tools.axe.stone"));
			addItem("gold_axe", config.getInt("tools.axe.gold"));
			addItem("iron_axe", config.getInt("tools.axe.iron"));
			addItem("diamond_axe", config.getInt("tools.axe.diamond"));

			addItem("wood_hoe", config.getInt("tools.hoe.wood"));
			addItem("stone_hoe", config.getInt("tools.hoe.stone"));
			addItem("gold_hoe", config.getInt("tools.hoe.gold"));
			addItem("iron_hoe", config.getInt("tools.hoe.iron"));
			addItem("diamond_hoe", config.getInt("tools.hoe.diamond"));

			addItem("enchantment_table", config.getInt("others.enchantment-table"));
			addItem("workbench", config.getInt("others.crafting-table"));
			addItem("anvil", config.getInt("others.anvil"));
			addItem(Material.BREWING_STAND.name().toLowerCase(), config.getInt("others.brewing_stand"));
			addItem("furnace", config.getInt("others.furnace"));

			if (messages.isSet("cancelled-armour")) {
				cancelled_armour = messages.getString("cancelled-armour");
			}
			if (messages.isSet("cancelled-tool")) {
				cancelled_tool = messages.getString("cancelled-tool");
			}
			if (messages.isSet("cancelled-weapon")) {
				cancelled_weapon = messages.getString("cancelled-weapon");
			}
			if (messages.isSet("cancelled-others")) {
				cancelled_others = messages.getString("cancelled-others");
			}
			if (messages.isSet("leveled-up")) {
				leveled_up = messages.getString("leveled-up");
			}
			if (messages.isSet("leveled-down")) {
				leveled_down = messages.getString("leveled-down");
			}
		} catch (Exception e) {
		}
	}

	public void addItem(String name, int level) {
		levels.put(name, level);
		if (!levelItemLookup.containsKey(level))
			levelItemLookup.put(level, new ArrayList<String>());
		levelItemLookup.get(level).add(name);
	}

	public Collection<String> getItemsAtLevel(int level) {
		return levelItemLookup.containsKey(level) ? levelItemLookup.get(level) : Arrays.asList();
	}

	public int getLevel(Material type) {
		return type != null && levels.containsKey(type.name().toLowerCase()) ? levels.get(type.name().toLowerCase()) : 0;
	}

	public void sendCancelledArmour(Player player, Material item) {
		if (item != null)
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', cancelled_armour.replace("%item%", item.name().replace("_", " ").toLowerCase())).replace("%level%", plugin.getSettings().getLevel(item) + ""));
	}

	public void sendCancelledTool(Player player, Material item) {
		if (item != null)
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', cancelled_tool.replace("%item%", item.name().replace("_", " ").toLowerCase())).replace("%level%", plugin.getSettings().getLevel(item) + ""));
	}

	public void sendCancelledWeapon(Player player, Material item) {
		if (item != null)
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', cancelled_weapon.replace("%item%", item.name().replace("_", " ").toLowerCase())).replace("%level%", plugin.getSettings().getLevel(item) + ""));
	}

	public void sendCancelledOthers(Player player, Material item) {
		if (item != null)
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', cancelled_others.replace("%item%", item.name().replace("_", " ").toLowerCase())).replace("%level%", plugin.getSettings().getLevel(item) + ""));
	}

	public void sendLeveledUp(Player player, int level, String item) {
		if (item != null)
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', leveled_up.replace("%item%", item).replace("%level%", level + "")));
	}
	
	public void sendLeveledDown(Player player, int level, String item) {
		if (item != null)
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', leveled_down.replace("%item%", item).replace("%level%", level + "")));
	}

}
