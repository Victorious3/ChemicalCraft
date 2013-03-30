package vic.chemicalcraft.items;

public class HeatAbsorber extends GenericChemItem {

	int absorbation = 0;
	
	public HeatAbsorber(int par1, int procent, String texture) 
	{
		super(par1, texture);
		this.absorbation = procent;
	}

	public int absorbs()
	{
		return absorbation;
	}
}
