package dev.shadow.api;

import java.util.Map;

import org.bukkit.enchantments.Enchantment;

public interface IHasEnchanments {

	public Map<Enchantment, Integer> getEnchants();
	
}
