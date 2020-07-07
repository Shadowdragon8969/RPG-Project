package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class PlainRapier extends ToolX {

	public PlainRapier() {
		super(ChatColor.GRAY + "[CMN] " + ChatColor.WHITE + "Plain Rapier", "plainRapier", Material.IRON_SWORD, 1.0f, true, "A very unspecial rapier.");
		noDurability = true;
		attackSpeed = -0.8;
	}
	
	
}
