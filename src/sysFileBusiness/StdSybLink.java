package sysFileBusiness;

import fileVisitor.Visitor;

/**
 * La classe StdSybLink implemente l'interface SybLink et pointant soit sur StdRegFile ou sur StdDir
 * @author steve et arezki
 *
 */
public class StdSybLink implements SybLink{
	
	private NameFile name;
	private PathFile path;
	
	/**
	 * StdSybLink c'est le constructeur de la classe StdSybLink
	 * @param nameFile le nom du lien symbolique
	 * @param pathFile le chemin
	 */
	public StdSybLink(NameFile nameFile,PathFile pathFile ) {
		name = nameFile;
		path = pathFile;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name.toString();
	}

	@Override
	public void SetName(String nameFile) {
		// TODO Auto-generated method stub
		name.setNameFile(nameFile);
		
	}

	@Override
	public void accept(Visitor v) {
		v.visitConcretLink(this);
		
	}

	@Override
	public String getPath() {
		
		return path.toString();
	}

	@Override
	public void setPath(String pathName) {
		// TODO Auto-generated method stub
		path.setPath(pathName);
		
		
		
		
	}

	@Override
	public float getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
