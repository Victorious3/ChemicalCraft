package vic.chemicalcraft.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import vic.chemicalcraft.proxy.CommonProxy;
import vic.chemicalcraft.substance.Substance;
import vic.chemicalcraft.substance.SubstanceRegistry;
import vic.chemicalcraft.substance.SubstanceSlot;

public class GuiPeriodicTable extends GuiScreen 
{	
	int guiLeft = 0;
	int guiTop = 0;
	int xSize = 0;
	int ySize = 0;
	
	int scrollY = 0;
	boolean isScrolling = false;
	int temperature = 20;
	
	Substance[] substances = SubstanceRegistry.getAllSubstances();
	SubstanceSlot[] substanceSlots = new SubstanceSlot[36];
	
	private GuiTextField searchField;
	
	@Override
	public void drawScreen(int par1, int par2, float par3) 
	{
		drawDefaultBackground();
		
		boolean var4 = Mouse.isButtonDown(0);		
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture(CommonProxy.GUI_PERIODIC));
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		fontRenderer.drawString("Periodic Table", guiLeft + 7, guiTop + 7, 0);
		fontRenderer.drawString("temp.:", guiLeft + 160, guiTop + 7, 0);	
		
		searchField.drawTextBox();
		drawSubstances();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture(CommonProxy.GUI_PERIODIC));
		drawTexturedModalRect(guiLeft + 229, guiTop + scrollY, 0, 170, 12, 15);
				
		if(var4)
		{
			if((par1 > guiLeft + 229 && par1 <= guiLeft + 241) && (par2 > guiTop + scrollY && par2 <= guiTop + scrollY + 15))
			{
				isScrolling = true;
			}			
		}
		else isScrolling = false;
		
		if(isScrolling)
		{
			scrollY = par2 - guiTop - 7;
			if(scrollY > 147) scrollY = 147;
			if(scrollY < 18) scrollY = 18;
		}
		
		super.drawScreen(par1, par2, par3);
	}

	private void drawSubstances() 
	{
		for(SubstanceSlot slot : substanceSlots)
		{
			slot.render(fontRenderer, temperature);
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) 
	{			
		super.mouseClicked(par1, par2, par3);				
		searchField.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) 
	{
		super.actionPerformed(par1GuiButton);
	}

	@Override
	public void initGui() {
		
		xSize = 249;
		ySize = 170;
		guiLeft = (this.width - xSize) /2;
		guiTop = (this.height - ySize) /2;
		scrollY = 18;
		
		int counter = 0;
		
		for(int i = 1; i <= 4; i++)
		{
			for(int j = 1; j <= 9; j++)
			{				
				Substance s = null;
				if(counter < substances.length)s = substances[counter];
				
				substanceSlots[counter] = new SubstanceSlot(s, (j-1)*24 + 8 + guiLeft, (i-1)*36 + 18 + guiTop);
				counter++;
			}
		}
		
		this.controlList.clear();
		
		fontRenderer.FONT_HEIGHT = 6;
		
		searchField = new GuiTextField(fontRenderer, this.guiLeft + 190, this.guiTop + 5, 46, 10);
        searchField.setMaxStringLength(6);
        searchField.setEnableBackgroundDrawing(true);
        searchField.setVisible(true);
        searchField.setTextColor(16777215);	
        searchField.setText(Integer.toString(temperature));
		
		super.initGui();
	}

	@Override
	protected void keyTyped(char par1, int par2) 
	{
		super.keyTyped(par1, par2);
		searchField.textboxKeyTyped(par1, par2);
		
		try{temperature = Integer.parseInt(searchField.getText());
		} catch(Exception e){}
	}

	@Override
	public void updateScreen() {
		// TODO Auto-generated method stub
		super.updateScreen();
	}	
}
