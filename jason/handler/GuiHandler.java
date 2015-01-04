package jason.handler;

import jason.client.gui.GuiTest;
import jason.entity.TestPlayerInfo;
import jason.inventory.ContainerTest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	public static final int GUI_TEST = 0;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch(id) {
		case GUI_TEST:
			return new ContainerTest(player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch(id) {
		case GUI_TEST:
			return new GuiTest(player);//, FalloutPlayerInfo.get(player).getPlayerStats());
		}
		return null;
	}
}
