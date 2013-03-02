package vic.chemicalcraft.handler;

import java.util.EnumSet;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import vic.chemicalcraft.ChemicalCraft;
import vic.chemicalcraft.helper.ChemRenderHelper;
import vic.chemicalcraft.helper.CursorHelper;
import vic.chemicalcraft.substance.ISubstanceInterface;
import vic.chemicalcraft.substance.SubstanceRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.TickType;

public class TickHandlerSP implements ITickHandler{

	private Container inventorySlots = null;
	
	int width;
	int height;
	int guiTop;
	int guiLeft;
	
	private Slot theSlot;
	
	RenderItem itemRenderer = new RenderItem();

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	
		if(type.contains(TickType.RENDER)) {
			
			Minecraft mc = Minecraft.getMinecraft();
			int x = 0;
			int y = 0;
			
			if(mc.currentScreen != null && mc.currentScreen instanceof GuiContainer)
			{
				width = mc.currentScreen.width;
				height =  mc.currentScreen.height;
				GuiContainer gui = (GuiContainer) mc.currentScreen;

				x = ((Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, 1)).intValue();
				y = ((Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, 2)).intValue();
				
				guiTop = (height - y)/2;
				guiLeft = (width - x)/2;
			}
				
			if(mc.thePlayer != null && !mc.inGameHasFocus && mc.currentScreen instanceof GuiContainer)
			{
				inventorySlots = mc.thePlayer.openContainer;

				for (int var13 = 0; var13 < this.inventorySlots.inventorySlots.size(); ++var13)
			    {
		            Slot var14 = (Slot)this.inventorySlots.inventorySlots.get(var13);
		            
		            if (CursorHelper.isMouseOverSlot(var14, CursorHelper.getX(), CursorHelper.getY(), guiLeft , guiTop)) this.theSlot = var14;
			    }
				
				InventoryPlayer var15 = mc.thePlayer.inventory;
				
				if (var15.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack())
		        {          
					ItemStack var19 = this.theSlot.getStack();
		            
					if(ChemicalCraft.isThaumcraftLoaded)
					{
						if(!mc.currentScreen.isShiftKeyDown())
						{
							renderAdditionalItemInformation(var19, CursorHelper.getX(), CursorHelper.getY());
						}
					}
					else renderAdditionalItemInformation(var19, CursorHelper.getX(), CursorHelper.getY());
		        }
				this.theSlot = null;
			}		
		}
	}

	private void renderAdditionalItemInformation(ItemStack stack, int x, int y)
	{
		Minecraft mc = Minecraft.getMinecraft();
		FontRenderer fontRenderer = mc.fontRenderer;		
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        List<?> var4 = stack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);

        String formula = null;
        
        Item item = stack.getItem();
   
        if(item instanceof ItemBlock)
        {
        	Block block = Block.blocksList[((ItemBlock)item).getBlockID()];
        	if(block instanceof ISubstanceInterface)
            {
            	ISubstanceInterface IS = (ISubstanceInterface) block;
            	if(IS.getSubstanceStack(stack) != null)
            	{
            		if(IS.hasStorage(stack))formula = IS.getSubstanceStack(stack).toStringWithStorage(IS.maxSubstanceStackSize());
            		else formula = IS.getSubstanceStack(stack).toString();
            	}         	       	          	
            }
        }
        
        if(item instanceof ISubstanceInterface)
        {
        	ISubstanceInterface IS = (ISubstanceInterface) item;
        	if(IS.getSubstanceStack(stack) != null)
        	{
        		if(IS.hasStorage(stack))formula = IS.getSubstanceStack(stack).toStringWithStorage(IS.maxSubstanceStackSize());
        		else formula = IS.getSubstanceStack(stack).toString();
        	}      	
        }
        else if(SubstanceRegistry.getSubstanceStack(item.getItemName()) != null)
        {
        	formula = SubstanceRegistry.getSubstanceStack(item.getItemName()).toString();
        }
     
        if (!var4.isEmpty() && formula != null)
        {
            int var5 = 0;
            int var6;
            int var7;

            for (var6 = 0; var6 < var4.size(); ++var6)
            {
                var7 = fontRenderer.getStringWidth((String)var4.get(var6));
                
                if (var7 > var5)
                {
                    var5 = var7;
                }
            }

            var6 = x + 20;
            var7 = y - 20;
            
            if(ChemicalCraft.isNEILoaded)
            {
            	var6 -= 8;
            	var7 -= 8;
            }
            
            if(fontRenderer.getStringWidth(formula) > var5)
            {
            	int var5A = fontRenderer.getStringWidth(formula);
            	var6 -= (var5A - var5) /2;
            	var5 += (var5A - var5);
            	
            }

            int var9 = 8;
            int var9A = 8;

            if (var4.size() > 1)
            {
                var9A += 2 + (var4.size() - 1) * 10;
            }

            if (var7 + var9A + 6 > this.height)
            {
                var7 = this.height - var9A - 22;
            }
     		
            ChemRenderHelper.zLevel = 300.0F;
            itemRenderer.zLevel = 300.0F;
            //-267386864
            int var10 = -267386864;
            ChemRenderHelper.drawGradientRect(var6 - 3, var7 - 4, var6 + var5 + 3, var7 - 3, var10, var10);
            ChemRenderHelper.drawGradientRect(var6 - 3, var7 + var9 + 3, var6 + var5 + 3, var7 + var9 + 4, var10, var10);
            ChemRenderHelper.drawGradientRect(var6 - 3, var7 - 3, var6 + var5 + 3, var7 + var9 + 3, var10, var10);
            ChemRenderHelper.drawGradientRect(var6 - 4, var7 - 3, var6 - 3, var7 + var9 + 3, var10, var10);
            ChemRenderHelper.drawGradientRect(var6 + var5 + 3, var7 - 3, var6 + var5 + 4, var7 + var9 + 3, var10, var10);
            //-123432432
            int var11 = 1347420415;      
            int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
            ChemRenderHelper.drawGradientRect(var6 - 3, var7 - 3 + 1, var6 - 3 + 1, var7 + var9 + 3 - 1, var11, var12);
            ChemRenderHelper.drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, var6 + var5 + 3, var7 + var9 + 3 - 1, var11, var12);
            ChemRenderHelper.drawGradientRect(var6 - 3, var7 - 3, var6 + var5 + 3, var7 - 3 + 1, var11, var11);
            ChemRenderHelper.drawGradientRect(var6 - 3, var7 + var9 + 2, var6 + var5 + 3, var7 + var9 + 3, var12, var12);


            fontRenderer.drawStringWithShadow(formula, var6, var7, -1);


            ChemRenderHelper.zLevel = 0.0F;
            itemRenderer.zLevel = 0.0F;
        }
	}
	
	 
    
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "chemicalCraft";
	}

}
