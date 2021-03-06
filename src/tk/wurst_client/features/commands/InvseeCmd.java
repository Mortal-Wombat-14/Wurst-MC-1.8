/*
 * Copyright � 2014 - 2016 | Wurst-Imperium | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.features.commands;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.inventory.GuiInventory;
import tk.wurst_client.events.listeners.RenderListener;
import tk.wurst_client.utils.ChatUtils;

@Cmd.Info(description = "Allows you to see parts of another player's inventory.",
	name = "invsee",
	syntax = {"<player>"})
public class InvseeCmd extends Cmd implements RenderListener
{
	private String playerName;
	
	@Override
	public void execute(String[] args) throws Error
	{
		if(args.length != 1)
			syntaxError();
		if(mc.player.capabilities.isCreativeMode)
		{
			ChatUtils.error("Survival mode only.");
			return;
		}
		playerName = args[0];
		wurst.events.add(RenderListener.class, this);
	}
	
	@Override
	public void onRender()
	{
		boolean found = false;
		for(Object entity : mc.world.loadedEntityList)
			if(entity instanceof EntityOtherPlayerMP)
			{
				EntityOtherPlayerMP player = (EntityOtherPlayerMP)entity;
				if(player.getName().equals(playerName))
				{
					ChatUtils.message("Showing inventory of "
						+ player.getName() + ".");
					mc.displayGuiScreen(new GuiInventory(player));
					found = true;
				}
			}
		if(!found)
			ChatUtils.error("Player not found.");
		playerName = null;
		wurst.events.remove(RenderListener.class, this);
	}
}
