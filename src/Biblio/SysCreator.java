package Biblio;

import sysFileBusiness.Dir;
import sysFileBusiness.RegFile;
import sysFileBusiness.SybLink;
import sysFileBusiness.SysFile;

/**
 * L'interface SysCreator est l'interface contenant toutes les factory method nécessaire pour la création
 *  et la  manipulation des objets du système de fichier 
 * @author steve et arezki
 *
 */
public interface SysCreator {
	
	/**
	 * La methode getElementByPath retourne le fichier se trouvant à  l'emplacement indiqué par le chemin 
	 * @param path le chemin 
	 * @return un fichier(dossier, fichier regulier ou lien symbolique)
	 */
	public SysFile getElementByPath(String path);
	
	/**
	 * La methode CreateDir permet de créer un dossier
	 * @param name est le nom du dossier
	 * @return un objet de type Dir(un dossier)
	 */
	public Dir CreateDir(String name);
	/**
	 * La methode CreateRegFile permet de créer un fichier regulier
	 * @param name le nom du fichier regulier 
	 * @param o son contenu
	 * @return un objet de type RegFile( un fichier regulier)
	 */
	public RegFile CreateRegFile(String name, Object o);
	
	/**
	 * La methode CreateSybLink permet de créer un Lien symbolique
	 * @param name est le nom du Lien symbolique
	 * @param path est le chemin sur lequel pointe le Lien symbolique
	 * @return un objet de type SybLink ( un Lien symbolique)
	 */
	public SybLink CreateSybLink(String name, String path);
	
	/**
	 * La methode add permet d'ajouter un fichier à  l'emplacement spécifié par le chemin 
	 * @param f est un objet de type SysFile represente le fichier 
	 * @param parentPath l'emplacement spécifié par le chemin
	 */
	public void add(SysFile f, String parentPath);
	
	/**
	 * La methode remove permet de supprimer un fichier à  l'emplacement spécifié par le chemin
	 * @param name est le nom du fichier
	 * @param parentPath l'emplacement spécifié par le chemin
	 */
	public void remove(String name, String parentPath );
	
	/**
	 * La methode getRoot retourne le dossier racine
	 * @return un objet de type Dir(dossier racine)
	 */
	public  Dir getRoot();
	
	/**
	 * La methode loadRecursifDirs permet de charger de maniere recursive le contenu d'un dossier
	 * @param path : le chemin de l'arborescence du dossier. chaque noeud de cet arborescence n'a qu'un seul successeur
	 */
	public void loadRecursifDirs(String path);

}
