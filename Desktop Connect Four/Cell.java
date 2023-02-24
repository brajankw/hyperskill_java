package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cell extends JButton {

    public Cell(String field) {
//        super(field);
        super(" ");
        setName("Button" + field);
        setPreferredSize(new Dimension(100, 100));
        setFocusPainted(true);
        setBackground(Color.green);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setOpaque(true);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Board.move(getName());
            }
        });
    }

    public void paintCell(String color){
        if(color.equals("green")){
            setBackground(Color.green);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        } else {
            setBackground(Color.red);
        }
    }

    @Override
    public String toString(){
        return getName();
    }

}
