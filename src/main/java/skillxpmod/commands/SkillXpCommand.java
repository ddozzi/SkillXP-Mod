package skillxpmod.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Arrays;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import skillxpmod.utils.APIUtil;

public class SkillXpCommand implements ICommand {

	@Override
	public int compareTo(ICommand o) {
		
		return 0;
	}

	@Override
	public String getCommandName() {
		
		return "calc";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "/calc <skill name> <level>";
	}

	@Override
	public List<String> getCommandAliases() {
		
		return Lists.newArrayList("cal");
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerSP player = (EntityPlayerSP) sender;
		if (args.length == 0) {
			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Command not found, please do /calc help for a list of commands."));
		}
		String subcommand = args[0].toLowerCase(Locale.ENGLISH);
		switch (subcommand) {
        case "setkey":
            if (args.length == 1) {
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Please provide your Hypixel API key!"));
                return;
            }
            new Thread(() -> {
                String apiKey = args[1];
                if (APIUtil.getJSONResponse("https://api.hypixel.net/key?key=" + apiKey).get("success").getAsBoolean()) {
                	
                	// --- start save api --- 
                	Properties prop = new Properties();

                	try {
                		prop.setProperty("key", apiKey);
                		
                		prop.store(new FileOutputStream("config/skillxpmod.cfg"), null);

                	} catch (IOException ex) {
                		ex.printStackTrace();
                    }
                	// --- end save api ---
                	
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Updated your API key to " + apiKey));
               
                } else {
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Please provide a valid Hypixel API key!"));
                }
            }).start();
            break;
		case "help":
        if (args.length == 1) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE +
            		"§7§m------------§7[§c§l SkillXP Commands §7]§7§m------------" + "\n" +
                    "§c● /calc skill <skill> <skill level> §7- Shows how much xp until level" + "\n" +
                    "§c● /calc setkey <api key> §7- Set the API key (required)" + "\n" + 
                    "§c● §7More coming soon!" + "\n" +
                    "§7§m--------------------------------------------------------"));
            return;
        } 
		case "skill":
			List<String> availableskills = new ArrayList<String>();
			availableskills.addAll(Arrays.asList("combat", "mining", "alchemy", "farming", "taming", "enchanting", "fishing", "foraging", "carpentry"));
			if (args.length == 1) {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Please enter the skill you want to check!"));
				return;
			}

			if (availableskills.contains(args[1])) {
				
			
			new Thread(() -> {
				try {
					//player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "player thing: "+ player));
   					NumberFormat myFormat = NumberFormat.getInstance();
			        myFormat.setGroupingUsed(true);
			        
					String uuid = APIUtil.getUUID(player.getName());
					//player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "player thing: " + player.getName()));
					Properties prop = new Properties();
	                String fileName = "config/skillxpmod.cfg";
	                InputStream is = null;
	                try {
	                    is = new FileInputStream(fileName);
	                } catch (FileNotFoundException ex) {
	                	player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "API KEY config file was not found! Try ./calc setkey <your key> if the problem persists please contact ddozzi on discord!"));
	                }
	                try {
	                    prop.load(is);
	                } catch (IOException ex) {
	                	player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "An IO Exception has occured! Please contact ddozzi on discord!"));
	                }
	                
	                String profileID = APIUtil.getLatestProfileID(uuid, prop.getProperty("key"));
	                
	                String JsonTotalXP = ((JsonObject) ((JsonObject) ((JsonObject) APIUtil.getJSONResponse("https://api.hypixel.net/skyblock/profile?key=" + prop.getProperty("key")+"&profile=" + profileID).get("profile")).get("members")).get(uuid)).get("experience_skill_"+args[1]).getAsString();
	        		int totalXP = new BigDecimal(JsonTotalXP).intValue();
	                
	        		List<Integer> skillxplist = new ArrayList<Integer>();
	        		skillxplist.addAll(Arrays.asList(5, 175, 375, 675, 1175, 1925, 2925, 4425, 6425, 9925, 14925, 22925, 32425, 47425, 67425, 97425, 147425, 222425, 322425, 522425, 822425, 1222425, 1722425, 2322425, 3022425, 3822425, 4722425, 5722425, 6822425, 8022425, 9322425, 10722425, 12222425, 13822425, 15522425, 17322425, 19222425, 21222425, 23322425, 25522425, 27822425, 30222425, 32722425, 35322425, 38072425, 40972425, 44072425, 47472245, 51172245, 55172245, 59472425, 64072425, 68972425, 74172425, 79672425, 85472425, 91572425, 97972425, 104672425, 111672425, 1000000000));
	                
	        		List<Integer> skilllevelupxplist = new ArrayList<Integer>();
	        		skilllevelupxplist.addAll(Arrays.asList(50, 125, 200, 300, 500, 750, 1000, 1500, 2000, 3500, 5000, 7500, 10000, 15000, 20000, 30000, 50000, 75000, 100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000, 1100000, 1200000, 1300000, 1400000, 1500000, 1600000, 1700000, 1800000, 1900000, 2000000, 2100000, 2200000, 2300000, 2400000, 2500000, 2600000, 2750000, 2900000, 3100000, 3400000, 3700000, 4000000, 4300000, 4600000, 4900000, 5200000, 5500000, 5800000, 6100000, 6400000, 6700000, 7000000, 1000000000));
	        		
	        		int mininglvl = 0;
	        		int currentxpgain = 0;
	        		int totalcurrentxp = 0;
	        		int neededxp = 0;
	        		int totalneededxp = 0;
		        	int totalmininglvl = 0;	
	        		
	        		try {
		        		for (int i = 1; i < 61; i++) {
		        		     if (totalXP > skillxplist.get(i)) {
		        		    	 //player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[DEBUG] Mining level higher than: "+ i)
		        		    	 
		        		     } else if (totalXP < skillxplist.get(i)) {
		        		    	 mininglvl = i;
		        		    	 currentxpgain = (totalXP - skillxplist.get(i - 1));
		        		    	 totalcurrentxp = skilllevelupxplist.get(i);
		        		    	 neededxp = (totalcurrentxp - currentxpgain);
		        		    	 totalmininglvl = i;
		        		    	 
		        		    	 if (Integer.parseInt(args[2]) > 60) {
		        		    		 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6You cannot go above level 60!"));
		        		    		 return;
		        		    	 }
		        		    	 
		        		    	 for (int e = totalmininglvl; e < (Integer.parseInt(args[2])); e++) {

		        		    		 totalneededxp = totalneededxp + skilllevelupxplist.get(e);
		        		    		 //player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Needed XP Until Level "+  totalneededxp + " XP"));
		        		    	 }
		        		    	 
		        		    	 if (Integer.parseInt(args[2]) < mininglvl) {
		        		    		 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6You cannot go below your current skill level!"));
		        		    		 return;
		        		    	 }
		        		    	 
		        		    	 if (totalneededxp == 0) {
		        		    		 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Skill Level: §e§lMAXED"));
			        		    	 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Skill Progress: §e§lMAXED"));
			        		    	 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Needed XP Until Level: §e§lMAXED"));
			        		    	 
			        		    	 break;
		        		    	 }
	
		        		    	 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Skill Level: "+ mininglvl));
		        		    	 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Skill Progress: "+ myFormat.format(currentxpgain) + " / " + myFormat.format(totalneededxp) + " XP" ));
		        		    	 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Needed XP Until Level "+ args[2] + ": " + myFormat.format(totalneededxp - currentxpgain) + " XP"));
		        		    	 
		        		    	 
		        		    	 //"| Needed XP: " +awd myFormat.format(neededxp)
		        		    	 
		        		    	 break;
		        		     } 
		        		     }
		        		    
		        		    
	        		} catch (ArrayIndexOutOfBoundsException e) {
	        			for (int i = 1; i < 61; i++) {
		        		     if (totalXP > skillxplist.get(i)) {
		        		    	 //player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[DEBUG] Mining level higher than: "+ i)
		        		    	 
		        		     } else if (totalXP < skillxplist.get(i)) {
		        		    	 mininglvl = i;
		        		    	 currentxpgain = (totalXP - skillxplist.get(i - 1));
		        		    	 totalcurrentxp = skilllevelupxplist.get(i);
		        		    	 neededxp = (totalcurrentxp - currentxpgain);
		        		    	 
		        		    	 if (totalcurrentxp == 1000000000) {
		        		    		 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Skill Level: §e§lMAXED"));
			        		    	 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Skill Progress: §e§lMAXED"));
			        		    	 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Needed XP Until Level: §e§lMAXED"));
			        		    	 
			        		    	 break;
		        		    	 }
		        		    	 
		        		    	 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Skill Level: "+ mininglvl));
		        		    	 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Skill Progress: "+ myFormat.format(currentxpgain) + " / " + myFormat.format(totalcurrentxp) + " XP" ));
		        		    	 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§f[§c§lSkill XP§f§r] §6Needed XP: "+ myFormat.format(neededxp) + " XP"));
		        		    	 //"| Needed XP: " + myFormat.format(neededxp)
		        		    	 
		        		    	 break;
	        		}}}
	                
				} catch (NullPointerException e) {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[ERROR]: An error occured! Please try the following methods: "));
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[ERROR]: Turn on Skill API "));
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[ERROR]: Make sure your API key is valid - you might want to regenerate it using ./api new"));
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[ERROR]: If none work, contact ddozzi with steps on how to recreate the error."));
				}
			}).start();
		} else {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[ERROR]: Currently only these skills are supported: combat, mining, alchemy, farming, taming, enchanting, fishing, foraging, carpentry"));
		}
       default:
}};


	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		
		return false;
	}

}
