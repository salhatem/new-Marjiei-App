
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

    public int ToText() throws IOException {

        int documentId = -1;

       //this.pdfStripper = null;
        //this.pdDoc = null;
        //this.cosDoc = null;
        file = new File(filePath);

        //parser = new PDFParser(new RandomAccessFile(file,"r")); // update for PDFBox V 2.0
        //parser.parse();
        //cosDoc = parser.getDocument();
        //pdfStripper = new PDFTextStripper();
        //pdDoc = new PDDocument(cosDoc);
        //pdDoc.getNumberOfPages();
        //pdfStripper.setStartPage(1);
        //pdfStripper.setEndPage(10);
        PDDocument document = PDDocument.load(file);
        PDDocumentInformation pdd = document.getDocumentInformation();
        author = pdd.getAuthor();
        title = pdd.getTitle();
        numberOfPages = document.getNumberOfPages();

        //reading text from page 1 to 10
        // if you want to get text from full pdf file use this code
        // pdfStripper.setEndPage(pdDoc.getNumberOfPages());  
        //Text = pdfStripper.getText(pdDoc);
        if (author == null) {
            author = "";
        } else if (title == null) {
            String fileName = file.getName();
            int last = fileName.lastIndexOf(".");
            title = last >= 1 ? fileName.substring(0, last) : fileName;
        }
        // Code to insert book information here.
        try {

            FileInputStream fis = new FileInputStream(file);
            int len = (int) file.length();
            // insert pdf file to db
            Query = ("INSERT IGNORE INTO referencedocument (documentType, title, author, pages, referenceFile)" + " VALUES(?, ?, ? ,? ,?)");
            pstmt = con.prepareStatement(Query);
            pstmt.setString(1, "book");
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setInt(4, numberOfPages);
            // method to insert a stream of bytes
            pstmt.setBinaryStream(5, fis, len);
            pstmt.executeUpdate();
            rs = stmt.executeQuery("SELECT documentID FROM referencedocument WHERE title = '" + title + "' AND author = '" + author + "'");
            rs.next();
            documentId = rs.getInt("documentID");
            stmt.executeUpdate("INSERT INTO book (documentID, edition)" + " VALUES ( '" + documentId + "', '" + 0 + "')");
            //*************************************************************
            File f = new File("m.pdf");
            FileOutputStream output = new FileOutputStream(f);
            rs = stmt.executeQuery("SELECT referenceFile FROM referencedocument WHERE title = '" + title + "' AND author = '" + author + "'");
            if (rs.next()) {
                InputStream input = rs.getBinaryStream("referenceFile");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }
            }
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
