package ru.qrushtabs.app.quests;

public class ProgressItem {
	public String name;
	public int full;
	public int part;
	
	public ProgressItem()
	{
		 
	}
	public ProgressItem(String name, int full, int part)
	{
		this.name = name;
		this.full = full;
		this.part = part;
	}
}
