/**
 * Created by Cornelius on 03.08.2016.
 */

import bimplus.api.ApiCore;
import bimplus.data.DtoIssue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class IssuesList extends JPanel
{
    private final JList list;
    private final DefaultListModel model;

    private ApiCore core;

    private List<DtoIssue> issues;
    private DtoIssue selectedIssue;

    // PropertyChangedListener
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public IssuesList()
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
                    for (DtoIssue issue: issues)
                    {
                        if(issue.getName().equals(list.getSelectedValue()))
                        {
                            selectedIssue = issue;
                            changes.firePropertyChange("selectedIssue", null, selectedIssue);
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

    public void setIssues(List<DtoIssue> _issues)
    {
        model.clear();

        if(_issues.size() == 0)
        {
            return;
        }

        issues = _issues;

        for (DtoIssue issue: issues)
        {
            model.addElement(issue.getName());
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
