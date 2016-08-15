/*
 * Copyright (c) 2016 Cornelius Preidel
 *         Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *         The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * Created by Cornelius on 03.08.2016.
 */

import bimplus.api.ApiCore;
import bimplus.data.DtoDivision;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class DivisionsTable extends JPanel
{
    private final JTable table;
    private final DefaultTableModel model;

    private List<DtoDivision> divisions;
    private DtoDivision selectedDivision;

    private ApiCore core;

    // DownloadButton
    private final JButton downloadButton;

    // PropertyChangedListener
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public DivisionsTable(ApiCore _core)
    {
        core = _core;

        setLayout(new BorderLayout());

        // UI
        downloadButton = new JButton("Download IfcFile for selected Division");
        downloadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DownloadIfcFile();
            }
        });

        model = new DefaultTableModel();

        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        // Disable Edit Mode
        table.setDefaultEditor(Object.class, null);

        add(pane, BorderLayout.NORTH);

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getClickCount() == 2)
                {
                    for (DtoDivision division: divisions)
                    {
                        if(division.id.equals(table.getValueAt(table.getSelectedRow(),1)))
                        {
                            selectedDivision = division;
                            changes.firePropertyChange("selectedDivision", null, selectedDivision);
                            StartBrowser();
                            break;
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void setDivisions(List<DtoDivision> _divisions)
    {
        divisions = _divisions;

        // Clear the model
        model.setRowCount(0);

        model.setColumnIdentifiers(new String[]{"Name","Id","InputType","Created","Changed"});

        for (DtoDivision division: divisions)
        {
            model.addRow(new String[]{division.getName(), division.GetId(), division.getInputType(), division.getCreated().toString(), division.getChanged().toString()});
        }
    }

    private void DownloadIfcFile()
    {
        if(selectedDivision != null)
        {
            // Divisions divisionApi = new DivisionsList(_core);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No Division selected!");
        }
    }

    private void StartBrowser()
    {
        try
        {
            if(Desktop.isDesktopSupported())
            {
                Desktop.getDesktop().browse(new URI(GetPortalAdress() + "/?embedded=1&token=" + core.connection.access_token + "/#/viewer?external_client=ClientProxy:BIF&project_id=" + selectedDivision.getProjectId() + "&team_id=" + core.currentTeam.id));
            }
        }
        catch(URISyntaxException e){        }
        catch (IOException e){        }
    }

    // PropertyChangedListeners
    public void addPropertyChangeListener(PropertyChangeListener l)
    {
        changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l)
    {
        changes.removePropertyChangeListener(l);
    }

    private String GetPortalAdress()
    {
        String currentServer = core.connection.host.getServerName();
        String portal = "";

        switch (currentServer)
        {
            // DEV
            case "https://api-dev.bimplus.net":
                portal = "https://portal-dev.bimplus.net";
                break;
            // STAGE
            case "https://api-stage.bimplus.net":
                portal = "https://portal-stage.bimplus.net";
                break;
            // PROD
            case "https://api.bimplus.net":
                portal = "https://portal.bimplus.net";
                break;
            // Localhost
            case "http://localhost/bimplus.server.web":
                portal = "http://localhost:3000";
                break;
        }

        return portal;
    }

}
