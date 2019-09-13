package gui;

import game.Game;
import game.GameConstants;
import game.Player;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
/**
 * @author BS
 */
public class CompetitionWindow extends JFrame implements ActionListener, ScoreChangeEvent {
	private static final long serialVersionUID = 1L;
	
	private Competition competition;
    private ColumnGraph columnGraph;

    // widgets
    private JButton runWarButton;

	private boolean competitionRunning;

	private JCheckBox startPausedCheckBox;

	private Thread competitionThread;

    public CompetitionWindow(Competition competition) {
        super("TNCC 2 - Alexey Shapovalov & Ido Heinemann");
        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.competition = competition;
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

        }
        else {
            new Thread(this::playUntilStopped).start();
            competitionRunning = true;
        }

    }

    public void playUntilStopped(){

    }

    public void updateScore(int index, float value) {
        columnGraph.addToValue(index, value);
    }

    /**
     * Starts a new war.
     * @return whether or not a new war was started.
     */
    /*public boolean runWar() {
        try {
            long seedValue;
            if (seed.getText().startsWith(SEED_PREFIX)){
                seedValue = Long.parseLong(seed.getText().substring(SEED_PREFIX.length()));
            }
            else {
                seedValue = seed.getText().hashCode();
            }
            competition.setSeed(seedValue);
            final int battlesPerGroup = Integer.parseInt(
                battlesPerGroupField.getText().trim());
            final int warriorsPerGroup = Integer.parseInt(
                warriorsPerGroupField.getText().trim());
            if (competition.getWarriorRepository().getNumberOfGroups() < warriorsPerGroup) {
                JOptionPane.showMessageDialog(this,
                    "Not enough survivors (got " +
                    competition.getWarriorRepository().getNumberOfGroups() +
                    " but " + warriorsPerGroup + " are needed)");
                return false;
            }
            warThread = new Thread("CompetitionThread") {
                @Override
                public void run() {
                    try {
                        competition.runCompetition(battlesPerGroup, warriorsPerGroup, startPausedCheckBox.isSelected());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            };
            if (!competitionRunning) {
            	warThread.start();
            	return true;
            }
        } catch (NumberFormatException e2) {
            JOptionPane.showMessageDialog(this, "Error in configuration");
        }
        return false;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == runWarButton) {
        	showBattleFrameIfNeeded();
        	switch (runWarButton.getText().trim()){
                case "<html><font color=red>Start!</font></html>":
                    if (runWar()) {
                        competitionRunning = true;
                    }
                    break;
                case ("<html><font color=red>Stop!</font></html>"):
                    competition.setAbort(true);
                    competitionRunning = false;
                    break;
                default:
                    break;
            }
        }
    }


    public void onWarStart() {
    	showBattleFrameIfNeeded();
    }

    private void showBattleFrameIfNeeded() {
    	if (showBattleCheckBox.isSelected() && battleFrame == null ) {
    		showBattleRoom();
    		showBattleCheckBox.setSelected(false);
    	}
    }
    
    private void showBattleRoom() {
        competition.setSpeed(5);
        battleFrame = new WarFrame(competition);
        battleFrame.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
            }

            public void windowClosed(WindowEvent e) {
                //System.out.println("BattleFrame=null");
                battleFrame = null;
                competition.setSpeed(Competition.MAXIMUM_SPEED);
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        competition.addMemoryEventLister(battleFrame);
        competition.addCompetitionEventListener(battleFrame);
        Rectangle battleFrameRect = new Rectangle(0, getY(), 750, 700);
        Rectangle screen = getGraphicsConfiguration().getBounds(); //for multiple monitors
           
        if (getX() + getWidth() <= screen.getX() + screen.getWidth()
        		- battleFrameRect.width)
        {
        	battleFrameRect.x = getX() + getWidth();
        }
        else if (screen.getX() + screen.getWidth() - battleFrameRect.width
        	- getWidth() >= screen.getX())
        {
        	setLocation((int) (screen.getX() + screen.getWidth() - battleFrameRect.width
        			- getWidth()), getY());
        	battleFrameRect.x = getX() + getWidth();
        }
        else
        {
        	setLocation((int)screen.getX(), getY());
        	battleFrameRect.x = getWidth();
        }
               
        battleFrame.setBounds(battleFrameRect);
        battleFrame.setVisible(true);
    }

    public void onWarEnd(int reason, String winners) {
        warCounter++;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                warCounterDisplay.setText("Wars so far:" + warCounter +
                    " (out of " + totalWars + ")");
            };
        });
    }

    public void onRound(int round) {
    }

    public void onWarriorBirth(String warriorName) {
    }

    public void onWarriorDeath(String warriorName, String reason) {
    }

    public void onCompetitionStart() {
        warCounter = 0;
        totalWars = competition.getTotalNumberOfWars();
        competition.setAbort(false);
        runWarButton.setText("<html><font color=red>Stop!</font></html>");
    }

    public void onCompetitionEnd() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                warCounterDisplay.setText("The competition is over. " +
                    warCounter + " wars were run.");
            };
        });
        warThread = null;
        runWarButton.setText("<html><font color=red>Start!</font></html>");
		competitionRunning = false;
    }

	*/
}