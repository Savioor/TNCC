package botdata.botsgui;

import gameengine.Competition;
import gameengine.GameConstants;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
/**
 * @author BS
 */
public class CompetitionWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private Competition competition;
    private ColumnGraph columnGraph;

    private GameConstants constants;

    // widgets
    private JButton runWarButton;

	private boolean competitionRunning;
	private boolean competitionInterrupted;

	private JCheckBox startPausedCheckBox;

	private Thread competitionThread;

	private List<List<Integer>> permutations;

    public CompetitionWindow(Competition competition, GameConstants constants) {
        super("TNCC 2 - Alexey Shapovalov & Ido Heinemann");
        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.competition = competition;
        this.constants = constants;
        this.permutations = getAllPermutations(competition.getGroupNames().length - 1);
        competitionInterrupted = false;

        columnGraph = new ColumnGraph(competition.getGroupNames());
        getContentPane().add(columnGraph, BorderLayout.CENTER);
        // -------------
        JPanel controlArea = new JPanel();
        controlArea.setLayout(new BoxLayout(controlArea, BoxLayout.Y_AXIS));
        // -------------- Button Panel
        JPanel buttonPanel = new JPanel();
        runWarButton = new JButton("<html><font color=red>Start!</font></html>");
        runWarButton.addActionListener(this);
        buttonPanel.add(runWarButton);
        
        controlArea.add(buttonPanel);
        // -------------
        controlArea.add(new JSeparator(JSeparator.HORIZONTAL));
        
        // ------------
        getContentPane().add(controlArea, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(competitionRunning){
            runWarButton.setText("<html><font color=red>Start!</font></html>");
            competitionRunning = false;
            competitionInterrupted = true;
        }
        else {
            competitionThread = new Thread(this::playUntilStopped);
            competitionThread.start();
            competitionRunning = true;
            runWarButton.setText("<html><font color=red>Stop!</font></html>");
        }

    }

    public void playUntilStopped(){
        List<Integer> winners;
        for (List<Integer> permutation : permutations){
            winners = competition.runGame(permutation, constants);
            for (Integer i : winners){
                updateScore(i, 1.0f / winners.size());
            }
            if (competitionInterrupted){
                competitionInterrupted = false;
                return;
            }
        }
        runWarButton.setText("<html><font color=red>Start!</font></html>");
        competitionRunning = false;
        competitionInterrupted = false;
    }

    private static List<List<Integer>> getAllPermutations(int n){
        if (n > 6){
            throw new RuntimeException("getAllPermutations supports only n < 7");
        }
        if (n == 0){
            List<List<Integer>> retList = new ArrayList<>();
            retList.add(new ArrayList<>());
            retList.get(0).add(0);
            return retList;
        }
        List<List<Integer>> prevList = getAllPermutations(n - 1);
        List<List<Integer>> retList = new ArrayList<>();
        for (List<Integer> perm : prevList){
            for (int i = 0; i <= perm.size(); i++){
                perm.add(i, n);
                retList.add(new ArrayList<>(perm));
                final Integer remove = perm.remove(i);
            }
        }
        return retList;
    }

    private void updateScore(int index, float value) {
        columnGraph.addToValue(index, value);
    }
}