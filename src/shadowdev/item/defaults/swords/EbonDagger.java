package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class EbonDagger extends ToolX {

	public EbonDagger() {
		super(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Ebon Dagger", "ebondagger", Material.ARROW, 23.0f, true, "A short reliable dagger. It smells like the forest.");
		noDurability = true;
		attackSpeed = 0;
	}
	
	@Override
	public int getEnhancements() {
		return 8;
	}
	
}
