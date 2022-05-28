package fileVisitor;

import sysFileBusiness.StdDir;
import sysFileBusiness.StdRegFile;
import sysFileBusiness.StdSybLink;

/**
 * La classe  ShowVisitor implémente l'interface Visitor. Elle permet de formater la réponse  HTML pour le client

 * @author steve et arezki
 *
 */
public class ShowVisitor implements Visitor {
	
	// private reference to the one and only instance
		private static ShowVisitor uniqueShowVisitor = null;
		
		// private reference to  the current class
		private static Object lock = ShowVisitor.class;
		
		
		
		/**
		 * la methode getInstance crée une unique instance de ShowVisitor s'il n'existe pas déjà
		 * @return un Objet de type ShowVisitor
		 */
		public static ShowVisitor getInstance() {
			synchronized (lock) {
				if (uniqueShowVisitor == null) {
					uniqueShowVisitor = new ShowVisitor();
					
				}
				return uniqueShowVisitor;
			}
			
		}
		
	/**
	 *  fragment of htmlBuilder destiné   à  MyHandler visitor
	 */
	private String  fragment;	

	@Override
	public void visitConcreteDir(StdDir d) {
	    //System.out.println(d.getName() + ":Dossier;   ");
		//String content = " <div class=\"column\">";
		String content =  "<a href=\"#\"><img src=\"images/**.png\" class=\"responsive\"><figcaption>" + d.getName() + ":" + " "+ d.getSize() + " " + "octects" + "</figcaption></a>" ;
		//content = content + "</div>";
		setFragment(content);
		
	}

	@Override
	public void visitConcretFile(StdRegFile f) {
		//System.out.println( f.getName() + ":Fichier,   contenu : " + f.getContent() + ";");
		//String content = " <div class=\"column\">";
		String content =   "<a href=\"#\"><img src=\"images/*.png\" class=\"responsive\"><figcaption>" + f.getName() + ":" + " "+ f.getSize() + " " + "octects" + "</figcaption></a>" ;
		//content = content + "</div>";
		setFragment(content);
		
	}

	@Override
	public void visitConcretLink(StdSybLink s) {
		//System.out.println( s.getName() + ":Lien symbolique,   contenu : " + s.getPath() + ";");
		//String content = " <div class=\"column\">"; 
		String temp = "<a href=\"#\"><img src=\"images/***.png\" class=\"responsive\"><figcaption>".replace("#", s.getPath()) +  s.getName() + ":" + " "+ s.getSize() + " " + "octect" + "</figcaption></a>" ;
		//System.out.println( temp);
		String content =   temp;
		setFragment(content);

	}
/**
 * La methode getFragment retourne les chaines de caracteres representant la description sous format HTML
 *  d'un objet de type SysFile dont il a visité
 * @return un Objet de type String 
 */
	public String getFragment() {
		return fragment;
	}
/**
 * La methode setFragment permet de modifier u fragment 
 * @param fragment chaine de caractere un reprsentant la descriprion HTML d'un fichier
 */
	public void setFragment(String fragment) {
		this.fragment = fragment;
	}

}
