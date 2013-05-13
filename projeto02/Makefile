all: clean compile
	@echo ""

clean:
	rm src/x86/*.class || true

compile:
	javac -classpath lib/etapa2.jar src/x86/Codegen.java 

run:
	java -classpath src:lib/etapa2.jar main/Main $(INPUT) $(OUTPUT)

compile_assembly:
	nasm -felf32 $(INPUT) -o saida.o
	gcc saida.o lib/runtime.o -o $(OUTPUT)

run_tests: clean compile
	./runTests.sh
