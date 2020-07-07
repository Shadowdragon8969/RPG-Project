package shadowdev.world.entity.defaults;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import shadowdev.world.entity.Creature;
import shadowdev.world.entity.CreatureLivingBase;
import shadowdev.world.entity.EntityLivingScript;

public class TaurusLivingScript implements EntityLivingScript {

	boolean first = true;
	
	@Override
	public void whenAlive(Creature c, LivingEntity e, Location l, World w) {
		CreatureLivingBase clb = CreatureLivingBase.fromLiving(e);
		if (clb.child == null) return;
		ArmorStand s = (ArmorStand) clb.child;
		s.teleport(l.add(l.getDirection().multiply(.65)));
		s.setCustomNameVisible(false);
		s.setVisible(false);
		//s.setInvulnerable(true);
		s.setArms(true);
		s.setDisabledSlots(EquipmentSlot.values());
		s.setCollidable(false);
		s.setGravity(false);
		if (s.getHelmet().getType() == Material.AIR)
			s.setHelmet(new ItemStack(Material.PLAYER_HEAD));
		if (s.getItem(EquipmentSlot.HAND).getType() == Material.AIR)
			s.setItem(EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD));
	}

}
