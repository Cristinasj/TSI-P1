package tracks.singlePlayer.evaluacion.src_SANCHEZ_JUSTICIA_CRISTINA;

import core.game.Observation; 
import core.game.StateObservation; 
import core.player.AbstractPlayer; 
import ontology.Types; 
import tools.ElapsedCpuTimer; 
import tools.Vector2d; 
import java.util.ArrayList; 



public class AgenteDijkstra extends AbstractPlayer {
    Vector2d fescala; 
    Vector2d posicionPortal; 
    
    public AgenteDijkstra(StateObservation stateObs, ElapsedCpuTimer elapsedTimer){

        fescala = new Vector2d(
            stateObs.getWorldDimension().width/
            stateObs.getObservationGrid().length, 
            stateObs.getWorldDimension().height / 
            stateObs.getObservationGrid()[0].length
        ); 

        // Lista de portales, ordenada por cercanía al avatar
        ArrayList<Observation>[] posiciones = stateObs.getPortalsPositions(stateObs.getAvatarPosition()); 

        // Selección del portal más próximo 
        posicionPortal = posiciones[0].get(0).position; 
        posicionPortal.x = Math.floor(posicionPortal.x / fescala.x); 
        posicionPortal.y = Math.floor(posicionPortal.y / fescala.y); 


    }

    @Override
    public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
        Vector2d posicionAvatar = new Vector2d(stateObs.getAvatarPosition().x / fescala.x, 
        stateObs.getAvatarPosition().y / fescala.y); 
        
        Vector2d posicionActual = posicionAvatar;  
        // Se expanden las casillas a las que se puede mover el avatar 
        // Se calculan las distancias hasta el portal 

        return Types.ACTIONS.ACTION_UP; 
    }
}

