package vic.chemicalcraft.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vic.chemicalcraft.blocks.tileentity.TileEntityHeatAcceptor;

public class BlockHeatAcceptor extends GenericChemBlock
{

	public BlockHeatAcceptor(int id, Material material, String texture)
	{
		super(id, material, texture);
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) 
	{	
		if(!par1World.isRemote)par5EntityPlayer.sendChatToPlayer(Integer.toString(((TileEntityHeatAcceptor)(par1World.getBlockTileEntity(par2, par3, par4))).heat));
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityHeatAcceptor();
	}

	@Override
	public boolean hasTileEntity(int metadata) 
	{
		return true;
	}
}
