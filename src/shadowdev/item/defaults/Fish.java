package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.FoodX;

public class Fish extends FoodX {

	public Fish() {
		super(Material.COD, "fish", 1, 0.4f);
		setDisplayName(ChatColor.GRAY + "[CMN] " + ChatColor.WHITE + "Fish");
		setLore("A plain fish.", "It would be tasty if cooked");
		noDurability = true;
	}

}
