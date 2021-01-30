/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caloriescalculator.client;

/**
 *
 * @author thechee
 */
import caloriescalculator.entity.Food;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.naming.InitialContext;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import caloriescalculator.ifce.calcalc.ICalCalc;
import caloriescalculator.ifce.cart.ICart;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;

public class CalCalcOSGiClient {
    
    ComponentContext context;
    // ServiceReference cartRef;
    // ServiceReference calcalcRef;
    ICart cart;
    ICalCalc calcalc;
    static JFrame f;
    
    public void activate(ComponentContext context) {
        if (cart != null && calcalc != null) {
            // cart = (ICart) context.locateService("ICart", reference);
            // calcalc = (ICalCalc) context.locateService("ICalCalc", reference);
            System.out.println("[Activate Done]");
            JFrame frame = new AppFrame(calcalc, cart);
            navigate(frame);
        } else {
            System.out.println("[Activate Fail] No reference");
        }

    }

    public void gotService(ServiceReference reference) {
        System.out.println("Bind Service");
        // this.reference = reference;
        System.out.println(reference.toString());
        System.out.println("");
        
        if (reference.toString().contains("Cart")) {
            System.out.println("Bind Cart Reference");
            // cartRef = reference;
            cart = (ICart) context.locateService("ICart", reference);
        } else {
            System.out.println("Bind CalCalc Reference");
            // calcalcRef = reference;
            calcalc = (ICalCalc) context.locateService("ICalCalc", reference);
        }
    }

    public void lostService(ServiceReference reference) {
        System.out.println("unbind Service");
        // this.reference = null;
    }
    
    public static void navigate(JFrame frame) {
        if (f != null) f.setVisible(false);
        f = frame;
        f.setVisible(true);
    }
}

// https://github.com/WIF3006-T3G3/timetable-osgi/tree/master/timetable-normal

class AppFrame extends JFrame implements ActionListener {
    
    private ICalCalc calCalcRemote;
    private ICart cartRemote;
    private int kcalPerDay;
    private JPanel calcPanel, cartPanel;
    private JLabel title, gender, age, weight, height, exerciseLevel, plan, calcResult, cart, remainingCal;
    private JRadioButton male, female; 
    private ButtonGroup genderGp; 
    private JTextField tAge, tWeight, tHeight; 
    private JComboBox levelCombo, planCombo; 
    private JButton calcBtn, backBtn, clearBtn; 
    private JTable allFoodTable, cartFoodTable;
    private Action action;
    
    private ComboItem[] levels = {
        new ComboItem(1, "Little to no exercise"),
        new ComboItem(2, "Light exercise (1–3 days per week))"),
        new ComboItem(3, "Moderate exercise (3–5 days per week)"),
        new ComboItem(4, "Heavy exercise (6–7 days per week)"),
        new ComboItem(5, "Very heavy exercise (twice per day, extra heavy workouts)")
    };
    
    private ComboItem[] plans = {
        new ComboItem(1, "Maintain weight"),
        new ComboItem(2, "Lose 0.5kg per week"),
        new ComboItem(3, "Lose 1kg per week"),
        new ComboItem(4, "Gain 0.5kg per week"),
        new ComboItem(5, "Gain 1kg per week")
    };
    
    private Food[] allFood = {
        new Food("Agar Agar - 1 piece", 37),
        new Food("Air Bandung - 1 galss", 150),
        new Food("Almond/Cashew Nuts - 6", 45),
        new Food("Apple - 1", 81),
        new Food("Apple Juice - 1 cup", 111),
        new Food("Apple Pie - 1 piece", 260),
        new Food("Bak Kut Teh - 1 bowl", 348),
        new Food("Banana - 1", 105),
        new Food("Beef Burger - 1", 317),
        new Food("Beef Rendang - 1 small piece", 114),
        new Food("Beer - 1 can 360 ml", 150),
        new Food("Biscuit - 1", 103),
        new Food("Bread (white) - 1 slice", 61),
        new Food("Bubur Chacha - 1 bowl", 165),
        new Food("Bubur Kacang Hijau - 1 bowl", 244),
        new Food("Butter/Margerine - 1 tsp", 45),
        new Food("Bun (chicken curry) - 1", 204),
        new Food("Bun (kaya/coconut) - 1", 219),
        new Food("Bun (red bean) - 1", 223),
        new Food("Bun (sardine) - 1", 71),
        new Food("Cake (plain) - 1 piece", 100),
        new Food("Capati + Green Gravy - 1 piece + 1/2 cup", 166),
        new Food("Carrot - 1", 31),
        new Food("Cendol - 1 bowl", 199),
        new Food("Century Egg Porridge - 1 bowl", 423),
        new Food("Char Siew - 1 small plate", 191),
        new Food("Cheese Burger - 1", 341),
        new Food("Cheese Burger + Extra Beef - 1", 438),
        new Food("Cheese Cake - 1 slice", 400),
        new Food("Chicken Curry - 1 piece", 195),
        new Food("Chicken Porridge - 1 bowl", 348),
        new Food("Chicken Rice - 1 plate", 600),
        new Food("Chicken Thigh - 1 piece", 320),
        new Food("Chicken Wing - 1 piece", 166),
        new Food("Chinese Tea - 1 glass", 0),
        new Food("Choc Chip Cookies - 1 piece", 50),
        new Food("Choo-Cheong-Fun - 1 piece", 236),
        new Food("Claypot Rice - 1 bowl", 898),
        new Food("Coconut Water - 1 fruit", 110),
        new Food("Coleslaw - 1 small cup", 62),
        new Food("Condensed Milk (sweetened) - 1 tbsp", 71),
        new Food("Corn - 1/2 cup", 89),
        new Food("Creamed Soup - 1 bowl", 375),
        new Food("Cucur Udang - 1 piece", 144),
        new Food("Curry Laksa - 1 bowl", 589),
        new Food("Curry Puff - 1 piece", 256),
        new Food("Doughnut - 1 piece", 268),
        new Food("Dumpling (chicken) - 1 piece", 203),
        new Food("Durian - 1 seeds", 30),
        new Food("Economy Rice + 3 Dishes - 1 plate", 620),
        new Food("Egg (hard boiled) - 1", 76),
        new Food("Egg Fuyong - 1 plate", 335),
        new Food("Fish Head Curry - 1 small plate", 228),
        new Food("French Fries - 1 small cup", 290),
        new Food("Fresh Juice (sweetened) - 1 glass", 120),
        new Food("Fried Baby Sotong - 1 small plate", 630),
        new Food("Fried Banana - 1 piece", 129),
        new Food("Fried Chicken (various portion) - 1 piece", 290),
        new Food("Fried Egg - 1", 128),
        new Food("Fried Fish Cake + Bun - 1", 433),
        new Food("Fried Fish Kembong - 1", 219),
        new Food("Fried Kuay Teow + Cockles - 1 plate", 743),
        new Food("Fried Noodle/Meehoon - 1 plate", 510),
        new Food("Fried Rice - 1 plate", 637),
        new Food("Fried Spring Roll - 1 piece", 91),
        new Food("Fruit Jam - 1 tsp", 20),
        new Food("Full Cream Milk (3 tbsp) - 1 glass", 150),
        new Food("Grape - 1", 8),
        new Food("Guava - 1/2", 60),
        new Food("Ham-Chi-Peng - 1 piece", 233),
        new Food("Hard Liquor - 1 peg 20 ml", 64),
        new Food("Hot Dog - 1", 225),
        new Food("Ice Cream - 1 scoops", 153),
        new Food("Ice Kacang - 1 bowl", 258),
        new Food("Ice Lemon Tea + 1 tsp Sugar - 1 glass", 26),
        new Food("Kaya - 1 tsp", 39),
        new Food("Kopi O/Teh O + 1 tsp Sugar - 1 cup", 20),
        new Food("Kuih Ang Koo (mungbean) - 1 piece", 111),
        new Food("Kuih Ang Koo (peanut)194 - 1 piece", 194),
        new Food("Kuih Apam Balik - 1 piece", 282),
        new Food("Kuih Koci (pulut putih) - 1 piece", 183),
        new Food("Kuih Talam Seri Kaya - 1 piece", 183),
        new Food("Langsat - 1", 3),
        new Food("Lor Mee  - 1 bowl", 383),
        new Food("Lor Mei Kei/Chinese Glutinous Rice - 1 bowl", 422),
        new Food("Low Fat Milk (3 tbsp) - 1 glass", 130),
        new Food("Luncheon Meat - 1 slice", 93),
        new Food("Mamak Mee Goreng  - 1 plate", 660),
        new Food("Mango - 1/2", 67),
        new Food("Mashed Potatoes - 1 cup", 87),
        new Food("Myonnaise - 1 tsp", 45),
        new Food("Mee Hailam  - 1 bowl", 277),
        new Food("Mee Rebus  - 1 plate", 556),
        new Food("Milo O (2 tbsp) - 1 glass", 50),
        new Food("Milo (sweetened) - 1 cup", 133),
        new Food("Murtabak (mutton) - 1", 722),
        new Food("Mutton Curry - 1 small piece", 144),
        new Food("Nasi Briyani + Mutton - 1 plate", 587),
        new Food("Nasi Kerabu - 1 plate", 360),
        new Food("Nasi Lemak + Gravy - 1 plate", 381),
        new Food("Nasi Minyak + Beef Rendang - 1 plate", 664),
        new Food("Noodle Soup - 1 bowl", 381),
        new Food("Oatmeal (plain) - 1 bowl", 225),
        new Food("Oatmeal + Fat Milk - 1 bowl", 356),
        new Food("Orange - 1", 65),
        new Food("Orange Juice - 1 cip", 112),
        new Food("Packet Drink (sweetened, assorted) - 1 packet", 103),
        new Food("Papadam - 1 piece", 26),
        new Food("Papaya - 1 slice", 56),
        new Food("Pasembur/Indian Rojak - 1 plate", 752),
        new Food("Peanut - 20 small", 45),
        new Food("Peanut Butter - 1 tsp", 33),
        new Food("Pear - 1", 98),
        new Food("Peas (green) - 1/2 cup", 63),
        new Food("Penang Laksa - 1 bowl", 436),
        new Food("Pizza (pepperoni, beef, etc) - 1 slice", 115),
        new Food("Pizza (beef, chicken, onion) - 1 slice", 242),
        new Food("Pizza (chicken, pineapple) - 1 slice", 268),
        new Food("Pizza (onion, tamatoes, etc) - 1 slice", 149),
        new Food("Plain Porridge - 1 bowl", 182),
        new Food("Plain Soup (meat/vegetable) - 1 bowl", 200),
        new Food("Plain White Rice - 1 bowl", 207),
        new Food("Pomfret (steaned) - 1", 65),
        new Food("Potato (plain, baked) - 1", 220),
        new Food("Potato Chips - 1 amll pack", 95),
        new Food("Prawn Crackers - 1 small packet", 106),
        new Food("Prawn Mee Soup - 1 bowl", 293),
        new Food("Prawn Sambal - 1 small plate", 194),
        new Food("Processed Cheese - 1 slice", 54),
        new Food("Rojak - 1 plate", 443),
        new Food("Roti Canai + Dhal - 1 piece + 1/2 cup", 359),
        new Food("Roti Telur + Dhal - 1 piece + 1/2 cup", 414),
        new Food("Salad (raw) - 1 bowl", 100),
        new Food("Salad Dressing - 1 tbsp", 60),
        new Food("Sanwiches (chicken) - 1", 270),
        new Food("Sanwiches (chicken, salad, etc) - 1", 481),
        new Food("Sanwiches (fish, salad, etc) - 1", 328),
        new Food("Sanwiches (tuna fish) - 1", 174),
        new Food("Satay (chicken) - 1 stick", 37),
        new Food("Satay Sauce - 1/2 cup", 129),
        new Food("Sayur Lemak - 1 plate", 140),
        new Food("Siew Mai - 1 piece", 105),
        new Food("Soft Drink - 1 can", 120),
        new Food("Soya Bean Milk - 1 small packet", 163),
        new Food("Spaghetti (cheese & meat sause) - 1 plate", 444),
        new Food("Spaghetti (plain) - 1 plate", 169),
        new Food("Stir Fried Vegetables - 1 plate", 66),
        new Food("Stout - 1 can 360 ml", 195),
        new Food("Sundae Chocolate - 1 cup", 380),
        new Food("Sweet Potato - 1", 118),
        new Food("Tauhoo (steamed) - 1 square", 102),
        new Food("Taukua (fried) - 1 square", 202),
        new Food("Tau Foo Far (sweetened) - 1 bowl", 144),
        new Food("Teh Tarik - 1 cup", 83),
        new Food("Vadai - 1 piece", 194),
        new Food("Wanton Mee Dry - 1 plate", 409),
        new Food("Wanton Mee Soup - 1 bowl", 217),
        new Food("Watermelon - 1 slice", 60),
        new Food("Wine (red/white) - 1 wine glass", 122),
        new Food("Yam Cake - 1 piece", 174),
        new Food("Yau-Car)kueh - 1 piece", 292),
        new Food("Yoghurt (low fat) - 1 cup 150g", 100)
    };
    
    public AppFrame(ICalCalc cc, ICart c) {
        calCalcRemote = cc;
        cartRemote = c;
        setTitle("Calories Calculator");
        setBounds(300, 90, 480, 600); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setResizable(false);
        createCalcPanel();
        add(calcPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == calcBtn && !tWeight.getText().isEmpty() && !tHeight.getText().isEmpty()) { 
//            if(male.isSelected())
//                System.out.println("Gender: Male");
//            else
//                System.out.println("Gender: Female");
//            System.out.println("Age: "+tAge.getText());
//            System.out.println("Weight: "+tWeight.getText());
//            System.out.println("Height: "+tHeight.getText());
            ComboItem selectedLevel = (ComboItem) levelCombo.getSelectedItem();
//            System.out.println("Exercise level: "+selectedLevel.getValue());
            ComboItem selectedPlan = (ComboItem) planCombo.getSelectedItem();
            float weightGain = 0;
            switch(selectedPlan.getValue()) {
                case 1:
                    weightGain = 0;
                    break;
                case 2:
                    weightGain = (float) -0.5;
                    break;
                case 3:
                    weightGain = -1;
                    break;
                case 4:
                    weightGain = (float) 0.5;
                    break;
                case 5:
                    weightGain = 1;
                    break;
            }
//            System.out.println("Plan: "+selectedPlan.getValue());
            
            // Calculate daily needed calories
            try {
                InitialContext ctx = new InitialContext();
                kcalPerDay = calCalcRemote.calculateCalories(male.isSelected(), Integer.parseInt(tAge.getText()), Integer.parseInt(tWeight.getText()), Integer.parseInt(tHeight.getText()), selectedLevel.getValue(), weightGain);       
//                System.out.println("\nYour daily calories requirement is "+kcalPerDay+" colories!");
            } catch(Exception ex){
                System.out.println(ex);
            }

            getContentPane().removeAll();
            createCartPanel();
            getContentPane().add(cartPanel);
            repaint();
            printAll(getGraphics());
            
        } else if (e.getSource() == backBtn) {
            getContentPane().removeAll();
            cartRemote.clearCart();
            createCalcPanel();
            getContentPane().add(calcPanel);
            repaint();
            printAll(getGraphics());
        } else if (e.getSource() == clearBtn) {
            cartRemote.clearCart();
            setupCartTable();
        }
        
    }
    
    private void createCalcPanel() {
        calcPanel = new JPanel(new GridBagLayout());
        JPanel inPanel = new JPanel(new BorderLayout());
        inPanel.setPreferredSize(new Dimension(480, 520));
        
        title = new JLabel("Calories Calculator"); 
        title.setFont(new Font("Arial", Font.PLAIN, 30)); 
        title.setSize(300, 30); 
        title.setLocation(90, 35);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        inPanel.add(title, "North");
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(60, 60, 60, 60));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.anchor = GridBagConstraints.WEST;
        gbc1.insets = new Insets(15, 0, 15, 5);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.weightx = 1;
        gbc2.anchor = GridBagConstraints.WEST;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(15, 0, 15, 5);
        
        gender = new JLabel("Gender"); 
        gender.setFont(new Font("Arial", Font.PLAIN, 18)); 
        gender.setSize(120, 20); 
        gender.setLocation(80, 100); 
        form.add(gender,gbc1);
        gbc1.gridy++;
  
        JPanel radioGp = new JPanel();
        
        male = new JRadioButton("Male"); 
        male.setFont(new Font("Arial", Font.PLAIN, 15)); 
        male.setSelected(true); 
        male.setSize(75, 20); 
        male.setLocation(200, 100); 
        male.setBorder(new EmptyBorder(0, 0, 0, 45));
        radioGp.add(male);
  
        female = new JRadioButton("Female"); 
        female.setFont(new Font("Arial", Font.PLAIN, 15)); 
        female.setSelected(false); 
        female.setSize(80, 20); 
        female.setLocation(275, 100); 
        female.setBorder(new EmptyBorder(0, 0, 0, 45));
        radioGp.add(female);
  
        genderGp = new ButtonGroup(); 
        genderGp.add(male); 
        genderGp.add(female); 
        form.add(radioGp, gbc2);
        gbc2.gridy++;
        
        age = new JLabel("Age"); 
        age.setFont(new Font("Arial", Font.PLAIN, 18)); 
        age.setSize(120, 20); 
        age.setLocation(80, 150); 
        form.add(age, gbc1);
        gbc1.gridy++;

        tAge = new JTextField(); 
        tAge.setFont(new Font("Arial", Font.PLAIN, 15)); 
        tAge.setSize(200, 20); 
        tAge.setLocation(200, 150); 
        form.add(tAge, gbc2);
        gbc2.gridy++;
        
        weight = new JLabel("Weight (kg)"); 
        weight.setFont(new Font("Arial", Font.PLAIN, 18)); 
        weight.setSize(120, 20); 
        weight.setLocation(80, 200); 
        form.add(weight, gbc1);
        gbc1.gridy++;
  
        tWeight = new JTextField(); 
        tWeight.setFont(new Font("Arial", Font.PLAIN, 15)); 
        tWeight.setSize(200, 20); 
        tWeight.setLocation(200, 200); 
        form.add(tWeight, gbc2);
        gbc2.gridy++;
        
        height = new JLabel("Height (cm)"); 
        height.setFont(new Font("Arial", Font.PLAIN, 18)); 
        height.setSize(120, 20); 
        height.setLocation(80, 250); 
        form.add(height, gbc1);
        gbc1.gridy++;
  
        tHeight = new JTextField(); 
        tHeight.setFont(new Font("Arial", Font.PLAIN, 15)); 
        tHeight.setSize(200, 20); 
        tHeight.setLocation(200, 250); 
        form.add(tHeight, gbc2);
        gbc2.gridy++;
        
        exerciseLevel = new JLabel("Exercise Level"); 
        exerciseLevel.setFont(new Font("Arial", Font.PLAIN, 18)); 
        exerciseLevel.setSize(120, 20); 
        exerciseLevel.setLocation(80, 300); 
        form.add(exerciseLevel, gbc1);
        gbc1.gridy++;
  
        levelCombo = new JComboBox(levels); 
        levelCombo.setFont(new Font("Arial", Font.PLAIN, 15)); 
        levelCombo.setSize(200, 20); 
        levelCombo.setLocation(200, 300); 
        form.add(levelCombo, gbc2);
        gbc2.gridy++;
        
        plan = new JLabel("Plan"); 
        plan.setFont(new Font("Arial", Font.PLAIN, 18)); 
        plan.setSize(120, 20); 
        plan.setLocation(80, 350); 
        form.add(plan, gbc1);
        gbc1.gridy++;
  
        planCombo = new JComboBox(plans); 
        planCombo.setFont(new Font("Arial", Font.PLAIN, 15)); 
        planCombo.setSize(200, 20); 
        planCombo.setLocation(200, 350); 
        form.add(planCombo, gbc2);
        gbc2.gridy++;
        inPanel.add(form);
        
        JPanel btnPanel = new JPanel();
        calcBtn = new JButton("Calculate"); 
        calcBtn.setFont(new Font("Arial", Font.PLAIN, 15)); 
        calcBtn.setSize(100, 20); 
        calcBtn.setLocation(190, 420);
        calcBtn.addActionListener(this); 
        btnPanel.add(calcBtn);
        inPanel.add(btnPanel, "South");
        
        calcPanel.add(inPanel);
    }
    
    private void createCartPanel() {
        
        try {
            InitialContext ctx = new InitialContext();
        } catch(Exception ex){
            System.out.println(ex);
        }
        
        cartPanel = new JPanel(new GridBagLayout());
        JPanel inPanel = new JPanel();
        inPanel.setLayout(new BoxLayout(inPanel, BoxLayout.Y_AXIS));
        inPanel.setPreferredSize(new Dimension(470, 560));
        
        calcResult = new JLabel("Your daily calories requirement is "+kcalPerDay+" calories!"); 
        calcResult.setFont(new Font("Arial", Font.PLAIN, 20)); 
//        calcResult.setSize(480, 30); 
        calcResult.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        inPanel.add(calcResult);
        
        Object[][] data = new Object[allFood.length][3];
        for(int i=0; i<allFood.length; i++) {
            data[i][0] = allFood[i].getName();
            data[i][1] = allFood[i].getCalories();
            data[i][2] = "Add" ;
        }
        allFoodTable = new JTable(new DefaultTableModel(data, new String[] {"Food", "Calories", "Add To Cart"}));
        int[] columnsWidth = {280, 80, 110};
        for (int i=0; i<columnsWidth.length; i++) {
            TableColumn column = allFoodTable.getColumnModel().getColumn(i);
            column.setMinWidth(columnsWidth[i]);
            column.setMaxWidth(columnsWidth[i]);
            column.setPreferredWidth(columnsWidth[i]);
        }
        
        cartFoodTable = new JTable();
        
        action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable)e.getSource();
                if(table.equals(allFoodTable)) {
                    int modelRow = Integer.valueOf(e.getActionCommand());
//                    System.out.println("Adding... "+data[modelRow][0]+","+data[modelRow][1]);
                    Food f = allFood[modelRow];
                    cartRemote.addFood(f);
//                    System.out.println("Food in cart:");
//                    for(Food food: cartBeanRemote.listFoods()) {
//                        System.out.println(food.getName()+" - "+food.getCalories()+"kcal x"+food.getQuantity());
//                    }
                    setupCartTable();
                } else if(table.equals(cartFoodTable)) {
                    int modelRow = Integer.valueOf( e.getActionCommand() );
                    Object[][] tableData = getCartData();
//                    System.out.println("Removing... "+tableData[modelRow][0]+","+tableData[modelRow][1]);
                    Food foodToRemove = new Food(tableData[modelRow][0].toString(), Integer.parseInt(tableData[modelRow][1].toString()));
                    cartRemote.removeFood(foodToRemove);
//                    System.out.println("Food in cart:");
//                    for(Food food: cartBeanRemote.listFoods()) {
//                        System.out.println(food.getName()+" - "+food.getCalories()+"kcal x"+food.getQuantity());
//                    }
                    setupCartTable();
                }
            }
        };
        
        ButtonColumn buttonColumn = new ButtonColumn(allFoodTable, action, 2);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
        JScrollPane scroll = new JScrollPane(allFoodTable);
        inPanel.add(scroll);
        
        cart = new JLabel("Food Cart"); 
        cart.setFont(new Font("Arial", Font.BOLD, 18)); 
//        cart.setSize(480, 20); 
        cart.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cart.setBorder(new EmptyBorder(20,0,0,0));
        inPanel.add(cart);
        
        remainingCal = new JLabel("");
        remainingCal.setFont(new Font("Arial", Font.PLAIN, 20)); 
//        remainingCal.setSize(480, 30); 
        remainingCal.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        setupCartTable();
        JScrollPane scroll2 = new JScrollPane(cartFoodTable);
        inPanel.add(scroll2);
        inPanel.add(remainingCal);
        
        backBtn = new JButton("Back"); 
        backBtn.setFont(new Font("Arial", Font.PLAIN, 15)); 
        backBtn.setSize(100, 20); 
        backBtn.addActionListener(this);
        clearBtn = new JButton("Clear Cart"); 
        clearBtn.setFont(new Font("Arial", Font.PLAIN, 15)); 
        clearBtn.setSize(100, 20); 
        clearBtn.addActionListener(this);
        JPanel btnPane = new JPanel();
        btnPane.setLayout(new BoxLayout(btnPane, BoxLayout.X_AXIS));
        btnPane.add(backBtn);
        btnPane.add(Box.createHorizontalGlue());
        btnPane.add(clearBtn);
        btnPane.setBorder(new EmptyBorder(20,0,0,0));
        inPanel.add(btnPane);
        
        cartPanel.add(inPanel);
        
    }
    
    private Object[][] getCartData() {
        List<Food> cartFoodList = cartRemote.listFoods();
        Object[][] cartData = new Object[cartFoodList.size()][4];
        for(int i=0; i<cartFoodList.size(); i++) {
            cartData[i][0] = cartFoodList.get(i).getName();
            cartData[i][1] = cartFoodList.get(i).getCalories();
            cartData[i][2] = cartFoodList.get(i).getQuantity();
            cartData[i][3] = "Remove" ;
        }
        return cartData;
    }
    
    private void setupCartTable() {
        DefaultTableModel cartFoodModel = new DefaultTableModel(getCartData(), new String[] {"Food", "Calories", "Quantity", "Remove From Cart"});
        cartFoodTable.setModel(cartFoodModel);
        int[] cartColumnsWidth = {220, 80, 60, 110};
        for (int i=0; i<cartColumnsWidth.length; i++) {
            TableColumn column = cartFoodTable.getColumnModel().getColumn(i);
            column.setMinWidth(cartColumnsWidth[i]);
            column.setMaxWidth(cartColumnsWidth[i]);
            column.setPreferredWidth(cartColumnsWidth[i]);
        }
        ButtonColumn buttonColumn2 = new ButtonColumn(cartFoodTable, action, 3);
        buttonColumn2.setMnemonic(KeyEvent.VK_D);
        int calNeeded = kcalPerDay - cartRemote.getTotalCal();
        remainingCal.setText(calNeeded<0?"You've exceeded your calorie limit by "+(-calNeeded)+" calories!":"You need "+calNeeded+" more calories today!"); 
    }

}

class ComboItem {
    private int value;
    private String label;

    public ComboItem(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        return label;
    }
}