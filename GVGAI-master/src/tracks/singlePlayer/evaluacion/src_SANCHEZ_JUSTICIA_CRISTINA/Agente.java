package tracks.singlePlayer.evaluacion.src_SANCHEZ_JUSTICIA_CRISTINA;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;

public class Agente extends AbstractPlayer {
	// Factor de escala entre mundos ( pixeles -> grid ) 
	Vector2d fescala;


	// Matriz de posiciones por las que se puede y no se puede pasar 
	int altura; 
	int anchura; 
	
	// Para rellenar tabla
	int nodosExpandidos; 
	int maxNodos; 
	
	// Coordenadas del inicio y del portal 
	ArrayList<Integer> inicio; 
	ArrayList<Integer> portal; 
	
	public Agente (StateObservation stateObs, ElapsedCpuTimer elapsedTimer) { 
		fescala = new Vector2d (stateObs.getWorldDimension().width /
				stateObs.getObservationGrid().length , 
				stateObs.getWorldDimension().height / 
				stateObs.getObservationGrid()[0].length);  
		anchura = stateObs.getObservationGrid().length;
		altura = stateObs.getObservationGrid()[0].length; 

		// Buscamos las coordenadas del inicio y del portal  
		ArrayList<Observation>[] posiciones = stateObs.getPortalsPositions(stateObs.getAvatarPosition());
		Vector2d portal2d = posiciones[0].get(0).position; 
		portal2d.x = Math.floor(portal2d.x / fescala.x); 
		portal2d.y = Math.floor(portal2d.y/fescala.y);
		portal = pasarAEntero(portal2d); 
		nodosExpandidos = 0; 
		maxNodos = 0; 
	}
	
	// Esta función llena la matriz esCaminable con true en los lugares por los que se puede 
	// pasar y false en los lugares que no se puede pasar 
	ArrayList<ArrayList<Boolean>> llenarMatriz(StateObservation stateObs) {
		ArrayList<ArrayList<Boolean>> devolver = new ArrayList<ArrayList<Boolean>>(); 
		// Primero se llenan todas las casillas con true 
		for (int i = 0; i < anchura; i++) {
			ArrayList<Boolean> linea = new ArrayList<Boolean>(); 
			for (int j = 0; j < altura; j++) {
				linea.add(true); 
			}
			devolver.add(linea); 
		}
		// Se cambian a false las casillas que son osbtáculos
		ArrayList<Observation> obstaculos [] = stateObs.getImmovablePositions(); 
		for (int indiceListaObstaculos = 0; indiceListaObstaculos < obstaculos.length; indiceListaObstaculos++ ) {
			for (int indiceObstaculo = 0; indiceObstaculo < obstaculos[indiceListaObstaculos].size(); indiceObstaculo++) {
				Observation obstaculo = obstaculos[indiceListaObstaculos].get(indiceObstaculo);
				int x = (int) Math.floor(obstaculo.position.x / fescala.x); 
				int y = (int) Math.floor(obstaculo.position.y / fescala.y); 
				devolver.get(x).set(y, false); 
			}
		}
		return devolver; 
	}
	
	ArrayList<Integer> pasarAEntero (Vector2d v) {
		ArrayList<Integer> xy = new ArrayList<Integer>(); 
		xy.add((int) Math.floor(v.x)); 
		xy.add((int) Math.floor(v.y));
		return xy; 
	}
	
	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		ACTIONS accion = ACTIONS.ACTION_NIL; 
		return accion;
	}
	
	public Vector2d posicionAvatar(StateObservation stateObs) {
		return new Vector2d(stateObs.getAvatarPosition().x / fescala.x, stateObs.getAvatarPosition().y / fescala.y); 
	}
	


}