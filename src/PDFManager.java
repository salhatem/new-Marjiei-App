
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import org.apache.pdfbox.cos.COSDocument;
//import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
//import org.apache.pdfbox.text.PDFTextStripper;
//import java.time.format.DateTimeFormatter;

public class PDFManager {
   //private PDFParser parser;
    //private PDFTextStripper pdfStripper;
    //private PDDocument pdDoc;
    //private COSDocument cosDoc;

    //private String Text, fileName;
    private String filePath, author, title;
    private File file;
    private int numberOfPages;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    String Query;
    PreparedStatement pstmt;

    private void DatabaseConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String host = "jdbc:mysql://localhost:3306/mg-marjieidb";
            String username = "root";
            String password = "";
            String unicode = "?useUnicode=yes&characterEncoding=UTF-8";
            con = DriverManager.getConnection(host + unicode, username, password);
            stmt = con.createStatement();

        } catch (ClassNotFoundException | SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public PDFManager() {
        DatabaseConnect();
    }

    public int ToText(String folderName) throws IOException { 
        
        int documentId = -1;
        file = new File(filePath);
        PDDocument document = PDDocument.load(file);
        // Extract Document Information
        PDDocumentInformation pdd = document.getDocumentInformation();
        author = pdd.getAuthor();
        title = pdd.getTitle();
        numberOfPages = document.getNumberOfPages();
        if (author == null) {
            author = "";
        } else if (title == null) {
            String fileName = file.getName();
            int last = fileName.lastIndexOf(".");
            title = last >= 1 ? fileName.substring(0, last) : fileName;
        }
        
        // Insert Document Information to Database.
        try {
            FileInputStream fis = new FileInputStream(file);
            int len = (int) file.length();
            Query = ("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, folder)" 
                    + " VALUES(?, ?, ? ,? ,?)");
            pstmt = con.prepareStatement(Query);
            pstmt.setString(1, "book");
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setInt(4, numberOfPages);
            pstmt.setString(5, folderName);
           // pstmt.setBinaryStream(6, fis, len);
            int i = pstmt.executeUpdate();
            if (i != -1)
            {
            rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + title 
                    + "' AND author = '" + author + "'");
            rs.next();
            documentId = rs.getInt("documentID");
            stmt.executeUpdate("INSERT INTO book (documentID, edition)" + " VALUES ( '" + documentId + "', '" + 0 + "')");
            } else 
            {
                return -1;
            }
            //*************************************************************
          /*  File f = new File("m.pdf");
            FileOutputStream output = new FileOutputStream(f);
            rs = stmt.executeQuery("SELECT referenceFile FROM referencedocument WHERE title = '" + title + "' AND author = '" + author + "'");
            if (rs.next()) {
                InputStream input = rs.getBinaryStream("referenceFile");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                } 
            } */
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        return documentId;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
