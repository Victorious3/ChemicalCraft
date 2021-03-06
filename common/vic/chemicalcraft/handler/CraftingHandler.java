package vic.chemicalcraft.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import vic.chemicalcraft.CC_Registry;
import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler {

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) 
	{		
		if(item.itemID == CC_Registry.substance.itemID)
		{
			int var1 = 0;
			
			for(int i = 0; i < craftMatrix.getSizeInventory(); i++)
			{
				if(craftMatrix.getStackInSlot(i) != null) var1 += craftMatrix.getStackInSlot(i).stackSize;
				craftMatrix.setInventorySlotContents(i, null);					
			}
			
			player.inventory.addItemStackToInventory(new ItemStack(CC_Registry.substance.itemID, var1 - 1, item.getItemDamage() + 3));
		}		
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		// TODO Auto-generated method stub
		
	}

}