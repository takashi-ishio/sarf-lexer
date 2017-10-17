package sarf.lexer;

import java.io.File;

import java.util.LinkedList;


/**
 * This main class simply executes a lexer and output a list of tokens. 
 * @author ishio
 *
 */
public class DirectoryScan {
	
	public interface Action {
		public void process(File f);
	}
	

	public static void scan(File dir, Action action) {
		LinkedList<File> files = new LinkedList<File>();
		files.add(dir);
		while (!files.isEmpty()) {
			File f = files.removeFirst();
			if (f.isDirectory() && f.canRead()) {
				File[] children = f.listFiles();
				for (File c: children) {
					if ((c.isDirectory() &&
						!c.getName().equals(".") && 
					    !c.getName().equals("..")) || c.isFile()) {
						files.addFirst(c);
					}
				}
			} else if (f.isFile() && f.canRead()) {
				action.process(f);
			}
		}
	}

}
