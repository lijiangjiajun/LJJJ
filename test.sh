#!/bin/bash

a=""
i=0
index=0
b=('|' '-' '\\' '/')
for i in {0..100}
do
  a=$a'#'
  printf "[%-100s] [%d%%] %c \r" "$a" "$i" "${b[index]}"
  sleep 0.1
  let index++
  ((index%=4))
done

