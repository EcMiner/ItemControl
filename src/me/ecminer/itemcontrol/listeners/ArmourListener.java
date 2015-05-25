package me.ecminer.itemcontrol.listeners;

import me.ecminer.itemcontrol.ItemControl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ArmourListener implements Listener {

	private ItemControl plugin;

	public ArmourListener(ItemControl plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent evt) {
		Player player = (Player) evt.getPlayer();
		if (!player.hasPermission("itemcontrol.exempt"))
			checkInventory(player);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent evt) {
		if (evt.getEntity() instanceof Player) {
			Player player = (Player) evt.getEntity();
			if (!player.hasPermission("itemcontrol.exempt"))
				checkInventory(player);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent evt) {
		Player player = (Player) evt.getWhoClicked();
		if (!player.hasPermission("itemcontrol.exempt"))
			if (isArmour(evt.getCurrentItem()) || (evt.getCursor() != null && isArmour(evt.getCursor())) || (evt.getAction() == InventoryAction.HOTBAR_SWAP && evt.getSlotType() == SlotType.ARMOR)) {
				final String playerName = player.getName();

				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

					@Override
					public void run() {
						Player player = Bukkit.getPlayer(playerName);
						if (player != null) {
							checkInventory(player);
						}
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() {
								Player player = Bukkit.getPlayer(playerName);
								if (player != null) {
									checkInventory(player);
								}
							}

						}, 2);
					}

				}, 1);
			}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {
		Player player = evt.getPlayer();
		if (!player.hasPermission("itemcontrol.exempt"))
			if ((evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) && isArmour(evt.getItem())) {
				final String playerName = player.getName();
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

					@Override
					public void run() {
						Player player = Bukkit.getPlayer(playerName);
						if (player != null)
							checkInventory(player);
					}

				}, 1);
			}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		Player player = evt.getPlayer();
		if (!player.hasPermission("itemcontrol.exempt"))
			checkInventory(player);
	}

	private boolean isArmour(ItemStack item) {
		return item != null && (item.getType().name().endsWith("_HELMET") || item.getType().name().endsWith("_CHESTPLATE") || item.getType().name().endsWith("_LEGGINGS") || item.getType().name().endsWith("_BOOTS"));
	}

	public void checkInventory(Player player) {
		PlayerInventory inv = player.getInventory();
		if (inv.getHelmet() != null) {
			if (plugin.getSettings().getLevel(inv.getHelmet().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
				plugin.getSettings().sendCancelledArmour(player, inv.getHelmet().getType());
				plugin.addItemToInventory(player, inv.getHelmet());
				inv.setHelmet(null);
			}
		}
		if (inv.getChestplate() != null) {
			if (plugin.getSettings().getLevel(inv.getChestplate().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
				plugin.getSettings().sendCancelledArmour(player, inv.getChestplate().getType());
				plugin.addItemToInventory(player, inv.getChestplate());
				inv.setChestplate(null);
			}
		}
		if (inv.getLeggings() != null) {
			if (plugin.getSettings().getLevel(inv.getLeggings().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
				plugin.getSettings().sendCancelledArmour(player, inv.getLeggings().getType());
				plugin.addItemToInventory(player, inv.getLeggings());
				inv.setLeggings(null);
			}
		}
		if (inv.getBoots() != null) {
			if (plugin.getSettings().getLevel(inv.getBoots().getType()) > plugin.getMcCombatLevel().getCombatLevel(player)) {
				plugin.getSettings().sendCancelledArmour(player, inv.getBoots().getType());
				plugin.addItemToInventory(player, inv.getBoots());
				inv.setBoots(null);
			}
		}
	}

}
