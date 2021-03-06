package miniprojekti.domain;

import java.util.HashSet;
import java.util.Set;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;


public class ReferenceTest {
    Article article;
    Book book;
    Set<Field> fields;
    String citationKey;
    
    @Before
    public void setUp() {
        citationKey = "testkey";
        fields = new HashSet<>();
        
        fields.add(new Field(FieldName.AUTHOR, "Anthony Robins and Janet Rountree and Nathan Rountree"));
        fields.add(new Field(FieldName.JOURNAL, "Computer Science Education"));
        fields.add(new Field(FieldName.TITLE, "Learning and teaching programming: A review and discussion"));
        fields.add(new Field(FieldName.YEAR, "20003"));
        fields.add(new Field(FieldName.VOLUME, "13"));
        fields.add(new Field(FieldName.PAGES, "137-172"));
    }
    
    @Rule
    public ExpectedException expected = ExpectedException.none();
    
    @Test
    public void initializationFailsWithMissingRequiredFields() {
        fields = new HashSet<>();
        
        fields.add(new Field(FieldName.AUTHOR, "Anthony Robins and Janet Rountree and Nathan Rountree"));
        fields.add(new Field(FieldName.JOURNAL, "Computer Science Education"));
        fields.add(new Field(FieldName.TITLE, "Learning and teaching programming: A review and discussion"));
        
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Required fields are missing");
        
        article = new Article(citationKey, fields);
    }
    
    @Test
    public void initializationFailsWithInvalidOptionalFields() {
        fields = new HashSet<>();
        
        fields.add(new Field(FieldName.AUTHOR, "Anthony Robins and Janet Rountree and Nathan Rountree"));
        fields.add(new Field(FieldName.JOURNAL, "Computer Science Education"));
        fields.add(new Field(FieldName.TITLE, "Learning and teaching programming: A review and discussion"));
        fields.add(new Field(FieldName.YEAR, "20003"));
        // Invalid field
        fields.add(new Field(FieldName.EDITION, "5"));
        
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Invalid optional fields");
        
        article = new Article(citationKey, fields);
    }
    
    @Test
    public void initializationWorksWithValidFields() {
        article = new Article(citationKey, fields);
    }
    
    @Test
    public void initializationFailsWithMissingAlternativeFields() {
        fields = new HashSet<>();
        
        fields.add(new Field(FieldName.TITLE, "Clean Code: A Handbook of Agile Software Craftsmanship"));
        //fields.add(new Field(FieldName.AUTHOR, "Martin Robert"));
        fields.add(new Field(FieldName.YEAR, "2008"));
        fields.add(new Field(FieldName.PUBLISHER, "Prentice Hall"));
        
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Required fields are missing");
        
        book = new Book(citationKey, fields);
    }
    
    @Test
    public void getFieldReturnsAProperField() {
        Article art = new Article("maaan", fields);
        Field vol = art.getField(FieldName.VOLUME);
        assertEquals(vol.getValue(), "13");
    }
    
    @Test
    public void fieldToStringWorks() {
        Field yr = new Field(FieldName.YEAR, "20003");
        assertEquals(yr.toString(), "20003");
    }
    
    @Test
    public void initializationWorksWithValidAlternativeFields() {
        fields = new HashSet<>();
        
        fields.add(new Field(FieldName.TITLE, "Clean Code: A Handbook of Agile Software Craftsmanship"));
        fields.add(new Field(FieldName.AUTHOR, "Martin Robert"));
        fields.add(new Field(FieldName.YEAR, "2008"));
        fields.add(new Field(FieldName.PUBLISHER, "Prentice Hall"));
        
        book = new Book(citationKey, fields);
    }
    
    @Test
    public void initializationForInproceedingsWorksWithTags() {
        fields = new HashSet<>();
        
        fields.add(new Field(FieldName.AUTHOR, "Anthony Robins and Janet Rountree and Nathan Rountree"));
        fields.add(new Field(FieldName.BOOKTITLE, "Computer Science Education"));
        fields.add(new Field(FieldName.TITLE, "Learning and teaching programming: A review and discussion"));
        fields.add(new Field(FieldName.YEAR, "20003"));
        Reference ref = new Inproceedings(citationKey, fields, "tag1 tag2");
    }
    
    @Test
    public void testToStringOutput() {
        fields = new HashSet<>();
        
        fields.add(new Field(FieldName.AUTHOR, "Anthony Robins and Janet Rountree and Nathan Rountree"));
        fields.add(new Field(FieldName.BOOKTITLE, "Computer Science Education"));
        fields.add(new Field(FieldName.TITLE, "Learning and teaching programming: A review and discussion"));
        fields.add(new Field(FieldName.YEAR, "20003"));
        Reference ref = new Inproceedings(citationKey, fields, "tag1 tag2");
        assertTrue(ref.toString().contains("\n\tINPROCEEDINGS\t\t\t\tCitation Key: testkey"));
        assertTrue(ref.toString().contains("\n\t\t\tYEAR: 20003"));
        assertTrue(ref.toString().contains("\n\t\t\tBOOKTITLE: Computer Science Education"));
        assertTrue(ref.toString().contains("\n\t\t\tAUTHOR: Anthony Robins and Janet Rountree and Nathan Rountree"));
        assertTrue(ref.toString().contains("\n\t\t\tTITLE: Learning and teaching programming: A review and discussion"));
        assertTrue(ref.toString().contains("\n\t\t\tTags: tag1, tag2\n"));
    }
    
    @Test
    public void constructorsCauseNoErrors() {
        Reference temp = new Article();
        temp = new Book();
        temp = new Inproceedings();
    }
    
}