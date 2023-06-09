package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>{

   private boolean marque;
   private Node sommet;
   protected double cout;
   private Arc père ;
   private boolean inTas; // vrai si le noeud a été mis dans le tas

   
   public Label(Node sommet, boolean marque, Arc père) {
	   this.sommet=sommet;
	   this.marque=marque;
	   this.père=père;
	   this.cout = Double.MAX_VALUE;
   }
  
   
	
   public boolean isMarque() {
		return this.marque;
	}

   // return true si le noeud a été marqué 
	public void setMarque(boolean marque) {
		this.marque = marque;
	}
   
   public Node getSommet() {
	   return sommet;
   }
   
   public void setSommet(Node sommet) {
	   this.sommet=sommet;
   }
   
   public Arc getPère() {
	   return père;
   }
   
   public void setPère(Arc père) {
	   this.père=père;
   }
   
   public double getCout() {
	   return cout ;
   }
   
   public void setCout(double cout ) {
	   this.cout=cout;
   }
   
   
  public double getCost() {
	  return this.cout;
  }
  
  public double getTotalCost() {
	  return this.cout;
  }

	/* Retourne true si le noeud a été mis dans le tas */
	public boolean getInTas() {
		return this.inTas;
	}	
	
	public void setInTas() {
		this.inTas = true;
	}

@Override
//Comparer les deux label selon leur couts 
 public int compareTo(Label label) {
	int comparaison=Double.compare(this.getTotalCost(), label.getTotalCost());
			return comparaison;
}
   
   

}

