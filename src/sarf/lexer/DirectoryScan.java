package sarf.lexer;

import java.io.File;
import java.util.ArrayList;


/**
 * A class to traverse files in a directory. 
 * @author ishio
 *
 */
public class DirectoryScan {
	
	public interface Action {
		public void process(File f);
	}

	/**
	 * Search all files in a directory recursively.
	 * @param dir specifies a search root.
	 * @param action is called for each file.
	 */
	public static void scan(File dir, Action action) {
		ArrayList<File> files = new ArrayList<File>();
		files.add(dir);
		while (!files.isEmpty()) {
			File f = files.remove(files.size()-1);
			if (f.isDirectory() && f.canRead()) {
				File[] children = f.listFiles();
				for (File c: children) {
					if (c.isDirectory() &&
						!c.getName().equals(".") && 
						!c.getName().equals("..")) {
						files.add(c);
					} else if (c.isFile() && f.canRead()) {
						action.process(f);
					}
				}
			} else if (f.isFile() && f.canRead()) {
				action.process(f);
			}
		}
	}

}
