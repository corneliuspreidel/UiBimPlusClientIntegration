/**
 * Created by Cornelius on 03.08.2016.
 */

import bimplus.data.DtoProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class ProjectsList extends JPanel
{
    // Ui Stuff
    private final JList list;
    private final DefaultListModel model;
    private List<DtoProject> projects;

    // Return Value
    private DtoProject selectedProject;

    // PropertyChangedListener
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public ProjectsList()
    {
        setLayout(new BorderLayout());
        model = new DefaultListModel();
        list = new JList(model);
        JScrollPane pane = new JScrollPane(list);

        add(pane, BorderLayout.NORTH);

        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getClickCount() == 2)
                {
                    for (DtoProject project: projects)
                    {
                        if(project.getName().equals(list.getSelectedValue()))
                        {
                            selectedProject = project;
                            changes.firePropertyChange("selectedProject", null, project);
                            break;
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    public void setProjects(List<DtoProject> _projects)
    {
        model.clear();

        if(_projects.size() == 0)
            return;

        projects = _projects;
        for (DtoProject project: projects)
        {
            model.addElement(project.getName());
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
