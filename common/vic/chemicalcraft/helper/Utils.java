package vic.chemicalcraft.helper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

public class Utils {

	public static ForgeDirection get2DOrientationForPlacing(EntityLiving entity)
	{
		int var1 = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		switch(var1)
		{
		case 0 : return ForgeDirection.NORTH;
		case 1 : return ForgeDirection.EAST;
		case 2 : return ForgeDirection.SOUTH;
		case 3 : return ForgeDirection.WEST;
		}
		
		return ForgeDirection.UNKNOWN;
	}	
}
