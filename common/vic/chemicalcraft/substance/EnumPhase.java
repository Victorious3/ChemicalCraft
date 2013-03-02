package vic.chemicalcraft.substance;

public enum EnumPhase {

	SOLID(0), LIQUID(1), GASEOUS(2);
	
	int phase = 0;
	
	EnumPhase(int p)
	{
		phase = p;
	}
		
}
