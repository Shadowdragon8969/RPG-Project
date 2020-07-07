package shadowdev.player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.destroystokyo.paper.Title;

import dev.shadow.api.CraftingTableManager;
import dev.shadow.api.ItemCustom;
import dev.shadow.api.ObjectSet;
import net.md_5.bungee.api.ChatColor;
import shadowdev.player.gui.PlayerGui;
import shadowdev.server.ServerManager;

public class GamePlayer {

	File f;
	FileConfiguration yml;
	PlayerGui currentGui = null;
	UUID playerUUID;
	String skillPattern = "none";
	int ptCD = 0;
	char lastclick = 'l';
	int hitStun = 0;
	int cCD = 0;
	int hitCD = 0;
	int parryTicks = 0;
	HashMap<String, GameStat> stats = new HashMap<String, GameStat>();
	HashMap<String, GameStat> attributeBuffs = new HashMap<String, GameStat>();
	HashMap<String, GameStat> attributes = new HashMap<String, GameStat>();
	HashMap<String, GameStat> values = new HashMap<String, GameStat>();
	List<Buff> buffs = new ArrayList<Buff>();
	List<ItemCustom> inventory = new ArrayList<ItemCustom>();
	List<Skill> skills = new ArrayList<Skill>();
	Scoreboard sb;
	Objective main;
	boolean mode = false;
	
	private void fileStats(FileConfiguration fc, String section, HashMap<String, GameStat> stats) {
		for (GameStat s : stats.values()) {
			fc.set(section + "." + s.title + ".level", s.level);
			fc.set(section + "." + s.title + ".exp", s.exp);
		}
	}
	
	private void loadStats(FileConfiguration fc, String section, HashMap<String, GameStat> stats) {
		for (GameStat s : stats.values()) {
			GameStat r = new GameStat(s.title, fc.getInt(section + "." + s.title + ".exp"), fc.getInt(section + "." + s.title + ".level"), s.getName());
			stats.put(s.title, r);
		}
	}
	
	public void addExp(String stat, int amt) {
		stats.get(stat).exp += amt;
		if (stats.get(stat).exp >= Math.pow(stats.get(stat).level, 2) * 15) {
			stats.get(stat).exp -= Math.pow(stats.get(stat).level, 2) * 15;
			stats.get(stat).level++;
			Bukkit.getPlayer(playerUUID).sendMessage(ChatColor.AQUA + "You have increased your " + ChatColor.GOLD + stats.get(stat).getName() + " to level " + ChatColor.GOLD + stats.get(stat).level);
			getAttribute("atbpts").addLevel();
			List<ObjectSet<Integer, Class<? extends Skill>>> l = ServerManager.unlockMap.get(stat);
			if (l != null) {
				for (ObjectSet<Integer, Class<? extends Skill>> x : l) {
					if (x.key == getStat(stat).getLevel()) {
						try {
							Skill sk = x.value.newInstance();
							skills.add(sk);
							Bukkit.getPlayer(playerUUID).sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "SKILL UNLOCK! " + ChatColor.GREEN + "You learned " + ChatColor.AQUA + sk.name);
						} catch (InstantiationException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public int getParryTicks() {
		return parryTicks;
	}
	
	public void setParryTicks(int ticks) {
		parryTicks = ticks;
	}
	
	public ItemCustom getEquippedWeapon() {
		ItemStack is = Bukkit.getServer().getPlayer(playerUUID).getInventory().getItem(2);
		return CraftingTableManager.isCustomItem(is);
	}
	
	public void equipWeapon(ItemCustom ic) {
		Bukkit.getServer().getPlayer(playerUUID).getInventory().setItem(2, CraftingTableManager.getItemFromCustom(ic));
	}
	
	public void setHitStun(int a) {
		hitStun = a;
	}
	
	public void addSkill(Skill s) {
		skills.add(s);
	}
	
	public void addBuff(Buff b) {
		b.onApply(Bukkit.getPlayer(playerUUID));
		buffs.add(b);
	}
	
	public char getLastClick() {
		return lastclick;
	}
	
	public void setLastClick(char c) {
		lastclick = c;
	}
	
	public int getHitStun() {
		return hitStun;
	}
	
	public void tryParry() {
		if (hitStun > 0) return;
		Player p = Bukkit.getServer().getPlayer(playerUUID);
		if (getValue("stam").getLevel() >= 55) {
			parryTicks = 8;
			getValue("stam").addLevels(-55);
			p.sendTitle(new Title(" ", ChatColor.GRAY + "* Parry *", 0, 16, 0));
		}else {
			p.sendTitle(new Title(" ", ChatColor.RED + "* No Stamina *", 0, 30, 0));
		}
		
	}
	 
	public void onTick() {
		if (hitCD > 0) hitCD--;
		if (ptCD > 0) ptCD--;
		if (ptCD <= 0) skillPattern = "none";
		if (cCD > 0) cCD--;
		if (hitStun > 0) hitStun--;
		if (parryTicks > 0) parryTicks --;
		updateScoreboard();
		for (Buff b : buffs) {
			b.dur -= 2;
			if (b.dur <= 0) {
				b.onLoss(Bukkit.getPlayer(playerUUID));
				buffs.remove(b);
				break;
			}
		}
	}
	
	public String getSkillPattern() {
		return skillPattern;
	}
	
	public void addToSkillPattern(char c) {
		if (skillPattern.equalsIgnoreCase("none")) {
			skillPattern = "";
		}
		if (skillPattern.length() > 9 || cCD > 0) return;
		skillPattern += c + " ";
		ptCD = 14;
		cCD = 2;
		if (skillPattern.length() >= 9) {
			String[] s = skillPattern.split(" ");
			for (Skill sk : skills) {
				boolean t = true;
				for (int i = 0; i < 5; i++) {
					if (!s[i].equalsIgnoreCase(sk.pattern[i])) t = false;
				}
				if (t) {
					Player p = Bukkit.getServer().getPlayer(playerUUID);
					if (getValue("stam").getLevel() >= sk.getStaminaCost()) {
						getValue("stam").addLevels(-sk.getStaminaCost());
						sk.useSkill(Bukkit.getServer().getPlayer(playerUUID));
						p.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "SKILL ACTIVATED! " + ChatColor.GOLD + sk.getName() + ChatColor.RED + " -" + sk.getStaminaCost() + " Stamina");
					}else
						p.sendMessage(ChatColor.RED + "Not enough stamina to use this skill.");
				}
			}
		}
	}
	
	public final int ccd() {
		return cCD;
	}
	
	public List<Skill> getSkills() {
		return skills;
	}
	
	public boolean canHit() {
		return hitCD == 0;
	}
	
	public void hit() {
		hitCD = 3;
	}
	
	public GameStat getValue(String s) {
		return values.get(s);
	}
	
	public GameStat getAttribute(String s) {
		return attributes.get(s);
	}
	
	public GameStat getStat(String s) {
		return stats.get(s);
	}
	
	public GameStat getAttributeBuff(String s) {
		return attributeBuffs.get(s);
	}
	
	public void setValue(String s, GameStat a) {
		values.put(s, a);
	}
	
	public void setStat(String s, GameStat a) {
		stats.put(s, a);
	}
	
	public void setAttribute(String s, GameStat a) {
		attributes.put(s, a);
	}
	
	public void setAttributeBuff(String s, GameStat a) {
		attributeBuffs.put(s, a);
	}
	
	public List<ItemCustom> getInventory() {
		return inventory;
	}
	
	public PlayerGui getGui() {
		return currentGui;
	}
	
	public void setCurrentGui(PlayerGui g) {
		currentGui = g;
	}
	
	public void switchMode() {
		mode = !mode;
	}
	
	public boolean getMode() {
		return mode;
	}
	
	private void fileInventory(FileConfiguration fc) {
		String a = "";
		for (ItemCustom i : inventory) {
			if (i == null) continue;
			if (a.length() < 1) {
				a += i.getId();
				continue;
			}else {
				a += ";" + i.getId();
			}
		}
		fc.set("inventory.contents", a);
	}
	
	private void fileSkills(FileConfiguration fc) {
		String a = "";
		for (Skill i : skills) {
			if (i == null) continue;
			if (a.length() < 1) {
				a += i.getId();
				continue;
			}else {
				a += ";" + i.getId();
			}
		}
		fc.set("skills.contents", a);
	}
	
	private void loadInventory(FileConfiguration fc) {
		String[] a = fc.getString("inventory.contents").split(";");
		for (int i = 0; i < a.length; i++) {
			inventory.add(CraftingTableManager.getById(a[i]));
		}
	}
	
	private void loadSkills(FileConfiguration fc) {
		String[] a = fc.getString("skills.contents").split(";");
		for (int i = 0; i < a.length; i++) {
			if (a[i].equalsIgnoreCase("") || a[i].equalsIgnoreCase(" ")) continue;
			try {
				skills.add(ServerManager.skillmap.get(a[i]).newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	int stage = 0;
	
	void initScoreBoard() {
		Calendar today = Calendar.getInstance();
		sb = ServerManager.board.getNewScoreboard();
		if (sb == null) System.out.println("sb null");
		if (Bukkit.getServer().getPlayer(playerUUID) == null) System.out.println("pl  null");
		main = sb.registerNewObjective("main", ChatColor.AQUA.toString() + ChatColor.BOLD + "SAOMC", "dummy");
		main.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "SAOMC");
		main.getScore(ChatColor.GRAY.toString() + today.get(Calendar.MONTH) + "/" + today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR)).setScore(15);
		main.getScore(" ").setScore(14);
		main.getScore("Location" + ChatColor.YELLOW + ":").setScore(13);
		main.getScore(ChatColor.YELLOW +"  Floor: " + ChatColor.WHITE.toString() + "Finding Floor...").setScore(12);
		main.getScore(ChatColor.GREEN + "  Finding Biome...").setScore(11);
		main.getScore("  ").setScore(10);
		main.getScore(ChatColor.GOLD + "Stats").setScore(9);
		main.getScore(ChatColor.RED + "  Hitpoints: Loading...").setScore(8);
		main.getScore(ChatColor.YELLOW + "  Stamina: Loading...").setScore(7);
		main.getScore("   ").setScore(6);
		main.getScore(ChatColor.GREEN + "Combat Level: Loading...").setScore(5);
		main.getScore(ChatColor.GREEN + "EXP: Loading...").setScore(4);
		main.getScore("    ").setScore(3);
		main.getScore(ChatColor.YELLOW + Bukkit.getServer().getIp()).setScore(2);
		main.getScore(ChatColor.YELLOW + "Ping: " + ((CraftPlayer)Bukkit.getServer().getPlayer(playerUUID)).getHandle().ping).setScore(1);
		main.setDisplaySlot(DisplaySlot.SIDEBAR);
		Bukkit.getServer().getPlayer(playerUUID).setScoreboard(sb);
	}
	
	void scrollScoreboardTitle() {
		stage++;
		if (stage >= 50) stage = 0;
		if (stage == 10) {
			main.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "S" + ChatColor.AQUA.toString() + ChatColor.BOLD + "AOMC");
		}
		if (stage == 12) {
			main.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "S" + ChatColor.WHITE.toString() + ChatColor.BOLD + "A" + ChatColor.AQUA.toString() + ChatColor.BOLD + "OMC");
		}
		if (stage == 14) {
			main.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "SA" + ChatColor.WHITE.toString() + ChatColor.BOLD + "O" + ChatColor.AQUA.toString() + ChatColor.BOLD + "MC");
		}
		if (stage == 16) {
			main.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "SAO" + ChatColor.WHITE.toString() + ChatColor.BOLD + "M" + ChatColor.AQUA.toString() + ChatColor.BOLD + "C");
		}
		if (stage == 18) {
			main.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "SAOM" + ChatColor.WHITE.toString() + ChatColor.BOLD + "C");
		}
		if (stage == 20) {
			main.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "SAOMC");
		}
	}
	
	void updateLines() {
		for (String s : sb.getEntries()) {
			Score sc = main.getScore(s);
			if (sc.getScore() == 12) {
				if (s.equalsIgnoreCase(ChatColor.YELLOW +"  Floor: " + ChatColor.WHITE.toString() + getValue("floor").getLevel())) continue;
				sb.resetScores(s);
				main.getScore(ChatColor.YELLOW +"  Floor: " + ChatColor.WHITE.toString() + getValue("floor").getLevel()).setScore(12);
			}
			if (sc.getScore() == 11) {
				if (s.equalsIgnoreCase(ChatColor.GREEN + "  " + Bukkit.getServer().getPlayer(playerUUID).getWorld().getBiome(Bukkit.getServer().getPlayer(playerUUID).getLocation().getBlockX(), Bukkit.getServer().getPlayer(playerUUID).getLocation().getBlockY(), Bukkit.getServer().getPlayer(playerUUID).getLocation().getBlockZ()).getKey().getKey())) continue;
				sb.resetScores(s);
				main.getScore(ChatColor.GREEN + "  " + Bukkit.getServer().getPlayer(playerUUID).getWorld().getBiome(Bukkit.getServer().getPlayer(playerUUID).getLocation().getBlockX(), Bukkit.getServer().getPlayer(playerUUID).getLocation().getBlockY(), Bukkit.getServer().getPlayer(playerUUID).getLocation().getBlockZ()).getKey().getKey()).setScore(11);
			}
			if (sc.getScore() == 8) {
				if (s.equalsIgnoreCase(ChatColor.RED + "  Hitpoints: " + ChatColor.WHITE + "" + getValue("hp").getLevel() + ChatColor.YELLOW + "/" + ChatColor.WHITE + (250 + getAttribute("hitpoint").getLevel() * 10))) continue;
				sb.resetScores(s);
				main.getScore(ChatColor.RED + "  Hitpoints: " + ChatColor.WHITE + "" + getValue("hp").getLevel() + ChatColor.YELLOW + "/" + ChatColor.WHITE + (250 + getAttribute("hitpoint").getLevel() * 10)).setScore(8);
			}
			if (sc.getScore() == 7) {
				if (s.equalsIgnoreCase(ChatColor.YELLOW + "  Stamina: " + ChatColor.WHITE + "" + getValue("stam").getLevel() + ChatColor.YELLOW + "/" + ChatColor.WHITE +  (90 + getAttribute("stamina").getLevel() * 10))) continue;
				sb.resetScores(s);
				main.getScore(ChatColor.YELLOW + "  Stamina: " + ChatColor.WHITE + "" + getValue("stam").getLevel() + ChatColor.YELLOW + "/" + ChatColor.WHITE +  (90 + getAttribute("stamina").getLevel() * 10)).setScore(7);
			}
			if (sc.getScore() == 5) {
				if (s.equalsIgnoreCase(ChatColor.GREEN + "Combat Level: " + ChatColor.WHITE + getStat("cmbt_level").getLevel())) continue;
				sb.resetScores(s);
				main.getScore(ChatColor.GREEN + "Combat Level: " + ChatColor.WHITE + getStat("cmbt_level").getLevel()).setScore(5);
			}
			if (sc.getScore() == 4) {
				if (s.equalsIgnoreCase(ChatColor.GREEN + "EXP: " + ChatColor.WHITE + getStat("cmbt_level").getExp() + ChatColor.YELLOW + "/" + ChatColor.WHITE + (int) Math.pow(stats.get("cmbt_level").level, 2) * 15)) continue;
				sb.resetScores(s);
				main.getScore(ChatColor.GREEN + "EXP: " + ChatColor.WHITE + getStat("cmbt_level").getExp() + ChatColor.YELLOW + "/" + ChatColor.WHITE + (int) Math.pow(stats.get("cmbt_level").level, 2) * 15).setScore(4);
			}
			if (sc.getScore() == 1) {
				if (s.equalsIgnoreCase(ChatColor.YELLOW + "Ping: " + ((CraftPlayer)Bukkit.getServer().getPlayer(playerUUID)).getHandle().ping)) continue;
				sb.resetScores(s);
				main.getScore(ChatColor.YELLOW + "Ping: " + ((CraftPlayer)Bukkit.getServer().getPlayer(playerUUID)).getHandle().ping).setScore(1);
			}
		}
	}
	
	void updateScoreboard() {
		scrollScoreboardTitle();
		updateLines();
	}
	
	public GamePlayer(UUID id) {
		playerUUID = id;
		if (Bukkit.getServer().getOfflinePlayer(id).isOnline())
			initScoreBoard();
		f = new File(Bukkit.getServer().getPluginManager().getPlugin("SwordCraftOnline").getDataFolder()  + File.separator + "PlayerDatabase", playerUUID.toString() + ".yml");
		yml = YamlConfiguration.loadConfiguration(f);
		values.put("hp", new GameStat("hp", 0, 20, "Health"));
		values.put("stam", new GameStat("stam", 0, 20, "Stamina"));
		values.put("floor", new GameStat("floor", 0, 1, "Floor"));
		
		stats.put("cmbt_level", new GameStat("cmbt_level", 0, 1, "Combat Level"));
		stats.put("smithing", new GameStat("smithing", 0, 1, "Smithing Level"));
		stats.put("forage", new GameStat("forage", 0, 1, "Foraging Level"));
		stats.put("cooking", new GameStat("cooking", 0, 1, "Cooking Level"));
		stats.put("alchemy", new GameStat("alchemy", 0, 1, "Alchemy Level"));
		
		attributes.put("atbpts", new GameStat("atbpts", 0, 1, "Attribute Points"));
		attributes.put("cor", new GameStat("cor", 0, 1, "コル"));
		attributes.put("strength", new GameStat("strength", 0, 1, "Strength"));
		attributes.put("stamina", new GameStat("stamina", 0, 1, "Max Stamina"));
		attributes.put("defense", new GameStat("defense", 0, 1, "Defense"));
		attributes.put("hitpoint", new GameStat("hitpoint", 0, 1, "Max Health"));
		attributes.put("regen", new GameStat("regen", 0, 1, "Health Regeneration"));
		attributes.put("crit_damage", new GameStat("crit_damage", 0, 50, "Critical Damage"));
		attributes.put("crit_chance", new GameStat("crit_chance", 0, 15, "Critical Chance"));
		if (f.exists()) {
			loadStats(yml, "stats", stats);
			loadStats(yml, "attributes", attributes);
			loadStats(yml, "values", values);
			loadInventory(yml);
			loadSkills(yml);
		}else {
			yml.createSection("values");
			yml.createSection("stats");
			yml.createSection("attributes");
			yml.createSection("inventory");
			yml.createSection("skills");
			
			fileStats(yml, "values", values);
			fileStats(yml, "stats", stats);
			fileStats(yml, "attributes", attributes);
			fileInventory(yml);
			fileSkills(yml);
			
			try {
				yml.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			addItem(CraftingTableManager.getById("woodSword"));
		}
	}
	
	public void addItem(ItemCustom i) {
		inventory.add(i);
	}
	
	public void removeItem(ItemCustom t) {
		inventory.remove(t);
	}
	
	public void save() {
		fileStats(yml, "values", values);
		fileStats(yml, "stats", stats);
		fileStats(yml, "attributes", attributes);
		fileInventory(yml);
		fileSkills(yml);
		try {
			yml.save(f);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
