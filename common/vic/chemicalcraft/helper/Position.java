package vic.chemicalcraft.helper;

import net.minecraftforge.common.ForgeDirection;

public class Position {

	public double x, y, z;
	public ForgeDirection direction;
	
	public Position(double x, double y, double z, ForgeDirection direction) 
	{
		this.x = x; this.y = y; this.z = z;
		this.direction = direction;
	}
	
	public void plus(double step) {
		
		switch (direction) {
		case UP:
			y = y + step;
			break;
		case DOWN:
			y = y - step;
			break;
		case SOUTH:
			z = z + step;
			break;
		case NORTH:
			z = z - step;
			break;
		case EAST:
			x = x + step;
			break;
		case WEST:
			x = x - step;
			break;
		default:
		}
	}
}
