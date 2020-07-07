package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.FoodX;

public class CookedBoarMeat extends FoodX {

	public CookedBoarMeat() {
		super(Material.COOKED_PORKCHOP, "cookboarmeat", 5, 1.4f);
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Cooked Boar Meat");
		setLore("A juicy chunk of boar meat.", "The heat has softened the", "texture.");
		noDurability = true;
	}

}
