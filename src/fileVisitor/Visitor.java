package fileVisitor;

import sysFileBusiness.StdDir;
import sysFileBusiness.StdRegFile;
import sysFileBusiness.StdSybLink;

/**
 * L'interface visitor decrit le contrat que doit respecter un visiteur
 * @author steve et arezki
 *
 */
public interface Visitor {
	
	/**
	 * La methode visitConcreteDir permet de manipuler de manière uniforme les objets de type Dir
	 * @param d est un objet de type StdDir
	 */
	public void visitConcreteDir(StdDir d);
	
	/**
	 * La methode visitConcretFile permet de manipuler de manière uniforme les objets de type StdRegFile
	 * @param f est un objet de type StdRegFile
	 */
	public void visitConcretFile(StdRegFile f);
	/**
	 * La methode visitConcretLink permet de manipuler de manière uniforme les objets de type StdSybLink
	 * @param f est un objet de type StdSybLink
	 */
	public void visitConcretLink(StdSybLink f);
	



}
