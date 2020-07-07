package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.FoodX;

public class Bread extends FoodX {

	public Bread() {
		super(Material.BREAD, "bread", 5, 1.9f);
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Bread Loaf");
		setLore("A full loaf of bread.");
		noDurability = true;
	}

	
	
}
