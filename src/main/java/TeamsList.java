/**
 * Created by Cornelius on 03.08.2016.
 */
import bimplus.data.DtoTeam;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javax.swing.*;

public class TeamsList extends JPanel
{
    // Ui Stuff
    private final JList list;
    private final DefaultListModel model;

    private List<DtoTeam> teams;
    // Return Value
    private DtoTeam selectedTeam;

    // PropertyChangedListener
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public TeamsList()
    {
        setLayout(new BorderLayout());
        model = new DefaultListModel();
        list = new JList(model);
        JScrollPane pane = new JScrollPane(list);

        // Add it to the pane ...
        add(pane, BorderLayout.NORTH);

        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getClickCount() == 2)
                {
                    for (DtoTeam team: teams)
                    {
                        if(team.GetDisplayName().equals(list.getSelectedValue()))
                        {
                            selectedTeam = team;
                            changes.firePropertyChange("selectedTeam", null, team);
                            break;
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {            }

            @Override
            public void mouseReleased(MouseEvent e) {            }

            @Override
            public void mouseEntered(MouseEvent e) {            }

            @Override
            public void mouseExited(MouseEvent e) {            }
        });
    }

    public void setTeams(List<DtoTeam> _teams)
    {
        teams = _teams;
        model.clear();

        for (DtoTeam team: teams)
        {
            model.addElement(team.GetDisplayName());
        }
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
}
