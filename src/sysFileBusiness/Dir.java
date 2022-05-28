package sysFileBusiness;

/**
 * L'inyerface representant les dossiers qui herite de l'interface SysFile
 * @author steve et arezki
 *
 */

public interface Dir extends SysFile {
	/**
	 * la methode add permet d'ajouter un fichier dans le  dossier
	 * @param f represente le fichier qu'on souhaite ajouter au dossier
	 */
	public void add(SysFile f);
	
	/**
	 * la methode remove permet de supprimer un fichier dans le  dossier
	 * @param name est la chaine de caractere representant le nom du fichier qu'on souhaite supprimer au dossier
	 */
	public void remove(String name);

}
