package shadowdev.item.defaults.armor;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import dev.shadow.api.AttributeType;
import dev.shadow.api.AttributeValue;
import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ArmorX;

public class CoatOfMidnight extends ArmorX {

	public CoatOfMidnight() {
		super(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Coat of Midnight", "coatofmidnight", Material.LEATHER_CHESTPLATE, 16, true, "A tough black coat. It is much more protective than it looks.");
		m = Color.BLACK;
	}
	
	@Override
	public AttributeValue getItemAttribute(AttributeType t) {
		if (t == AttributeType.ARMOR) {
		AttributeValue v = super.getItemAttribute(t);
		v.setSlot(EquipmentSlot.CHEST);
		return v;
		}else return null;
	}
	
}
