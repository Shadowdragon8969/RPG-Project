package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.FoodX;

public class CookedFish extends FoodX {

	public CookedFish() {
		super(Material.COOKED_COD, "cookfish", 3, 0.9f);
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.YELLOW + "Cooked Fish");
		setLore("A cooked fish.", "The skin on the outside", "is quite crispy.");
		noDurability = true;
	}

}
