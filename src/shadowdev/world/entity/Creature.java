package shadowdev.world.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import dev.shadow.api.CraftingTableManager;
import dev.shadow.api.ItemCustom;
import dev.shadow.api.ObjectSet;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_15_R1.EntityCreature;
import net.minecraft.server.v1_15_R1.EntityTypes;
import shadowdev.server.ServerManager;

public class Creature {
	
	double atk, hp, spd, attackRange = 1.5;
	EntityEquipment eq;
	Class<? extends Entity> parent = null;
	List<ObjectSet<ItemCustom, Double>> loot = new ArrayList<ObjectSet<ItemCustom, Double>>();
	boolean aiAttack = false;
	EntityTypes<? extends EntityCreature> et;
	String name, id;
	List<EntityScript> scripts = new ArrayList<EntityScript>();
	Biome spawnBiome;
	boolean respawn = true;
	int timesSpawned = 0;
	ItemStack helmet, chestplate, leggings, boots, hand;
	int[] spawnWindow = new int[2];
	
	boolean hostile;
	
	public Creature(String name, String id, Biome spawnbiome, int[] spawnwindow, boolean hostile, double hp, double atk, double spd, EntityTypes<? extends EntityCreature> et) {
		this.atk = atk;
		this.hp = hp;
		this.spd = spd;
		this.et = et;
		this.name = name;
		this.id = id;
		this.hostile = hostile;
		this.spawnBiome = spawnbiome;
		this.spawnWindow = spawnwindow;
	}
	
	public void setHelmet(Material x) {
		helmet = new ItemStack(x);
	}
	
	public void setChestplate(Material x) {
		chestplate = new ItemStack(x);
	}
	
	public void setLeggings(Material x) {
		leggings = new ItemStack(x);
	}

	public void setBoots(Material x) {
		boots = new ItemStack(x);
	}
	
	public void setHand(Material x) {
		hand = new ItemStack(x);
	}
	
	public void enableAiAttack() {
		aiAttack = true;
	}
	
	public boolean usesAiAttack() {
		return aiAttack;
	}
	
	public final void addScript(EntityScript i) {
		scripts.add(i);
	}
	
	public final List<EntityScript> getScripts() {
		return scripts;
	}
	
	public final String getName() {
		return name;
	}
	
	public void setParent(Class<? extends Entity> parent) {
		this.parent = parent;
	}
	
	//public final void useLootTable(ItemCustom[] loot) {
	//	this.loot = loot;
	//}
	
	public void addLoot(ItemCustom item, double chance) {
		loot.add(new ObjectSet<ItemCustom, Double>(item, chance));
	}
	
	public void setRespawns(boolean r) {
		respawn = r;
	}
	
	public boolean doesRespawn() {
		return respawn;
	}
	
	public int getTimeSpawned() {
		return timesSpawned;
	}
	
	@SuppressWarnings("deprecation")
	public LivingEntity spawn(Location loc) {
		CreatureLivingBase clb = new CreatureLivingBase(this, loc.getWorld());
		LivingEntity e = clb.getBukkitLivingEntity();
		e.teleport(loc);
		((CraftWorld)loc.getWorld()).addEntity(clb, SpawnReason.CUSTOM);
		for (EntityScript es : scripts) {
			if (es instanceof EntitySpawnScript) {
				((EntitySpawnScript) es).onSpawn(e);
			}
		}
		clb.onSpawn();
		e.setMaxHealth(20);
		e.setCustomName(ChatColor.WHITE + name + ChatColor.RED + " " + (int) hp + ChatColor.WHITE + "/" + ChatColor.RED + (int) hp);
		e.setCustomNameVisible(true);
		if (loot != null) eq = CraftingTableManager.generateRandomEquipment(e, loot);
		e.getEquipment().setHelmet(helmet);
		e.getEquipment().setChestplate(chestplate);
		e.getEquipment().setLeggings(leggings);
		e.getEquipment().setBoots(boots);
		e.getEquipment().setItemInMainHand(hand);
		e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, (int) spd / 10, true, false));
		if (spd <= 0) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, (int) 100, true, false));
		}
		//for (EntityScript es : scripts) {
		//	if (es instanceof ChasePlayerScript) {
		//		Husk z = loc.getWorld().spawn(loc, Husk.class);
		//		z.setInvulnerable(true);
		//		parent = z;
		//		z.setSilent(true);
		//		z.setCustomName("dummy");
		//		z.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, (int) spd / 10, true, false));
		//		z.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, (int) 0, true, false));
		//	}
		//}
		if (parent != null) {
			
		}
		e.setRemoveWhenFarAway(false);
		timesSpawned++;
		return e;
	}
	
	public Class<? extends Entity> getParent() {
		return parent;
	}
	
	public void onDeath(Entity killer, Location l) {
		for (ObjectSet<ItemCustom, Double> x : loot) {
			double c = x.value / 100;
			double t = new Random().nextDouble();
			if (t < c) {
				Item it = l.getWorld().dropItemNaturally(l.add(0,1,0), CraftingTableManager.getItemFromCustom(x.key));
				if (killer != null && killer instanceof Player) {
					ServerManager.dropOwners.put(it, (Player) killer);
				}
			}
		}
	}
	
	public final int[] getSpawnWindow() {
		return spawnWindow;
	}

	public Biome getSpawnBiome() {
		return spawnBiome;
	}
	
	public String getId() {
		return id;
	}

	public double getAttack() {
		return atk;
	}

	public void setAttackRange(double i) {
		attackRange = i;
	}
	
}
