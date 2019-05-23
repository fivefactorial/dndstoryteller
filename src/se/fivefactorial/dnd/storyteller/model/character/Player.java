package se.fivefactorial.dnd.storyteller.model.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Player {

	public static final String[] SKILLS = new String[] { "Acrobatics", "Animal Handling", "Arcana", "Athletics",
			"Deception", "History", "Insight", "Intimidation", "Investigation", "Medicine", "Nature", "Perception",
			"Performance", "Persuasion", "Religion", "Sleight of Hand", "Stealth", "Survival" };
	private static final int[] SKILL_STAT = new int[] { 1, 4, 3, 0, 5, 3, 4, 5, 3, 4, 3, 4, 5, 5, 3, 1, 1, 4 };
	private String name;
	private int prof;
	private int[] stats;
	private int[] profs;
	private Random random;

	private int exp;
	private int cp;
	private int sp;
	private int ep;
	private int gp;
	private int pp;
	private ArrayList<String> otherRewards;
	private HashMap<String, Integer> tokens;

	public Player(String name, int prof, int[] stats, int[] profs) {
		this.name = name;
		this.prof = prof;
		this.stats = stats;
		this.profs = profs;

		random = new Random();

		otherRewards = new ArrayList<>();
		tokens = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public int getProf() {
		return prof;
	}

	public String getStatsString() {
		StringBuilder sb = new StringBuilder();
		for (int stat : stats) {
			int mod = stat / 2 - 5;
			sb.append(String.format("%s (%d) ", mod < 0 ? "" + mod : "+" + mod, stat));
		}
		return sb.toString().trim();
	}

	public List<String> getProficiencies() {
		ArrayList<String> data = new ArrayList<>();

		for (int i = 0; i < SKILLS.length; i++) {
			if (profs[i] > 0) {
				int mod = profs[i] * prof + (stats[SKILL_STAT[i]] / 2) - 5;
				data.add(String.format("%s %s%s", SKILLS[i], mod < 0 ? "" + mod : "+" + mod, profs[i] == 2 ? "*" : ""));
			}
		}
		return data;
	}

	public boolean roll(String check, int dc) {
		int mod = 0;
		switch (check) {
		case "Strength":
			mod = stats[0] / 2 - 5;
			break;
		case "Dexterity":
			mod = stats[1] / 2 - 5;
			break;
		case "Constitution":
			mod = stats[2] / 2 - 5;
			break;
		case "Inteligence":
			mod = stats[3] / 2 - 5;
			break;
		case "Wisdom":
			mod = stats[4] / 2 - 5;
			break;
		case "Charisma":
			mod = stats[5] / 2 - 5;
			break;
		default:
			boolean found = false;
			for (int i = 0; i < SKILLS.length; i++) {
				if (SKILLS[i].equals(check)) {
					found = true;
					mod = profs[i] * prof + (stats[SKILL_STAT[i]] / 2) - 5;
				}
			}
			if (!found) {
				System.err.printf("Error! illegal check '%s'\nTerminating program.\n", check);
				System.exit(0);
			}
		}

		int roll = random.nextInt(20) + 1;

		int result = roll + mod;
		System.out.printf("d20+%d\t%d+%d DC%d", mod, roll, mod, dc);
		return result >= dc;
	}

	public void addExp(int exp) {
		this.exp += exp;
	}

	public String getExp() {
		return exp == 0 ? "" : "" + exp + " exp";
	}

	public void addMoney(int cp, int sp, int ep, int gp, int pp) {
		this.cp += cp;
		this.sp += sp;
		this.ep += ep;
		this.gp += gp;
		this.pp += pp;
	}

	public String getMoney() {
		String text = "";
		text = addMoneyText(text, pp, "pp");
		text = addMoneyText(text, gp, "gp");
		text = addMoneyText(text, ep, "ep");
		text = addMoneyText(text, sp, "sp");
		text = addMoneyText(text, cp, "cp");
		return text;
	}

	private String addMoneyText(String text, int coin, String coinName) {
		if (coin != 0)
			text += String.format("%d%s ", coin, coinName);
		return text;
	}

	public void addOtherReward(String reward) {
		otherRewards.add(reward);
	}

	public List<String> getRewards() {
		ArrayList<String> data = new ArrayList<>();
		String exp = getExp();
		if (!exp.equals(""))
			data.add(exp);
		String money = getMoney();
		if (!money.equals(""))
			data.add(money);
		if (!otherRewards.isEmpty()) {
			data.add("Other:");
			for (String reward : otherRewards) {
				data.add("- " + reward);
			}
		}
		return data;
	}

	public void addToken(String token) {
		Integer n = tokens.get(token);
		if (n == null)
			n = 0;
		n++;
		tokens.put(token,n);
	}
	
	public int getToken(String token) {
		Integer i = tokens.get(token);
		return i == null ? 0 : i;
	}

}
