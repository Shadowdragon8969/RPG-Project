package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.MaterialX;

public class SmallBranch extends MaterialX {

	public SmallBranch() {
		super(Material.DEAD_BUSH, "smbranch", 10);
		setDisplayName(ChatColor.GRAY + "[CMN] " + ChatColor.WHITE + "Small Branch");
		setLore("A small tree branch.", "It looks good for starting fires.");
		noDurability = true;
	}

	
	
}
