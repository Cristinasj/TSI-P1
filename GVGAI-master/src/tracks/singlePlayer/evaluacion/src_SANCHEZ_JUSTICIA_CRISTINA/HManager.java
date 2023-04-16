package tracks.singlePlayer.evaluacion.src_SANCHEZ_JUSTICIA_CRISTINA;

import java.util.HashMap;

public class HManager {
	HashMap<Integer, Integer> tabla; 
	Nodo fin; 
	
	HManager() {
		tabla = new HashMap<Integer, Integer>();
		fin = null; 
	}
	
	void setNodoFinal(Nodo n) {
		fin = n; 
	}
	
	Integer get(int x, int y) {
	      int val = 0; 
	      Nodo n = new Nodo(x,y);
	      if (tabla.containsKey(n.getID()))
	    	  val = tabla.get(new Nodo(x,y).getID());
	      else 
	    	  val = n.getDistancia(fin); 
	      return val;
	}
	
    public void set(int x, int y,int h) {
        tabla.put(new Nodo(x,y).getID(), h);
      }
      
}