# eWalletShowCase

This show case is built upon the articel: https://www.ibm.com/developerworks/library/j-chaincode-for-java-developers/index.html. How to setup the environment, pls look there.

To run this ShowCase do the following
* Checkout the source code of Hyperledger Fabric
* Build the shim client library
* Build the eWalletShowCase
* Run the eWalletShowCase chaincode
* Run the eWalletShowCase client
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
 
