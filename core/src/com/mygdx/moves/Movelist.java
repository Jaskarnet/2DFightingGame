package com.mygdx.moves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.commands.Player;
import com.mygdx.utils.json.FrameRangeData;
import com.mygdx.utils.json.TempData;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Movelist {
    private ArrayList<Move> movelist;
    private String moveInfoDirectory;
    private String spriteSheetDirectory;

    public Movelist(Player player) {
        int index = player.ordinal() + 1;
        if (index > 2) index = index - 2;

        // Zdefiniuj ścieżki do katalogów
        moveInfoDirectory = "moves/Fighter" + index + "/moveinfo/";
        spriteSheetDirectory = "moves/Fighter" + index + "/spritesheets/";

        movelist = new ArrayList<>();
        FileHandle assetsFile = Gdx.files.internal("assets.txt");
        String[] assetList = assetsFile.readString().split("\n");

        for (String asset : assetList) {
            if (asset.startsWith(moveInfoDirectory) && asset.endsWith(".json")) {
                processAsset(asset);
            }
        }
    }

    private void processAsset(String asset) {
        FileHandle file = Gdx.files.internal(asset);
        try {
            TempData moveInfo = new ObjectMapper().readValue(file.read(), TempData.class);
            String jsonFileName = file.name();
            String pngFileName = jsonFileName.replace(".json", ".png");
            String spriteSheetPath = spriteSheetDirectory + pngFileName;

            List<FrameRangeData> frameRangeDataList = moveInfo.getFrameRangeDataList();
            Move move = createMoveFromFrameRangeDataList(frameRangeDataList, spriteSheetPath);
            movelist.add(move);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Move createMoveFromFrameRangeDataList(List<FrameRangeData> frameRangeDataList, String spriteSheetPath) {
        Texture spriteSheet = new Texture(spriteSheetPath);
        Move move = new Move(spriteSheet, 0);

        for (FrameRangeData frameRangeData : frameRangeDataList) {
            Frame frame = frameRangeData.toFrame(spriteSheet);
            move.addFrame(frame);
        }

        return move;
    }

    public Move getMove(int index) {
        return movelist.get(index);
    }

    public int getMoveIndex(Move move) {
        return movelist.indexOf(move);
    }

    public ArrayList<Move> getMovelist() {
        return movelist;
    }
}