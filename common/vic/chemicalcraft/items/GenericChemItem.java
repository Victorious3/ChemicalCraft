package vic.chemicalcraft.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class GenericChemItem extends Item
{
	
    private String iconName;
	
    public GenericChemItem(int par1, String texture) 
    {
		super(par1);
		iconName = texture;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister) 
    {
        this.iconIndex = par1IconRegister.registerIcon("vic/chemicalcraft:" + iconName);
    }
}
