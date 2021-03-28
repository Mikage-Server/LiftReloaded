package com.minecraftcorp.lift.bukkit.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

import com.minecraftcorp.lift.bukkit.LiftPlugin;
import com.minecraftcorp.lift.bukkit.model.BukkitConfig;

public class VehicleListener implements Listener {

	private final LiftPlugin plugin = LiftPlugin.INSTANCE;
	private final BukkitConfig config = BukkitConfig.INSTANCE;

	public VehicleListener() {
		Bukkit.getServer().getPluginManager().registerEvents(this, LiftPlugin.INSTANCE);
	}

	/**
	 * Prevent players from ejecting vehicles while within an elevator
	 */
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onEntityEject(VehicleExitEvent event){
		LivingEntity ejector = event.getExited();
		if (plugin.isInNoLift(event.getVehicle().getUniqueId())) {
			return;
		}
		if (ejector instanceof Player) {
			ejector.sendMessage(config.getUnsafe());
		}
		event.setCancelled(true);
		plugin.logDebug("Canceled ejection for " + ejector);
	}
}