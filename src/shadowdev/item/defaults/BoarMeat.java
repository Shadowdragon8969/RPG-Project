package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.FoodX;

public class BoarMeat extends FoodX {

	public BoarMeat() {
		super(Material.PORKCHOP, "boarmeat", 1, 0.4f);
		setDisplayName(ChatColor.GRAY + "[CMN] " + ChatColor.WHITE + "Boar Meat");
		setLore("A slab of hard meat that", "was once part of a wild boar.");
		noDurability = true;
	}

}
