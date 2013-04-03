grammar Wiki;

@header {
}
// Variaveis "globais"
@members {
    String textofinal = "";
    int stars=0;
    int hashs=0;
}

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs
BR   : [\n]+;
STRING: ( ~('"'|'\n') | '\\"' )+;

//##--- Regras ---##//
	
wiki:
    { 
        textofinal = "";
        stars=0;
        hashs=0;
    }
     '"' br? (linha br*)+ '"'
    ;

w returns [ String s ]
	: wiki
	{ $s = "<p>" + textofinal + "</p>"; }
	;
	
br
    : BR
    { textofinal += "\n"; }
    ;

linha
    : STRING
    {
        String tmp = $linha.text;
        
        // Italico
        tmp = tmp.replaceAll("([^'])''([^']*)''([^'])", "\$1<i>\$2</i>\$3");
        // Negrito
        tmp = tmp.replaceAll("([^'])'''([^']*)'''([^'])", "\$1<b>\$2</b>\$3");
        // Italico e Negrito
        tmp = tmp.replaceAll("([^'])'''''([^']*)'''''([^'])", "\$1<i><b>\$2</b></i>\$3");
        
        // Titulo
        tmp = tmp.replaceAll("===([^=]*)===", "<h3>\$1</h3>");
        
        // Recuo (ficaria melhor como regra gramatical)
        String tmp2 = tmp.replaceAll("[^:]*", "");
        for(int i=0; i<tmp2.length(); i++)
            tmp = tmp.replaceAll("^(\\s*:+):", "\$1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        tmp = tmp.replaceAll("^(\\s*):", "<br>\$1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        
        // Links
        tmp = tmp.replaceAll("\\[([^\\]]*)\\|([^\\]]*)\\]", "<a href=\"\$1\">\$2</a>");
        
        // URL solta
        tmp = tmp.replaceAll("([^\"])(http://[a-z0-9.]*)([^\"])", "\$1<a href=\"\$2\">\$2</a>\$3");
        
        // Lista bullet
        tmp2 = tmp.replaceAll("(\\**).*","\$1"); // Deixa só os asteriscos
        for(int i=0; i<tmp2.length()-stars; i++)
            textofinal += "<ul>";
        for(int i=0; i<stars-tmp2.length(); i++)
            textofinal += "</ul>";
        stars = tmp2.length();
        
        // Lista ordenada
        tmp2 = tmp.replaceAll("(#*).*","\$1"); // Deixa só as cerquilhas
        for(int i=0; i<tmp2.length()-hashs; i++)
            textofinal += "<ol>";
        for(int i=0; i<hashs-tmp2.length(); i++)
            textofinal += "</ol>";
        hashs = tmp2.length();
        
        // Tira os asteriscos e cerquilhas
        if ((stars > 0) || (hashs > 0)) {
            tmp = tmp.replaceAll("#*", "");
            tmp = tmp.replaceAll("^\\**", "");
            tmp = tmp.replaceAll("^", "<li>").replaceAll("\$", "</li>");
        }
        
        
        textofinal += tmp;
    }
    ;
