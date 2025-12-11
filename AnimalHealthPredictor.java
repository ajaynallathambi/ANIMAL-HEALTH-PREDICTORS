uimport javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class AnimalHealthPredictor extends JFrame {
    // Components
    private JComboBox<String> animalTypeCombo;
    private JComboBox<String> breedCombo;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private JTextField ageField;
    private JTextField weightField;
    private JTextField pincodeField;
    private JCheckBox[] symptomCheckboxes;
    private JTextArea resultsArea;
    private JPanel hospitalPanel;
    private JPanel resultsPanel;
    private JLabel healthStatusLabel;
    
    // Data
    private HashMap<String, String[]> animalBreeds = new HashMap<>();
    private String[] symptoms = {
        "Fever", "Vomit", "Bleeding", "Cannot walk", "Cough", "Sneeze",
        "Loss of appetite", "Diarrhea", "Skin rash", "Lethargy", "Weight loss",
        "Swelling", "Lameness", "Discharge", "Itching", "Shaking head"
    };
    
    private HashMap<String, String[]> districtHospitals = new HashMap<>();
    
    public AnimalHealthPredictor() {
        initializeData();
        setupUI();
    }
    
    private void initializeData() {
        // Initialize animal breeds
        animalBreeds.put("Dog", new String[]{
            "Labrador Retriever", "German Shepherd", "Golden Retriever", "Bulldog",
            "Beagle", "French Bulldog", "Poodle", "Rottweiler", "Yorkshire Terrier"
        });
        animalBreeds.put("Cat", new String[]{
            "Siamese", "Persian", "Maine Coon", "Ragdoll", "Bengal",
            "British Shorthair", "Abyssinian", "Sphynx", "Scottish Fold"
        });
        animalBreeds.put("Horse", new String[]{
            "Arabian", "Thoroughbred", "Quarter Horse", "Appaloosa", "Andalusian"
        });
        animalBreeds.put("Cow", new String[]{
            "Holstein Friesian", "Jersey", "Guernsey", "Ayrshire", "Brown Swiss"
        });
        animalBreeds.put("Rabbit", new String[]{
            "Holland Lop", "Netherland Dwarf", "Mini Rex", "Lionhead", "Flemish Giant"
        });
        animalBreeds.put("Goat", new String[]{
            "Boer", "Nubian", "Saanen", "Alpine", "Toggenburg"
        });
        
        // Initialize hospitals
        districtHospitals.put("639001", new String[]{
            "Karur Veterinary Hospital\nCollectorate Campus, Karur\nPhone: +91 4324 224 567",
            "Pets Care Veterinary Clinic\nNear Bus Stand, Karur\nPhone: +91 4324 225 678"
        });
        
        districtHospitals.put("641001", new String[]{
            "Coimbatore Veterinary Hospital\nGandhipuram, Coimbatore\nPhone: +91 422 245 6789",
            "Peoples Vet Clinic\nRS Puram, Coimbatore\nPhone: +91 422 246 7890"
        });
        
        districtHospitals.put("625001", new String[]{
            "Madurai Veterinary Hospital\nNear Meenakshi Temple, Madurai\nPhone: +91 452 234 5678",
            "Madurai Pet Clinic\nKK Nagar, Madurai\nPhone: +91 452 235 6789"
        });
        
        districtHospitals.put("600001", new String[]{
            "Chennai Veterinary Hospital\nT Nagar, Chennai\nPhone: +91 44 2434 5678",
            "Blue Cross of India\nGuindy, Chennai\nPhone: +91 44 2235 6789"
        });
    }
    
    private void setupUI() {
        setTitle("Animal Health Predictor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 247, 253));
        
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Main Content Panel with Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(240, 247, 253));
        
        tabbedPane.addTab("Animal Information", createAnimalInfoPanel());
        tabbedPane.addTab("Symptoms", createSymptomsPanel());
        tabbedPane.addTab("Results", createResultsPanel());
        tabbedPane.addTab("Hospitals", createHospitalPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Predict Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 247, 253));
        JButton predictButton = new JButton("Predict Health Status");
        predictButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        predictButton.setBackground(new Color(75, 108, 183));
        predictButton.setForeground(Color.WHITE);
        predictButton.setFocusPainted(false);
        predictButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        predictButton.addActionListener(e -> predictHealth());
        
        buttonPanel.add(predictButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(75, 108, 183));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("🐾 Animal Health Predictor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Identify potential health issues and find nearby veterinary hospitals", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(240, 240, 240));
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private JPanel createAnimalInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Animal Information");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 82, 130));
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 242, 255)));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Animal Type
        JPanel animalTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        animalTypePanel.setBackground(Color.WHITE);
        JLabel animalTypeLabel = new JLabel("Animal Type:");
        animalTypeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        animalTypeLabel.setForeground(new Color(44, 82, 130));
        
        String[] animals = {"Select an animal type", "Dog", "Cat", "Horse", "Cow", "Rabbit", "Goat", "Sheep", "Pig", "Buffalo", "Donkey", "Hamster", "Guinea Pig"};
        animalTypeCombo = new JComboBox<>(animals);
        animalTypeCombo.setPreferredSize(new Dimension(200, 30));
        animalTypeCombo.addActionListener(e -> updateBreeds());
        
        animalTypePanel.add(animalTypeLabel);
        animalTypePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        animalTypePanel.add(animalTypeCombo);
        
        // Breed
        JPanel breedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        breedPanel.setBackground(Color.WHITE);
        JLabel breedLabel = new JLabel("Breed:");
        breedLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        breedLabel.setForeground(new Color(44, 82, 130));
        
        breedCombo = new JComboBox<>(new String[]{"Select a breed"});
        breedCombo.setPreferredSize(new Dimension(200, 30));
        
        breedPanel.add(breedLabel);
        breedPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        breedPanel.add(breedCombo);
        
        // Gender
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.setBackground(Color.WHITE);
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        genderLabel.setForeground(new Color(44, 82, 130));
        
        maleRadio = new JRadioButton("♂ Male");
        femaleRadio = new JRadioButton("♀ Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        
        genderPanel.add(genderLabel);
        genderPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        genderPanel.add(maleRadio);
        genderPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        genderPanel.add(femaleRadio);
        
        // Age
        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        agePanel.setBackground(Color.WHITE);
        JLabel ageLabel = new JLabel("Age (in years):");
        ageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ageLabel.setForeground(new Color(44, 82, 130));
        
        ageField = new JTextField(10);
        ageField.setPreferredSize(new Dimension(100, 30));
        
        agePanel.add(ageLabel);
        agePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        agePanel.add(ageField);
        
        // Weight
        JPanel weightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        weightPanel.setBackground(Color.WHITE);
        JLabel weightLabel = new JLabel("Weight (in kg):");
        weightLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        weightLabel.setForeground(new Color(44, 82, 130));
        
        weightField = new JTextField(10);
        weightField.setPreferredSize(new Dimension(100, 30));
        
        weightPanel.add(weightLabel);
        weightPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        weightPanel.add(weightField);
        
        // Pincode
        JPanel pincodePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pincodePanel.setBackground(Color.WHITE);
        JLabel pincodeLabel = new JLabel("Your Pincode:");
        pincodeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pincodeLabel.setForeground(new Color(44, 82, 130));
        
        pincodeField = new JTextField(10);
        pincodeField.setPreferredSize(new Dimension(100, 30));
        
        JButton checkPincodeButton = new JButton("Check Location");
        checkPincodeButton.addActionListener(e -> checkPincode());
        
        pincodePanel.add(pincodeLabel);
        pincodePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        pincodePanel.add(pincodeField);
        pincodePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        pincodePanel.add(checkPincodeButton);
        
        // Add all panels
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(animalTypePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(breedPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(genderPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(agePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(weightPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(pincodePanel);
        
        return panel;
    }
    
    private JPanel createSymptomsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Symptoms");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 82, 130));
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 242, 255)));
        
        // Instruction
        JLabel instructionLabel = new JLabel("Select all symptoms that apply:");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Symptoms checkboxes in a grid
        JPanel symptomsGrid = new JPanel(new GridLayout(0, 3, 10, 10));
        symptomsGrid.setBackground(Color.WHITE);
        symptomsGrid.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        symptomCheckboxes = new JCheckBox[symptoms.length];
        for (int i = 0; i < symptoms.length; i++) {
            symptomCheckboxes[i] = new JCheckBox(symptoms[i]);
            symptomsGrid.add(symptomCheckboxes[i]);
        }
        
        // Scroll pane for symptoms
        JScrollPane scrollPane = new JScrollPane(symptomsGrid);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Add components
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(instructionLabel, BorderLayout.SOUTH);
        northPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createResultsPanel() {
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Initially hide results
        resultsPanel.setVisible(false);
        
        return resultsPanel;
    }
    
    private JPanel createHospitalPanel() {
        hospitalPanel = new JPanel();
        hospitalPanel.setLayout(new BoxLayout(hospitalPanel, BoxLayout.Y_AXIS));
        hospitalPanel.setBackground(Color.WHITE);
        hospitalPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Find Veterinary Hospitals");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 82, 130));
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 242, 255)));
        
        hospitalPanel.add(titleLabel);
        hospitalPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JLabel instructionLabel = new JLabel("Enter pincode and click 'Predict Health Status' to see hospitals in your area");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        hospitalPanel.add(instructionLabel);
        
        return hospitalPanel;
    }
    
    private void updateBreeds() {
        String selectedAnimal = (String) animalTypeCombo.getSelectedItem();
        if (selectedAnimal != null && animalBreeds.containsKey(selectedAnimal)) {
            breedCombo.removeAllItems();
            for (String breed : animalBreeds.get(selectedAnimal)) {
                breedCombo.addItem(breed);
            }
        } else {
            breedCombo.removeAllItems();
            breedCombo.addItem("Select a breed");
        }
    }
    
    private void checkPincode() {
        String pincode = pincodeField.getText().trim();
        if (pincode.length() != 6) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 6-digit pincode.", "Invalid Pincode", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Check if pincode is in our database
        if (districtHospitals.containsKey(pincode)) {
            JOptionPane.showMessageDialog(this, "Pincode detected in our database!", "Location Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Pincode not in database. Showing sample hospitals.", "Location Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void predictHealth() {
        // Validate inputs
        if (animalTypeCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select an animal type.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (breedCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a breed.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!maleRadio.isSelected() && !femaleRadio.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select gender.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (ageField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter age.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (weightField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter weight.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (pincodeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter pincode.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Check if at least one symptom is selected
        boolean symptomSelected = false;
        for (JCheckBox checkbox : symptomCheckboxes) {
            if (checkbox.isSelected()) {
                symptomSelected = true;
                break;
            }
        }
        
        if (!symptomSelected) {
            JOptionPane.showMessageDialog(this, "Please select at least one symptom.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Process prediction
        String animalType = (String) animalTypeCombo.getSelectedItem();
        String breed = (String) breedCombo.getSelectedItem();
        String gender = maleRadio.isSelected() ? "Male" : "Female";
        double age = Double.parseDouble(ageField.getText());
        double weight = Double.parseDouble(weightField.getText());
        String pincode = pincodeField.getText();
        
        // Get selected symptoms
        List<String> selectedSymptoms = new ArrayList<>();
        for (JCheckBox checkbox : symptomCheckboxes) {
            if (checkbox.isSelected()) {
                selectedSymptoms.add(checkbox.getText());
            }
        }
        
        // Process prediction
        PredictionResult result = processPrediction(animalType, breed, gender, age, weight, selectedSymptoms);
        
        // Display results
        displayResults(result, animalType, breed, gender, age, weight, pincode);
        
        // Display hospitals
        displayHospitals(pincode);
    }
    
    private PredictionResult processPrediction(String animalType, String breed, String gender, double age, double weight, List<String> symptoms) {
        PredictionResult result = new PredictionResult();
        
        // Calculate severity score
        int severityScore = 0;
        for (String symptom : symptoms) {
            if (symptom.equals("Bleeding") || symptom.equals("Cannot walk")) {
                severityScore += 10;
            } else if (symptom.equals("Vomit") || symptom.equals("Fever")) {
                severityScore += 8;
            } else if (symptom.equals("Diarrhea") || symptom.equals("Loss of appetite")) {
                severityScore += 6;
            } else {
                severityScore += 4;
            }
        }
        
        // Determine health status
        if (severityScore >= 15) {
            result.healthStatus = "CRITICAL";
            result.suggestion = "Immediate veterinary attention is required!";
            result.statusColor = new Color(254, 215, 215);
            result.textColor = new Color(197, 48, 48);
        } else if (severityScore >= 10) {
            result.healthStatus = "Unhealthy";
            result.suggestion = "Urgent consultation with a veterinarian is advised!";
            result.statusColor = new Color(254, 235, 203);
            result.textColor = new Color(192, 86, 33);
        } else if (severityScore >= 5) {
            result.healthStatus = "Needs Checkup";
            result.suggestion = "Schedule a vet appointment soon.";
            result.statusColor = new Color(233, 245, 255);
            result.textColor = new Color(44, 82, 130);
        } else {
            result.healthStatus = "Healthy";
            result.suggestion = "No urgent action needed. Maintain good diet and care.";
            result.statusColor = new Color(240, 255, 244);
            result.textColor = new Color(47, 133, 90);
        }
        
        // Detect possible diseases
        result.possibleDiseases.add("Infection");
        result.possibleDiseases.add("Fever");
        if (symptoms.contains("Vomit") || symptoms.contains("Diarrhea")) {
            result.possibleDiseases.add("Gastrointestinal Issue");
        }
        if (symptoms.contains("Cough") || symptoms.contains("Sneeze")) {
            result.possibleDiseases.add("Respiratory Infection");
        }
        
        // Gender-specific concerns
        if (gender.equals("Male")) {
            result.genderConcerns.add("Urinary Issues");
            result.genderConcerns.add("Reproductive Health");
        } else {
            result.genderConcerns.add("Mammary Issues");
            result.genderConcerns.add("Reproductive Health");
        }
        
        // Breed-specific concerns
        if (breed.contains("Labrador") || breed.contains("German Shepherd")) {
            result.breedConcerns.add("Hip Dysplasia");
            result.breedConcerns.add("Joint Problems");
        }
        
        return result;
    }
    
    private void displayResults(PredictionResult result, String animalType, String breed, String gender, double age, double weight, String pincode) {
        resultsPanel.removeAll();
        resultsPanel.setVisible(true);
        
        // Title
        JLabel titleLabel = new JLabel("Prediction Results");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 82, 130));
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 242, 255)));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Health Status
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(result.statusColor);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statusPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        healthStatusLabel = new JLabel(result.healthStatus, SwingConstants.CENTER);
        healthStatusLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        healthStatusLabel.setForeground(result.textColor);
        statusPanel.add(healthStatusLabel, BorderLayout.CENTER);
        
        // Animal Details
        JPanel detailsPanel = createResultSection("Animal Details", 
            animalType + " - " + breed + ", " + gender + ", " + age + " years, " + weight + " kg");
        
        // Possible Diseases
        JPanel diseasesPanel = createResultSection("Possible Diseases", 
            String.join("\n", result.possibleDiseases));
        
        // Gender Concerns
        JPanel genderPanel = createResultSection("Gender-Specific Health Concerns",
            String.join("\n", result.genderConcerns));
        
        // Breed Concerns
        JPanel breedPanel = createResultSection("Breed-Specific Health Concerns",
            String.join("\n", result.breedConcerns));
        
        // Recommendation
        JPanel recommendationPanel = createResultSection("Recommendation", result.suggestion);
        
        // Emergency Contacts (if critical)
        if (result.healthStatus.equals("CRITICAL")) {
            JPanel emergencyPanel = new JPanel();
            emergencyPanel.setLayout(new BoxLayout(emergencyPanel, BoxLayout.Y_AXIS));
            emergencyPanel.setBackground(new Color(255, 245, 245));
            emergencyPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 62, 62)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            
            JLabel emergencyTitle = new JLabel("🚨 Emergency Contacts");
            emergencyTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
            emergencyTitle.setForeground(new Color(197, 48, 48));
            
            JTextArea emergencyText = new JTextArea(
                "Blue Cross of India: +91 44 2235 6789 (24/7)\n" +
                "Animal Care Speciality Center: +91 422 247 8901 (24/7)"
            );
            emergencyText.setEditable(false);
            emergencyText.setBackground(new Color(255, 245, 245));
            emergencyText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            emergencyPanel.add(emergencyTitle);
            emergencyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            emergencyPanel.add(emergencyText);
            emergencyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            resultsPanel.add(emergencyPanel);
            resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        // Add all panels
        resultsPanel.add(titleLabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        resultsPanel.add(statusPanel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        resultsPanel.add(detailsPanel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        resultsPanel.add(diseasesPanel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        resultsPanel.add(genderPanel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        resultsPanel.add(breedPanel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        resultsPanel.add(recommendationPanel);
        
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
    
    private JPanel createResultSection(String title, String content) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(44, 82, 130));
        
        JTextArea contentArea = new JTextArea(content);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBackground(Color.WHITE);
        contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(contentArea);
        
        return panel;
    }
    
    private void displayHospitals(String pincode) {
        hospitalPanel.removeAll();
        
        JLabel titleLabel = new JLabel("Find Veterinary Hospitals");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 82, 130));
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 242, 255)));
        
        hospitalPanel.add(titleLabel);
        hospitalPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Show hospitals based on pincode
        String[] hospitals;
        if (districtHospitals.containsKey(pincode)) {
            hospitals = districtHospitals.get(pincode);
            JLabel locationLabel = new JLabel("Hospitals near pincode " + pincode + ":");
            locationLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            hospitalPanel.add(locationLabel);
        } else {
            hospitals = new String[]{
                "City Veterinary Hospital\n123 Main Street\nPhone: +91 98765 43210",
                "Pet Care Center\n456 Park Avenue\nPhone: +91 98765 43211"
            };
            JLabel locationLabel = new JLabel("Sample hospitals (pincode not in database):");
            locationLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            hospitalPanel.add(locationLabel);
        }
        
        hospitalPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Display hospitals
        for (String hospital : hospitals) {
            JPanel hospitalCard = createHospitalCard(hospital);
            hospitalPanel.add(hospitalCard);
            hospitalPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        hospitalPanel.revalidate();
        hospitalPanel.repaint();
    }
    
    private JPanel createHospitalCard(String hospitalInfo) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 108, 183)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        
        String[] lines = hospitalInfo.split("\n");
        for (String line : lines) {
            JLabel label = new JLabel(line);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            card.add(label);
        }
        
        return card;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            AnimalHealthPredictor app = new AnimalHealthPredictor();
            app.setVisible(true);
        });
    }
    
    // Inner class for prediction results
    class PredictionResult {
        String healthStatus;
        String suggestion;
        Color statusColor;
        Color textColor;
        List<String> possibleDiseases = new ArrayList<>();
        List<String> genderConcerns = new ArrayList<>();
        List<String> breedConcerns = new ArrayList<>();
    }
}