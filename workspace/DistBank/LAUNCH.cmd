mkdir .\bin
set PATH=C:\Program Files\glassfish3\jdk\bin;%PATH%
javac .\src\core\node\NodeRunner.java -sourcepath .\src -d .\bin
javac .\src\bank\branch\BranchApp.java -sourcepath .\src -d .\bin
javac .\src\bank\gui\BranchGuiApp.java -sourcepath .\src -d .\bin
javac .\src\oracle\OracleApp.java -sourcepath .\src -d .\bin

start java -cp "./bin" "core/node/NodeRunner" 200

start java -cp "./bin" "core/node/NodeRunner" 150
start java -cp "./bin" "core/node/NodeRunner" 151
start java -cp "./bin" "core/node/NodeRunner" 152

start java -cp "./bin" "core/node/NodeRunner" 100
start java -cp "./bin" "core/node/NodeRunner" 101
start java -cp "./bin" "core/node/NodeRunner" 102