/**
 * Created by Cornelius on 03.08.2016.
 */

import bimplus.api.ApiCore;
import bimplus.data.DtoDivision;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class DivisionsList extends JPanel
{
    private final JList list;
    private final DefaultListModel model;

    private ApiCore core;

    private List<DtoDivision> divisions;
    private DtoDivision selectedDivision;

    // PropertyChangedListener
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public DivisionsList()
    {
        setLayout(new BorderLayout());
        model = new DefaultListModel();
        list = new JList(model);
        JScrollPane pane = new JScrollPane(list);

        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getClickCount() == 2)
                {
                    for (DtoDivision division: divisions)
                    {
                        if(division.getName().equals(list.getSelectedValue()))
                        {
                            selectedDivision = division;
                            changes.firePropertyChange("selectedDivision", null, selectedDivision);
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

        add(pane, BorderLayout.NORTH);
    }

    public void setDivisions(List<DtoDivision> _divisions)
    {
        model.clear();

        if(_divisions.size() == 0)
        {
            return;
        }

        divisions = _divisions;

        for (DtoDivision division: divisions)
        {
            model.addElement(division.getName());
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
