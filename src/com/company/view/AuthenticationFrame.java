package com.company.view;

import com.company.controller.UserController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class AuthenticationFrame extends JFrame {
    JTextField userText;
    JPasswordField passwordText;
    JButton loginButton;
    JButton cancelButton;
    UserController userController;

    public AuthenticationFrame() {
        setTitle("Authentification");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userController = new UserController();
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
        setLocationRelativeTo(null);
        setVisible(true);
        getRootPane().setDefaultButton(loginButton);

    }
    private void placeComponents(JPanel panel) {

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(10, 10, 100, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(110, 10, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Mot de passe");
        passwordLabel.setBounds(10, 40, 160, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(110, 40, 160, 25);
        passwordText.setEchoChar('*');
        panel.add(passwordText);

        loginButton = new JButton("login");
        loginButton.setBounds(60, 80, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                char[] password = passwordText.getPassword();
                String pwd = new String(password);
                if(username.isEmpty() || pwd.isEmpty()) {
                    JOptionPane.showMessageDialog(AuthenticationFrame.this,
                            "Veuillez remplir les champs.",
                            "Probléme d'authentification",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    userController.connect();
                    if(userController.userIsValid(username, pwd)) {
                        MainFrame mainFrame = new MainFrame();
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(AuthenticationFrame.this,
                                "username ou mot de passe est incorrect.",
                                "Probléme d'authentification",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (Exception e2){
                    e2.printStackTrace();
                }

            }
        });
        panel.add(loginButton);

        cancelButton = new JButton("cancel");
        cancelButton.setBounds(180, 80, 80, 25);
        cancelButton.setMnemonic(KeyEvent.VK_X);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(cancelButton);
    }

}
