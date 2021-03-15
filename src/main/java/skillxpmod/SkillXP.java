package skillxpmod;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import skillxpmod.commands.SkillXpCommandv2;
import skillxpmod.proxy.CommonProxy;

@Mod(modid = "skillxpmod")

public class SkillXP {
	
	@Instance
    public static SkillXP instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public static void preInit(FMLPostInitializationEvent e) {

    }
    
    @EventHandler
    public static void init(FMLInitializationEvent e) {
        ClientCommandHandler.instance.registerCommand(new SkillXpCommandv2());
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent e) {

    }
    
}
