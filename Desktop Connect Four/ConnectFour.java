package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ConnectFour extends JFrame {
    private class ButtonReset extends JButton {
        public ButtonReset() {
            super("Reset");
            setName("ButtonReset");
            setPreferredSize(new Dimension(50, 15));
            setFocusPainted(false);
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Board.reset();
                }
            });
        }
    }

    public ConnectFour() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Connect Four");
        setMinimumSize(new Dimension(700, 615));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(createCells());

        JPanel reset = new JPanel();
        reset.setMaximumSize(new Dimension(700, 15));
        reset.setLayout(new FlowLayout(FlowLayout.RIGHT));
        reset.add(new ButtonReset());
        mainPanel.add(reset, BorderLayout.PAGE_END);

        add(mainPanel);
        pack();
        setVisible(true);
    }

    private JPanel createCells() {
        HashMap<String, ArrayList<Cell>> cells = new HashMap<>() {{
           put("A", new ArrayList<>());
           put("B", new ArrayList<>());
           put("C", new ArrayList<>());
           put("D", new ArrayList<>());
           put("E", new ArrayList<>());
           put("F", new ArrayList<>());
           put("G", new ArrayList<>());
        }};

        JPanel cellsGrid = new JPanel();
        cellsGrid.setLayout(new GridLayout(6, 7, 0, 0));
        cellsGrid.setMinimumSize(new Dimension(700, 600));

        for (int i = 6; i >= 1; i--) {
            for (String key: cells.keySet()) {
                String name = key + i;
                Cell cell = new Cell(name);

                cellsGrid.add(cell);

                cells.get(key).add(cell);

            }
        }
        for(String key: cells.keySet()){
            Collections.reverse(cells.get(key));
        }

        Board board = new Board(cells);
        System.out.println(board);


        return cellsGrid;
    }
}