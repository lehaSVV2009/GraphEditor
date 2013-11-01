package graphColoringAlgorithm.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 13.04.13
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
public class XMLParser {

    /*
    public void saveXMLFile() {

        try {

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            this.adjustFileChooser();
            int result = fileChooser.showSaveDialog(this.mainPanel);
            if(result == JFileChooser.APPROVE_OPTION) {

                this.saveFile(builder);
            }
        } catch (Exception e) {
            this.showErrorDialog(ResourceStrings.getStringFromResource("file_save_error"));
        }
    }

    public void saveInBaseFile () {

        String fileName = ResourceStrings.getStringFromResource("database_file_name");
        this.saveInDefinedFile(fileName);
    }

    public void saveInDefinedFile (String fileName) {

        try {

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            this.file = new File((fileName + ".xml"));
            this.saveElementsInDefinedFile(builder);
        } catch (Exception e) {
            this.showErrorDialog(ResourceStrings.getStringFromResource("file_save_error"));
        }
    }

    private void saveElementsInDefinedFile (DocumentBuilder builder) throws Exception {

        Document doc = builder.newDocument();
        Element rootElement = doc.createElement("students");
        doc.appendChild(rootElement);
        String []columnsTitles = this.getColumnsTitlesFromStore();
        List<Student> students = this.getStudentListFromStore();
        this.addStudentsToXMLFile(students, doc, columnsTitles, rootElement);
        this.transformSource(doc);

    }

    private void adjustFileChooser() {

        this.fileChooser = new JFileChooser();
        this.fileChooser.setAcceptAllFileFilterUsed(true);
        this.fileChooser.setCurrentDirectory(new File("."));
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
    }

    private void saveFile(DocumentBuilder builder) throws Exception {

        String fileName = this.fileChooser.getSelectedFile().getAbsolutePath();
        this.file = new File(fileName);
        Document doc = builder.newDocument();
        Element rootElement = doc.createElement("students");
        doc.appendChild(rootElement);
        String []columnsTitles = this.getColumnsTitlesFromStore();
        List<Student> students = this.getStudentListFromStore();
        this.addStudentsToXMLFile(students, doc, columnsTitles, rootElement);
        this.transformSource(doc);
    }

    private String[] getColumnsTitlesFromStore() {

        String []columnsForXML = {
                ResourceStrings.getStringFromResource("xml_fio"),
                ResourceStrings.getStringFromResource("xml_group"),
                ResourceStrings.getStringFromResource("xml_by_illness"),
                ResourceStrings.getStringFromResource("xml_by_other"),
                ResourceStrings.getStringFromResource("xml_by_not_good"),
                ResourceStrings.getStringFromResource("xml_total")
        };
        return columnsForXML;
    }

    private List<Student> getStudentListFromStore() {

        Model model = this.mainPanel.getModel();
        return model.getStudentsList();
    }

    private void addStudentsToXMLFile(List<Student> students, Document doc, String []columnsTitles, Element rootElement) throws Exception{

        int studentNum = students.size();
        for(int studentID = 0; studentID < studentNum; ++studentID) {

            Element studentEl = doc.createElement("student");
            rootElement.appendChild(studentEl);
            Student curStudent = students.get(studentID);
            this.addAllElements(curStudent, columnsTitles, studentEl, doc);
        }
    }

    private void addAllElements(Student curStudent, String []titleNames, Element studentEl, Document doc) throws Exception{

        this.addElement(titleNames[0], TextChanger.getFIOFromStudent(curStudent), studentEl, doc);
        this.addElement(titleNames[1], String.valueOf(curStudent.getGroupNumber()), studentEl, doc);
        this.addElement(titleNames[2], String.valueOf(curStudent.getAbsenceByIllness()), studentEl, doc);
        this.addElement(titleNames[3], String.valueOf(curStudent.getAbsenceByOther()), studentEl, doc);
        this.addElement(titleNames[4], String.valueOf(curStudent.getAbsenceByNotGoodReason()), studentEl, doc);
        this.addElement(titleNames[5], String.valueOf(curStudent.getFullAbsence()), studentEl, doc);
    }

    private void addElement(String title, String value, Element studentEl, Document doc) {

        Element el = doc.createElement(title);
        studentEl.appendChild(el);
        el.appendChild(doc.createTextNode(value));
    }

    private void showErrorDialog(String error) {

        JOptionPane.showMessageDialog(this.mainPanel, error);
    }


    private void transformSource(Document doc) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(this.file);
        transformer.transform(source, streamResult);
    }
      */
}
