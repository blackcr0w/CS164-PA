#!/bin/sh
echo "I will now compile list2.cl and start it in spim"
coolc -o list2.s list2.cl
spim -file list2.s
