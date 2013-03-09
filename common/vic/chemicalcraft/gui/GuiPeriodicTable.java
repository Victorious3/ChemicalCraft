package vic.chemicalcraft.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import vic.chemicalcraft.proxy.CommonProxy;
import vic.chemicalcraft.substance.Substance;
import vic.chemicalcraft.substance.SubstanceRegistry;

public class GuiPeriodicTable extends GuiScreen 
{	
	int guiLeft = 0;
	int guiTop = 0;
	int xSize = 0;
	int ySize = 0;
	
	int scrollY = 0;
	boolean isScrolling = false;
	
	Substance[] substances = SubstanceRegistry.getAllSubstances();
	
	@Override
	public void drawScreen(int par1, int par2, float par3) 
	{
		drawDefaultBackground();
		
		boolean var4 = Mouse.isButtonDown(0);		
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture(CommonProxy.GUI_PERIODIC));
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		fontRenderer.drawString("Periodic Table", guiLeft + 7, guiTop + 7, 0);		
//		fontRenderer.drawString("Solid", guiLeft + 15, guiTop + 22, 0xFF3A3A);
//		fontRenderer.drawString("Liquid", guiLeft + 90, guiTop + 22, 0x4762FF);
//		fontRenderer.drawString("Gaseous", guiLeft + 165, guiTop + 22, 0x66B743);
		
		drawSubstances();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture(CommonProxy.GUI_PERIODIC));
		drawTexturedModalRect(guiLeft + 236, guiTop + scrollY, 0, 169, 12, 15);
				
		if(var4)
		{
			if((par1 > guiLeft + 236 && par1 <= guiLeft + 248) && (par2 > guiTop + scrollY && par2 <= guiTop + scrollY + 15))
			{
				isScrolling = true;
			}			
		}
		else isScrolling = false;
		
		if(isScrolling)
		{
			scrollY = par2 - guiTop - 7;
			if(scrollY > 146) scrollY = 146;
			if(scrollY < 18) scrollY = 18;
		}
		
		super.drawScreen(par1, par2, par3);
	}

	private void drawSubstances() 
	{
		
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
			
		super.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		// TODO Auto-generated method stub
		super.actionPerformed(par1GuiButton);
	}

	@Override
	public void initGui() {
		
		xSize = 256;
		ySize = 169;
		guiLeft = (this.width - xSize) /2;
		guiTop = (this.height - ySize) /2;
		scrollY = 18;
		
		this.controlList.clear();
		
		super.initGui();
	}

	@Override
	public void updateScreen() {
		// TODO Auto-generated method stub
		super.updateScreen();
	}
}
