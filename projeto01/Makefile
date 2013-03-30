SHELL=/bin/bash -rcfile ~/.bashrc -i
.PHONY: test
ifdef GUI
    X=-gui
else
    X=-tree
endif

all:
	(cd src && antlr4 NPL.g4 -o ../bin)
	(cd bin && javac NPL*.java)

test_final:
	(cd bin && grun NPL root $X < ../test/exemplo.npl)

test0:
	(cd bin && grun NPL root $X < ../test/test0.npl)

alltests: test0 test_final
