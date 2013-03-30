grammar NPL;

// Tokens das palavras reservadas
BEGIN: 'begin';
END: 'end';
CONTENT: 'content';
NEWSPAPER: 'newspaper';
TITLE: 'title';
DATE: 'date';
ABSTRACT: 'abstract';
TEXT: 'text';
SOURCE: 'source';
IMAGE: 'image';
AUTHOR: 'author';
STRUCTURE: 'structure';
FORMAT: 'format';
ITEM: 'item';

// Tokens extras
LCURL: '{';
RCURL: '}';
LBRAC: '[';
RBRAC: ']';
COLON: ':';
NOTICIA: [A-Za-z] [A-Za-z0-9_]*;
STRING: '"' ( ~('"') | '\\"' )* '"';

// Tokens de coisas ignoradas
COMMENT: '//' .*? '\n' -> skip;
WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines
//EVERYTHING_ELSE: .+? -> skip;


// Regras
root
    : BEGIN cont struct END
    ;

cont
    : CONTENT LCURL newsp NOTICIA* RCURL
    ;

struct
    : STRUCTURE LCURL RCURL
    ;

newsp
    : NEWSPAPER LCURL title_ date_? RCURL
    ;

title_
    : TITLE COLON STRING
    ;

date_
    : DATE COLON STRING
    ;
