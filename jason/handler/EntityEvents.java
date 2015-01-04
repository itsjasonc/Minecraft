package jason.handler;

import jason.entity.TestPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEvents {
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			TestPlayerInfo.get(player).onUpdate();
		}
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		// Can't send update packets from here - use EntityJoinWorldEvent
		TestPlayerInfo.get(event.entityPlayer).copy(TestPlayerInfo.get(event.original));
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (!event.entity.worldObj.isRemote) {
			if (event.entity instanceof EntityPlayer) {
				TestPlayerInfo.get((EntityPlayer) event.entity).onJoinWorld();
			} 
		}
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer && TestPlayerInfo.get((EntityPlayer) event.entity) == null) {
			TestPlayerInfo.register((EntityPlayer) event.entity);
		}
	}
}
