package jason.client.gui;

import jason.client.KeyHandler;
import jason.entity.TestPlayerInfo;
import jason.inventory.ContainerTest;
import jason.lib.Constants;
import jason.network.PacketDispatcher;
import jason.network.packet.bidirectional.MyMessage;
import jason.network.packet.bidirectional.PlaySoundPacket;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class GuiTest extends GuiContainer {

	private final EntityPlayer player;
	
	public GuiTest(EntityPlayer player) {
		super(new ContainerTest(player));
		this.player = player;
		PacketDispatcher.sendToAllAround(new PlaySoundPacket("random.bow", 1, 1, player), player, 65.0D);
		//PacketDispatcher.sendToAllAround(new MyMessage(player.getDisplayName() + " sent this message."), player, 65.0D);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,
			int p_146976_2_, int p_146976_3_) {
		// TODO Auto-generated method stub
		
	}

}
