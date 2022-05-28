package sysFileBusiness;

import fileVisitor.Visitor;

/**
 * La classe StdRegFile implemente l'interface RegFile avec pour choix de reprsentation en memoire du contenu
 * Un tableau de byte.cette representation est taxée de representation Standard
 * @author steve et arezki
 *
 */
public class StdRegFile implements RegFile {
	private NameFile name;
	private byte[] content;
	
	/**
	 * StdRegFile c'est le constructeur de la classe StdRegFile
	 * @param nameFile nom du fichier
	 * @param contentFile contenu du fichier
	 */
	public StdRegFile(NameFile nameFile, byte[] contentFile ) {
		name = nameFile;
		content = contentFile;
	}
	
	/**
	 * la methode getContent retourne le contenu du fichier regulier
	 * @return le contenu du fichier regulier
	 */
	public byte[] getContent() {
		return content;
		
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
		v.visitConcretFile(this);
		
	}

	@Override
	public void load(Object o) {
		if(o != null) content = o.toString().getBytes();
		
		
		
	}

	@Override
	public float getSize() {
		if (content == null) {
			return 0;
		}
		return content.length;
	}

}
