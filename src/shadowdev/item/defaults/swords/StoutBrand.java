package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class StoutBrand extends ToolX {

	public StoutBrand() {
		super(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Stout Brand", "stoutSword", Material.STONE_SWORD, 12.0f, true, "A sword carried by the elven knights of the 4th floor. It is quite heavy");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	
}
