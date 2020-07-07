package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.FoodX;

public class Wheat extends FoodX {

	public Wheat() {
		super(Material.WHEAT, "wheat", 1, 0.3f);
		setDisplayName(ChatColor.GRAY + "[CMN] " + ChatColor.WHITE + "Wheat");
		setLore("A bundle of wheat.");
		noDurability = true;
	}

	
	
}
