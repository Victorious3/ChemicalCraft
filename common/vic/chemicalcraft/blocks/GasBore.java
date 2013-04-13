package vic.chemicalcraft.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vic.chemicalcraft.ChemicalCraft;
import vic.chemicalcraft.blocks.tileentity.TileEntityElectric;
import vic.chemicalcraft.blocks.tileentity.TileEntityGasBore;
import vic.chemicalcraft.helper.Utils;

public class GasBore extends GenericChemBlock {

	public GasBore(int id, Material material, String texture) 
	{
		super(id, Material.anvil, texture);
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack stack) 
	{
		TileEntityElectric TE = (TileEntityElectric) par1World.getBlockTileEntity(par2, par3, par4);
		TE.orientation = (short) Utils.get2DOrientationForPlacing(par5EntityLiving).ordinal();	
	}

	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityGasBore();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) 
	{		
		if(!par1World.isRemote)par5EntityPlayer.sendChatToPlayer(Integer.toString(((TileEntityGasBore)(par1World.getBlockTileEntity(par2, par3, par4))).powerLevel));
		return true;
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
}
