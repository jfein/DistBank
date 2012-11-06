mkdir .\bin
set PATH=C:\Program Files\glassfish3\jdk\bin;%PATH%
javac .\src\bank\branch\BranchRunner.java -sourcepath .\src -d .\bin
javac .\src\bank\gui\BranchGuiRunner.java -sourcepath .\src -d .\bin

start java -cp "./bin" "bank/gui/BranchGuiRunner" 150
start java -cp "./bin" "bank/gui/BranchGuiRunner" 151

start java -cp "./bin" "bank/branch/BranchRunner" 100