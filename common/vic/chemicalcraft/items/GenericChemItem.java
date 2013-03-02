package vic.chemicalcraft.items;

import vic.chemicalcraft.proxy.CommonProxy;
import net.minecraft.item.Item;

public class GenericChemItem extends Item{
	
	public GenericChemItem(int par1) {
		super(par1);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
	}
}
