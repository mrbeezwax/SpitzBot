package io.github.mrbeezwax.jermobot.Data;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import io.github.mrbeezwax.jermobot.Currency.CrayonBank;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpreadsheetReader {
    private static final String APPLICATION_NAME = "Crayon Catalog";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "resources/tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String SPREADSHEETS_ID = "1oskxstIiOnJuaunpktmuCOrKwJaRBN5B-10q5aCmOp4";
    private static Sheets SERVICE;
    private static String range;
    private static String cellOfQuantity;

    private static void connect() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        SERVICE = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SpreadsheetReader.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private static int findItem(String ID, int quantity) {
        try {
            ValueRange response = SERVICE.spreadsheets().values()
                    .get(SPREADSHEETS_ID, range)
                    .execute();
            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty())
                return -1;
            for (List row : values) {
                String itemID = (String) row.get(0);
                if (itemID.equalsIgnoreCase(ID)) {
                    try {
                        if (quantity <= Integer.parseInt((String) row.get(2))) {
                            return quantity * Integer.parseInt((String) row.get(3));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Quantity cannot be converted to Integer");
                        return -1;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IOException with reading values");
        }
        return -1;
    }

    public static String buyItem(MessageReceivedEvent event, String ID, int quantity) {
        System.out.println("Buy Process Initialized");
        try {
            connect();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return "You broke the bot. Contact friendlynood";
        }
        if (ID.charAt(0) == 'P') range = "A3:D71";
        else if (ID.charAt(0) == 'S') range = "F3:I47";
        else if (ID.charAt(0) == 'M') range = "K3:N48";
        else {
            return "Item not found. Please enter a valid ID";
        }
        int totalCost = findItem(ID, quantity);
        if (totalCost == -1) return "Error making purchase";
        else {
            if (totalCost > CrayonBank.getBalance(event.getAuthor())) return "You do not have enough crayons for your purchase";
            CrayonBank.withdraw(totalCost, event.getAuthor());
            event.getGuild().getChannelByID(547309639314964480L).sendMessage("User " + event.getAuthor().getName() + " bought x" +
                    quantity + " " + ID + " for " + totalCost + " crayons.");
            return "Item(s) successfully purchased for " + totalCost + " crayons. Please message a moderator to claim your items.";
        }
    }

//    private static void updateSheet(int quantity) {
//        List<List<Object>> values = Arrays.asList(
//                Arrays.asList(),
//                // Additional cols ...
//                Arrays.asList(),
//                Arrays.asList(),
//                Arrays.asList("new value")
//        );
//        ValueRange body = new ValueRange()
//                .setValues(values)
//                .setMajorDimension("COLUMNS");
//        UpdateValuesResponse result =
//                null;
//        try {
//            result = SERVICE.spreadsheets().values().update(SPREADSHEETS_ID, range, body)
//                    .setValueInputOption("RAW")
//                    .execute();
//        } catch (IOException e) {
//            System.out.println("IOException with writing values");
//        }
//        System.out.printf("%d cells updated.", result.getUpdatedCells());
//    }
}
