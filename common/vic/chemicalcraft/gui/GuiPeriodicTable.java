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
	
	float scrollY = 0.0F;
	
	boolean isScrolling = false;
	int temperature = 20;
	
	final int minScroll = 18;
	final int maxScroll = 146;
	
	Substance[] substances = SubstanceRegistry.getAllSubstances();
	SubstanceSlot[] substanceSlots = new SubstanceSlot[36];
	
	private GuiTextField temperatureField;
	
	@Override
	public void drawScreen(int par1, int par2, float par3) 
	{
		drawDefaultBackground();
		
		boolean var4 = Mouse.isButtonDown(0);
		if(substances.length <= 36) var4 = false;
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(CommonProxy.GUI_PERIODIC);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		fontRenderer.drawString("Periodic Table", guiLeft + 7, guiTop + 7, 0);
		fontRenderer.drawString("temp.:", guiLeft + 160, guiTop + 7, 0);	
		
		temperatureField.drawTextBox();
		drawSubstances();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(CommonProxy.GUI_PERIODIC);
		
		int var1 = guiTop + minScroll;
        int var2 = var1 + maxScroll;
		
        drawTexturedModalRect(guiLeft + 229, var1 + (int)((float)(var2 - var1 - 17) * this.scrollY), 0 + substances.length <= 36 ? 12 : 0, 170, 12, 15);
        
		if(var4)
		{
			if((par1 > guiLeft + 229 && par1 <= guiLeft + 241) && (par2 > guiTop + minScroll && par2 <= guiTop + maxScroll + 15))
			{
				isScrolling = true;
			}			
		}
		else if(isScrolling == true) isScrolling = false;
		
		if(isScrolling)
		{	        			
			scrollY = ((float)(par2 - var1) - 7.5F) / ((float)(var2 - var1) - 15.0F);
			
			if(scrollY > 1.0F) scrollY = 1.0F;
			if(scrollY < 0.0F) scrollY = 0.0F;
			
			scrollTo(scrollY);
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
		temperatureField.mouseClicked(par1, par2, par3);
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
		
		this.buttonList.clear();
			
		temperatureField = new GuiTextField(fontRenderer, this.guiLeft + 190, this.guiTop + 5, 46, 10);
        temperatureField.setMaxStringLength(6);
        temperatureField.setEnableBackgroundDrawing(true);
        temperatureField.setVisible(true);
        temperatureField.setTextColor(16777215);	
        temperatureField.setText(Integer.toString(temperature));
		
		super.initGui();
	}
	
	void scrollTo(float f1)
	{
        int var1 = this.substances.length / 9 - 4 + 1;
        int var2 = (int)((double)(f1 * (float)var1) + 0.5D);
        
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 9; j++)
			{	
				int counter = j + (i + var2) * 9;
				
				Substance s = null;
				if(counter >= 0 && counter < this.substances.length)s = substances[counter];
				
				substanceSlots[j + i * 9].substance = s;
			}
		}
	}

	@Override
	public void handleMouseInput() 
	{		
		super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();

        if (var1 != 0 && substances.length > 36)
        {
            int var2 = substances.length / 9 - 4 + 1;

            if (var1 > 0)
            {
                var1 = 1;
            }

            if (var1 < 0)
            {
                var1 = -1;
            }

            this.scrollY = (float)((double)this.scrollY - (double)var1 / (double)var2);

            if (this.scrollY < 0.0F)
            {
                this.scrollY = 0.0F;
            }

            if (this.scrollY > 1.0F)
            {
                this.scrollY = 1.0F;
            }

            scrollTo(this.scrollY);
        }
	}

	@Override
	protected void keyTyped(char par1, int par2) 
	{
		super.keyTyped(par1, par2);
		temperatureField.textboxKeyTyped(par1, par2);
		
		try{temperature = Integer.parseInt(temperatureField.getText());
		} catch(Exception e){}
	}	
}
