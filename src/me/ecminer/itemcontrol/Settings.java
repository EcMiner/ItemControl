package me.ecminer.itemcontrol;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Settings {

	private Map<String, Integer> levels = new HashMap<String, Integer>();
	private ItemControl plugin;
	private String cancelled_armour = "&cYou can't wear %item%, you need to be level &6%level%";
	private String cancelled_tool = "&cYou can't use a %item%, you need to be level &6%level%";
	private String cancelled_weapon = "&cYou can't use a %item%, you need to be level &6%level%";
	private String cancelled_others = "&cYou can't use a %item%, you need to be level &6%level%";

	public Settings(ItemControl plugin, FileConfiguration config, FileConfiguration messages) {
		this.plugin = plugin;

		try {
			levels.put("leather_helmet", config.getInt("armour.helmet.leather"));
			levels.put("chainmail_helmet", config.getInt("armour.helmet.chain"));
			levels.put("gold_helmet", config.getInt("armour.helmet.gold"));
			levels.put("iron_helmet", config.getInt("armour.helmet.iron"));
			levels.put("diamond_helmet", config.getInt("armour.helmet.diamond"));

			levels.put("leather_chestplate", config.getInt("armour.chestplate.leather"));
			levels.put("chainmail_chestplate", config.getInt("armour.chestplate.chain"));
			levels.put("gold_chestplate", config.getInt("armour.chestplate.gold"));
			levels.put("iron_chestplate", config.getInt("armour.chestplate.iron"));
			levels.put("diamond_chestplate", config.getInt("armour.chestplate.diamond"));

			levels.put("leather_leggings", config.getInt("armour.leggings.leather"));
			levels.put("chainmail_leggings", config.getInt("armour.leggings.chain"));
			levels.put("gold_leggings", config.getInt("armour.leggings.gold"));
			levels.put("iron_leggings", config.getInt("armour.leggings.iron"));
			levels.put("diamond_leggings", config.getInt("armour.leggings.diamond"));

			levels.put("leather_boots", config.getInt("armour.boots.leather"));
			levels.put("chainmail_boots", config.getInt("armour.boots.chain"));
			levels.put("gold_boots", config.getInt("armour.boots.gold"));
			levels.put("iron_boots", config.getInt("armour.boots.iron"));
			levels.put("diamond_boots", config.getInt("armour.boots.diamond"));

			levels.put("bow", config.getInt("weapons.bow"));
			levels.put("wood_sword", config.getInt("weapons.wood-sword"));
			levels.put("stone_sword", config.getInt("weapons.stone-sword"));
			levels.put("gold_sword", config.getInt("weapons.gold-sword"));
			levels.put("iron_sword", config.getInt("weapons.iron-sword"));
			levels.put("diamond_sword", config.getInt("weapons.diamond-sword"));

			levels.put("wood_pickaxe", config.getInt("tools.pickaxe.wood"));
			levels.put("stone_pickaxe", config.getInt("tools.pickaxe.stone"));
			levels.put("gold_pickaxe", config.getInt("tools.pickaxe.gold"));
			levels.put("iron_pickaxe", config.getInt("tools.pickaxe.iron"));
			levels.put("diamond_pickaxe", config.getInt("tools.pickaxe.diamond"));

			levels.put("wood_spade", config.getInt("tools.shovel.wood"));
			levels.put("stone_spade", config.getInt("tools.shovel.stone"));
			levels.put("gold_spade", config.getInt("tools.shovel.gold"));
			levels.put("iron_spade", config.getInt("tools.shovel.iron"));
			levels.put("diamond_spade", config.getInt("tools.shovel.diamond"));

			levels.put("wood_axe", config.getInt("tools.axe.wood"));
			levels.put("stone_axe", config.getInt("tools.axe.stone"));
			levels.put("gold_axe", config.getInt("tools.axe.gold"));
			levels.put("iron_axe", config.getInt("tools.axe.iron"));
			levels.put("diamond_axe", config.getInt("tools.axe.diamond"));

			levels.put("wood_hoe", config.getInt("tools.hoe.wood"));
			levels.put("stone_hoe", config.getInt("tools.hoe.stone"));
			levels.put("gold_hoe", config.getInt("tools.hoe.gold"));
			levels.put("iron_hoe", config.getInt("tools.hoe.iron"));
			levels.put("diamond_hoe", config.getInt("tools.hoe.diamond"));

			levels.put("enchantment_table", config.getInt("others.enchantment-table"));
			levels.put("workbench", config.getInt("others.crafting-table"));
			levels.put("anvil", config.getInt("others.anvil"));
			levels.put(Material.BREWING_STAND.name().toLowerCase(), config.getInt("others.brewing_stand"));
			levels.put("furnace", config.getInt("others.furnace"));

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
		} catch (Exception e) {
		}
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

}
