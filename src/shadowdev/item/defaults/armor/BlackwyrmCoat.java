package shadowdev.item.defaults.armor;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import dev.shadow.api.AttributeType;
import dev.shadow.api.AttributeValue;
import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ArmorX;

public class BlackwyrmCoat extends ArmorX {

	public BlackwyrmCoat() {
		super(ChatColor.LIGHT_PURPLE + "[EPC] " + ChatColor.WHITE + "Blackwyrm Coat", "blackcoat", Material.LEATHER_CHESTPLATE, 120, true, "A coat woven from black dragon leather. It often weighs more than its wearer.");
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
