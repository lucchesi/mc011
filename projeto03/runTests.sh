#!/bin/sh

for i in test/*.txt; do
	echo $i;
	java -classpath src:lib/etapa3.jar main/Main $i $i.noopt.asm;
	java -classpath src:lib/etapa3.jar main/Main $i $i.asm -O;
	nasm -felf32 $i.asm -o $i.o;
	nasm -felf32 $i.noopt.asm -o $i.noopt.o;
	gcc $i.o lib/runtime.o -o $i.bin;
	gcc $i.noopt.o lib/runtime.o -o $i.noopt.bin;
	./$i.noopt.bin > $i.noopt.res;
	./$i.bin > $i.res;
	echo "Checking for difference in results...";
	diff $i.res $i.noopt.res;
	rm $i.asm $i.noopt.asm $i.o $i.noopt.o $i.bin $i.noopt.bin $i.res $i.noopt.res;
done
