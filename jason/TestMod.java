package jason;

import jason.handler.EntityEvents;
import jason.handler.EventsFML;
import jason.handler.GuiHandler;
import jason.lib.Constants;
import jason.network.PacketDispatcher;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Constants.MODID, name = Constants.MODNAME, version = Constants.VERSION)
public class TestMod {
	@Instance(Constants.MODID)
	public static TestMod instance;
	
	@SidedProxy(clientSide = Constants.CLIENT_PROXY, serverSide = Constants.COMMON_PROXY)
	public static CommonProxy proxy;
	
	/**
	 * For adding blocks, items, worldgen and more
	 * 
	 * @param event
	 */
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
		PacketDispatcher.registerPackets();
	}

	/**
	 * For adding TileEntities, events, renderers
	 * 
	 * @param event
	 */
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderers();
		
		MinecraftForge.EVENT_BUS.register(new EntityEvents());
		
		//Registering event listener
		FMLCommonHandler.instance().bus().register(new EventsFML());
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
	}

	/**
	 * For other mods
	 * 
	 * @param event
	 */
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
	
	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		
	}
}
