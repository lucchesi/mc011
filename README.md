NPL Parser
===

School assignment (MC011 - Unicamp - 2013s1)
Thiago Valverde / Fernando Lucchesi

This is a parser for the NPL (News Publication Language) implemented using ANTLR.

### NPL Extensions
- newsitem.readmore adds a "Read more..." link to the home page, leading to the full text of the newsitem.;
- newsitem.caption adds a caption to the article's attached image;
- [youtube videoid] tag in the newsitem.text field allows Youtube videos do be embedded within articles;
- {author|quote} tag in the newsitem.text field allows the display of citations with authorship attribution.

### Libraries in use
- Twitter Bootstrap for general layout
- jQuery for user interactions and DOM manipulation
- ANTLR for lexing/parsing 
