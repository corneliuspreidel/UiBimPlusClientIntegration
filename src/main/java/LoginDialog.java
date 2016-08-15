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
import bimplus.api.BimPlusHost;
import bimplus.api.ServerName;
import bimplus.utilities.LoginSettings;
import com.sun.corba.se.spi.activation.Server;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

public class LoginDialog extends JDialog
{
    // UI Stuff
    private final JLabel nameLabel = new JLabel("Name : ");
    private final JLabel passwordLabel = new JLabel("Password : ");

    public final JTextField nameField = new JTextField();
    public final JPasswordField passwordField = new JPasswordField();

    private final JButton okButton = new JButton("OK");
    private final JButton cancelButton = new JButton("Cancel");
    private final JComboBox<ServerName> environmentComboBox = new JComboBox<ServerName>();

    private final JCheckBox rememberMeCheckBox = new JCheckBox("RememberMe");

    private LoginSettings loginSettings;

    // BimPlus ApiCore for communication
    public ApiCore _core;

    public LoginDialog()
    {
        setupUI();
        setUpListeners();

        loginSettings = new LoginSettings();
        if(loginSettings.GetLoginSettings())
        {
            this.nameField.setText(loginSettings.username);
            this.passwordField.setText(loginSettings.password);
            BimPlusHost host = new BimPlusHost();
            host.setBimPlusEnvironment(ServerName.get(loginSettings.serverName));
            this.environmentComboBox.setSelectedItem(host);
            this.rememberMeCheckBox.setSelected(loginSettings.rememberMe);
        }
    }

    private void login() {

        // Get Connected to BimPlus
        if(ConnectToBimPlus(this.nameField.getText(), new String(this.passwordField.getPassword())))
        {
            if(rememberMeCheckBox.isSelected())
            {
                loginSettings.SaveLoginSettings(this.nameField.getText(), new String(this.passwordField.getPassword()), _core.connection.host, rememberMeCheckBox.isSelected());
            }
            else
            {
                // loginSettings.
            }
            System.out.println("You are logged in!");
            LoginDialog.this.setVisible(false);
        }
        else
        {
            System.out.println("Login didn't work!");
        }
    }

    private Boolean ConnectToBimPlus(String username, String password)
    {
        // Set the host
        BimPlusHost host = new BimPlusHost();
        if(environmentComboBox.getSelectedItem() != null)
            host.setBimPlusEnvironment((ServerName)environmentComboBox.getSelectedItem());
        else
            host.setBimPlusEnvironment(ServerName.Dev);

        // Create the ApiCore
        _core = new ApiCore(username, password, host);

        return _core.connected;
    }

    private void setupUI() {

        this.setTitle("Login");
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.pack();

        JPanel topPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(rememberMeCheckBox);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(4, 4, 4, 4);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        topPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        topPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        topPanel.add(passwordLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        topPanel.add(passwordField, gbc);

        // Environment Seledtion
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0;
        environmentComboBox.setVisible(false);
        environmentComboBox.addItem(ServerName.Dev);
        environmentComboBox.addItem(ServerName.Stage);
        environmentComboBox.addItem(ServerName.Prod);
        // environmentComboBox.setSelectedIndex(0);
        // comboBox.add(BimPlusHost)
        topPanel.add(environmentComboBox, gbc);

        this.add(topPanel);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setUpListeners() {

        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_D)
                {
                    if(environmentComboBox.isVisible())
                        environmentComboBox.setVisible(false);
                    else
                        environmentComboBox.setVisible(true);
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_D)
                {
                    if(environmentComboBox.isVisible())
                        environmentComboBox.setVisible(false);
                    else
                        environmentComboBox.setVisible(true);
                }
            }
        });

        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog.this.setVisible(false);
            }
        });
    }
}
