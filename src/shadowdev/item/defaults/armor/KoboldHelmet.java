package shadowdev.item.defaults.armor;

import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import dev.shadow.api.AttributeType;
import dev.shadow.api.AttributeValue;
import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ArmorX;

public class KoboldHelmet extends ArmorX {

	public KoboldHelmet() {
		super(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Kobold Helmet", "koboldhelmet", Material.CHISELED_STONE_BRICKS, 5, true, "An iron helmet worn by Kobold Soldiers.");
		defenseB = 5;
	}
	
	@Override
	public AttributeValue getItemAttribute(AttributeType t) {
		if (t == AttributeType.ARMOR) {
		AttributeValue v = super.getItemAttribute(t);
		v.setSlot(EquipmentSlot.HEAD);
		return v;
		}else return null;
	}
	
}
