
import com.mysql.jdbc.Connection;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.application.Platform;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shatha Suliman
 */
public class Library extends javax.swing.JFrame {

    /**
     * Creates new form Library
     */
    public Library() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Icons/Logo.jpg")).getImage());
        ShowDocuments();
        JTableHeader header = DocsList.getTableHeader();
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.RIGHT);
        for (int i=0 ; i<=5 ; i++) 
        {
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
            DocsList.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }
        Sort();
        Label_extra.setVisible(false);
        TextField_extra.setVisible(false);
        Label_extra1.setVisible(false);
        TextField_extra1.setVisible(false);
        
        extraInfo1Label.setVisible(true);
        extraInfo1TextField.setVisible(true);
        extraInfo2Label.setVisible(false);
        extraInfo2TextField.setVisible(false);
    }
     
    public Connection DBConnection()
    {
        Connection con = null;
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            String host = "jdbc:mysql://localhost:3306/mg-marjieidb";
            String unicode= "?useUnicode=yes&characterEncoding=UTF-8";
            con = (Connection) DriverManager.getConnection( host+unicode, "root", "" );
            return con;
        } catch (Exception ex)
        {
            System.out.print(ex.getMessage());
        }
        return con;
    }
    
    public ArrayList<ReferenceDocument> getDocumentList()
    {
        ArrayList<ReferenceDocument> documentList = new ArrayList<ReferenceDocument>();
        Connection connection = DBConnection();
        
        String query = "SELECT * FROM referencedocument";
        Statement stmt;
        ResultSet rs;
        
        try 
        {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            ReferenceDocument rf;
            while (rs.next())
            {
                int id = rs.getInt("documentID");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                int publishYear = rs.getInt("publishYear");
                 String dateAdded = rs.getString("DateAdded");
                rf = new ReferenceDocument (id, title, author, publisher, publishYear, dateAdded);
                documentList.add(rf);
            }
            
        } catch (Exception ex)
        {
            System.out.print(ex.getMessage());
        }
        return documentList;
    } 
    
    public void ShowDocuments()
    {
        ArrayList<ReferenceDocument> List = getDocumentList();
        DefaultTableModel model = (DefaultTableModel) DocsList.getModel();
        Object[] row = new Object[6];
        for (int i=0 ; i<List.size() ; i++)
        {
            row[0] = List.get(i).getDateAdded(); 
            row[1] = List.get(i).getPublishYear(); 
            row[2] = List.get(i).getPublisher();
            row[3] = List.get(i).getAuthor();
            row[4] = List.get(i).getTitle();
            row[5] = List.get(i).getID();
            model.addRow(row);
        }
    }
    
    private void Sort()
      {
          DefaultTableModel model = (DefaultTableModel) DocsList.getModel();
          TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
          DocsList.setRowSorter(sorter);
      }
    
     private void Search(String key)
      {
          DefaultTableModel model = (DefaultTableModel) DocsList.getModel();
          TableRowSorter<DefaultTableModel> filter = new TableRowSorter<DefaultTableModel>(model);
          DocsList.setRowSorter(filter);
          filter.setRowFilter(RowFilter.regexFilter(key));
      }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel = new javax.swing.JPanel();
        Button_add = new javax.swing.JButton();
        Button_delete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        DocsList = new javax.swing.JTable();
        TextField_search = new javax.swing.JTextField();
        Label_searchIcon = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Panel_edit = new javax.swing.JPanel();
        Label_title = new javax.swing.JLabel();
        Label_author = new javax.swing.JLabel();
        Label_publisher = new javax.swing.JLabel();
        Label_year = new javax.swing.JLabel();
        Label_pages = new javax.swing.JLabel();
        Label_extra = new javax.swing.JLabel();
        Label_extra1 = new javax.swing.JLabel();
        ComboBox_type = new javax.swing.JComboBox<>();
        TextField_title = new javax.swing.JTextField();
        TextField_author = new javax.swing.JTextField();
        TextField_year = new javax.swing.JTextField();
        TextField_publisher = new javax.swing.JTextField();
        TextField_extra = new javax.swing.JTextField();
        TextField_pages = new javax.swing.JTextField();
        TextField_extra1 = new javax.swing.JTextField();
        Button_edit = new javax.swing.JButton();
        Panel_manually = new javax.swing.JPanel();
        typeCombobox = new javax.swing.JComboBox<>();
        titleLabel = new javax.swing.JLabel();
        titleTextField = new javax.swing.JTextField();
        publisherLabel = new javax.swing.JLabel();
        publisherTextField = new javax.swing.JTextField();
        pagesLabel = new javax.swing.JLabel();
        pagesTextField = new javax.swing.JTextField();
        extraInfo2Label = new javax.swing.JLabel();
        extraInfo2TextField = new javax.swing.JTextField();
        authorLabel = new javax.swing.JLabel();
        authorTextField = new javax.swing.JTextField();
        yearLabel = new javax.swing.JLabel();
        yearTextField = new javax.swing.JTextField();
        extraInfo1Label = new javax.swing.JLabel();
        extraInfo1TextField = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        Panel_folders = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        Menu_file = new javax.swing.JMenu();
        MenuItem_add = new javax.swing.JMenuItem();
        MenuItem_manually = new javax.swing.JMenuItem();
        MenuItem_edit = new javax.swing.JMenuItem();
        MenuItem_delete = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        MenuItem_folder = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        MenuItem_exit = new javax.swing.JMenuItem();
        Menu_help = new javax.swing.JMenu();
        MenuItem_help = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("مكتبة مرجعي");

        Panel.setBackground(new java.awt.Color(245, 245, 245));

        Button_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/addIcon.png"))); // NOI18N
        Button_add.setText("إضافة مرجع");
        Button_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_addActionPerformed(evt);
            }
        });

        Button_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/deleteIcon.png"))); // NOI18N
        Button_delete.setText("حذف مرجع");
        Button_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteActionPerformed(evt);
            }
        });

        DocsList.setBackground(new java.awt.Color(249, 249, 249));
        DocsList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "تاريخ الإضافة", "سنة النشر", "الناشر", "المؤلف", "العنوان", "id", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        DocsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DocsListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(DocsList);
        if (DocsList.getColumnModel().getColumnCount() > 0) {
            DocsList.getColumnModel().getColumn(0).setPreferredWidth(130);
            DocsList.getColumnModel().getColumn(1).setPreferredWidth(30);
            DocsList.getColumnModel().getColumn(2).setPreferredWidth(50);
            DocsList.getColumnModel().getColumn(4).setPreferredWidth(150);
            DocsList.getColumnModel().getColumn(5).setMinWidth(0);
            DocsList.getColumnModel().getColumn(5).setPreferredWidth(0);
            DocsList.getColumnModel().getColumn(5).setMaxWidth(0);
            DocsList.getColumnModel().getColumn(6).setResizable(false);
            DocsList.getColumnModel().getColumn(6).setPreferredWidth(4);
        }

        TextField_search.setColumns(20);
        TextField_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextField_searchKeyReleased(evt);
            }
        });

        Label_searchIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search.png"))); // NOI18N

        jTabbedPane1.setBackground(new java.awt.Color(249, 249, 249));

        Panel_edit.setBackground(new java.awt.Color(249, 249, 249));

        Label_title.setText("العنوان");

        Label_author.setText("المؤلف");

        Label_publisher.setText("الناشر");

        Label_year.setText("سنة النشر");

        Label_pages.setText("الصفحات");

        Label_extra.setText("jLabel1");

        Label_extra1.setText("jLabel1");

        ComboBox_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "كتاب", "مقال صحفي", "مقال مجلة", "صفحة ويب", "ورقة مؤتمر", "أخرى" }));
        ComboBox_type.setSelectedIndex(5);
        ComboBox_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox_typeActionPerformed(evt);
            }
        });

        TextField_title.setColumns(15);

        TextField_author.setColumns(15);

        TextField_year.setColumns(15);

        TextField_publisher.setColumns(15);

        TextField_extra.setColumns(15);

        TextField_pages.setColumns(15);

        TextField_extra1.setColumns(15);

        Button_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/editIcon.png"))); // NOI18N
        Button_edit.setText("تعديل ");
        Button_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_editActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_editLayout = new javax.swing.GroupLayout(Panel_edit);
        Panel_edit.setLayout(Panel_editLayout);
        Panel_editLayout.setHorizontalGroup(
            Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_editLayout.createSequentialGroup()
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_editLayout.createSequentialGroup()
                        .addContainerGap(71, Short.MAX_VALUE)
                        .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TextField_title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextField_author, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextField_year, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextField_publisher, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextField_extra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextField_pages, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextField_extra1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Label_extra1)
                            .addComponent(Label_extra)
                            .addComponent(Label_pages)
                            .addComponent(Label_author)
                            .addComponent(Label_publisher)
                            .addComponent(Label_year)
                            .addComponent(Label_title)))
                    .addGroup(Panel_editLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_edit)
                            .addComponent(ComboBox_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Panel_editLayout.setVerticalGroup(
            Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_editLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ComboBox_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_title)
                    .addComponent(TextField_title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_author)
                    .addComponent(TextField_author, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_publisher)
                    .addComponent(TextField_publisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_year)
                    .addComponent(TextField_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_pages)
                    .addComponent(TextField_pages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_extra)
                    .addComponent(TextField_extra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_extra1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextField_extra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(Button_edit)
                .addGap(63, 63, 63))
        );

        jTabbedPane1.addTab("تعديل مرجع ", Panel_edit);

        Panel_manually.setBackground(new java.awt.Color(249, 249, 249));

        typeCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "كتاب", "مقال صحفي", "مقال مجلة", "صفحة ويب", "ورقة مؤتمر", "أخرى" }));
        typeCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboboxActionPerformed(evt);
            }
        });

        titleLabel.setText("العنوان");

        titleTextField.setColumns(10);
        titleTextField.setToolTipText("");

        publisherLabel.setText("الناشر");

        publisherTextField.setColumns(10);

        pagesLabel.setText("الصفحات");

        pagesTextField.setColumns(10);

        extraInfo2Label.setText("jLabel1");

        extraInfo2TextField.setColumns(10);

        authorLabel.setText("المؤلف");

        authorTextField.setColumns(10);
        authorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorTextFieldActionPerformed(evt);
            }
        });

        yearLabel.setText("سنة النشر");

        yearTextField.setColumns(10);

        extraInfo1Label.setText("الطبعة");

        extraInfo1TextField.setColumns(10);

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/manuallyIcon.png"))); // NOI18N
        addButton.setText("إدخال ");
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_manuallyLayout = new javax.swing.GroupLayout(Panel_manually);
        Panel_manually.setLayout(Panel_manuallyLayout);
        Panel_manuallyLayout.setHorizontalGroup(
            Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_manuallyLayout.createSequentialGroup()
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_manuallyLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(typeCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Panel_manuallyLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_manuallyLayout.createSequentialGroup()
                                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(authorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(publisherTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(yearLabel)
                                    .addComponent(publisherLabel)
                                    .addComponent(titleLabel)
                                    .addComponent(authorLabel)))
                            .addGroup(Panel_manuallyLayout.createSequentialGroup()
                                .addComponent(pagesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(pagesLabel))
                            .addGroup(Panel_manuallyLayout.createSequentialGroup()
                                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(extraInfo1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(extraInfo2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(extraInfo2Label)
                                    .addComponent(extraInfo1Label)))))
                    .addGroup(Panel_manuallyLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(addButton)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        Panel_manuallyLayout.setVerticalGroup(
            Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_manuallyLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(typeCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel)
                    .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(authorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(authorLabel))
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(publisherLabel)
                    .addComponent(publisherTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yearLabel)
                    .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pagesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pagesLabel))
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(extraInfo1Label)
                    .addComponent(extraInfo1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(extraInfo2Label)
                    .addComponent(extraInfo2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addComponent(addButton)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("إدخال يدوي", Panel_manually);

        Panel_folders.setBackground(new java.awt.Color(249, 249, 249));

        javax.swing.GroupLayout Panel_foldersLayout = new javax.swing.GroupLayout(Panel_folders);
        Panel_folders.setLayout(Panel_foldersLayout);
        Panel_foldersLayout.setHorizontalGroup(
            Panel_foldersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );
        Panel_foldersLayout.setVerticalGroup(
            Panel_foldersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 543, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("الملفات", Panel_folders);

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGap(0, 135, Short.MAX_VALUE)
                        .addComponent(Button_delete)
                        .addGap(18, 18, 18)
                        .addComponent(Button_add)
                        .addGap(41, 41, 41)
                        .addComponent(TextField_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Label_searchIcon)
                        .addGap(16, 16, 16))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Button_add)
                                    .addComponent(Button_delete)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TextField_search, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Label_searchIcon, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        Menu_file.setText("ملف");

        MenuItem_add.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_add.setText("إضافة مرجع");
        MenuItem_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_addActionPerformed(evt);
            }
        });
        Menu_file.add(MenuItem_add);

        MenuItem_manually.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_manually.setText("إدخال يدوي");
        MenuItem_manually.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_manuallyActionPerformed(evt);
            }
        });
        Menu_file.add(MenuItem_manually);

        MenuItem_edit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_edit.setText("تعديل مرجع");
        MenuItem_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_editActionPerformed(evt);
            }
        });
        Menu_file.add(MenuItem_edit);

        MenuItem_delete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_delete.setText("حذف مرجع");
        MenuItem_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_deleteActionPerformed(evt);
            }
        });
        Menu_file.add(MenuItem_delete);
        Menu_file.add(jSeparator1);

        MenuItem_folder.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_folder.setText("إنشاء مجلد");
        Menu_file.add(MenuItem_folder);
        Menu_file.add(jSeparator2);

        MenuItem_exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        MenuItem_exit.setText("خروج");
        MenuItem_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_exitActionPerformed(evt);
            }
        });
        Menu_file.add(MenuItem_exit);

        MenuBar.add(Menu_file);

        Menu_help.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/helpIcon.png"))); // NOI18N

        MenuItem_help.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_help.setText("مساعدة");
        Menu_help.add(MenuItem_help);

        MenuBar.add(Menu_help);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TextField_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextField_searchKeyReleased
        // TODO add your handling code here:
        String key = TextField_search.getText();
        Search(key);
    }//GEN-LAST:event_TextField_searchKeyReleased

    private void DocsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DocsListMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(Panel_edit);
        int i = DocsList.getSelectedRow();
      TableModel model = DocsList.getModel();  
        
                    TextField_year.setText(" ");
                    TextField_publisher.setText(" ");
                    TextField_author.setText(" ");
                    TextField_title.setText(" ");
                    TextField_pages.setText(" ");
                    TextField_extra.setText(" ");
                    TextField_extra1.setText(" ");
                    Label_extra.setVisible(false);
                    TextField_extra.setVisible(false);
                    Label_extra1.setVisible(false);
                    TextField_extra1.setVisible(false);
       try 
        {
                    
            Connection con = DBConnection();
            Statement stmt = con.createStatement();
            int ID = (int) model.getValueAt(i, 5);
            ResultSet rs = stmt.executeQuery("SELECT documentType FROM referencedocument WHERE documentID = "+ID);
            rs.next();
            String type = rs.getString("documentType");
         
            switch (type) {
                case "book":
                    ComboBox_type.setSelectedIndex(0);
                    
                    Label_extra.setVisible(true);
                    TextField_extra.setVisible(true);
                    Label_extra.setText("الطبعة");
                    
                    if ( model.getValueAt(i,4) != null )
                    { TextField_title.setText(model.getValueAt(i,4).toString()); }
                    if ( model.getValueAt(i,3) != null )
                    { TextField_author.setText(model.getValueAt(i,3).toString()); }
                    if ( model.getValueAt(i,2) != null )
                    { TextField_publisher.setText(model.getValueAt(i,2).toString()); }
                     TextField_year.setText(model.getValueAt(i,1).toString()); 
                    
                    int bookID = (int) model.getValueAt(i, 5);
                    rs = stmt.executeQuery("SELECT pages FROM referencedocument WHERE documentID = "+bookID);
                    rs.next();
                    TextField_pages.setText(rs.getInt("pages")+"");
       
                   // Label_extra1.setVisible(false);
                  //  TextField_extra1.setVisible(false);  
                    rs = stmt.executeQuery("SELECT * FROM book WHERE documentID = "+bookID);
                    rs.next();
                    TextField_extra.setText(rs.getInt("edition")+"");
                    break;
                    
                case "journalarticle":
                    ComboBox_type.setSelectedIndex(1);
                    
                    Label_extra.setVisible(true);
                    TextField_extra.setVisible(true);
                    Label_extra1.setVisible(true);
                    TextField_extra1.setVisible(true);
                    Label_extra.setText("الصحيفة");
                    Label_extra1.setText("الحجم");
                    
                    if ( model.getValueAt(i,4) != null )
                    { TextField_title.setText(model.getValueAt(i,4).toString()); }
                    if ( model.getValueAt(i,3) != null )
                    { TextField_author.setText(model.getValueAt(i,3).toString()); }
                    if ( model.getValueAt(i,2) != null )
                    { TextField_publisher.setText(model.getValueAt(i,2).toString()); }
                     TextField_year.setText(model.getValueAt(i,1).toString());
                    
                    int jarticleID = (int) model.getValueAt(i, 5);
                    rs = stmt.executeQuery("SELECT pages FROM referencedocument WHERE documentID = "+jarticleID);
                    rs.next();
                    TextField_pages.setText(rs.getInt("pages")+"");
       
                    rs = stmt.executeQuery("SELECT * FROM journalarticle WHERE documentID = "+jarticleID);
                    rs.next();
                    TextField_extra.setText(rs.getString("journalName"));
                    TextField_extra1.setText(rs.getString("volume")); 
                    break;
                    
                case "magazinearticle":
                    ComboBox_type.setSelectedIndex(2);
                    
                    Label_extra.setVisible(true);
                    TextField_extra.setVisible(true);
                    Label_extra1.setVisible(true);
                    TextField_extra1.setVisible(true);
                    Label_extra.setText("المجلة");
                    Label_extra1.setText("الشهر");
                    
                    if ( model.getValueAt(i,4) != null )
                    { TextField_title.setText(model.getValueAt(i,4).toString()); }
                    if ( model.getValueAt(i,3) != null )
                    { TextField_author.setText(model.getValueAt(i,3).toString()); }
                    if ( model.getValueAt(i,2) != null )
                    { TextField_publisher.setText(model.getValueAt(i,2).toString()); }
                     TextField_year.setText(model.getValueAt(i,1).toString());
                    
                    int marticleID = (int) model.getValueAt(i, 5);
                    rs = stmt.executeQuery("SELECT pages FROM referencedocument WHERE documentID = "+marticleID);
                    rs.next();
                    TextField_pages.setText(rs.getInt("pages")+"");
       
                    rs = stmt.executeQuery("SELECT * FROM magazinearticle WHERE documentID = "+marticleID);
                    rs.next();
                    TextField_extra.setText(rs.getString("magazineName"));
                    TextField_extra1.setText(rs.getString("month")); 
                    break;
                    
                case "webpage":
                    ComboBox_type.setSelectedIndex(3);
                    
                    Label_extra.setVisible(true);
                    TextField_extra.setVisible(true);
                    Label_extra1.setVisible(true);
                    TextField_extra1.setVisible(true);
                    Label_extra.setText("الرابط");
                    Label_extra1.setText("تاريخ الوصول");
                    
                    if ( model.getValueAt(i,4) != null )
                    { TextField_title.setText(model.getValueAt(i,4).toString()); }
                    if ( model.getValueAt(i,3) != null )
                    { TextField_author.setText(model.getValueAt(i,3).toString()); }
                    if ( model.getValueAt(i,2) != null )
                    { TextField_publisher.setText(model.getValueAt(i,2).toString()); }
                     TextField_year.setText(model.getValueAt(i,1).toString());
                    
                    int webID = (int) model.getValueAt(i, 5);
                    rs = stmt.executeQuery("SELECT pages FROM referencedocument WHERE documentID = "+webID);
                    rs.next();
                    TextField_pages.setText(rs.getInt("pages")+"");
       
                    rs = stmt.executeQuery("SELECT * FROM webpage WHERE documentID = "+webID);
                    rs.next();
                    TextField_extra.setText(rs.getString("url"));
                    TextField_extra1.setText(rs.getString("AccessDate")); 
                    break;
                    
                    case "conferenceproceeding":
                    ComboBox_type.setSelectedIndex(4);
                    
                    Label_extra.setVisible(true);
                    TextField_extra.setVisible(true);
                    Label_extra1.setVisible(true);
                    TextField_extra1.setVisible(true);
                    Label_extra.setText("المؤتمر");
                    Label_extra1.setText("المكان");
                    
                    if ( model.getValueAt(i,4) != null )
                    { TextField_title.setText(model.getValueAt(i,4).toString()); }
                    if ( model.getValueAt(i,3) != null )
                    { TextField_author.setText(model.getValueAt(i,3).toString()); }
                    if ( model.getValueAt(i,2) != null )
                    { TextField_publisher.setText(model.getValueAt(i,2).toString()); }
                     TextField_year.setText(model.getValueAt(i,1).toString());
                    
                    int cpID = (int) model.getValueAt(i, 5);
                    rs = stmt.executeQuery("SELECT pages FROM referencedocument WHERE documentID = "+cpID);
                    rs.next();
                    TextField_pages.setText(rs.getInt("pages")+"");
       
                    rs = stmt.executeQuery("SELECT * FROM conferenceproceeding WHERE documentID = "+cpID);
                    rs.next();
                    TextField_extra.setText(rs.getString("conferenceName"));
                    TextField_extra1.setText(rs.getString("place")); 
                    break;
                    
                case "other":
                    ComboBox_type.setSelectedIndex(5);
                    
                    Label_extra.setVisible(false);
                    TextField_extra.setVisible(false);
                    Label_extra1.setVisible(false);
                    TextField_extra1.setVisible(false);
                    
                    if ( model.getValueAt(i,4) != null )
                    { TextField_title.setText(model.getValueAt(i,4).toString()); }
                    if ( model.getValueAt(i,3) != null )
                    { TextField_author.setText(model.getValueAt(i,3).toString()); }
                    if ( model.getValueAt(i,2) != null )
                    { TextField_publisher.setText(model.getValueAt(i,2).toString()); }
                     TextField_year.setText(model.getValueAt(i,1).toString());
                    
                    int otherID = (int) model.getValueAt(i, 5);
                    rs = stmt.executeQuery("SELECT pages FROM referencedocument WHERE documentID = "+otherID);
                    rs.next();
                    TextField_pages.setText(rs.getInt("pages")+"");
                    break;
            }
                
        
        } catch (Exception ex)
        {
            System.out.print(ex.getMessage());
        }
    }//GEN-LAST:event_DocsListMouseClicked

  /*  public void ExecuteMyQuery(String type, String title, String author, String publisher, int year, int pages, String extra, String extra1)
    {
        Connection con = DBConnection();
        ResultSet rs;
        Statement stmt;
        
        try
        {
            stmt = con.createStatement();
            String query;
            query = "INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('"+type+"', '"+title+"', '"+author+"', '"+pages+"', '"+publisher+"', '"+year+"')";
            stmt.executeUpdate(query);
            RefereshTable();
            rs = stmt.executeQuery("SELECT documentID FROM referencedocumentWHERE title = '"+title+"' AND author = '"+author+"'");
            rs.next();
            int id = rs.getInt("documentID");
            stmt.executeUpdate("INSERT INTO book (documentID, edition)"+" VALUES ( '"+ id +"', '"+Integer.parseInt(extra)+"')");
        } catch (Exception ex)
        {
            System.out.print(ex.getMessage());
        }
        
    } */ 
    
    public void RefereshTable()
    {
         DefaultTableModel defModel = (DefaultTableModel) DocsList.getModel();
         defModel.setRowCount(0);
         ShowDocuments();
    }
    private void Button_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_editActionPerformed
        // TODO add your handling code here:
        //
        try{ 
            int i = DocsList.getSelectedRow();
            TableModel model = DocsList.getModel();
            Connection con = DBConnection();
            Statement stmt = con.createStatement();
            int ID = (int) model.getValueAt(i, 5);
            ResultSet rs = stmt.executeQuery("SELECT documentType FROM referencedocument WHERE documentID = "+ID);
            rs.next();
            String type = rs.getString("documentType");
       
         String value1= TextField_title.getText();
         String value2= TextField_author.getText();
         String value3= TextField_publisher.getText();
         String value4= TextField_year.getText();  
         String value5= TextField_pages.getText();
         String value6= TextField_extra.getText();  
         String value7= TextField_extra1.getText();
         
         String query = "update referencedocument set title='"+value1+"' , author='"+value2+"', publisher='"+value3+"', publishYear='"+value4+"', pages='"+value5+"' where documentID='"+ID+"' ";
         PreparedStatement pst = con.prepareStatement(query);
         pst.execute();
         
         RefereshTable();
         
         switch (type)
         {
             case "book":
                  query = "update book set edition='"+value6+"' where documentID='"+ID+"' ";
                  pst = con.prepareStatement(query);
                  pst.execute();
                  
                   RefereshTable();
                 break;
                 
             case "journalarticle":
                  query = "update journalarticle set journalName='"+value6+"' , volume='"+value7+"' where documentID='"+ID+"' ";
                  pst = con.prepareStatement(query);
                  pst.execute();
                  
                  RefereshTable();
                 break;
                 
             case "magazinearticle":
                 query = "update magazinearticle set magazineName='"+value6+"' , month='"+value7+"' where documentID='"+ID+"' ";
                 pst = con.prepareStatement(query);
                 pst.execute();
                 
                 RefereshTable();
                 break;
                 
             case "webpage":
                 query = "update webpage set url='"+value6+"' , AccessDate='"+value7+"' where documentID='"+ID+"' ";
                 pst = con.prepareStatement(query);
                 pst.execute();
                 
                 RefereshTable();
                 break;
                 
             case "conferenceproceeding":
                 query = "update conferenceproceeding set conferenceName='"+value6+"' , place='"+value7+"' where documentID='"+ID+"' ";
                 pst = con.prepareStatement(query);
                 pst.execute();
                 
                 RefereshTable();
                 break;
         }

                    TextField_year.setText(" ");
                    TextField_publisher.setText(" ");
                    TextField_author.setText(" ");
                    TextField_title.setText(" ");
                    TextField_pages.setText(" ");
                    TextField_extra.setText(" ");
                    TextField_extra1.setText(" ");
                    Label_extra.setVisible(false);
                    TextField_extra.setVisible(false);
                    Label_extra1.setVisible(false);
                    TextField_extra1.setVisible(false);
                    
         JOptionPane.showMessageDialog(null, "تم التحديث بنجاح");
         }catch (Exception ex)
          {
              JOptionPane.showMessageDialog(null, ex.getMessage());
          }         
    }//GEN-LAST:event_Button_editActionPerformed

    private void Button_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_addActionPerformed
        // TODO add your handling code here:
        try 
            {
            JFileChooser chooser = new JFileChooser();
            String fName = "";
            //*****************filter***********************
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Pdf file(.pdf)", "pdf");
            chooser.setFileFilter(filter);
            
            //*****************JFileChooser*****************
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
            fName = chooser.getSelectedFile().getPath();
            }
            
            //**************get PDF file info***************
            PDFManager pdfManager = new PDFManager();
            pdfManager.setFilePath(fName);
            pdfManager.ToText();  
            
          //  addNewDoc();
            RefereshTable();
            } catch (HeadlessException | IOException ex)
                  {
                    System.out.println(ex.getMessage());
                  }
    }//GEN-LAST:event_Button_addActionPerformed

    private void Button_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteActionPerformed
        // TODO add your handling code here:
        
        try {
            
           int confirmed = JOptionPane.showConfirmDialog(null, "سيتم حذف المرجع", "تأكيد",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                   // Check the user option
                   if (confirmed == 0)
                   {
            int i = DocsList.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel)DocsList.getModel();
            Connection con = DBConnection();
            Statement stmt = con.createStatement();
            int row = DocsList.getSelectedRow();
            int ID = (int) model.getValueAt(i, 5);
   
                stmt.execute("DELETE FROM referencedocument WHERE documentID ="+ID+"");  
                int  modelRow = DocsList.convertRowIndexToModel( row );
                   
                   
                  model.removeRow( modelRow );
                  
                    TextField_year.setText(" ");
                    TextField_publisher.setText(" ");
                    TextField_author.setText(" ");
                    TextField_title.setText(" ");
                    TextField_pages.setText(" ");
                    TextField_extra.setText(" ");
                    TextField_extra1.setText(" ");
                    Label_extra.setVisible(false);
                    TextField_extra.setVisible(false);
                    Label_extra1.setVisible(false);
                    TextField_extra1.setVisible(false);
                   }
        }
             catch(Exception e) {
                e.printStackTrace();
  System.out.println( e.getMessage( ) ); }
    
    }//GEN-LAST:event_Button_deleteActionPerformed

    private void MenuItem_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_addActionPerformed
        // TODO add your handling code here:
         try 
            {
            JFileChooser chooser = new JFileChooser();
            String fName = "";
            //*****************filter***********************
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Pdf file(.pdf)", "pdf");
            chooser.setFileFilter(filter);
            
            //*****************JFileChooser*****************
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
            fName = chooser.getSelectedFile().getPath();
            }
            
            //**************get PDF file info***************
            PDFManager pdfManager = new PDFManager();
            pdfManager.setFilePath(fName);
            pdfManager.ToText();  
           // addNewDoc();
            RefereshTable();

            } catch (HeadlessException | IOException ex)
                  {
                    System.out.println(ex.getMessage());
                  }
    }//GEN-LAST:event_MenuItem_addActionPerformed

    private void MenuItem_manuallyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_manuallyActionPerformed
        // TODO add your handling code here:
        
        // document information insertion
        int index = typeCombobox.getSelectedIndex();
      //  Library lib = new Library();
      Connection con = DBConnection();
      Statement stmt; 
      ResultSet rs;
        switch ( index )
        {
            case 0:
            // Code to insert book information here.
              try
            {
             //   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                //   LocalDate localDate = LocalDate.now();
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('book', '"+titleTextField.getText()+"', '"+authorTextField.getText()+"', '"+Integer.parseInt(pagesTextField.getText())+"', '"+publisherTextField.getText()+"', '"+Integer.parseInt(yearTextField.getText())+"')");
                rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '"+ titleTextField.getText() +"' AND author = '"+ authorTextField.getText() +"'");
                rs.next();
                int id = rs.getInt("documentID");
                stmt.executeUpdate("INSERT INTO book (documentID, edition)"+" VALUES ( '"+ id +"', '"+Integer.parseInt(extraInfo1TextField.getText())+"')");
                RefereshTable();
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            } 

         /*   String type = "book";
            String title = titleTextField.getText();
            String author = authorTextField.getText();
            int pages = Integer.parseInt(pagesTextField.getText());
            String publisher = publisherTextField.getText();
            int year = Integer.parseInt(yearTextField.getText());
            String extra = extraInfo1TextField.getText();

            lib.ExecuteMyQuery(type, title, author, publisher, year, pages, extra, null); */

            break;
            case 1:
            // Code to insert journal article information here.
            try
            {
               // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                //    LocalDate localDate = LocalDate.now();
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('journalarticle', '"+titleTextField.getText()+"', '"+authorTextField.getText()+"', '"+Integer.parseInt(pagesTextField.getText())+"', '"+publisherTextField.getText()+"', '"+Integer.parseInt(yearTextField.getText())+"')");
                rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '"+ titleTextField.getText() +"' AND author = '"+ authorTextField.getText() +"'");
                rs.next();
                int id = rs.getInt("documentID");
                stmt.executeUpdate("INSERT INTO journalarticle (documentID, journalName, volume)"+" VALUES ( '"+ id +"', '"+extraInfo1TextField.getText()+"', '"+extraInfo2TextField.getText()+"')");
                RefereshTable();
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
            break;

            case 2:
            // Code to insert magazine article information here.
            try
            {
              //  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                //     LocalDate localDate = LocalDate.now();
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('magazinearticle', '"+titleTextField.getText()+"', '"+authorTextField.getText()+"', '"+Integer.parseInt(pagesTextField.getText())+"', '"+publisherTextField.getText()+"', '"+Integer.parseInt(yearTextField.getText())+"')");
                rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '"+ titleTextField.getText() +"' AND author = '"+ authorTextField.getText() +"'");
                rs.next();
                int id = rs.getInt("documentID");
                stmt.executeUpdate("INSERT INTO magazinearticle (documentID, magazineName, month) VALUES ( '"+ id +"', '"+extraInfo1TextField.getText()+"', '"+extraInfo2TextField.getText()+"')");
                RefereshTable();
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }

            break;

            case 3:
            // Code to insert web page information here.
            try
            {
             //   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                //     LocalDate localDate = LocalDate.now();
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('webpage', '"+titleTextField.getText()+"', '"+authorTextField.getText()+"', '"+Integer.parseInt(pagesTextField.getText())+"', '"+publisherTextField.getText()+"', '"+Integer.parseInt(yearTextField.getText())+"')");
                rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '"+ titleTextField.getText() +"' AND author = '"+ authorTextField.getText() +"'");
                rs.next();
                int id = rs.getInt("documentID");
                stmt.executeUpdate("INSERT INTO webpage (documentID, url, AccessDate) VALUES ( '"+ id +"', '"+extraInfo1TextField.getText()+"', '"+extraInfo2TextField.getText()+"')");
                RefereshTable();
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }

            break;

            case 4:
            // Code to insert conference proceeding information here.
            try
            {
             //   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                //    LocalDate localDate = LocalDate.now();
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('conferenceproceeding', '"+titleTextField.getText()+"', '"+authorTextField.getText()+"', '"+Integer.parseInt(pagesTextField.getText())+"', '"+publisherTextField.getText()+"', '"+Integer.parseInt(yearTextField.getText())+"')");
                rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '"+ titleTextField.getText() +"' AND author = '"+ authorTextField.getText() +"'");
                rs.next();
                int id = rs.getInt("documentID");
                stmt.executeUpdate("INSERT INTO conferenceproceeding (documentID, conferenceName, place) VALUES ( '"+ id +"', '"+extraInfo1TextField.getText()+"', '"+extraInfo2TextField.getText()+"')");
                RefereshTable();
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }

            break;

            case 5:
            // Code to insert other document information here.
            try
            {
            //    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                //   LocalDate localDate = LocalDate.now();
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('other', '"+titleTextField.getText()+"', '"+authorTextField.getText()+"', '"+Integer.parseInt(pagesTextField.getText())+"', '"+publisherTextField.getText()+"', '"+Integer.parseInt(yearTextField.getText())+"')");
                RefereshTable();
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }

            break;
        } // End of switch
        
    }//GEN-LAST:event_MenuItem_manuallyActionPerformed

    private void MenuItem_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_deleteActionPerformed
        // TODO add your handling code here:
        try {
            int i = DocsList.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel)DocsList.getModel();
            Connection con = DBConnection();
            Statement stmt = con.createStatement();
            int row = DocsList.getSelectedRow();
            int ID = (int) model.getValueAt(i, 5);
   
                stmt.execute("DELETE FROM referencedocument WHERE documentID ="+ID+"");  
                int  modelRow = DocsList.convertRowIndexToModel( row );
                   JOptionPane.showConfirmDialog(null, "سيتم حذف المرجع", "Confirm",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                  model.removeRow( modelRow );
        }
             catch(Exception e) {
                e.printStackTrace();
  System.out.println( e.getMessage( ) ); }
    }//GEN-LAST:event_MenuItem_deleteActionPerformed

    private void MenuItem_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_exitActionPerformed
        // TODO add your handling code here:
        Platform.exit();
        System.exit(0);
    }//GEN-LAST:event_MenuItem_exitActionPerformed

    private void MenuItem_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_editActionPerformed
        // TODO add your handling code here:
        try{ 
            int i = DocsList.getSelectedRow();
            TableModel model = DocsList.getModel();
            Connection con = DBConnection();
            Statement stmt = con.createStatement();
            int ID = (int) model.getValueAt(i, 5);
            ResultSet rs = stmt.executeQuery("SELECT documentType FROM referencedocument WHERE documentID = "+ID);
            rs.next();
            String type = rs.getString("documentType");
       
         String value1= TextField_title.getText();
         String value2= TextField_author.getText();
         String value3= TextField_publisher.getText();
         String value4= TextField_year.getText();  
         String value5= TextField_pages.getText();
         String value6= TextField_extra.getText();  
         String value7= TextField_extra1.getText();
         
         String query = "update referencedocument set title='"+value1+"' , author='"+value2+"', publisher='"+value3+"', publishYear='"+value4+"', pages='"+value5+"' where documentID='"+ID+"' ";
         PreparedStatement pst = con.prepareStatement(query);
         pst.execute();
         
         RefereshTable();
         
         switch (type)
         {
             case "book":
                  query = "update book set edition='"+value6+"' where documentID='"+ID+"' ";
                  pst = con.prepareStatement(query);
                  pst.execute();
                  
                   RefereshTable();
                 break;
                 
             case "journalarticle":
                  query = "update journalarticle set journalName='"+value6+"' , volume='"+value7+"' where documentID='"+ID+"' ";
                  pst = con.prepareStatement(query);
                  pst.execute();
                  
                  RefereshTable();
                 break;
                 
             case "magazinearticle":
                 query = "update magazinearticle set magazineName='"+value6+"' , month='"+value7+"' where documentID='"+ID+"' ";
                 pst = con.prepareStatement(query);
                 pst.execute();
                 
                 RefereshTable();
                 break;
                 
             case "webpage":
                 query = "update webpage set url='"+value6+"' , AccessDate='"+value7+"' where documentID='"+ID+"' ";
                 pst = con.prepareStatement(query);
                 pst.execute();
                 
                 RefereshTable();
                 break;
                 
             case "conferenceproceeding":
                 query = "update conferenceproceeding set conferenceName='"+value6+"' , place='"+value7+"' where documentID='"+ID+"' ";
                 pst = con.prepareStatement(query);
                 pst.execute();
                 
                 RefereshTable();
                 break;
         }

         JOptionPane.showMessageDialog(null, "تم التحديث بنجاح");
         }catch (Exception ex)
          {
              JOptionPane.showMessageDialog(null, ex.getMessage());
          }
    }//GEN-LAST:event_MenuItem_editActionPerformed

    private void typeComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboboxActionPerformed
        // TODO add your handling code here:

        // 'Type' Combobox Selection
        int index = typeCombobox.getSelectedIndex();
        switch ( index )
        {
            case 0:
            // Code for show Book form
            extraInfo1Label.setText("الطبعة");
            extraInfo1Label.setVisible(true);
            extraInfo1TextField.setVisible(true);
            extraInfo2Label.setVisible(false);
            extraInfo2TextField.setVisible(false);

            break;
            case 1:
            // Code for show Journal Article form
            extraInfo1Label.setText("الصحيفة");
            extraInfo1Label.setVisible(true);
            extraInfo1TextField.setVisible(true);
            extraInfo2Label.setText("الحجم");
            extraInfo2Label.setVisible(true);
            extraInfo2TextField.setVisible(true);

            break;
            case 2:
            // Code for show Magazine Article form
            extraInfo1Label.setText("المجلة");
            extraInfo1Label.setVisible(true);
            extraInfo1TextField.setVisible(true);
            extraInfo2Label.setText("الشهر");
            extraInfo2Label.setVisible(true);
            extraInfo2TextField.setVisible(true);

            break;
            case 3:
            // Code for show Web Page form
            extraInfo1Label.setText("الرابط");
            extraInfo1Label.setVisible(true);
            extraInfo1TextField.setVisible(true);
            extraInfo2Label.setText("تاريخ الوصول");
            extraInfo2Label.setVisible(true);
            extraInfo2TextField.setVisible(true);

            break;
            case 4:
            // Code for show Conference Proceeding form
            extraInfo1Label.setText("المؤتمر");
            extraInfo1Label.setVisible(true);
            extraInfo1TextField.setVisible(true);
            extraInfo2Label.setText("المكان");
            extraInfo2Label.setVisible(true);
            extraInfo2TextField.setVisible(true);

            break;
            case 5:
            // Code for show Other Document form
            extraInfo1Label.setVisible(false);
            extraInfo1TextField.setVisible(false);
            extraInfo2Label.setVisible(false);
            extraInfo2TextField.setVisible(false);

        } // End Switch Statment.

    }//GEN-LAST:event_typeComboboxActionPerformed

    private void authorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authorTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_authorTextFieldActionPerformed

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addButtonMouseClicked

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:

        // document information insertion
        int index = typeCombobox.getSelectedIndex();
      //  Library lib = new Library();
      Connection con = DBConnection();
      Statement stmt; 
      ResultSet rs;
        switch ( index )
        {
            case 0:
            // Code to insert book information here.
              try
            {
             //   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                //   LocalDate localDate = LocalDate.now();
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('book', '"+titleTextField.getText()+"', '"+authorTextField.getText()+"', '"+Integer.parseInt(pagesTextField.getText())+"', '"+publisherTextField.getText()+"', '"+Integer.parseInt(yearTextField.getText())+"')");
                rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '"+ titleTextField.getText() +"' AND author = '"+ authorTextField.getText() +"'");
                rs.next();
                int id = rs.getInt("documentID");
                stmt.executeUpdate("INSERT INTO book (documentID, edition)"+" VALUES ( '"+ id +"', '"+Integer.parseInt(extraInfo1TextField.getText())+"')");
                RefereshTable();
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            } 

         /*   String type = "book";
            String title = titleTextField.getText();
            String author = authorTextField.getText();
            int pages = Integer.parseInt(pagesTextField.getText());
            String publisher = publisherTextField.getText();
            int year = Integer.parseInt(yearTextField.getText());
            String extra = extraInfo1TextField.getText();

            lib.ExecuteMyQuery(type, title, author, publisher, year, pages, extra, null); */

            break;
            case 1:
            // Code to insert journal article information here.
            try
            {
               // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                //    LocalDate localDate = LocalDate.now();
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('journalarticle', '"+titleTextField.getText()+"', '"+authorTextField.getText()+"', '"+Integer.parseInt(pagesTextField.getText())+"', '"+publisherTextField.getText()+"', '"+Integer.parseInt(yearTextField.getText())+"')");
                rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '"+ titleTextField.getText() +"' AND author = '"+ authorTextField.getText() +"'");
                rs.next();
                int id = rs.getInt("documentID");
                stmt.executeUpdate("INSERT INTO journalarticle (documentID, journalName, volume)"+" VALUES ( '"+ id +"', '"+extraInfo1TextField.getText()+"', '"+extraInfo2TextField.getText()+"')");
                RefereshTable();
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
            break;

            case 2:
            // Code to insert magazine article information here.
            try
            {
              //  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                //     LocalDate localDate = LocalDate.now();
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('magazinearticle', '"+titleTextField.getText()+"', '"+authorTextField.getText()+"', '"+Integer.parseInt(pagesTextField.getText())+"', '"+publisherTextField.getText()+"', '"+Integer.parseInt(yearTextField.getText())+"')");
                rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '"+ titleTextField.getText() +"' AND author = '"+ authorTextField.getText() +"'");
                rs.next();
                int id = rs.getInt("documentID");
                stmt.executeUpdate("INSERT INTO magazinearticle (documentID, magazineName, month) VALUES ( '"+ id +"', '"+extraInfo1TextField.getText()+"', '"+extraInfo2TextField.getText()+"')");
                RefereshTable();
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }

            break;

            case 3:
            // Code to insert web page information here.
            try
            {
             //   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                //     LocalDate localDate = LocalDate.now();
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('webpage', '"+titleTextField.getText()+"', '"+authorTextField.getText()+"', '"+Integer.parseInt(pagesTextField.getText())+"', '"+publisherTextField.getText()+"', '"+Integer.parseInt(yearTextField.getText())+"')");
                rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '"+ titleTextField.getText() +"' AND author = '"+ authorTextField.getText() +"'");
                rs.next();
                int id = rs.getInt("documentID");
                stmt.executeUpdate("INSERT INTO webpage (documentID, url, AccessDate) VALUES ( '"+ id +"', '"+extraInfo1TextField.getText()+"', '"+extraInfo2TextField.getText()+"')");
                RefereshTable();
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }

            break;

            case 4:
            // Code to insert conference proceeding information here.
            try
            {
             //   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                //    LocalDate localDate = LocalDate.now();
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('conferenceproceeding', '"+titleTextField.getText()+"', '"+authorTextField.getText()+"', '"+Integer.parseInt(pagesTextField.getText())+"', '"+publisherTextField.getText()+"', '"+Integer.parseInt(yearTextField.getText())+"')");
                rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '"+ titleTextField.getText() +"' AND author = '"+ authorTextField.getText() +"'");
                rs.next();
                int id = rs.getInt("documentID");
                stmt.executeUpdate("INSERT INTO conferenceproceeding (documentID, conferenceName, place) VALUES ( '"+ id +"', '"+extraInfo1TextField.getText()+"', '"+extraInfo2TextField.getText()+"')");
                RefereshTable();
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }

            break;

            case 5:
            // Code to insert other document information here.
            try
            {
            //    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                //   LocalDate localDate = LocalDate.now();
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear)"+" VALUES ('other', '"+titleTextField.getText()+"', '"+authorTextField.getText()+"', '"+Integer.parseInt(pagesTextField.getText())+"', '"+publisherTextField.getText()+"', '"+Integer.parseInt(yearTextField.getText())+"')");
                RefereshTable();
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }

            break;
        } // End of switch

       // this.setVisible(false);

        /*  Library lib;
        lib = new Library();
        lib.setVisible(false);
        lib.setVisible(true); */
    }//GEN-LAST:event_addButtonActionPerformed

    private void ComboBox_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox_typeActionPerformed
        // TODO add your handling code here:
        int index = typeCombobox.getSelectedIndex();
        switch ( index )
        {
            case 0:
            // Code for show Book form
            Label_extra.setText("الطبعة");
            Label_extra.setVisible(true);
            TextField_extra.setVisible(true);
            Label_extra1.setVisible(false);
            TextField_extra1.setVisible(false);

            break;
            case 1:
            // Code for show Journal Article form
            Label_extra.setText("الصحيفة");
            Label_extra.setVisible(true);
            TextField_extra.setVisible(true);
            Label_extra1.setText("الحجم");
            Label_extra1.setVisible(true);
            TextField_extra1.setVisible(true);

            break;
            case 2:
            // Code for show Magazine Article form
            Label_extra.setText("المجلة");
            Label_extra.setVisible(true);
            TextField_extra.setVisible(true);
            Label_extra1.setText("الشهر");
            Label_extra1.setVisible(true);
            TextField_extra1.setVisible(true);

            break;
            case 3:
            // Code for show Web Page form
            Label_extra.setText("الرابط");
            Label_extra.setVisible(true);
            TextField_extra.setVisible(true);
            Label_extra1.setText("تاريخ الوصول");
            Label_extra1.setVisible(true);
            TextField_extra1.setVisible(true);

            break;
            case 4:
            // Code for show Conference Proceeding form
            Label_extra.setText("المؤتمر");
            Label_extra.setVisible(true);
            TextField_extra.setVisible(true);
            Label_extra1.setText("المكان");
            Label_extra1.setVisible(true);
            TextField_extra1.setVisible(true);

            break;
            case 5:
            // Code for show Other Document form
            Label_extra.setVisible(false);
            TextField_extra.setVisible(false);
            Label_extra1.setVisible(false);
            TextField_extra1.setVisible(false);

        } // End Switch Statment.
    }//GEN-LAST:event_ComboBox_typeActionPerformed

    /*    public void addNewDoc(){
            String Query;
            Connection con = DBConnection();
            ResultSet rs;
            
              try{
                Statement stmt = con.createStatement();
                Query = "SELECT * FROM referencedocument WHERE documentID = (SELECT MAX(documentID) FROM referencedocument)";
                rs = stmt.executeQuery(Query);
                DefaultTableModel model = (DefaultTableModel) DocsList.getModel();
                  if(rs.next()){
                 int id = rs.getInt("documentID");
                 String title = rs.getString("title");
                 String author = rs.getString("author");
                // int pages = rs.getInt("pages");
                 String publisher = rs.getString("publisher");
                 int publishYear = rs.getInt("publishYear");
                 String dateAdded = rs.getString("DateAdded"); 
             //    String[] docs = {dateAdded, publishYear+"", publisher, author, title, id+""};
                 
                 String[] docs = new String[6];
                 docs[0] = rs.getString("DateAdded"); 
                 docs[1] = rs.getInt("publishYear")+"";
                 docs[2] = rs.getString("publisher");
                 docs[3] = rs.getString("author");
                 docs[4] = rs.getString("title");
                 docs[5] = rs.getInt("documetID")+""; 
                 
                 model.addRow(docs); }
                  
                }catch(SQLException ex)
                  {
                    System.out.println(ex.getMessage());
                  }
} */
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Library.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Library.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Library.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Library.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Library().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_add;
    private javax.swing.JButton Button_delete;
    private javax.swing.JButton Button_edit;
    private javax.swing.JComboBox<String> ComboBox_type;
    private javax.swing.JTable DocsList;
    private javax.swing.JLabel Label_author;
    private javax.swing.JLabel Label_extra;
    private javax.swing.JLabel Label_extra1;
    private javax.swing.JLabel Label_pages;
    private javax.swing.JLabel Label_publisher;
    private javax.swing.JLabel Label_searchIcon;
    private javax.swing.JLabel Label_title;
    private javax.swing.JLabel Label_year;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenuItem MenuItem_add;
    private javax.swing.JMenuItem MenuItem_delete;
    private javax.swing.JMenuItem MenuItem_edit;
    private javax.swing.JMenuItem MenuItem_exit;
    private javax.swing.JMenuItem MenuItem_folder;
    private javax.swing.JMenuItem MenuItem_help;
    private javax.swing.JMenuItem MenuItem_manually;
    private javax.swing.JMenu Menu_file;
    private javax.swing.JMenu Menu_help;
    private javax.swing.JPanel Panel;
    private javax.swing.JPanel Panel_edit;
    private javax.swing.JPanel Panel_folders;
    private javax.swing.JPanel Panel_manually;
    private javax.swing.JTextField TextField_author;
    private javax.swing.JTextField TextField_extra;
    private javax.swing.JTextField TextField_extra1;
    private javax.swing.JTextField TextField_pages;
    private javax.swing.JTextField TextField_publisher;
    private javax.swing.JTextField TextField_search;
    private javax.swing.JTextField TextField_title;
    private javax.swing.JTextField TextField_year;
    private javax.swing.JButton addButton;
    private javax.swing.JLabel authorLabel;
    private javax.swing.JTextField authorTextField;
    private javax.swing.JLabel extraInfo1Label;
    private javax.swing.JTextField extraInfo1TextField;
    private javax.swing.JLabel extraInfo2Label;
    private javax.swing.JTextField extraInfo2TextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel pagesLabel;
    private javax.swing.JTextField pagesTextField;
    private javax.swing.JLabel publisherLabel;
    private javax.swing.JTextField publisherTextField;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTextField;
    private javax.swing.JComboBox<String> typeCombobox;
    private javax.swing.JLabel yearLabel;
    private javax.swing.JTextField yearTextField;
    // End of variables declaration//GEN-END:variables
}
