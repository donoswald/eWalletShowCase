# eWalletShowCase

This show case is built upon the articel: https://www.ibm.com/developerworks/library/j-chaincode-for-java-developers/index.html. How to setup the environment, pls look there.

To run this ShowCase do the following
* Checkout the source code of Hyperledger Fabric
* Build the shim client library
* Build the eWalletShowCase
* Start the Hyperledger
* Run the eWalletShowCase chaincode
* Run the Car client
* Query the content of the blockchain via REST API

## Checkout the Source Code of Hyperledger Fabric
Since this show case is built up upon the v0.6 implementation of Hyperledger Fabric we have to check out the branch v0.6
```
donoswald@notpad$ git clone https://github.com/hyperledger/fabric.git -b v0.6
```
## Buid the Shim Client Library
Navigate to the shim library in the source code, and build it.
```
donoswald@notpad$ cd ../src/github.com/hyperledger/fabric/core/chaincode/shim/java
donoswald@notpad$ gradle clean
donoswald@notpad$ gradle build
```
 After this, the shim.jar is on your local maven repository.
 
 ## Build the Chaincode
 Go to the eWalletShowCase directory an execute the build script
 ```
 donoswald@notpad$ gradle clean
 donoswald@notpad$ gradle build
 ```
## Start Hyperledger
To start the Hyperledger itself, just execute the docker-compose script
```
donoswald@notpad$ docker-compose up
```
## Run the Chaincode
To run the chaincode, just run the mainclass from the distribution which gradle generated
```
donoswald@notpad$ cd build/distributions
donoswald@notpad$ unzip eWalletShowCase.zip
donoswald@notpad$ ./eWalletShowCase/bin/EWalletMain
```
## Run the Car Client
Import the project to your favorite IDE and create a run configuration vor the Car class, giving it a license-number as a startup parameter
```
donoswald@notpad$ java $CLASS_PATH client.Car zh-123-456
```
## Query the Blockchain
To query the blockchain, it is best to act with [SOAP UI](https://www.soapui.org/) 
Create a new endpoint to http://loclehost:7050/chaincode and execute the following POST request
```
{
"jsonrpc": "2.0",
  "method": "query",
  "params": {
    "type": 1,
    "chaincodeID":{
        "name": "eWallet"
    },
    "CtorMsg": {
        "args": ["query","zh-123-456"]
    }
  },
  "id": 2
}
```


