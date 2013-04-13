package vic.chemicalcraft.blocks;

import vic.chemicalcraft.ChemicalCraft;
import vic.chemicalcraft.blocks.tileentity.TileEntityTemperedFabricator;
import vic.chemicalcraft.helper.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TemperedFabricator extends GenericChemBlock 
{
    public TemperedFabricator(int id, Material material, String icon) 
    {
        super(id, material, icon);
    }
    
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack stack) 
    {
        TileEntityTemperedFabricator TE = (TileEntityTemperedFabricator) par1World.getBlockTileEntity(par2, par3, par4);
        TE.orientation = (short) Utils.get2DOrientationForPlacing(par5EntityLiving).ordinal();  
    }

    @Override
    public TileEntity createNewTileEntity(World var1) 
    {
        return new TileEntityTemperedFabricator();
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) 
    {       
        return false;
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
