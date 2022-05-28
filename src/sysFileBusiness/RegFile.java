package sysFileBusiness;

/**
 * Interface du fichier regulier qui herite de l'interface SysFile
 * @author steve et arezki 
 *
 */

public interface RegFile extends SysFile {
	
	/**
	 * La methode load permet de charger le contenu d'un fichier 
	 * @param o est l'objet que le souhaite charger dans le contenu du fichier
	 */
	public void load(Object o);

}
