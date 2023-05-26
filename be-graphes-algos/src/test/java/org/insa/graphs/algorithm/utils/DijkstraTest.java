package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;


public class DijkstraTest {
	 	
			public void testScenario(String mapName, int Mode, int origine, int destination) throws Exception {

				// Create a graph reader.
				GraphReader reader = new BinaryGraphReader(
						new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

				// Read the graph.
				Graph graph = reader.read();


				System.out.println("Size graph :"+graph.size());


				//les modes : 0 pour distance & 1 pour temps 
				if (Mode!=0 && Mode!=1) {
					System.out.println("les argument invalide");
				} else {
					   // On est hors du graphe. / Sommets inexistants
					if (origine<0 || destination<0 || origine>=(graph.size()-1) || destination>=(graph.size()-1)) { 
						System.out.println("ERREUR : Paramètres invalides ");
						
					} else {
						ArcInspector arcInspectorDijkstra;
						
						if (Mode == 0) { //Temps
							System.out.println("le Mode : Temps");
							arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
						} else {
							System.out.println("le Mode : Distance");
							arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
						}
						
						
						System.out.println("--> Chemin de la carte : "+mapName);
						System.out.println("--> Origine : " + origine);
						System.out.println("--> Destination : " + destination);
						
						if(origine==destination) {
							System.out.println("Origine et Destination identiques");
							System.out.println("Cout solution: 0");
							
						} else {			
							ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
				
							BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
							DijkstraAlgorithm D = new DijkstraAlgorithm(data);
							
							// Recuperation des solutions de Bellman et Dijkstra 
							ShortestPathSolution solution = D.run();
							ShortestPathSolution attendu = B.run();
			
							// ya pas de PCC
							if (solution.getPath() == null) {
								assertEquals(attendu.getPath(), solution.getPath());
								System.out.println("PAS DE SOLUTION");
							}
							// on trouve un PCC
							else {
								double costSolution;
								double costattendu;
								if(Mode == 0) { //Temps
									//le cout de la solution 
									costSolution = solution.getPath().getMinimumTravelTime();
									costattendu = attendu.getPath().getMinimumTravelTime();
								} else { //distance 
									costSolution = solution.getPath().getLength();
									costattendu = attendu.getPath().getLength();
								}
								assertEquals(costattendu, costSolution, 0.001);
								System.out.println("Le Cout de la solution:  " + costSolution);
							}
						}
					}
				}
				
			}
			
			
		
			
			
			public void SenarioDistancebordeauxTest() throws Exception {
				
				String mapName = "C:\\Users\\Administrateur\\OneDrive\\Bureau\\BE_Graphe_Elouali\\cartes\\bordeaux.mapgr";
				
				DijkstraTest test = new  DijkstraTest();
				int origine;
				int destination;

				System.out.println("******************************************************");
				System.out.println("*     Test de validité d'Algo sur une carte           *");
				System.out.println("*                  Carte : INSA                      *");
				System.out.println("*           Mode de Test : distance                     *");
				System.out.println("******************************************************");
				System.out.println();
				
				
				System.out.println("***************** Cas d'un chemin nul *****************");
				origine = 0 ;
				destination = 0;
				test.testScenario(mapName, 1,origine,destination);    // mode 1 :distance 
				
				System.out.println("*****************  Cas d'un chemin simple ***************** ");
				origine = 1464;
				destination = 7559;
				test.testScenario(mapName, 1,origine,destination);  
				
				System.out.println("*****************  Cas d'un chemin origine inexistant ***************** ");
				origine = -1 ;
				destination = 7559;
				test.testScenario(mapName, 1,origine,destination);  
				
				System.out.println("***************** Cas d'un chemin destination inexistant ******************** ");
				origine =  36000;
				destination = 700000;
				test.testScenario(mapName, 1,origine,destination);  
			}
			





			
			public void SenarioTempsbordeauxTest() throws Exception {


				DijkstraTest test = new  DijkstraTest();

				String mapName = "C:\\Users\\Administrateur\\OneDrive\\Bureau\\BE_Graphe_Elouali\\cartes\\bordeaux.mapgr";	
				int origine;
				int destination;
				
				System.out.println("******************************************************");
				System.out.println("*     Test de validité d'Algo sur une carte           *");
				System.out.println("*                  Carte : INSA                      *");
				System.out.println("*           Mode de Test : Temps                      *");
				System.out.println("******************************************************");
				System.out.println();
				
				
				System.out.println("***************** Cas d'un chemin nul *****************");
				origine = 0 ;
				destination = 0;
				test.testScenario(mapName, 0,origine,destination);    // mode 1 :distance 
				
				System.out.println("*****************  Cas d'un chemin simple ***************** ");
				origine = 14646;
				destination = 7559;
				test.testScenario(mapName, 0,origine,destination);  
				
				System.out.println("*****************  Cas d'un chemin origine inexistant ***************** ");
				origine = -1 ;
				destination = 7559;
				test.testScenario(mapName, 0,origine,destination);  
				
				System.out.println("----- Cas d'un chemin destination inexistant ------");
				origine =  36000;
				destination = 700000;
				test.testScenario(mapName, 0,origine,destination);  
			}


			
			public static void main(String[] args) throws Exception {
				DijkstraTest dijkstraTest = new DijkstraTest();
				dijkstraTest.SenarioDistancebordeauxTest();
				//dijkstraTest.SenarioTempsbordeauxTest();
			}
	}
