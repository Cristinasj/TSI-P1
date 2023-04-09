package tracks.singlePlayer.evaluacion.src_SANCHEZ_JUSTICIA_CRISTINA;

public class AgenteDijkstra extends AbstractPlayer {
    Vector2d fescala; 
    Vector2d portar; 

    public AgenteDijkstra(StateObservation stateObs, ElapsedCpuTimer elapsedTimer){
        
        // Calculo factor escala entre mundos 
        fescala = new Vector2d(
            stateObs.getWorldDimension().width/
            stateObs.getObservationGrid().lenth, 
            stateObs.getWorldDimension().height / 
            stateObs.getObservationGrid()[0].length
        ); 

        // Lista de portales, ordenada por cercanía al avatar
        ArrayList<Observation>[] posiciones = stateObs.getPortalPositions(stateObs.getAvatarPosition()); 

        // Selección del portal más próximo 
        portal = posiciones[0].get(0).position; 
        portal.x = Math.floor(portal.x / fescala.x); 
        portal.y = Math.floor(portal.y / fescala.y); 

        public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
            Vector2d avatar = new Vector2d(stateObs.getAvatarPosition().x / fescala.x, 
            stateObs.getAvatarPosition().y / fescala.y); 
        }
    }
}
