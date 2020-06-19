package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	private SightingsDAO dao ;
	private Graph<String, DefaultEdge> grafo;
	private List <String> visita ;
	
	public Model () {
		this.dao = new SightingsDAO ();
	}
	
	public List<Avvistamenti> getAnno(){
		List<Avvistamenti> res = new ArrayList<>();
		res = this.dao.getTendina();
		
		Collections.sort(res);
		return res;
	}
	
	public void creaGrafo(Avvistamenti a) {
		this.grafo = new SimpleDirectedGraph<>(DefaultEdge.class);
		
		List <String> vertici = this.dao.getVertex(a.getYear());
		Graphs.addAllVertices(this.grafo, vertici);
		
		
		for (Arco s : this.dao.getArchi(a.getYear())) {
			if (vertici.contains(s.getS1()) && vertici.contains(s.getS2())) {
				this.grafo.addEdge(s.getS1(), s.getS2());
			}
		}
		
		/*for (String s1 : this.grafo.vertexSet()) {
			for (String s2 : this.grafo.vertexSet()) {
				if (!s1.equals(s2)) {
					//nel dao ho un metodo che mi ritorna un booleano, se tra i due vertici c'è un aroc VERO
					if (this.dao.getArchi(s1,s2, a.getYear())==true) {
						this.grafo.addEdge(s1, s2);
					}
				}
			}
		}*/
	}
	
	public int getVertexNumber() {
		return this.grafo.vertexSet().size();
	}
	public int getEdgeNumber() {
		return this.grafo.edgeSet().size();
	}
	public Set<String> getVertex(){
		return this.grafo.vertexSet();
	}
	
	public List<String> precedenti(String stato){
		List<String> precedenti = Graphs.predecessorListOf(this.grafo, stato);
		
		return precedenti;
	}
	public List<String> successori (String stato){
		List<String> successori = Graphs.successorListOf(this.grafo, stato);
		
		return successori;
	}
	
	public List<String> raggiungibili (String stato){
		visita = new ArrayList<>();
		BreadthFirstIterator <String, DefaultEdge> bfv = new BreadthFirstIterator <>(this.grafo, stato);
		
		while (bfv.hasNext()) {
			visita.add(bfv.next());
		}
		Collections.sort(visita);
		return visita;
	}
	public int visitaSize() {
		return this.visita.size();
	}
	
	
}
