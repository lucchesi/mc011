CLASSPATH=.:./bin:$(CURDIR)/src/lib/antlr-4.0-complete.jar:$$CLASSPATH

compile:
	@echo export ${CLASSPATH}
	(rm -rf bin)
	(cd src && java -jar $(CURDIR)/src/lib/antlr-4.0-complete.jar NPL.g4 -o br/unicamp/mc011/projeto01/ -package br.unicamp.mc011.projeto01)
	(cd src && java -jar $(CURDIR)/src/lib/antlr-4.0-complete.jar Wiki.g4 -o br/unicamp/mc011/projeto01/ -package br.unicamp.mc011.projeto01)
	(cd src && find . -name "*.java" > sources)
	(mkdir bin && cd src && javac -d ../bin @sources && rm sources)

all: compile

clean:
	(cd src && find . -type f -name "NPL*.java" -exec rm -f {} \; )
	(cd src && find . -type f -name "NPL*.tokens" -exec rm -f {} \; )
	(cd src && find . -type f -name "Wiki*.java" -exec rm -f {} \; )
	(cd src && find . -type f -name "Wiki*.tokens" -exec rm -f {} \; )
	(cd test && find . -type f -name "*.html" -exec rm -f {} \; )
	(rm -rf bin)

run:
	@echo export ${CLASSPATH}
	(java br.unicamp.mc011.projeto01.Main < $(INPUT) > $(OUTPUT))
	
testexemplo:
	(java br.unicamp.mc011.projeto01.Main < test/exemplo.npl > test/index.html)

test0:
	(java br.unicamp.mc011.projeto01.Main $X < test/test0.npl > test/index.html)

test1:
	(java br.unicamp.mc011.projeto01.Main $X < test/test1.npl > test/index.html)
	
test2:
	(java br.unicamp.mc011.projeto01.Main $X < test/testtoday.npl > test/index.html)