README

-------------------------------------------------------------------------------

SOURCE CODE FILE DESCRIPTION:

package bank
	Account.java
		This file represents an account object. Each object stores the following account information: an accountId, an account balance, and a list of used serial numbers for the transactions originating from this account.
	AccountId.java
		This file represents an account id object. Since each account id is represented by 2 digit branch id, and a 5 digit account number, the AccountId object stores a branch id and an account number.
	BranchClient.java
		This layer interacts with the BranchServerHandler by making different types of requests depending on what the user presses on the GUI. So if the user wants to withdraw, BranchClient's withdraw method created a WithdrawRequest and send the request to BranchServerHandler which will handle the request appropriately. Similar actions occur for the other requests possible: deposit, transfer, and query (check balance). 
	BranchServerHandler.java
		This layer handles all the requests from BranchClient. This file contains overloaded  handle methods that differ in the arguments they take which are the request objects generated in BranchClient.java(WithdrawRequest, DepositRequest, QueryRequest, TransferRequest). Each handle method handles the distinct Request objects appropriately. For example, the handle for a WithdrawRequest, calls the BranchState's withdraw method and waits for the BranchState's function to return success or failure, then the handle method returns a BranchResponse object with the balance of this account and whether it was successful.
	BranchState.java
		BranchState holds all the main methods for changing the state of an account which changes the state of the branch, hence the name BranchState. It contains methods like deposit, withdraw, query, and transfer. Each method will get the account or create a new account with the balance of 0.0 if it does not exist. It will then proceed by checking if the serial number that came with this request has been used in previous requests with this account. If it has not, then we will do the appropriate change (withdraw, deposit, transfer, check the balance), and add the serial number to the list of used serial numbers, then return.
	BranchId.java
		This file represents the BranchId object. This object extends NodeId and stores an Integer id for a branch. This ID is how the BranchServer is identifiable on the network.
	GuiId.java
		This file represents the GUI Id object. This object extends NodeId and stores an Integer id for the gui. This ID is how the BranchGui is identifiable on the network.

package bank.gui
	BranchMain.java
		This is the main file that sets up the GUI layout and GUI interactions between the user. This sets up the appropriate textfields that are needed from the user including the serial number, the source account number, the destination account number, and the amount.  Then the GUI sets up the buttons the user can click to withdraw, deposit, transfer, and query the balance of their account. There are corresponding action methods to the buttons which will contact their counterparts in the BranchClient.java file described above.

package bank.main
	BranchGuiRunner.java
		Launches a GUI with a corresponding GUI id ( account number ) that is given on input. This GUI ID is what is used to identify this node on the network, and is used in the topology file.
	BranchServerRunner.java
		A branch Id is given on input which will be used to find the corresponding hostname:port in a mapping file, and launch a server on this hostname:port combination.
		
package bank.messages
	BranchRequest.java
		Generic abstract class representing a request object. It stores the source account id from where the request generated and the serial number associated with this request.
	BranchResponse.java
		This represents the response that the branch client will send back to the GUI. It stores the balance of the account that had originally requested a transaction and whether the transaction was successful.
	DepositRequest.java
		This represents the request that will be generated when the user presses the "deposit" button on GUI panel. It stores the amount to deposit along with the account id and serial number. This request objects helps the BranchServerHandler distinguish which "handle" method to call.
	QueryRequest.java
		This represents the request that will be generated when he user presses "Check Balance" button on the GUI panel. This object does not store any additional information other then what BranchRequest stores.This request objects helps the BranchServerHandler distinguish which "handle" method to call.
	TransferRequest.java
		This represents the request that will be generated when the user presses "Transfer" button on the GUI Panel. This object stores the amount and destination account number which is needed additional to the source account number and serial number in order to complete the transaction. This request objects helps the BranchServerHandler distinguish which "handle" method to call.
	WithdrawRequest.java
		This represents the request that will be generated when the user presses "Withdraw" button on the GUI Panel. This object stores the amount alongside the account number and serial number. This request objects helps the BranchServerHandler distinguish which "handle" method to call.
		
package core.network.client
	Client.java
		An abstract class that all client APIs should extend. Provides one function called "exec" that other client API functions can call to send a request message to a destination node and return the response message. If there are any network failures along the way, will return Null.

package core.network.server
	Server.java
		A single threaded server that is started in a ServerNodeRuntime. Takes in a server handler to process the incoming request messages and produce a response message. A new socket is used for each request/response pair. If anything goes wrong when processing the message, including topology failures, the server will close the socket and continue to accept another connection. If everything is OK, the client will close the socket.
	ServerHandler.java
		A class to handle incoming request messages and return a response. Is specific to the application. Uses the generic handle() method to use reflections to route the messages to the appropriate handle() method. A handle() method is determined by the kind of message it takes in. See BranchServerHandler.java for a specific example.

package core.network.common
	NetworkInterface.java
		Wrapper class to send and receive messages on the network. When doing a send, checks through the topology that the sending node has a channel to the destination. The functions are synchronized, so no two network operations can occur simultaneously. 
	Topology.java
		Class to hold topology and node mapping information to be retrieved statically from anywhere in the code. Parses the two files "topology_file.txt" and "node_mapping.txt". Provides functions to retrieve a host:port from a NodeId and to check if the current node can send or receive from another NodeId.
	Message.java
		A message that can be sent from a source node to a destination node. Includes the sender node's ID by default.
		
package core.node
	NodeRuntime.java
		The static runtime for a node on the network. Has an initialize function that sets the global static NodeId and NodeState for the node running on the JVM. Thus, the NodeId and NodeState can be accessed statically from anywhere in the program.
	ServerNodeRuntime.java
		Does the same as NodeRuntime except also starts up a server to run on the given 
	NodeId.java
		General ID to identify a node on the network. A node corresponds to a single JVM. The ID is in the form of an integer.
	NodeState.java
		Interface for a NodeState. Thus, any mutable object can be a NodeState.

-------------------------------------------------------------------------------
		
OTHER FILE DESCRIPTION:
	server_node_mapping.txt
		This file lists all of the server nodes in the system and maps it to a host:port address on the network. This address is the host and port the server node is listening on; thus, other ndoes can use this mapping to figure out where to send messages to reach a server node when only the server node ID is known. Each line of the file is of the form "NodeID\thost:port".
		
-------------------------------------------------------------------------------

INSTALLATION TUTORIAL:

	To install and run on Windows from the .zip folder, first unzip the folder to the desired directory. Open a Windows shell to this directory, and set the Java path as follows (change to your local Java installation path):
		
		set PATH=C:\Program Files\Java\jdk1.7.0_18\bin;%PATH%
		
	Now, you can compile the BranchGui and BranchServer with the following two commands:
	
		javac .\src\bank\main\BranchServerRunner.java -sourcepath .\src -d .\bin
		javac .\src\bank\main\BranchGuiRunner.java -sourcepath .\src -d .\bin
	
	To run a BranchGui, use the following command. The XX is a 2 digit ID for the GUI; this 2 digit ID is used in the file "topology_file.txt" to identify the channels of the running GUI, and can be modified to be the desired topology.
	
		start java -cp "./bin" "bank/main/BranchGuiRunner" XX
		
	To run a BranchServer, use the following command. The XX is a 2 digit ID for the GUI; this 2 digit ID is used in the file "topology_file.txt" to identify the channels of the running GUI, and can be modified to be the desired topology. It is also used in the file "server_node_mapping.txt", where the 2 digit server ID is mapped to an address on the network the server is listening on. This file can be modified to have the desired amount of BranchServers listening on the desired ports.
	
		start java -cp "./bin" "bank/main/BranchServerRunner" XX
		
	The script "LAUNCH.cmd" performs the necessary commands to start BranchGui's and BranchServer's that correspond to the default "topology_file.txt" and "server_node_mapping.txt". The script will start 3 GUIs and 3 servers; servers 00 and 01 can communicate bidirectionally, and server 01 can send messages unidirectionally to server 02. GUI 50 connects to server 00, GUI 51 connects to server 01, and GUI 52 connects to 02. This configuration can be started using the default files by running the LAUNCH.cmd script.	
	
-------------------------------------------------------------------------------

TUTORIAL

The following describes the topology and test cases that can be made after running LAUNCH.cmd

In order to appropriately test all the cases, we have included a topology file that contains three branches (branch 00, branch 01, and branch 02) connected in this manner:

00->01
01->00
01->02

We also launch an ATM (GUI) with each branch above.
ATM 00050 goes with branch 00
ATM 00051 goes with branch 01
ATM 00052 goes with branch 02

Therefore, branch 0 and 1 can communicate with each other. Branch 1 has a unidirectional link to branch 2, and branch 2 has no links with branch 0. This topology layout covers all the possible cases of how the branches can be connected to each other. Either there is a uni-directional link both ways between the branches, or there is only one uni-directional link from one branch to another, or there are no links between two branches at all.

Inputs to the GUI:
	- Serial Number: An integer representing the unique serial number
	- Source Account Number: A number of the format bb.aaaaa, such as 00.12345, to mean branch 00 account 12345. This is the target account for deposits, withdraws, and queries, and is the source account in a transfer
	- Dest Account Number: Same as source account number, but only used as the destination in a transfer
	- Amount: The amount to use in the transaction. Ignored in a check balance query transaction.

The following interactions through the gui will thouroughly the cases covered in the topology above:

GENERAL TESTS (from any account):
   - Test any action on a branch with the wrong account. For example, using ATM 51, deposit to account 00.12345. This will fail since 51 is not connected to branch 00.
   - Test serial number field with a text input instead of a numeric input. Result is a rejection
   - Test the src account field with a text input instead of a numeric input. Result is a rejection
   - Test the src account field with an input that does not follow the bb.aaaaa format. Result is rejection
   - Test the dest account field with a text input instead of numeric input. Result is a rejection
   - Test the dest account field with an input that does not follow the bb.aaaaa format. Result is rejection
   - Test the amount field with a negative number. Result is rejection
   - Test the amount field with a non-numeric entry. Result is a rejection.

STATE CONSISTENCY:
    - Deposit 10$ into account 00.00000 with serial number 0 on ATM 50.
    - Try to perform another action (deposit,withdraw, check balance) with same serial number 0. This will be rejected.
    - Withdraw 5$ from this account with serial number 1. 
    - Check balance of this account with serial number 2. Will return $5.
	
TRANSFER TEST:
 (1) Testing transfer between two branches that can communicate with each other.
    - Since we know that there exist a uni-directional connection both ways between branch 00 and branch 01, that means 00.00050 and 01.00051 can make transfers between each other. So
	- Either on branch 00 or branch 01, using a different serial number, make a transfer of some amount from src account being either 00.00050 or 01.00051 to the destination account 01.00051 or 00.00050. On success of the transaction, go to the other branch to make sure it received the transfer by checking the balance on that account.
	- attempt doing a transfer with a previously used serial number. Will fail.
(2) Testing transfer between a branch that has a uni-directional link to another branch.
	- In this example, we know 01 can communicate with 02, but 02 can not communicate with 01. Therefore, if we try to transfer from 01 to 02, it should withdraw from our account, and should deposit into the account at 02, but we will never get a response.
(3) Testing a transfer when there is no link.
	If 00 tries to transfer to 02, the transfer should fail as no link exists between 00 and 02. However, the withdraw from 00 will occur. So we should observe that change.
	- Make a transfer from 00 to 02. Money should be withdrawn from the account at 00, but should not be deposited into 02. An error will come up.

