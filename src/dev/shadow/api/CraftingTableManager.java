package dev.shadow.api;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Dropper;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import dev.shadow.api.ItemEffect.ItemEffectType;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagDouble;
import net.minecraft.server.v1_15_R1.NBTTagInt;
import net.minecraft.server.v1_15_R1.NBTTagList;
import net.minecraft.server.v1_15_R1.NBTTagString;
import shadowdev.item.ToolX;
import shadowdev.server.ServerManager;
import shadowdev.world.entity.Creature;
import shadowdev.world.entity.CreatureLivingBase;
import shadowdev.world.entity.EntityInteractScript;
import shadowdev.world.entity.EntityLivingScript;
import shadowdev.world.entity.EntityScript;

public class CraftingTableManager {
		
	static int index = 0, indexB;
		
	static HashMap<Integer, ItemCustom> items = new HashMap<Integer, ItemCustom>();
	static HashMap<Integer, Multiblock> multiblocks = new HashMap<Integer, Multiblock>();
	static HashMap<Material, List<ObjectSet<ItemCustom, Double>>> mineMap = new HashMap<Material, List<ObjectSet<ItemCustom, Double>>>();
	static List<ItemStack> references = new ArrayList<ItemStack>();
	static List<LivingEntity> spawnedCreatures = new ArrayList<LivingEntity>();
	static List<Entity> parents = new ArrayList<Entity>();
	static HashMap<LivingEntity, Creature> customMobs = new HashMap<LivingEntity, Creature>(); 
	static HashMap<String, Creature> registeredCreatures = new HashMap<String, Creature>();
	static List<Creature> registeredBosses = new ArrayList<Creature>();
	static HashMap<Location, ItemCustom> treasure = new HashMap<Location, ItemCustom>();
	static List<Location> treasureLocs = new ArrayList<Location>();
	static HashMap<Location, Inventory> cookingPots = new HashMap<Location, Inventory>();
	//static HashMap<Arrow, BowX> arrowsMap = new HashMap<Arrow, BowX>();
	static List<Integer[]> spawnMapChance = new ArrayList<Integer[]>();
	static List<ItemCustom[]> spawnMapContent = new ArrayList<ItemCustom[]>();
	public static HashMap<Entity, CreatureLivingBase> parentMap = new HashMap<Entity, CreatureLivingBase>();
	
	public static ItemCustom isHoldingCustomItem(Player p) {
		if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() != Material.AIR
			&& p.getInventory().getItemInMainHand().hasItemMeta() && p.getInventory().getItemInMainHand().getItemMeta().hasLore()
			&& p.getInventory().getItemInMainHand().getItemMeta().getLore().size() >= 1) {
			String lore1 = p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0);
			if (lore1.contains("CTiD>")) {
				String id = lore1.split(">")[1];
				int idTrans = -1;
				try {
					idTrans = Integer.parseInt(id);
				}catch(NumberFormatException e) {
					return null;
				}
				ItemCustom item = items.get(idTrans);
				if (item != null && item.getType() == p.getInventory().getItemInMainHand().getType()) {
					return item;
				}
			}
		}
		return null;
	}
	
	public static void addLootTable(int chance, int endChance, ItemCustom[] table) {
		spawnMapChance.add(new Integer[] {chance, endChance});
		spawnMapContent.add(table);
	}
	
	public static Creature isCustomMob(LivingEntity e) {
		return customMobs.get(e);
	}
	
	public static void addAsBoss(Creature c) {
		registeredBosses.add(c);
	}
	
	public static ItemCustom isCustomItem(ItemStack i) {
		if (i != null && i.getType() != Material.AIR
				&& i.hasItemMeta() && i.getItemMeta().hasLore()
				&& i.getItemMeta().getLore().size() >= 1) {
				String lore1 = i.getItemMeta().getLore().get(0);
				if (lore1.contains("CTiD>")) {
					String id = lore1.split(">")[1];
					int idTrans = -1;
					try {
						idTrans = Integer.parseInt(id);
					}catch(NumberFormatException e) {
						return null;
					}
					ItemCustom item = items.get(idTrans);
					if (item != null && item.getType() == i.getType()) {
						return item;
					}
				}
			}
			return null;
	}
	
	public static ItemCustom registerItem(ItemCustom ix) {
		items.put(index, ix);
		ix.id2 = index;
		index++;
		if (ix instanceof ToolX) {
			ToolX ie = (ToolX) ix;
			for (int i = 1; i < ie.getEnhancements(); i++) {
				try {
					ToolX tx = ie.getClass().newInstance();
					tx.id += "" + i;
					tx.enc = i;
					tx.isenc = true;
					tx.base = ie;
					tx.rebuild();
					ie.children.add(tx);
					items.put(index, tx);
					tx.id2 = index;
					index++;
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return ix;
	}
	
	public static EntityEquipment generateRandomEquipment(LivingEntity target, List<ObjectSet<ItemCustom, Double>> loot) {
		EntityEquipment e = target.getEquipment();
		//e.setItemInMainHand(getItemFromCustom(loot[new Random().nextInt(loot.length - 1)]));
		//e.setItemInMainHandDropChance(0.001f);
		return e;
	}
	
	public static void setupDropBlock(Material x) {
		mineMap.put(x, new ArrayList<ObjectSet<ItemCustom, Double>>());
	}
	
	public static void registerCreature(Creature c) {
		registeredCreatures.put(c.getId(), c);
	}
	
	public static void registerBlockDrop(Material x, ItemCustom item, double chance) {
		mineMap.get(x).add(new ObjectSet<ItemCustom, Double>(item, chance));
	}
	
	public static void registerMultiblock(Multiblock b) {
		multiblocks.put(indexB, b);
		indexB++;
	}
	
	public static LivingEntity spawnMob(Creature c, Location l) {
		if (l.getChunk().getEntities().length > 5) {
			return null;
		}
		LivingEntity e = c.spawn(l);
		spawnedCreatures.add(e);
		if (CreatureLivingBase.fromLiving(e).child != null)
			parents.add(CreatureLivingBase.fromLiving(e).child);
		customMobs.put(e, c);
		return e;
	}
	
	public static ItemStack getItemFromCustom(ItemCustom x) {
		return references.get(x.id2);
	}
	
	public static ItemCustom getById(String id) {
		for (ItemCustom t : items.values()) {
			//Bukkit.getServer().getLogger().info(t.id);
			if (t.id.equalsIgnoreCase(id)) {
				return t;
			}
		}
		return null;
	}
	
	static Location addLoc(double x, double y, double z, Location l) {
		return new Location(l.getWorld(), l.getX() + x, l.getY() + y, l.getZ() + z);
	}
	
	public static Multiblock isMultiblock(Location x) {
		if (x.getBlock().getType() == Material.PISTON) {
			for (Multiblock m : multiblocks.values()) {
				boolean correct = true;
				for (ObjectSet<PosRelative, Material> f : m.blocks) {
					switch(f.key) {
					case ABOVE:
						if (addLoc(0,1,0, x).getBlock().getType() == f.value) {
							break;
						}
						correct = false;
						break;
					case ABOVE_BELOW:
						if (addLoc(0,1,0, x).getBlock().getType() == f.value && addLoc(0,-1,0, x).getBlock().getType() == f.value) {
							break;
						}
						correct = false;
						break;
					case ADJACENT:
						if (addLoc(1,0,0, x).getBlock().getType() == f.value || addLoc(-1,0,0, x).getBlock().getType() == f.value
						|| addLoc(0,0,1, x).getBlock().getType() == f.value || addLoc(0,0,-1, x).getBlock().getType() == f.value) {
							break;
						}
						correct = false;
						break;
					case BELOW:
						if (addLoc(0,-1,0, x).getBlock().getType() == f.value) {
							break;
						}
						correct = false;
						break;
					case DOUBLE_ADJACENT:
						if ((addLoc(1,0,0, x).getBlock().getType() == f.value || addLoc(-1,0,0, x).getBlock().getType() == f.value)
						|| (addLoc(0,0,1, x).getBlock().getType() == f.value || addLoc(0,0,-1, x).getBlock().getType() == f.value)) {
							break;
						}
						correct = false;
						break;
					}
					if (!correct) {
						break;
					}
				}
				if (correct)
					return m;
			}
		}
		return null;
	}
	
	public static ItemStack setDisplayName(ItemStack it, ItemCustom u, String name) {
		ItemMeta m = it.getItemMeta();
		m.setDisplayName(name);
		if (u.hasNoDurability()) {
			m.setLore(getLore(u.id2, u.lore));
		}else {
			m.setLore(getLore(u.id2, u.durability, u.durability, u.lore));
		}
		m.setUnbreakable(true);
		it.setItemMeta(m);
		return it;
	}
	
	public static void spawnParticleRGB(World w, Color particles, double x, double y, double z, int amt, float scale) {
		Location l = new Location(w, x, y, z);
		w.spawnParticle(Particle.REDSTONE, l, amt, new Particle.DustOptions(particles, scale));
	}
		
	private static List<String> getLore(int id, String... lore) {
		List<String> l = new ArrayList<String>();
		l.add(ChatColor.GRAY + "CTiD>" + id);
		for (int i = 0; i < lore.length; i++) {
			l.add(ChatColor.YELLOW + lore[i]);
		}
		return l;
	}
	
	public static void spawnLoot(Location l) {
		ItemCustom loot = null;
		Location spawnPoint = l;
		Random r = new Random();
		int i = r.nextInt(100);
		for (int it = 0; it < spawnMapContent.size(); it++) {
			int s = spawnMapChance.get(it)[0], e = spawnMapChance.get(it)[1];
			if (i > s && i < e) {
				loot = spawnMapContent.get(it)[r.nextInt(spawnMapContent.get(it).length)];
			}
		}
		Location copy = l;
		while(copy.add(0, -1, 0).getBlock().getType().toString().toLowerCase().contains("water")) {
			spawnPoint = copy;
		}
		spawnPoint.add(0,1,0);
		
		spawnPoint.getBlock().setType(Material.CHEST, false);
		Chest c = (Chest) spawnPoint.getBlock().getState();
		c.setCustomName(ChatColor.GOLD + "Treasure Chest");
		System.out.println("spawned loot: x" + l.getX() + " y:" + l.getY() + " z:" + l.getZ());
		//System.out.println("spawned loot at: " + l.getX() + "," + l.getY() + "," + l.getZ());
		
			treasureLocs.add(spawnPoint);
			treasure.put(spawnPoint, loot);
		
	}
	
	private static List<String> getLore(int id, int dur, int maxdur, String... lore) {
		List<String> l = new ArrayList<String>();
		l.add(ChatColor.GRAY + "CTiD>" + id);
		for (int i = 0; i < lore.length; i++) {
			l.add(ChatColor.YELLOW + lore[i]);
		}
		l.add(ChatColor.BLUE.toString() + dur + "/" + maxdur);
		return l;
	}
	
	static BlockState getContainer(Location x, PosRelative r) {
		switch(r) {
		case ABOVE:
			if (addLoc(0,1,0, x).getBlock().getType() == Material.DROPPER) {
				return addLoc(0, 1, 0, x).getBlock().getState();
			}
			break;
		case ADJACENT:
			if (addLoc(1,0,0, x).getBlock().getType() == Material.DROPPER) {
				return addLoc(1, 0, 0, x).getBlock().getState();
			}
			if (addLoc(-1,0,0, x).getBlock().getType() == Material.DROPPER) {
				return addLoc(-1, 0, 0, x).getBlock().getState();
			}
			if (addLoc(0,0,1, x).getBlock().getType() == Material.DROPPER) {
				return addLoc(0, 0, 1, x).getBlock().getState();
			}
			if (addLoc(0,0,-1, x).getBlock().getType() == Material.DROPPER) {
				return addLoc(0, 0, -1, x).getBlock().getState();
			}
			break;
		case BELOW:
			if (addLoc(0,-1,0, x).getBlock().getType() == Material.DROPPER) {
				return addLoc(0, -1, 0, x).getBlock().getState();
			}
			break;
		default:
			break;
		}
		return null;
	}
	
	public static void displayItem(Player p, ItemCustom c, String msg) {
		Inventory i = Bukkit.getServer().createInventory(null, 27, ChatColor.GOLD + msg);
		ItemStack item = getItemFromCustom(c);
		i.setItem(13, item);
		
		p.openInventory(i);
		ServerManager.getPlayer(p).addItem(c);
		ServerManager.spawnHolo(p.getEyeLocation().add(p.getEyeLocation().getDirection().multiply(2)), "+ " + c.getDisplayName());
	}
		
	public static void initCraftingTable(Plugin server) {
		Bukkit.getServer().getPluginManager().registerEvents(new Listener() {
				
			@EventHandler
			public void onBlockBreak(BlockBreakEvent e) {
				if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
				Random r = new Random();
				List<ObjectSet<ItemCustom, Double>> k = mineMap.get(e.getBlock().getType());
				//Block b = e.getBlock();
				Material t = e.getBlock().getType();
				if (t == Material.GRASS || t == Material.DEAD_BUSH || t == Material.FERN || t == Material.SEAGRASS || t == Material.BROWN_MUSHROOM || t == Material.RED_MUSHROOM
					|| t == Material.TALL_GRASS || t == Material.LARGE_FERN) {
					if (new Random().nextInt(5) == 1) {
					e.setDropItems(false);
					//String id = "id" + (162 + new Random().nextInt(35));
					//e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), getItemFromCustom(CraftingTableManager.getById(id)));
					}
				}
				if (k != null) {
					for (ObjectSet<ItemCustom, Double> f : k) {
						if (r.nextInt(100) + 1 <= f.value) {
							e.setDropItems(false);
							e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), getItemFromCustom(f.key));
						}else {
							e.setDropItems(true);
						}
					}
				}
			}
			
			@EventHandler
			public void onChunkLoad(ChunkLoadEvent e) {
				if (e.isNewChunk()) {
					if (new Random().nextInt(20) == 1) {
						//System.out.println("spawned new loot");
						spawnLoot(e.getWorld().getHighestBlockAt(e.getChunk().getX() * 16, e.getChunk().getZ() * 16).getLocation());
					}
					return;
				}
			}
			
			//@EventHandler
			//public void onBlock(EntitySh)
			
			/*@EventHandler
			public void onShoot(EntityShootBowEvent e) {
				Projectile proj = (Projectile) e.getProjectile();
				if (!(proj.getShooter() instanceof Player)) return;
				if (proj instanceof Arrow) {
					Player p = (Player) proj.getShooter();
					ItemCustom c = isHoldingCustomItem((Player) proj.getShooter());
					if (!c.hasNoDurability()) {
						ItemStack i = p.getInventory().getItemInMainHand();
						ItemMeta m = i.getItemMeta();
						List<String> pastLore = m.getLore();
						String pastdurS = (pastLore.get(pastLore.size() - 1));
						int pastdur = Integer.parseInt(pastdurS.split("/")[0].replace(ChatColor.BLUE.toString(), ""));
						m.setLore(getLore(c.id2, pastdur - 1, c.durability, c.lore));
						i.setItemMeta(m);
						p.getInventory().setItemInMainHand(i);
						if (pastdur - 1 <= 0) {
							p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 0);
							p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
							p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 5, 0);
							p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_HURT, 1, 0);
							p.getInventory().setItemInMainHand(null);
						}else if (pastdur - 1 == 2) {
							
						}
					}
					
				}
			}*/
			
			@EventHandler
			public void onHit(ProjectileHitEvent e) {
				if (e.getEntity() instanceof Arrow && e.getHitBlock() != null) {
					//arrowsMap.remove(e.getEntity());
				}
			}
			
			@EventHandler(priority = EventPriority.HIGH)
			public void onAttack(EntityDamageByEntityEvent e) {
				if (e.getDamager() instanceof Arrow) {
					//Arrow a = (Arrow) e.getDamager();
					//if (arrowsMap.containsKey(a)) {
					//	e.setDamage(arrowsMap.get(a).getArrowDamage() / 2);
					//	arrowsMap.remove(e.getDamager());
					//}
				}
				if (e.getDamager() instanceof Player) {
					Player p = (Player) e.getDamager();
					ItemCustom item = isHoldingCustomItem(p);
					if (item != null) {
						if (item.hasAttackScript()) {
							if (item.getAttackScript().onAttack(p).getEffect() == ItemEffectType.ATTACK) {
								e.setDamage(item.getAttackScript().onAttack(p).getCoefficient());
							}
							if (item.getAttackScript().onAttack(p).getEffect() == ItemEffectType.FIRE) {
								e.getEntity().setFireTicks((int) item.getAttackScript().onAttack(p).getCoefficient());
							}
							if (item.getAttackScript().onAttack(p).getEffect() == ItemEffectType.ICE) {
								((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) item.getAttackScript().onAttack(p).getCoefficient(), 20));
							}
							if (item.getAttackScript().onAttack(p).getEffect() == ItemEffectType.LIGHTNING) {
								((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (int) item.getAttackScript().onAttack(p).getCoefficient(), 20));
								((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) item.getAttackScript().onAttack(p).getCoefficient(), 20));
								((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, (int) item.getAttackScript().onAttack(p).getCoefficient(), 20));
							}
						}
						if (!item.hasNoDurability()) {
							ItemStack i = p.getInventory().getItemInMainHand();
							ItemMeta m = i.getItemMeta();
							List<String> pastLore = m.getLore();
							String pastdurS = (pastLore.get(pastLore.size() - 1));
							int pastdur = Integer.parseInt(pastdurS.split("/")[0].replace(ChatColor.BLUE.toString(), ""));
							m.setLore(getLore(item.id2, pastdur - 1, item.durability, item.lore));
							i.setItemMeta(m);
							p.getInventory().setItemInMainHand(i);
							if (i.getType().toString().toLowerCase().contains("wood")) {
								p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1, 0);
							}
							if (i.getType().toString().toLowerCase().contains("stone")) {
								p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 0.5f, 0);
								p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.25f, 2);
								p.playSound(p.getLocation(), Sound.BLOCK_METAL_HIT, 0.05f, 0);
							}
							if (i.getType().toString().toLowerCase().contains("iron")) {
								p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.5f, 0);
								p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 2);
								p.playSound(p.getLocation(), Sound.BLOCK_METAL_HIT, 1, 0);
							}
							if (i.getType().toString().toLowerCase().contains("gold")) {
								p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.5f, 0);
								p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 2);
								p.playSound(p.getLocation(), Sound.BLOCK_METAL_HIT, 1, 0);
							}
							if (i.getType().toString().toLowerCase().contains("diamond")) {
								p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.5f, 0);
								p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 2);
								p.playSound(p.getLocation(), Sound.BLOCK_METAL_HIT, 1, 0);
							}
							if (pastdur - 1 <= 0) {
								p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 0);
								p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
								p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 5, 0);
								p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_HURT, 1, 0);
								p.getInventory().setItemInMainHand(null);
								if (item instanceof ToolX) {
									ToolX x = (ToolX) item;
									if (x.hasBreakScript()) x.getBreakScript().onWeaponBreak(x, p);
								}
							}else if (pastdur - 1 == 2) {
								
							}
						}
					}
				}
				e.setDamage(e.getDamage() / 2);
			} // 4 5 8 7 9 5
			
			@EventHandler
			public void onEntityInteract(PlayerInteractAtEntityEvent e) {
				Player p = e.getPlayer();
				//System.out.println("1");
				if (spawnedCreatures.contains(e.getRightClicked()) && customMobs.containsKey(e.getRightClicked())) {
					for (EntityScript es : customMobs.get(e.getRightClicked()).getScripts()) {
						if (es instanceof EntityInteractScript) {
							//System.out.println("4");
							e.setCancelled(((EntityInteractScript) es).onInteract(p, (LivingEntity) e.getRightClicked()));
						}
					}
				}
			}
			
			@EventHandler
			public void onDie(EntityDeathEvent e) {
				if (customMobs.containsKey(e.getEntity())) {
					if (((CraftLivingEntity)e.getEntity()).getHandle() instanceof CreatureLivingBase) {
						CreatureLivingBase clb = CreatureLivingBase.fromLiving(e.getEntity());
						if (clb.child != null) {
							clb.child.remove();
						}
					}
					if (registeredBosses.contains(customMobs.get(e.getEntity()))) {
						
						//e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), getItemFromCustom(Main.exHC));
					}
				}
			}
			
			@EventHandler
			public void unloadWorldStuff(PluginDisableEvent e) {
				for (LivingEntity ent : spawnedCreatures) {
					ent.getEquipment().clear();
					ent.remove();
				}
				for (Entity ent : parents) {
					ent.remove();
				}
				for (int i = 0; i < treasureLocs.size(); i++) {
					Location l = treasureLocs.get(i);
					l.getChunk().load();
					l.getBlock().setType(Material.AIR);
				}
			}
			
			@EventHandler
			public void onInteract(PlayerInteractEvent e) {
				if (e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null) {
					if (e.getClickedBlock().getType() == Material.CAULDRON) {
						if (!e.getPlayer().isSneaking()) {
							if (cookingPots.containsKey(e.getClickedBlock().getLocation())) {
								//e.getPlayer().openInventory(cookingPots.get(e.getClickedBlock().getLocation()));
							}else {
								cookingPots.put(e.getClickedBlock().getLocation(), Bukkit.createInventory(null, 9, "Cooking Pot"));
								//e.getPlayer().openInventory(cookingPots.get(e.getClickedBlock().getLocation()));
							}
						}else {
							if (!cookingPots.containsKey(e.getClickedBlock().getLocation())) return;
							//Inventory inv = cookingPots.get(e.getClickedBlock().getLocation());
							//RecipeBook.checkRecipe(inv);
						}
					}
					if (e.getClickedBlock().getType() == Material.CHEST) {
						//System.out.println("took loot at: " + e.getClickedBlock().getLocation().getX() + "," + e.getClickedBlock().getLocation().getY() + "," + e.getClickedBlock().getLocation().getZ());
						if (treasure.containsKey(e.getClickedBlock().getLocation())) {
							e.getClickedBlock().setType(Material.AIR);
							e.setCancelled(true);
							e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 4, 0.4f);
							displayItem(e.getPlayer(), treasure.get(e.getClickedBlock().getLocation()), "Item Found!");
						}else {
							Chest c = (Chest) e.getClickedBlock().getState();
							if (c.getCustomName() != null && c.getCustomName().contains("Treasure  Chest")) {
								ItemCustom loot = null;
								Random r = new Random();
								int i = r.nextInt(100);
								for (int it = 0; it < spawnMapContent.size(); it++) {
									int s = spawnMapChance.get(it)[0], x = spawnMapChance.get(it)[1];
									if (i > s && i < x) {
										loot = spawnMapContent.get(it)[r.nextInt(spawnMapContent.get(it).length)];
									}
								}
								e.getClickedBlock().setType(Material.AIR);
								e.setCancelled(true);
								e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 4, 0.4f);
								displayItem(e.getPlayer(), loot, "Item Found!");
							}
						}
					}
					if (e.getClickedBlock().getType() == Material.PISTON) {
						Multiblock b = isMultiblock(e.getClickedBlock().getLocation());
						if (b != null) {
							if (b instanceof IRecipeHolderMultiblock) {
								Dropper drop = (Dropper) getContainer(e.getClickedBlock().getLocation(), ((IRecipeHolderMultiblock) b).getContainerLocation());
								for (MultiblockRecipe r: ((IRecipeHolderMultiblock) b).getRecipes()) {
									r.checkShape(drop.getInventory());
								}
							}
							b.onInteract(e.getPlayer(), e.getClickedBlock().getState(), e.getClickedBlock().getLocation());
							return;
						}
					}
				}
				if (e.getItem() != null && e.getAction().toString().toLowerCase().contains("right")) {
					ItemCustom item = isHoldingCustomItem(e.getPlayer());
					if (item != null) {
						if (item instanceof IInteractable) {
							((IInteractable)item).onInteract(e.getPlayer());
							if (item instanceof IConsumable) {
								ItemStack i = e.getPlayer().getInventory().getItemInMainHand().clone();
								i.setAmount(1);
								e.getPlayer().getInventory().removeItem(i);
							}
							return;
						}
						if (item.getInteractScript() != null) {
							item.getInteractScript().onInteract(e.getPlayer());
							return;
						}
					}
				}
			}
				
		}, server);
		for (int i = 0; i < items.values().size(); i++) {
			ItemCustom u = items.get(i);
			ItemStack it = new ItemStack(u.type);
			ItemMeta m = it.getItemMeta();
			m.setDisplayName(u.getDisplayName());
			double boost = 0;
			if (u instanceof IEnhanceable) {
				boost = ((IEnhanceable)u).getCurrentEnhancement();
			}
			if (u.hasNoDurability()) {
				m.setLore(getLore(u.id2, u.lore));
			}else {
				m.setLore(getLore(u.id2, u.durability, u.durability, u.lore));
			}
			m.setUnbreakable(true);
			if (u.hideFlags)
				m.addItemFlags(ItemFlag.values());
			it.setItemMeta(m);
			net.minecraft.server.v1_15_R1.ItemStack nms = CraftItemStack.asNMSCopy(it);
			it = CraftItemStack.asBukkitCopy(nms);
			//
			if (u instanceof IHasEnchanments) {
				it.addUnsafeEnchantments(((IHasEnchanments) u).getEnchants());
			}
			if (u instanceof IHasNBT) {
				if (((IHasNBT) u).getItemAttribute(AttributeType.ATTACK_DAMAGE) != null) {
					net.minecraft.server.v1_15_R1.ItemStack nms2 = CraftItemStack.asNMSCopy(it);
					NBTTagCompound tag2;
					if (!nms2.hasTag())
						tag2 = new NBTTagCompound();
					else {
						tag2 = nms2.getTag();
					}
					NBTTagList atb;
					if (tag2.hasKey("AttributeModifiers"))
						atb = (NBTTagList) tag2.get("AttributeModifiers");
					else atb = new NBTTagList();
					//
					NBTTagCompound armor = new NBTTagCompound();
					armor.set("Amount", NBTTagDouble.a(((IHasNBT) u).getItemAttribute(AttributeType.ATTACK_DAMAGE).value + boost));
					armor.set("AttributeName", NBTTagString.a("generic.attackDamage"));
					armor.set("Name", NBTTagString.a("generic.attackDamage"));
					armor.set("Operation", NBTTagInt.a(0));
					armor.set("Slot", NBTTagString.a("mainhand"));
					armor.set("UUIDLeast", NBTTagInt.a(930488));
					armor.set("UUIDMost", NBTTagInt.a(127635));
					atb.add(armor);
					tag2.set("AttributeModifiers", atb);
					nms2.setTag(tag2);
					it = CraftItemStack.asBukkitCopy(nms2);
				}
				if (((IHasNBT) u).getItemAttribute(AttributeType.ATTACK_SPEED) != null) {
					net.minecraft.server.v1_15_R1.ItemStack nms2 = CraftItemStack.asNMSCopy(it);
					NBTTagCompound tag2;
					if (!nms2.hasTag())
						tag2 = new NBTTagCompound();
					else {
						tag2 = nms2.getTag();
					}
					NBTTagList atb;
					if (tag2.hasKey("AttributeModifiers"))
						atb = (NBTTagList) tag2.get("AttributeModifiers");
					else atb = new NBTTagList();
					//
					NBTTagCompound armor = new NBTTagCompound();
					armor.set("Amount",  NBTTagDouble.a(((IHasNBT) u).getItemAttribute(AttributeType.ATTACK_SPEED).value));
					armor.set("AttributeName",  NBTTagString.a("generic.attackSpeed"));
					armor.set("Name",  NBTTagString.a("generic.attackSpeed"));
					armor.set("Operation",  NBTTagInt.a(0));
					armor.set("Slot",  NBTTagString.a("mainhand"));
					armor.set("UUIDLeast",  NBTTagInt.a(930488));
					armor.set("UUIDMost",  NBTTagInt.a(127635));
					atb.add(armor);
					tag2.set("AttributeModifiers", atb);
					nms2.setTag(tag2);
					it = CraftItemStack.asBukkitCopy(nms2);
				}
				if (((IHasNBT) u).getItemAttribute(AttributeType.ARMOR) != null) {
					net.minecraft.server.v1_15_R1.ItemStack nms2 = CraftItemStack.asNMSCopy(it);
					NBTTagCompound tag2;
					if (!nms2.hasTag())
						tag2 = new NBTTagCompound();
					else {
						tag2 = nms2.getTag();
					}
					NBTTagList atb;
					if (tag2.hasKey("AttributeModifiers"))
						atb = (NBTTagList) tag2.get("AttributeModifiers");
					else atb = new NBTTagList();
						//
					NBTTagCompound armor = new NBTTagCompound();
					armor.set("Amount",  NBTTagDouble.a(((IHasNBT) u).getItemAttribute(AttributeType.ARMOR).value + boost));
					armor.set("AttributeName",  NBTTagString.a("generic.armor"));
					armor.set("Name",  NBTTagString.a("generic.armor"));
					armor.set("Operation",  NBTTagInt.a(0));
					armor.set("Slot",  NBTTagString.a(((IHasNBT) u).getItemAttribute(AttributeType.ARMOR).eq.toString().toLowerCase()));
					armor.set("UUIDLeast",  NBTTagInt.a(930488));
					armor.set("UUIDMost",  NBTTagInt.a(127635));
					//
					NBTTagCompound armorT = new NBTTagCompound();
					armorT.set("Amount",  NBTTagDouble.a(((IHasNBT) u).getItemAttribute(AttributeType.ARMOR).value / 4 + boost));
					armorT.set("AttributeName",  NBTTagString.a("generic.armorToughness"));
					armorT.set("Name",  NBTTagString.a("generic.armorToughness"));
					armorT.set("Operation",  NBTTagInt.a(0));
					armorT.set("Slot",  NBTTagString.a(((IHasNBT) u).getItemAttribute(AttributeType.ARMOR).eq.toString().toLowerCase()));
					armorT.set("UUIDLeast",  NBTTagInt.a(977108));
					armorT.set("UUIDMost",  NBTTagInt.a(512624));
					atb.add(armor);
					atb.add(armorT);
					tag2.set("AttributeModifiers", atb);
					nms2.setTag(tag2);
					it = CraftItemStack.asBukkitCopy(nms2);
				}
			}
			if (u instanceof IHasRecipe<?>) {
				Bukkit.getServer().addRecipe(((IHasRecipe<?>) u).getRecipe(it));
			}
			if (u.m != null) {
				LeatherArmorMeta meta = (LeatherArmorMeta) it.getItemMeta();
				meta.setColor(u.m);
				it.setItemMeta(meta);
			}
			if (u.p != null) {
				PotionMeta meta = (PotionMeta) it.getItemMeta();
				meta.setColor(u.p);
				it.setItemMeta(meta);
			}
			references.add(it);
		}
		
		BukkitRunnable gameTick = new BukkitRunnable() {
			
			@Override
			public void run() {
				List<LivingEntity> dead = new ArrayList<LivingEntity>();
				try {
				for (int i = 0; i < spawnedCreatures.size(); i++) {
					LivingEntity e = spawnedCreatures.get(i);
					if (e != null && !e.isDead()) {
						Creature c = customMobs.get(e);
						if (((CraftLivingEntity)e).getHandle() instanceof CreatureLivingBase) {
							((CreatureLivingBase)((CraftLivingEntity)e).getHandle()).globalTick();
						}
						if (e.getTicksLived() > 2400 && c.doesRespawn()) {
							e.remove();
							if (((CraftLivingEntity)e).getHandle() instanceof CreatureLivingBase) {
								CreatureLivingBase clb = CreatureLivingBase.fromLiving(e);
								if (clb.child != null) {
									clb.child.remove();
								}
							}
						}
						for (EntityScript es : c.getScripts()) {
							if (es instanceof EntityLivingScript) {
								((EntityLivingScript)es).whenAlive(c, e, e.getLocation(), e.getWorld());
							}
						}
					}else {
						dead.add(e);
						continue;
					}
				}
				}catch(ConcurrentModificationException e) {
					e.printStackTrace();
				}
				if (dead.size() > 0) {
					for (LivingEntity e : dead) {
						spawnedCreatures.remove(e);
						customMobs.remove(e);
					}
				}
			}
		};
		gameTick.runTaskTimer(server, 0, 3);
		
		Random r = new Random();
		
		BukkitRunnable gameTick2 = new BukkitRunnable() {
			
			@Override
			public void run() {
				for (World w : server.getServer().getWorlds()) {
					if (w.getLoadedChunks().length > 0) {
					//System.out.println("A");
					for (Chunk c : w.getLoadedChunks()) {
						//System.out.println("B");
						int x = r.nextInt(50);
						if (x == 6) {
							for (Creature q : registeredBosses) {
								int i = 0;
								//System.out.println("C\n"
								//	+ w.getBiome(c.getX() * 16, c.getZ() * 16) + ":" + q.getSpawnBiome());
								//System.out.println("Found acceptable spawn location for " + q.getName());
								if (i < 1 && new Random().nextInt(250) == 6) {
									i++;
									//System.out.println("Tried spawning " + q.getName() + " at " + w.getHighestBlockAt(c.getX() * 16, c.getZ() * 16).getLocation().add(0, 1, 0).getX() + "," + w.getHighestBlockAt(c.getX() * 16, c.getZ() * 16).getLocation().add(0, 1, 0).getY() + "," + w.getHighestBlockAt(c.getX() * 16, c.getZ() * 16).getLocation().add(0, 1, 0).getZ());
									//spawnMob(q, w.getHighestBlockAt(c.getX() * 16, c.getZ() * 16).getLocation().add(0, 1, 0));
								}
							}
						}else
						for (Creature q : registeredCreatures.values()) {
							int i = 0;
							//System.out.println("C\n"
								//	+ w.getBiome(c.getX() * 16, c.getZ() * 16) + ":" + q.getSpawnBiome());
							if (w.getBiome(c.getX() * 16, c.getZ() * 16) == q.getSpawnBiome()) {
								//System.out.println("Found acceptable spawn location for " + q.getName());
								if (i < 2 && new Random().nextInt(250) == 6 && w.getTime() > q.getSpawnWindow()[0] && w.getTime() < q.getSpawnWindow()[1]) {
									i++;
									//System.out.println("Tried spawning " + q.getName() + " at " + w.getHighestBlockAt(c.getX() * 16, c.getZ() * 16).getLocation().add(0, 1, 0).getX() + "," + w.getHighestBlockAt(c.getX() * 16, c.getZ() * 16).getLocation().add(0, 1, 0).getY() + "," + w.getHighestBlockAt(c.getX() * 16, c.getZ() * 16).getLocation().add(0, 1, 0).getZ());
									//spawnMob(q, w.getHighestBlockAt(c.getX() * 16, c.getZ() * 16).getLocation().add(0, 1, 0));
								}
							}
						}
						for (BlockState b : c.getTileEntities()) {
							if (b.getType() == Material.OAK_SIGN) {
								Sign cb = (Sign) b;
								if (cb.getLine(0).contains("!#spawnsao") && cb.getLine(1) != null) {
									Creature cr = registeredCreatures.get(cb.getLine(1));
									if (cr == null) continue;
									if (!cr.doesRespawn() && cr.getTimeSpawned() >= 1) continue;
									if (cr.doesRespawn())
									CraftingTableManager.spawnMob(cr, cb.getWorld().getHighestBlockAt(cb.getLocation().add(new Random().nextInt(10) - 5, 0, new Random().nextInt(10) - 5)).getLocation().add(0, 2.5, 0));
									else CraftingTableManager.spawnMob(cr, cb.getLocation().add(0, 2.5, 0));
								}
							}
						}
					}
				}
			}
			}
		};
		gameTick2.runTaskTimer(server, 0, 605);
		
		BukkitRunnable gameTick3 = new BukkitRunnable() {
			
			@Override
			public void run() {
				for (World w : server.getServer().getWorlds()) {
					if (w.getLoadedChunks().length > 0) {
					//System.out.println("A");
					for (Chunk c : w.getLoadedChunks()) {
						//System.out.println("B");
						if (new Random().nextInt(5000) == 1) {
							Location spawnPoint = w.getHighestBlockAt(c.getX() * 16, c.getZ() * 16).getLocation();
							ItemCustom loot = null;
							Random r = new Random();
							int i = r.nextInt(100);
							for (int it = 0; it < spawnMapContent.size(); it++) {
								int s = spawnMapChance.get(it)[0], e = spawnMapChance.get(it)[1];
								if (i > s && i < e) {
									loot = spawnMapContent.get(it)[r.nextInt(spawnMapContent.get(it).length)];
								}
							}
							Location copy = spawnPoint;
							while(copy.add(0, -1, 0).getBlock().getType().toString().toLowerCase().contains("water")) {
								spawnPoint = copy;
							}
							spawnPoint.add(0,1,0);
							if (r.nextInt(500) == 350) {
								//spawnMob(Main.sp, spawnPoint);
								return;
							}
							spawnPoint.getBlock().setType(Material.CHEST, false);
							Chest c1 = (Chest) spawnPoint.getBlock().getState();
							c1.setCustomName(ChatColor.GOLD + "Treasure Chest");
							//System.out.println("spawned loot at: " + l.getX() + "," + l.getY() + "," + l.getZ());
							
								treasureLocs.add(spawnPoint);
								treasure.put(spawnPoint, loot);
							
						}
					}
				}
			}
			}
		};
		gameTick3.runTaskTimer(server, 0, 1200);
		
	}
		
}
