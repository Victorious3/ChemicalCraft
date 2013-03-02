package vic.chemicalcraft.blocks;

import vic.chemicalcraft.blocks.tileentity.TileEntityGasBore;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GasBore extends GenericChemBlock {

	public GasBore(int id, int texture, Material material) 
	{
		super(id, texture, Material.anvil);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityGasBore();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		// TODO Auto-generated method stub
		
		if(par1World.isRemote)par5EntityPlayer.sendChatToPlayer(Integer.toString(((TileEntityGasBore)(par1World.getBlockTileEntity(par2, par3, par4))).powerLevel));
		return true;
	}

	@Override
	public boolean hasTileEntity(int metadata) 
	{
		return true;
	}
	
	

}
