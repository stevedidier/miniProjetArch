package sysFileBusiness;

import fileVisitor.Visitor;

/**
 * Interface representant l'abstration metier d'un systeme de fichier
 * @author steve et arezki
 *
 */

public interface SysFile {
	
	/**
	 * La methode getName retourne le nom d'un element contenu dans le systeme de fichier
	 * @return une chaine de caractere representant le nom  du fichier
	 * 
	 */
	
	public String getName();
	
	/**
	 * La methode setName permet de renommer un fichier
	 * @param name est la chaine de caractere qui represente le nouveau nom du fichier
	 */
	public void SetName(String name);
	
	/**
	 * La methode accept permet au objet dela objet de type Visitor d'effectuer 
	 * des operations sur les objets de type SysFile ces dernieres sont decrit dans l'interface Visitor
	 * @param v est un objet de type Visitor
	 */
	public void accept(Visitor v);
	
	/**
	 * La methode getSize retourne  la taille d'un fichier 
	 * @return un float representant la taille du fichier
	 */
	public float getSize();
	

}
