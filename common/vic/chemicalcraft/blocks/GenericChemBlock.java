package vic.chemicalcraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GenericChemBlock extends BlockContainer
{        
    private String iconName;

    public GenericChemBlock(int id, Material material, String icon) 
    {
        super(id, material);
        iconName = icon;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) 
	{
        blockIcon = par1IconRegister.registerIcon("vic/chemicalcraft:" + iconName);
    }

    @Override
	public TileEntity createNewTileEntity(World var1) {
		return null;
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return false;
	}
}
