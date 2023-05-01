package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    protected Label NewLabel(Node node, boolean marque, Arc père,ShortestPathData data) {
    	return new Label(node,marque,père);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	 final ShortestPathData data = getInputData();
         ShortestPathSolution solution = null;
         Graph graph= data.getGraph();
         
         final int nbLabels = graph.size();
         Label X;
         boolean exist;
         // Nb Nodes = Nblabels 
         Label[] tableauLabel = new Label[nbLabels];
         Node orgNode = data.getOrigin(); // origin Node 
         //initialiser le tableau de labels
         for(Node node :graph.getNodes()) {
         	tableauLabel[node .getId()]=NewLabel(node,false,null,data);
         		}	
         // initialiser le cout du Origin Node , on la marque 
         // cost(origin)=0
         tableauLabel[orgNode.getId()]= NewLabel(data.getOrigin(),true,null,data);
         tableauLabel[data.getOrigin().getId()].setCout(0);
         
         //Notify all observers that the origin has been processed.
         notifyOriginProcessed(data.getOrigin());
         
         //inserer le label du node origin dans  le heap 
         BinaryHeap <Label> HeapLabel = new BinaryHeap <Label>();
         
        for(Label label : tableauLabel) {
     		HeapLabel.insert(label);
     		}
     
        while(!HeapLabel.isEmpty() && ! tableauLabel[data.getDestination().getId()].isMarque()) {
       // X c'est le sommet courant 
         	X=HeapLabel.deleteMin();
         	// on vérifie d'abord si le cout du sommet courant est à l'infini, si c'est le cas le problème n'a pas de solution
         	if(X.getCost() == Double.MAX_VALUE ) {
         		break;
         	}
          // on supprime le min du tas et on le marque 
         	else {	
         		X.setMarque(true);
         		System.out.println(X.getCost());
         	
         	// pour tous les succeseurs de X noeud courant 
         for(Arc arc : X.getSommet().getSuccessors()) { 
        	 // on verifie si on peut vraiment prendre cet arc 
  			if (!data.isAllowed(arc)) {
                continue;
            }
    
            double w = data.getCost(arc);
            //on recupere le label du sommet suivant 
         	Label labSuiv =tableauLabel[arc.getDestination().getId()];
         	if(! labSuiv.isMarque() ) {
         		//si c'est le meilleur cout 
         		if(labSuiv.getCost() > X.getCost()+ w ) {

         		labSuiv.setCout(X.getCost()+  w );
         		labSuiv.setPère(arc);
         		notifyNodeReached(arc.getDestination());
         	if(labSuiv.isMarque()==true) {
         		HeapLabel.remove(labSuiv);
             	HeapLabel.insert(labSuiv);
             	
         		}else {
         		HeapLabel.insert(labSuiv);
         		}
        
         		
         		
         	}
         	}
         	
         	}
        }
        }
        // si la destination n'a pas de predecesseur la solution est  infeasible
        if (!tableauLabel[data.getDestination().getId()].isMarque()) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = tableauLabel[data.getDestination().getId()].getPère();
            while (arc != null) {
                arcs.add(arc);
                arc = tableauLabel[arc.getOrigin().getId()].getPère();
            }

            // Reverse the path...
            Collections.reverse(arcs);
     		notifyDestinationReached(data.getDestination());

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }

        


        return solution;
    }

}
