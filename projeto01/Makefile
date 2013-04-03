SHELL=/bin/bash -rcfile ~/.bashrc -i
.PHONY: test
ifdef GUI
    X=-gui
else
    X=-tree
endif
ifdef NOGUI
    X=
endif

compile:
	(cd src && antlr4 NPL.g4 -o br/unicamp/mc011/projeto01/ -package br.unicamp.mc011.projeto01)
	(cd src && find . -name "*.java" > sources)
	(mkdir bin && cd src && javac -d ../bin @sources && rm sources)

all: compile

clean:
	(cd src && find . -type f -name "NPL*.java" -exec rm -f {} \; )
	(cd src && find . -type f -name "NPL*.tokens" -exec rm -f {} \; )
	(rm -rf bin)
	
test666:
	(cd bin && java br.unicamp.mc011.projeto01.Main < ../test/exemplo.npl > ../index.html)

test0:
	(cd bin && java br.unicamp.mc011.projeto01.Main $X < ../test/test0.npl > ../index.html)

test1:
	(cd bin && java br.unicamp.mc011.projeto01.Main $X < ../test/test1.npl > ../index.html)

alltests: test0 test_final
