
import com.mysql.jdbc.Connection;
import java.awt.HeadlessException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
// import java.util.ArrayList;
// import java.util.HashMap;
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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Shatha Suliman
 */
public class LibraryEng extends javax.swing.JFrame {

//    private HashMap<Integer, DefaultMutableTreeNode> treeMap;
    private TreeModelMap treeModelMap;

    /**
     * Creates new form Library
     */
    public LibraryEng() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Icons/Logo.jpg")).getImage());
        ShowDocuments();
        JTableHeader header = DocsList.getTableHeader();
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.RIGHT);
        for (int i = 0; i <= 5; i++) {
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

        jTree1.setEditable(true);

        try {
            FileInputStream fileIn = new FileInputStream("treeModelMap.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            treeModelMap = (TreeModelMap) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception i) {
            i.printStackTrace();
        }

        if (treeModelMap == null) {
            treeModelMap = new TreeModelMap();
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Library");
            DefaultMutableTreeNode books = new DefaultMutableTreeNode("Books");
            DefaultMutableTreeNode journals = new DefaultMutableTreeNode("Journels");
            DefaultMutableTreeNode magazines = new DefaultMutableTreeNode("Magazines");
            root.insert(books, 0);
            root.insert(journals, 1);
            root.insert(magazines, 2);
            DefaultTreeModel dtm = new DefaultTreeModel(root);
            treeModelMap.setDefaultTreeModel(dtm);
            treeModelMap.setTreeMap(new HashMap<>());
            jTree1.setModel(dtm);
        } else {
            jTree1.setModel(treeModelMap.getDefaultTreeModel());
        }
    }

    private void saveModel(TreeModelMap e) {
        try {
            FileOutputStream fileOut = new FileOutputStream("treeModelMap.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(e);
            out.close();
            fileOut.close();
           // System.out.printf("Serialized data is saved in model.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

//    private void saveTreeModel() {
//        try {
//            FileOutputStream fileOut = new FileOutputStream("treeMap.ser");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(treeMap);
//            out.close();
//            fileOut.close();
//            System.out.printf("Serialized data is saved in model.ser");
//        } catch (IOException i) {
//            i.printStackTrace();
//        }
//    }
    public void updateTree(String fileName, int documentId) {
        DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
        if (selNode != null) {
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(fileName);
            model.insertNodeInto(newNode, selNode, selNode.getChildCount());
            treeModelMap.getTreeMap().put(documentId, newNode);
            TreeNode[] nodes = model.getPathToRoot(newNode);
            TreePath path = new TreePath(nodes);
            jTree1.scrollPathToVisible(path);
            jTree1.setSelectionPath(path);
        } else {
            JOptionPane.showMessageDialog(null, "حدد مجلد للإضافة");
        }
    }

    public Connection DBConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String host = "jdbc:mysql://localhost:3306/mg-marjieidb";
            String unicode = "?useUnicode=yes&characterEncoding=UTF-8";
            con = (Connection) DriverManager.getConnection(host + unicode, "root", "");
            return con;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return con;
    }

    public ArrayList<ReferenceDocument> getDocumentList() {
        ArrayList<ReferenceDocument> documentList = new ArrayList<ReferenceDocument>();
        Connection connection = DBConnection();

        String query = "SELECT * FROM referencedocument";
        Statement stmt;
        ResultSet rs;

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            ReferenceDocument rf;
            while (rs.next()) {
                int id = rs.getInt("documentID");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                int publishYear = rs.getInt("publishYear");
                String dateAdded = rs.getString("DateAdded");
                rf = new ReferenceDocument(id, title, author, publisher, publishYear, dateAdded);
                documentList.add(rf);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return documentList;
    }

    public void ShowDocuments() {
        ArrayList<ReferenceDocument> List = getDocumentList();
        DefaultTableModel model = (DefaultTableModel) DocsList.getModel();
        Object[] row = new Object[6];
        for (int i = 0; i < List.size(); i++) {
            row[0] = List.get(i).getDateAdded();
            row[1] = List.get(i).getPublishYear();
            row[2] = List.get(i).getPublisher();
            row[3] = List.get(i).getAuthor();
            row[4] = List.get(i).getTitle();
            row[5] = List.get(i).getID();
            model.addRow(row);
        }
    }

    private void Sort() {
        DefaultTableModel model = (DefaultTableModel) DocsList.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        DocsList.setRowSorter(sorter);
    }

    private void Search(String key) {
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
        Button_import = new javax.swing.JButton();
        Button_delete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        DocsList = new javax.swing.JTable();
        TextField_search = new javax.swing.JTextField();
        Label_searchIcon = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Panel_folders = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        Button_newFolder = new javax.swing.JButton();
        Button_deleteFolder = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
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
        MenuBar = new javax.swing.JMenuBar();
        Menu_file = new javax.swing.JMenu();
        MenuItem_add = new javax.swing.JMenuItem();
        MenuItem_manually = new javax.swing.JMenuItem();
        MenuItem_edit = new javax.swing.JMenuItem();
        MenuItem_delete = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        MenuItem_NewFolder = new javax.swing.JMenuItem();
        MenuItem_DeleteFolder = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        MenuItem_exit = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        MenuItem_english = new javax.swing.JMenuItem();
        Menu_help = new javax.swing.JMenu();
        MenuItem_help = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("مكتبة مرجعي");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        Panel.setBackground(new java.awt.Color(245, 245, 245));

        Button_import.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/addIcon.png"))); // NOI18N
        Button_import.setText("Import Reference");
        Button_import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_importActionPerformed(evt);
            }
        });

        Button_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/deleteIcon.png"))); // NOI18N
        Button_delete.setText("Delete Reference");
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
                "تاريخ الإضافة", "سنة النشر", "الناشر", "المؤلف", "العنوان", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
        }

        TextField_search.setColumns(20);
        TextField_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextField_searchKeyReleased(evt);
            }
        });

        Label_searchIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search.png"))); // NOI18N

        jTabbedPane1.setBackground(new java.awt.Color(249, 249, 249));

        Panel_folders.setBackground(new java.awt.Color(249, 249, 249));

        jTree1.setBackground(new java.awt.Color(249, 249, 249));
        jScrollPane2.setViewportView(jTree1);

        Button_newFolder.setText("Create Folder");
        Button_newFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_newFolderActionPerformed(evt);
            }
        });

        Button_deleteFolder.setText("Delete Folder");
        Button_deleteFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_deleteFolderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_foldersLayout = new javax.swing.GroupLayout(Panel_folders);
        Panel_folders.setLayout(Panel_foldersLayout);
        Panel_foldersLayout.setHorizontalGroup(
            Panel_foldersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_foldersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_foldersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(Panel_foldersLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Button_newFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_deleteFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        Panel_foldersLayout.setVerticalGroup(
            Panel_foldersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_foldersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_foldersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Button_deleteFolder)
                    .addComponent(Button_newFolder)))
        );

        jTabbedPane1.addTab("Folders", Panel_folders);

        Panel_manually.setBackground(new java.awt.Color(249, 249, 249));

        typeCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Book", "Journal Article", "Magazine Article", "Web Page", "Conference Proceeding", "miscellaneous" }));
        typeCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboboxActionPerformed(evt);
            }
        });

        titleLabel.setText("Title");

        titleTextField.setColumns(15);
        titleTextField.setToolTipText("");

        publisherLabel.setText("Publisher");

        publisherTextField.setColumns(15);

        pagesLabel.setText("Pages");

        pagesTextField.setColumns(15);

        extraInfo2Label.setText("jLabel1");

        extraInfo2TextField.setColumns(15);

        authorLabel.setText("Author");

        authorTextField.setColumns(15);
        authorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorTextFieldActionPerformed(evt);
            }
        });

        yearLabel.setText("Year");

        yearTextField.setColumns(15);

        extraInfo1Label.setText("Edition");

        extraInfo1TextField.setColumns(15);

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/manuallyIcon.png"))); // NOI18N
        addButton.setText("Enter");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_manuallyLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_manuallyLayout.createSequentialGroup()
                        .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titleLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(authorLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(publisherLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(yearLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pagesLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(extraInfo1Label, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(extraInfo2Label, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(authorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(publisherTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pagesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(extraInfo1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(extraInfo2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_manuallyLayout.createSequentialGroup()
                        .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addButton)
                            .addComponent(typeCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))))
        );
        Panel_manuallyLayout.setVerticalGroup(
            Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_manuallyLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(typeCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titleLabel))
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(authorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(authorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(publisherTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(publisherLabel))
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pagesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pagesLabel))
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(extraInfo1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(extraInfo1Label))
                .addGap(18, 18, 18)
                .addGroup(Panel_manuallyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(extraInfo2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(extraInfo2Label))
                .addGap(38, 38, 38)
                .addComponent(addButton)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Insert Entry Manually", Panel_manually);

        Panel_edit.setBackground(new java.awt.Color(249, 249, 249));

        Label_title.setText("Title");

        Label_author.setText("Author");

        Label_publisher.setText("Publisher");

        Label_year.setText("Year");

        Label_pages.setText("Pages");

        Label_extra.setText("jLabel1");

        Label_extra1.setText("jLabel1");

        ComboBox_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Book", "Journal Article", "Magazine Article", "Web Page", "Conference Proceeding", "miscellaneous" }));
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
        Button_edit.setText("Edit");
        Button_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_editActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_editLayout = new javax.swing.GroupLayout(Panel_edit);
        Panel_edit.setLayout(Panel_editLayout);
        Panel_editLayout.setHorizontalGroup(
            Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_editLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Button_edit)
                    .addComponent(ComboBox_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Panel_editLayout.createSequentialGroup()
                        .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_editLayout.createSequentialGroup()
                                .addComponent(Label_title)
                                .addGap(18, 18, 18)
                                .addComponent(TextField_title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_editLayout.createSequentialGroup()
                                .addComponent(Label_author)
                                .addGap(18, 18, 18)
                                .addComponent(TextField_author, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_editLayout.createSequentialGroup()
                                .addComponent(Label_year)
                                .addGap(18, 18, 18)
                                .addComponent(TextField_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_editLayout.createSequentialGroup()
                                .addComponent(Label_publisher)
                                .addGap(18, 18, 18)
                                .addComponent(TextField_publisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_editLayout.createSequentialGroup()
                                .addComponent(Label_extra)
                                .addGap(18, 18, 18)
                                .addComponent(TextField_extra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_editLayout.createSequentialGroup()
                                .addComponent(Label_pages)
                                .addGap(18, 18, 18)
                                .addComponent(TextField_pages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_editLayout.createSequentialGroup()
                                .addComponent(Label_extra1)
                                .addGap(18, 18, 18)
                                .addComponent(TextField_extra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(69, 69, 69)))
                .addGap(19, 19, 19))
        );
        Panel_editLayout.setVerticalGroup(
            Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_editLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(ComboBox_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextField_title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_title))
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TextField_author, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_author))
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextField_publisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_publisher))
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextField_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_year))
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextField_pages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_pages))
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextField_extra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_extra))
                .addGap(18, 18, 18)
                .addGroup(Panel_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextField_extra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_extra1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(Button_edit)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Edit Reference", Panel_edit);

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addComponent(Button_delete)
                        .addGap(18, 18, 18)
                        .addComponent(Button_import)
                        .addGap(41, 41, 41)
                        .addComponent(TextField_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Label_searchIcon))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Button_import)
                                .addComponent(Button_delete))
                            .addComponent(TextField_search, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Label_searchIcon, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLayout.createSequentialGroup()
                        .addGap(0, 34, Short.MAX_VALUE)
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        Menu_file.setText("File");

        MenuItem_add.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_add.setText("Import Reference");
        MenuItem_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_addActionPerformed(evt);
            }
        });
        Menu_file.add(MenuItem_add);

        MenuItem_manually.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_manually.setText("Insert Entry Manually");
        MenuItem_manually.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_manuallyActionPerformed(evt);
            }
        });
        Menu_file.add(MenuItem_manually);

        MenuItem_edit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_edit.setText("Edit Reference");
        MenuItem_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_editActionPerformed(evt);
            }
        });
        Menu_file.add(MenuItem_edit);

        MenuItem_delete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_delete.setText("Delete Reference");
        MenuItem_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_deleteActionPerformed(evt);
            }
        });
        Menu_file.add(MenuItem_delete);
        Menu_file.add(jSeparator1);

        MenuItem_NewFolder.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_NewFolder.setText("Create Folder");
        MenuItem_NewFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_NewFolderActionPerformed(evt);
            }
        });
        Menu_file.add(MenuItem_NewFolder);

        MenuItem_DeleteFolder.setText("Delete Folder");
        MenuItem_DeleteFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_DeleteFolderActionPerformed(evt);
            }
        });
        Menu_file.add(MenuItem_DeleteFolder);
        Menu_file.add(jSeparator2);

        MenuItem_exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        MenuItem_exit.setText("Exit");
        MenuItem_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_exitActionPerformed(evt);
            }
        });
        Menu_file.add(MenuItem_exit);

        MenuBar.add(Menu_file);

        jMenu1.setText("View");

        MenuItem_english.setText("عربي");
        MenuItem_english.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_englishActionPerformed(evt);
            }
        });
        jMenu1.add(MenuItem_english);

        MenuBar.add(jMenu1);

        Menu_help.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/helpIcon.png"))); // NOI18N

        MenuItem_help.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_help.setText("Help");
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

        getAccessibleContext().setAccessibleName("Marjiei Library");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TextField_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextField_searchKeyReleased
        // TODO add your handling code here:
        String key = TextField_search.getText();
        Search(key);
    }//GEN-LAST:event_TextField_searchKeyReleased

    private void DocsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DocsListMouseClicked
        // Show Edit Section and get the Selected Document. 
        jTabbedPane1.setSelectedComponent(Panel_edit);
        TableModel model = DocsList.getModel();
        // Reset the TextFields 
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
        
        try {
            // Get the Selected Document Type. 
            int i = DocsList.getSelectedRow();
            Connection con = DBConnection();
            Statement stmt = con.createStatement();
           // int ID = (int) model.getValueAt(i, 5);
            ResultSet rs = stmt.executeQuery("SELECT documentType FROM referencedocument WHERE title = '" + model.getValueAt(i, 4) 
            + "' AND author = '" + model.getValueAt(i, 3) +"'" );
            rs.next(); 
            String type = rs.getString("documentType");
            // Set Text Fields According to Document Type. 
            switch (type) {
                case "book":
                    ComboBox_type.setSelectedIndex(0);
                    Label_extra.setVisible(true);
                    TextField_extra.setVisible(true);
                    Label_extra.setText("Edition");
                    if (model.getValueAt(i, 4) != null) {
                        TextField_title.setText(model.getValueAt(i, 4).toString()); }
                    if (model.getValueAt(i, 3) != null) {
                        TextField_author.setText(model.getValueAt(i, 3).toString()); }
                    if (model.getValueAt(i, 2) != null) {
                        TextField_publisher.setText(model.getValueAt(i, 2).toString()); }
                    TextField_year.setText(model.getValueAt(i, 1).toString());
                    // Get the Rest of Document Information From Database. 
                    int bookID = (int) model.getValueAt(i, 5);
                    rs = stmt.executeQuery("SELECT pages FROM referencedocument WHERE documentID = " + bookID);
                    rs.next();
                    TextField_pages.setText(rs.getString("pages")); 
                    rs = stmt.executeQuery("SELECT * FROM book WHERE documentID = " + bookID);
                    rs.next();
                    TextField_extra.setText(rs.getString("edition"));
                    break;

                case "journalarticle":
                    ComboBox_type.setSelectedIndex(1);

                    Label_extra.setVisible(true);
                    TextField_extra.setVisible(true);
                    Label_extra1.setVisible(true);
                    TextField_extra1.setVisible(true);
                    Label_extra.setText("Journal");
                    Label_extra1.setText("Volume");

                    if (model.getValueAt(i, 4) != null) {
                        TextField_title.setText(model.getValueAt(i, 4).toString());
                    }
                    if (model.getValueAt(i, 3) != null) {
                        TextField_author.setText(model.getValueAt(i, 3).toString());
                    }
                    if (model.getValueAt(i, 2) != null) {
                        TextField_publisher.setText(model.getValueAt(i, 2).toString());
                    }
                    TextField_year.setText(model.getValueAt(i, 1).toString());

                    int jarticleID = (int) model.getValueAt(i, 5);
                    rs = stmt.executeQuery("SELECT pages FROM referencedocument WHERE documentID = " + jarticleID);
                    rs.next();
                    TextField_pages.setText(rs.getString("pages"));

                    rs = stmt.executeQuery("SELECT * FROM journalarticle WHERE documentID = " + jarticleID);
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
                    Label_extra.setText("Magazine");
                    Label_extra1.setText("Month");

                    if (model.getValueAt(i, 4) != null) {
                        TextField_title.setText(model.getValueAt(i, 4).toString());
                    }
                    if (model.getValueAt(i, 3) != null) {
                        TextField_author.setText(model.getValueAt(i, 3).toString());
                    }
                    if (model.getValueAt(i, 2) != null) {
                        TextField_publisher.setText(model.getValueAt(i, 2).toString());
                    }
                    TextField_year.setText(model.getValueAt(i, 1).toString());

                    int marticleID = (int) model.getValueAt(i, 5);
                    rs = stmt.executeQuery("SELECT pages FROM referencedocument WHERE documentID = " + marticleID);
                    rs.next();
                    TextField_pages.setText(rs.getString("pages"));

                    rs = stmt.executeQuery("SELECT * FROM magazinearticle WHERE documentID = " + marticleID);
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
                    Label_extra.setText("URL");
                    Label_extra1.setText("Access Date");

                    if (model.getValueAt(i, 4) != null) {
                        TextField_title.setText(model.getValueAt(i, 4).toString());
                    }
                    if (model.getValueAt(i, 3) != null) {
                        TextField_author.setText(model.getValueAt(i, 3).toString());
                    }
                    if (model.getValueAt(i, 2) != null) {
                        TextField_publisher.setText(model.getValueAt(i, 2).toString());
                    }
                    TextField_year.setText(model.getValueAt(i, 1).toString());

                    int webID = (int) model.getValueAt(i, 5);
                    rs = stmt.executeQuery("SELECT pages FROM referencedocument WHERE documentID = " + webID);
                    rs.next();
                    TextField_pages.setText(rs.getString("pages"));

                    rs = stmt.executeQuery("SELECT * FROM webpage WHERE documentID = " + webID);
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
                    Label_extra.setText("Conference");
                    Label_extra1.setText("City");

                    if (model.getValueAt(i, 4) != null) {
                        TextField_title.setText(model.getValueAt(i, 4).toString());
                    }
                    if (model.getValueAt(i, 3) != null) {
                        TextField_author.setText(model.getValueAt(i, 3).toString());
                    }
                    if (model.getValueAt(i, 2) != null) {
                        TextField_publisher.setText(model.getValueAt(i, 2).toString());
                    }
                    TextField_year.setText(model.getValueAt(i, 1).toString());

                    int cpID = (int) model.getValueAt(i, 5);
                    rs = stmt.executeQuery("SELECT pages FROM referencedocument WHERE documentID = " + cpID);
                    rs.next();
                    TextField_pages.setText(rs.getString("pages"));

                    rs = stmt.executeQuery("SELECT * FROM conferenceproceeding WHERE documentID = " + cpID);
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

                    if (model.getValueAt(i, 4) != null) {
                        TextField_title.setText(model.getValueAt(i, 4).toString());
                    }
                    if (model.getValueAt(i, 3) != null) {
                        TextField_author.setText(model.getValueAt(i, 3).toString());
                    }
                    if (model.getValueAt(i, 2) != null) {
                        TextField_publisher.setText(model.getValueAt(i, 2).toString());
                    }
                    TextField_year.setText(model.getValueAt(i, 1).toString());

                    int otherID = (int) model.getValueAt(i, 5);
                    rs = stmt.executeQuery("SELECT pages FROM referencedocument WHERE documentID = " + otherID);
                    rs.next();
                    TextField_pages.setText(rs.getString("pages"));
                    break;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
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
    public void RefereshTable() {
        DefaultTableModel defModel = (DefaultTableModel) DocsList.getModel();
        defModel.setRowCount(0);
        ShowDocuments();
    }
    private void Button_importActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_importActionPerformed
        DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        if (selNode == null) {
            JOptionPane.showMessageDialog(null, "Select Folder to Import");  }
        if (selNode == null) {
            return; }
        try {
            // Declaring Components & local Variables
            JFileChooser chooser = new JFileChooser();
            String fileName = "";
            // Filter, only PDF document is shown 
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Pdf file(.pdf)", "pdf");
            chooser.setFileFilter(filter);
            // Show the Selection Window 
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) // if the selected file is valid
            {
                fileName = chooser.getSelectedFile().getPath();
            }
            // Pass the Document to PDFManager.java
            PDFManager pdfManager = new PDFManager();
            pdfManager.setFilePath(fileName);
            int documentId = pdfManager.ToText(selNode.getUserObject().toString());
            // Update the Library 
            if (documentId != -1)
            {
            RefereshTable();
            updateTree(chooser.getSelectedFile().getName(), documentId);
            } else 
            {
                JOptionPane.showMessageDialog(null, "Invaliv Document");
            }
        } catch (HeadlessException | IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_Button_importActionPerformed

    private void Button_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteActionPerformed
        try {
            int i = DocsList.getSelectedRow();
            if ( i != -1) // If there is Selected Document
            { // Show Confirmation Message
            int confirmed = JOptionPane.showConfirmDialog(null, "Reference will be Deleted", "Comfirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            DefaultTableModel model = (DefaultTableModel) DocsList.getModel();
            Connection con = DBConnection();
            Statement stmt = con.createStatement();
            int row = DocsList.getSelectedRow();
            int ID = (int) model.getValueAt(i, 5);
            // Check the User Option
            if (confirmed == 0) {
                stmt.execute("DELETE FROM referencedocument WHERE documentID =" + ID + "");
                int modelRow = DocsList.convertRowIndexToModel(row);
                model.removeRow(modelRow);
                // Reset TextFields. 
                ComboBox_type.setSelectedIndex(5);
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
                if (treeModelMap.getTreeMap().get(ID) != null) {
                    ((DefaultTreeModel) jTree1.getModel()).removeNodeFromParent(treeModelMap.getTreeMap().get(ID)); }
            }
            } else {
                JOptionPane.showMessageDialog(null, "Select Reference to Delete");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }//GEN-LAST:event_Button_deleteActionPerformed

    private void MenuItem_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_addActionPerformed
       DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        if (selNode == null) {
            JOptionPane.showMessageDialog(null, "Select Folder to Import");  }
        if (selNode == null) {
            return; }
        try {
            // Declaring Components & local Variables
            JFileChooser chooser = new JFileChooser();
            String fileName = "";
            // Filter, only PDF document is shown 
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Pdf file(.pdf)", "pdf");
            chooser.setFileFilter(filter);
            // Show the Selection Window 
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) // if the selected file is valid
            {
                fileName = chooser.getSelectedFile().getPath();
            }
            // Pass the Document to PDFManager.java
            PDFManager pdfManager = new PDFManager();
            pdfManager.setFilePath(fileName);
            int documentId = pdfManager.ToText(selNode.getUserObject().toString());
            // Update the Library 
            if (documentId != -1)
            {
            RefereshTable();
            updateTree(chooser.getSelectedFile().getName(), documentId);
            } else 
            {
                JOptionPane.showMessageDialog(null, "Invalid Document");
            }
        } catch (HeadlessException | IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_MenuItem_addActionPerformed

    private void MenuItem_manuallyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_manuallyActionPerformed
       // Declaring local variables
        DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        if (selNode == null) {
            JOptionPane.showMessageDialog(null, "Select Folder to Insert"); }
        if (selNode == null) {
            return;  }
        Connection con = DBConnection();
        Statement stmt;
        ResultSet rs;
        int index = typeCombobox.getSelectedIndex(); // Get the selected Document Type 
        switch (index) {
            case 0: // Book
                try {   // Code to insert book information here.
             stmt = con.createStatement();
             stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear, folder)"
                      + " VALUES ('book', '" + titleTextField.getText() + "', '" + authorTextField.getText()
                      + "', '" + pagesTextField.getText() + "', '" + publisherTextField.getText()
                      + "', '" + yearTextField.getText() + "', '" + selNode.getUserObject().toString() + "')");
                    rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + titleTextField.getText()
                            + "' AND author = '" + authorTextField.getText() + "'");
                    rs.next();
                    int id = rs.getInt("documentID");
                    stmt.executeUpdate("INSERT INTO book (documentID, edition)" + " VALUES ( '" + id + "', '"
                            + extraInfo1TextField.getText() + "')"); // Book Edition
                    updateTree(titleTextField.getText(), id);
                    RefereshTable(); 
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());  }
                break;

            /*   String type = "book";
             String title = titleTextField.getText();
             String author = authorTextField.getText();
             int pages = Integer.parseInt(pagesTextField.getText());
             String publisher = publisherTextField.getText();
             int year = Integer.parseInt(yearTextField.getText());
             String extra = extraInfo1TextField.getText();

             lib.ExecuteMyQuery(type, title, author, publisher, year, pages, extra, null); */
            case 1:
                // Code to insert journal article information here.
                try {
                    // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                    //    LocalDate localDate = LocalDate.now();
                    stmt = con.createStatement();
             stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear, folder)"
                      + " VALUES ('book', '" + titleTextField.getText() + "', '" + authorTextField.getText()
                      + "', '" + pagesTextField.getText() + "', '" + publisherTextField.getText()
                      + "', '" + yearTextField.getText() + "', '" + selNode.getUserObject().toString() + "')");
                    rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + titleTextField.getText() 
                            + "' AND author = '" + authorTextField.getText() + "'");
                    rs.next();
                    int id = rs.getInt("documentID");
                    stmt.executeUpdate("INSERT INTO journalarticle (documentID, journalName, volume)" 
                            + " VALUES ( '" + id + "', '" + extraInfo1TextField.getText() + "', '" + extraInfo2TextField.getText() + "')");
                    updateTree(titleTextField.getText(), id);
                    RefereshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());
                }
                break;

            case 2:
                // Code to insert magazine article information here.
                try {
                    //  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                    //     LocalDate localDate = LocalDate.now();
                    stmt = con.createStatement();
             stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear, folder)"
                      + " VALUES ('book', '" + titleTextField.getText() + "', '" + authorTextField.getText()
                      + "', '" + pagesTextField.getText() + "', '" + publisherTextField.getText()
                      + "', '" + yearTextField.getText() + "', '" + selNode.getUserObject().toString() + "')");
                    rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + titleTextField.getText() 
                            + "' AND author = '" + authorTextField.getText() + "'");
                    rs.next();
                    int id = rs.getInt("documentID");
                    stmt.executeUpdate("INSERT INTO magazinearticle (documentID, magazineName, month) " 
                            + " VALUES ( '" + id + "', '" + extraInfo1TextField.getText() + "', '" + extraInfo2TextField.getText() + "')");
                    updateTree(titleTextField.getText(), id);
                    RefereshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());
                }

                break;

            case 3:
                // Code to insert web page information here.
                try {
                    //   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                    //     LocalDate localDate = LocalDate.now();
                    stmt = con.createStatement();
             stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear, folder)"
                      + " VALUES ('book', '" + titleTextField.getText() + "', '" + authorTextField.getText()
                      + "', '" + pagesTextField.getText() + "', '" + publisherTextField.getText()
                      + "', '" + yearTextField.getText() + "', '" + selNode.getUserObject().toString() + "')");
                    rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + titleTextField.getText() 
                            + "' AND author = '" + authorTextField.getText() + "'");
                    rs.next();
                    int id = rs.getInt("documentID");
                    stmt.executeUpdate("INSERT INTO webpage (documentID, url, AccessDate) " 
                            + " VALUES ( '" + id + "', '" + extraInfo1TextField.getText() + "', '" + extraInfo2TextField.getText() + "')");
                    updateTree(titleTextField.getText(), id);
                    RefereshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());
                }

                break;

            case 4:
                // Code to insert conference proceeding information here.
                try {
                    //   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                    //    LocalDate localDate = LocalDate.now();
                    stmt = con.createStatement();
             stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear, folder)"
                      + " VALUES ('book', '" + titleTextField.getText() + "', '" + authorTextField.getText()
                      + "', '" + pagesTextField.getText() + "', '" + publisherTextField.getText()
                      + "', '" + yearTextField.getText() + "', '" + selNode.getUserObject().toString() + "')");
                    rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + titleTextField.getText() 
                            + "' AND author = '" + authorTextField.getText() + "'");
                    rs.next();
                    int id = rs.getInt("documentID");
                    stmt.executeUpdate("INSERT INTO conferenceproceeding (documentID, conferenceName, place) " 
                            + " VALUES ( '" + id + "', '" + extraInfo1TextField.getText() + "', '" + extraInfo2TextField.getText() + "')");
                    updateTree(titleTextField.getText(), id);
                    RefereshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());
                }

                break;

            case 5:
                // Code to insert other document information here.
                try {
                    //    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                    //   LocalDate localDate = LocalDate.now();
                    stmt = con.createStatement();
             stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear, folder)"
                      + " VALUES ('book', '" + titleTextField.getText() + "', '" + authorTextField.getText()
                      + "', '" + pagesTextField.getText() + "', '" + publisherTextField.getText()
                      + "', '" + yearTextField.getText() + "', '" + selNode.getUserObject().toString() + "')");
                    rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + titleTextField.getText() 
                            + "' AND author = '" + authorTextField.getText() + "'");
                    rs.next();
                    int id = rs.getInt("documentID");
                    updateTree(titleTextField.getText(), id);
                    RefereshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());
                }

                break;
        } // End of switch

        typeCombobox.setSelectedIndex(0);
        titleTextField.setText(" ");
        publisherTextField.setText(" ");
        authorTextField.setText(" ");
        yearTextField.setText(" ");
        pagesTextField.setText(" ");
        extraInfo1TextField.setText(" ");
        extraInfo2TextField.setText(" ");
        extraInfo1Label.setVisible(true);
        extraInfo1Label.setText("الطبعة");
        extraInfo1TextField.setVisible(true);
        extraInfo2Label.setVisible(false);
        extraInfo2TextField.setVisible(false);
    }//GEN-LAST:event_MenuItem_manuallyActionPerformed

    private void MenuItem_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_deleteActionPerformed
       try {
            int i = DocsList.getSelectedRow();
            if ( i != -1) // If there is Selected Document
            { // Show Confirmation Message
            int confirmed = JOptionPane.showConfirmDialog(null, "Reference will be Deleted", "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            DefaultTableModel model = (DefaultTableModel) DocsList.getModel();
            Connection con = DBConnection();
            Statement stmt = con.createStatement();
            int row = DocsList.getSelectedRow();
            int ID = (int) model.getValueAt(i, 5);
            // Check the User Option
            if (confirmed == 0) {
                stmt.execute("DELETE FROM referencedocument WHERE documentID =" + ID + "");
                int modelRow = DocsList.convertRowIndexToModel(row);
                model.removeRow(modelRow);
                // Reset TextFields. 
                ComboBox_type.setSelectedIndex(5);
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
                if (treeModelMap.getTreeMap().get(ID) != null) {
                    ((DefaultTreeModel) jTree1.getModel()).removeNodeFromParent(treeModelMap.getTreeMap().get(ID)); }
            }
            } else {
                JOptionPane.showMessageDialog(null, "Select Reference to Delete");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_MenuItem_deleteActionPerformed

    private void MenuItem_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_exitActionPerformed
        // TODO add your handling code here:
        saveModel(treeModelMap);
        
        Platform.exit();
        System.exit(0);
    }//GEN-LAST:event_MenuItem_exitActionPerformed

    private void MenuItem_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_editActionPerformed
       // TODO add your handling code here:
        //
        try {
            int i = DocsList.getSelectedRow();
            TableModel model = DocsList.getModel();
            Connection con = DBConnection();
            Statement stmt = con.createStatement();
           // int ID = (int) model.getValueAt(i, 5);
            ResultSet rs = stmt.executeQuery("SELECT documentType FROM referencedocument WHERE title = '" + model.getValueAt(i, 4) 
            + "' AND author = '" + model.getValueAt(i, 3) +"'" );
            rs.next();
            String oldType = rs.getString("documentType");
            String newType = null;
            int newTypeInt = ComboBox_type.getSelectedIndex();
            switch (newTypeInt) {
                case 0:
                    newType = "book";
                    break;

                case 1:
                    newType = "journalarticle";
                    break;

                case 2:
                    newType = "magazinearticle";
                    break;

                case 3:
                    newType = "webpage";
                    break;

                case 4:
                    newType = "conferenceproceeding";
                    break;

                case 5:
                    newType = "other";
                    break;
            } // End newTypeInt switch

            String value1 = TextField_title.getText();
            String value2 = TextField_author.getText();
            String value3 = TextField_publisher.getText();
            String value4 = TextField_year.getText();
            String value5 = TextField_pages.getText();
            String value6 = TextField_extra.getText();
            String value7 = TextField_extra1.getText();

            rs = stmt.executeQuery("SELECT documentType FROM referencedocument WHERE title = '" + model.getValueAt(i, 4) 
            + "' AND author = '" + model.getValueAt(i, 3) +"'" );
            int ID = rs.getInt("documentID");
            String query = "update referencedocument set title='" + value1 + "' , author='" + value2 + "', publisher='" 
                    + value3 + "', publishYear='" + value4 + "', pages='" + value5 + "' where documentID='" + ID + "' ";
            PreparedStatement pst = con.prepareStatement(query);
            pst.execute();
            RefereshTable();
            DefaultMutableTreeNode dmtn = treeModelMap.getTreeMap().get(ID);
            dmtn.setUserObject(value1);
            ((DefaultTreeModel) treeModelMap.getDefaultTreeModel()).reload();
            TreeNode[] nodes = treeModelMap.getDefaultTreeModel().getPathToRoot(dmtn);
            TreePath path = new TreePath(nodes);
            jTree1.scrollPathToVisible(path);
            jTree1.setSelectionPath(path);

            switch (oldType) {
                case "book":
                    stmt.execute("DELETE FROM book WHERE documentID =" + ID + "");
                    break;

                case "journalarticle":
                    stmt.execute("DELETE FROM journalarticle WHERE documentID =" + ID + "");
                    break;

                case "magazinearticle":
                    stmt.execute("DELETE FROM magazinearticle WHERE documentID =" + ID + "");
                    break;

                case "webpage":
                    stmt.execute("DELETE FROM webpage WHERE documentID =" + ID + "");
                    break;

                case "conferenceproceeding":
                    stmt.execute("DELETE FROM conferenceproceeding WHERE documentID =" + ID + "");
                    break;

            }
            switch (newType) {
                case "book":
                    stmt.executeUpdate("update referencedocument set documentType='book' where documentID='" + ID + "' ");
                    if (stmt.executeUpdate("update book set edition='" + value6 + "' where documentID='" + ID + "' ") != 1);
                     {
                        stmt.executeUpdate("INSERT INTO book (documentID, edition)" + " VALUES ( '" + ID + "', '" + value6 + "')");
                    }

                    break;

                case "journalarticle":
                    stmt.executeUpdate("update referencedocument set documentType='journalarticle' where documentID='" + ID + "' ");
                    if (stmt.executeUpdate("update journalarticle set journalName='" + value6 + "' , volume='" + value7 + "' where documentID='" + ID + "' ") != 1);
                     {
                        stmt.executeUpdate("INSERT INTO journalarticle (documentID, journalName, volume)" + " VALUES ( '" + ID + "', '" + value6 + "', '" + value7 + "')");
                    }

                    break;

                case "magazinearticle":
                    stmt.executeUpdate("update referencedocument set documentType='magazinearticle' where documentID='" + ID + "' ");
                    if (stmt.executeUpdate("update magazinearticle set magazineName='" + value6 + "' , month='" + value7 + "' where documentID='" + ID + "' ") != 1);
                     {
                        stmt.executeUpdate("INSERT INTO magazinearticle (documentID, magazineName, month)" + " VALUES ( '" + ID + "', '" + value6 + "', '" + value7 + "')");
                    }
                    break;

                case "webpage":
                    stmt.executeUpdate("update referencedocument set documentType='webpage' where documentID='" + ID + "' ");
                    if (stmt.executeUpdate("update webpage set url='" + value6 + "' , AccessDate='" + value7 + "' where documentID='" + ID + "' ") != 1);
                     {
                        stmt.executeUpdate("INSERT INTO webpage (documentID, url, AccessDate)" + " VALUES ( '" + ID + "', '" + value6 + "', '" + value7 + "')");
                    }

                    break;

                case "conferenceproceeding":
                    stmt.executeUpdate("update referencedocument set documentType='conferenceproceeding' where documentID='" + ID + "' ");
                    if (stmt.executeUpdate("update conferenceproceeding set conferenceName='" + value6 + "' , place='" + value7 + "' where documentID='" + ID + "' ") != 1);
                     {
                        stmt.executeUpdate("INSERT INTO conferenceproceeding (documentID, conferenceName, place)" + " VALUES ( '" + ID + "', '" + value6 + "', '" + value7 + "')");
                    }

                    break;

                case "other":
                    stmt.executeUpdate("update referencedocument set documentType='other' where documentID='" + ID + "' ");
                    break;

            } // End newType switch

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

            JOptionPane.showMessageDialog(null, "Updated Successfully");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "بيانات غير صالحة\n" + ex.getMessage());
        }
    }//GEN-LAST:event_MenuItem_editActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        saveModel(treeModelMap);
//        saveTreeModel();
    }//GEN-LAST:event_formWindowClosing

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // Declaring local variables
        DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        if (selNode == null) {
            JOptionPane.showMessageDialog(null, "Select Folder to Insert"); }
        if (selNode == null) {
            return;  }
        Connection con = DBConnection();
        Statement stmt;
        ResultSet rs;
        int index = typeCombobox.getSelectedIndex(); // Get the selected Document Type 
        switch (index) {
            case 0: // Book
                try {   // Code to insert book information here.
             stmt = con.createStatement();
             stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear, folder)"
                      + " VALUES ('book', '" + titleTextField.getText() + "', '" + authorTextField.getText()
                      + "', '" + pagesTextField.getText() + "', '" + publisherTextField.getText()
                      + "', '" + yearTextField.getText() + "', '" + selNode.getUserObject().toString() + "')");
                    rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + titleTextField.getText()
                            + "' AND author = '" + authorTextField.getText() + "'");
                    rs.next();
                    int id = rs.getInt("documentID");
                    stmt.executeUpdate("INSERT INTO book (documentID, edition)" + " VALUES ( '" + id + "', '"
                            + extraInfo1TextField.getText() + "')"); // Book Edition
                    updateTree(titleTextField.getText(), id);
                    RefereshTable(); 
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());  }
                break;

            /*   String type = "book";
             String title = titleTextField.getText();
             String author = authorTextField.getText();
             int pages = Integer.parseInt(pagesTextField.getText());
             String publisher = publisherTextField.getText();
             int year = Integer.parseInt(yearTextField.getText());
             String extra = extraInfo1TextField.getText();

             lib.ExecuteMyQuery(type, title, author, publisher, year, pages, extra, null); */
            case 1:
                // Code to insert journal article information here.
                try {
                    // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                    //    LocalDate localDate = LocalDate.now();
                    stmt = con.createStatement();
             stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear, folder)"
                      + " VALUES ('journalarticle', '" + titleTextField.getText() + "', '" + authorTextField.getText()
                      + "', '" + pagesTextField.getText() + "', '" + publisherTextField.getText()
                      + "', '" + yearTextField.getText() + "', '" + selNode.getUserObject().toString() + "')");
                    rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + titleTextField.getText() 
                            + "' AND author = '" + authorTextField.getText() + "'");
                    rs.next();
                    int id = rs.getInt("documentID");
                    stmt.executeUpdate("INSERT INTO journalarticle (documentID, journalName, volume)" 
                            + " VALUES ( '" + id + "', '" + extraInfo1TextField.getText() + "', '" + extraInfo2TextField.getText() + "')");
                    updateTree(titleTextField.getText(), id);
                    RefereshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());
                }
                break;

            case 2:
                // Code to insert magazine article information here.
                try {
                    //  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                    //     LocalDate localDate = LocalDate.now();
                    stmt = con.createStatement();
             stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear, folder)"
                      + " VALUES ('magazinearticle', '" + titleTextField.getText() + "', '" + authorTextField.getText()
                      + "', '" + pagesTextField.getText() + "', '" + publisherTextField.getText()
                      + "', '" + yearTextField.getText() + "', '" + selNode.getUserObject().toString() + "')");
                    rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + titleTextField.getText() 
                            + "' AND author = '" + authorTextField.getText() + "'");
                    rs.next();
                    int id = rs.getInt("documentID");
                    stmt.executeUpdate("INSERT INTO magazinearticle (documentID, magazineName, month) " 
                            + " VALUES ( '" + id + "', '" + extraInfo1TextField.getText() + "', '" + extraInfo2TextField.getText() + "')");
                    updateTree(titleTextField.getText(), id);
                    RefereshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());
                }

                break;

            case 3:
                // Code to insert web page information here.
                try {
                    //   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                    //     LocalDate localDate = LocalDate.now();
                    stmt = con.createStatement();
             stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear, folder)"
                      + " VALUES ('webpage', '" + titleTextField.getText() + "', '" + authorTextField.getText()
                      + "', '" + pagesTextField.getText() + "', '" + publisherTextField.getText()
                      + "', '" + yearTextField.getText() + "', '" + selNode.getUserObject().toString() + "')");
                    rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + titleTextField.getText() 
                            + "' AND author = '" + authorTextField.getText() + "'");
                    rs.next();
                    int id = rs.getInt("documentID");
                    stmt.executeUpdate("INSERT INTO webpage (documentID, url, AccessDate) " 
                            + " VALUES ( '" + id + "', '" + extraInfo1TextField.getText() + "', '" + extraInfo2TextField.getText() + "')");
                    updateTree(titleTextField.getText(), id);
                    RefereshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());
                }

                break;

            case 4:
                // Code to insert conference proceeding information here.
                try {
                    //   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                    //    LocalDate localDate = LocalDate.now();
                    stmt = con.createStatement();
             stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear, folder)"
                      + " VALUES ('conferenceproceeding', '" + titleTextField.getText() + "', '" + authorTextField.getText()
                      + "', '" + pagesTextField.getText() + "', '" + publisherTextField.getText()
                      + "', '" + yearTextField.getText() + "', '" + selNode.getUserObject().toString() + "')");
                    rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + titleTextField.getText() 
                            + "' AND author = '" + authorTextField.getText() + "'");
                    rs.next();
                    int id = rs.getInt("documentID");
                    stmt.executeUpdate("INSERT INTO conferenceproceeding (documentID, conferenceName, place) " 
                            + " VALUES ( '" + id + "', '" + extraInfo1TextField.getText() + "', '" + extraInfo2TextField.getText() + "')");
                    updateTree(titleTextField.getText(), id);
                    RefereshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());
                }

                break;

            case 5:
                // Code to insert miscellaneous document information here.
                try {
                    //    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                    //   LocalDate localDate = LocalDate.now();
                    stmt = con.createStatement();
             stmt.executeUpdate("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, publisher, publishYear, folder)"
                      + " VALUES ('miscellaneous', '" + titleTextField.getText() + "', '" + authorTextField.getText()
                      + "', '" + pagesTextField.getText() + "', '" + publisherTextField.getText()
                      + "', '" + yearTextField.getText() + "', '" + selNode.getUserObject().toString() + "')");
                    rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + titleTextField.getText() 
                            + "' AND author = '" + authorTextField.getText() + "'");
                    rs.next();
                    int id = rs.getInt("documentID");
                    updateTree(titleTextField.getText(), id);
                    RefereshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());
                }

                break;
        } // End of switch

        typeCombobox.setSelectedIndex(0);
        titleTextField.setText(" ");
        publisherTextField.setText(" ");
        authorTextField.setText(" ");
        yearTextField.setText(" ");
        pagesTextField.setText(" ");
        extraInfo1TextField.setText(" ");
        extraInfo2TextField.setText(" ");
        extraInfo1Label.setVisible(true);
        extraInfo1Label.setText("Edition");
        extraInfo1TextField.setVisible(true);
        extraInfo2Label.setVisible(false);
        extraInfo2TextField.setVisible(false);
    }//GEN-LAST:event_addButtonActionPerformed

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addButtonMouseClicked

    private void authorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authorTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_authorTextFieldActionPerformed

    private void typeComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboboxActionPerformed
        // TODO add your handling code here:

        // 'Type' Combobox Selection
        int index = typeCombobox.getSelectedIndex();
        switch (index) {
            case 0:
                // Code for show Book form
                extraInfo1Label.setText("Edition");
                extraInfo1Label.setVisible(true);
                extraInfo1TextField.setVisible(true);
                extraInfo2Label.setVisible(false);
                extraInfo2TextField.setVisible(false);

                break;
            case 1:
                // Code for show Journal Article form
                extraInfo1Label.setText("Journal");
                extraInfo1Label.setVisible(true);
                extraInfo1TextField.setVisible(true);
                extraInfo2Label.setText("Volume");
                extraInfo2Label.setVisible(true);
                extraInfo2TextField.setVisible(true);

                break;
            case 2:
                // Code for show Magazine Article form
                extraInfo1Label.setText("Magazine");
                extraInfo1Label.setVisible(true);
                extraInfo1TextField.setVisible(true);
                extraInfo2Label.setText("Month");
                extraInfo2Label.setVisible(true);
                extraInfo2TextField.setVisible(true);

                break;
            case 3:
                // Code for show Web Page form
                extraInfo1Label.setText("URL");
                extraInfo1Label.setVisible(true);
                extraInfo1TextField.setVisible(true);
                extraInfo2Label.setText("Access Date");
                extraInfo2Label.setVisible(true);
                extraInfo2TextField.setVisible(true);

                break;
            case 4:
                // Code for show Conference Proceeding form
                extraInfo1Label.setText("Conference");
                extraInfo1Label.setVisible(true);
                extraInfo1TextField.setVisible(true);
                extraInfo2Label.setText("City");
                extraInfo2Label.setVisible(true);
                extraInfo2TextField.setVisible(true);

                break;
            case 5:
                // Code for show miscellaneous Document form
                extraInfo1Label.setVisible(false);
                extraInfo1TextField.setVisible(false);
                extraInfo2Label.setVisible(false);
                extraInfo2TextField.setVisible(false);

        } // End Switch Statment.
    }//GEN-LAST:event_typeComboboxActionPerformed

    private void Button_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_editActionPerformed
        // TODO add your handling code here:
        //
        try {
            int i = DocsList.getSelectedRow();
            TableModel model = DocsList.getModel();
            Connection con = DBConnection();
            Statement stmt = con.createStatement();
           // int ID = (int) model.getValueAt(i, 5);
            ResultSet rs = stmt.executeQuery("SELECT documentType FROM referencedocument WHERE title = '" + model.getValueAt(i, 4) 
            + "' AND author = '" + model.getValueAt(i, 3) +"'" );
            rs.next();
            String oldType = rs.getString("documentType");
            String newType = null;
            int newTypeInt = ComboBox_type.getSelectedIndex();
            switch (newTypeInt) {
                case 0:
                    newType = "book";
                    break;

                case 1:
                    newType = "journalarticle";
                    break;

                case 2:
                    newType = "magazinearticle";
                    break;

                case 3:
                    newType = "webpage";
                    break;

                case 4:
                    newType = "conferenceproceeding";
                    break;

                case 5:
                    newType = "miscellaneous";
                    break;
            } // End newTypeInt switch

            String value1 = TextField_title.getText();
            String value2 = TextField_author.getText();
            String value3 = TextField_publisher.getText();
            String value4 = TextField_year.getText();
            String value5 = TextField_pages.getText();
            String value6 = TextField_extra.getText();
            String value7 = TextField_extra1.getText();

            rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + model.getValueAt(i, 4) 
            + "' AND author = '" + model.getValueAt(i, 3) +"'" );
            rs.next();
            int ID = rs.getInt("documentID");
            String query = "update referencedocument set title='" + value1 + "' , author='" + value2 + "', publisher='" 
                    + value3 + "', publishYear='" + value4 + "', pages='" + value5 + "' where documentID='" + ID + "' ";
            PreparedStatement pst = con.prepareStatement(query);
            pst.execute();
            RefereshTable();
            DefaultMutableTreeNode dmtn = treeModelMap.getTreeMap().get(ID);
            dmtn.setUserObject(value1);
            ((DefaultTreeModel) treeModelMap.getDefaultTreeModel()).reload();
            TreeNode[] nodes = treeModelMap.getDefaultTreeModel().getPathToRoot(dmtn);
            TreePath path = new TreePath(nodes);
            jTree1.scrollPathToVisible(path);
            jTree1.setSelectionPath(path);

            switch (oldType) {
                case "book":
                    stmt.execute("DELETE FROM book WHERE documentID =" + ID + "");
                    break;

                case "journalarticle":
                    stmt.execute("DELETE FROM journalarticle WHERE documentID =" + ID + "");
                    break;

                case "magazinearticle":
                    stmt.execute("DELETE FROM magazinearticle WHERE documentID =" + ID + "");
                    break;

                case "webpage":
                    stmt.execute("DELETE FROM webpage WHERE documentID =" + ID + "");
                    break;

                case "conferenceproceeding":
                    stmt.execute("DELETE FROM conferenceproceeding WHERE documentID =" + ID + "");
                    break;

            }
            switch (newType) {
                case "book":
                    stmt.executeUpdate("update referencedocument set documentType='book' where documentID='" + ID + "' ");
                    if (stmt.executeUpdate("update book set edition='" + value6 + "' where documentID='" + ID + "' ") != 1);
                     {
                        stmt.executeUpdate("INSERT INTO book (documentID, edition)" + " VALUES ( '" + ID + "', '" + value6 + "')");
                    }

                    break;

                case "journalarticle":
                    stmt.executeUpdate("update referencedocument set documentType='journalarticle' where documentID='" + ID + "' ");
                    if (stmt.executeUpdate("update journalarticle set journalName='" + value6 + "' , volume='" + value7 + "' where documentID='" + ID + "' ") != 1);
                     {
                        stmt.executeUpdate("INSERT INTO journalarticle (documentID, journalName, volume)" + " VALUES ( '" + ID + "', '" + value6 + "', '" + value7 + "')");
                    }

                    break;

                case "magazinearticle":
                    stmt.executeUpdate("update referencedocument set documentType='magazinearticle' where documentID='" + ID + "' ");
                    if (stmt.executeUpdate("update magazinearticle set magazineName='" + value6 + "' , month='" + value7 + "' where documentID='" + ID + "' ") != 1);
                     {
                        stmt.executeUpdate("INSERT INTO magazinearticle (documentID, magazineName, month)" + " VALUES ( '" + ID + "', '" + value6 + "', '" + value7 + "')");
                    }
                    break;

                case "webpage":
                    stmt.executeUpdate("update referencedocument set documentType='webpage' where documentID='" + ID + "' ");
                    if (stmt.executeUpdate("update webpage set url='" + value6 + "' , AccessDate='" + value7 + "' where documentID='" + ID + "' ") != 1);
                     {
                        stmt.executeUpdate("INSERT INTO webpage (documentID, url, AccessDate)" + " VALUES ( '" + ID + "', '" + value6 + "', '" + value7 + "')");
                    }

                    break;

                case "conferenceproceeding":
                    stmt.executeUpdate("update referencedocument set documentType='conferenceproceeding' where documentID='" + ID + "' ");
                    if (stmt.executeUpdate("update conferenceproceeding set conferenceName='" + value6 + "' , place='" + value7 + "' where documentID='" + ID + "' ") != 1);
                     {
                        stmt.executeUpdate("INSERT INTO conferenceproceeding (documentID, conferenceName, place)" + " VALUES ( '" + ID + "', '" + value6 + "', '" + value7 + "')");
                    }

                    break;

                case "miscellaneous":
                    stmt.executeUpdate("update referencedocument set documentType='other' where documentID='" + ID + "' ");
                    break;

            } // End newType switch

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

            JOptionPane.showMessageDialog(null, "Updated Successfully");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid Inputs\n" + ex.getMessage());
            System.out.print(ex);
        }
    }//GEN-LAST:event_Button_editActionPerformed

    private void Button_deleteFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_deleteFolderActionPerformed
        // TODO add your handling code here:
        DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
        if (selNode != null) {
            int confirmed = JOptionPane.showConfirmDialog(null, "Folder will be Deleted", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirmed == 0) {
                model.removeNodeFromParent(selNode);
                //                saveModel(model);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select Folder to Delete");
        }
    }//GEN-LAST:event_Button_deleteFolderActionPerformed

    private void Button_newFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_newFolderActionPerformed
        // TODO add your handling code here:
        DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
        if (selNode != null) {
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("New Folder");
            model.insertNodeInto(newNode, selNode, selNode.getChildCount());
            TreeNode[] nodes = model.getPathToRoot(newNode);
            TreePath path = new TreePath(nodes);
            jTree1.scrollPathToVisible(path);
            jTree1.setSelectionPath(path);
            jTree1.startEditingAtPath(path);
            //            saveModel(model);
        } else {
            JOptionPane.showMessageDialog(null, "Select Parent Folder");
        }
    }//GEN-LAST:event_Button_newFolderActionPerformed

    private void MenuItem_NewFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_NewFolderActionPerformed
        // TODO add your handling code here:
        DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
        if (selNode != null) {
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("New Folder");
            model.insertNodeInto(newNode, selNode, selNode.getChildCount());
            TreeNode[] nodes = model.getPathToRoot(newNode);
            TreePath path = new TreePath(nodes);
            jTree1.scrollPathToVisible(path);
            jTree1.setSelectionPath(path);
            jTree1.startEditingAtPath(path);
            //            saveModel(model);
        } else {
            JOptionPane.showMessageDialog(null, "Select Parent Folder");
        }
    }//GEN-LAST:event_MenuItem_NewFolderActionPerformed

    private void MenuItem_englishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_englishActionPerformed
        // TODO add your handling code here:
        saveModel(treeModelMap);
        this.setVisible(false);
        Library Arb = new Library();
        Arb.setVisible(true);
    }//GEN-LAST:event_MenuItem_englishActionPerformed

    private void MenuItem_DeleteFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_DeleteFolderActionPerformed
        // TODO add your handling code here:
        DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
        if (selNode != null) {
            int confirmed = JOptionPane.showConfirmDialog(null, "Folder will be Deleted", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirmed == 0) {
                model.removeNodeFromParent(selNode);
                //                saveModel(model);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select Folder tp Delete");
        }
    }//GEN-LAST:event_MenuItem_DeleteFolderActionPerformed

    private void ComboBox_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox_typeActionPerformed
        // TODO add your handling code here:
        int index = ComboBox_type.getSelectedIndex();
        switch (index) {
            case 0:
            // Code for show Book form
            Label_extra.setText("Edition");
            Label_extra.setVisible(true);
            TextField_extra.setVisible(true);
            Label_extra1.setVisible(false);
            TextField_extra1.setVisible(false);

            break;
            case 1:
            // Code for show Journal Article form
            Label_extra.setText("Journal");
            Label_extra.setVisible(true);
            TextField_extra.setVisible(true);
            Label_extra1.setText("Volume");
            Label_extra1.setVisible(true);
            TextField_extra1.setVisible(true);

            break;
            case 2:
            // Code for show Magazine Article form
            Label_extra.setText("Magazine");
            Label_extra.setVisible(true);
            TextField_extra.setVisible(true);
            Label_extra1.setText("Month");
            Label_extra1.setVisible(true);
            TextField_extra1.setVisible(true);

            break;
            case 3:
            // Code for show Web Page form
            Label_extra.setText("URL");
            Label_extra.setVisible(true);
            TextField_extra.setVisible(true);
            Label_extra1.setText(" Access Date");
            Label_extra1.setVisible(true);
            TextField_extra1.setVisible(true);

            break;
            case 4:
            // Code for show Conference Proceeding form
            Label_extra.setText("Conference");
            Label_extra.setVisible(true);
            TextField_extra.setVisible(true);
            Label_extra1.setText("City");
            Label_extra1.setVisible(true);
            TextField_extra1.setVisible(true);

            break;
            case 5:
            // Code for show miscellaneous Document form
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
                if ("Windows".equals(info.getName())) {
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
    private javax.swing.JButton Button_delete;
    private javax.swing.JButton Button_deleteFolder;
    private javax.swing.JButton Button_edit;
    private javax.swing.JButton Button_import;
    private javax.swing.JButton Button_newFolder;
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
    private javax.swing.JMenuItem MenuItem_DeleteFolder;
    private javax.swing.JMenuItem MenuItem_NewFolder;
    private javax.swing.JMenuItem MenuItem_add;
    private javax.swing.JMenuItem MenuItem_delete;
    private javax.swing.JMenuItem MenuItem_edit;
    private javax.swing.JMenuItem MenuItem_english;
    private javax.swing.JMenuItem MenuItem_exit;
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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTree jTree1;
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
