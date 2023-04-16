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
	HManager heuristicas;  
	final int INF = 999999; 

	public AgenteRTAStar(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		super(stateObs, elapsedTimer);
		camino = new ArrayList<ACTIONS>(); 
		encontrado = false; 
		heuristicas = new HManager(); 
		esVisitable = llenarMatriz(stateObs);
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
	    // Obtener nodo en el que se encuentra
	    Nodo actual = new Nodo(inicio.get(0), inicio.get(1));
	    Nodo fin = new Nodo(coordenadasFin.get(0), coordenadasFin.get(1)); 
	    heuristicas.setNodoFinal(fin);
	    
	    // Generar los sucesores
	    ArrayList<Nodo> sucesores = new ArrayList<Nodo>();
	    ArrayList<Integer> hs = new ArrayList<Integer>();
	    Nodo arriba = new Nodo(actual.x, actual.y-1);
	    if (esVisitable.get(arriba.x).get(arriba.y)) {
	      sucesores.add(arriba);
	      hs.add(heuristicas.get(arriba.x, arriba.y));
	    }
	    Nodo abajo = new Nodo(actual.x, actual.y+1);
	    if (esVisitable.get(abajo.x).get(abajo.y)) {
	      sucesores.add(abajo); 
	      hs.add(heuristicas.get(abajo.x, abajo.y));
	    }
	    Nodo izquierda = new Nodo(actual.x-1, actual.y);
	    if (esVisitable.get(izquierda.x).get(izquierda.y)) {
	      sucesores.add(izquierda);
	      hs.add(heuristicas.get(izquierda.x, izquierda.y));
	    }
	    Nodo derecha = new Nodo(actual.x+1, actual.y);
	    if (esVisitable.get(derecha.x).get(derecha.y)) {
	      sucesores.add(derecha); 
	      hs.add(heuristicas.get(derecha.x, derecha.y));
	    }
	    
	    // Seleccionar el de menor valor h
	    int indMejorVecino = -1; int hMejorVecino = INF;
	    for (int i=0; i<hs.size(); ++i) {
	      if (hs.get(i) < hMejorVecino) {
	        hMejorVecino = hs.get(i);
	        indMejorVecino = i;
	      }
	    }
	    
	    if (indMejorVecino == -1) {
	      return ACTIONS.ACTION_NIL;
	    }
	    Nodo siguienteNodo = sucesores.get(indMejorVecino);
	    
	    // Seleccionar el de segundo menor valor h
	    sucesores.remove(indMejorVecino);
	    hs.remove(indMejorVecino);

	    int hSegundoMejorVecino = INF;
	    int indSegundoMejorVecino = -1;
	    for (int i=0; i<hs.size(); i++) {
	      if (hs.get(i) < hSegundoMejorVecino) {
	        hSegundoMejorVecino = hs.get(i);
	        indSegundoMejorVecino = i;
	      }
	    }
	    if (indSegundoMejorVecino == -1) {
	      hSegundoMejorVecino = hMejorVecino;
	    }
	    
	    // Actualizar la h del nodo actual, con el valor del segundo mejor vecino
	    heuristicas.set(actual.x, actual.y, Math.max(heuristicas.get(actual.x, actual.y), hSegundoMejorVecino)+1);
	    
	    if (siguienteNodo.esIgualA(fin)) 
	    	encontrado = true; 
	    // Devolver la acción que lleva del nodo actual al siguiente
	    if (actual.x == siguienteNodo.x) {
	      if (siguienteNodo.y == actual.y+1) 
	        return ACTIONS.ACTION_DOWN;
	      else if (siguienteNodo.y == actual.y-1) 
	        return ACTIONS.ACTION_UP;
	    }
	    else if (actual.y == siguienteNodo.y) {
	      if (siguienteNodo.x == actual.x-1) 
	        return ACTIONS.ACTION_LEFT;
	      else if (siguienteNodo.x == actual.x+1) 
	        return ACTIONS.ACTION_RIGHT;
	    }
	    
	    return ACTIONS.ACTION_NIL;
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
				System.out.println("Máximo nº nodos en memoria: " + heuristicas.tabla.size()); 			
			}
		return accion;
	}
}

