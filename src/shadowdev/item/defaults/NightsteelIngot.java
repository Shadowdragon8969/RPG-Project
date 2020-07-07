package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.MaterialX;

public class NightsteelIngot extends MaterialX {

	public NightsteelIngot() {
		super(Material.NETHER_BRICK, "nightsteelIngot", 20);
		setDisplayName(ChatColor.LIGHT_PURPLE + "[EPC] " + ChatColor.WHITE + "Nightsteel Ingot");
		setLore("A chunk of cold steel.", "The color looks unnatural.");
		noDurability = true;
	}

	
	
}
