set PATH=C:\Program Files\glassfish3\jdk\bin;%PATH%
javac .\src\bank\main\BankServerRunner.java -sourcepath .\src -d .\bin
javac .\src\bank\main\BankGuiRunner.java -sourcepath .\src -d .\bin

start java -cp "./bin" "bank/main/BankGuiRunner" 50
start java -cp "./bin" "bank/main/BankGuiRunner" 51
start java -cp "./bin" "bank/main/BankGuiRunner" 52

start java -cp "./bin" "bank/main/BankServerRunner" 00
start java -cp "./bin" "bank/main/BankServerRunner" 01
start java -cp "./bin" "bank/main/BankServerRunner" 02