package shadowdev.player;

public class GameStat {

	String title, name2;
	int exp;
	int level;
	
	public GameStat(String name, int exp, int level, String name2) {
		title = name;
		this.exp = exp;
		this.level = level;
		this.name2 = name2;
	}

	public int getLevel() {
		return level;
	}
	
	public String getName() {
		return name2;
	}
	
	public void setLevel(int i) {
		level = i;
	}
	
	public void addLevel() {
		level++;
	}
	
	public void removeLevel() {
		level--;
	}
	
	public int getExp() {
		return exp;
	}

	public void addLevels(int l) {
		level += l;
	}
	
}
