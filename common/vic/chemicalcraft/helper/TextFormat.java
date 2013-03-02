package vic.chemicalcraft.helper;

public class TextFormat {

	public static String getLowerValue(int value) 
	{
		String s1 = Integer.toString(value);
		String out = "";
		
		for(int i = 0; i < s1.length(); i++)
		{
			int i1 = Integer.parseInt(String.valueOf(s1.charAt(i)));
			i1 = i1 + 8320;
			out = out + (char)i1;	
		}
		
		return out;
	}
	
}
