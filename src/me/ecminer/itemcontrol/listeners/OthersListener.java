package me.ecminer.itemcontrol.listeners;

import me.ecminer.itemcontrol.ItemControl;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OthersListener implements Listener {

	private ItemControl plugin;

	public OthersListener(ItemControl plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {
		Player player = evt.getPlayer();
		if (!player.hasPermission("itemcontrol.exempt"))
			if (evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Block b = evt.getClickedBlock();
				if (isOther(b)) {
					try {
						if (plugin.getSettings().getLevel(b.getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
							evt.setCancelled(true);
							plugin.getSettings().sendCancelledOthers(player, b.getType());
						}
					} catch (Exception e) {
					}
				}
			}
	}

	public boolean isOther(Block b) {
		return b != null && b.getType() != null && (b.getType() == Material.ENCHANTMENT_TABLE || b.getType() == Material.WORKBENCH || b.getType() == Material.ANVIL || b.getType() == Material.BREWING_STAND || b.getType() == Material.FURNACE);
	}

}
