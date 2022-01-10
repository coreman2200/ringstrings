#!/bin/bash
compiler_path=/Users/new/Downloads
proto_path=/Users/new/git/ringstrings/app/src/main/java/com/coreman2200/ringstrings/data/protos/gen/
java_out=./


java -jar $compiler_path/wire-compiler-4.0.1-jar-with-dependencies.jar \
    --proto_path=$proto_path \
    --kotlin_out=$java_out \
    --files=$proto_path/protos.include \
    --android
