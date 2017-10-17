package sarf.sing.lexer;

/**
 * A simple token reader that provides a sequence of tokens.
 */
public interface TokenReader {

	/**
	 * Read the next token.
	 * This method must be called before reading the first token.
	 * @return true if there exists a token.
	 */
	public boolean next();
	
	/**
	 * @return the current token.  This method returns null for EOF.
	 */
	public String getToken();
	
	/**
	 * @return the line of the current token.
	 */
	public int getLine();

	/**
	 * @return the position of the current token.
	 */
	public int getCharPositionInLine();
	
	/**
	 * @return file type.
	 */
	public int getFileType();

}
