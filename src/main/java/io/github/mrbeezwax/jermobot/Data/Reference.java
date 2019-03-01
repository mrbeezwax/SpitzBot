package io.github.mrbeezwax.jermobot.Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.mrbeezwax.jermobot.Main;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Reference {
    private static final File PLAYER_CRAYONS = new File("resources/playercrayons.json");
    private static final File PLAYER_COOLDOWN = new File("resources/playercooldown.json");
    private static final File PLAYER_CRAYONLIMIT = new File("resources/playercrayonlimit.json");
    public static String readToken()
    {
        File file = new File("token.txt");
        try {
            Scanner scanner = new Scanner(file);

            String token = scanner.nextLine(); // only reads line

            scanner.close();
            return token;
        }
        catch (FileNotFoundException p) {
            return "TOKEN NOT FOUND";
        }
    }

    /**
     * Loads all files
     */
    public static void loadFiles() {
        try {
            if (!PLAYER_CRAYONS.exists()) {
                writeJson(new HashMap<Long, Integer>(), new FileWriter(PLAYER_CRAYONS));
                System.out.println("Player Crayons File does not exist. Creating file...");
            }
            if (!PLAYER_COOLDOWN.exists()) {
                writeJson(new HashMap<Long, Date>(), new FileWriter(PLAYER_COOLDOWN));
                System.out.println("Player Cooldown File does not exist. Creating file...");
            }
            if (!PLAYER_CRAYONLIMIT.exists()) {
                writeJson(new HashMap<Long, Integer>(), new FileWriter(PLAYER_CRAYONLIMIT));
                System.out.println("Player Crayon Limit File does not exist. Creating file...");
            }

            Main.playerCrayons = readJson(new TypeToken<HashMap<Long, Integer>>(){}, new FileReader(PLAYER_CRAYONS));
            Main.playerCooldown = readJson(new TypeToken<HashMap<Long, Date>>(){}, new FileReader(PLAYER_COOLDOWN));
            Main.playerCrayonLimit = readJson(new TypeToken<HashMap<Long, Integer>>(){}, new FileReader(PLAYER_CRAYONLIMIT));
            System.out.println("Files loaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all files
     */
    public static void saveFiles() {
        try {
            writeJson(Main.playerCrayons, new FileWriter(PLAYER_CRAYONS));
            writeJson(Main.playerCooldown, new FileWriter(PLAYER_COOLDOWN));
            writeJson(Main.playerCrayonLimit, new FileWriter(PLAYER_CRAYONLIMIT));
            System.out.println("Files saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes in a map and filewriter to create a json file
     * @param map A HashMap
     * @param writer A FileWriter Object
     */
    private static void writeJson(HashMap map, FileWriter writer) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes in a filereader of a file and converts it into a HashMap
     * @param fr A FileReader Object
     * @return HashMap
     */
    private static HashMap readJson(TypeToken typeToken, FileReader fr) {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(fr);
        return gson.fromJson(br, typeToken.getType());
    }

//    private static void convertDatToJson() {
//        try {
//            writeJson(loadPlayerCrayons(), new FileWriter(PLAYER_CRAYONS));
//            writeJson(loadPlayerCooldown(), new FileWriter(PLAYER_COOLDOWN));
//            writeJson(loadPlayerCrayonLimit(), new FileWriter(PLAYER_CRAYONLIMIT));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        convertDatToJson();
//    }
}
