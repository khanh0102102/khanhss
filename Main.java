package sdfdsf;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Main extends JFrame {
    private JLabel clockLabel;
    private JTextField timeZoneField;
    private JButton createClockButton;
    
    public Main() {
        setTitle("World Clock");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Panel chứa đồng hồ
        JPanel clockPanel = new JPanel();
        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial", Font.BOLD, 20));
        clockPanel.add(clockLabel);
        
        // Panel chứa textfield và button
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        
        timeZoneField = new JTextField(10);
        controlPanel.add(new JLabel("Timezone: "));
        controlPanel.add(timeZoneField);
        
        createClockButton = new JButton("Create Clock");
        createClockButton.addActionListener(e -> createNewClock());
        controlPanel.add(createClockButton);
        
        // Panel chứa cả hai panel trên
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(clockPanel);
        mainPanel.add(controlPanel);
        
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        // Bắt đầu đồng hồ chạy
        startClock();
    }
    
    private void startClock() {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    clockLabel.setText(sdf.format(calendar.getTime()));
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
    
    private void createNewClock() {
        String timeZoneInput = timeZoneField.getText().trim();
        if (!timeZoneInput.isEmpty()) {
            try {
                TimeZone timeZone = TimeZone.getTimeZone(timeZoneInput);
                ClockFrame clockFrame = new ClockFrame(timeZone);
                clockFrame.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid timezone input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}

class ClockFrame extends JFrame {
    private JLabel clockLabel;
    
    public ClockFrame(TimeZone timeZone) {
        setTitle("World Clock");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel clockPanel = new JPanel();
        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial", Font.BOLD, 20));
        clockPanel.add(clockLabel);
        
        add(clockPanel);
        pack();
        setLocationRelativeTo(null);
        
        // Bắt đầu đồng hồ chạy với múi giờ mới
        startClock(timeZone);
    }
    
    private void startClock(TimeZone timeZone) {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    Calendar calendar = Calendar.getInstance(timeZone);
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    sdf.setTimeZone(timeZone);
                    clockLabel.setText(sdf.format(calendar.getTime()));
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}