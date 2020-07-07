package shadowdev.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Husk;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.ScoreboardManager;

import com.destroystokyo.paper.Title;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.gson.JsonSyntaxException;

import dev.shadow.api.AttributeType;
import dev.shadow.api.CraftingTableManager;
import dev.shadow.api.ItemCustom;
import dev.shadow.api.ObjectSet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_15_R1.ChatMessageType;
import net.minecraft.server.v1_15_R1.DamageSource;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_15_R1.PacketPlayInUseEntity;
import net.minecraft.server.v1_15_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import shadowdev.item.ArmorX;
import shadowdev.item.FoodX;
import shadowdev.item.MaterialX;
import shadowdev.item.PotionX;
import shadowdev.item.ToolX;
import shadowdev.item.defaults.Apple;
import shadowdev.item.defaults.BoarMeat;
import shadowdev.item.defaults.BoarTusk;
import shadowdev.item.defaults.Bottle;
import shadowdev.item.defaults.Bread;
import shadowdev.item.defaults.BriskLeaf;
import shadowdev.item.defaults.CookedBoarMeat;
import shadowdev.item.defaults.CookedFish;
import shadowdev.item.defaults.Fish;
import shadowdev.item.defaults.HealthShroom;
import shadowdev.item.defaults.IronBroadswordBlade;
import shadowdev.item.defaults.IronIngot;
import shadowdev.item.defaults.IronOre;
import shadowdev.item.defaults.MenuItem;
import shadowdev.item.defaults.NightsteelIngot;
import shadowdev.item.defaults.SmallBranch;
import shadowdev.item.defaults.StaminaFlower;
import shadowdev.item.defaults.SturdyBranch;
import shadowdev.item.defaults.Wheat;
import shadowdev.item.defaults.armor.CoatOfMidnight;
import shadowdev.item.defaults.armor.KoboldBoots;
import shadowdev.item.defaults.armor.KoboldChestplate;
import shadowdev.item.defaults.armor.KoboldHelmet;
import shadowdev.item.defaults.armor.KoboldLeggings;
import shadowdev.item.defaults.potions.CampfireCrystal;
import shadowdev.item.defaults.potions.HealthPotion;
import shadowdev.item.defaults.potions.SpeedPotion;
import shadowdev.item.defaults.potions.StaminaPotion;
import shadowdev.item.defaults.swords.AnnealBlade;
import shadowdev.item.defaults.swords.DarkRepulser;
import shadowdev.item.defaults.swords.EbonDagger;
import shadowdev.item.defaults.swords.Elucidator;
import shadowdev.item.defaults.swords.GuiltyThorn;
import shadowdev.item.defaults.swords.IronBroadsword;
import shadowdev.item.defaults.swords.IronRapier;
import shadowdev.item.defaults.swords.Karakurenai;
import shadowdev.item.defaults.swords.KoboldMace;
import shadowdev.item.defaults.swords.LambentLight;
import shadowdev.item.defaults.swords.Liberator;
import shadowdev.item.defaults.swords.NightsteelOdachi;
import shadowdev.item.defaults.swords.SteelLongsword;
import shadowdev.item.defaults.swords.ToughBoarSword;
import shadowdev.item.defaults.swords.WoodenSword;
import shadowdev.player.GamePlayer;
import shadowdev.player.GameStat;
import shadowdev.player.PlayerNA;
import shadowdev.player.Skill;
import shadowdev.player.gui.AnvilMenu;
import shadowdev.player.gui.AuctionHouse;
import shadowdev.player.gui.BrewingMenu;
import shadowdev.player.gui.CookingMenu;
import shadowdev.player.gui.EnhanceMenu;
import shadowdev.player.gui.FurnaceMenu;
import shadowdev.player.gui.ShopEditor;
import shadowdev.player.skills.Avalanche;
import shadowdev.player.skills.DeadlySins;
import shadowdev.player.skills.FellCrescent;
import shadowdev.player.skills.FlashingPenetrator;
import shadowdev.player.skills.HorizontalSquare;
import shadowdev.player.skills.HowlingOctave;
import shadowdev.player.skills.LightningFall;
import shadowdev.player.skills.Linear;
import shadowdev.player.skills.MeteorBreak;
import shadowdev.player.skills.MeteorFall;
import shadowdev.player.skills.RageSpike;
import shadowdev.player.skills.Reaver;
import shadowdev.player.skills.SharpNail;
import shadowdev.utils.JavaUtils;
import shadowdev.world.entity.BossCreature;
import shadowdev.world.entity.ChasePlayerScript;
import shadowdev.world.entity.Creature;
import shadowdev.world.entity.CreatureLivingBase;
import shadowdev.world.entity.EntityInteractScript;
import shadowdev.world.entity.EntityLivingScript;
import shadowdev.world.entity.defaults.TaurusLivingScript;

@SuppressWarnings("deprecation")
public class ServerManager extends JavaPlugin implements Listener {

	static HashMap<UUID, GamePlayer> players = new HashMap<UUID, GamePlayer>();
	public static HashMap<String, Class<? extends Skill>> skillmap = new HashMap<String, Class<? extends Skill>>();
	public static HashMap<String, List<ObjectSet<Integer, Class<? extends Skill>>>> unlockMap = new HashMap<String, List<ObjectSet<Integer, Class<? extends Skill>>>>();
	public static HashMap<Item, Player> dropOwners = new HashMap<Item, Player>();
	public static HashMap<FoodX, FoodX> cookbook = new HashMap<FoodX, FoodX>();
	public static HashMap<String, ToolX> anvil = new HashMap<String, ToolX>();
	public static HashMap<ToolX, MaterialX> enhance = new HashMap<ToolX, MaterialX>();
	public static HashMap<String, MaterialX> furnace = new HashMap<String, MaterialX>();
	public static HashMap<String, PotionX> brews = new HashMap<String, PotionX>();
	public static HashMap<LivingEntity, List<ObjectSet<OfflinePlayer, Integer>>> bossdmg = new HashMap<LivingEntity, List<ObjectSet<OfflinePlayer, Integer>>>();
	public static HashMap<Creature, List<ObjectSet<ItemCustom, Integer>>> shopMap = new HashMap<Creature, List<ObjectSet<ItemCustom, Integer>>>();
	public static List<ObjectSet<LivingEntity, ObjectSet<Entity, Double>>> damageQueue = new ArrayList<ObjectSet<LivingEntity, ObjectSet<Entity, Double>>>();
	public static ScoreboardManager board;
	
	public static int[] seeds = new int[27];
	
	static void generateSeeds() {
		for (int i = 1; i < 27; i++) {
			seeds[i] = new Random().nextInt(i);
		}
	}
	
	public static int getSeed(int size) {
		return seeds[size];
	}
	
	public static GamePlayer getPlayer(Player p) {
		return players.get(p.getUniqueId());
	}
	
	public static void registerSkill(Class<? extends Skill> s) {
		try {
			skillmap.put(s.newInstance().getId(), s);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void registerSwordRecipe(MaterialX hilt, MaterialX blade, ToolX output) {
		anvil.put(hilt.getId() + "," + blade.getId(), output);
	}
	
	public static void registerFurnaceRecipe(MaterialX hilt, MaterialX blade, MaterialX output) {
		furnace.put(hilt.getId() + "," + blade.getId(), output);
	}
	
	public static void registerBrewingRecipe(MaterialX hilt, MaterialX blade, PotionX output) {
		brews.put(hilt.getId() + "," + blade.getId(), output);
	}
	
	public static void registerFood(FoodX base, FoodX cooked) {
		CraftingTableManager.registerItem(base);
		CraftingTableManager.registerItem(cooked);
		cookbook.put(base, cooked);
	}
	
	public static FoodX getCookedVariant(FoodX x) {
		return cookbook.get(x);
	}
	
	public static void addUnlock(String stat, int level, Class<? extends Skill> skill) {
		List<ObjectSet<Integer, Class<? extends Skill>>> k = unlockMap.get(stat);
		if (k == null) {
			unlockMap.put(stat, new ArrayList<ObjectSet<Integer, Class<? extends Skill>>>());
		}
		unlockMap.get(stat).add(new ObjectSet<Integer, Class<? extends Skill>>(level, skill));
	}
	
	@SafeVarargs
	public static void addShopMap(Creature holder, ObjectSet<ItemCustom, Integer>...objectSets) {
		if (objectSets != null)
		shopMap.put(holder, Arrays.asList(objectSets));
	}
	
	public static void registerEnhancements(MaterialX m, ToolX t) {
		enhance.put(t, m);
	}
	
	public void openShop(Creature holder) {
		
	}
	
	List<ItemCustom> forage = new ArrayList<ItemCustom>();
	
	ItemCustom[] commons;
	ItemCustom[] uncommons;
	ItemCustom[] rares;
	ItemCustom[] epics;
	ItemCustom[] legendaries;
	
	MenuItem menu = new MenuItem();
	
	Creature fl1_hog = new Creature(ChatColor.RED + "[" + ChatColor.YELLOW + "3" + ChatColor.RED + "] " + ChatColor.WHITE + "Frenzy Boar", "fl1hog", null, new int[] {400, 13000}, true, 60, 3, 1, EntityTypes.PIG);
	Creature fl1_wolf = new Creature(ChatColor.RED + "[" + ChatColor.YELLOW + "1" + ChatColor.RED + "] " + ChatColor.WHITE + "Dire Wolf", "fl1wolf", null, new int[] {400, 13000}, true, 35, 9, 1, EntityTypes.WOLF);
	Creature fl1_kobold = new Creature(ChatColor.RED + "[" + ChatColor.YELLOW + "10" + ChatColor.RED + "] " + ChatColor.WHITE + "Ruin Kobold Trooper", "fl1kobold", null, new int[] {400, 13000}, true, 260, 28, 1.2, EntityTypes.HUSK);
	Creature fl1_golem = new Creature(ChatColor.RED + "[" + ChatColor.YELLOW + "25" + ChatColor.RED + "] " + ChatColor.WHITE + ChatColor.YELLOW + "Cave Golem", "fl1golem", null, new int[] {400, 13000}, true, 800, 56, 1, EntityTypes.IRON_GOLEM);
	Creature fl1_miner = new Creature(ChatColor.RED + "[" + ChatColor.YELLOW + "15" + ChatColor.RED + "] " + ChatColor.WHITE + "Kobold Miner", "fl1kobold3", null, new int[] {400, 13000}, true, 160, 14, 1.2, EntityTypes.HUSK);
	Creature fl1_kobold2 = new Creature(ChatColor.RED + "[" + ChatColor.YELLOW + "15" + ChatColor.RED + "] " + ChatColor.WHITE + "Ruin Kobold Sentinel", "fl1kobold2", null, new int[] {400, 13000}, true, 320, 44, 1.2, EntityTypes.HUSK);
	BossCreature fl1_illfang = new BossCreature(ChatColor.RED + "[" + ChatColor.YELLOW + "85" + ChatColor.RED + "] " + ChatColor.WHITE + "Illfang The Kobold Lord", "fl1illfang", 5000, 89D, 1.2, EntityTypes.GIANT);
	
	Creature fl2_ox = new Creature(ChatColor.RED + "[" + ChatColor.YELLOW + "30" + ChatColor.RED + "] " + ChatColor.WHITE + "Trembling Ox", "fl2cow", null, new int[] {400, 13000}, true, 400, 74, 1, EntityTypes.COW);
	Creature fl2_cow = new Creature(ChatColor.RED + "[" + ChatColor.YELLOW + "15" + ChatColor.RED + "] " + ChatColor.WHITE + "Trembling Cow", "fl2cow2", null, new int[] {400, 13000}, true, 250, 38, 1, EntityTypes.COW);
	Creature fl2_taurus = new Creature(ChatColor.RED + "[" + ChatColor.YELLOW + "50" + ChatColor.RED + "] " + ChatColor.WHITE + "Taurus Striker", "fl2taurus", null, new int[] {400, 13000}, true, 1400, 117, 1, EntityTypes.LLAMA);
	Creature fl2_ironguard = new Creature(ChatColor.RED + "[" + ChatColor.YELLOW + "65" + ChatColor.RED + "] " + ChatColor.YELLOW + "Taurus Ironguard", "fl2taurus2", null, new int[] {400, 13000}, true, 1800, 157, 1, EntityTypes.LLAMA);
	
	
	Creature forager = new Creature(ChatColor.YELLOW + "Forager", "fl1shopA", null, new int[] {-1,  -2}, false, 10000, 0, 0, EntityTypes.VILLAGER);
	Creature auctioneer = new Creature(ChatColor.YELLOW + "Auctioneer", "fl1shopB", null, new int[] {-1,  -2}, false, 10000, 0, 0, EntityTypes.VILLAGER);
	
	public void onEnable() {
		instance = this;
		board = Bukkit.getServer().getScoreboardManager();
		generateSeeds();
		
		
		fl1_kobold.setHelmet(Material.CHISELED_STONE_BRICKS);
		fl1_kobold.setChestplate(Material.IRON_CHESTPLATE);
		fl1_kobold.setLeggings(Material.IRON_LEGGINGS);
		fl1_kobold.setBoots(Material.IRON_BOOTS);
		fl1_kobold.setHand(Material.IRON_SHOVEL);
		
		fl1_miner.setHelmet(Material.CHISELED_STONE_BRICKS);
		fl1_miner.setChestplate(Material.LEATHER_CHESTPLATE);
		fl1_miner.setLeggings(Material.LEATHER_LEGGINGS);
		fl1_miner.setBoots(Material.LEATHER_BOOTS);
		fl1_miner.setHand(Material.STONE_PICKAXE);
		
		fl1_kobold2.setHelmet(Material.CHISELED_STONE_BRICKS);
		fl1_kobold2.setChestplate(Material.IRON_CHESTPLATE);
		fl1_kobold2.setLeggings(Material.IRON_LEGGINGS);
		fl1_kobold2.setBoots(Material.IRON_BOOTS);
		fl1_kobold2.setHand(Material.IRON_SHOVEL);
		
		fl1_illfang.setHelmet(Material.NETHER_QUARTZ_ORE);
		fl1_illfang.setChestplate(Material.LEATHER_CHESTPLATE);
		fl1_illfang.setLeggings(Material.LEATHER_LEGGINGS);
		fl1_illfang.setBoots(Material.LEATHER_BOOTS);
		fl1_illfang.setHand(Material.IRON_SWORD);
		fl1_illfang.setAttackRange(3);
		fl1_illfang.addScript(new EntityLivingScript() {
			
			int i = 0;
			
			@Override
			public void whenAlive(Creature c, LivingEntity e, Location l, World w) {
				i++;
				if (i >= 200) {
					i = 0;
					CraftingTableManager.spawnMob(fl1_kobold2, l.add(0, 3, 0));
				}
			}
		});
		
		fl2_taurus.setParent(ArmorStand.class);
		fl2_taurus.setHelmet(Material.CHISELED_STONE_BRICKS);
		fl2_taurus.addScript(new TaurusLivingScript());
		
		fl2_ironguard.setParent(ArmorStand.class);
		fl2_ironguard.setHelmet(Material.CHISELED_STONE_BRICKS);
		fl2_ironguard.addScript(new TaurusLivingScript());
		
		forager.setRespawns(false);
		forager.addScript(new EntityInteractScript() {
			
			@Override
			public boolean onInteract(Player p, LivingEntity e) {
				ShopEditor i = new ShopEditor(p, shopMap.get(forager));
				i.send();
				return true;
			}
		});
		
		auctioneer.setRespawns(false);
		auctioneer.addScript(new EntityInteractScript() {
			
			@Override
			public boolean onInteract(Player p, LivingEntity e) {
				AuctionHouse i = new AuctionHouse(p, 0);
				i.send();
				return true;
			}
		});
		
		WoodenSword woodsword = new WoodenSword();
		AnnealBlade a = new AnnealBlade();
		DarkRepulser d = new DarkRepulser();
		EbonDagger eb = new EbonDagger();
		Elucidator e = new Elucidator();
		GuiltyThorn gt = new GuiltyThorn();
		IronBroadsword ib = new IronBroadsword();
		Karakurenai k = new Karakurenai();
		LambentLight ll = new LambentLight();
		Liberator r = new Liberator();
		SteelLongsword sl = new SteelLongsword();
		CoatOfMidnight m = new CoatOfMidnight();
		Fish f = new Fish();
		CookedFish cf = new CookedFish();
		BoarTusk bt = new BoarTusk();
		SturdyBranch sb = new SturdyBranch();
		ToughBoarSword tbs = new ToughBoarSword();
		BoarMeat bm = new BoarMeat();
		CookedBoarMeat cbm = new CookedBoarMeat();
		KoboldMace km = new KoboldMace();
		IronIngot ig = new IronIngot();
		IronBroadswordBlade bb = new IronBroadswordBlade();
		IronRapier ir = new IronRapier();
		KoboldHelmet kh = new KoboldHelmet();
		KoboldChestplate kc = new KoboldChestplate();
		KoboldLeggings kl = new KoboldLeggings();
		KoboldBoots kb = new KoboldBoots();
		Apple apl = new Apple();
		Bread brd = new Bread();
		BriskLeaf blf = new BriskLeaf();
		HealthShroom hsh = new HealthShroom();
		Wheat wt = new Wheat();
		StaminaFlower stf = new StaminaFlower();
		Bottle btl = new Bottle();
		HealthPotion hpt = new HealthPotion();
		SmallBranch smb = new SmallBranch();
		CampfireCrystal cfc = new CampfireCrystal();
		IronOre iore = new IronOre();
		SpeedPotion spd = new SpeedPotion();
		StaminaPotion stp = new StaminaPotion();
		NightsteelIngot nsi = new NightsteelIngot();
		NightsteelOdachi nso = new NightsteelOdachi();
		
		fl1_hog.addLoot(bt, 1);
		fl1_hog.addLoot(sb, 2);
		fl1_hog.addLoot(bm, 35);
		
		fl1_wolf.addLoot(sb, 5);
		
		fl1_kobold.addLoot(km, 3);
		
		fl1_kobold.addLoot(kb, 1);
		fl1_kobold.addLoot(kl, 1);
		fl1_kobold.addLoot(kh, 1);
		fl1_kobold.addLoot(kc, 1);
		
		fl1_kobold2.addLoot(km, 3);
		
		fl1_kobold2.addLoot(kb, 1);
		fl1_kobold2.addLoot(kl, 1);
		fl1_kobold2.addLoot(kh, 1);
		fl1_kobold2.addLoot(kc, 1);
		
		fl1_miner.addLoot(iore, 10);
		
		fl1_golem.addLoot(ig, 40);
		
		fl1_illfang.addLoot(m, 100);
		fl1_illfang.addBossLoot(nso, 85);
		fl1_illfang.addBossLoot(nsi, 0);
		
		commons = new ItemCustom[] { sb, apl, brd};
		
		CraftingTableManager.addLootTable(0, 100, commons);
		
		CraftingTableManager.registerItem(menu);
		CraftingTableManager.registerItem(woodsword);
		CraftingTableManager.registerItem(a);
		CraftingTableManager.registerItem(d);
		CraftingTableManager.registerItem(eb);
		CraftingTableManager.registerItem(e);
		CraftingTableManager.registerItem(gt);
		CraftingTableManager.registerItem(ib);
		CraftingTableManager.registerItem(k);
		CraftingTableManager.registerItem(ll);
		CraftingTableManager.registerItem(sl);
		CraftingTableManager.registerItem(r);
		CraftingTableManager.registerItem(m);
		registerFood(f, cf);
		CraftingTableManager.registerItem(bt);
		CraftingTableManager.registerItem(sb);
		CraftingTableManager.registerItem(tbs);
		registerFood(bm, cbm);
		CraftingTableManager.registerItem(km);
		CraftingTableManager.registerItem(ig);
		CraftingTableManager.registerItem(bb);
		CraftingTableManager.registerItem(ir);
		CraftingTableManager.registerItem(kh);
		CraftingTableManager.registerItem(kc);
		CraftingTableManager.registerItem(kl);
		CraftingTableManager.registerItem(kb);
		CraftingTableManager.registerItem(apl);
		registerFood(wt, brd);
		CraftingTableManager.registerItem(blf);
		CraftingTableManager.registerItem(hsh);
		CraftingTableManager.registerItem(stf);
		CraftingTableManager.registerItem(btl);
		CraftingTableManager.registerItem(hpt);
		CraftingTableManager.registerItem(smb);
		CraftingTableManager.registerItem(cfc);
		CraftingTableManager.registerItem(iore);
		CraftingTableManager.registerItem(spd);
		CraftingTableManager.registerItem(stp);
		CraftingTableManager.registerItem(nsi);
		CraftingTableManager.registerItem(nso);
		
		forage.add(apl);
		forage.add(wt);
		forage.add(blf);
		forage.add(hsh);
		forage.add(stf);
		forage.add(btl);
		forage.add(smb);
		
		registerFurnaceRecipe(ig, ig, bb);
		registerSwordRecipe(sb, bt, tbs);
		registerSwordRecipe(sb, bb, ib);
		registerSwordRecipe(sb, ig, ir);
		registerSwordRecipe(smb, sb , woodsword);
		registerBrewingRecipe(btl, hsh, hpt);
		registerBrewingRecipe(smb, smb, cfc);
		registerBrewingRecipe(btl, blf, spd);
		registerBrewingRecipe(btl, stf, stp);
		registerFurnaceRecipe(iore, iore, ig);
		registerEnhancements(sb, woodsword);
		registerEnhancements(bt, tbs);
		registerEnhancements(ig, ib);
		registerEnhancements(ig, ir);
		registerEnhancements(ig, km);
		registerEnhancements(nsi, nso);
		
		CraftingTableManager.registerCreature(fl1_hog);
		CraftingTableManager.registerCreature(fl1_kobold);
		CraftingTableManager.registerCreature(fl1_golem);
		CraftingTableManager.registerCreature(fl1_kobold2);
		CraftingTableManager.registerCreature(fl1_illfang);
		CraftingTableManager.addAsBoss(fl1_illfang);
		CraftingTableManager.registerCreature(forager);
		CraftingTableManager.registerCreature(fl1_miner);
		CraftingTableManager.registerCreature(fl1_wolf);
		CraftingTableManager.registerCreature(fl2_cow);
		CraftingTableManager.registerCreature(fl2_ironguard);
		CraftingTableManager.registerCreature(fl2_ox);
		CraftingTableManager.registerCreature(fl2_taurus);
		CraftingTableManager.registerCreature(auctioneer);
		registerSkill(Avalanche.class);
		registerSkill(FellCrescent.class);
		registerSkill(FlashingPenetrator.class);
		registerSkill(HorizontalSquare.class);
		registerSkill(Linear.class);
		registerSkill(MeteorBreak.class);
		registerSkill(RageSpike.class);
		registerSkill(DeadlySins.class);
		registerSkill(HowlingOctave.class);
		registerSkill(LightningFall.class);
		registerSkill(SharpNail.class);
		registerSkill(MeteorFall.class);
		registerSkill(Reaver.class);
		
		
		addUnlock("cmbt_level", 5, RageSpike.class);
		addUnlock("cmbt_level", 70, MeteorBreak.class);
		addUnlock("cmbt_level", 110, DeadlySins.class);
		addUnlock("cmbt_level", 130, FlashingPenetrator.class);
		addUnlock("cmbt_level", 150, HorizontalSquare.class);
		addUnlock("cmbt_level", 170, HowlingOctave.class);
		addUnlock("cmbt_level", 200, LightningFall.class);
		
		addUnlock("smithing", 2, Avalanche.class);
		addUnlock("smithing", 15, SharpNail.class);
		addUnlock("smithing", 30, MeteorFall.class);
		
		addUnlock("forage", 3, Reaver.class);
		addUnlock("forage", 15, FellCrescent.class);
		
		addUnlock("cooking", 6, Linear.class);
		
		
		addShopMap(forager, new ObjectSet<ItemCustom, Integer>(sb, 4000), new ObjectSet<ItemCustom, Integer>(cfc, 1000), new ObjectSet<ItemCustom, Integer>(bt, 10000), new ObjectSet<ItemCustom, Integer>(bm, 400), new ObjectSet<ItemCustom, Integer>(tbs, 50000));
		
		EconomyManager.startEcon();
		
		getServer().getPluginManager().registerEvents(this, this);
		
		
		CraftingTableManager.initCraftingTable(this);
		for (Player p : getServer().getOnlinePlayers()) {
			System.out.println("loading " + p.getName() + "stats");
			players.put(p.getUniqueId(), new GamePlayer(p.getUniqueId()));
			p.getInventory().setItem(8, CraftingTableManager.getItemFromCustom(menu));
			ChannelPipeline cp = ((CraftPlayer)p).getHandle().playerConnection.networkManager.channel.pipeline();
			cp.addBefore("packet_handler", p.getName(), new PacketListener(p));
		}
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Player p : getServer().getOnlinePlayers()) {
					GamePlayer gp = getPlayer(p);
					String ex = "";
					if (!gp.getSkillPattern().equalsIgnoreCase("none")) ex = ChatColor.BLUE + "     [ " + ChatColor.AQUA + gp.getSkillPattern() + ChatColor.BLUE + "]";
					gp.onTick();
					sendActionBar(p, ChatColor.RED + "" + gp.getValue("hp").getLevel() + "/" + (250 + gp.getAttribute("hitpoint").getLevel() * 10)
						+ ex + ChatColor.YELLOW + "     " + gp.getValue("stam").getLevel() + "/" + (90 + gp.getAttribute("stamina").getLevel() * 10));
				}
			}
		}.runTaskTimer(this, 0, 2);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				List<Object> rq = new ArrayList<Object>();
				for (int i = 0; i < damageQueue.size(); i++) {
					ObjectSet<LivingEntity, ObjectSet<Entity, Double>> p = damageQueue.get(i);
					p.key.damage(p.value.value, p.value.key);
					System.out.println("hit2");
					rq.add(p);
				}
				for (Object o : rq) {
					damageQueue.remove(o);
				}
			}
		}.runTaskTimer(this, 0, 1);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Player p : getServer().getOnlinePlayers()) {
					GamePlayer gp = getPlayer(p);
					if (gp.getValue("stam").getLevel() < (90 + gp.getAttribute("stamina").getLevel() * 10)) {
						gp.getValue("stam").addLevel();
					}
				}
			}
		}.runTaskTimer(this, 0, 14);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				generateSeeds();
			}
			
		}.runTaskTimer(instance, 1200 * 60, 1);
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		players.put(e.getPlayer().getUniqueId(), new GamePlayer(e.getPlayer().getUniqueId()));
		e.getPlayer().getInventory().setItem(8, CraftingTableManager.getItemFromCustom(menu));
		ChannelPipeline p = ((CraftPlayer)e.getPlayer()).getHandle().playerConnection.networkManager.channel.pipeline();
		p.addBefore("packet_handler", e.getPlayer().getName(), new PacketListener(e.getPlayer()));
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		players.get(e.getPlayer().getUniqueId()).save();
		players.remove(e.getPlayer().getUniqueId());
		Channel c = ((CraftPlayer)e.getPlayer()).getHandle().playerConnection.networkManager.channel;
		c.eventLoop().submit(() -> {
			c.pipeline().remove(e.getPlayer().getName());
			return null;
		});
	}
	
	@EventHandler
	public void onSwapHands(PlayerSwapHandItemsEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onInteractEntity(PlayerInteractAtEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked().getType() == EntityType.VILLAGER && p.getGameMode() == GameMode.CREATIVE) {
			Villager v = ((Villager)e.getRightClicked());
			Inventory i = Bukkit.getServer().createInventory(v, 9, v.getUniqueId().toString());
			i.setContents(v.getInventory().getContents());
			i.setItem(8, new ItemStack(Material.BARRIER));
			p.openInventory(i);
		}
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (!getPlayer((Player) e.getWhoClicked()).getMode())
			e.setCancelled(true);
		if (players.get(e.getWhoClicked().getUniqueId()).getGui() != null) {
			e.setCancelled(true);
			if (players.get(e.getWhoClicked().getUniqueId()).getGui().getListener() != null && e.getClickedInventory() != e.getWhoClicked().getInventory()) {
				players.get(e.getWhoClicked().getUniqueId()).getGui().getListener().onClick((Player) e.getWhoClicked(), e.getCurrentItem(), e.getSlot(), e.getClick(), e.getInventory());
			}
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (!getPlayer((Player) e.getPlayer()).getMode())
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		Creature c = CraftingTableManager.isCustomMob(e.getEntity());
		if (c != null) {
			c.onDeath(e.getEntity().getKiller(), e.getEntity().getLocation());
		}
		e.getDrops().clear();
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (getPlayer(e.getPlayer()).getHitStun() > 0) e.setCancelled(true);
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		if (!getPlayer((Player) e.getPlayer()).getMode()) {
			if (dropOwners.get(e.getItem()) != null) {
				if (dropOwners.get(e.getItem()) == e.getPlayer()) {
					getPlayer(e.getPlayer()).addItem(CraftingTableManager.isCustomItem(e.getItem().getItemStack()));
					e.getItem().remove();
				}
			}
			ItemCustom c = CraftingTableManager.isCustomItem(e.getItem().getItemStack());
			if (c != null && dropOwners.get(e.getItem()) != e.getPlayer() && e.getItem().getTicksLived() >= 600) {
				getPlayer(e.getPlayer()).addItem(CraftingTableManager.isCustomItem(e.getItem().getItemStack()));
				e.getItem().remove();
			}
			e.setCancelled(true);	
		}
	}
	
	static int getHealth(LivingEntity e) {
		if (e.getCustomName() != null && e.getCustomName().contains("/")) {
			String[] s = ChatColor.stripColor(e.getCustomName()).split("/");
			return Integer.parseInt(s[s.length - 2].split(" ")[s[s.length - 2].split(" ").length - 1]);
		}
		return (int) e.getHealth();
	}
	
	static int getMaxHealth(LivingEntity e) {
		if (e.getCustomName() != null && e.getCustomName().contains("/")) {
			String[] s = ChatColor.stripColor(e.getCustomName()).split("\\/");
			return Integer.parseInt(s[s.length - 1]);
		}
		return (int) e.getHealth();
	}
	
	public static void spawnBoss(BossCreature boss, Location l) {
		l.getBlock().setType(Material.OBSIDIAN);
		new BukkitRunnable() {
			double t = 0;
			Random r = new Random();
			@Override
			public void run() {
				t++;
				CraftingTableManager.spawnParticleRGB(l.getWorld(), Color.PURPLE, l.getX() + r.nextDouble() - .5, l.getY() + t, l.getZ() + r.nextDouble() - .5, 0, 2f);
				if (t >= 20) {
					final LivingEntity le = CraftingTableManager.spawnMob(boss, l.add(0, t, 0));
					List<ObjectSet<OfflinePlayer, Integer>> dmg = new ArrayList<ObjectSet<OfflinePlayer, Integer>>();
					bossdmg.put(le, dmg);
					new BukkitRunnable() {
						BossBar b = Bukkit.getServer().createBossBar(boss.getName(), BarColor.RED, BarStyle.SEGMENTED_12, BarFlag.PLAY_BOSS_MUSIC, BarFlag.CREATE_FOG);
						@Override
						public void run() {
							for (Player p : l.getNearbyPlayers(30)) {
								if (!b.getPlayers().contains(p)) {
									b.addPlayer(p);
									dmg.add(new ObjectSet<OfflinePlayer, Integer>(p, 0));
								}
							}
							b.setProgress((double)getHealth(le) / (double) getMaxHealth(le));
							if (le.isDead() || le == null) {
								List<ObjectSet<OfflinePlayer, Integer>> x = sortIntArray(dmg);
								JavaUtils<ObjectSet<OfflinePlayer, Integer>> jt = new JavaUtils<ObjectSet<OfflinePlayer, Integer>>();
								PlayerNA na = new PlayerNA();
								for (Player pl : b.getPlayers()) {
									pl.sendMessage(" ");
									pl.sendMessage(" ");
									pl.sendMessage(ChatColor.YELLOW + "---------------------- " + ChatColor.LIGHT_PURPLE + "Battle Results" + ChatColor.YELLOW + " ----------------------");
									pl.sendMessage(" ");
									pl.sendMessage(ChatColor.YELLOW + "                          " + boss.getName());
									pl.sendMessage(" ");
									if (x.size() >= 1)
										pl.sendMessage(ChatColor.GOLD + "                 1st Damager: " + ChatColor.GREEN + jt.checkNotNull(x.get(0), new ObjectSet<OfflinePlayer, Integer>(na, 0)).key.getName());
									if (x.size() >= 2)
										pl.sendMessage(ChatColor.BLUE + "                 2nd Damager: " + ChatColor.GREEN + jt.checkNotNull(x.get(1), new ObjectSet<OfflinePlayer, Integer>(na, 0)).key.getName());
									if (x.size() >= 3)
										pl.sendMessage(ChatColor.RED + "                 3rd Damager: " + ChatColor.GREEN + jt.checkNotNull(x.get(2), new ObjectSet<OfflinePlayer, Integer>(na, 0)).key.getName());
									pl.sendMessage(" ");
									pl.sendMessage(" ");
								}
								for (ObjectSet<OfflinePlayer, Integer> b : x) {
									if (!b.key.isOnline()) continue; 
									getPlayer(b.key.getPlayer()).addItem(boss.getDropFor(b.value));
									b.key.getPlayer().sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "BOSS DROP! " + ChatColor.WHITE + boss.getDropFor(b.value).getDisplayName());
								}
								b.removeAll();
								this.cancel();
							}
						}
					}.runTaskTimer(instance, 0, 1);
					this.cancel();
				}
			}
		}.runTaskTimer(instance, 0, 5);
	}
	
	public int[] sortIntArray(int[] ir) {
		int[] newI = new int[ir.length];
		for (int i = 0; i < ir.length; i++) {
			int placement = 0;
			for (int i2 = 0; i2 < ir.length; i2++) {
				if (i == i2) continue;
				if (ir[i] < ir[i2]) placement++;
			}
			newI[placement] = ir[i];
		}
		return newI;
	}
	
	public static List<ObjectSet<OfflinePlayer, Integer>> sortIntArray(List<ObjectSet<OfflinePlayer, Integer>> ir) {
		List<ObjectSet<OfflinePlayer, Integer>> newI = new ArrayList<ObjectSet<OfflinePlayer, Integer>>();
		for (int i = 0; i < ir.size(); i++) {
			newI.add(null);
		}
		for (int i = 0; i < ir.size(); i++) {
			int placement = 0;
			for (int i2 = 0; i2 < ir.size(); i2++) {
				if (i == i2) continue;
				if (ir.get(i).value < ir.get(i2).value) placement++;
			}
			newI.set(placement, ir.get(i));
		}
		return newI;
	}
	
	public static void spawnHolo(Location l, String text) {
		Hologram h = HologramsAPI.createHologram(instance, l);
		h.appendTextLine(text);
		new BukkitRunnable() {
			int t = 0;
			@Override
			public void run() {
				t++;
				h.teleport(h.getLocation().add(0,0.01,0));
				if (t >= 40) {
					h.delete();
					this.cancel();
				}
			}
		}.runTaskTimer(instance, 0, 1);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onDamage(EntityDamageEvent e) {
		final Entity entity = e.getEntity();
		if (entity instanceof Player) {
			GamePlayer gp = getPlayer((Player)entity);
			if (gp.getParryTicks() > 0) {
				e.setDamage(0);
				e.setCancelled(true);
				return;
			}
		}
		if (entity.isInvulnerable()) return;
		if (entity instanceof LivingEntity && (!(entity instanceof Player)) && e.getCause() != DamageCause.PROJECTILE && e.getCause() != DamageCause.ENTITY_SWEEP_ATTACK && e.getCause() != DamageCause.ENTITY_EXPLOSION && e.getCause() != DamageCause.ENTITY_ATTACK) {
			LivingEntity l = (LivingEntity) entity;
			entity.setCustomNameVisible(true);
			new BukkitRunnable() {
				final int damage = (int) e.getDamage(); 
				@Override
				public void run() {
					String n = entity.getType().getName();
					if (CraftingTableManager.isCustomMob(l) != null) {
						n = CraftingTableManager.isCustomMob(l).getName();
					}
					String c = n.charAt(0) + "";
					entity.setCustomName(ChatColor.WHITE + n.replaceFirst(c, c.toUpperCase()) + ChatColor.RED + " " + Math.max((int) (getHealth(l) - damage), 0) + ChatColor.WHITE + "/" + ChatColor.RED + (int) getMaxHealth(l));
					spawnHolo(entity.getLocation().add(0, 0.8, 0), ChatColor.WHITE +  "" + damage);
					if (getHealth(l) <= 0) {
						l.setHealth(0);
					}
					this.cancel();
				}
			}.runTaskLater(this, 2);
			e.setDamage(0);
		}
		if (entity instanceof Player) {
			Player pl = (Player) entity;
			GamePlayer p = getPlayer(pl);
			int hp = p.getValue("hp").getLevel();
			int max = 250 + p.getAttribute("hitpoint").getLevel() * 10;
			int defbuff = 0;
			if (p.getAttributeBuff("defense") != null) {
				defbuff = p.getAttributeBuff("defense").getLevel();
			}
			for (ItemStack i : pl.getEquipment().getArmorContents()) {
				ItemCustom ca = CraftingTableManager.isCustomItem(i);
				if (ca != null && ca instanceof ArmorX) {
					defbuff += ((ArmorX)ca).getDefenseBoost();
				}
			}
			hp -= Math.max(e.getDamage() - (p.getAttribute("defense").getLevel() + defbuff), 1);
			if (hp <= 0) {
				pl.teleport(pl.getWorld().getSpawnLocation());
				hp = max;
			}
			if (e.getCause() == DamageCause.CUSTOM)
				p.setHitStun(5);
			spawnHolo(entity.getLocation().add(0, 0.8, 0), ChatColor.GRAY +  "" + (int) (Math.max(e.getDamage() - (p.getAttribute("defense").getLevel() + defbuff), 1)));
			p.setValue("hp", new GameStat("hp", 0, hp, "Health"));
			pl.setHealth(Math.max((double) hp / (double) max * 20, 0.1));
			//System.out.println(e.getDamage() + "| " + (e.getDamage() - p.getAttribute("defense").getLevel()) + "| " + hp + "| " + max);
			e.setDamage(0);
		}
		
		
	}
	
	@EventHandler
	public void onF(PlayerSwapHandItemsEvent e) {
		GamePlayer gp = getPlayer(e.getPlayer());
		if (gp.getEquippedWeapon() != null && CraftingTableManager.isCustomItem(e.getPlayer().getInventory().getItemInMainHand()) instanceof ToolX) {
			gp.tryParry();
		}
	}
	
	@EventHandler
	public void onPlayerHitEntity(EntityDamageByEntityEvent e) {
		Entity entity = e.getEntity();
		
		if (entity instanceof Player) {
			GamePlayer gp = getPlayer((Player)entity);
			if (gp.getParryTicks() > 0) {
				if (((CraftEntity)e.getDamager()).getHandle() instanceof CreatureLivingBase) {
					CreatureLivingBase.fromLiving((LivingEntity) e.getDamager()).stunTicks = 35;
				}
				if (e.getDamager() instanceof Player) {
					getPlayer((Player) e.getDamager()).setHitStun(15);
					((Player) e.getDamager()).sendTitle(new Title(" ", ChatColor.YELLOW + "* Stunned *", 0, 30, 0));
				}
				((Player)entity).sendTitle(new Title("", ChatColor.GREEN + "* Parry! *", 0, 30, 0));
				e.setDamage(0);
				e.setCancelled(true);
				return;
			}
		}
		
		if (CraftingTableManager.parentMap.containsKey(entity)) {
			//System.out.println("pp");
			LivingEntity ent = CraftingTableManager.parentMap.get(entity).getBukkitLivingEntity();
			ent.damage(e.getDamage(), e.getDamager());
			e.setCancelled(true);
			return;
		}
		if (entity.isInvulnerable()) return;
		
		if (e.getDamager().getCustomName() != null && e.getDamager().getCustomName().equalsIgnoreCase("dummy")) {
			e.setCancelled(true);
			return;
		}
		if (e.getDamager() instanceof Player) {
			GamePlayer gp = getPlayer((Player) e.getDamager());
			if (gp.getHitStun() > 0) {
				e.setCancelled(true);
				return;
			}
		}
		if (entity instanceof LivingEntity && (!(entity instanceof Player))) {
			LivingEntity l = (LivingEntity) entity;
			entity.setCustomNameVisible(true);
			new BukkitRunnable() {
				final int damage = (int) e.getDamage(); 
				String st = ChatColor.WHITE.toString();
				@Override
				public void run() {
					int ex = 0;
					double boost = 1;
					if (e.getDamager() instanceof Player) {
						System.out.println("player dmg: " + damage);
						Player p = (Player) e.getDamager();
						if (new Random().nextInt(99) < getPlayer(p).getAttribute("crit_chance").getLevel()) {
							double crit = getPlayer(p).getAttribute("crit_damage").getLevel();
							crit /= 100;
							boost = crit * 4;
							st = ChatColor.RED + ChatColor.BOLD.toString() + "緊要";
							p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1f, 1f);
							p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f);
							CraftingTableManager.spawnParticleRGB(p.getWorld(), Color.RED, entity.getLocation().getX(), entity.getLocation().getY() + 0.7, entity.getLocation().getZ(), 3, 2.6f);
						}
						double str = getPlayer(p).getAttribute("strength").getLevel(), cmbt = getPlayer(p).getStat("cmbt_level").getLevel(), dam = damage;
						ex = (int) (((str + cmbt) / 10.0) * dam);
						//System.out.println("damage:" + dam + ", base:" + ((str + cmbt) / 10.0) + "f:" + ex + "|" + ((str + cmbt) / 10.0) * dam);
						if (bossdmg.containsKey(entity)) {
							for (ObjectSet<OfflinePlayer, Integer> x : bossdmg.get(entity)) {
								if (x.key == p) {
									x.value += Math.max((int) (getHealth(l) - ((damage + ex) * boost)), 0);
								}
							}
						}
					}
					String n = entity.getType().getName();
					if (CraftingTableManager.isCustomMob(l) != null) {
						n = CraftingTableManager.isCustomMob(l).getName();
					}
					String c = n.charAt(0) + "";
					entity.setCustomName(ChatColor.WHITE + n.replaceFirst(c, c.toUpperCase()) + ChatColor.RED + " " + Math.max((int) (getHealth(l) - ((damage + ex) * boost)), 0) + ChatColor.WHITE + "/" + ChatColor.RED + (int) getMaxHealth(l));
					spawnHolo(entity.getLocation().add(0, 0.8, 0), st + "" + (int) ((damage + ex) * boost));
					if (getHealth(l) <= 0) {
						l.setHealth(0);
						if (e.getDamager() instanceof Player) {
							Player p = (Player) e.getDamager();
							getPlayer(p).addExp("cmbt_level", getMaxHealth(l) / 10);
							double rx = new Random().nextDouble();
							getPlayer(p).getAttribute("cor").addLevels((int) (getMaxHealth(l) / 10 * (1 + rx)));
							spawnHolo(entity.getLocation().add(0, 1.2, 0), ChatColor.GOLD + "+" + (int) (getMaxHealth(l) / 10 * (1 + rx)) + ChatColor.GOLD + ChatColor.BOLD.toString() + "コル");
						}
					}
					this.cancel();
				}
			}.runTaskLater(this, 2);
			e.setDamage(0);
		}
	}
	
	
	@EventHandler
	public void onRegen(EntityRegainHealthEvent e) {
		if (e.getEntity() instanceof Player) {
			Player pl = (Player) e.getEntity();
			GamePlayer p = getPlayer(pl);
			double hp = p.getValue("hp").getLevel();
			int max = 250 + p.getAttribute("hitpoint").getLevel() * 10;
			hp +=  (e.getAmount()) / 20 * max + p.getAttribute("regen").getLevel();
			if (hp > max) hp = max;
			p.setValue("hp", new GameStat("hp", 0, Math.max(Math.min((int) Math.ceil(hp), max), 0), "Health"));
			
			pl.setHealth(Math.min(hp / max * 20, 20));
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onSpawn(EntitySpawnEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			LivingEntity l = (LivingEntity) e.getEntity();
			e.getEntity().setCustomNameVisible(true);
			e.getEntity().setCustomName(ChatColor.WHITE + e.getEntity().getName() + ChatColor.RED + " " + (int) l.getHealth() + ChatColor.WHITE + "/" + ChatColor.RED + (int) getMaxHealth(l));
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		//System.out.println("n" + e.getAction());
		GamePlayer gp = getPlayer(e.getPlayer());
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.END_PORTAL_FRAME) {
			if (e.getClickedBlock().getLocation().subtract(0, 1, 0).getBlock() != null) {
				Material x = e.getClickedBlock().getLocation().subtract(0, 1, 0).getBlock().getType();
				switch(x) {
				case ACACIA_LOG:
					spawnBoss(fl1_illfang, e.getClickedBlock().getLocation());
					break;
				}
			}
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.CAULDRON) {
			CookingMenu cm = new CookingMenu(e.getPlayer(), 0);
			cm.send();
			e.setCancelled(true);
			return;
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.CAMPFIRE) {
			CookingMenu cm = new CookingMenu(e.getPlayer(), 0);
			cm.send();
			e.setCancelled(true);
			return;
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.BREWING_STAND) {
			BrewingMenu cm = new BrewingMenu(e.getPlayer(), 0);
			cm.send();
			e.setCancelled(true);
			return;
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.ENCHANTING_TABLE) {
			EnhanceMenu cm = new EnhanceMenu(e.getPlayer(), 0);
			cm.send();
			e.setCancelled(true);
			return;
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.ANVIL) {
			AnvilMenu cm = new AnvilMenu(e.getPlayer(), 0);
			cm.send();
			e.setCancelled(true);
			return;
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.FURNACE) {
			FurnaceMenu cm = new FurnaceMenu(e.getPlayer(), 0);
			cm.send();
			e.setCancelled(true);
			return;
		}
		if (e.getItem() != null && e.getAction().toString().toLowerCase().contains("right")) {
			
		}
		if (e.getItem() != null && e.getAction().toString().toLowerCase().contains("left")) {
			//System.out.println("left");
			//System.out.println("" + e.getBlockFace());
			

			ItemCustom item = CraftingTableManager.isHoldingCustomItem(e.getPlayer());
			if (item != null) {
				
			}
		}
	}
	
	public static Entity onRClick(Player pl, ItemCustom hand) {
		GamePlayer gp = getPlayer(pl);
		if (!gp.canHit()) return null;
		gp.hit();
		if (hand != null && hand instanceof ToolX) {
			for (Player p : pl.getWorld().getPlayers()) {
				if (p.getLocation().distance(pl.getLocation()) < 24) {
					CraftPlayer c = (CraftPlayer) pl;
					CraftPlayer ca = ((CraftPlayer)p);
					PacketPlayOutAnimation a = new PacketPlayOutAnimation(c.getHandle(), 0);
					ca.getHandle().playerConnection.sendPacket(a);
				}
			}
			new BukkitRunnable() {
				int t = 45;
				Location l = pl.getEyeLocation();
				@Override
				public void run() {
					t -= 10;
					double x = pl.getEyeLocation().getDirection().getX() * 2;
					double y = (pl.getEyeLocation().getDirection().getY() + Math.sin(Math.toRadians(t))) * 2;
					double z = pl.getEyeLocation().getDirection().getZ() * 2;
					CraftingTableManager.spawnParticleRGB(pl.getWorld(), Color.YELLOW, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
					if (t < -30) {
						this.cancel();
					}
				}
			}.runTaskTimer(instance, 0, 1);
			for (double t = 1; t < 3.5; t += 0.1) {
				Location l = pl.getEyeLocation();
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				l.add(x, y, z);
				for (LivingEntity e : pl.getWorld().getLivingEntities()) {
					if (e != pl && e.getBoundingBox().contains(l.getX(), l.getY(), l.getZ())) {
						//damageQueue.add(new ObjectSet<LivingEntity, ObjectSet<Entity, Double>>(e, new ObjectSet<Entity, Double>(pl, (double) (((ToolX)hand).getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2))));
						//((CraftLivingEntity)e).getHandle().damageEntity(DamageSource.playerAttack(((CraftPlayer)pl).getHandle()), ((ToolX)hand).getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2);
						return e;
					}
				}
			}
		}
		return null;
	}
	
	public static void damageEntity(LivingEntity t, double dam, Entity a) {
		if (t == null || a == null) {
			return;
		}
		damageQueue.add(new ObjectSet<LivingEntity, ObjectSet<Entity,Double>>(t, new ObjectSet<Entity, Double>(a, dam)));
	}
	
	public static ServerManager instance;
	
	@EventHandler
	public void cmdPPc(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equalsIgnoreCase("/spawn")) {
			e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
			e.setCancelled(true);
		}
		if (!e.getPlayer().isOp()) return;
		if (e.getMessage().equalsIgnoreCase("/swapmodes")) {
			getPlayer((Player) e.getPlayer()).switchMode();
			e.setCancelled(true);
		}
		if (e.getMessage().toLowerCase().contains("/item")) {
			e.setCancelled(true);
			String[] args = e.getMessage().split(" ");
			if (args.length < 2) {
				e.getPlayer().sendMessage(ChatColor.RED + "Please provide and item id");
				return;
			}
			ItemCustom c = CraftingTableManager.getById(args[1]);
			if (c == null) {
				e.getPlayer().sendMessage(ChatColor.RED + "Could not find item for provided id");
				return;
			}
			getPlayer(e.getPlayer()).addItem(c);
			e.getPlayer().sendMessage(ChatColor.GREEN + "You were given a " + c.getDisplayName());
		}
		if (e.getMessage().equalsIgnoreCase("/fixhealth")) {
			getPlayer(e.getPlayer()).addSkill(new MeteorBreak());
		}
	}
	
	@EventHandler
	public void onForage(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode() == GameMode.CREATIVE) return;
		e.setCancelled(true);
		if (!e.getBlock().getType().toString().toLowerCase().contains("leaves"))
			return;
		GamePlayer gp = getPlayer(p);
		int x = new Random().nextInt(forage.size());
		ItemCustom c = forage.get(x);
		gp.addItem(c);
		spawnHolo(e.getBlock().getLocation().add(0, 1.5, 0), "+ " + c.getDisplayName());
		gp.addExp("forage", EconomyManager.getSellPrice(c) / 10);
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		players.get(e.getPlayer().getUniqueId()).setCurrentGui(null);
	}
	
	public void onDisable() {
		for (Player p : getServer().getOnlinePlayers()) {
			Channel c = ((CraftPlayer)p).getHandle().playerConnection.networkManager.channel;
			c.eventLoop().submit(() -> {
				c.pipeline().remove(p.getName());
				return null;
			});
		}
		for (GamePlayer p : players.values()) {
			p.save();
		}
	}
	
	public static void sendActionBar(Player player, String args) {
		 try {
			IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + args + "\"}");
			PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.a((byte) 2));
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(ppoc);
		 }catch (JsonSyntaxException e) {
			 return;
		 }
	}
	
}
