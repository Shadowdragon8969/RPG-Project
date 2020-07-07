package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class WoodenSword extends ToolX {

	public WoodenSword() {
		super(ChatColor.GRAY + "[CMN] " + ChatColor.WHITE + "Small Sword", "woodSword", Material.WOODEN_SWORD, 2.0f, true, "A simple wooden sword. It is prestinely crafted");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	@Override
	public int getEnhancements() {
		return 3;
	}
	
}
