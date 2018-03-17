package net.feerie.creatura.shared.creature.ia;

import net.feerie.creatura.shared.actions.TypeAction;
import net.feerie.creatura.shared.entites.TypeEntite;

public class ActionPonderee
{
	private final TypeAction action;
	private final TypeEntite entite;
	private final int score;
	
	public ActionPonderee(TypeAction action, TypeEntite entite, int score)
	{
		this.action = action;
		this.entite = entite;
		this.score = score;
	}
	
	public TypeAction getAction()
	{
		return action;
	}
	
	public TypeEntite getEntite()
	{
		return entite;
	}
	
	public int getScore()
	{
		return score;
	}
}
