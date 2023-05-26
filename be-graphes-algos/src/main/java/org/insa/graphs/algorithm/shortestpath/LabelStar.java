package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class LabelStar  extends Label{
	
	public double coutDest;
	
	//LabelStar prend un argument de plus
	public LabelStar(Node sommet, boolean marque, Arc père, ShortestPathData data) {
		super(sommet, marque, père);
		

		this.coutDest = Point.distance(sommet.getPoint(), data.getDestination().getPoint());
		

	}
	
	
	@Override
	
	//Renvoi le cout de l'origine jusqu'au noeud  + cout a vol d'oiseau de noeud jusqu'au a la distinition 
	public double getTotalCost() {
		return this.cout+ this.coutDest;
	}

}
