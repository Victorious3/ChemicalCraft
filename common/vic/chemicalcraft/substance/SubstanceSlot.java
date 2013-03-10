package vic.chemicalcraft.substance;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubstanceSlot 
{
	public Substance substance;
	public int x;
	public int y;
	public int width = 24;
	public int height = 36;
	public boolean isResearched;
		
	public SubstanceSlot(Substance substance, int x, int y)
	{
		this.substance = substance;
		this.x = x;
		this.y = y;
		if(substance != null)this.isResearched = substance.isResearchedByPlayer();
		else this.isResearched = false;
	}
	
	@SideOnly(Side.CLIENT)
	public void render(FontRenderer fontRenderer, int temperature)
	{
		if(this.substance == null)return;
		
		int phaseColor = substance.phase(temperature).getColor();
		
		String symbol = substance.Symbol;
		int color = substance.Color;
		
		if(!substance.isResearchedByPlayer() && !Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
		{
			symbol = "??";
			color = 0xFF000000;
			phaseColor = 0xFFA8A8A8;
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiScreen.drawRect(x, y, x+width, y+height, phaseColor);
		GuiScreen.drawRect(x, y, x+width, y+1, 0xff8B8B8B);
		GuiScreen.drawRect(x, y+height-1, x+width, y+height, 0xff8B8B8B);
		GuiScreen.drawRect(x, y, x+1, y+height, 0xff8B8B8B);
		GuiScreen.drawRect(x+width-1, y, x+width, y+height, 0xff8B8B8B);
		
		fontRenderer.drawStringWithShadow(symbol, x+3, y+3, color);
		fontRenderer.drawString(Integer.toString(substance.id), x+3, y+26, 0xFFFFFF);
	}
}
