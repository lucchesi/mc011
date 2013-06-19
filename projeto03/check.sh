#!/bin/bash

for txt in test/*.txt; do
	echo $txt;
	asm=${txt//txt/asm}
	bin=${txt//txt/bin}
	out=${txt//txt/out}
	
	make run INPUT=$txt OUTPUT=$asm > /dev/null
	make compile_assembly INPUT=$asm OUTPUT=$bin > /dev/null
	
	./$bin > saida.out
	diff -q $out saida.out
	if [ $? == 0 ]
	then
		echo "Nothing wrong."
	else
		diff -y -W 80 $out saida.out
	fi
	#rm $asm $bin saida.o saida.out
	echo;
done
