
# CPP14 公式バージョンから変更した内容

元の内容: Directive は # 以降，改行が来るまで skip する
（最新版だと行末 \\ から，続きまで取り込むようになっている）．

	Directive
	:
		'#' ~[\r\n]* -> skip
	;

変更後の内容: Directive は # に続いてキーワードが登場するので
それを Directive として，# からキーワードまで，
間に空白があっても取り除いて１トークンとみなす．
ディレクティブを取り除かないので，字句解析には使えるが，
逆に構文解析にはまったく使えない文法ファイルになっている．

	Directive
	:
		'#' Whitespace* ~[ \t\r\n<"]+ 
		{ setText(getText().replaceAll("\\s+","")); }
	;

たとえば，以下のコードは #define, VALID_MACRO の2トークンになる．

	# define VALID_MACRO

DirectiveFilename は，#include において，
`<filename>` と `"filename"` をいずれも1トークンとしてみなすために導入したキーワード
（標準では，`<`, `filename`, `>` の3トークンになってしまう）．
いずれも構文解析ではないので単独で存在．

	DirectiveFileName
	:
		'<' ~[ \t\r\n]+ '>'
		| '"' ~[ \t\r\n]+ '"'
	;

行末継続の "\" は，単体で１トークンとするようにした．

	LineContinue
	:  
		'\\' Newline { setText("\\\\n"); }
	;


