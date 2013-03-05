package vic.chemicalcraft.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import vic.chemicalcraft.CC_Registry;
import vic.chemicalcraft.blocks.tileentity.TileEntityGas;
import vic.chemicalcraft.helper.HeatTicker;
import vic.chemicalcraft.substance.EnumPhase;
import vic.chemicalcraft.substance.ISubstanceInterface;
import vic.chemicalcraft.substance.Substance;
import vic.chemicalcraft.substance.SubstanceRegistry;
import vic.chemicalcraft.substance.SubstanceStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GenericSubstance extends GenericChemItem implements ISubstanceInterface {
		
	public int heat = 0;
	public int formula = 1;
	
	public GenericSubstance(int par1) 
	{
		super(par1);
		this.setHasSubtypes(true);
	}	

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconIndex(ItemStack stack, int pass) {						
		
		if(stack.getItemDamage() == 1)return pass > 0 ? 1: 0;
		if(stack.getItemDamage() == 2)return pass > 0 ? 3: 2;
		if(stack.getItemDamage() == 3)return pass > 0 ? 5: 4;
		
		if(stack.getItemDamage() == 4)return 0;
		if(stack.getItemDamage() == 5)return 2;
		if(stack.getItemDamage() == 6)return 4;
		
		return pass > 0 ? 1: 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public SubstanceStack getSubstanceStack(ItemStack stack)
	{
		return SubstanceStack.getFromNBT(stack.getTagCompound());	
	}
	
	@Override
	public int maxSubstanceStackSize() 
	{
		return 5;
	}
	
	@Override
	public boolean hasStorage(ItemStack stack) 
	{
		return true;
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		
		int color = 0xFFF0FFF0; 
		
		if(par1ItemStack.hasTagCompound())
		{
			if(SubstanceStack.getFromNBT(par1ItemStack.getTagCompound()) != null) color = SubstanceStack.getFromNBT(par1ItemStack.getTagCompound()).addColors();
		}	
		
		if(par2 > 0 && par1ItemStack.getItemDamage() < 4){
			return color;
		}
		else
		{
			return 0xFFF0FFF0;
		}
	}
	
	

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		
		SubstanceStack substanceStack = null;
		int temp = 0;
		
		if(par1ItemStack.hasTagCompound())
		{
			substanceStack = SubstanceStack.getFromNBT(par1ItemStack.getTagCompound());			
			if(substanceStack != null) temp = substanceStack.temperature;
		}
		
		//HeatTicker.updateHeat(par1ItemStack, par2EntityPlayer.posX, par2EntityPlayer.posY, par2EntityPlayer.posZ);
		
		if(substanceStack != null)par3List.add("Temperature: " + temp + "\u00B0C");
	}

	@Override
	public String getItemDisplayName(ItemStack par1ItemStack)
	{
		switch(par1ItemStack.getItemDamage())
		{
		case 1: return "Liquid " + super.getItemDisplayName(par1ItemStack);
		case 2: return "Solid " + super.getItemDisplayName(par1ItemStack);
		case 3: return "Gaseous " + super.getItemDisplayName(par1ItemStack);
		case 4: return "Liquid " + super.getItemDisplayName(par1ItemStack) + " (empty)";
		case 5: return "Solid " + super.getItemDisplayName(par1ItemStack) + " (empty)";
		case 6: return "Gaseous " + super.getItemDisplayName(par1ItemStack) + " (empty)";
		}
		
		return super.getItemDisplayName(par1ItemStack);
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {		
		
		if(par1ItemStack.getItemDamage() == 3)
		{
			int var11 = par3World.getBlockId(par4, par5, par6);
			int spawnID = CC_Registry.genericGas.blockID;

	        if (var11 == Block.snow.blockID)
	        {
	            par7 = 1;
	        }
	        else if (var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID)
	        {
	            if (par7 == 0)
	            {
	                --par5;
	            }

	            if (par7 == 1)
	            {
	                ++par5;
	            }

	            if (par7 == 2)
	            {
	                --par6;
	            }

	            if (par7 == 3)
	            {
	                ++par6;
	            }

	            if (par7 == 4)
	            {
	                --par4;
	            }

	            if (par7 == 5)
	            {
	                ++par4;
	            }
	        }

	        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
	        {
	            return false;
	        }
	        else if (par1ItemStack.stackSize == 0)
	        {
	            return false;
	        }
	        else
	        {
	            if (par3World.canPlaceEntityOnSide(spawnID, par4, par5, par6, false, par7, (Entity)null))
	            {
	                Block var12 = Block.blocksList[spawnID];

	                if (par3World.setBlock(par4, par5, par6, spawnID))
	                {
	                    if (par3World.getBlockId(par4, par5, par6) == spawnID)
	                    {
	                        Block.blocksList[spawnID].onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer);
	                        par3World.setBlockTileEntity(par4, par5, par6, new TileEntityGas(SubstanceStack.getFromNBT(par1ItemStack.getTagCompound()), 100));
	                    }

	                    par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
	                    
	                    SubstanceStack currSubstanceStack = SubstanceStack.getFromNBT(par1ItemStack.getTagCompound());
	                    
	                    if(!par2EntityPlayer.capabilities.isCreativeMode)
	                    {
	                    	if(par1ItemStack.stackSize > 1)
		                    {
		                    	ItemStack stack2 = par1ItemStack.copy();
		                    	stack2.stackSize = par1ItemStack.stackSize - 1;
		                    	par1ItemStack.stackSize = 1; 
		                    	--currSubstanceStack.amount;
		                    	currSubstanceStack.writeToNBT(par1ItemStack.getTagCompound());
		                    	
		                    	par2EntityPlayer.inventory.addItemStackToInventory(stack2);	                    	
		                    }                 	                    
		                    else if(currSubstanceStack.amount == 1)
		                    {                    	
		                    	par2EntityPlayer.inventory.addItemStackToInventory(new ItemStack(CC_Registry.substance.itemID, 1, par1ItemStack.getItemDamage() + 3));
		                    	--par1ItemStack.stackSize;	
		                    }
		                    else
		                    {
		                    	--currSubstanceStack.amount;
		                    	currSubstanceStack.writeToNBT(par1ItemStack.getTagCompound());
		                    }
	                    }	                    
	                }
	            }
	            return true;
	        }
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		
		Substance[] substances = SubstanceRegistry.getAllSubstances();
		
		ItemStack stack = new ItemStack(par1, 1, 4); par3List.add(stack);
		stack = new ItemStack(par1, 1, 5); par3List.add(stack);
		stack = new ItemStack(par1, 1, 6); par3List.add(stack);
		
		
		for(int i = 0; i < substances.length; i++)
		{
			stack = new ItemStack(par1, 1, 1);

			SubstanceStack subStack = new SubstanceStack(1, new Substance[]{substances[i]}, new int[]{1}, 0, EnumPhase.LIQUID);		
			stack.setTagCompound(new NBTTagCompound());
			subStack.writeToNBT(stack.getTagCompound());
			
			par3List.add(stack);
		}
		
		for(int i = 0; i < substances.length; i++)
		{
			stack = new ItemStack(par1, 1, 2);

			SubstanceStack subStack = new SubstanceStack(1, new Substance[]{substances[i]}, new int[]{1}, -500, EnumPhase.LIQUID);		
			stack.setTagCompound(new NBTTagCompound());
			subStack.writeToNBT(stack.getTagCompound());

			par3List.add(stack);
		}
		
		for(int i = 0; i < substances.length; i++)
		{
			stack = new ItemStack(par1, 1, 3);

			SubstanceStack subStack = new SubstanceStack(1, new Substance[]{substances[i]}, new int[]{1}, 500, EnumPhase.GASEOUS);		
			stack.setTagCompound(new NBTTagCompound());
			subStack.writeToNBT(stack.getTagCompound());
			
			par3List.add(stack);
		}
	
	}

	private int delay = 10;
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{	
		if(delay >= 0)delay--;
		if(delay == -1)
		{
			HeatTicker.updateHeat(par1ItemStack, par3Entity.posX, par3Entity.posY, par3Entity.posZ, par2World, par3Entity);
			delay = 10;				
		}
	}

	@Override
	public ItemStack getItemStackBasedOnPhase(EnumPhase phase) {
	
		switch(phase)
		{
		case GASEOUS: return new ItemStack(this.itemID, 1, 3); 
		case SOLID: return new ItemStack(this.itemID, 1, 2); 
		case LIQUID: return new ItemStack(this.itemID, 1, 1); 
		default: return null;		
		}
	}
}
