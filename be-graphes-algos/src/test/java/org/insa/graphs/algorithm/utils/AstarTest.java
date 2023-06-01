package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Test;


public class AstarTest {
	public void testScenario(String mapName, int Mode) throws Exception {
		// Create a graph reader.
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
	
		// Read the graph.
		Graph graph = reader.read();
	
		System.out.println("Size graph = " + graph.size());
	
		// les modes : 0 pour distance & 1 pour temps
		if (Mode != 0 && Mode != 1) {
			System.out.println("les argument invalide");
		} else {
			ArcInspector arcInspectorAstar;
	
			if (Mode == 0) { // Temps
				System.out.println("le Mode : Temps");
				arcInspectorAstar = ArcInspectorFactory.getAllFilters().get(2);
			} else {
				System.out.println("le Mode : Distance");
				arcInspectorAstar = ArcInspectorFactory.getAllFilters().get(0);
			}
	
			System.out.println("--> Chemin de la carte : " + mapName);
	
			for (int i = 0; i < 1; i++) {
				// Génération aléatoire de l'origine et de la destination
				int origine = (int) (Math.random() * graph.size());
				int destination = (int) (Math.random() * graph.size());
	
				System.out.println("--> Origine : " + origine);
				System.out.println("--> Destination : " + destination);
	
				if (origine == destination) {
					System.out.println("\nERREUR : Origine et Destination identiques");
					System.out.println("Cout solution = 0");
				} else {
					ShortestPathData data = new ShortestPathData(graph, graph.get(origine), graph.get(destination),
							arcInspectorAstar);
	
					BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
					AStarAlgorithm A = new AStarAlgorithm(data);
	
					// Recuperation des solutions de Bellman et Dijkstra
					ShortestPathSolution solution = A.run();
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
						if (Mode == 0) { // Temps
							// le cout de la solution
							costSolution = solution.getPath().getMinimumTravelTime();
							costattendu = attendu.getPath().getMinimumTravelTime();
						} else { // distance
							costSolution = solution.getPath().getLength();
							costattendu = attendu.getPath().getLength();
						}
						//assertEquals(costattendu, costSolution, 0.001);
						

						System.out.println("\nLe Cout attendu =      " + costattendu +"\t"+ origine + "\t" + destination);
						System.out.println("Le Cout de la solution = " + costSolution +"\t"+ origine + "\t" + destination);
					}
				}
			}
		}
	}
			public void testScenario2(String mapName, int Mode, int origine, int destination) throws Exception {

				// Create a graph reader.
				GraphReader reader = new BinaryGraphReader(
						new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

				// Read the graph.
				Graph graph = reader.read();


				System.out.println("Size graph = "+graph.size());


				//les modes : 0 pour distance & 1 pour temps 
				if (Mode!=0 && Mode!=1) {
					System.out.println("les argument invalide");
				} else {
					   // On est hors du graphe. / Sommets inexistants
					if (origine<0 || destination<0 || origine>=(graph.size()-1) || destination>=(graph.size()-1)) { 
						System.out.println("ERREUR : Paramètres invalides ");
						
					} else {
						ArcInspector arcInspectorAstar;
						
						if (Mode == 0) { //Temps
							System.out.println("le Mode : Temps");
							arcInspectorAstar = ArcInspectorFactory.getAllFilters().get(2);
						} else {
							System.out.println("le Mode : Distance");
							arcInspectorAstar = ArcInspectorFactory.getAllFilters().get(0);
						}
						
						
						System.out.println("--> Chemin de la carte : "+mapName);
						System.out.println("--> Origine : " + origine);
						System.out.println("--> Destination : " + destination);
						
						if(origine==destination) {
							System.out.println("\n ERREUR :Origine et Destination identiques");
							System.out.println("Cout solution = 0");
							
						} else {			
							ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorAstar);
				
							BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
							AStarAlgorithm A = new AStarAlgorithm(data);
							
							// Recuperation des solutions de Bellman et Dijkstra 
							ShortestPathSolution solution = A.run();
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


								System.out.println("\n Le Cout attendu =      " + costattendu );
								System.out.println(" Le Cout de la solution = " + costSolution );

							}
						}
					}
				}
				
			}
			@Test
			public void Test() throws Exception {


				String mapName = "C:\\Users\\Administrateur\\OneDrive\\Bureau\\BE_Graphe_Elouali\\cartes\\INSA.mapgr";
				AstarTest test = new  AstarTest();
				System.out.println("\n *****************  Cas d'un chemin simple *****************  \n ");

				test.testScenario(mapName, 1); ///Temps
				System.out.println("Astar - Temps ");

				//test.testScenario(mapName, 0); //distance
				//System.out.println("Astar - distance ");


			
			}
			@Test
			public void SenarioDistancebordeauxTest() throws Exception {
				
				String mapName = "C:\\Users\\Administrateur\\OneDrive\\Bureau\\BE_Graphe_Elouali\\cartes\\bordeaux.mapgr";
				
				AstarTest test = new  AstarTest();
				int origine;
				int destination;

				System.out.println("******************************************************");
				System.out.println("*     Test de validité d'Algo sur une carte           *");
				System.out.println("*                  Carte : INSA                      *");
				System.out.println("*           Mode de Test : distance                     *");
				System.out.println("******************************************************");
				System.out.println();
				
				System.out.println("\n *****************  Cas d'un chemin simple *****************  \n ");
				origine = 1464;
				destination = 7559;
				test.testScenario2(mapName, 1,origine,destination); 

				System.out.println("\n ***************** Cas d'un chemin nul *****************\n ");
				origine = 0 ;
				destination = 0;
				test.testScenario2(mapName, 1,origine,destination);    // mode 1 :distance 
				
				 
				
				System.out.println("\n *****************  Cas d'un chemin origine inexistant ***************** \n ");
				origine = -1 ;
				destination = 7559;
				test.testScenario2(mapName, 1,origine,destination);  
				
				System.out.println("\n ***************** Cas d'un chemin destination inexistant ******************** \n ");
				origine =  36000;
				destination = 700000;
				test.testScenario2(mapName, 1,origine,destination);  
			}
			





			@Test
			public void SenarioTempsbordeauxTest() throws Exception {


				AstarTest test = new  AstarTest();

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
				test.testScenario2(mapName, 0,origine,destination);    // mode 1 :distance 
				
				System.out.println("*****************  Cas d'un chemin simple ***************** ");
				origine = 14646;
				destination = 14645;
				test.testScenario2(mapName, 0,origine,destination);  
				
				System.out.println("*****************  Cas d'un chemin origine inexistant ***************** ");
				origine = -1 ;
				destination = 7559;
				test.testScenario2(mapName, 0,origine,destination);  
				
				System.out.println("----- Cas d'un chemin destination inexistant ------");
				origine =  36000;
				destination = 700000;
				test.testScenario2(mapName, 0,origine,destination);  
			}


			
			public static void main(String[] args) throws Exception {
				AstarTest Astartest = new  AstarTest();
				//Astartest.SenarioDistancebordeauxTest();
				//Astartest.SenarioTempsbordeauxTest();
				Astartest.Test();

			}
	}
