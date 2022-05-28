package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import Biblio.SysCreator;
import fileVisitor.ShowVisitor;
import fileVisitor.Visitor;
import sysFileBusiness.StdDir;
import sysFileBusiness.StdRegFile;
import sysFileBusiness.StdSybLink;
import sysFileBusiness.SysFile;

/**
 * Package client comprend la classe HttpTest et la classe MyHandler implementant l'interface HttpHandler
 * @author steve et arezki
 *
 */


public class HttpTest {
	
	/**
	 * La methode main est le  point d'entrée de l'application cliente
	 * @param args est un tableau contenant les chaine de caractere passe en argument au démarrage du programme principale
	 * @throws Exception est l'erreur generé au demarrage du serveur ou durant son execution si une des configurations ne fonctionne pas correctement notamment un port deja utilisé
	 */
	
	public static void main(String[] args) throws Exception {
		
		if(args[0] != null) {
			SysCreator stdSysCreator = Biblio.StdSysCreator.getInstance();
			stdSysCreator.loadRecursifDirs(args[0]);
		}
		
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        
        
    }
	
	/**
	 * MyHandler est la classe qui est chargée de traiter les requetes http. Elle implemente egalement l'interface Visitor
	 * @author steve
	 * Elle contient plusieurs methodes donc la plus importante est handler
	 */

	static class MyHandler implements HttpHandler, Visitor {
		
		/**
		 * HttpExchange reprsente les echanges avec le client web
		 */
		private static HttpExchange httpExchange;
		
		/**
		 * response la reponse renvoyée
		 */
		private static String response;
		private static String header;
		
		private static String footer = "</div>\n"
				
				+ "</body>\n"
				+ "</html>";

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			// path and query
			String path = exchange.getRequestURI().getPath();
			//String query = exchange.getRequestURI().getQuery();
			//System.out.println(path);
			
			httpExchange = exchange;
			
			String create_folder = "<a href=\"create_folder@\">Dossier</a>\n".replace("create_folder@", mYconcat(path ,"create_folder@"));
			String create_file = "<a href=\"create_file@\">Fichier</a>\n".replace("create_file@", mYconcat(path ,"create_file@"));
			String create_link = "<a href=\"create_linksyb@\">Lien</a>\n".replace("create_linksyb@", mYconcat(path ,"create_linksyb@"));
			
			
			
			
			header = "<!DOCTYPE html>\n"
					+ "<html>\n"
					+ "<head>\n"
					+ "<script src=\"https://kit.fontawesome.com/a076d05399.js\" crossorigin=\"anonymous\"></script>\n"
					+ "    <meta charset=\"UTF-8\">\n"
					+ "   \n"
					+ "    <title> Systéme de fichier </title>\n"
					+ "\n"
					+ "\n"
					+ "\n"
					+ "\n"
					+ "<style>\n"
					+ "\n"
					+ "body {\n"
					+ "   \n"
					+ "    background-color: #E2DCE2;\n"
					+ "}\n"
					+ "\n"
					+ ".icone{\n"
					+ "	margin-top:50px;\n"
					+ "}\n"
					+ "\n"
					+ "/* Add a black background color to the top navigation */\n"
					+ ".topnav {\n"
					+ "  background-color: #333;\n"
					+ "  overflow: hidden;\n"
					+ "}\n"
					+ "\n"
					+ "/* Style the links inside the navigation bar */\n"
					+ ".topnav a {\n"
					+ "  float: left;\n"
					+ "  color: #f2f2f2;\n"
					+ "  text-align: center;\n"
					+ "  padding: 14px 16px;\n"
					+ "  text-decoration: none;\n"
					+ "  font-size: 17px;\n"
					+ "}\n"
					+ "\n"
					+ "/* Change the color of links on hover */\n"
					+ ".topnav a:hover {\n"
					+ "  background-color: #ddd;\n"
					+ "  color: black;\n"
					+ "}\n"
					+ "\n"
					+ "/* Add a color to the active/current link */\n"
					+ ".topnav a.active {\n"
					+ "  background-color: #04AA6D;\n"
					+ "  color: white;\n"
					+ "\n"
					+ "}\n"
					+ "/* Responsive image */\n"
					+ ".responsive {\n"
					+ "  width: 10%;\n"
					+ "  height: auto;\n"
					+ "}\n"
					+ "</style>\n"
					+ "</head>\n"
					+ "\n"
					+ "<body>\n"
					+ "<div class=\"topnav\">\n"
					+ "  <a class=\"active\" href=\"/\">Acceuil</a>\n"
					+ create_folder
					+ create_file
					+ create_link
					+ "  <a href=\"#about\">About</a>\n"
					+ "</div>\n"
					+ "<div class=\"icone\">\n"
					;
			
			// get a  unique instance of sysfile library
			SysCreator stdSysCreator = Biblio.StdSysCreator.getInstance();
			
			if("GET".equalsIgnoreCase(httpExchange.getRequestMethod())) {
				
				// get images behind source
				if( (path.endsWith("**.png") || path.endsWith("*.png") || path.endsWith("***.png")) && path.contains("images/")) {
            		//System.out.println( path );
            		
            		String result = path.replaceFirst("/", "");
            		//System.out.println( result );
            		
            		File file = new File(result);
            		httpExchange.sendResponseHeaders(200, file.length());
            		// TODO set the Content-Type header to image/png
            		httpExchange.getResponseHeaders().add("Content-Type", "image/png");
            		 OutputStream os = httpExchange.getResponseBody();
            		 Files.copy(file.toPath(), os);
                     os.close();
            	}
				
				
				
				if(! path.endsWith("@")) {
					// try to get element behind the path
					SysFile f = stdSysCreator.getElementByPath(path);
					f.accept(this);
					
				}else {
					
					Path realPath = Paths.get(path).getParent();
					String parent = realPath.toString();
					
					String create_folder2 = "<a href=\"create_folder@\">Dossier</a>\n".replace("create_folder@", mYconcat(parent ,"create_folder@"));
					String create_file2 = "<a href=\"create_file@\">Fichier</a>\n".replace("create_file@", mYconcat(parent ,"create_file@"));
					String create_link2 = "<a href=\"create_linksyb@\">Lien</a>\n".replace("create_folder@", mYconcat(parent ,"create_linksyb@"));
					
					String create_folderActive2 = "<a class=\"active\" href=\"create_folder@\">Dossier</a>\n".replace("create_folder@", mYconcat(parent ,"create_folder@"));
					String create_fileActive2 = "<a class=\"active\" href=\"create_file@\">Fichier</a>\n".replace("create_file@", mYconcat(parent ,"create_file@"));
					String create_linkActive2 = " <a class=\"active\" href=\"create_linksyb@\">Lien</a>\n".replace("create_folder@", mYconcat(parent ,"create_linksyb@"));
					
					
					//System.out.println(parent);
					String form = "	<form action=\"#\" method=\"post\">\n".replace("#", parent);
					//System.out.println(form);
					
					//System.out.println(path);
					String encoding = "UTF-8";
					httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
					
					if(path.endsWith("create_folder@")) {
						
						response = "<!DOCTYPE html>\n"
	            				+ " <html lang=\"en\">\n"
	            				+ " <head>\n"
	            				+ "     <meta charset=\"UTF-8\">\n"
	            				+ "     <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
	            				+ "     <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
	            				+ "     <title>Créer un Dossier</title>\n"
	            				+ "     \n"
	            				+ "\n"
	            				+ " </head>\n"
	            				+ " <body>\n"
	            				+ " <style>\n"
	            				+ " /* Add a black background color to the top navigation */\n"
	            				+ ".topnav {\n"
	            				+ "  background-color: #333;\n"
	            				+ "  overflow: hidden;\n"
	            				+ "}\n"
	            				+ "\n"
	            				+ "/* Style the links inside the navigation bar */\n"
	            				+ ".topnav a {\n"
	            				+ "  float: left;\n"
	            				+ "  color: #f2f2f2;\n"
	            				+ "  text-align: center;\n"
	            				+ "  padding: 14px 16px;\n"
	            				+ "  text-decoration: none;\n"
	            				+ "  font-size: 17px;\n"
	            				+ "}\n"
	            				+ "\n"
	            				+ "/* Change the color of links on hover */\n"
	            				+ ".topnav a:hover {\n"
	            				+ "  background-color: #ddd;\n"
	            				+ "  color: black;\n"
	            				+ "}\n"
	            				+ "\n"
	            				+ "/* Add a color to the active/current link */\n"
	            				+ ".topnav a.active {\n"
	            				+ "  background-color: #04AA6D;\n"
	            				+ "  color: white;\n"
	            				+ "}\n"
	            				+ "\n"
	            				+ "\n"
	            				+ "section {\n"
	            				+ "    display: flex;\n"
	            				+ "    flex-direction: column;\n"
	            				+ "    text-align: center;\n"
	            				+ "    background-color: #fff;\n"
	            				+ "    border-radius: 20px;\n"
	            				+ "    padding: 20px;\n"
	            				+ "}\n"
	            				+ "h1 {\n"
	            				+ "   margin: 30px; \n"
	            				+ "}\n"
	            				+ "#f{\n"
	            				+ "    padding: 10px;\n"
	            				+ "    margin: 20px 0;\n"
	            				+ "    font-size: 18px;\n"
	            				+ "    border: 1px solid #5370f0;\n"
	            				+ "    border-radius: 6px;\n"
	            				+ "}\n"
	            				+ "\n"
	            				+ "textarea {\n"
	            				+ "    resize:none ;\n"
	            				+ "    padding: 10px;\n"
	            				+ "    font-size: 18px;\n"
	            				+ "    border-radius: 6px;\n"
	            				+ "    outline: 0;\n"
	            				+ "    border: 1px solid #ccc;\n"
	            				+ "}\n"
	            				+ "button {\n"
	            				+ "	width:20%;\n"
	            				+ "    margin: 20px 0;\n"
	            				+ "	margin-left:40%;\n"
	            				+ "    background-color: #5370f0;\n"
	            				+ "    color: #fff;\n"
	            				+ "    border: 1px solid transparent;\n"
	            				+ "    padding: 10px 0;\n"
	            				+ "    font-weight: bold;\n"
	            				+ "    border-radius: 6px;\n"
	            				+ "    cursor: pointer;\n"
	            				+ "}\n"
	            				+ "button:hover {\n"
	            				+ "    background-color: #fff;\n"
	            				+ "    color: #5370f0;\n"
	            				+ "    border: 1px solid #5370f0\n"
	            				+ "}\n"
	            				+ " </style>\n"
	            				+ " \n"
	            				+ "<div class=\"topnav\">\n"
	            				+ "  <a href=\"/\">Acceuil</a>\n"
	            				+ create_folderActive2
	            				+ create_file2
	        					+ create_link2
	            				+ "  <a href=\"#about\">About</a>\n"
	            				+ "</div>\n"
	            				+ "  \n"
	            				+ "     <section>\n"
	            				+ "	\n"
	            				+ "           <h1>Créer Votre dossier</h1>\n"
	            				+ form
	            				+ "	   <input type=\"text\" placeholder=\"Nom du dossier\" id=\"nom\" name=\"nom\" >	 \n"
	            				+ "	   <input type=\"submit\" value=\"Submit\">\n"
	            				+ "	</form>\n"
	            				+ "     </section>\n"
	            				+ "  \n"
	            				+ "     \n"
	            				+ "\n"
	            				+ "\n"
	            				+ " </body>\n"
	            				+ " </html>";
						
						
					}
					
					if(path.endsWith("create_file@")) {
						
						response = "<!DOCTYPE html>\n"
	            				+ " <html lang=\"en\">\n"
	            				+ " <head>\n"
	            				+ "     <meta charset=\"UTF-8\">\n"
	            				+ "     <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
	            				+ "     <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
	            				+ "     <title>Créer un Fichier</title>\n"
	            				+ "     \n"
	            				+ "\n"
	            				+ " </head>\n"
	            				+ " <body>\n"
	            				+ " <style>\n"
	            				+ " /* Add a black background color to the top navigation */\n"
	            				+ ".topnav {\n"
	            				+ "  background-color: #333;\n"
	            				+ "  overflow: hidden;\n"
	            				+ "}\n"
	            				+ "\n"
	            				+ "/* Style the links inside the navigation bar */\n"
	            				+ ".topnav a {\n"
	            				+ "  float: left;\n"
	            				+ "  color: #f2f2f2;\n"
	            				+ "  text-align: center;\n"
	            				+ "  padding: 14px 16px;\n"
	            				+ "  text-decoration: none;\n"
	            				+ "  font-size: 17px;\n"
	            				+ "}\n"
	            				+ "\n"
	            				+ "/* Change the color of links on hover */\n"
	            				+ ".topnav a:hover {\n"
	            				+ "  background-color: #ddd;\n"
	            				+ "  color: black;\n"
	            				+ "}\n"
	            				+ "\n"
	            				+ "/* Add a color to the active/current link */\n"
	            				+ ".topnav a.active {\n"
	            				+ "  background-color: #04AA6D;\n"
	            				+ "  color: white;\n"
	            				+ "}\n"
	            				+ "\n"
	            				+ "\n"
	            				+ "section {\n"
	            				+ "    display: flex;\n"
	            				+ "    flex-direction: column;\n"
	            				+ "    text-align: center;\n"
	            				+ "    background-color: #fff;\n"
	            				+ "    border-radius: 20px;\n"
	            				+ "    padding: 20px;\n"
	            				+ "}\n"
	            				+ "h1 {\n"
	            				+ "   margin: 30px; \n"
	            				+ "}\n"
	            				+ "#f{\n"
	            				+ "    padding: 10px;\n"
	            				+ "    margin: 20px 0;\n"
	            				+ "    font-size: 18px;\n"
	            				+ "    border: 1px solid #5370f0;\n"
	            				+ "    border-radius: 6px;\n"
	            				+ "}\n"
	            				+ "\n"
	            				+ "textarea {\n"
	            				+ "    resize:none ;\n"
	            				+ "    padding: 10px;\n"
	            				+ "    font-size: 18px;\n"
	            				+ "    border-radius: 6px;\n"
	            				+ "    outline: 0;\n"
	            				+ "    border: 1px solid #ccc;\n"
	            				+ "}\n"
	            				+ "button {\n"
	            				+ "	width:20%;\n"
	            				+ "    margin: 20px 0;\n"
	            				+ "	margin-left:40%;\n"
	            				+ "    background-color: #5370f0;\n"
	            				+ "    color: #fff;\n"
	            				+ "    border: 1px solid transparent;\n"
	            				+ "    padding: 10px 0;\n"
	            				+ "    font-weight: bold;\n"
	            				+ "    border-radius: 6px;\n"
	            				+ "    cursor: pointer;\n"
	            				+ "}\n"
	            				+ "button:hover {\n"
	            				+ "    background-color: #fff;\n"
	            				+ "    color: #5370f0;\n"
	            				+ "    border: 1px solid #5370f0\n"
	            				+ "}\n"
	            				+ " </style>\n"
	            				+ " \n"
	            				+ "<div class=\"topnav\">\n"
	            				+ "  <a href=\"/\">Acceuil</a>\n"
	            				+ create_folder2
	            				+ create_fileActive2
	            				+ create_link2
	            				+ "  <a href=\"#about\">About</a>\n"
	            				+ "</div>\n"
	            				+ "   \n"
	            				+ "     <section>\n"
	            				+ "         <h1>Deposer Votre fichier</h1>\n"
	            				+ form
	            				+ "           <input type=\"text\" placeholder=\"Nom du fichier\" id=\"nom\" name=\"nom\">\n"
	            				+ "	 <label for=\"myfile\">Select a file:</label>\n"
	            				+ "	<input type=\"file\" id=\"myfile\" name=\"myfile\"> 	 \n"
	            				+ "         <input type=\"submit\" value=\"Submit\">\n"
	            				+ "	</form>\n"
	            				+ "     </section>\n"
	            				+ "   \n"
	            				+ "     \n"
	            				+ "\n"
	            				+ " </body>\n"
	            				+ " </html>";
						
					}
					
					if(path.endsWith("create_linksyb@")) {
						
						response = "\n"
								+ "<!DOCTYPE html>\n"
								+ " <html lang=\"en\">\n"
								+ " <head>\n"
								+ "     <meta charset=\"UTF-8\">\n"
								+ "     <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
								+ "     <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
								+ "     <title>Créer un Lien</title>\n"
								+ "     \n"
								+ "\n"
								+ " </head>\n"
								+ " <body>\n"
								+ " <style>\n"
								+ " /* Add a black background color to the top navigation */\n"
								+ ".topnav {\n"
								+ "  background-color: #333;\n"
								+ "  overflow: hidden;\n"
								+ "}\n"
								+ "\n"
								+ "/* Style the links inside the navigation bar */\n"
								+ ".topnav a {\n"
								+ "  float: left;\n"
								+ "  color: #f2f2f2;\n"
								+ "  text-align: center;\n"
								+ "  padding: 14px 16px;\n"
								+ "  text-decoration: none;\n"
								+ "  font-size: 17px;\n"
								+ "}\n"
								+ "\n"
								+ "/* Change the color of links on hover */\n"
								+ ".topnav a:hover {\n"
								+ "  background-color: #ddd;\n"
								+ "  color: black;\n"
								+ "}\n"
								+ "\n"
								+ "/* Add a color to the active/current link */\n"
								+ ".topnav a.active {\n"
								+ "  background-color: #04AA6D;\n"
								+ "  color: white;\n"
								+ "}\n"
								+ "\n"
								+ "\n"
								+ "section {\n"
								+ "    display: flex;\n"
								+ "    flex-direction: column;\n"
								+ "    text-align: center;\n"
								+ "    background-color: #fff;\n"
								+ "    border-radius: 20px;\n"
								+ "    padding: 20px;\n"
								+ "}\n"
								+ "h1 {\n"
								+ "   margin: 30px; \n"
								+ "}\n"
								+ "#f{\n"
								+ "    padding: 10px;\n"
								+ "    margin: 20px 0;\n"
								+ "    font-size: 18px;\n"
								+ "    border: 1px solid #5370f0;\n"
								+ "    border-radius: 6px;\n"
								+ "}\n"
								+ "\n"
								+ "textarea {\n"
								+ "    resize:none ;\n"
								+ "    padding: 10px;\n"
								+ "    font-size: 18px;\n"
								+ "    border-radius: 6px;\n"
								+ "    outline: 0;\n"
								+ "    border: 1px solid #ccc;\n"
								+ "}\n"
								+ "button {\n"
								+ "	width:20%;\n"
								+ "    margin: 20px 0;\n"
								+ "	margin-left:40%;\n"
								+ "    background-color: #5370f0;\n"
								+ "    color: #fff;\n"
								+ "    border: 1px solid transparent;\n"
								+ "    padding: 10px 0;\n"
								+ "    font-weight: bold;\n"
								+ "    border-radius: 6px;\n"
								+ "    cursor: pointer;\n"
								+ "}\n"
								+ "button:hover {\n"
								+ "    background-color: #fff;\n"
								+ "    color: #5370f0;\n"
								+ "    border: 1px solid #5370f0\n"
								+ "}\n"
								+ " </style>\n"
								+ " \n"
								+ "<div class=\"topnav\">\n"
								+ "  <a href=\"/\">Acceuil</a>\n"
								+ create_folder2
								+ create_file2
								+ create_linkActive2
								+ "  <a href=\"#about\">About</a>\n"
								+ "</div>\n"
								+ "     <section>\n"
								+ "         <h1>Créer Votre Lien</h1>\n"
								+ form
								+ "         <input type=\"text\" placeholder=\"Nom\" id=\"nom\"  name=\"nom\">\n"
								+ "		 <input type=\"text\" placeholder=\"chemin\" id=\"chemin\" name=\"chemin\">\n"
								+ "         \n"
								+ "         <input type=\"submit\" value=\"Submit\">\n"
								+ "	</form>\n"
								+ "     </section>\n"
								+ "     \n"
								+ "\n"
								+ " </body>\n"
								+ " </html>";
						
					}
					
					
					
					
				}
				
			}else if("POST".equalsIgnoreCase(httpExchange.getRequestMethod())) {
				
				//System.out.println(path);
				
				//  Get post data
				InputStreamReader isr =  new InputStreamReader(httpExchange.getRequestBody(),"utf-8");
            	BufferedReader br = new BufferedReader(isr);
            	int b;
            	StringBuilder buf = new StringBuilder(512);
            	while ((b = br.read()) != -1) {
            	    buf.append((char) b);
            	}
            	br.close();
            	isr.close();
            	
            	String data = buf.toString();
            	//System.out.println(data);
            	//System.out.println(data.split("\\&").length);
            	
            	if(data.split("\\&").length == 1 && data.split("\\&")[0].split("=")[0].equalsIgnoreCase("nom") && data.split("\\&")[0].split("=")[1] != null) {
            		// try to get element behind the path
					SysFile f = stdSysCreator.CreateDir(data.split("\\&")[0].split("=")[1]);
					stdSysCreator.add(f, path);
					
					
            	} 
            	
            	if(data.split("\\&").length == 2 && data.split("\\&")[0].split("=")[0].equalsIgnoreCase("nom") && data.split("\\&")[0].split("=")[1] != null  && data.split("\\&")[1].split("=")[0].equalsIgnoreCase("myfile") && data.split("\\&")[1].split("=")[1] != null) {
            		
            		SysFile f = stdSysCreator.CreateRegFile(data.split("\\&")[0].split("=")[1], data.split("\\&")[1].split("=")[1]);
					stdSysCreator.add(f, path);
					
            	}
            	
            	if(data.split("\\&").length == 2 && data.split("\\&")[0].split("=")[0].equalsIgnoreCase("nom") && data.split("\\&")[0].split("=")[1] != null  && data.split("\\&")[1].split("=")[0].equalsIgnoreCase("chemin") && data.split("\\&")[1].split("=")[1] != null) {
            		//System.out.println(data);
            		
            		String chemin = data.split("\\&")[1].split("=")[1].replace("%2F", "/");
            		//System.out.println(chemin);
            		
            		SysFile f = stdSysCreator.CreateSybLink(data.split("\\&")[0].split("=")[1], chemin);
					stdSysCreator.add(f, path);
					//System.out.println(data);
            	}
            	
            	
            	SysFile d = stdSysCreator.getElementByPath(path);
				d.accept(this);
			}
			
				
				
				
		httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
				
			
			
		}

		@Override
		public void visitConcreteDir(StdDir d) {
			
			String content = "";
			String encoding = "UTF-8";
			
			httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
			
			if(d.getContent().isEmpty()) {
				content = "<p>Ce dossier est vide</p>\n";
				response = header + content + footer;
				
				
				
			}else {
				
				for (SysFile sysFile : d.getContent()) {
					sysFile.accept(ShowVisitor.getInstance());
					//System.out.println(ShowVisitor.getInstance().getFragment());
					content = content + ShowVisitor.getInstance().getFragment().replace("#", mYconcat(httpExchange.getRequestURI().getPath() ,sysFile.getName()));
					//System.out.println(content);
					//String test = ShowVisitor.getInstance().getFragment().replace("#", mYconcat(httpExchange.getRequestURI().getPath() , sysFile.getName()));
					//System.out.println(test);
				}
				
				response = header + content + footer;
				
			}
		}

		@Override
		public void visitConcretFile(StdRegFile f) {
			Headers responseHeaders = httpExchange.getResponseHeaders();
			responseHeaders.add("Content-Type", "application/octet-stream");
			
			response = new String(f.getContent(), StandardCharsets.UTF_8);
		}

		@Override
		public void visitConcretLink(StdSybLink s) {
			// get a  unique instance of sysfile library
			SysCreator stdSysCreator = Biblio.StdSysCreator.getInstance();
			SysFile f = stdSysCreator.getElementByPath(s.getPath());
			f.accept(this);
		}
		
		public String mYconcat(String s1, String s2){
			String answer;
			
			if(s1.endsWith("/")) {
				answer = s1 + s2;
			}else {
				answer = s1 + "/" + s2;
			}
			
			return answer;
			
			
		}
		
	}
	

}
