package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.DocumentRent;



/**
 * @author leiyuxiao
 *
 */
public class ClientDAO {

	
		/**
		 * Paramètres de connexion à la base de données
		 */
		final static String URL = "jdbc:mysql://localhost/raspberry?serverTimezone=Europe/Paris";
		final static String LOGIN="root";
		final static String PASS="root";
		/**
		 * singleton attribut permettant de mettre en oeuvre le design pattern singleton
		 */
		private static ClientDAO singleton;
		
		/**
		 * Constructeur (privé) de la classe
		 * Privé car utilisation du design pattern singleton
		 */
		private ClientDAO()
		{
			// chargement du pilote Mysql
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e2) {
				System.out.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier mysql-connector-java-XXXX.jar dans le projet");
			}
			
		}
		/**
		 * Permet de récupérer l'instance unique de la classe ClientDAO
		 * cf design pattern singleton
		 * @return
		 */
		public static ClientDAO getInstance()
		{
			if(ClientDAO.singleton==null)
				singleton=new ClientDAO();
			return singleton;
		}
		
		
		
		/**
		 * Permet de vérifier le compte et le mot de passe
		 * @return le résultat de vérification
		 */
		public boolean verifyConnexion(String userId)
		{
			
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
		
			//connexion a la base de données
			try {
				
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				String query="SELECT * FROM client_ WHERE userId = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, userId);
				//on execute la requete 
				rs=ps.executeQuery();
				//on parcourt les lignes du resultat
				if(rs.next())
					return true;
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				//fermeture du rs,preparedStatement et de la connexion
				try {if (rs != null)rs.close();} catch (Exception t) {}
				try {if (ps != null)ps.close();} catch (Exception t) {}
				try {if (con != null)con.close();} catch (Exception t) {}
			}
			return false;
		
		}
		
		
		/**
		 * Permet de récupérer le type de compte
		 * @return le type de compte
		 */
		public String getType(String userId)
		{
			
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
		
			//connexion a la base de données
			try {
				
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				String query="SELECT * FROM client_ WHERE userId = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, userId);
				//on execute la requete 
				rs=ps.executeQuery();
				//on parcourt les lignes du resultat
				if(rs.next())
					return rs.getString("type");
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				//fermeture du rs,preparedStatement et de la connexion
				try {if (rs != null)rs.close();} catch (Exception t) {}
				try {if (ps != null)ps.close();} catch (Exception t) {}
				try {if (con != null)con.close();} catch (Exception t) {}
			}
			return null;
		
		}
		
		
		
		/**
		 * Permet de récupérer le nom de l'utilisateur
		 * @return le nom
		 */
		public String getName(String userId)
		{
			
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
		
			//connexion a la base de données
			try {
				
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				String query="SELECT * FROM client_ WHERE userId = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, userId);
				//on execute la requete 
				rs=ps.executeQuery();
				//on parcourt les lignes du resultat
				if(rs.next())
					return rs.getString("userName");
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				//fermeture du rs,preparedStatement et de la connexion
				try {if (rs != null)rs.close();} catch (Exception t) {}
				try {if (ps != null)ps.close();} catch (Exception t) {}
				try {if (con != null)con.close();} catch (Exception t) {}
			}
			return "unknown";
		
		}
		
		/**
		 * Permet de récupérer la liste de documents empruntés
		 * @return la liste de documents
		 */
		public ArrayList<DocumentRent> listDocumentRent(String userId)
		{
			
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
		
			//connexion a la base de données
			try {
				ArrayList<DocumentRent> l=new ArrayList<DocumentRent>();
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				String query="SELECT * FROM document_rent_ WHERE userId = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, userId);
				//on execute la requete 
				rs=ps.executeQuery();
				//on parcourt les lignes du resultat
				while(rs.next()) {
					DocumentRent d=new DocumentRent();
					d.setName(rs.getString("name"));
					d.setDate(rs.getTimestamp("date"));
					d.setUserId(rs.getString("userId"));
					l.add(d);
				}
				return l;
					
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				//fermeture du rs,preparedStatement et de la connexion
				try {if (rs != null)rs.close();} catch (Exception t) {}
				try {if (ps != null)ps.close();} catch (Exception t) {}
				try {if (con != null)con.close();} catch (Exception t) {}
			}
			return null;
		
		}


}
