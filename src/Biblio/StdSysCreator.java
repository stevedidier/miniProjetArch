package Biblio;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fileVisitor.Visitor;
import sysFileBusiness.Dir;
import sysFileBusiness.NameFile;
import sysFileBusiness.PathFile;
import sysFileBusiness.RegFile;
import sysFileBusiness.StdDir;
import sysFileBusiness.StdRegFile;
import sysFileBusiness.StdSybLink;
import sysFileBusiness.SybLink;
import sysFileBusiness.SysFile;

/**
 * StdSysCreator implemente les interface SysCreator, Visitor et 
 * implémente les méthodes de fabrique des fichiers standard(StdRegFile, StdDir, StdSybLink)
 * et d'autres opérations leur concernant 
 * @author steve et arezki
 *
 */
public class StdSysCreator implements SysCreator, Visitor {
	/**
	 *  private reference to the one and only instance
	 */
	private static StdSysCreator uniqueSysCreator = null;
	
	/**
	 * search file s'il est null alors la recherche du fichier par son chemin est un échec

	 */
	private  SysFile searchFile;
	
	/**
	 *  search name le nom du fichier courant de la recherche
	 */
	private  String searchName;
	
	/**
	 *  addFileBol est à true il s'agit d'une operation d'ajout 
	 */
	private  SysFile addFile;
	
	private  boolean addFileBol;
	
	/**
	 *  removeFileBol est à true il s'agit d'une operation de suppression 
	 */
	private  String removeFile;
	
	private  boolean removeFileBol;
	
	/**
	 *   visit = true si visitConcretFile
	 */
	private  boolean visit = false ;
	
	
	/**
	 *  dossier racine
	 */
	private static Dir root;
		
	/**
	 *  private reference to  the current class
	 */
	private static Object lock = SysCreator.class;
	
	/**
	 * default size of file
	 */
	private int size = 100;
	
	/**
	 * la méthode getInstance crée une unique instance de ShowVisitor s'il n'existe pas déjà
	 * @return un Objet de type StdSysCreator
	 */
	
	public static StdSysCreator getInstance() {
		synchronized (lock) {
			if (uniqueSysCreator == null) {
				uniqueSysCreator = new StdSysCreator();
				
				//  initialization of root 
				
				Dir dir9 = new StdDir(new NameFile("etc"), new ArrayList<SysFile>());
				Dir dir8 = new StdDir(new NameFile("bin"), new ArrayList<SysFile>());
				Dir dir7 = new StdDir(new NameFile("syslog"), new ArrayList<SysFile>());
				dir8.add(dir7);
				RegFile f9 = new StdRegFile(new NameFile("file.txt"), new byte[10]);
				f9.load("contenu file.txt");
				List<SysFile> l = new ArrayList<SysFile>();
				l.add(f9);
				l.add(dir9);
				l.add(dir8);
				root = new StdDir(new NameFile("root"), l);
				
			}
			return uniqueSysCreator;
		}
		
	}
	
	
		

	@Override
	public SysFile getElementByPath(String pathFile) {
		if(pathFile == null) throw new InvalidParameterException(" Component missing ");

		PathFile filePath = new PathFile(pathFile);
		if(filePath.toString().equalsIgnoreCase("/")) return root;
		else {
			Path path = Paths.get(filePath.toString());
			
			Iterator<Path> itr = path.iterator();
			searchFile = root;
			while(itr.hasNext()) {
		         Object element = itr.next();
		         searchName = element.toString();
		         searchFile.accept(StdSysCreator.getInstance());

		         if(searchFile == null || ( visit &&  itr.hasNext())) {
		        	 throw new InvalidParameterException("Invalide Path");
		         }
		      }
		}
		visit = false;
		return searchFile;
	}

	@Override
	public Dir CreateDir(String name) {
		if(name == null) throw new InvalidParameterException(" Component missing ");
		
		return new StdDir(new NameFile(name), new ArrayList<SysFile>());
	}

	@Override
	public RegFile CreateRegFile(String name, Object o) {
		if(name == null) throw new InvalidParameterException(" Component missing ");
		
		RegFile f = new StdRegFile(new NameFile(name), new byte[size]);
		f.load(o);
		
		return f;
	}

	@Override
	public SybLink CreateSybLink(String name, String path) {
		if(name == null || path == null) throw new InvalidParameterException(" Component missing ");
		return new StdSybLink(new NameFile(name), new PathFile(path));
	}

	@Override
	public void add(SysFile f, String parentPath) {
		addFile = f;
		SysFile dir4 = getElementByPath(parentPath);
		addFileBol = true;
		dir4.accept(uniqueSysCreator);
		if(visit) throw new InvalidParameterException(" Invalid parent path ");
		visit = false;
		addFile = null;
		
		
	}

	@Override
	public void remove(String name, String parentPath) {
		removeFile = name;
		SysFile dir4 = getElementByPath(parentPath);
		removeFileBol = true;
		dir4.accept(uniqueSysCreator);
		if(visit) throw new InvalidParameterException(" Invalid parent path ");
		visit = false;
		removeFile = null;		
	}




	@Override
	public void visitConcreteDir(StdDir d) {
		boolean find = false;
		
		if(addFileBol) {
			
			//SysFile fichier = addFile.;
			d.add(addFile);
			
			addFileBol = false;
			
			
		}else if (removeFileBol) {
			d.remove(removeFile);
			
			removeFileBol = false;
			
		}
		if(d.getContent().isEmpty()) {
			searchFile = null;
			


		}else {
			for (SysFile sysFile : d.getContent()) {
				if(sysFile.getName().equalsIgnoreCase(searchName)) {
					searchFile = sysFile;
					find = true;

					break;
				}
				
			}
			if(! find) {
				searchFile = null;
				
				}			         

		}
		
	}




	@Override
	public void visitConcretFile(StdRegFile f) {
		visit = true;
		
		
	}


	@Override
	public void visitConcretLink(StdSybLink f) {
		SysFile f5 = getElementByPath(f.getPath());
		f5.accept(uniqueSysCreator);
		
	}
	
	
	@Override
	public Dir getRoot() {
		return root;
	}




	@Override
	public void loadRecursifDirs(String path) {
		PathFile filePath = new PathFile(path);
		Path realPath = Paths.get(filePath.toString());
		Dir tempDir = null;
		
		Iterator<Path> itr = realPath.iterator();
		
		List<String> list = new ArrayList<String>();
		
		while(itr.hasNext()) {
	         Object element = itr.next();
	      
	         list.add(element.toString());
	        
	      }
		
		
		for (int i = list.size(); i-- > 0; ) {
		    if (tempDir != null) {
		    	Dir temp = CreateDir(list.get(i));
		    	temp.add(tempDir);
		    	tempDir = temp;
				
			} else {
				tempDir = CreateDir(list.get(i));

			}
		}
		
		if (tempDir != null) {
			root.add(tempDir);
		}
		
		
		
		
		
	}

}
