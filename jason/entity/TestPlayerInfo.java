package jason.entity;

import jason.network.PacketDispatcher;
import jason.network.packet.client.SyncPlayerPropsMessage;

import java.util.EnumMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;


public class TestPlayerInfo implements IExtendedEntityProperties
{
	private static final String EXT_PROP_NAME = "TestPlayerInfo";

	private final EntityPlayer player;

	public TestPlayerInfo(EntityPlayer player) {
		this.player = player;
	}

	@Override
	public void init(Entity entity, World world) {}
	
	
	public void onAttackBlocked(ItemStack shield, float damage) {
	}
	
	public void onUpdate() {

	}

	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(EXT_PROP_NAME, new TestPlayerInfo(player));
	}
	
	public static final TestPlayerInfo get(EntityPlayer player) {
		return (TestPlayerInfo) player.getExtendedProperties(EXT_PROP_NAME);
	}

	public void onPlayerLoggedIn() {
	}

	public void onJoinWorld() {
	}

	public void copy(TestPlayerInfo info) {
		NBTTagCompound compound = new NBTTagCompound();
		info.saveNBTData(compound);
		this.loadNBTData(compound);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
	}
}