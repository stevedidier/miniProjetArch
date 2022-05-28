package sysFileBusiness;

import java.security.InvalidParameterException;
import java.util.regex.Pattern;

/**
 * La classe PathFile represente le chemin d'un fichier. Elle formate le chemin du fichier
 * @author steve et arezki
 *
 */
public class PathFile {
private String path;
	/**
	 * PathFile c'est le constructeur de la classe PathFile
	 * @param pathFile est la chaine de caractere representant le chemin 
	 */
	public PathFile(String pathFile) {
		Pattern p = Pattern.compile("[/[a-zA-z0-9._]+]+");
		pathFile = pathFile.strip();
		if (!p.matcher(pathFile).matches()) {
			throw new InvalidParameterException("Invalide Path");
		}
		this.path = pathFile;
	}
/**
 * La methode toString retourne la chaine de caractere representant le nom du chemin	
 */
	public String  toString() { return path ; }
	
	/**
	 * La methode setPath permet de modifier le chemin
	 * @param pathFile chaine de caractere representant le chemin du fichier
	 */
	public void setPath(String pathFile){
		Pattern p = Pattern.compile("[/[a-zA-z0-9._]+]+");
		pathFile = pathFile.strip();

		if (!p.matcher(pathFile).matches()) {
			throw new InvalidParameterException();
		}
		this.path = pathFile;
	}
	
	

}
