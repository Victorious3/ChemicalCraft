package vic.chemicalcraft.helper;

import java.util.HashMap;
import net.minecraft.world.World;
import net.minecraft.world.biome.*;

public class BiomeTemperature {
	
	static int standardTemp = 18;
	
	public static BiomeTemperature instance = new BiomeTemperature();
	
	public HashMap<BiomeGenBase, Integer> temperatures = new HashMap<BiomeGenBase, Integer>();
	
	public BiomeTemperature()
	{
		registerBiomeTemperature(BiomeGenBase.beach, 20);
		registerBiomeTemperature(BiomeGenBase.desert, 40);
		registerBiomeTemperature(BiomeGenBase.forest, 18);
		registerBiomeTemperature(BiomeGenBase.hell, 45);
		registerBiomeTemperature(BiomeGenBase.extremeHills, 12);
		registerBiomeTemperature(BiomeGenBase.jungle, 30);
		registerBiomeTemperature(BiomeGenBase.mushroomIsland, 17);
		registerBiomeTemperature(BiomeGenBase.ocean, 14);
		registerBiomeTemperature(BiomeGenBase.plains, 18);
		registerBiomeTemperature(BiomeGenBase.river, 17);
		registerBiomeTemperature(BiomeGenBase.iceMountains, -2);
		registerBiomeTemperature(BiomeGenBase.swampland, 15);
		registerBiomeTemperature(BiomeGenBase.taiga, 0);
	}
	
	public int currTemperature(double posX, double posY, double posZ, World world)
	{
		BiomeGenBase biome = world.getBiomeGenForCoords((int)posX, (int)posZ);
		
		if(temperatures.containsKey(biome))
		{
			return (Integer)temperatures.get(biome);
		}
		
		return standardTemp;
	}
	
	/**
	 * Needed to register the temperature of a Biome
	 */
	public void registerBiomeTemperature(BiomeGenBase gen, int temperature)
	{
		temperatures.put(gen, temperature);
	}
}
