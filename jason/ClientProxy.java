package jason;

import jason.client.KeyHandler;
import jason.lib.Constants;
import jason.network.packet.client.UnpressKeyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {
	private Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void preInit() {
		super.preInit();

		FMLCommonHandler.instance().bus().register(new KeyHandler());
		UnpressKeyPacket.init();
	}
	
	@Override
	public void registerRenderers() {
	}

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return (ctx.side.isClient() ? mc.thePlayer : super.getPlayerEntity(ctx));
	}
}
