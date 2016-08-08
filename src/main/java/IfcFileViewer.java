/**
 * Created by Cornelius on 03.08.2016.
 */

import bimplus.api.ApiCore;
import bimplus.data.DtoTeam;

import javax.swing.*;
import java.awt.*;

class IfcFileViewer extends JPanel
{
    private DefaultListModel model;
    private ApiCore core;

    public DtoTeam selectedTeam;

    public IfcFileViewer(String IfcFile)
    {
        setLayout(new BorderLayout());
        JTextPane textArea = new JTextPane();
        JScrollPane pane = new JScrollPane(textArea);

        JButton cancelButton = new JButton("Cancel");
        // Init
        textArea.setText(IfcFile);
        add(pane, BorderLayout.NORTH);
    }
}
