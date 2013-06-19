INPUT?=test/Test.txt
ASM?=Test.asm
OUTPUT?=Test.out

all:  base test
	@echo ""

base: clean compile 

test: run compile_assembly execute

clean:
	rm -f src/optimization/*.class src/main/*.class test/*.asm test/*.res test/*.o *.asm *.out saida.o

compile:
#  Retire o comentario da linha abaixo se vc for usar o seu próprio Codegen
	javac -classpath src:lib/etapa3.jar src/x86/*.java

	javac -classpath src:lib/etapa3.jar src/optimization/LivenessAnalysis.java
	javac -classpath src:lib/etapa3.jar src/optimization/DeadCodeElimination.java
	javac -classpath src:lib/etapa3.jar src/main/Main.java

run:
	java -classpath src:lib/etapa3.jar main/Main $(INPUT) $(ASM) -O

compile_assembly:
	nasm -felf32 $(ASM) -o saida.o
	gcc -m32 saida.o lib/runtime.o -o $(OUTPUT)

execute:
	@echo "Running $(INPUT) "
	./$(OUTPUT)
