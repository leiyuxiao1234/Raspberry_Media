package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Client;
import DTO.Document;
import DTO.DocumentRent;



/**
 * @author leiyuxiao
 *
 */
public class ManagerDAO {

	
		/**
		 * Paramètres de connexion à la base de données
		 */
		final static String URL = "jdbc:mysql://localhost/raspberry?serverTimezone=Europe/Paris";
		final static String LOGIN="root";
		final static String PASS="root";
		/**
		 * singleton attribut permettant de mettre en oeuvre le design pattern singleton
		 */
		private static ManagerDAO singleton;
		
		/**
		 * Constructeur (privé) de la classe
		 * Privé car utilisation du design pattern singleton
		 */
		private ManagerDAO()
		{
			// chargement du pilote Mysql
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e2) {
				System.out.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier mysql-connector-java-XXXX.jar dans le projet");
			}
			
		}
		/**
		 * Permet de récupérer l'instance unique de la classe ManagerDAO
		 * cf design pattern singleton
		 * @return
		 */
		public static ManagerDAO getInstance()
		{
			if(ManagerDAO.singleton==null)
				singleton=new ManagerDAO();
			return singleton;
		}
		
		
		/**
		 * Permet de récupérer la liste de clients
		 * @return la liste de clients
		 */
		public ArrayList<Client> listClient()
		{
			
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
		
			//connexion a la base de données
			try {
				ArrayList<Client> l=new ArrayList<Client>();
				
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				String query="SELECT * FROM client_";
				ps = con.prepareStatement(query);
				//on execute la requete 
				rs=ps.executeQuery();
				//on parcourt les lignes du resultat
				while(rs.next()) {
					Client c=new Client();
					c.setUserId(rs.getString("userId"));
					c.setUserName(rs.getString("userName"));
					c.setType(rs.getString("type"));
					l.add(c);
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
		
		
		/**
		 * Permet de ajouter un client
		 */
		public void addClient(Client client)
		{
			
			Connection con = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			
			//connexion a la base de données
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				
				ArrayList<Client> l=this.listClient();
				for(Client c:l) {
					if(c.getUserId().toUpperCase().equals(client.getUserId().toUpperCase())) {
						System.out.println("La carte est déjà inscrite!");
						return;
					}
				}
				
				con.setAutoCommit(false);
				String query1="INSERT INTO client_ (userId,userName,type) VALUES (?,?,?)";
				ps1 = con.prepareStatement(query1);
				ps1.setString(1, client.getUserId());
				ps1.setString(2, client.getUserName());
				ps1.setString(3, client.getType());
				//on execute la requete 
				ps1.executeUpdate();
				
				//on parcourt les lignes du resultat
				con.commit();
				
				System.out.println("Enregistré!\n");
			} catch (Exception ee) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ee.printStackTrace();
			} finally {
				//fermeture du rs,preparedStatement et de la connexion
				try {if (ps1 != null)ps1.close();} catch (Exception t) {}
				try {if (ps2 != null)ps2.close();} catch (Exception t) {}
				try {if (con != null)con.close();} catch (Exception t) {}
				
			}
		
		}
		
		
		/**
		 * Permet de modifier un client
		 */
		public void modifyClient(Client client)
		{
			
			Connection con = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			
			//connexion a la base de données
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				
				ArrayList<Client> l=this.listClient();
				if(!l.toString().toUpperCase().contains("USERID:"+client.getUserId().toUpperCase())) {
					System.out.println("La carte n'est pas encore inscrite!");
					return;
				}
				
				con.setAutoCommit(false);
				String query1="UPDATE client_ SET userName=? WHERE userId=?";
				ps1 = con.prepareStatement(query1);
				ps1.setString(1, client.getUserName());
				ps1.setString(2, client.getUserId());
				//on execute la requete 
				ps1.executeUpdate();
				
				//on parcourt les lignes du resultat
				con.commit();
				
				System.out.println("Modifié!\n");
			} catch (Exception ee) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ee.printStackTrace();
			} finally {
				//fermeture du rs,preparedStatement et de la connexion
				try {if (ps1 != null)ps1.close();} catch (Exception t) {}
				try {if (ps2 != null)ps2.close();} catch (Exception t) {}
				try {if (con != null)con.close();} catch (Exception t) {}
				
			}
		
		}
		
		
		/**
		 * Permet de supprimer un client
		 */
		public void deleteClient(String cardId)
		{
			
			Connection con = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			
			//connexion a la base de données
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				
				ArrayList<Client> l=this.listClient();
				if(!l.toString().toUpperCase().contains("USERID:"+cardId.toUpperCase())) {
					System.out.println("La carte n'est pas encore inscrite!");
					return;
				}
				
				con.setAutoCommit(false);
				String query1="DELETE FROM client_ WHERE userId=?";
				ps1 = con.prepareStatement(query1);
				ps1.setString(1, cardId);
				//on execute la requete 
				ps1.executeUpdate();
				
				//on parcourt les lignes du resultat
				con.commit();
				
				System.out.println("Supprimé!\n");
			} catch (Exception ee) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ee.printStackTrace();
			} finally {
				//fermeture du rs,preparedStatement et de la connexion
				try {if (ps1 != null)ps1.close();} catch (Exception t) {}
				try {if (ps2 != null)ps2.close();} catch (Exception t) {}
				try {if (con != null)con.close();} catch (Exception t) {}
				
			}
		
		}
		
		
		
		
		/**
		 * Permet de récupérer la liste de documents
		 * @return la liste de documents
		 */
		public ArrayList<Document> listDocument()
		{
			
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
		
			//connexion a la base de données
			try {
				ArrayList<Document> l=new ArrayList<Document>();
				
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				String query="SELECT * FROM document_";
				ps = con.prepareStatement(query);
				//on execute la requete 
				rs=ps.executeQuery();
				//on parcourt les lignes du resultat
				while(rs.next()) {
					Document d=new Document();
					d.setName(rs.getString("name"));
					d.setAvailable(rs.getBoolean("isAvailable"));
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

		
		/**
		 * Permet de ajouter un document
		 */
		public void addDocument(String documentName)
		{
			
			Connection con = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			
			//connexion a la base de données
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				
				ArrayList<Document> l=this.listDocument();
				for(Document d:l) {
					if(d.getName().toUpperCase().equals(documentName.toUpperCase())) {
						System.out.println("Le document est déjà enregistré!");
						return;
					}
				}
				
				con.setAutoCommit(false);
				String query1="INSERT INTO document_ (name) VALUES (?)";
				ps1 = con.prepareStatement(query1);
				ps1.setString(1, documentName);
				//on execute la requete 
				ps1.executeUpdate();
				
				//on parcourt les lignes du resultat
				con.commit();
				
				System.out.println("Enregistré!\n");
			} catch (Exception ee) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ee.printStackTrace();
			} finally {
				//fermeture du rs,preparedStatement et de la connexion
				try {if (ps1 != null)ps1.close();} catch (Exception t) {}
				try {if (ps2 != null)ps2.close();} catch (Exception t) {}
				try {if (con != null)con.close();} catch (Exception t) {}
				
			}
		
		}
		
		
		/**
		 * Permet de modifier un document
		 */
		public void modifyDocument(String documentName,String documentNameNew)
		{
			
			Connection con = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			
			//connexion a la base de données
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				
				ArrayList<Document> l=this.listDocument();
				for(Document d:l) {
					if(d.getName().equals(documentName)) {
						con.setAutoCommit(false);
						String query1="UPDATE document_ SET name=? WHERE name=?";
						ps1 = con.prepareStatement(query1);
						ps1.setString(1, documentNameNew);
						ps1.setString(2, documentName);
						//on execute la requete 
						ps1.executeUpdate();
						
						//on parcourt les lignes du resultat
						con.commit();
						
						System.out.println("Modifié!\n");
						return;
					}
				}
				
				System.out.println("Le document n'existe pas!");
				
			} catch (Exception ee) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ee.printStackTrace();
			} finally {
				//fermeture du rs,preparedStatement et de la connexion
				try {if (ps1 != null)ps1.close();} catch (Exception t) {}
				try {if (ps2 != null)ps2.close();} catch (Exception t) {}
				try {if (con != null)con.close();} catch (Exception t) {}
				
			}
		
		}
		
		
		/**
		 * Permet de supprimer un document
		 */
		public void deleteDocument(String documentName)
		{
			
			Connection con = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			
			//connexion a la base de données
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				
				ArrayList<Document> l=this.listDocument();
				for(Document d:l) {
					if(d.getName().equals(documentName)) {
						con.setAutoCommit(false);
						String query1="DELETE FROM document_ WHERE name=?";
						ps1 = con.prepareStatement(query1);
						ps1.setString(1, documentName);
						//on execute la requete 
						ps1.executeUpdate();
						
						//on parcourt les lignes du resultat
						con.commit();
						
						System.out.println("Supprimé!\n");
						return;
					}
				}
				
				System.out.println("Le document n'existe pas!");
				
			} catch (Exception ee) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ee.printStackTrace();
			} finally {
				//fermeture du rs,preparedStatement et de la connexion
				try {if (ps1 != null)ps1.close();} catch (Exception t) {}
				try {if (ps2 != null)ps2.close();} catch (Exception t) {}
				try {if (con != null)con.close();} catch (Exception t) {}
				
			}
		
		}
		
		
		
		
		/**
		 * Permet de récupérer la liste de documents empruntés
		 * @return la liste de documents empruntés
		 */
		public ArrayList<DocumentRent> listDocumentRent()
		{
			
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
		
			//connexion a la base de données
			try {
				ArrayList<DocumentRent> l=new ArrayList<DocumentRent>();
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				String query="SELECT * FROM document_rent_";
				ps = con.prepareStatement(query);
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
		
		
		/**
		 * Permet de ajouter un emprunt
		 */
		public void addDocumentRent(DocumentRent documentRent)
		{
			
			Connection con = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			
			//connexion a la base de données
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				
				ArrayList<Document> ld=this.listDocument();
				for(Document d:ld) {
					if(d.getName().equals(documentRent.getName())) {
						if(!d.isAvailable()) {
							System.out.println("Le document n'est pas en stock!");
							return;
						}
						else {
							con.setAutoCommit(false);
							String query1="INSERT INTO document_rent_ (name,userId) VALUES (?,?)";
							ps1 = con.prepareStatement(query1);
							ps1.setString(1, documentRent.getName());
							ps1.setString(2, documentRent.getUserId());
							//on execute la requete 
							ps1.executeUpdate();
							
							String query2="UPDATE document_ SET isAvailable=0 WHERE name=?";
							ps2 = con.prepareStatement(query2);
							ps2.setString(1, documentRent.getName());
							//on execute la requete 
							ps2.executeUpdate();
							
							//on parcourt les lignes du resultat
							con.commit();
							
							System.out.println("Enregistré!\n");
							return;
						}
					}
				}
				System.out.println("Le document n'est pas en stock!");
				
			} catch (Exception ee) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ee.printStackTrace();
			} finally {
				//fermeture du rs,preparedStatement et de la connexion
				try {if (ps1 != null)ps1.close();} catch (Exception t) {}
				try {if (ps2 != null)ps2.close();} catch (Exception t) {}
				try {if (con != null)con.close();} catch (Exception t) {}
				
			}
		
		}
		
		
		/**
		 * Permet de retourner un emprunt
		 */
		public void returnDocumentRent(DocumentRent documentRent)
		{
			
			Connection con = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			PreparedStatement ps3 = null;
			//connexion a la base de données
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				
				ArrayList<DocumentRent> ld=this.listDocumentRent();
				for(DocumentRent d:ld) {
					if(d.getName().equals(documentRent.getName()) && d.getUserId().equals(documentRent.getUserId())) {
						con.setAutoCommit(false);
						
						String query1="DELETE FROM document_rent_ WHERE name=? AND userId=?";
						ps1 = con.prepareStatement(query1);
						ps1.setString(1, documentRent.getName());
						ps1.setString(2, documentRent.getUserId());
						//on execute la requete 
						ps1.executeUpdate();
						
						String query2="INSERT INTO document_return_ (name,userId) VALUES (?,?)";
						ps2 = con.prepareStatement(query2);
						ps2.setString(1, documentRent.getName());
						ps2.setString(2, documentRent.getUserId());
						//on execute la requete 
						ps2.executeUpdate();

						String query3="UPDATE document_ SET isAvailable=1 WHERE name=?";
						ps3 = con.prepareStatement(query3);
						ps3.setString(1, documentRent.getName());
						//on execute la requete 
						ps3.executeUpdate();
						
						
						//on parcourt les lignes du resultat
						con.commit();
						
						System.out.println("Enregistré!\n");
						return;
					}
				}
				System.out.println("Le document n'est pas emprunté ou n'existe pas!");
			} catch (Exception ee) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ee.printStackTrace();
			} finally {
				//fermeture du rs,preparedStatement et de la connexion
				try {if (ps1 != null)ps1.close();} catch (Exception t) {}
				try {if (ps2 != null)ps2.close();} catch (Exception t) {}
				try {if (ps3 != null)ps3.close();} catch (Exception t) {}
				try {if (con != null)con.close();} catch (Exception t) {}
				
			}
		
		}


}
