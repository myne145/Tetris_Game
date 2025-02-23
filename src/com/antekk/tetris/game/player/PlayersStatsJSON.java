package com.antekk.tetris.game.player;

import com.antekk.tetris.view.ErrorDialog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.util.ArrayList;

public class PlayersStatsJSON {
    private File playersFile = new File("./best_players.json");
    private JSONArray players;

    private void initialize() throws IOException {
        if(!playersFile.exists() && !playersFile.createNewFile()) {
            throw new FileSystemException("Cannot create " + playersFile.getAbsolutePath() + " file.");
        }

        StringBuilder jsonText = new StringBuilder();
        for(String s : Files.readAllLines(playersFile.toPath())) {
            jsonText.append(s).append("\n");
        }

        try {
            players = new JSONArray(jsonText.toString());
        } catch (JSONException e) {
            players = new JSONArray();
            writePlayersToFile();
        }
    }

    private TetrisPlayer parsePlayerAt(int index) {
        JSONObject playerAtIndex = players.getJSONObject(index); //TODO exception handling here

        int loadedScore;
        int loadedLinesCleared;
        int loadedLevel;
        String loadedName;

        try {
            loadedScore = playerAtIndex.getInt("score");
            loadedLinesCleared = playerAtIndex.getInt("lines_cleared");
            loadedLevel = playerAtIndex.getInt("level");
            loadedName = playerAtIndex.getString("name");
        } catch (Exception e) {
            new ErrorDialog("Missing JSON key in players statistics file!", e);
            return null;
        }

        TetrisPlayer player = new TetrisPlayer(
                loadedScore,loadedLinesCleared,loadedLevel,loadedName
        );


        return player;
    }

    private JSONObject createPlayerJSONObject(TetrisPlayer player) {
        JSONObject object = new JSONObject();
        object.put("score", player.score);
        object.put("lines_cleared", player.linesCleared);
        object.put("level", player.level);
        object.put("name", player.name);
        return object;
    }

    public void addPlayer(TetrisPlayer player) {
        if(players == null) {
            throw new JSONException("Empty players JSON file!");
        }

        players.put(createPlayerJSONObject(player));
        writePlayersToFile();
    }

    private void writePlayersToFile() {
        try(FileWriter writer = new FileWriter(playersFile)) {
            writer.write(players.toString(4));
        } catch (IOException e) {
            new ErrorDialog("Cannot write to player statistics file!", e);
        }
    }

    public ArrayList<TetrisPlayer> getPlayers() {
        ArrayList<TetrisPlayer> result = new ArrayList<>();
        for(int i = 0; i < players.length(); i++) {
            result.add(parsePlayerAt(i));
        }
        result.sort((o1, o2) -> (int) (o2.score - o1.score));
        return result;
    }

    public PlayersStatsJSON() {
        try {
            initialize();
        } catch (IOException e) {
            new ErrorDialog("Cannot initialize players statistics file!", e);
        }
    }

}
