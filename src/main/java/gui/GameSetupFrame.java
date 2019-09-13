package gui;

import game.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSetupFrame extends JFrame {

    JButton generateGame;
    JTable playersTable;
    JComboBox<String> playerRemover;
    JButton removePlayer;
    JTextField newPlayerName;
    JComboBox<String> aiChooser;
    JButton addPlayer;

    public GameSetupFrame(){
        super();

        generateGame = new JButton("Create Game");
        playersTable = new JTable();
        playerRemover = new JComboBox<>();
        removePlayer = new JButton("Remove Player");
        newPlayerName = new JTextField();
        aiChooser = new JComboBox<>();
        addPlayer = new JButton("Add Player");



    }

}
