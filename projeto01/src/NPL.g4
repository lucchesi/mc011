grammar NPL;

// Variaveis "globais"

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
COL: 'col';
BORDER: 'border';

// Tokens extras
LCURL: '{';
RCURL: '}';
LBRAC: '[';
RBRAC: ']';
COLON: ':';
DOT  : '.';
NEWS: [A-Za-z] [A-Za-z0-9_]*;
STRING: '"' ( ~('"') | '\\"' )* '"';
NUM: [0-9]+;

// Tokens de coisas ignoradas
COMMENT: '//' ~('\n')* '\n' -> skip;
WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines
//EVERYTHING_ELSE: .+? -> skip;

//##--- Regras ---##//
root
    : 
      BEGIN cont struct END
    ;

//## Content ##//
cont
    : CONTENT LCURL newsp_ news_* RCURL
    ;

// Newspaper
newsp_
    : NEWSPAPER LCURL title_ date_? RCURL
    ;

title_
    : TITLE COLON STRING 
    ;

date_
    : DATE COLON STRING
    ;

// News
news_
    : NEWS LCURL title_ news_obj+ RCURL
    ;

news_obj_token: TITLE | ABSTRACT | IMAGE | SOURCE | DATE | AUTHOR | TEXT;
news_obj
    : news_obj_token COLON STRING
    ;

//## Structure ##/
struct
    :
      STRUCTURE LCURL format_ item_+ RCURL
    ;

// format
format_
    : FORMAT LCURL col_ border_? RCURL
    ;

col_
    : COL COLON NUM
    ;

border_
    : BORDER COLON NUM
    ;


// Item
item_
    : ITEM LBRAC NUM RBRAC LCURL print+ RCURL
    | ITEM LBRAC range RBRAC LCURL print+ RCURL
    ;

range
    : NUM COLON NUM
    ;

print
    : NEWS DOT news_obj_token
    ;

