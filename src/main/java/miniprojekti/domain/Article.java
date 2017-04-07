package miniprojekti.domain;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * Contains reference information of an article.
 */
public class Article {
    private static final Set<FieldName> requiredFields = EnumSet.of(
        FieldName.AUTHOR, 
        FieldName.JOURNAL, 
        FieldName.TITLE, 
        FieldName.YEAR
    );
    
    private static final Set<FieldName> optionalFields = EnumSet.of(
        FieldName.MONTH, 
        FieldName.NOTE, 
        FieldName.NUMBER, 
        FieldName.PAGES,
        FieldName.VOLUME
    );
    
    private String citationKey;
    
    private ObservableMap<FieldName, Field> fields = FXCollections.observableHashMap();
    
    /**
     * Initialization with a collection of fields instead of a map, calls
     * {@link #Article(java.lang.String, java.util.Map)}.
     * 
     * @see #Article(java.lang.String, java.util.Map)
     */
    public Article(String citationKey, Collection<Field> fields) {
        this(citationKey, fieldMap(fields));
    }
    
    /**
     * Initializes the article with given fields if they are valid. 
     * 
     * @param   citationKey
     * @param   fields
     * 
     * @see miniprojekti.domain.Field
     * @see miniprojekti.domain.FieldName
     * 
     * @throws IllegalArgumentException on missing required fields
     * @throws IllegalArgumentException on invalid optional fields
     */
    public Article(String citationKey, ObservableMap<FieldName, Field> fields) throws IllegalArgumentException {
        if (!fields.keySet().containsAll(requiredFields)) {
            throw new IllegalArgumentException("Required fields are missing");
        }
        
        if (!this.getAllFieldNames().containsAll(fields.keySet())) {
            throw new IllegalArgumentException("Invalid optional fields");
        }
        
        this.citationKey = citationKey;
        this.fields = fields;
    }
    
    /**
     * Gets the field of given fieldname.
     * 
     * @param   name
     * 
     * @see miniprojekti.domain.Field
     * @see miniprojekti.domain.FieldName
     * 
     * @return Fieldname if one exists, otherwise null.
     */
    public Field getField(FieldName name) {
        return fields.getOrDefault(name, null);
    }

    public String getCitationKey() {
        return citationKey;
    }
    
    public ObservableMap<FieldName, Field> getFieldMap() {
        return fields;
    }
    
    public static Set<FieldName> getRequiredFields() {
        return requiredFields;
    }
    
    public static Set<FieldName> getOptionalFields() {
        return optionalFields;
    }
    
    private static ObservableMap<FieldName, Field> fieldMap(Collection<Field> fields) {
        ObservableMap<FieldName, Field> fieldMap = FXCollections.observableHashMap();
        
        for (Field field: fields) {
            fieldMap.put(field.getName(), field);
        }
        
        return fieldMap;
    }
    
    private Set<FieldName> getAllFieldNames() {
        return Stream.concat(optionalFields.stream(), requiredFields.stream())
               .collect(Collectors.toSet());
    }
    
    public String toString() {
        String s = "source: Article\ncitation key: " + citationKey + "\n";
        for(FieldName fn : fields.keySet()){
            s += fn.name().toLowerCase() + ": " + fields.get(fn).getValue() + "\n";
        }
        s += "\n\n";
        return s;
    }
}
