package CourseCheckerGUI;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

/*
* 28/04/2024
*
* V1 : implemented
* Working Window
* Icons
* ComboBox
* Table
*
*    !! TO WORK ON:
* Action Listeners
*       - for action listener, change the 3 buttons' color from panelColor to outBtn on hover
* Table Data (array list)
* Second Table for Incomplete
* Sorting Icons and Editable Icons
* Font import
* Combo Box Design
* Finalization of Colors and Design
* Preferrably incorporate use of Graphics Environment for dual monitor (fucks up the panel sizing though idk what to do abt that too bad lol)
* Loops and Exception Handling
*
*
* */

public class GUImain {
    //Resources
    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("CourseCheckerGUI/Icons/MainIcon.png"));

    ImageIcon add = new ImageIcon(getClass().getClassLoader().getResource("CourseCheckerGUI/Icons/AddIcon.png"));
    ImageIcon archive = new ImageIcon(getClass().getClassLoader().getResource("CourseCheckerGUI/Icons/ArchiveIcon.png"));
    ImageIcon info = new ImageIcon(getClass().getClassLoader().getResource("CourseCheckerGUI/Icons/InfoIcon.png"));

    //J COMPS
    JFrame frame = new JFrame("Saint Louis University Checklist Manager");

    JTextField title = new JTextField("     My Checklist Manager");

    JPanel btnPanel = new JPanel();
    JPanel basePanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel navPanel = new JPanel();
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel baseWrapperPanel = new JPanel(new GridBagLayout());

    JButton addCourse = new JButton(add);
    JButton archiveCourse = new JButton(archive);
    JButton appInfo = new JButton(info);

    JComboBox termChoice = new JComboBox<>();

    // MEASUREMENTS
    Dimension btnSml = new Dimension(30,30);
    Dimension drpDm = new Dimension(330,30);
    Dimension minSize = new Dimension(1200,590);

    // MAIN COLORS
    Color bgColor = new Color(239, 243, 246);
    Color topColor = new Color(20, 48, 102);
    Color panelColor = new Color(248, 250, 251);
    Color lineColor = new Color(225, 227, 237);
    Color outButton = new Color(0xEEEFEF);
    Color fontColor = new Color(102, 102, 102);
    Font mainFont = new Font("Arial",1,16);

    //Layout Managers
    FlowLayout btnL = new FlowLayout();
    GridBagLayout navL = new GridBagLayout();
    public GUImain() {

        // top panel (blue strip at top)
        topPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        topPanel.setPreferredSize(new Dimension(1280, 30));
        topPanel.setBackground(topColor);

        // top add text
        topPanel.add(title);
        title.setBorder(null);
        title.setFont(mainFont);
        title.setForeground(bgColor);
        title.setBackground(topColor);

        //set buttons (inside button panel)
        btnPanel.setLayout(btnL);
        btnPanel.setSize(120,60);
        btnPanel.add(addCourse);
        btnPanel.add(archiveCourse);
        btnPanel.add(appInfo);
        btnPanel.setBackground(panelColor);

        //Plus sign
        addCourse.setPreferredSize(btnSml);
        addCourse.setBorder(new LineBorder(outButton, 2));
        addCourse.setBackground(bgColor);
        addCourse.setFocusPainted(false);
        //Box SIgn
        archiveCourse.setPreferredSize(btnSml);
        archiveCourse.setBorder(new LineBorder(outButton, 2));
        archiveCourse.setBackground(bgColor);
        archiveCourse.setFocusPainted(false);
        // i with circle sign
        appInfo.setPreferredSize(btnSml);
        appInfo.setBorder(new LineBorder(outButton, 2));
        appInfo.setBackground(bgColor);
        appInfo.setFocusPainted(false);

        //ComboBOx
        termChoice.setPreferredSize(drpDm);
        termChoice.setBackground(bgColor);
        termChoice.setEditable(false);
        termChoice.setFocusable(false);

        // set nav panel (where buttons are)

        navPanel.setLayout(navL);
        navPanel.setPreferredSize(new Dimension(1280, 60));
        navPanel.setBackground(panelColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.5;
        gbc.weighty = 0.333;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,30,0,0);
        gbc.gridheight = 60;
        gbc.gridwidth = 120;
        gbc.anchor = GridBagConstraints.WEST;
        navPanel.add(termChoice, gbc);

        //This just overwrites the constraints might bbe okay for now????...
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0,0,0,30);
        gbc.gridheight = 60;
        gbc.gridwidth = 120;
        navPanel.add(btnPanel, gbc);


        // base panel
        basePanel.setPreferredSize(new Dimension(1170, 440));
        basePanel.setBackground(panelColor);
        basePanel.setLayout(new GridBagLayout());


        // making table
        AbstractTableModel cur = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return 12;//size of ary list to
            }
            @Override
            public int getColumnCount() {
                return 5;
            }
            @Override
            public String getColumnName(int columnIndex) {
                switch (columnIndex){
                    case 0: return "Units";
                    case 1: return "Course Number";
                    case 2: return "Course Description";
                    case 3: return "Grade";
                    case 4: return "Grade Point";
                }
                return null;
            }
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return true;
            }
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return null; // i think i2 data
            }
            @Override
            public TableModelListener[] getTableModelListeners() {
                return super.getTableModelListeners();
            }
        };
        // supply with arrsy
        // eg JTable(Object[][] rowData, Object[] columnNames)
        JTable tbl = new JTable(cur) {
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(1140,400);
            }
        };

        //table specifications
        JScrollPane jp = new JScrollPane(tbl); // viewport
        jp.setViewportView(tbl);
        tbl.getColumnModel().getColumn(0).setPreferredWidth(120);
        tbl.getColumnModel().getColumn(1).setPreferredWidth(300);
        tbl.getColumnModel().getColumn(2).setPreferredWidth(540);
        tbl.getColumnModel().getColumn(3).setPreferredWidth(119);
        tbl.getColumnModel().getColumn(4).setPreferredWidth(200);
        tbl.setRowHeight(20);
        tbl.setGridColor(lineColor);
        tbl.setBackground(panelColor);
        tbl.setForeground(fontColor);
        //table font tbc when data is imoportwed...

        // basepanel
        GridBagConstraints gbbc = new GridBagConstraints();
        gbbc.gridx = 0;
        gbbc.gridy = 0;
        gbbc.weightx = 1.0;
        gbbc.fill = GridBagConstraints.HORIZONTAL;

        //main
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(navPanel, BorderLayout.CENTER);

        //wrapper
        baseWrapperPanel.setBackground(new Color(0xEFF3F6));
        baseWrapperPanel.add(basePanel);
        basePanel.add(jp);

        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setMinimumSize(minSize);
        frame.getContentPane().setBackground(bgColor);
        frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(baseWrapperPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new GUImain());
        }
}

