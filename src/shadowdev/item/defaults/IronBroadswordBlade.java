package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.MaterialX;

public class IronBroadswordBlade extends MaterialX {

	public IronBroadswordBlade() {
		super(Material.IRON_INGOT, "ironbroadswordblade", 25);
		setDisplayName(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Iron Broadsword Blade");
		setLore("A sturdy broadsword blade.");
		noDurability = true;
	}

	
	
}
