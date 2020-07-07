package shadowdev.utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;

import dev.shadow.api.AttributeType;
import dev.shadow.api.CraftingTableManager;
import net.minecraft.server.v1_15_R1.PacketPlayOutAnimation;
import shadowdev.item.ToolX;
import shadowdev.server.ServerManager;

public class SwordSkillUtils {

	public static void slashUpDown(Player pl, Color c, double dist, double dmg) {
		ToolX tx = (ToolX) CraftingTableManager.isHoldingCustomItem(pl);
		if (tx != null) {
			for (double t = 1; t < 3.5; t += 0.1) {
				Location l = pl.getEyeLocation();
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				l.add(x, y, z);
				for (LivingEntity e : pl.getWorld().getLivingEntities()) {
					if (e != pl && e.getBoundingBox().contains(l.getX(), l.getY(), l.getZ())) {
						ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
					}
				}
			}
		}else return;
		new BukkitRunnable() {
			int t = 45;
			Location l = pl.getEyeLocation();
			boolean hashit = false;
			@Override
			public void run() {
				if (!pl.isOnline()) { this.cancel(); return; }
				t -= 10;
				double x = pl.getEyeLocation().getDirection().getX() * dist;
				double y = (pl.getEyeLocation().getDirection().getY() + Math.sin(Math.toRadians(t))) * dist;
				double z = pl.getEyeLocation().getDirection().getZ() * dist;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), c, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getBoundingBox().contains(l.getX() + x, l.getY() + y, l.getZ() + z)) {
							ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
							Bukkit.getServer().getPluginManager().callEvent(new EntityDamageEvent(e, DamageCause.CUSTOM, 0));
							hashit = true;
						}
					}
				}
				if (t < -30) {
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
	public static void slashDownUp(Player pl, Color c, double dist, double dmg) {
		ToolX tx = (ToolX) CraftingTableManager.isHoldingCustomItem(pl);
		if (tx != null) {
			for (double t = 1; t < 3.5; t += 0.1) {
				Location l = pl.getEyeLocation();
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				l.add(x, y, z);
				for (LivingEntity e : pl.getWorld().getLivingEntities()) {
					if (e != pl && e.getBoundingBox().contains(l.getX(), l.getY(), l.getZ())) {
						ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
					}
				}
			}
		}else return;
		new BukkitRunnable() {
			int t = -30;
			Location l = pl.getEyeLocation();
			boolean hashit = false;
			@Override
			public void run() {
				if (!pl.isOnline()) { this.cancel(); return; }
				t += 10;
				double x = pl.getEyeLocation().getDirection().getX() * dist;
				double y = (pl.getEyeLocation().getDirection().getY() + Math.sin(Math.toRadians(t))) * dist;
				double z = pl.getEyeLocation().getDirection().getZ() * dist;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), c, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getBoundingBox().contains(l.getX() + x, l.getY() + y, l.getZ() + z)) {
							ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
							Bukkit.getServer().getPluginManager().callEvent(new EntityDamageEvent(e, DamageCause.CUSTOM, 0));
							hashit = true;
						}
					}
				}
				if (t > 45) {
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
	public static void slashLeftRight(Player pl, Color c, double dist, double dmg) {
		ToolX tx = (ToolX) CraftingTableManager.isHoldingCustomItem(pl);
		if (tx != null) {
			for (double t = 1; t < 3.5; t += 0.1) {
				Location l = pl.getEyeLocation();
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				l.add(x, y, z);
				for (LivingEntity e : pl.getWorld().getLivingEntities()) {
					if (e != pl && e.getBoundingBox().contains(l.getX(), l.getY(), l.getZ())) {
						ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
					}
				}
			}
		}else return;
		new BukkitRunnable() {
			int t = 45;
			boolean hashit = false;
			Location l = pl.getEyeLocation();
			@Override
			public void run() {
				if (!pl.isOnline()) { this.cancel(); return; }
				t -= 10;
				double x = Math.cos(Math.toRadians(pl.getEyeLocation().getYaw() + 90 - t)) * dist;
				double y = pl.getEyeLocation().getDirection().getY() * dist;
				double z = Math.sin(Math.toRadians(pl.getEyeLocation().getYaw() + 90 - t)) * dist;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), c, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getBoundingBox().contains(l.getX() + x, l.getY() + y, l.getZ() + z)) {
							ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
							Bukkit.getServer().getPluginManager().callEvent(new EntityDamageEvent(e, DamageCause.CUSTOM, 0));
							hashit = true;
						}
					}
				}
				if (t < -30) {
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
	public static void slashRightLeft(Player pl, Color c, double dist, double dmg) {
		ToolX tx = (ToolX) CraftingTableManager.isHoldingCustomItem(pl);
		if (tx != null) {
			for (double t = 1; t < 3.5; t += 0.1) {
				Location l = pl.getEyeLocation();
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				l.add(x, y, z);
				for (LivingEntity e : pl.getWorld().getLivingEntities()) {
					if (e != pl && e.getBoundingBox().contains(l.getX(), l.getY(), l.getZ())) {
						ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
					}
				}
			}
		}else return;
		new BukkitRunnable() {
			int t = 45;
			boolean hashit = false;
			Location l = pl.getEyeLocation();
			@Override
			public void run() {
				if (!pl.isOnline()) { this.cancel(); return; }
				t -= 10;
				double x = Math.cos(Math.toRadians(pl.getEyeLocation().getYaw() + 90 + t)) * dist;
				double y = pl.getEyeLocation().getDirection().getY() * dist;
				double z = Math.sin(Math.toRadians(pl.getEyeLocation().getYaw() + 90 + t)) * dist;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), c, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getBoundingBox().contains(l.getX() + x, l.getY() + y, l.getZ() + z)) {
							ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
							Bukkit.getServer().getPluginManager().callEvent(new EntityDamageEvent(e, DamageCause.CUSTOM, 0));
							hashit = true;
						}
					}
				}
				if (t < -30) {
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
	public static void slashLeftDiagDown(Player pl, Color c, double dist, double dmg) {
		ToolX tx = (ToolX) CraftingTableManager.isHoldingCustomItem(pl);
		if (tx != null) {
			for (double t = 1; t < 3.5; t += 0.1) {
				Location l = pl.getEyeLocation();
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				l.add(x, y, z);
				for (LivingEntity e : pl.getWorld().getLivingEntities()) {
					if (e != pl && e.getBoundingBox().contains(l.getX(), l.getY(), l.getZ())) {
						ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
					}
				}
			}
		}else return;
		new BukkitRunnable() {
			int t = 45;
			boolean hashit = false;
			Location l = pl.getEyeLocation();
			@Override
			public void run() {
				if (!pl.isOnline()) { this.cancel(); return; }
				t -= 10;
				double x = Math.cos(Math.toRadians(pl.getEyeLocation().getYaw() + 90 - t)) * dist;
				double y = (pl.getEyeLocation().getDirection().getY() + Math.sin(Math.toRadians(t))) * dist;
				double z = Math.sin(Math.toRadians(pl.getEyeLocation().getYaw() + 90 - t)) * dist;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), c, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getBoundingBox().contains(l.getX() + x, l.getY() + y, l.getZ() + z)) {
							ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
							Bukkit.getServer().getPluginManager().callEvent(new EntityDamageEvent(e, DamageCause.CUSTOM, 0));
							hashit = true;
						}
					}
				}
				if (t < -30) {
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
	public static void slashRightDiagDown(Player pl, Color c, double dist, double dmg) {
		ToolX tx = (ToolX) CraftingTableManager.isHoldingCustomItem(pl);
		if (tx != null) {
			for (double t = 1; t < 3.5; t += 0.1) {
				Location l = pl.getEyeLocation();
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				l.add(x, y, z);
				for (LivingEntity e : pl.getWorld().getLivingEntities()) {
					if (e != pl && e.getBoundingBox().contains(l.getX(), l.getY(), l.getZ())) {
						ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
					}
				}
			}
		}else return;
		new BukkitRunnable() {
			int t = 45;
			boolean hashit = false;
			Location l = pl.getEyeLocation();
			@Override
			public void run() {
				if (!pl.isOnline()) { this.cancel(); return; }
				t -= 10;
				double x = Math.cos(Math.toRadians(pl.getEyeLocation().getYaw() + 90 + t)) * dist;
				double y = (pl.getEyeLocation().getDirection().getY() + Math.sin(Math.toRadians(t))) * dist;
				double z = Math.sin(Math.toRadians(pl.getEyeLocation().getYaw() + 90 + t)) * dist;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), c, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getBoundingBox().contains(l.getX() + x, l.getY() + y, l.getZ() + z)) {
							ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
							Bukkit.getServer().getPluginManager().callEvent(new EntityDamageEvent(e, DamageCause.CUSTOM, 0));
							hashit = true;
						}
					}
				}
				if (t < -30) {
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
	public static void slashRightDiagUp(Player pl, Color c, double dist, double dmg) {
		ToolX tx = (ToolX) CraftingTableManager.isHoldingCustomItem(pl);
		if (tx != null) {
			for (double t = 1; t < 3.5; t += 0.1) {
				Location l = pl.getEyeLocation();
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				l.add(x, y, z);
				for (LivingEntity e : pl.getWorld().getLivingEntities()) {
					if (e != pl && e.getBoundingBox().contains(l.getX(), l.getY(), l.getZ())) {
						ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
					}
				}
			}
		}else return;
		new BukkitRunnable() {
			int t = 45;
			boolean hashit = false;
			Location l = pl.getEyeLocation();
			@Override
			public void run() {
				if (!pl.isOnline()) { this.cancel(); return; }
				t -= 10;
				double x = Math.cos(Math.toRadians(pl.getEyeLocation().getYaw() + 90 - t)) * dist;
				double y = (pl.getEyeLocation().getDirection().getY() - Math.sin(Math.toRadians(t))) * dist;
				double z = Math.sin(Math.toRadians(pl.getEyeLocation().getYaw() + 90 - t)) * dist;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), c, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getBoundingBox().contains(l.getX() + x, l.getY() + y, l.getZ() + z)) {
							ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
							Bukkit.getServer().getPluginManager().callEvent(new EntityDamageEvent(e, DamageCause.CUSTOM, 0));
							hashit = true;
						}
					}
				}
				if (t < -30) {
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
	public static void slashLeftDiagUp(Player pl, Color c, double dist, double dmg) {
		ToolX tx = (ToolX) CraftingTableManager.isHoldingCustomItem(pl);
		if (tx != null) {
			for (double t = 1; t < 3.5; t += 0.1) {
				Location l = pl.getEyeLocation();
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				l.add(x, y, z);
				for (LivingEntity e : pl.getWorld().getLivingEntities()) {
					if (e != pl && e.getBoundingBox().contains(l.getX(), l.getY(), l.getZ())) {
						ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
					}
				}
			}
		}else return;
		new BukkitRunnable() {
			int t = 45;
			boolean hashit = false;
			Location l = pl.getEyeLocation();
			@Override
			public void run() {
				if (!pl.isOnline()) { this.cancel(); return; }
				t -= 10;
				double x = Math.cos(Math.toRadians(pl.getEyeLocation().getYaw() + 90 + t)) * dist;
				double y = (pl.getEyeLocation().getDirection().getY() - Math.sin(Math.toRadians(t))) * dist;
				double z = Math.sin(Math.toRadians(pl.getEyeLocation().getYaw() + 90 + t)) * dist;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), c, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getBoundingBox().contains(l.getX() + x, l.getY() + y, l.getZ() + z)) {
							ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
							Bukkit.getServer().getPluginManager().callEvent(new EntityDamageEvent(e, DamageCause.CUSTOM, 0));
							hashit = true;
						}
					}
				}
				if (t < -30) {
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
	public static void stab(Player pl, Color c, double dist, double dmg) {
		ToolX tx = (ToolX) CraftingTableManager.isHoldingCustomItem(pl);
		if (tx != null) {
			for (double t = 1; t < 3.5; t += 0.1) {
				Location l = pl.getEyeLocation();
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				l.add(x, y, z);
				for (LivingEntity e : pl.getWorld().getLivingEntities()) {
					if (e != pl && e.getBoundingBox().contains(l.getX(), l.getY(), l.getZ())) {
						ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
					}
				}
			}
		}else return;
		new BukkitRunnable() {
			double t = 0;
			boolean hashit = false;
			Location l = pl.getEyeLocation();
			@Override
			public void run() {
				if (!pl.isOnline()) { this.cancel(); return; }
				t += 0.3;
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), c, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getBoundingBox().contains(l.getX() + x, l.getY() + y, l.getZ() + z)) {
							ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
							Bukkit.getServer().getPluginManager().callEvent(new EntityDamageEvent(e, DamageCause.CUSTOM, 0));
							hashit = true;
						}
					}
				}
				if (t > dist) {
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
	public static void spin(Player pl, Color c, double dist, double dmg) {
		ToolX tx = (ToolX) CraftingTableManager.isHoldingCustomItem(pl);
		if (tx != null) {
			
		}else return;
		new BukkitRunnable() {
			int t = 0;
			boolean hashit = false;
			Location l = pl.getEyeLocation();
			@Override
			public void run() {
				if (!pl.isOnline()) { this.cancel(); return; }
				t += 36;
				l = pl.getLocation();
				l.setYaw((float) (pl.getLocation().getYaw() + 36));
				pl.teleport(l);
				for (Player p : pl.getWorld().getPlayers()) {
					if (p.getLocation().distance(pl.getLocation()) < 24) {
						CraftPlayer c = (CraftPlayer) pl;
						CraftPlayer ca = ((CraftPlayer)p);
						PacketPlayOutAnimation a = new PacketPlayOutAnimation(c.getHandle(), 0);
						ca.getHandle().playerConnection.sendPacket(a);
					}
				}
				double x = pl.getEyeLocation().getDirection().getX() * dist;
				double y = pl.getEyeLocation().getDirection().getY() * dist + 1.5;
				double z = pl.getEyeLocation().getDirection().getZ() * dist;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), c, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getLocation().distance(pl.getLocation()) < dist) {
							ServerManager.damageEntity(e, tx.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2 * dmg, pl);
							Bukkit.getServer().getPluginManager().callEvent(new EntityDamageEvent(e, DamageCause.CUSTOM, 0));
							hashit = true;
						}
					}
				}
				if (t > 359) {
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
}
