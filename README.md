# CaesarCipher-CommandApp
v0.01 - it's alive // apparently i did something…

Let's assume you you know how to run it.
-key - shifts letters by number specified afterwards. e.g. -key 3

-data "text" - input that you want to code or decode. e.g. -data "Hello World!"

-in "Path/file.txt" or "file.txt" - if you want to supply the program with *.txt file
 instead of raw data in console. e.g. -in "Users/text.txt"

-out "Path/outfile.txt" or "outfile.txt" - if you want your output to be saved in a file 
instead of displaying it in console e.g. -out "Users/tempfile.txt"

Example use: java encryptdecrypt.Main -mode -enc -in testFile.txt -out testOutFile.txt
