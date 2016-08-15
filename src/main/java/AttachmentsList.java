/*
 * Copyright (c) 2016 Cornelius Preidel
 *         Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *         The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
