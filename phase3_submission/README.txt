README


-------------------------------------------------------------------------------


SOURCE CODE FILE DESCRIPTION:

package bank.branch
	BranchApp.java
		Extends core.app.App. This is the main app that runs a single branch on a node. Holds the branch's BranchState and AppId. Contains request handlers to handle requests made by a separate app using a BranchClient. This file contains overloaded  handle methods that differ in the arguments they take which are the request objects generated in BranchClient.java(WithdrawRequest, DepositRequest, QueryRequest, TransferRequest). Each handle method handles the distinct Request objects appropriately. For example, the handle for a WithdrawRequest, calls the BranchState's withdraw method and waits for the BranchState's function to return success or failure, then the handle method returns a BranchResponse object with the balance of this account and whether it was successful.
	BranchClient.java
		Extends core.app.Client. This layer allows an app (most likely a BranchGuiApp or another BranchApp) to interact with any BranchApp by making different types of requests depending on what the user presses on the GUI. So if the user wants to withdraw, BranchClient's withdraw method created a WithdrawRequest and send the request to BranchServerHandler which will handle the request appropriately. Similar actions occur for the other requests possible: deposit, transfer, and query (check balance). 
	BranchState.java
		Extends core.app.AppState and represents a single BranchApp's current state. BranchState holds all the main methods for changing the state of an account which changes the state of the branch, hence the name BranchState. It contains methods like deposit, withdraw, query, and transfer. Each method will get the account or create a new account with the balance of 0.0 if it does not exist. It will then proceed by checking if the serial number that came with this request has been used in previous requests with this account. If it has not, then we will do the appropriate change (withdraw, deposit, transfer, check the balance), and add the serial number to the list of used serial numbers, then return.
	Account.java
		This file represents an account object. Each object stores the following account information: an accountId, an account balance, and a list of used serial numbers for the transactions originating from this account.
	AccountId.java
		This file represents an account id object. Since each account id is represented by 2 digit branch id, and a 5 digit account number, the AccountId object stores a branch id and an account number.

		
package bank.gui
	BranchGuiApp.java
		Extends core.app.App. This is the main GUI app that runs on a node. Simply starts up a BranchGuiController. Since its a unique app, is able to use a client to send requests and receive responses on its response buffer. Has no new request handlers.
	BranchGuiController.java
		Controller part of of the MVC model for GUI design. Does the functionality that is requested through user interaction with the BranchView or the GUI. Includes classes declaring listeners for Deposit, Withdraw, Query, Transfer, and TakeSnapshot transactions and appropriate processing for such to display on the GUI.
	BrancGuiView.java
		Class that sets up the buttons,textfields, and panes of the GUI that is displayed to the user and with which the user interacts.
	GuiSpecs.java
		Class containing static final variables that define GUI specifications.
		
		
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
	
	
package core.app
	App.java
		An App represents an individual program running on a node runtime. It is its own thread with a unique appID, and infinitely listens for requests on its requestBuffer. Could either be a primary or a backup. Generic type of the App is the "appstate" this app holds. This state is what is replicated to backups when a request happens. An app can either act a backup or a primary. Although this information is known, the App does not use it to make decisions. If it gets a SYNCH request, it assumes its a backup and performs the SYNCH. If it gets any other request (essentially UpdateHist), then it processes it accordingly and sends SYNCHs to all other nodes running this app (so it assumes we are primary and everyone else is backup). The AppID is unique in the sense that a specific reachable app has a unique ID, and all primaries and backups across different nodes will have the same appID. An app should simply define the constructor, a way to generate a new state (for new app creation), and then any handleRequest methods for each request this app can take in.
	AppId.java
		Represents a unique ID in the global space of all app IDs. This means that if multiple types apps are running in the system, no two can have the same app ID value. However, the same app with the same app ID can be running at multiple nodes on the system - these act as the primary and backups. Generically typed to be for a specific app class.
	AppState.java
		AppState class to represent any state that can be held in an app. The app itself will modify the state by handling requests, and the state will be replicated on all backups in a "SynchRequest".
	Client.java
		Generic client class. Should be used whenever one app wants to send a request to another app. Uses the sending app's response buffer as a serialization point for an app sending request, so an app can only have one request in flight at a time. Contains one method for an app to send a request to another app and block waiting for a response. If the response fails, the request is resent with an updated primary (updated by the configurator by the oracle). If no node is known to be running a primary of the app, then the request fails. The other method simply sends a request to a specific node (not an app) and can either block on a response or not. If block waiting for a response, uses the sending app's response buffer. If block waiting for a response and returns NULL, then it can be assumed the request failed.
		

package core.messages
	Fail.java
		Request to tell a server to fail. This is the only request that does not expect a response, since the node will be failed and won't be able to send a response.
	InitConnMessage.java
		Message that is sent when a socket to a node's network interface is first opened. Used to identify the node that opened the socket. This is the only message that is not a request or response.
	Message.java
		Generic message for the system. All messages record the sender's node ID and the receipient app ID (so the receiver will know what app to route the message to)
	NotifyFailure.java
		Request from the oracle to a node's configurator to notify that some node has failed. Will cause the configurator to register the failure, close any sockets to the remote node, and send back a NotifyFailureResponse as acknowledgment.
	NotifyFailureResponse.java
		An "ack" to a NotifyFailure request.
	NotifyRecovery.java
		Request from the oracle to a node's configurator to notify that some node that was previously failed has now recovered. Will cause the configurator to register the recovery, which brings the node back up as a backup for any apps on it. If the node receiving this request is the primary to an app that the recovered node is a backup for, will cause the configurator to "ping" that primary app which will trigger the primary app to synchronize the backup. Once all pings happen, this NotifyRecovery request is complete, and the configurator sends back a NotifyRecoveryResponse.
	NotifyRecoveryResponse.java
		An "ack" to a NotifyRecovery request.
	PingRequest.java
		A blank "ping" message sent to an app. Will cause the app to register the request as an "updateHist", and trigger a SYNCH to all backups. Once that happens, will send a PingResponse.
	PingResponse.java
		An "ack" to a PingRequest.
	Request.java
		An abstract class that represents a request that can be sent from on app to another. All requests have a senderAppId so the receiving node knows what app to send a response to. Also has a receiverAppId, just like all messages. When a node receives an incoming request, will use the receiverAppId to put the request on a specific app's request buffer. That app will know to send a response to this sending app's response buffer using the request's senderAppId.
	Response.java
		An abstract class that represents a response that can be sent from one app to another. All responses are associated with an original request. After an app handles a request and synchronizes, will then create a Response to send to the app that sent the request.
	SynchRequest.java
		A request that a primary app will send to a backup app (both will have the same AppId, but will be on different nodes). Contains a serialized AppState, so the backup can simply make the sent AppState its new AppState. Once this happens, the backup will send a SynchResponse back to the primary. Generic type is the type of AppState the request holds.
	SynchResponse.java		
		A response that a backup will sent back to a primary after finishing handling a SynchRequest.
	
		 
package core.network
	MessageListener.java
		A thread that runs continuously on a node listening for incoming messages from one specific remote node. When it gets a new message, retrieves the app running on this node that the message is meant for. Then, if request, puts the message on that app's request buffer. If its a response, puts it on the app's response buffer.
	NetworkInterface.java
		Wrapper class to send and receive messages on the network. When doing a send, checks through the topology that the sending node has a channel to the destination. The functions are synchronized based on remote node, so no two network operations to the same node can occur simultaneously and no two network operations receiving from the same node can occur simultaneously. Contains a main loop that will continuously listen on this node's server socket for new incoming channels. When a new socket is established, spawns a MessageListener thread to continuously get messages from the socket.
	Topology.java
		Class to hold topology and node mapping information to be retrieved statically from anywhere in the code. Parses the two files "topology_file.txt" and "node_mapping.txt". Provides functions to retrieve a host:port from a NodeId and to check if the current node can send or receive from another NodeId.
	
	
package core.node
	AppManager.java
		Class to hold mappings from AppIDs to NodeIDs. Parses the APP_FILE "apps.txt" to figure out what apps run on what nodes, including which apps to start on this node. This app to node mapping is modified whenever the configurator is notified of a failure or recovery. Also holds this runtime's specific apps, creates all apps, and starts all app threads. As mentioned above, creates apps based on the APP_FILE.
	Configurator.java
		Configurator class that is on all nodes (besides the oracle node). Responsible for handing all requests from the oracle to this node, specifically to fail, notify fail, and notify recover. Makes appropriate changes to the AppManager on the appropriate requests. The configurator has an appID of null. Has a function "initialize" that sends the oracle a subscription request for each node of interest. If the response tells us that node is failed, registers it as failed with the AppManager. "Nodes of interest" are defined as any node connected to us in the topology, since if any of those fail we must register it to know to close the sockets. Will subscribe to ourselves, so that on startup we can know if we are technically still failed in the overall system; this is to trigger ourselves as still failed and thus removing ourselves from any primaries.
	ConfiguratorClient.java
		Client for the oracle to use to send messages to a node's Configurator.
	NodeId.java
		ID to identify a node on the network. A node corresponds to a single JVM running a single NodeRuntime. The ID is in the form of an integer. The node then starts up multiple apps with different AppIds. It is important to understand the difference between NodeIDs and AppIDs.
	NodeRuntime.java
		The static runtime for a JVM node on the network. Holds static variables to be accessed any time from any thread on the node - specifically, the node ID, the NetworkInterface, the AppManager, the configurator, and the oracle's AppId. When run, will start a NetworkInterface thread to take in incoming sockets, a Configurator app thread that will be able to interact with the oracle, and finally one thread for each app on the system.
	NodeRunner.java
		Starts up a node with the given NodeID. With this, the AppManager can figure out what apps to start on this node, its topology, and its server listening IP address.
		

package oracle
	OracleApp.java
		An app representing an oracle. Can only take in 1 kind of request - to register a new subscription. Its app state contains the GUI controller which contains the overall OracleState with the failed node information.
	OracleClient.java
		Client class for any Configurator to send a SubscribeRequest to the oracle.
	OracleGuiController.java
		MVC controller for the oracle's GUI
	OracleGuiView.java
		MVC view for the oracle's GUI
	OracleState.java
		The current state of all failed nodes in the system and state of what nodes are subscribed to what. All methods are synchronized so all notifications and system state manipulations are serialized.

	
package oracle.messages
	SubscribeRequest.java
		Request that configurator sends to an oracle when a node starts up. Contains a nodeOfInterest node id. When oracle gets this request, will mark the sender as subscribed to nodeOfInterest, so the sender will receive any failure or recovery information about the nodeOfInterest. Oracle will send back a SubscribeResponse. This response holds whether or not the nodeOfInterest is already failed, so the sending node will have an up to date system view on startup.
	SubscribeResponse.java
		Response to a SubscribeRequest. Oracle sends this back to a node that just subscribed to a nodeOfInterest. If the nodeOfInterest is already failed, this response will indicate so.
		

-------------------------------------------------------------------------------
		
		
OTHER FILE DESCRIPTION:
	topology_file.txt
		Indicated what nodes are connected. A line of this file is of the form "NodeID1 NodeID2" and indicated a bidirectional channel between NodeID1 and NodeID2.
	nodes.txt
		This file lists all of the server nodes in the system and maps it to a host:port address on the network. This address is the host and port the node is listening on; thus, other ndoes can use this mapping to figure out where to send messages to reach a node when only the server node ID is known. Each line of the file is of the form "NodeID host:port".
	apps.txt
		Defines all apps that run in the distributed environment. An App is defined by a unique AppID, an App class name, and a list of nodes that run the app. The first node on the list is the original primary, and the rest are the backups. Thus, each line of the file is of the form  "ID path.to.App nodePrimary nodeBackup1 nodeBackup2 ... nodeBackupN". For example, if this line is present, then an app with AppID 01, defined by bank.branch.BranchApp, will run on nodes 100 (the primary), 101, and 102 - "01 bank.branch.BranchApp 100 101 102"
	LAUNCH_ONE.cmd
		A batch script that starts up one node with the given NodeID from the command line. Used for the Oracle to start up one specific node.
		
		
-------------------------------------------------------------------------------


INSTALLATION TUTORIAL:

	To install and run on Windows from the .zip folder, first unzip the folder to the desired directory. Open a Windows shell to this directory, and set the Java path as follows (change to your local Java installation path):
		
		set PATH=C:\Program Files\Java\jdk1.7.0_18\bin;%PATH%
		
	Now, you can compile the BranchGui, BranchServer, and Oracle with the following three sets of commands:
		javac .\src\core\node\NodeRunner.java -sourcepath .\src -d .\bin
		javac .\src\bank\branch\BranchApp.java -sourcepath .\src -d .\bin
		
		javac .\src\core\node\NodeRunner.java -sourcepath .\src -d .\bin		
		javac .\src\bank\gui\BranchGuiApp.java -sourcepath .\src -d .\bin
		
		javac .\src\core\node\NodeRunner.java -sourcepath .\src -d .\bin		
		javac .\src\oracle\OracleApp.java -sourcepath .\src -d .\bin
	
	In this phase, we have created a generic NodeRunner class that launches any app as specified in the apps.txt folder. An app will specify a NodeId that is associated with it. If an app is a BranchServer (aka BranchApp) app, it will specify a primary server(NodeId) first and then any backup server NodeIds. This is an example:
		AppId	AppName  		    	NodeIDs
		A1		bank.gui.BranchGuiApp   XXX
		A2		bank.branch.BranchApp  	XXP XXB XXB2
		A3		orace.OracleApp 	    XXO
		
	Therefore, to launch any app you only need to specify a NodeID in the following command. The XXX is the 3 digit id that is used to identify each node. This 3 digit id can be found under the NodeIDs column in in apps.txt. 
	
		start java -cp "./bin" "core/node/NodeRunner" XXX

	Therefore, to launch in the example above we would need to use the above command and use XXX,XXP, XXB,XXB2,XXO as inputs to launch all of those branch, oracle, and branch gui apps.
		
	The script "LAUNCH.cmd" performs the necessary commands to start BranchGui's and BranchServer's that correspond to the default "topology_file.txt" and "nodes.txt" and "apps.txt". The "topology_file.txt" represents physical links that all the nodes have between each other. The script will start 3 GUIs and 3 servers. The GUIS have physical links to all the servers. Furthermore, "apps.txt" specifies that server 100 will serve as primary for branch 00, 101 for branch 01, and 102 for branch 02. For each branch, the other servers will serve as backups. This configuration can be started using the default files by running the LAUNCH.cmd script.


-------------------------------------------------------------------------------


TUTORIAL


-------------------------------------------------------------------------------


TEAM MEMBERS 

PHASE 1: Vera Kutsenko & Jeremy Fein
PHASE 2: Vera Kutsenko & Jeremy Fein
