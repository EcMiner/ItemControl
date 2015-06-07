package me.ecminer.itemcontrol.listeners;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import me.ecminer.itemcontrol.ItemControl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelChangeEvent;

public class LevelListener implements Listener {

	private ItemControl plugin;
	private Map<String, Integer> oldLevel = new HashMap<String, Integer>();

	public LevelListener(ItemControl plugin) {
		this.plugin = plugin;
		for (Player player : Bukkit.getOnlinePlayers()) {
			plugin.getMcCombatLevel().updateLevel(player);
			oldLevel.put(player.getName(), plugin.getMcCombatLevel().getCombatLevel(player));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent evt) {
		Player player = evt.getPlayer();
		plugin.getMcCombatLevel().updateLevel(player);
		oldLevel.put(player.getName(), plugin.getMcCombatLevel().getCombatLevel(player));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent evt) {
		Player player = evt.getPlayer();
		oldLevel.remove(player.getName());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCombatLevelChange(McMMOPlayerLevelChangeEvent evt) {
		Player player = evt.getPlayer();
		plugin.getMcCombatLevel().updateLevel(player);
		int old = oldLevel.get(player.getName());
		int newLvl = plugin.getMcCombatLevel().getCombatLevel(player);
		if (old != newLvl) {
			if (newLvl > old) {
				for (int i = old + 1; i <= newLvl; i++) {
					Collection<String> items = plugin.getSettings().getItemsAtLevel(i);
					for (String item : items) {
						plugin.getSettings().sendLeveledUp(player, i, item.replace("_", " "));
					}
				}
			} else if (newLvl < old) {
				for (int i = old; i > newLvl; i--) {
					Collection<String> items = plugin.getSettings().getItemsAtLevel(i);
					for (String item : items) {
						plugin.getSettings().sendLeveledDown(player, newLvl, item);
					}
				}
			}
			oldLevel.put(player.getName(), newLvl);
		}
	}

}
