package vic.chemicalcraft.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import vic.chemicalcraft.CC_Registry;
import vic.chemicalcraft.blocks.tileentity.TileEntityGas;
import vic.chemicalcraft.helper.Position;
import vic.chemicalcraft.substance.EnumPhase;
import vic.chemicalcraft.substance.ISubstanceInterface;
import vic.chemicalcraft.substance.Substance;
import vic.chemicalcraft.substance.SubstanceRegistry;
import vic.chemicalcraft.substance.SubstanceStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Gas extends GenericChemBlock implements ISubstanceInterface{
	
	public Gas(int id, Material material, String texture)
	{
		super(id, material, texture);
		this.setLightOpacity(3);
        this.setBlockUnbreakable();
        this.setBlockBounds(0.0001F, 0.0001F, 0.0001F, 0.9999F, 0.9999F, 0.9999F);
	}
	
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) 
	{		
		TileEntityGas currTE = (TileEntityGas)par1World.getBlockTileEntity(par2, par3, par4);
		if(currTE.strenght < 10) par1World.setBlock(par2, par3, par4, 0);
		ArrayList<Position> pos = getGasBlocks(par2, par3, par4, par1World);
		
		int strenghtToPass = 0;
		if(pos.size() != 0)strenghtToPass = currTE.strenght / pos.size(); 
		
		if(strenghtToPass > 0)
		{
			for(int i= 0; i < pos.size(); i++)
			{
				Position posX = pos.get(i);
				par1World.setBlock((int)posX.x, (int)posX.y, (int)posX.z, CC_Registry.genericGas.blockID);
				par1World.setBlockTileEntity((int)posX.x, (int)posX.y, (int)posX.z, new TileEntityGas(currTE.substanceStack, strenghtToPass));
				par1World.markBlockForUpdate((int)posX.x, (int)posX.y, (int)posX.z);
			}	
		}		
		
		currTE.strenght -= strenghtToPass;
		par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
	}

	@Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);

        if (par1World.getBlockId(par2, par3, par4) == this.blockID)
        {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
        }
    }
	
	ArrayList<Position> getGasBlocks(int par1, int par2, int par3, World par4World)
	{
		ArrayList<Position> out = new ArrayList<Position>(){};
		for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS)
		{
			Position pos = new Position(par1, par2, par3, o);
			pos.plus(1);
			
			int block = par4World.getBlockId((int)pos.x, (int)pos.y, (int)pos.z);
			
			if(block == 0)
			{
				out.add(pos);
			}
		}			
		return out;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
		TileEntityGas TE = (TileEntityGas)par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
		if(TE.substanceStack != null)
		{
			return TE.substanceStack.addColors();			
		}
		return 0;
    }

	@Override
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

	@Override
	public boolean isAirBlock(World world, int x, int y, int z) {
		return true;
	}

	@Override
    public boolean isBlockReplaceable(World world, int x, int y, int z)
    {
    	return true;
    }
    
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
    	int id = par1IBlockAccess.getBlockId(par2, par3, par4);
    	return id == this.blockID ? false : (par5 == 1 ? true : (id == 0 ? true : (Block.blocksList[id].isOpaqueCube() ? false : true)));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        int id = par1IBlockAccess.getBlockId(par2, par3, par4);
        return id == this.blockID ? false : (par5 == 1 ? true : (id == 0 ? true : (Block.blocksList[id].isOpaqueCube() ? false : true)));
    }
    
    @Override
    public boolean isCollidable()
    {
    	return false;
    }
    
    @Override
	public int quantityDropped(Random par1Random)
    {
        return 0;
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {

    	return new TileEntityGas(new SubstanceStack(1, new Substance[]{SubstanceRegistry.Gold}, new int[]{4}, 1, EnumPhase.GASEOUS), 100);
	
    }
    
    @Override
	public boolean hasTileEntity(int metadata) 
    {
		return true;
	}

	@Override
	public SubstanceStack getSubstanceStack(ItemStack stack) 
    {
    	return new SubstanceStack(1, new Substance[]{SubstanceRegistry.Gold}, new int[]{4}, 1, EnumPhase.GASEOUS);		
	}

	@Override
	public int maxSubstanceStackSize() 
	{
		return 5;
	}

	@Override
	public boolean hasStorage(ItemStack stack) 
	{
		return false;
	}

	@Override
	public ItemStack getItemStackBasedOnPhase(EnumPhase phase)
	{
		return null;
	}

}
