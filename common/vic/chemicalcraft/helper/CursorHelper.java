package vic.chemicalcraft.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.inventory.Slot;

import org.lwjgl.input.Mouse;

public class CursorHelper {

	public static int getX()
	{
		return cursorPosition()[0];
	}
	
	public static int getY()
	{
		return cursorPosition()[1];
	}
	
	private static int[] cursorPosition()
	{
		Minecraft mc = Minecraft.getMinecraft();
		
		ScaledResolution var13 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int var14 = var13.getScaledWidth();
        int var15 = var13.getScaledHeight();
        int var16 = Mouse.getX() * var14 / mc.displayWidth;
        int var17 = var15 - Mouse.getY() * var15 / mc.displayHeight - 1;
        
        return new int[]{var16, var17};
	}
	
	/**
     * Returns if the passed mouse position is over the specified slot.
	 * @param y 
	 * @param x 
     */
    public static boolean isMouseOverSlot(Slot par1Slot, int par2, int par3, int x, int y)
    {
    	int var1 = par1Slot.xDisplayPosition + x + 16;
    	int var2 = par1Slot.yDisplayPosition + y + 16;
    	
    	if(par2 <= var1 && par2 >= var1 - 17 && par3 <= var2 && par3 >= var2 - 17) return true;
    	return false;
    }
}
