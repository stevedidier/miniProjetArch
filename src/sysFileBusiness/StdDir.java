package sysFileBusiness;

import java.security.InvalidParameterException;
import java.util.List;

import fileVisitor.Visitor;

/**
 * La classe StdDir implemente l'interface Dir et avec pour choix de reprsentation en memoire du contenu
 * des ArrayList. cette representation est taxée de representation Standard
 * @author steve et arezki
 *
 */
public class StdDir implements Dir {
	private NameFile name;
	private List<SysFile> content;
	
	/**
	 * StdDir() est le constructeur du Dossier  de type Standard
	 * @param nameFile represente le nom du fichier
	 * @param contentFile le  contenu du fichier
	 */
	public StdDir(NameFile nameFile, List<SysFile> contentFile) {
		name = nameFile;
		content = contentFile;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name.toString();
	}

	@Override
	public void SetName(String nameFile) {
		name.setNameFile(nameFile);
		
	}

	@Override
	public void accept(Visitor v) {
		v.visitConcreteDir(this);		
	}

	@Override
	public void add(SysFile f) {
		for (SysFile sysFile : content) {
			if(sysFile.getName().equalsIgnoreCase(f.getName())){
				throw new InvalidParameterException("This name already used ");
				
			}
		
		}
		content.add(f);
		
	
		
	}

	@Override
	public void remove(String name) {
		int j = -1 ,i = 0;
		
		for (SysFile sysFile : content) {
			if(sysFile.getName().equalsIgnoreCase(name)){
				j = i;
				break;
				
			}
			i = i + 1;
		
		}
		if(j != -1) {
			content.remove(j);
		} else {
			throw new InvalidParameterException("Filename not found in target");
		}
/**
 * @exception	la methode remove leve une exception lorsque le dossier ne contient 
 * pas le fichier que l'on souhaite supprimer	
 */

		
	}
/**
 *  permet d'afficher  le contenu du dossier
 * @return la  liste de fichiers contenu dans le repertoire
 */
	public List<SysFile> getContent() {
		/**
		 * l'attribut content est une liste de fichier representant le contenu du repertoire
		 */
		return content;
	}

	/**
	 * la methode setContent permet d'ajouter un contenu a un dossier
	 * @param content est le contenu du dossier
	 */
	public void setContent(List<SysFile> content) {
		this.content = content;
	}

	@Override
	public float getSize() {
		float nbre = 0;
		if (! content.isEmpty()) {
			for (SysFile element : content) {
				nbre = nbre + element.getSize();
		
			}
		}
		return nbre;
	}

}
