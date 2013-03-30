package vic.chemicalcraft.blocks;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import vic.chemicalcraft.ChemicalCraft;
import vic.chemicalcraft.blocks.tileentity.TileEntityHeatProducer;
import vic.chemicalcraft.helper.Position;
import vic.chemicalcraft.helper.Utils;

public class BlockHeatProducer extends GenericChemBlock {

	public BlockHeatProducer(int id, Material material, String texture) 
	{
		super(id, material, texture);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityHeatProducer();
	}

	@Override
	public boolean hasTileEntity(int metadata) 
	{
		return true;
	}
	
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public int getRenderType()
	{
		return ChemicalCraft.renderID;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess, int par2, int par3, int par4) 
	{
		TileEntityHeatProducer TE = (TileEntityHeatProducer) par1iBlockAccess.getBlockTileEntity(par2, par3, par4);
		
		switch(ForgeDirection.getOrientation(TE.orientation))
		{
		case EAST:
			this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F); break;
		case NORTH:
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F); break;
		case SOUTH:
			this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F); break;
		case WEST:
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F); break;
		default:;		
		}
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack stack) 
	{
		TileEntityHeatProducer TE = (TileEntityHeatProducer) par1World.getBlockTileEntity(par2, par3, par4);
		TE.orientation = (short) Utils.get2DOrientationForPlacing(par5EntityLiving).getOpposite().ordinal();
		if(!isNeighborBlockSolid(par1World, par2, par3, par4, ForgeDirection.getOrientation(((TileEntityHeatProducer)(par1World.getBlockTileEntity(par2, par3, par4))).orientation)))
		{
			par1World.setBlock(par2, par3, par4, 0);
			
			if(par5EntityLiving instanceof EntityPlayer)
			{
				((EntityPlayer)par5EntityLiving).inventory.addItemStackToInventory(new ItemStack(this.blockID, 1, 0));
			}
		}
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5) {
		ForgeDirection dir = ForgeDirection.getOrientation(par5);
        return (dir == NORTH && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH)) ||
               (dir == SOUTH && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH)) ||
               (dir == WEST  && par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST)) ||
               (dir == EAST  && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST));
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) 
	{		
		if(!isNeighborBlockSolid(par1World, par2, par3, par4, ForgeDirection.getOrientation(((TileEntityHeatProducer)(par1World.getBlockTileEntity(par2, par3, par4))).orientation)))
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlock(par2, par3, par4, 0);
			return;
		}
	}

	/**
	 * Returns false if the Block behind the HeatProducer is not solid.
	 * @param par1World
	 * @param par2
	 * @param par3
	 * @param par4
	 * @return boolean
	 */
	private boolean isNeighborBlockSolid(World par1World, int par2, int par3, int par4, ForgeDirection direction)
	{
		Position p = new Position(par2, par3, par4, direction);		
		p.plus(1);
		
		if(par1World.getBlockId((int)p.x, (int)p.y, (int)p.z) == 0)
		{
			return false;
		}
		else if(!Block.blocksList[par1World.getBlockId((int)p.x, (int)p.y, (int)p.z)].isBlockSolidOnSide(par1World, (int)p.x, (int)p.y, (int)p.z, direction.getOpposite()))
		{
			return false;
		}
		return true;
	}
}
