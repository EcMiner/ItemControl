package me.ecminer.itemcontrol.listeners;

import me.ecminer.itemcontrol.ItemControl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ToolsListener implements Listener {

	private ItemControl plugin;

	public ToolsListener(ItemControl plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {
		Player player = evt.getPlayer();
		if (!player.hasPermission("itemcontrol.exempt"))
			if (isTool(player.getItemInHand())) {
				if (plugin.getSettings().getLevel(player.getItemInHand().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
					evt.setCancelled(true);
					plugin.getSettings().sendCancelledTool(player, player.getItemInHand().getType());
				}
			}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {
		Player player = evt.getPlayer();
		if (!player.hasPermission("itemcontrol.exempt"))
			if (isTool(player.getItemInHand())) {
				if (plugin.getSettings().getLevel(player.getItemInHand().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
					evt.setCancelled(true);
					plugin.getSettings().sendCancelledWeapon(player, player.getItemInHand().getType());
				}
			}
	}

	private boolean isTool(ItemStack item) {
		return item != null && (item.getType().name().endsWith("_PICKAXE") || item.getType().name().endsWith("_AXE") || item.getType().name().endsWith("_SHOVEL") || item.getType().name().endsWith("_HOE"));
	}

}
