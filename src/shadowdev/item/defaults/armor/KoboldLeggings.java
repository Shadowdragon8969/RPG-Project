package shadowdev.item.defaults.armor;

import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import dev.shadow.api.AttributeType;
import dev.shadow.api.AttributeValue;
import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ArmorX;

public class KoboldLeggings extends ArmorX {

	public KoboldLeggings() {
		super(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Kobold Leggings", "koboldleggings", Material.IRON_LEGGINGS, 7, true, "A set of iron leggings worn by Kobold Soldiers.");
		defenseB = 7;
	}
	
	@Override
	public AttributeValue getItemAttribute(AttributeType t) {
		if (t == AttributeType.ARMOR) {
		AttributeValue v = super.getItemAttribute(t);
		v.setSlot(EquipmentSlot.LEGS);
		return v;
		}else return null;
	}
	
}
