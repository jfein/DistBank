mkdir .\bin
set PATH=C:\Program Files\glassfish3\jdk\bin;%PATH%
javac .\src\bank\main\BranchServerRunner.java -sourcepath .\src -d .\bin
javac .\src\bank\main\BranchGuiRunner.java -sourcepath .\src -d .\bin

start java -cp "./bin" "bank/main/BranchGuiRunner" 50
start java -cp "./bin" "bank/main/BranchGuiRunner" 51
start java -cp "./bin" "bank/main/BranchGuiRunner" 52

start java -cp "./bin" "bank/main/BranchServerRunner" 00
start java -cp "./bin" "bank/main/BranchServerRunner" 01
start java -cp "./bin" "bank/main/BranchServerRunner" 02