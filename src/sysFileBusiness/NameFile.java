package sysFileBusiness;

import java.security.InvalidParameterException;
import java.util.regex.Pattern;

/**
 * La classe NameFile represente le nom d'un fichier. Elle formate le nom du fichier 
 * @author steve et arezki
 *
 */

public class NameFile {
private String name;
	/**
	 * NameFile est le constructeur de la classe NameFile
	 * @param nameFile est la chaine de caractere representant le chemin
	 */
	public NameFile(String nameFile) {
		Pattern p = Pattern.compile("[a-zA-z0-9._]+");
		nameFile = nameFile.strip();
		if (!p.matcher(nameFile).matches()) {
			throw new InvalidParameterException("Invalide name");
		}
		this.name = nameFile;
	}
	
	public String  toString() { return name ; }
/**
 * La methode setNameFile permet de modifier le contenu de l'attribut name	
 * @param nameFile est chaine de caratere representant le nouveau nom
 */
	
	
	public void setNameFile(String nameFile) {
		Pattern p = Pattern.compile("[a-zA-z0-9._]+");
		nameFile = nameFile.strip();
		if (!p.matcher(nameFile).matches()) {
			throw new InvalidParameterException();
		}
		this.name = nameFile;
	}
	

}
