package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class IronRapier extends ToolX {

	public IronRapier() {
		super(ChatColor.GRAY + "[CMN] " + ChatColor.WHITE + "Iron Rapier", "ironRapier", Material.IRON_SWORD, 4.0f, true, "A cheap rapier. It looks unbelievably plain.");
		noDurability = true;
		attackSpeed = -0.8;
	}
	
	@Override
	public int getEnhancements() {
		return 6;
	}
	
}
