package me.ecminer.itemcontrol.listeners;

import me.ecminer.itemcontrol.ItemControl;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WeaponsListener implements Listener {

	private ItemControl plugin;

	public WeaponsListener(ItemControl plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent evt) {
		if (evt.getDamager() instanceof Player) {
			Player player = (Player) evt.getDamager();
			if (!player.hasPermission("itemcontrol.exempt"))
				if (isSword(player.getItemInHand())) {
					try {
						if (plugin.getSettings().getLevel(player.getItemInHand().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
							evt.setCancelled(true);
							plugin.getSettings().sendCancelledWeapon(player, player.getItemInHand().getType());
						}
					} catch (Exception e) {
					}
				}
		} else if (evt.getDamager() instanceof Arrow) {
			if (((Arrow) evt.getDamager()).getShooter() instanceof Player) {
				Player player = (Player) ((Arrow) evt.getDamager()).getShooter();
				if (!player.hasPermission("itemcontrol.exempt"))
					try {
						if (plugin.getSettings().getLevel(Material.BOW) > plugin.getMcCombatLevel().getCombatLevel(player)) {
							evt.setCancelled(true);
							plugin.getSettings().sendCancelledWeapon(player, Material.BOW);
						}
					} catch (Exception e) {
					}
			}
		}
	}

	@EventHandler
	public void onHangingBreakByEntity(HangingBreakByEntityEvent evt) {
		if (evt.getRemover() instanceof Player) {
			Player player = (Player) evt.getRemover();
			if (!player.hasPermission("itemcontrol.exempt")) {
				if (isSword(player.getItemInHand()) || (player.getItemInHand() != null && player.getItemInHand().getType() == Material.BOW)) {
					try {
						if (plugin.getSettings().getLevel(player.getItemInHand().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
							evt.setCancelled(true);
							plugin.getSettings().sendCancelledWeapon(player, player.getItemInHand().getType());
						}
					} catch (Exception e) {
					}
				}
			}
		}
	}

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent evt) {
		if (evt.getEntity() instanceof Arrow && ((Arrow) evt.getEntity()).getShooter() instanceof Player) {
			Player player = (Player) ((Arrow) evt.getEntity()).getShooter();
			if (!player.hasPermission("itemcontrol.exempt"))
				try {
					if (plugin.getSettings().getLevel(Material.BOW) > plugin.getMcCombatLevel().getCombatLevel(player)) {
						evt.setCancelled(true);
						plugin.getSettings().sendCancelledWeapon(player, Material.BOW);
					}
				} catch (Exception e) {
				}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {
		Player player = evt.getPlayer();
		if (!player.hasPermission("itemcontrol.exempt"))
			if ((evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) && (evt.getItem() != null && evt.getItem().getType() == Material.BOW)) {
				try {
					if (plugin.getSettings().getLevel(Material.BOW) > plugin.getMcCombatLevel().getCombatLevel(player)) {
						evt.setCancelled(true);
						plugin.getSettings().sendCancelledWeapon(player, evt.getItem().getType());
						player.updateInventory();
					}
				} catch (Exception e) {
				}
			}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {
		Player player = evt.getPlayer();
		if (isSword(player.getItemInHand()) || (player.getItemInHand() != null && player.getItemInHand().getType() == Material.BOW)) {
			try {
				if (plugin.getSettings().getLevel(player.getItemInHand().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
					evt.setCancelled(true);
					plugin.getSettings().sendCancelledWeapon(player, player.getItemInHand().getType());
				}
			} catch (Exception e) {
			}
		}
	}

	private boolean isSword(ItemStack item) {
		return item != null && (item.getType().name().endsWith("_SWORD"));
	}

}
