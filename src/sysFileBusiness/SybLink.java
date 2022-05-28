package sysFileBusiness;

/**
 * L'interface representant les liens symboliques
 * @author steve et arezki
 *
 */

public interface SybLink extends SysFile {
	
	/**
	 * La methode getPath retourne le chemin sur lequel pointe le lien symbolique
	 * @return une chaine de caractere representant son chemin 
	 */
	public String getPath();
	
	/**
	 * La setPath met  à jour le chemin du lien symbolique
	 * @param path nouveau chemin
	 */
	public void setPath(String path);
	

}
