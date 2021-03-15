package skillxpmod.utils;

public class SkillUtil {

	public static int[] skillXPPerLevel = {0, 50, 125, 200, 300, 500, 750, 1000, 1500, 2000, 3500, 5000, 7500, 10000, 15000, 20000, 30000, 50000,
			   75000, 100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000, 1100000,
			   1200000, 1300000, 1400000, 1500000, 1600000, 1700000, 1800000, 1900000, 2000000, 2100000, 2200000,
			   2300000, 2400000, 2500000, 2600000, 2750000, 2900000, 3100000, 3400000, 3700000, 4000000, 4300000,
			   4600000, 4900000, 5200000, 5500000, 5800000, 6100000, 6400000, 6700000, 7000000};
	static int[] dungeonsXPPerLevel = {0, 50, 75, 110, 160, 230, 330, 470, 670, 950, 1340, 1890, 2665, 3760, 5260, 7380, 10300, 14400,
			  20000, 27600, 38000, 52500, 71500, 97000, 132000, 180000, 243000, 328000, 445000, 600000, 800000,
			  1065000, 1410000, 1900000, 2500000, 3300000, 4300000, 5600000, 7200000, 9200000, 12000000, 15000000,
			  19000000, 24000000, 30000000, 38000000, 48000000, 60000000, 75000000, 93000000, 116250000};
	static int[] expertiseKills = {50, 100, 250, 500, 1000, 2500, 5500, 10000, 15000};
	
	
	
	public static double xpToSkillLevel(int xp, int limit) {
		for (int i = 0, xpAdded = 0; i < limit + 1; i++) {
			xpAdded += skillXPPerLevel[i];
			if (xp < xpAdded) {
				return (i - 1) + (xp - (xpAdded - skillXPPerLevel[i])) / skillXPPerLevel[i];
			}
		}
		return limit;
	}
	
	public static double totalxpskill(int xp, int limit) {
		for (int i = 0, xpAdded = 0; i < limit + 1; i++) {
			xpAdded += skillXPPerLevel[i];
			if (xp < xpAdded) {
				return skillXPPerLevel[i];
			}
		}
		return limit;
	}
	
	
	public static double xpToDungeonsLevel(double xp) {
		for (int i = 0, xpAdded = 0; i < dungeonsXPPerLevel.length; i++) {
			xpAdded += dungeonsXPPerLevel[i];
			if (xp < xpAdded) {
				double level =  (i - 1) + (xp - (xpAdded - dungeonsXPPerLevel[i])) / dungeonsXPPerLevel[i];
				return (double) Math.round(level * 100) / 100;
			}
		}
		return 50D;
	}
	
	public static int expertiseKillsLeft(int kills) {
		for (int i = 0; i < expertiseKills.length; i++) {
			if (kills < expertiseKills[i]) {
				return expertiseKills[i] - kills;
			}
		}
		return -1;
	}
	
	public static int getPastXpEarned(int currentLevelXp, int limit) {
		if (currentLevelXp == 0) {
			int xpAdded = 0;
			for (int i = 1; i <= limit; i++) {
				xpAdded += skillXPPerLevel[i];
			}
			return xpAdded;
		}
		for (int i = 1, xpAdded = 0; i <= limit; i++) {
			xpAdded += skillXPPerLevel[i - 1];
			if (currentLevelXp == skillXPPerLevel[i]) return xpAdded;
		}
		return 0;
	}


	
	
}
