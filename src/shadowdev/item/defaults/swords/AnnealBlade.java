package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class AnnealBlade extends ToolX {

	public AnnealBlade() {
		super(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Anneal Blade", "annealBlade", Material.IRON_SWORD, 16.0f, true, "A plain but strong longsword. It requires a firm grip to wield");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	@Override
	public int getEnhancements() {
		return 8;
	}
	
}
