package media;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import DAO.ClientDAO;
import DAO.ManagerDAO;
import DTO.Client;
import DTO.Document;
import DTO.DocumentRent;

public class media {
	
	private static boolean exit=false;
	private static boolean exitBlock=false;
	private static String userId;
	
	public static void main(String args[]) throws IOException, InterruptedException {
		
		String select;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Scanner la carte,svp.");
		////////////////////////////////////////////
//		userId = in.nextLine().trim();
		userId = readCard();
		while(!ClientDAO.getInstance().verifyConnexion(userId)) {
			System.out.println("Identification Non Réussie\n");
			System.out.println("Scanner la carte,svp.");
			////////////////////////////////////////////
//			userId = in.nextLine().trim();
			userId = readCard();
		}
		System.out.println("Bienvenue!: "+ClientDAO.getInstance().getName(userId));
		
		if(ClientDAO.getInstance().getType(userId).equals("client")) {
			ArrayList<DocumentRent> l=ClientDAO.getInstance().listDocumentRent(userId);
			if(l!=null) {
				System.out.println("La liste de documents:");
				for(DocumentRent d:l) {
					System.out.println(d);
				}
			}
		}
		else if(ClientDAO.getInstance().getType(userId).equals("manager")) {
			while(!exit) {
				exitBlock=false;
				System.out.println("Menu:");
				System.out.println("1.Gérer les emprunts");
				System.out.println("2.Gérer les comptes");
				System.out.println("3.Gérer les documents");
				System.out.println("4.Terminer");
				System.out.println("Entrez le choix:\n");
				select=in.nextLine().trim();
				switch(select) {
					//GERER LES EMPRUNTS
					case "1":{
						while(!exitBlock) {
							System.out.println("1.Louer");
							System.out.println("2.Retourner");
							System.out.println("3.Afficher tous les emprunts");
							System.out.println("4.Menu");
							System.out.println("Entrez le choix:\n");
							select=in.nextLine();
							switch(select) {
							case "1":{
								System.out.println("Entrez le nom de document");
								String documentName=in.nextLine().trim();
								if(!ManagerDAO.getInstance().listDocument().toString().toUpperCase().contains(documentName.toUpperCase()) || documentName.equals("")) {
									System.out.println("Le document n'existe pas!");
								}
								else {
									System.out.println("Scanner la carte,svp.");
									//////////////////////////////////////////////
//									String cardId=in.nextLine().trim();
									String cardId = readCard();
									DocumentRent documentRent=new DocumentRent();
									documentRent.setName(documentName);
									documentRent.setUserId(cardId);
									ManagerDAO.getInstance().addDocumentRent(documentRent);
								}
								break;	
							}
							case "2":{
								System.out.println("Entrez le nom de document");
								String documentName=in.nextLine().trim();
								System.out.println("Scanner la carte,svp.");
								//////////////////////////////////////////////
//									String cardId = in.nextLine().trim();
								String cardId=readCard();
								DocumentRent documentRent=new DocumentRent();
								documentRent.setName(documentName);
								documentRent.setUserId(cardId);
								ManagerDAO.getInstance().returnDocumentRent(documentRent);
								break;
							}
							case "3":{
								ArrayList<DocumentRent> l = ManagerDAO.getInstance().listDocumentRent();
								if(l!=null) {
									System.out.println("La liste de documents:");
									for(DocumentRent d:l) {
										System.out.println(d);
									}
								}
								break;
							}
							case "4":{
								exitBlock=true;
								break;
							}
							default:{
								System.out.println("Erreur de choix");
							}
							break;
							}
						}
						break;	
					}
					
					//GERER LES COMPTES
					case "2":{
						while(!exitBlock) {
							System.out.println("1.Créer un compte");
							System.out.println("2.Modifier un compte");
							System.out.println("3.Supprimer un compte");
							System.out.println("4.Menu");
							System.out.println("Entrez le choix:\n");
							select=in.nextLine();
							switch(select) {
							case "1":{
								System.out.println("Scanner la carte,svp.");
								/////////////////////////////////////////////
//								String cardId=in.nextLine().trim();
								String cardId=readCard();
								System.out.println("Entrez le nom,svp.");
								String userName=in.nextLine().trim();
								if(userName.equals("")) {
									System.out.println("Le nom ne peut pas être vide!");
								}
								else {
										Client client=new Client();
										client.setUserName(userName);
										client.setUserId(cardId);
										client.setType("client");
										ManagerDAO.getInstance().addClient(client);
								}
								
								break;	
							}
							case "2":{
								System.out.println("Scanner la carte,svp.");
								/////////////////////////////////////////////
//								String cardId=in.nextLine().trim();
								String cardId = readCard();
								System.out.println("Entrez un nouveau nom");
								String userName=in.nextLine().trim();
								Client client=new Client();
								client.setUserName(userName);
								client.setUserId(cardId);
								client.setType("client");
								ManagerDAO.getInstance().modifyClient(client);
								break;
							}
							case "3":{
								System.out.println("Scanner la carte,svp.");
								/////////////////////////////////////////////
//								String cardId=in.nextLine().trim();
								String cardId = readCard();
								ManagerDAO.getInstance().deleteClient(cardId);
								break;
							}
							case "4":{
								exitBlock=true;
								break;
							}
							default:{
								System.out.println("Erreur de choix");
							}
							break;
							}
						}
						break;
					}
					
					//GERER LES DOCUMENTS
					case "3":{
						while(!exitBlock) {
							System.out.println("1.Créer un document");
							System.out.println("2.Modifier un document");
							System.out.println("3.Supprimer un document");
							System.out.println("4.Afficher les documents");
							System.out.println("5.Menu");
							System.out.println("Entrez le choix:\n");
							select=in.nextLine();
							switch(select) {
							case "1":{
								System.out.println("Entrez le nom de document,svp.");
								String documentName = in.nextLine().trim();
								if(documentName.equals("")) {
									System.out.println("Le nom ne peut pas être vide!");
								}
								else {
										ManagerDAO.getInstance().addDocument(documentName);
								}
								
								break;	
							}
							case "2":{
								System.out.println("Entrez le nom du document");
								String documentName=in.nextLine().trim();
								System.out.println("Entrez le nouveau nom du document");
								String documentNameNew=in.nextLine().trim();
								ManagerDAO.getInstance().modifyDocument(documentName,documentNameNew);
								break;
							}
							case "3":{
								System.out.println("Entrez le nom du document");
								String documentName=in.nextLine().trim();
								ManagerDAO.getInstance().deleteDocument(documentName);
								break;
							}
							case "4":{
								ArrayList<Document> l = ManagerDAO.getInstance().listDocument();
								if(l!=null) {
									System.out.println("La liste de documents:");
									for(Document d:l) {
										System.out.println(d);
									}
								}
								break;
							}
							case "5":{
								exitBlock=true;
								break;
							}
							default:{
								System.out.println("Erreur de choix");
							}
							break;
							}
						}
						break;
					}
					
					//QUITTER
					case "4":{
						exit=true;
						break;
					}
					
					//ERREUR SAISIE
					default:{
						System.out.println("ERREUR SAISIE");
						break;
					}
				}
			}
		}
		else {
			System.out.println("Erreur de carte");
		}
		

		in.close();
		
	}
	
	private static String readCard() throws IOException, InterruptedException {
		String shpath="/home/pi/scripts/reader";
	    Process ps = Runtime.getRuntime().exec(shpath);
	    ps.waitFor();

		BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line;
		if ((line = br.readLine()) != null) {
			sb.append(line);
		}
		ps.destroy();
		return line;
	}
}
