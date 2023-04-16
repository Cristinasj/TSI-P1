package tracks.singlePlayer.evaluacion.src_SANCHEZ_JUSTICIA_CRISTINA;

import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.PriorityQueue; 
import core.game.StateObservation;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;

public class AgenteRTAStar extends Agente {
	
	ArrayList<ACTIONS> camino; 
	int numLlamadasAct = 0; 
	ArrayList<ArrayList<Boolean>> esVisitable;
	boolean encontrado; // Salida de datos
	HashMap<Integer, Integer> heuristicas;  

	public AgenteRTAStar(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		super(stateObs, elapsedTimer);
		camino = new ArrayList<ACTIONS>(); 
		encontrado = false; 
		heuristicas = new HashMap<>(); 
	}
	
	/**
	 * @param stateObs: Observaciones del mundo 
	 * @param inicio: Posición del inicio del avatar
	 * @param coordenadasFin: Posicion del portal 
	 * @return acciones que llevan al camino más corto
	 */	
	public ACTIONS RTAStar (StateObservation stateObs,
			ArrayList<Integer> inicio, 
			ArrayList<Integer> coordenadasFin) {
		Nodo nodoActual = new Nodo(inicio.get(0), 
				inicio.get(1), null, ACTIONS.ACTION_NIL);
		Nodo fin = new Nodo(coordenadasFin.get(0), coordenadasFin.get(1), null, 
				ACTIONS.ACTION_NIL);  
		Nodo nodoSiguiente = null; 
		ArrayList<Nodo> vecindario = nodoActual.generarSucesores(); 
		ArrayList<ArrayList<Boolean>> esVisitable = llenarMatriz(stateObs);
		int f, heuristicaActual; 
		final int INF = 99999; 
		int mejorH = INF; 
		int mejorH2 = INF; 
		
		for (Nodo vecino : vecindario) {
			if (esVisitable.get(nodoActual.x).get(nodoActual.y)) {
				// Se ha visitado el nodo 
				if (heuristicas.containsKey(vecino.getID()))
					f = heuristicas.get(vecino.getID());
				else {
					vecino.g = 0; 
					vecino.h = vecino.getDistancia(fin); 
					f = vecino.f(); 
					
				}
				if (f < mejorH) {
					mejorH2 = mejorH;
					mejorH = f; 
					nodoSiguiente = vecino; 
				}
				else 
					if (f < mejorH2)
						mejorH2 = f; 
					
				
			}
		}
		if (heuristicas.containsKey(nodoActual.getID()))
			heuristicaActual = heuristicas.get(nodoActual.getID()); 
		else 
			heuristicaActual = nodoActual.f(); 
		// Se busca el máximo 
		if (mejorH2 != INF && mejorH2 > heuristicaActual)
			heuristicaActual = mejorH2 + 1; 
		else {
			if (mejorH+1 > heuristicaActual) {
				heuristicaActual = mejorH+1; 
			}
		}
		heuristicas.put(nodoActual.getID(), heuristicaActual); 
		nodosExpandidos++; 
		if (nodoSiguiente.esIgualA(fin))
			encontrado = true; 
		return nodoSiguiente.ultimaAccion; 
	}
	
	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		numLlamadasAct++;
		ArrayList<Integer> inicio = pasarAEntero(posicionAvatar(stateObs));
		ACTIONS accion = null; 
			long tInicio = System.nanoTime(); 
			accion = RTAStar(stateObs, inicio, portal); 
			long tFin = System.nanoTime(); 
			// Para rellenar la tabla
			if (encontrado) {
				System.out.println("Runtime acumulado: " + (tFin - tInicio)/1000000);
				System.out.println("Tamaño ruta: " + numLlamadasAct);
				System.out.println("Numero de nodos: " + numLlamadasAct);
				System.out.println("Máximo nº nodos en memoria: " + heuristicas.size()); 			
			}
		return accion;
	}
}

