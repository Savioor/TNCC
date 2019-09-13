package gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author BS
 */
public class ColumnGraph extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private float[] values;
    private String[] names;
    private float maxValue;
    private double reduceFactor;

    private static final int NAME_HEIGHT = 14;

    public ColumnGraph(String[] names) {
        super();
        this.names = new String[names.length];
        // the first element holds the sum of all the other values
        values = new float[names.length];
        System.arraycopy(names, 0, this.names, 0, names.length);
        maxValue = 0;
        reduceFactor = 5;
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(500,500);
    }

    @Override
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }

    public void addToValue(int pos, float value) {
        values[pos] += value;

        if (values[pos] > maxValue) {
            // reset graph factor by half to make more room
            maxValue = values[pos];
            if (maxValue * reduceFactor > getSize().height-10) {
                reduceFactor *= 0.5;
            }
        }
        repaint();
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    protected void paintComponent(Graphics g) {
        Dimension d = getSize();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,d.width, d.height);
        d.setSize(d.width, d.height - NAME_HEIGHT);
        g.drawRect(0,0,d.width, d.height);
		final int numPlayers = names.length;
		int columnWidth = d.width / numPlayers;
        ColorHolder colorHolder= ColorHolder.getInstance();
        for (int i = 0; i < numPlayers; i++) {
            paintColumn(g, i, columnWidth, d.height, colorHolder);
            g.setColor(colorHolder.getColor(i, false));
            g.drawString(names[i], i*columnWidth+5, d.height+NAME_HEIGHT-2);
        }
    }

    private void paintColumn(Graphics g, int col, int width, int startHeight, ColorHolder colorHolder) {
        g.setColor(colorHolder.getColor(col, false));
        int height = (int) (reduceFactor*values[col]);
        g.fill3DRect(col*width, startHeight - height, width, height, true);
        g.drawString(""+values[col], col*width+5, startHeight-height- 5);
    }	
}
