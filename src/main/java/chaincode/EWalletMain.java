/*
Copyright DTCC 2016 All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


package chaincode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.java.shim.ChaincodeBase;
import org.hyperledger.java.shim.ChaincodeStub;


public class EWalletMain extends ChaincodeBase {
    private static Log log = LogFactory.getLog(EWalletMain.class);

    @Override
    public String run(ChaincodeStub stub, String function, String[] args) {
        log.info("In run, function:" + function);
        log.info("args: "+String.join(",",args));
        switch (function) {
            case "put":
                String licenceNumber = args[0];
                Transactions transactions = getTransactions(stub, licenceNumber);
                Transaction transaction = JsonConverter.toObject(args[1], Transaction.class);
                transactions.transactions.add(transaction);
                String state = JsonConverter.fromObject(transactions);

                stub.putState(licenceNumber, state);
        }
        return null;
    }

    private Transactions getTransactions(ChaincodeStub stub, String licenceNumber) {
        String state = stub.getState(licenceNumber);
        if (state == null || state.length() == 0) {
            return new Transactions();
        }
        return JsonConverter.toObject(state, Transactions.class);
    }

    @Override
    public String query(ChaincodeStub stub, String function, String[] args) {
        log.info("query");
        log.debug("query:" + args[0] + "=" + stub.getState(args[0]));
        if (stub.getState(args[0]) != null && !stub.getState(args[0]).isEmpty()) {
            return stub.getState(args[0]);
        }
        return args[0];
    }

    @Override
    public String getChaincodeID() {
        return "eWallet";
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello eWallet! starting " + args);
        log.info("starting");
        new EWalletMain().start(args);
    }


}
