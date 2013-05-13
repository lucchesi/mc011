#!/bin/sh

for i in test/*.txt; do
	echo $i;
	java -classpath src:lib/etapa2.jar main/Main $i $i.asm;
	nasm -felf32 $i.asm -o $i.o;
	gcc $i.o lib/runtime.o -o $i.bin;
	./$i.bin;
	rm $i.asm $i.o $i.bin
done
