package sarf.sing.lexer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

import org.antlr.v4.runtime.CharStreams;

import sarf.sing.lexer.lang.CPP14Lexer;
import sarf.sing.lexer.lang.CSharpLexer;
import sarf.sing.lexer.lang.ECMAScriptLexer;
import sarf.sing.lexer.lang.Java8Lexer;

public class TokenReaderFactory {
	
	private static HashMap<String, FileType> filetype;
	static {
		filetype = new HashMap<>(64);
		filetype.put("c", FileType.CPP);
		filetype.put("cc", FileType.CPP);
		filetype.put("cp", FileType.CPP);
		filetype.put("cpp", FileType.CPP);
		filetype.put("cx", FileType.CPP);
		filetype.put("cxx", FileType.CPP);
		filetype.put("c+", FileType.CPP);
		filetype.put("c++", FileType.CPP);
		filetype.put("h", FileType.CPP);
		filetype.put("hh", FileType.CPP);
		filetype.put("hxx", FileType.CPP);
		filetype.put("h+", FileType.CPP);
		filetype.put("h++", FileType.CPP);
		filetype.put("hp", FileType.CPP);
		filetype.put("hpp", FileType.CPP);

		filetype.put("java", FileType.JAVA);
		
		filetype.put("js", FileType.ECMASCRIPT);

		filetype.put("cs", FileType.CSHARP);
	}
	

	public static boolean isSupported(String filename) {
		return isSupported(getFileType(filename));
	}

	public static boolean isSupported(FileType filetype) {
		return filetype != FileType.UNSUPPORTED;
	}

	public static FileType getFileType(String filename) {
		// Remove directories 
		int index = filename.lastIndexOf("/");
		filename = filename.substring(index+1);
		
		if (filename.startsWith("._")) { // Mac OS's backup file
			return FileType.UNSUPPORTED;
		}
		
		index = filename.lastIndexOf('.');
		if (index < 0) {
			return FileType.UNSUPPORTED;
		}
		String ext = filename.substring(index + 1);
		FileType type = filetype.get(ext);
		if (type == null) {
			type = filetype.get(ext.toLowerCase());
		}
		if (type != null) {
			return type;
		}
		return FileType.UNSUPPORTED;
	}

	/**
	 * @param filetype specifies a file type that can be obtained by TokenReaderFactory#getFileType. 
	 * @param buf the source code buffer.    
	 * @return a token reader.
	 */
	public static TokenReader create(FileType filetype, byte[] stream) {
		try {
			switch (filetype) {
			case CPP:
				return new LexerTokenReader(filetype.ordinal(), new CPP14Lexer(CharStreams.fromStream(new ByteArrayInputStream(stream))));
	
			case JAVA:
				return new LexerTokenReader(filetype.ordinal(), new Java8Lexer(CharStreams.fromStream(new ByteArrayInputStream(stream))));

			case ECMASCRIPT:
				return new LexerTokenReader(filetype.ordinal(), new ECMAScriptLexer(CharStreams.fromStream(new ByteArrayInputStream(stream))));
				
			case CSHARP:
				return new LexerTokenReader(filetype.ordinal(), new CSharpLexer(CharStreams.fromStream(new ByteArrayInputStream(stream))));
				
			case UNSUPPORTED:
			default:
				return null;
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param filetype specifies a file type that can be obtained by TokenReaderFactory#getFileType. 
	 * @param stream is a source code stream.  The object is automatically closed.  
	 * @return a token reader.
	 */
	public static TokenReader create(FileType filetype, Reader stream) {
		try {
			switch (filetype) {
			case CPP:
				return new LexerTokenReader(filetype.ordinal(), new CPP14Lexer(CharStreams.fromReader(stream)));
	
			case JAVA:
				return new LexerTokenReader(filetype.ordinal(), new Java8Lexer(CharStreams.fromReader(stream)));

			case ECMASCRIPT:
				return new LexerTokenReader(filetype.ordinal(), new ECMAScriptLexer(CharStreams.fromReader(stream)));

			case CSHARP:
				return new LexerTokenReader(filetype.ordinal(), new CSharpLexer(CharStreams.fromReader(stream)));

			case UNSUPPORTED:
			default:
				return null;
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
