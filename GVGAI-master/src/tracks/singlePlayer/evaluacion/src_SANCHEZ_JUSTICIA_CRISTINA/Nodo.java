package tracks.singlePlayer.evaluacion.src_SANCHEZ_JUSTICIA_CRISTINA;

import java.util.ArrayList;
import ontology.Types.ACTIONS;


public class Nodo implements Comparable <Nodo> {
	Nodo padre;
	ACTIONS ultimaAccion;
	final int x; 
	final int y; 
	public int g; // Distancia recorrida
	public int h; // Estimación heurística a la meta
	final int INF = 99999999; 
	
	
	// Constructor para agentes online 
	public Nodo (int x, int y) {
		this.x = x; 
		this.y = y; 
	}
	
	// Constructor para agentes offline
	public Nodo (int x, int y, Nodo nodoPadre, 
			ACTIONS accion) {
		padre = nodoPadre;
		ultimaAccion = accion;
		this.x = x; 
		this.y = y;
		g = INF; 
		h = 0; 
	}
	
	public int f() {
		return g + h; 
	}

	public int compareTo(Nodo otro) {
		if (this.f() < otro.f()) {
			return -1; 
		}
		if (this.f() == otro.f()) {
			return 0; 
		}
		if (this.f() > otro.f()){
			return 1; 			
		}
		// Para que no se queje eclipse
		return 0; 
	}
	
	public ArrayList<Nodo> generarSucesores() {
		ArrayList<Nodo> sucesores = new ArrayList<Nodo>(); 
		// Expande arriba abajo, izquierda, derecha
		// Cambios en ACTION. por motivos de debug
		sucesores.add(new Nodo(x, y-1, this, ACTIONS.ACTION_UP)); 
		sucesores.add(new Nodo(x, y+1, this, ACTIONS.ACTION_DOWN)); 
		sucesores.add(new Nodo(x-1, y, this, ACTIONS.ACTION_LEFT));
		sucesores.add(new Nodo(x+1, y, this, ACTIONS.ACTION_RIGHT)); 
		return sucesores; 
	}
	
	public ArrayList<ACTIONS> getCamino() {
		ArrayList<ACTIONS> camino = new ArrayList<ACTIONS>(); 
		ACTIONS anterior = ultimaAccion;
		Nodo nodoAnterior = padre;
		while(nodoAnterior != null) {
			camino.add(0, anterior); 
			nodoAnterior = nodoAnterior.padre; 
			if (nodoAnterior != null)
				anterior = nodoAnterior.ultimaAccion; 
		}
		return camino; 
	}
	
	public boolean esIgualA(Nodo otro) {
		return this.x == otro.x && this.y == otro.y; 
	}
	
	public int getID() {
		return 1000*x+y; 
	}
	
	
	public int getDistancia(Nodo nodo) {
		return (int) (Math.abs(this.x - nodo.x) + Math.abs(this.y - nodo.y)); 
	}
}