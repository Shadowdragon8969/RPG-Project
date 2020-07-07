package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class LambentLight extends ToolX {

	public LambentLight() {
		super(ChatColor.GOLD + "[LGD] " + ChatColor.WHITE + "Lambent Light", "lambentLight", Material.IRON_SWORD, 150.0f, true, "A handcrafted rapier forged for speed. Most smiths consider this a masterpiece.");
		noDurability = true;
		attackSpeed = -0.8;
	}
	
	@Override
	public int getEnhancements() {
		return 25;
	}
	
}
