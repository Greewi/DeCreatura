package net.feerie.creatura.client;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.RootPanel;

import net.feerie.creatura.shared.Console;
import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.monde.Monde;

public class ControleurVue
{
	private final Vue vue;
	private final Monde monde;
	private final Creatura creatura;
	private int xVueInitial;
	private int xSourisInitial;
	private int ySourisInitial;
	private boolean scrollEnCours;
	private HandlerRegistration mouseMoveRegistration;
	private HandlerRegistration mouseUpRegistration;
	
	public ControleurVue(Creatura creatura)
	{
		this.creatura = creatura;
		this.vue = creatura.getVue();
		this.monde = creatura.getMonde();
		initialiseEvenement();
	}
	
	private final void reinitialiseHandlers()
	{
		if (mouseMoveRegistration != null)
		{
			mouseMoveRegistration.removeHandler();
			mouseMoveRegistration = null;
			mouseUpRegistration.removeHandler();
			mouseUpRegistration = null;
		}
	}
	
	private final void initialiseEvenement()
	{
		final MouseMoveHandler mouseMoveHandler = new MouseMoveHandler()
		{
			
			@Override
			public void onMouseMove(MouseMoveEvent event)
			{
				int x = event.getRelativeX(event.getRelativeElement());
				int y = event.getRelativeY(event.getRelativeElement());
				if ((x - xSourisInitial) * (x - xSourisInitial) + (y - ySourisInitial) * (y - ySourisInitial) > 20 * 20) //20 pixels de tolérance au scroll
					scrollEnCours = true;
				vue.setXVue(xVueInitial - ((x - xSourisInitial) * vue.getLargeurVue()) / vue.getLargeurFenetre());
			}
		};
		
		final MouseUpHandler mouseUpHandler = new MouseUpHandler()
		{
			
			@Override
			public void onMouseUp(MouseUpEvent event)
			{
				reinitialiseHandlers();
				
				if (scrollEnCours)
				{
					scrollEnCours = false;
				}
				else
				{
					int x = vue.getXVue() + (event.getRelativeX(event.getRelativeElement()) * vue.getLargeurVue()) / vue.getLargeurFenetre();
					int y = 1000 - (event.getRelativeY(event.getRelativeElement()) * vue.getHauteurVue()) / vue.getHauteurFenetre();
					
					Entite entiteSelectionnee = null;
					for (Entite entite : monde.getEntiteAPosition(new Position(x, y)))
					{
						if (entiteSelectionnee == null || entite.getType().getPrioriteSelection() > entiteSelectionnee.getType().getPrioriteSelection())
							entiteSelectionnee = entite;
					}
					
					creatura.ouvreInterfaceGenerale();
					
					if (entiteSelectionnee != null)
					{
						String outilAOuvrir = entiteSelectionnee.active(null);
						Console.log("Outil à ouvrir : " + outilAOuvrir);
						if (outilAOuvrir != null && outilAOuvrir.equalsIgnoreCase("Creature") && entiteSelectionnee.getType() == TypeEntite.CREATURE)
							creatura.ouvreInterfaceCreature((Creature) entiteSelectionnee);
					}
				}
			}
		};
		
		final MouseDownHandler mouseDownHandler = new MouseDownHandler()
		{
			
			@Override
			public void onMouseDown(MouseDownEvent event)
			{
				scrollEnCours = false;
				xSourisInitial = event.getRelativeX(event.getRelativeElement());
				ySourisInitial = event.getRelativeY(event.getRelativeElement());
				xVueInitial = vue.getXVue();
				mouseMoveRegistration = RootPanel.get().addDomHandler(mouseMoveHandler, MouseMoveEvent.getType());
				mouseUpRegistration = RootPanel.get().addDomHandler(mouseUpHandler, MouseUpEvent.getType());
			}
		};
		
		final BlurHandler blurHandler = new BlurHandler()
		{
			
			@Override
			public void onBlur(BlurEvent event)
			{
				reinitialiseHandlers();
				scrollEnCours = false;
			}
		};
		
		final MouseOutHandler mouseOutHandler = new MouseOutHandler()
		{
			
			@Override
			public void onMouseOut(MouseOutEvent event)
			{
				reinitialiseHandlers();
				scrollEnCours = false;
			}
		};
		
		vue.getCanvas().addMouseDownHandler(mouseDownHandler);
		RootPanel.get().addDomHandler(blurHandler, BlurEvent.getType());
		RootPanel.get().addDomHandler(mouseOutHandler, MouseOutEvent.getType());
		
		RootPanel.get().addDomHandler(new KeyDownHandler()
		{
			
			@Override
			public void onKeyDown(KeyDownEvent event)
			{
				if (event.getNativeKeyCode() == KeyCodes.KEY_NUM_PLUS)
				{
					Constantes.PERIODE_TIC /= 1.20;
					if (Constantes.PERIODE_TIC < 5)
						Constantes.PERIODE_TIC = 5;
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_NUM_MINUS)
				{
					Constantes.PERIODE_TIC *= 1.20;
				}
			}
		}, KeyDownEvent.getType());
		
	}
	
}
