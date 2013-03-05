package vic.chemicalcraft.substance;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import vic.chemicalcraft.CC_Registry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		return CC_Registry.substance;
	}
	
	

}
