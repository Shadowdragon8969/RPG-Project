package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class ElvenStoutSword extends ToolX {

	public ElvenStoutSword() {
		super(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Elven Stout Sword", "elfStoutSword", Material.STONE_SWORD, 17.0f, true, "A sword carried by the elven knights of the 4th floor. It is quite heavy");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	
}
