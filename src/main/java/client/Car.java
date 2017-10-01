package client;


import common.TransactionType;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Car {

    private final String licenseNumber;
    private JLabel label = new JLabel();

    {
        label.setFont(new Font("Arial", Font.BOLD, 24));
    }

    private Car(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            throw new RuntimeException("must provide a valid license-number");
        }
        Car car = new Car(args[0]);
        car.show();
        car.run();

    }

    private void doTransaction() {
        String json = null;
        try {
            json = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("transaction-template.tmpl"), Charset.forName("UTF-8"));
            json = json.replace("${license-number}", licenseNumber);
            json = json.replace("${date}", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date()));
            String amount = String.valueOf(new Random().nextInt(99) + 1);
            json = json.replace("${amount}", amount);
            String type = TransactionType.values()[new Random().nextInt(3)].name();
            json = json.replace("${type}", type);

            label.setText(licenseNumber + ", amount: " + amount + ", type: " + type);
            System.out.println(json);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost("http://localhost:7050/chaincode");
            StringEntity input = new StringEntity(json);
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);
            System.out.println(response.getStatusLine());
            httpClient.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void run() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                doTransaction();
            }
        }, 0, 5000);
    }

    private void show() throws IOException {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        BufferedImage image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("car1.jpg"));

        frame.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
        frame.add(label, BorderLayout.AFTER_LAST_LINE);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
