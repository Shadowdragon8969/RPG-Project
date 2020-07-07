package shadowdev.item.defaults.armor;

import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import dev.shadow.api.AttributeType;
import dev.shadow.api.AttributeValue;
import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ArmorX;

public class KoboldBoots extends ArmorX {

	public KoboldBoots() {
		super(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Kobold Boots", "koboldboots", Material.IRON_BOOTS, 3, true, "A pair of iron boots worn by Kobold Soldiers.");
	}
	
	@Override
	public AttributeValue getItemAttribute(AttributeType t) {
		if (t == AttributeType.ARMOR) {
		AttributeValue v = super.getItemAttribute(t);
		v.setSlot(EquipmentSlot.FEET);
		return v;
		}else return null;
	}
	
}
