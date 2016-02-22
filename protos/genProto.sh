#!/bin/bash
compiler_path=/Users/new/Downloads
proto_path=/Users/new/git/ringstrings/protos/
java_out=/Users/new/git/ringstrings/ringstrings-app/src/test/java/


java -jar $compiler_path/wire-compiler-2.0.1-jar-with-dependencies.jar \
    --proto_path=$proto_path \
    --java_out=$java_out \
    --files=protos.include
