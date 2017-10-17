package sarf.sing.lexer;


import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;


public class LexerTokenReader implements TokenReader {

	private int filetype;
	private Lexer lexer;
	private Token token;
	
	public LexerTokenReader(int filetype, Lexer lexer) {
		this.filetype = filetype;
		this.lexer = lexer;
	}
	
	@Override
	public boolean next() {
		token = lexer.nextToken();
		return token.getType() != Lexer.EOF;
	}
	
	@Override
	public String getToken() {
		if (token.getType() != Lexer.EOF) {
			return token.getText();
		} else {
			return null;
		}
	}

	@Override
	public int getLine() {
		return token.getLine();
	}
	
	@Override
	public int getCharPositionInLine() {
		return token.getCharPositionInLine();
	}
	
	@Override
	public int getFileType() {
		return filetype;
	}
}
