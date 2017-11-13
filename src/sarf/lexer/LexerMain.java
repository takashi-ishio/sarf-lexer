package sarf.lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This main class simply executes a lexer and output a list of tokens. 
 * The first argument must be a file name or a directory name.
 * If "-lexer:(c|cpp|java)" is specified as an argument, the specified lexer is used.
 * If "-hideTokens" is specified as an argument, this program does not output tokens (only error messages).
 */
public class LexerMain {

	public static void main(String[] args) {
		
		String lexer = null;
		boolean showTokens = true;
		ArrayList<String> files = new ArrayList<>();
		for (String arg: args) {
			if (arg.equals("-hideTokens")) {
				showTokens = false;
			} else if (arg.startsWith("-lexer:")) {
				lexer = arg.substring("-lexer:".length());
			} else {
				files.add(arg);
			}
		}
		
		for (String filename: files) {
			FileType filetype; 
			if (lexer != null) {
				filetype = FileType.valueOf(lexer);
			} else {
				filetype = TokenReaderFactory.getFileType(filename);
			}
				
			if (TokenReaderFactory.isSupported(filetype)) {
				try {
					FileReader reader = new FileReader(new File(filename));
					TokenReader t = TokenReaderFactory.create(filetype, reader);
					while (t.next()) {
						if (showTokens) {
							System.out.print(filename);
							System.out.print(",");
							System.out.print(t.getLine());
							System.out.print(",");
							System.out.print(t.getCharPositionInLine());
							System.out.print(",");
							System.out.println(t.getToken());
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
