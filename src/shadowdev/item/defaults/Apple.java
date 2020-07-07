package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.FoodX;

public class Apple extends FoodX {

	public Apple() {
		super(Material.APPLE, "apple", 2, 1.0f);
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Apple");
		setLore("A bright red apple.");
		noDurability = true;
	}

	
	
}
