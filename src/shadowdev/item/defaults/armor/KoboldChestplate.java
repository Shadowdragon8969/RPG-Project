package shadowdev.item.defaults.armor;

import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import dev.shadow.api.AttributeType;
import dev.shadow.api.AttributeValue;
import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ArmorX;

public class KoboldChestplate extends ArmorX {

	public KoboldChestplate() {
		super(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Kobold Chestplate", "koboldchestplate", Material.IRON_CHESTPLATE, 9, true, "An iron chestplate worn by Kobold Soldiers.");
		defenseB = 9;
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
