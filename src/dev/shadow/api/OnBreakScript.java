package dev.shadow.api;

import org.bukkit.entity.Player;

import dev.shadow.api.ItemCustom;

public interface OnBreakScript {

	public void onWeaponBreak(ItemCustom t, Player user);
	
}
