package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.MaterialX;

public class IronIngot extends MaterialX {

	public IronIngot() {
		super(Material.IRON_INGOT, "ironIngot", 20);
		setDisplayName(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Iron Ingot");
		setLore("A solid bar of iron.", "It could take many forms if forged.");
		noDurability = true;
	}

	
	
}
