set PATH=C:\Program Files\glassfish3\jdk\bin;%PATH%
javac .\src\bank\main\BankServerRunner.java -sourcepath .\src -d .\bin
javac .\src\bank\main\BankGuiRunner.java -sourcepath .\src -d .\bin

start java -cp "./bin" "bank/main/BankGuiRunner" 50
start java -cp "./bin" "bank/main/BankGuiRunner" 60

start java -cp "./bin" "bank/main/BankServerRunner" 00
start java -cp "./bin" "bank/main/BankServerRunner" 01