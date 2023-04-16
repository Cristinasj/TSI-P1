package tracks.singlePlayer.evaluacion.src_SANCHEZ_JUSTICIA_CRISTINA;

import java.util.ArrayList;
import java.util.PriorityQueue; 
import core.game.StateObservation;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;

public class AgenteAStar extends Agente {
	
	ArrayList<ACTIONS> camino; 
	ArrayList<ArrayList<Boolean>> esVisitable; 

	public AgenteAStar(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		super(stateObs, elapsedTimer);
		camino = new ArrayList<ACTIONS>(); 
	}
	
	/**
	 * @param stateObs: Observaciones del mundo 
	 * @param inicio: Posición del inicio del avatar
	 * @param coordenadasFin: Posicion del portal 
	 * @return acciones que llevan al camino más corto
	 */	
	public ArrayList<ACTIONS> AStar (StateObservation stateObs,
			ArrayList<Integer> inicio, 
			ArrayList<Integer> coordenadasFin) {
		Nodo fin = new Nodo(coordenadasFin.get(0), coordenadasFin.get(1), null, 
				ACTIONS.ACTION_NIL);  
		PriorityQueue<Nodo> abiertos = new PriorityQueue<Nodo>(); 
		// forall nodo in grafo: g(nodo) = inf (automatico en clase Nodo)  
		// visitados = []
		ArrayList<ArrayList<Boolean>> esVisitable = llenarMatriz(stateObs);
		// g(incial) = 0
		Nodo nodoInicial = new Nodo(inicio.get(0), 
				inicio.get(1), null, ACTIONS.ACTION_NIL);
		nodoInicial.g = 0;
		abiertos.add(nodoInicial); 
		// while True: 
		while (!abiertos.isEmpty()) {
			// actual = argmin(g tal que not visitado)
			Nodo actual = abiertos.poll(); 
			// if actual == objetivo: 
			if (actual.x == fin.x && actual.y == fin.y) {
				// return, ya se ha encontrado el camino 
				return actual.getCamino(); 
			}
			// visitados.add(actual) 
			esVisitable.get(actual.x).set(actual.y, false); 
			// foreach sucesor in expandir(actual):
			for (Nodo sucesor : actual.generarSucesores()) {
				// if not visitados.contains(sucesor) 
				// and g(sucesor) > g(actual) 
				// + distance(actual, sucesor):
				if (esVisitable.get(sucesor.x).get(sucesor.y) 
					&& sucesor.g > ( actual.g + actual.getDistancia(sucesor))) {
					// g(sucesor) = g(actual) + 
					// distancia(actual, sucesor) 
					sucesor.g = actual.g + actual.getDistancia(sucesor); 
					abiertos.add(sucesor);
					sucesor.h = sucesor.getDistancia(fin); 
				}
				
			}
		
		}
		// El código solo llega aquí si no hay solución 
		return new ArrayList<ACTIONS>(); 
	}

	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		ArrayList<Integer> inicio = pasarAEntero(posicionAvatar(stateObs));
		ACTIONS accion = null; 
		// Si no hay camino calculado se calcula el camino 
		if (camino.size() == 0) {
			long tInicio = System.nanoTime(); 
			camino = AStar(stateObs, inicio, portal);
			System.out.println(camino); 
			long tFin = System.nanoTime(); 
			// Para rellenar la tabla
			System.out.println("Runtime acumulado: " + (tFin - tInicio)/1000000);
			System.out.println("Tamaño ruta: " + camino.size());
			System.out.println("Numero de nodos: " + nodosExpandidos);
			System.out.println("Máximo nº nodos en memoria: " + maxNodos); 	
		}		
		// Si ya tenemos el camino, se van sacando las posiciones 
		if (camino.size() > 0) {
			// pop
			if (camino.size() > 0) {
				accion = camino.get(0); 
				camino.remove(0);		
			}
		} 
		return accion;
	}
}

