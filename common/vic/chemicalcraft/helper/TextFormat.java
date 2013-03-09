package vic.chemicalcraft.helper;

public class TextFormat {

	public static String BLACK = "\u00A70";
	public static String DARK_BLUE = "\u00A71";
	public static String DARK_GREEN = "\u00A72";
	public static String DARK_AQUA = "\u00A73";
	public static String DARK_RED = "\u00A74";
	public static String PURPLE = "\u00A75";
	public static String GOLD = "\u00A76";
	public static String GRAY = "\u00A77";
	public static String DARK_GRAY = "\u00A78";
	public static String BLUE = "\u00A79";
	public static String GREEN = "\u00A7a";
	public static String AQUA = "\u00A7b";
	public static String RED = "\u00A7c";
	public static String LIGHT_PURPLE = "\u00A7d";
	public static String YELLOW = "\u00A7e";
	public static String WHITE = "\u00A7f";	
	
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
