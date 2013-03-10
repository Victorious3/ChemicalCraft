package vic.chemicalcraft.substance;

import vic.chemicalcraft.proxy.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Substance {

	public String Name = "NaN";
	public int Color = 0xFFF0FFF0;
	public String Symbol = "NaN";
	
	public int meltingPoint = 1000;
	public int boilingPoint = 100;
	
	public int id = 0;
	
	public Substance(String Name, int Color, String Symbol, int meltingPoint, int boilingPoint)
	{
		this.Name = Name;
		this.Color = Color;
		this.Symbol = Symbol;
		
		this.meltingPoint = meltingPoint;
		this.boilingPoint = boilingPoint;
		
		addToList();
	}
	
	/**
	 * Is called when creating a Substance to add it to the Substance Registry.
	 */
	private void addToList()
	{
		SubstanceRegistry.addSubstance(Name, this);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isResearchedByPlayer()
	{
		return ClientProxy.substanceResearchClient.isResearched(this);
	}
	
	public EnumPhase phase(int temperature)
	{
		if(temperature > meltingPoint && temperature < boilingPoint) return EnumPhase.LIQUID;
		if(temperature < meltingPoint) return EnumPhase.SOLID;
		
		return EnumPhase.GASEOUS;
	}
}
