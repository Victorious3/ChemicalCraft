package vic.chemicalcraft.substance;

import vic.chemicalcraft.ChemicalCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SubstanceTab extends CreativeTabs{

	public SubstanceTab(String label) {
		super(label);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return "Chemicals";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return ChemicalCraft.substance;
	}
	
	

}
