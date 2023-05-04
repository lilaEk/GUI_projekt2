package model.map;

import model.characters.CharacterAnimationState;
import model.characters.Player;
import views.game.components.panels.gameWindow.GameplayMap;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import static model.map.MapModel.enemies;
import static model.map.MapModel.player;

public class MapComponentsRenderer extends DefaultTableCellRenderer {
    public MapComponentsRenderer() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        int cellSize = GameplayMap.cellSize;

        if (table.getValueAt(row, column).equals(1)) {
            JComponent component = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    g.setColor(Color.BLUE);
                    g.fillOval(0, 0, cellSize, cellSize);
                }
            };
            return component;
        }

        if (table.getValueAt(row, column).equals(3)) {
            return new JComponent() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    g.drawImage(player.getPlayerImage(Player.currentState), 0, 0, (int) (cellSize * 0.9), (int) (cellSize * 0.9), this);
                }
            };
        }

        if (table.getValueAt(row, column).equals(4)) {
            return new JComponent() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    g.drawImage(enemies.get(0).getEnemyImage(CharacterAnimationState.GhostBLUE), 0, 0, (int) (cellSize * 0.9), (int) (cellSize * 0.9), this);
                }
            };
        }

        if (table.getValueAt(row, column).equals(5)) {
            return new JComponent() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    g.drawImage(enemies.get(1).getEnemyImage(CharacterAnimationState.GhostPURPLE), 0, 0, (int) (cellSize * 0.9), (int) (cellSize * 0.9), this);
                }
            };
        }

        if (table.getValueAt(row, column).equals(6)) {
            return new JComponent() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    g.drawImage(enemies.get(2).getEnemyImage(CharacterAnimationState.GhostGREEN), 0, 0, (int) (cellSize * 0.9), (int) (cellSize * 0.9), this);
                }
            };
        }

        if (table.getValueAt(row, column).equals(7)) {
            return new JComponent() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    g.drawImage(enemies.get(3).getEnemyImage(CharacterAnimationState.GhostPINK), 0, 0, (int) (cellSize * 0.9), (int) (cellSize * 0.9), this);
                }
            };
        }

        return this;
    }


}
