package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class NumbDagger extends ToolX {

	public NumbDagger() {
		super(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Numb Dagger", "numbdagger", Material.ARROW, 23.0f, true, "A dagger with stun inflicting properties.");
		noDurability = true;
		attackSpeed = 0;
	}
	
	
}
