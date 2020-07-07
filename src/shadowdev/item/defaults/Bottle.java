package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.MaterialX;

public class Bottle extends MaterialX {

	public Bottle() {
		super(Material.GLASS_BOTTLE, "bottle", 5);
		setDisplayName(ChatColor.GRAY + "[CMN] " + ChatColor.WHITE + "Empty Bottle");
		setLore("A plain glass bottle.");
		noDurability = true;
	}

	
	
}
