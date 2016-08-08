import bimplus.api.ApiCore;
import bimplus.data.DtoAttachment;
import bimplus.data.DtoDivision;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * Created by Cornelius on 05.08.2016.
 */
public class AttachmentsList extends JPanel
{
    private final JList list;
    private final DefaultListModel model;

    private ApiCore core;

    private List<DtoAttachment> attachments;
    private DtoAttachment selectedAttachment;

    // PropertyChangedListener
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public AttachmentsList()
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
                    for (DtoAttachment attachment: attachments)
                    {
                        if(attachment.getFileName().equals(list.getSelectedValue()))
                        {
                            selectedAttachment = attachment;
                            changes.firePropertyChange("selectedAttachment", null, selectedAttachment);
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

    public void setAttachments(List<DtoAttachment> _attachments)
    {
        model.clear();

        if(_attachments.size() == 0)
        {
            return;
        }

        attachments = _attachments;

        for (DtoAttachment attachment: attachments)
        {
            model.addElement(attachment.getFileName());
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
