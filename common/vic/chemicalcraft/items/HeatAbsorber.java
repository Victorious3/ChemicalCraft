package vic.chemicalcraft.items;

public class HeatAbsorber extends GenericChemItem {

	int absorbation = 0;
	
	public HeatAbsorber(int par1, int procent) {
		super(par1);
		this.absorbation = procent;
	}

	public int absorbs()
	{
		return absorbation;
	}
}
