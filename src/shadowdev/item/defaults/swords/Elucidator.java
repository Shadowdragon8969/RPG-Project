package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class Elucidator extends ToolX {

	public Elucidator() {
		super(ChatColor.GOLD + "[LGD] " + ChatColor.WHITE + "Elucidator", "elucidator", Material.STONE_SWORD, 180.0f, true, "An unpractically heavy one handed longsword. It can cut through almost anything.");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	@Override
	public int getEnhancements() {
		return 45;
	}
	
}
