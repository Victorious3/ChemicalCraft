package vic.chemicalcraft.substance;

import java.util.ArrayList;

import vic.chemicalcraft.ChemicalCraft;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class SubstanceRecipes implements IRecipe {

	private ItemStack Substance;
	
	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		
		ArrayList<ItemStack> itemStack = new ArrayList<ItemStack>();
		
		for (int var3 = 0; var3 < var1.getSizeInventory(); ++var3)
        {
			ItemStack var4 = var1.getStackInSlot(var3);
			
			if(var4 != null)
			{
				if(var1.getStackInSlot(var3).itemID == ChemicalCraft.substance.itemID)
				{
					if(SubstanceStack.getFromNBT(var1.getStackInSlot(var3).getTagCompound()) != null)
					{
						itemStack.add(var1.getStackInSlot(var3));
					}
				}
			}
        }			
		
		if(itemStack.size() <= 1) return false;
		
		
		int var5 = itemStack.get(0).getItemDamage();
		SubstanceStack returnStack = SubstanceStack.getFromNBT(itemStack.get(0).getTagCompound());
		returnStack.addStack(returnStack, itemStack.get(0).stackSize - 1);
		
		for(int i = 1; i < itemStack.size(); i++)
		{
			if(itemStack.get(i).getItemDamage() != var5) return false;
			returnStack.addStack(SubstanceStack.getFromNBT(itemStack.get(i).getTagCompound()), itemStack.get(i).stackSize);
		}
		


		Substance = new ItemStack(itemStack.get(0).itemID, 1, var5);
		Substance.setTagCompound(new NBTTagCompound());
		returnStack.writeToNBT(Substance.getTagCompound());
		
		ISubstanceInterface IS = (ISubstanceInterface)Substance.getItem();
		if(IS.maxSubstanceStackSize() < returnStack.amount) return false;
		
		//Minecraft.getMinecraft().thePlayer.inventory.addItemStackToInventory(new ItemStack(ChemicalCraft.substance.shiftedIndex, itemStack.size() - 1, var5 + 3));
		
		return true;
			
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		return Substance.copy();
	}

	@Override
	public int getRecipeSize() {
		return 10;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return Substance;
	}

}
