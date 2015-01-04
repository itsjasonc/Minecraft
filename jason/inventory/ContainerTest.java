package jason.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerTest extends Container {
	public ContainerTest(EntityPlayer player) {
		
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
