package dev.shadow.api;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public interface IHasRecipe<T extends Recipe> {

	public T getRecipe(ItemStack i);
	
}
