package ru.surin;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.IntellitypeListener;
import com.melloware.jintellitype.JIntellitype;

import javax.swing.*;

public class MainFrame extends JFrame implements HotkeyListener, IntellitypeListener {
    private static final RobotThread ROBOT_THREAD = new RobotThread("RobotThread");
    private static final KeyIntDict DEFAULT_START_KEY = KeyIntDict.F4;
    private static final KeyIntDict DEFAULT_STOP_KEY = KeyIntDict.F3;
    private static final KeyIntDict DEFAULT_SHIFT_TOGGLE_KEY = KeyIntDict.F2;
    private static final int SHIFT = 100;
    private static final int W = 50;

    private boolean onceStarted = false;

    private KeyIntDict registeredStartKey = DEFAULT_START_KEY;
    private KeyIntDict registeredStopKey = DEFAULT_STOP_KEY;
    private KeyIntDict registeredShiftToggleKey = DEFAULT_SHIFT_TOGGLE_KEY;

    private JTextField fStartKeyTextField;
    private JTextField fStopKeyTextField;
    private JTextField shiftToggleTextField;
    private JCheckBox shiftRunCheckbox;
    private JCheckBox isRobotActiveCheckbox;
    private JLabel alertLabel;
    private JButton saveButton;


    // initializing using constructor
    MainFrame() {
        initComponents();
        addListeners();
        addHotKeys(DEFAULT_START_KEY, DEFAULT_STOP_KEY, DEFAULT_SHIFT_TOGGLE_KEY);
    }

    @Override
    public void onIntellitype(int command) {
        //some shit
    }


    @Override
    public void onHotKey(int identifier) {
        if (isStartIdentifier(identifier)) { //85
            startRobot();
        } else
        if (isStopIdentifier(identifier)) { //84 184 134 234
            stopRobot();
        } else
        if (isShiftIdentifier(identifier)) { //84 184 134 234
            setShiftRunHotKey();
        }
    }

    private void initComponents() {

        alertLabel = new JLabel();
        alertLabel.setBounds(20, 20, 400, 20);
        alertLabel.setVisible(true);
        alertLabel.setText("Use only F1-F12 values for controls");

        JLabel fStartKeyLabel = new JLabel();
        fStartKeyLabel.setBounds(50, 50, 200, 20);
        fStartKeyLabel.setText("F key to start robot");

        fStartKeyTextField = new JTextField();
        fStartKeyTextField.setBounds(250, 50, 50, 20);
        fStartKeyTextField.setText(DEFAULT_START_KEY.getKeyCode());

        JLabel fStopKeyLabel = new JLabel();
        fStopKeyLabel.setBounds(50, 75, 200, 20);
        fStopKeyLabel.setText("F key to stop robot");

        fStopKeyTextField = new JTextField();
        fStopKeyTextField.setBounds(250, 75, 50, 20);
        fStopKeyTextField.setText(DEFAULT_STOP_KEY.getKeyCode());

        JLabel shiftToggleLabel = new JLabel();
        shiftToggleLabel.setBounds(50, 100, 200, 20);
        shiftToggleLabel.setText("Shift key to toggle shift run");

        shiftToggleTextField = new JTextField();
        shiftToggleTextField.setBounds(250, 100, 50, 20);
        shiftToggleTextField.setText(DEFAULT_SHIFT_TOGGLE_KEY.getKeyCode());

        saveButton = new JButton("Save");
        saveButton.setBounds(200, 130, 100, 20);

        shiftRunCheckbox = new JCheckBox("Shift run");
        shiftRunCheckbox.setBounds(100, 130, 100, 20);

        isRobotActiveCheckbox = new JCheckBox("Active");
        isRobotActiveCheckbox.setBounds(40, 130, 60, 20);
        isRobotActiveCheckbox.setEnabled(false);

        add(fStartKeyTextField);
        add(shiftToggleTextField);
        add(shiftToggleLabel);
        add(fStopKeyTextField);
        add(fStartKeyLabel);
        add(fStopKeyLabel);
        add(shiftRunCheckbox);
        add(alertLabel);
        add(saveButton);
        add(isRobotActiveCheckbox);

        setSize(350, 200);
        setLayout(null);
        setVisible(true);

        initJIntellitype();
    }

    private void addListeners() {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                // don't forget to clean up any resources before close
                JIntellitype.getInstance().cleanUp();
                System.exit(0);
            }
        });

        shiftRunCheckbox.addActionListener(e -> setShiftRun());

        fStartKeyTextField.addActionListener(e -> changeStartKey());
        fStopKeyTextField.addActionListener(e -> changeStopKey());
        shiftToggleTextField.addActionListener(e -> changeShiftKey());

        saveButton.addActionListener(e -> changeHotkeys());
    }

    private void setShiftRunHotKey() {
        shiftRunCheckbox.setSelected(!shiftRunCheckbox.isSelected());
        setShiftRun();
    }

    private void setShiftRun() {
        ROBOT_THREAD.setShiftRun(shiftRunCheckbox.isSelected());
    }

    private void changeStartKey() {
        if(registeredStartKey != null){
            removeStartKey(registeredStartKey);
        }

        registeredStartKey = KeyIntDict.fromCode(fStartKeyTextField.getText());
        if (registeredStartKey == null || registeredStartKey == registeredStopKey || registeredStartKey == registeredShiftToggleKey) {
            addHotKeys(DEFAULT_START_KEY, registeredStopKey, DEFAULT_SHIFT_TOGGLE_KEY);
            registeredStartKey = DEFAULT_START_KEY;
            fStartKeyTextField.setText(registeredStartKey.getKeyCode());
        }
        else {
            addHotKeys(registeredStartKey, registeredStopKey, DEFAULT_SHIFT_TOGGLE_KEY);
        }
    }

    private void changeStopKey() {
        if(registeredStopKey != null){
            removeStopKey(registeredStopKey);
        }

        registeredStopKey = KeyIntDict.fromCode(fStopKeyTextField.getText());
        if (registeredStopKey == null || registeredStartKey == registeredStopKey || registeredStopKey == registeredShiftToggleKey) {
            addHotKeys(registeredStartKey, DEFAULT_STOP_KEY, DEFAULT_SHIFT_TOGGLE_KEY);
            registeredStopKey = DEFAULT_STOP_KEY;
            fStopKeyTextField.setText(registeredStopKey.getKeyCode());
        }
        else {
            addHotKeys(registeredStartKey, registeredStopKey, DEFAULT_SHIFT_TOGGLE_KEY);
        }
    }

    private void changeShiftKey() {
        if(registeredShiftToggleKey != null){
            removeStopKey(registeredShiftToggleKey);
        }

        registeredShiftToggleKey = KeyIntDict.fromCode(shiftToggleTextField.getText());
        if (registeredShiftToggleKey == null || registeredShiftToggleKey == registeredStopKey || registeredShiftToggleKey == registeredStartKey) {
            addHotKeys(registeredStartKey, registeredStopKey, DEFAULT_SHIFT_TOGGLE_KEY);
            registeredShiftToggleKey = DEFAULT_SHIFT_TOGGLE_KEY;
            shiftToggleTextField.setText(registeredShiftToggleKey.getKeyCode());
        }
        else {
            addHotKeys(registeredStartKey, registeredStopKey, registeredShiftToggleKey);
        }
    }

    private void changeHotkeys() {
        changeStartKey();
        changeStopKey();
        changeShiftKey();
    }

    private void addHotKeys(KeyIntDict startKey, KeyIntDict stopKey, KeyIntDict defaultShiftToggleKey) {
        JIntellitype.getInstance().registerHotKey(startKey.getValue(), startKey.getKeyCode()); //85

        JIntellitype.getInstance().registerHotKey(stopKey.getValue(), stopKey.getKeyCode()); //84
        JIntellitype.getInstance().registerHotKey(stopKey.getValue() + SHIFT, "SHIFT+" + stopKey.getKeyCode()); //184
        JIntellitype.getInstance().registerHotKey(stopKey.getValue() + W, "W+" + stopKey.getKeyCode()); //134
        JIntellitype.getInstance().registerHotKey(stopKey.getValue() + W + SHIFT, "W+SHIFT+" + stopKey.getKeyCode()); //234

        JIntellitype.getInstance().registerHotKey(defaultShiftToggleKey.getValue(), defaultShiftToggleKey.getKeyCode()); //85
        JIntellitype.getInstance().registerHotKey(defaultShiftToggleKey.getValue() + SHIFT, "SHIFT+" + defaultShiftToggleKey.getKeyCode()); //85
        JIntellitype.getInstance().registerHotKey(defaultShiftToggleKey.getValue() + W + SHIFT, "W+" + defaultShiftToggleKey.getKeyCode()); //85
        JIntellitype.getInstance().registerHotKey(defaultShiftToggleKey.getValue() + W, "W+SHIFT+" + defaultShiftToggleKey.getKeyCode()); //85
    }

    private void removeStartKey(KeyIntDict registeredKey) {
        JIntellitype.getInstance().unregisterHotKey(registeredKey.getValue());
    }

    private void removeStopKey(KeyIntDict registeredKey) {
        JIntellitype.getInstance().unregisterHotKey(registeredKey.getValue() + SHIFT);
        JIntellitype.getInstance().unregisterHotKey(registeredKey.getValue() + W);
        JIntellitype.getInstance().unregisterHotKey(registeredKey.getValue() + W + SHIFT);
    }

    public void initJIntellitype() {
        try {
            // initialize JIntellitype with the frame so all windows commands can
            // be attached to this window
            JIntellitype.getInstance().addHotKeyListener(this);
            JIntellitype.getInstance().addIntellitypeListener(this);
        } catch (RuntimeException ex) {
            System.out.println("Either you are not on Windows, or there is a problem with the JIntellitype library!");
        }
    }

    private boolean isStartIdentifier(int identifier) {
        return registeredStartKey.getValue() == identifier;
    }

    private boolean isStopIdentifier(int identifier) {
        return identifier == registeredStopKey.getValue() ||
                identifier == registeredStopKey.getValue() + SHIFT ||
                identifier == registeredStopKey.getValue() + W ||
                identifier == registeredStopKey.getValue() + W + SHIFT;
    }

    private boolean isShiftIdentifier(int identifier) {
        return identifier == registeredShiftToggleKey.getValue() ||
                identifier == registeredShiftToggleKey.getValue() + SHIFT ||
                identifier == registeredShiftToggleKey.getValue() + W ||
                identifier == registeredShiftToggleKey.getValue() + W + SHIFT;
    }

    private void startRobot() {
        isRobotActiveCheckbox.setSelected(true);

        synchronized (ROBOT_THREAD){
            if(!onceStarted){
                onceStarted = true;
                ROBOT_THREAD.start();
                return;
            }

            ROBOT_THREAD.notify();
        }

    }

    private void stopRobot() {
        isRobotActiveCheckbox.setSelected(false);

        if(ROBOT_THREAD.isAlive()){
            ROBOT_THREAD.setStopRunning(true);
        }
    }
}
