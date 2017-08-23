package com.upstridge.tmapp;

import android.graphics.Bitmap;

/**
 * 
 * @author Saurabh tomar
 * 
 */

public class Item
{
	public Bitmap image;
	public Bitmap icon;
	public String title;
	public String name;
	public boolean isSelected;
	

	public boolean isSelected()
	{
		return isSelected;
	}


	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}


	public Item(Bitmap image, String title)
	{
		super();
		this.image = image;
		this.title = title;
	}


	public Bitmap getImage()
	{
		return image;
	}

	public void setImage(Bitmap image)
	{
		this.image = image;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}
}
