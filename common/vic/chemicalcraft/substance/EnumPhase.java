package vic.chemicalcraft.substance;

public enum EnumPhase {

	SOLID(0), LIQUID(1), GASEOUS(2);
	
	int phase = 0;
	
	EnumPhase(int p)
	{
		phase = p;
	}
	
	public int getColor()
	{
		switch(phase)
		{
		case 0: return 0xFF000000;
		case 1: return 0xFF4F7DFF;
		case 2: return 0xFFFF425B;
		}
		
		return 0xFF000000;
	}		
}
