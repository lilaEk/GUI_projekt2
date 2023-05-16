package model.characters;

import model.characters.components.GhostsColors;
import model.characters.components.MapTile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static model.DrawableObjects.addDrawable;
import static model.map.MapGenerator.*;
import static model.map.MapModel.player;

public class Enemy extends Character implements MapTile {

    public final GhostsColors color;
    private Image image;
    public int valueWhereIsStanding;
    int colorCode;
    boolean passedTheGate;

    public Enemy(int row, int column, GhostsColors color, int colorCode) {
        super(null);
        this.spawnLocationRow = row;
        this.spawnLocationClumn = column;
        this.colorCode = colorCode;

        this.currentRow = row;
        this.currentColumn = column;

        valueWhereIsStanding = 1000;

        this.color = color;

        passedTheGate = false;

        try {
            switch (color) {
                case PINK -> this.image = ImageIO.read(new File("assets/enemies_icons/pink/pink_2_straight.png"));
                case GREEN -> this.image = ImageIO.read(new File("assets/enemies_icons/green/green_2_straight.png"));
                case BLUE -> this.image = ImageIO.read(new File("assets/enemies_icons/blue/blue_2_straight.png"));
                default -> this.image = ImageIO.read(new File("assets/enemies_icons/purple/purple_2_straight.png"));
            }
        } catch (IOException e) {
            System.out.println("Nieprawidłowe zdjęcie.");
        }
        addDrawable(this.getMapCode(), this);
    }


    @Override
    public Image getAnimatonFrame(long deltaT) {
        return null;
    }


    @Override
    public int getMapCode() {
        return Integer.parseInt("4" + this.color.ordinal());
    }

    @Override
    public void moveHorizontally(int X) {
    }

    @Override
    public void moveVertically(int Y) {
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    public void updateAI(Enemy e) {
        if (mapModel != null) {
            setNewDestination(e);
        }
    }


    private void setNewDestination(Enemy e) {

        ArrayList<Integer> possibleDestinations = new ArrayList<>();
        possibleDestinations = checkDestinations(e);

        //jeśli nie ma żadnej drogi, zostań w miejscu
        if (possibleDestinations.size() == 0) {
            return;
        }

        //wybierz losowy kierunek spośród możliwych
        Random random = new Random();
        int randomIndex = random.nextInt(possibleDestinations.size());
        int chosenDestination = possibleDestinations.get(randomIndex);

        if (e.valueWhereIsStanding == gate) chosenDestination = 0;

        switch (chosenDestination) {
            case 0 -> { //do góry
                if (((int) mapModel.getValueAt(e.currentRow - 1, e.currentColumn) == gate)) {
                    if (!e.passedTheGate) {
                        e.passedTheGate = true;
                    } else {
                        break;
                    }
                }
                if (e.valueWhereIsStanding != 1000) {
                    mapModel.setValueAt(e.valueWhereIsStanding, e.currentRow, e.currentColumn);
                } else {
                    mapModel.setValueAt(pustePole, e.currentRow, e.currentColumn);
                }
                e.valueWhereIsStanding = (int) mapModel.getValueAt(e.currentRow - 1, e.currentColumn);
                e.setCurrentRow(e.currentRow - 1);
                mapModel.setValueAt(colorCode, e.currentRow, e.currentColumn);
                possibleDestinations = null;
            }
            case 1 -> { //w dół
                if (((int) mapModel.getValueAt(e.currentRow + 1, e.currentColumn) == gate)) {
                    if (!e.passedTheGate) {
                        e.passedTheGate = true;
                    } else {
                        break;
                    }
                }
                if (e.valueWhereIsStanding != 1000) {
                    mapModel.setValueAt(e.valueWhereIsStanding, e.currentRow, e.currentColumn);
                } else {
                    mapModel.setValueAt(pustePole, e.currentRow, e.currentColumn);
                }
                e.valueWhereIsStanding = (int) mapModel.getValueAt(e.currentRow + 1, e.currentColumn);
                e.setCurrentRow(e.currentRow + 1);
                mapModel.setValueAt(colorCode, e.currentRow, e.currentColumn);
                possibleDestinations = null;
            }
            case 2 -> { //w prawo
                if (((int) mapModel.getValueAt(e.currentRow, e.currentColumn + 1) == gate)) {
                    if (!e.passedTheGate) {
                        e.passedTheGate = true;
                    } else {
                        break;
                    }
                }
                if (e.valueWhereIsStanding != 1000) {
                    mapModel.setValueAt(e.valueWhereIsStanding, e.currentRow, e.currentColumn);
                } else {
                    mapModel.setValueAt(pustePole, e.currentRow, e.currentColumn);
                }
                e.valueWhereIsStanding = (int) mapModel.getValueAt(e.currentRow, e.currentColumn + 1);
                e.setCurrentColumn(e.currentColumn + 1);
                mapModel.setValueAt(colorCode, e.currentRow, e.currentColumn);
                possibleDestinations = null;
            }
            case 3 -> { //w lewo
                if (((int) mapModel.getValueAt(e.currentRow, e.currentColumn - 1) == gate)) {
                    if (!e.passedTheGate) {
                        e.passedTheGate = true;
                    } else {
                        break;
                    }
                }
                if (e.valueWhereIsStanding != 1000) {
                    mapModel.setValueAt(e.valueWhereIsStanding, e.currentRow, e.currentColumn);
                } else {
                    mapModel.setValueAt(pustePole, e.currentRow, e.currentColumn);
                }
                e.valueWhereIsStanding = (int) mapModel.getValueAt(e.currentRow, e.currentColumn - 1);
                e.setCurrentColumn(e.currentColumn - 1);
                mapModel.setValueAt(colorCode, e.currentRow, e.currentColumn);
                possibleDestinations = null;
            }
        }
    }


    private ArrayList<Integer> checkDestinations(Enemy e) {
        ArrayList<Integer> destinations = new ArrayList<>();
        int[] currentOptions = new int[4];

        //problem przy zjedzeniu wszystkich ciasteczek / z liczeniem czasu
        int currentValueUp = (int) mapModel.getValueAt(e.currentRow - 1, e.currentColumn); //0
        int currentValueDown = (int) mapModel.getValueAt(e.currentRow + 1, e.currentColumn); //1
        int currentValueRight = (int) mapModel.getValueAt(e.currentRow, e.currentColumn + 1); //2
        int currentValueLeft = (int) mapModel.getValueAt(e.currentRow, e.currentColumn - 1); //3

        currentOptions[0] = currentValueUp;
        currentOptions[1] = currentValueDown;
        currentOptions[2] = currentValueRight;
        currentOptions[3] = currentValueLeft;

        //sprawdzam czy jest możliwość ruchu
        if ((currentValueUp <= 15 || currentValueUp >= 40)
                && (currentValueDown <= 16 || currentValueDown >= 40)
                && (currentValueRight <= 15 || currentValueRight >= 40)
                && (currentValueLeft <= 15 || currentValueLeft >= 40)) { // 16 - bramka
            return destinations;
        }

        //jesli na trasie jest pacman to zignoruj inne opcje
        isPacmanOnWay(destinations, e);
        if (destinations.size() != 0) return destinations;

        //jesli nie ma pacmana, podaj wszystkie możliwe kierunki
        for (int i = 0; i < currentOptions.length; i++) {
            if (isDestinationFreeToGo(currentOptions, i)) {
                destinations.add(i);
            }
        }
        return destinations;
    }

    private ArrayList<Integer> isPacmanOnWay(ArrayList<Integer> destinations, Enemy e) { // sprawdzić czy da się tam bezposrednio dotrzec
        if (player.getCurrentRow() == e.currentRow) {
            if (player.getCurrentColumn() < e.currentColumn) {
                boolean thereIsSomethingOnWay = false;
                for (int i = e.currentColumn - 1; i >= player.currentColumn; i--) {
                    if (!mapModel.getValueAt(e.currentRow, i).equals(pustePole) || !mapModel.getValueAt(e.currentRow, i).equals(cookieBig)
                            || !mapModel.getValueAt(e.currentRow, i).equals(cookieSmall) || !mapModel.getValueAt(e.currentRow, i).equals(pacman)) {
                        thereIsSomethingOnWay = true;
                        break;
                    }
                }
                if (!thereIsSomethingOnWay) {
                    destinations.add(3);
                    return destinations;
                }
            }
            if (player.getCurrentColumn() > e.currentColumn) {

                boolean thereIsSomethingOnWay = false;
                for (int i = e.currentColumn + 1; i <= player.currentColumn; i++) {
                    if (!mapModel.getValueAt(e.currentRow, i).equals(pustePole) || !mapModel.getValueAt(e.currentRow, i).equals(cookieBig)
                            || !mapModel.getValueAt(e.currentRow, i).equals(cookieSmall) || !mapModel.getValueAt(e.currentRow, i).equals(pacman)) {
                        thereIsSomethingOnWay = true;
                        break;
                    }
                }
                if (!thereIsSomethingOnWay) {
                    destinations.add(2);
                    return destinations;
                }
            }
        }

        if (player.getCurrentColumn() == currentColumn) {
            if (player.getCurrentRow() < e.currentRow) {
                boolean thereIsSomethingOnWay = false;
                for (int i = e.currentRow - 1; i >= player.currentColumn; i--) {
                    if (!mapModel.getValueAt(i, e.currentColumn).equals(pustePole) || !mapModel.getValueAt(i, e.currentColumn).equals(cookieBig)
                            || !mapModel.getValueAt(i, e.currentColumn).equals(cookieSmall) || !mapModel.getValueAt(i, e.currentColumn).equals(pacman)) {
                        thereIsSomethingOnWay = true;
                        break;
                    }
                }
                if (!thereIsSomethingOnWay) {
                    destinations.add(1);
                    return destinations;
                }
            }
            if (player.getCurrentRow() > e.currentRow) {
                boolean thereIsSomethingOnWay = false;
                for (int i = e.currentRow + 1; i <= player.currentColumn; i++) {
                    if (!mapModel.getValueAt(i, e.currentColumn).equals(pustePole) || !mapModel.getValueAt(i, e.currentColumn).equals(cookieBig)
                            || !mapModel.getValueAt(i, e.currentColumn).equals(cookieSmall) || !mapModel.getValueAt(i, e.currentColumn).equals(pacman)) {
                        thereIsSomethingOnWay = true;
                        break;
                    }
                }
                if (!thereIsSomethingOnWay) {
                    destinations.add(0);
                    return destinations;
                }
            }
        }
        return destinations;
    }

    private boolean isDestinationFreeToGo(int[] currentOptions, int i) {

        return currentOptions[i] == pustePole || currentOptions[i] == cookieSmall || currentOptions[i] == cookieBig || currentOptions[i] == gate || currentOptions[i] == pacman;
    }
}