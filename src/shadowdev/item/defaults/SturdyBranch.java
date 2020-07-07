package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.MaterialX;

public class SturdyBranch extends MaterialX {

	public SturdyBranch() {
		super(Material.STICK, "sturdyBranch", 0);
		setDisplayName(ChatColor.GRAY + "[CMN] " + ChatColor.WHITE + "Sturdy Branch");
		setLore("A plain branch that fell", "from some tree.");
		noDurability = true;
	}

	
	
}
