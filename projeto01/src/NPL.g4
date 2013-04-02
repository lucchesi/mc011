grammar NPL;

// Variaveis "globais"
@header {
    import java.util.List;
    import java.util.ArrayList;
    import java.util.Map;
    import java.util.HashMap;
}
@members {
    int i=0;
    List<String> a = new ArrayList<String>();
    class Noticia {
        public String bla;
    }
    List<Noticia> news = new ArrayList<Noticia>();
}

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
      {
          i++; System.out.println("i: " + i + " " + $STRING.text);
          a.add($STRING.text);
      }
    ;

date_
    : DATE COLON STRING
    ;

// News
news_
    : NEWS LCURL title_ news_obj+ RCURL
    {
        Noticia tmp = new Noticia();
        tmp.bla = $NEWS.text;
        news.add(tmp);
    }
    ;

news_obj_token: TITLE | ABSTRACT | IMAGE | SOURCE | DATE | AUTHOR | TEXT;
news_obj
    : news_obj_token COLON STRING
    ;

//## Structure ##/
struct
    : {
        System.out.println("<html><head></head><body>");
        for(Iterator<String> it = a.iterator(); it.hasNext(); ) {
            String item = it.next();
            System.out.println(item);
        }
        System.out.println("Noticias:");
        for(Iterator<Noticia> it = news.iterator(); it.hasNext(); ) {
            String item = it.next().bla;
            System.out.println(item);
        }
      }
      STRUCTURE LCURL format_ item_+ RCURL
      {System.out.println("</body></html>");}
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

