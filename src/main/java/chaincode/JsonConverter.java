package chaincode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import common.TransactionType;

import java.io.IOException;

public class JsonConverter {


    private static final ObjectMapper mapper =new ObjectMapper();
    static{
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);    }

    static String fromObject(Transactions transactions) {

        try {
            return mapper.writeValueAsString(transactions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    static String fromObject(Transaction transaction) {

        try {
            return mapper.writeValueAsString(transaction);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    static <T> T toObject(String json, Class<T> clazz) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Transaction t = new Transaction();
        t.amount = 20;
        t.type = TransactionType.FUEL;


        String json = fromObject(t);
        System.out.println(json);
        Transaction transaction = toObject(json, Transaction.class);
        System.out.println(transaction);
    }

}

