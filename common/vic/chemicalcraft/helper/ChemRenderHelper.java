package vic.chemicalcraft.helper;

import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

public class ChemRenderHelper {
	 
	public static double zLevel = 0.0F;

	/**
     * Draws a rectangle with a vertical gradient between the specified colors. Copied because of missing access to the original one. 
     */
    public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float var7 = (float)(par5 >> 24 & 255) / 255.0F;
        float var8 = (float)(par5 >> 16 & 255) / 255.0F;
        float var9 = (float)(par5 >> 8 & 255) / 255.0F;
        float var10 = (float)(par5 & 255) / 255.0F;
        float var11 = (float)(par6 >> 24 & 255) / 255.0F;
        float var12 = (float)(par6 >> 16 & 255) / 255.0F;
        float var13 = (float)(par6 >> 8 & 255) / 255.0F;
        float var14 = (float)(par6 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator var15 = Tessellator.instance;
        var15.startDrawingQuads();
        var15.setColorRGBA_F(var8, var9, var10, var7);
        var15.addVertex((double)par3, (double)par2, (double)zLevel);
        var15.addVertex((double)par1, (double)par2, (double)zLevel);
        var15.setColorRGBA_F(var12, var13, var14, var11);
        var15.addVertex((double)par1, (double)par4, (double)zLevel);
        var15.addVertex((double)par3, (double)par4, (double)zLevel);
        var15.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}
