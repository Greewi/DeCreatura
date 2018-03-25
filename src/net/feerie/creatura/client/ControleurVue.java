package net.feerie.creatura.client;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.RootPanel;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.monde.Monde;

public class ControleurVue
{
	private final Vue vue;
	private final Camera camera;
	private final Monde monde;
	private final Creatura creatura;
	private double xCameraInitial;
	private double xSourisInitial;
	private double ySourisInitial;
	private boolean scrollEnCours;
	private HandlerRegistration mouseMoveRegistration;
	private HandlerRegistration mouseUpRegistration;
	private HandlerRegistration touchMoveRegistration;
	private HandlerRegistration touchEndRegistration;
	private int idTouchActuel;
	
	public ControleurVue(Creatura creatura)
	{
		this.creatura = creatura;
		this.vue = creatura.getVue();
		this.camera = this.vue.getCamera();
		this.monde = creatura.getMonde();
		initialiseEvenement();
	}
	
	private final void reinitialiseEtatInteraction()
	{
		if (mouseMoveRegistration != null)
		{
			mouseMoveRegistration.removeHandler();
			mouseMoveRegistration = null;
			mouseUpRegistration.removeHandler();
			mouseUpRegistration = null;
		}
		if (touchMoveRegistration != null)
		{
			touchMoveRegistration.removeHandler();
			touchMoveRegistration = null;
			touchEndRegistration.removeHandler();
			touchEndRegistration = null;
		}
		
		scrollEnCours = false;
		idTouchActuel = -1;
	}
	
	/**
	 * Début d'un clic/touch/drag
	 * 
	 * @param x coordonnée x du curseur sur l'écran
	 * @param y coordonnée y du curseur sur l'écran
	 */
	public final void touchStart(int x, int y)
	{
		xSourisInitial = x;
		ySourisInitial = y;
		scrollEnCours = false;
		xCameraInitial = camera.getX();
	}
	
	public final void touchMove(int x, int y)
	{
		if ((x - xSourisInitial) * (x - xSourisInitial) + (y - ySourisInitial) * (y - ySourisInitial) > 20 * 20) //20 pixels de tolérance au scroll
			scrollEnCours = true;
		camera.setX(xCameraInitial - (camera.getXMonde(x) - camera.getXMonde(xSourisInitial)));
	}
	
	public final void touchEnd(int x, int y)
	{
		if (!scrollEnCours)
		{
			int xMonde = camera.getXMonde(x);
			int zMonde = camera.getZMonde(y);
			
			Entite entiteSelectionnee = null;
			for (Entite entite : monde.getEntiteAPosition(new Position(xMonde, zMonde)))
			{
				if (entiteSelectionnee == null || entite.getType().getPrioriteSelection() > entiteSelectionnee.getType().getPrioriteSelection())
					entiteSelectionnee = entite;
			}
			
			if (entiteSelectionnee == null)
			{
				camera.focusEntite(null, 1.0);
				creatura.ouvreInterfaceGenerale();
			}
			else
			{
				String outilAOuvrir = entiteSelectionnee.active(null);
				Console.log("Outil à ouvrir : " + outilAOuvrir);
				if (outilAOuvrir != null && outilAOuvrir.equalsIgnoreCase("Creature") && entiteSelectionnee.getType() == TypeEntite.CREATURE)
				{
					creatura.ouvreInterfaceCreature((Creature) entiteSelectionnee);
					if (camera.getFocus() != entiteSelectionnee)
						camera.focusEntite(entiteSelectionnee, 3.0);
				}
				else
				{
					camera.focusEntite(null, 1.0);
					creatura.ouvreInterfaceGenerale();
				}
			}
		}
	}
	
	private final void initialiseEvenement()
	{
		final MouseMoveHandler mouseMoveHandler = (MouseMoveEvent event) -> {
			int x = event.getRelativeX(event.getRelativeElement());
			int y = event.getRelativeY(event.getRelativeElement());
			touchMove(x, y);
		};
		
		final MouseUpHandler mouseUpHandler = (MouseUpEvent event) -> {
			int x = event.getRelativeX(event.getRelativeElement());
			int y = event.getRelativeY(event.getRelativeElement());
			touchEnd(x, y);
			reinitialiseEtatInteraction();
		};
		
		final MouseDownHandler mouseDownHandler = (MouseDownEvent event) -> {
			int x = event.getRelativeX(event.getRelativeElement());
			int y = event.getRelativeY(event.getRelativeElement());
			touchStart(x, y);
			mouseMoveRegistration = RootPanel.get().addDomHandler(mouseMoveHandler, MouseMoveEvent.getType());
			mouseUpRegistration = RootPanel.get().addDomHandler(mouseUpHandler, MouseUpEvent.getType());
		};
		
		final BlurHandler blurHandler = (BlurEvent event) -> {
			reinitialiseEtatInteraction();
		};
		
		final MouseOutHandler mouseOutHandler = (MouseOutEvent event) -> {
			reinitialiseEtatInteraction();
		};
		
		final TouchMoveHandler touchMoveHandler = (TouchMoveEvent event) -> {
			JsArray<Touch> touches = event.getChangedTouches();
			for (int i = 0; i < touches.length(); i++)
			{
				Touch touch = touches.get(i);
				if (touch.getIdentifier() == idTouchActuel)
				{
					int x = touch.getRelativeX(event.getRelativeElement());
					int y = touch.getRelativeY(event.getRelativeElement());
					touchMove(x, y);
					break;
				}
			}
		};
		
		final TouchEndHandler touchEndHandler = (TouchEndEvent event) -> {
			JsArray<Touch> touches = event.getChangedTouches();
			for (int i = 0; i < touches.length(); i++)
			{
				Touch touch = touches.get(i);
				if (touch.getIdentifier() == idTouchActuel)
				{
					int x = touch.getRelativeX(event.getRelativeElement());
					int y = touch.getRelativeY(event.getRelativeElement());
					touchEnd(x, y);
					reinitialiseEtatInteraction();
					break;
				}
			}
		};
		
		final TouchStartHandler touchStartHandler = (TouchStartEvent event) -> {
			if (idTouchActuel != -1)
				return;
			JsArray<Touch> touches = event.getChangedTouches();
			Touch touch = touches.get(0);
			idTouchActuel = touch.getIdentifier();
			int x = touch.getRelativeX(event.getRelativeElement());
			int y = touch.getRelativeY(event.getRelativeElement());
			touchStart(x, y);
			touchMoveRegistration = RootPanel.get().addDomHandler(touchMoveHandler, TouchMoveEvent.getType());
			touchEndRegistration = RootPanel.get().addDomHandler(touchEndHandler, TouchEndEvent.getType());
		};
		
		final TouchCancelHandler touchCancelHandler = (TouchCancelEvent) -> {
			reinitialiseEtatInteraction();
		};
		
		vue.getCanvas().addMouseDownHandler(mouseDownHandler);
		vue.getCanvas().addTouchStartHandler(touchStartHandler);
		RootPanel.get().addDomHandler(blurHandler, BlurEvent.getType());
		RootPanel.get().addDomHandler(mouseOutHandler, MouseOutEvent.getType());
		RootPanel.get().addDomHandler(touchCancelHandler, TouchCancelEvent.getType());
		
		RootPanel.get().addDomHandler((KeyDownEvent event) -> {
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
		}, KeyDownEvent.getType());
		
		reinitialiseEtatInteraction();
	}
	
}
