package vic.chemicalcraft.substance;

import java.util.HashMap;

public class SubstanceResearchClient {

	public HashMap research = new HashMap<Substance, Boolean>();
	
	public SubstanceResearchClient(){}
	
	public SubstanceResearchClient(String s)
	{
		SubstanceResearch.players.put(s, this);
	}
	
	public boolean isResearched(Substance s)
	{
		return (Boolean) (research.containsKey(s) ? research.get(s) : false);
	}
	
	public void changeResearched(Substance s, boolean b)
	{
		research.put(s, b);		
	}
}
