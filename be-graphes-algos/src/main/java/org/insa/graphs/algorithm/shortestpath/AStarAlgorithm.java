package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    //on va Réécrire la méthode Newlabel a fin d'étuliser LabeslStar ou lieu de label 
    //on garde notre 3 parametres pour pouvoir utiliser newLabel mais on rajoute la destination quand on va le créer
    protected Label NewLabel(Node node , boolean marque, Arc père,ShortestPathData data) {
    	//final ShortestPathData data = getInputData();
    	return new LabelStar(node,false,null,data);
    }
    

}
