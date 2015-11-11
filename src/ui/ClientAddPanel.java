package ui;



import shop.Client;
import shop.Shop;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientAddPanel  extends JPanel {

    private Shop shop;
    private int age;
    private String selectGender;

    private class RBListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            selectGender = e.getActionCommand();
        }

    }

    public ClientAddPanel(final Shop shop, final ShopUI shopUI) {

        this.shop = shop;
        setLayout(new GridBagLayout());

        JLabel lName = new JLabel("New Client");
        add(lName, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        final JTextField lClient = new JTextField();
        lClient.setColumns(25);
        add(lClient, new GridBagConstraints(1, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lG = new JLabel("Set Gender");
        add(lG, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        ArrayList<String> gender = new ArrayList<>();
        gender.add("Male");
        gender.add("Female");
        ArrayList<String> modelRadioButton = gender;
        final ButtonGroup group = new ButtonGroup();
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(0,modelRadioButton.size()));
        radioPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        ActionListener rbListner = new RBListener();
        String genderName;
        for (int i =0; i < modelRadioButton.size(); i++) {
            genderName = modelRadioButton.get(i);
            JRadioButton rb = new JRadioButton(genderName);
            group.add(rb);
            radioPanel.add(rb);
            rb.setActionCommand(genderName);
            rb.addActionListener(rbListner);
        }
        add(radioPanel, new GridBagConstraints(1, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));


        JLabel lAge = new JLabel("Age");
        add(lAge, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinner.setMaximumSize(new Dimension(100, 20));
        ChangeListener listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner js = (JSpinner) e.getSource();
                age = (int) js.getValue();

            }
        };
        spinner.addChangeListener(listener);
        add(spinner, new GridBagConstraints(1, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lAdr = new JLabel("Address");
        add(lAdr, new GridBagConstraints(0, 3, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        final JTextField lAddress = new JTextField();
        lAddress.setColumns(25);
        add(lAddress, new GridBagConstraints(1, 3, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lPh = new JLabel("Telephone");
        add(lPh, new GridBagConstraints(0, 4, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        final JTextField lTelephone = new JTextField();
        lTelephone.setColumns(25);
        add(lTelephone, new GridBagConstraints(1, 4, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        JButton bANC = new JButton("Add New Client");
        add(bANC, new GridBagConstraints(1, 5, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        bANC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(lClient.getText().isEmpty()) {
                    JOptionPane.showMessageDialog((JFrame)shopUI.getFrame(), "You mus set new Client");
                } else {
                    Client cl = new Client(lClient.getText());
                    cl.setAge(age);
                    cl.setAddress(lAddress.getText());
                    cl.setTelephone(lTelephone.getText());
                    cl.setGender(selectGender);
                    cl.setIdClient(System.nanoTime());
                    shop.addNewClient(cl);
                    shopUI.showClientsForm();
                }
            }
        });
    }


}
