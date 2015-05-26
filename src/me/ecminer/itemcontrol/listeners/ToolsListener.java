package me.ecminer.itemcontrol.listeners;

import me.ecminer.itemcontrol.ItemControl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
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
				try {
					if (plugin.getSettings().getLevel(player.getItemInHand().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
						evt.setCancelled(true);
						plugin.getSettings().sendCancelledTool(player, player.getItemInHand().getType());
					}
				} catch (Exception e) {
				}
			}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent evt) {
		if (evt.getDamager() instanceof Player) {
			Player player = (Player) evt.getDamager();
			if (!player.hasPermission("itemcontrol.exempt"))
				if (isTool(player.getItemInHand())) {
					try {
						if (plugin.getSettings().getLevel(player.getItemInHand().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
							evt.setCancelled(true);
							plugin.getSettings().sendCancelledTool(player, player.getItemInHand().getType());
						}
					} catch (Exception e) {
					}
				}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {
		Player player = evt.getPlayer();
		if (evt.getAction() == Action.RIGHT_CLICK_BLOCK)
			if (!player.hasPermission("itemcontrol.exempt"))
				if (isTool(player.getItemInHand())) {
					try {
						if (plugin.getSettings().getLevel(player.getItemInHand().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
							evt.setCancelled(true);
							plugin.getSettings().sendCancelledTool(player, player.getItemInHand().getType());
						}
					} catch (Exception e) {
					}
				}
	}

	@EventHandler
	public void onHangingBreakByEntity(HangingBreakByEntityEvent evt) {
		if (evt.getRemover() instanceof Player) {
			Player player = (Player) evt.getRemover();
			if (!player.hasPermission("itemcontrol.exempt")) {
				if (isTool(player.getItemInHand())) {
					try {
						if (plugin.getSettings().getLevel(player.getItemInHand().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
							evt.setCancelled(true);
							plugin.getSettings().sendCancelledTool(player, player.getItemInHand().getType());
						}
					} catch (Exception e) {
					}
				}
			}
		}
	}

	private boolean isTool(ItemStack item) {
		return item != null && (item.getType().name().endsWith("_PICKAXE") || item.getType().name().endsWith("_AXE") || item.getType().name().endsWith("_SPADE") || item.getType().name().endsWith("_HOE"));
	}

}
