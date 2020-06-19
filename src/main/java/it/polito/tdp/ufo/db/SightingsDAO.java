package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.Arco;
import it.polito.tdp.ufo.model.Avvistamenti;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List <Avvistamenti> getTendina (){
		String sql = "select Year(datetime) as anno, count(*) as n_avvistamente " + 
				"from sighting " + 
				"where country='us' "+
				"group by Year(datetime)";
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			List<Avvistamenti> list = new ArrayList<>() ;
			ResultSet res = st.executeQuery() ;
			
			while (res.next()) {	
				Avvistamenti s = new Avvistamenti (res.getInt("n_avvistamente"),res.getInt("anno"));
				list.add(s);
			}
			conn.close();
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
	}
	
	public List <String> getVertex(int anno){
		String sql = "select distinct state, count(*) " + 
				"from sighting " +
				"where year(datetime)=? " + 
				"and country = 'us' " +
				"group by state";
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			List<String> list = new ArrayList<>() ;
			ResultSet res = st.executeQuery() ;
			
			while (res.next()) {	
				String s = res.getString("state");
				list.add(s);
			}
			conn.close();
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Arco> getArchi(Integer anno) {
		String sql = "select s1.state as s1, s2.state as s2 " + 
				"from sighting s1, sighting s2 " + 
				"where year(s1.datetime) = year (s2.datetime) " + 
				"and year (s2.datetime) = ? " + 
				"and s1.country = \"us\" and s2.country = \"us\" " + 
				"and s2.datetime>s1.datetime " + 
				"and s1.state>s2.state"; 
				
				
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			List<Arco> archi = new ArrayList<>();
			ResultSet res = st.executeQuery() ;
			
			while (res.next()) {
				Arco a = new Arco (res.getString("s1"), res.getString("s2"));
				archi.add(a);
			}
			conn.close();
			return archi;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

}
